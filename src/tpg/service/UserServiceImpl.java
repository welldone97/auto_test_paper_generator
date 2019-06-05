package tpg.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import cn.hengxin.paper.generator.PaperGenerator;
import ga.Individual;
import ga.Paper;
import ga.PaperMaker;
import ga.Question;
import output.OutputUtil;
import tpg.common.MD5Utils;
import tpg.dao.QuestionDao;
import tpg.dao.UserDao;
import tpg.dao.UserDaoImpl;
import tpg.domain.Exam;
import tpg.domain.ExamQuestions;
import tpg.domain.KnowledgePoint;
import tpg.domain.QuestionDetails;
import tpg.domain.Subject;
import tpg.domain.User;

public class UserServiceImpl implements UserService {

	UserDao userDaoImpl = new UserDaoImpl();
	
	@Override
	public boolean login(User user) {
		User myUser = userDaoImpl.getUserByUsernameAndRole(user.getUsername(), user.getType());
		if(null!=myUser 
				&& user.getUsername().equals(myUser.getUsername()) 
				&& MD5Utils.generateMD5(user.getPassword()).equalsIgnoreCase(myUser.getPassword())
				&& user.getType().equals(myUser.getType())){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Individual makePaper(String subject, List<Integer> kpList, double diff, Map<Integer, Integer> typeCountMapping,
			Map<Integer, Integer> typeScoreMapping) {
		Paper userWantPaper = new Paper();
		userWantPaper.setId(0);
		userWantPaper.setKnowledgePoints(kpList);
		userWantPaper.setDifficulty(diff);
		userWantPaper.setTypeCountMapping(typeCountMapping);
		userWantPaper.setTypeScoreMapping(typeScoreMapping);
		
		return PaperMaker.make(userWantPaper);
		
	}

	@Override
	public void savePaper(Individual paper, List<QuestionDetails> questionDetails, Map<String, String> examInfo) {
		String ename = examInfo.get("ename");
		String examMethod = examInfo.get("examMethod");
		String duration = examInfo.get("duration");
		String kcbianhao = examInfo.get("kcbianhao");
		int singlenum = 0, multinum = 0, blanknum = 0, judgementnum = 0;
		for(QuestionDetails qd : questionDetails){
			switch(qd.getType()){
				case "single":singlenum++;break;
				case "multi":multinum++;break;
				case "blank":blanknum++;break;
				case "judgement":judgementnum++;break;
				default:break;
			}
		}
		int score = paper.getTotalScore();
		List<Question> questions = paper.getQuestions();
		Set<Integer> kps = new HashSet<Integer>();
		for(Question q:questions){
			kps.addAll(q.getKnowledgePoints());
		}
		
		String ga_run_info = "试卷适应度:" + paper.getFitness() + ";"
						+ "试卷难度:" + paper.getDifficulty() + ";"
						+ "总题数:" + paper.getQuestionCount() + ";"
						+ "覆盖知识点:" + kps;
		String gonghao = examInfo.get("gonghao");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ename", ename);
		paramMap.put("examMethod", examMethod);
		paramMap.put("duration", duration);
		paramMap.put("kcbianhao", kcbianhao);
		paramMap.put("singlenum", singlenum);
		paramMap.put("multinum", multinum);
		paramMap.put("blanknum", blanknum);
		paramMap.put("judgementnum", judgementnum);
		paramMap.put("score", score);
		paramMap.put("ga_run_info", ga_run_info);
		paramMap.put("gonghao", gonghao);
		UserDao userDao = new UserDaoImpl();
		int examId = userDao.saveExam(paramMap);
		
		String questiontype = "";
		String questionid = "";
		for(QuestionDetails qd : questionDetails){
			questionid = qd.getId().split("_")[1];
			questiontype = qd.getType();
			userDao.saveExamQuestion(examId, questionid, questiontype);
		}
		
	}

	
	@Override
	public List<Exam> getHistoryPapers(String gonghao) {
		List<Exam> exams = new ArrayList<Exam>();
		exams = userDaoImpl.getExamsByUser(gonghao);
		List<Question> questions;
		List<QuestionDetails> questionDetails;
		for(Exam exam : exams){
			String examId = exam.getExamid();
			questions = userDaoImpl.getQuestionsByExamId(examId);
			QuestionDao qd = new QuestionDao();
			questionDetails = qd.getQuestionByTypeAndId(questions);
			ExamQuestions examQuestions = new ExamQuestions(questionDetails);
			exam.setQuestions(examQuestions);
		}
		return exams;
	}

	@Override
	public Exam getAppointedExamPaper(String examid) {
		Exam exam = new Exam();
		exam = userDaoImpl.getExamById(examid);
		
		Subject subject = userDaoImpl.getSubjectById(exam.getKcbianhao());
		exam.setCourseName(subject.getName());
		if(exam.getGa_run_info()!=null){
			String ga_run_info = exam.getGa_run_info();
			String kpString = exam.getGa_run_info().substring(ga_run_info.indexOf("[")+1, ga_run_info.indexOf("]"));
			System.out.println(kpString);
			String[] kps = kpString.split(",");
			List<KnowledgePoint> knowledgePoints = userDaoImpl.getAppointedKnowledgePoints(kps);
			exam.setKnowledgePoints(knowledgePoints);
			
			if(ga_run_info.contains("难度")){
				for(String str :ga_run_info.split(";")){
					if(str.contains("试卷难度")){
						exam.setDifficulty(str.split(":")[1]);
					}
				}
			}
			
			QuestionDao qd = new QuestionDao();
			List<Question> questions = qd.getQuestionByExamId(examid);
			List<QuestionDetails> questionDetails = qd.getQuestionByTypeAndId(questions);
			ExamQuestions eq = new ExamQuestions(questionDetails);
			exam.setQuestions(eq);
		}
		return exam;
		
		
		
		
		
	}

	@Override
	public String output(Exam exam, Map<String, String> examTermInfo) {
		String outputPath = OutputUtil.outputPaper(exam, examTermInfo); 
		return outputPath;
	}

	@Override
	public boolean download(String path, OutputStream os) throws IOException {
		boolean result = false;
		FileInputStream fis = null;  
		try {
			fis = new FileInputStream(path);
			byte[] b = new byte[1024];  
			int i = 0;  
			while((i = fis.read(b)) > 0)  {  
				os.write(b, 0, i);  
	        }
			os.flush();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fis!=null){
				fis.close();
			}
			if(os!=null){
				os.close();
			}
		}
		return result;
	}





}
