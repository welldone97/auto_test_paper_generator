package tpg.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ga.Individual;
import ga.Question;
import tpg.common.BaseServlet;
import tpg.dao.QuestionDao;
import tpg.domain.Exam;
import tpg.domain.QuestionDetails;
import tpg.domain.User;
import tpg.service.UserService;
import tpg.service.UserServiceImpl;

@WebServlet("/user")
public class UserServlet extends BaseServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String startMakePaper(HttpServletRequest request, HttpServletResponse response){
		return "/WEB-INF/pages/client/make_paper.jsp";
	}
	
	public void makePaper(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String subject = request.getParameter("subject");
		
		String knowledgePoints = request.getParameter("knowledgePoints");
		String[] kps = knowledgePoints.split(",");
		List<Integer> kpList = new ArrayList<Integer>();
		for(String kp : kps){
			kpList.add(Integer.parseInt(kp));
		}
		
		String difficulty = request.getParameter("difficulty");
		double diff = 0.5;
		try{
			diff = Double.parseDouble(difficulty);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("subject:" + subject + ", knowledgePoints:" + knowledgePoints + " difficulty:" + difficulty);
		
		Map<Integer, Integer> typeCountMapping = new HashMap<Integer, Integer>();
		Map<Integer, Integer> typeScoreMapping = new HashMap<Integer, Integer>();
		for(int typeCount=1; typeCount<=4; typeCount++){
			String count = request.getParameter("count" + typeCount);
			String score = request.getParameter("score" + typeCount);
			System.out.println("count" + typeCount + ":" + count + ", " + "score" + typeCount + ":" + score);
			int intCount = 0, intScore = 0;
			try{
				intCount = Integer.parseInt(count);
				intScore = Integer.parseInt(score);
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
			
			typeCountMapping.put(typeCount, intCount);
			typeScoreMapping.put(typeCount, intScore);
		}
		
		UserService userService = new UserServiceImpl();
		Individual paper = userService.makePaper(subject, kpList, diff, typeCountMapping, typeScoreMapping);
		List<Question> questions = paper.getQuestions();
		QuestionDao qd = new QuestionDao();
		List<QuestionDetails> qds = qd.getQuestionByTypeAndId(questions);
		
		HttpSession session = request.getSession();
		session.removeAttribute("paper");
		session.setAttribute("paper", paper);
		session.removeAttribute("questionDetails");
		session.setAttribute("questionDetails", qds);
		session.removeAttribute("subject");
		session.setAttribute("subject", subject);
		
		response.getWriter().write("success");
		
	}
	
	public String paperDetail(HttpServletRequest request, HttpServletResponse response){
		return "/WEB-INF/pages/client/paper_detail.jsp";
	}
	
	public void savePaper(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		Individual paper =  (Individual) session.getAttribute("paper");
		List<QuestionDetails> questionDetails = (List<QuestionDetails>) session.getAttribute("questionDetails");
		int totalScore = paper.getTotalScore();
		
		Map<String, String> examInfo = new HashMap<String,String>();
		String duration = request.getParameter("duration");
		examInfo.put("duration", duration);
		String ename = new String(request.getParameter("ename").getBytes("ISO-8859-1"), "UTF-8");
		examInfo.put("ename", ename);
		String examMethod = request.getParameter("examMethod");
		examInfo.put("examMethod", examMethod);
		String kcbianhao = (String) session.getAttribute("subject");
		examInfo.put("kcbianhao", kcbianhao);
		User user = (User) session.getAttribute("user");
		String gonghao = user.getUsername();
		examInfo.put("gonghao", gonghao);
		
		System.out.println("总分" + totalScore + "时长"+ duration + "ename" + ename);
		
		
		UserService userService = new UserServiceImpl();
		userService.savePaper(paper, questionDetails, examInfo);
		response.getWriter().write("保存成功！");
	}

	public String getHistory(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String gonghao = user.getUsername();
		UserService userService = new UserServiceImpl();
		List<Exam> histories = userService.getHistoryPapers(gonghao);
		for(Exam exam: histories){
			System.out.println(exam.getEname());
			for(QuestionDetails qd : exam.getQuestions().getQuestions()){
				System.out.println(qd.getType() + qd.getId());
			}
		}
		session.setAttribute("histories", histories);
		return "/WEB-INF/pages/client/history.jsp";
	}
	
	public String examDetails(HttpServletRequest request, HttpServletResponse response){
		String examid = request.getParameter("examid");
		UserService userService = new UserServiceImpl();
		Exam exam = userService.getAppointedExamPaper(examid);
		request.getSession().setAttribute("exam", exam);
		return "/WEB-INF/pages/client/exam_details.jsp";
	}
	
	public String returnHome(HttpServletRequest request, HttpServletResponse response){
		return "/WEB-INF/pages/client/make_paper.jsp";
	}
	
	public void outputPaper(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String startYear = request.getParameter("startYear");
		String endYear = request.getParameter("endYear");
		String term = request.getParameter("term");
		Map<String,String> examTermInfo = new HashMap<String, String>();
		examTermInfo.put("startYear", startYear);
		examTermInfo.put("endYear", endYear);
		examTermInfo.put("term", term);
		Exam exam =(Exam)request.getSession().getAttribute("exam");
		UserService userService = new UserServiceImpl();
		String path = userService.output(exam, examTermInfo);
		String filename = "outputPaper.docx";
		response.setContentType("application/x-download");  
		response.addHeader("Content-Disposition","attachment;filename=" + filename); 
		userService.download(path, response.getOutputStream());
	}
}
