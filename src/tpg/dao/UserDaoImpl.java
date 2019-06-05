package tpg.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ga.Question;
import tpg.common.DataBase;
import tpg.common.DataBaseUtil;
import tpg.domain.Exam;
import tpg.domain.KnowledgePoint;
import tpg.domain.QuestionDetails;
import tpg.domain.Subject;
import tpg.domain.User;

public class UserDaoImpl implements UserDao{
	
	@Override
	public User getUserByUsernameAndRole(String username, String type) {
		DataBaseUtil db = new DataBaseUtil();
		String sql = "select * from user where username = ? and type = ?;";
		Object[] params = new Object[]{username, type};
		Class<User> clazz = User.class;
		List<User> userList = db.query(sql, params, clazz);
		db.closeConnection();
		if(userList.size() == 1){
			return userList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public int saveExam(Map<String, Object> paramMap) {
		DataBase db = new DataBase();
		String sql = "insert into kaoshi_exam(kcbianhao, ename, examMethod, duration, singlenum, multinum, blanknum, judgementnum, score, addtime, ga_run_info, gonghao) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?);";
		PreparedStatement ps;
		ResultSet idResultSet;
		int id = 0;
		try {
			ps = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setObject(1, paramMap.get("kcbianhao"));
			ps.setObject(2, paramMap.get("ename"));
			ps.setObject(3, paramMap.get("examMethod"));
			ps.setObject(4, paramMap.get("duration"));
			ps.setObject(5, paramMap.get("singlenum"));
			ps.setObject(6, paramMap.get("multinum"));
			ps.setObject(7, paramMap.get("blanknum"));
			ps.setObject(8, paramMap.get("judgementnum"));
			ps.setObject(9, paramMap.get("score"));
			ps.setObject(10, paramMap.get("addtime"));
			ps.setObject(11, paramMap.get("ga_run_info"));
			ps.setObject(12, paramMap.get("gonghao"));
			ps.executeUpdate();
			idResultSet = ps.getGeneratedKeys();
			if(idResultSet.next()){
				id = idResultSet.getInt(1);
			}
			db.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
		
	}

	@Override
	public void saveExamQuestion(int examId, String questionid, String questiontype) {
		String sql = "insert into kaoshi_examquestions(examid, questiontype, questionid) values(?,?,?)";
		DataBase db = new DataBase();
		try {
			PreparedStatement ps = db.getConnection().prepareStatement(sql);
			ps.setObject(1, examId);
			ps.setObject(2, questiontype);
			ps.setObject(3, questionid);
			int rs = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Exam> getExamsByUser(String gonghao) {
		List<Exam> exams = new ArrayList<Exam>();
		String sql = "select examid, kcbianhao, ename, examMethod, duration, singlenum, multinum, blanknum, judgementnum, score, addtime, ga_run_info"
				+ " from kaoshi_exam where kaoshi_exam.gonghao = ? order by examid desc";
		System.out.println(sql);
		DataBase db = new DataBase();
		PreparedStatement ps;
		ResultSet rs;
		Exam exam;
		try {
			ps = db.getConnection().prepareStatement(sql);
			ps.setObject(1, gonghao);
			rs = ps.executeQuery();
			
			while(rs.next()){
				exam = new Exam();
				exam.setExamid(rs.getString("examid"));
				exam.setKcbianhao(rs.getString("kcbianhao"));
				exam.setEname(rs.getString("ename"));
				exam.setExamMethod(rs.getString("examMethod"));
				exam.setDuration(rs.getString("duration"));
				exam.setSinglenum(rs.getString("singlenum"));
				exam.setMultinum(rs.getString("multinum"));
				exam.setBlanknum(rs.getString("blanknum"));
				exam.setJudgementnum(rs.getString("judgementnum"));
				exam.setScore(rs.getString("score"));
				exam.setAddtime(rs.getString("addtime"));
				exam.setGa_run_info(rs.getString("ga_run_info"));
				exams.add(exam);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exams;
		
	}

	@Override
	public List<Question> getQuestionsByExamId(String examId) {
		String sql = "select questiontype, questionid from kaoshi_examquestions where examid = ?";
		DataBase db = new DataBase();
		List<Question> questions = new ArrayList<Question>();
		PreparedStatement ps;
		ResultSet rs;
		Question question;
		try {
			ps = db.getConnection().prepareStatement(sql);
			ps.setObject(1, examId);
			rs = ps.executeQuery();
			while(rs.next()){
				question = new Question();
				switch(rs.getString("questiontype")){
					case "single":question.setType(1);
					case "multi":question.setType(2);
					case "blank":question.setType(3);
					case "judgement":question.setType(4);
				}
				question.setId(rs.getString("questiontype") + "_"  + rs.getString("questionid"));
				questions.add(question);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(Question q:questions){
			System.out.println(q.getId());
		}
		return questions;
	}

	@Override
	public Exam getExamById(String examid) {
		String sql = "select examid, kcbianhao, ename, examMethod, duration, singlenum, multinum, blanknum, judgementnum, score, addtime, ga_run_info"
				+ " from kaoshi_exam where examid = ?";
		DataBase db = new DataBase();
		PreparedStatement ps;
		ResultSet rs;
		Exam exam = null;
		try {
			ps = db.getConnection().prepareStatement(sql);
			ps.setObject(1, examid);
			rs = ps.executeQuery();
			while(rs.next()){
				exam= new Exam();
				exam.setExamid(rs.getString("examid"));
				exam.setKcbianhao(rs.getString("kcbianhao"));
				exam.setEname(rs.getString("ename"));
				exam.setExamMethod(rs.getString("examMethod"));
				exam.setDuration(rs.getString("duration"));
				exam.setSinglenum(rs.getString("singlenum"));
				exam.setMultinum(rs.getString("multinum"));
				exam.setBlanknum(rs.getString("blanknum"));
				exam.setJudgementnum(rs.getString("judgementnum"));
				exam.setScore(rs.getString("score"));
				exam.setAddtime(rs.getString("addtime"));
				exam.setGa_run_info(rs.getString("ga_run_info"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exam;
	}

	
	@Override
	public List<KnowledgePoint> getAppointedKnowledgePoints(String[] kps) {
		DataBaseUtil db = new DataBaseUtil();
		StringBuffer kpString = new StringBuffer("(");
		for(String kp:kps){
			kpString.append(kp);
			kpString.append(",");
		}
		kpString.replace(kpString.lastIndexOf(","), kpString.lastIndexOf(",")+1, ")");
		String sql = "select * from knowledge_point where id in " + kpString.toString();
		List<KnowledgePoint> knowledgePoints = db.query(sql, null, KnowledgePoint.class);
		for(KnowledgePoint kp:knowledgePoints){
			System.out.println(kp.getName());
		}
		return knowledgePoints;
	}

	@Override
	public Subject getSubjectById(String kcbianhao) {
		String sql = "select * from subject where id = ?";
		Object[] params = {kcbianhao};
		Class<Subject> clazz = Subject.class;
		DataBaseUtil db = new DataBaseUtil();
		List<Subject> subjects = db.query(sql, params, clazz);
		db.closeConnection();
		return subjects.get(0);
	}
	
	

}
