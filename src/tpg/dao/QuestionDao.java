package tpg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.mysql.fabric.xmlrpc.base.Array;

import ga.Question;
import tpg.common.DataBase;
import tpg.domain.QuestionDetails;

public class QuestionDao {
	
	private Connection conn = null;
	public QuestionDao() {
		DataBase db = new DataBase();
		this.conn = db.getConnection();
	}
	
	
	public List<Question> getQuestionsByKnowledgePoints(List<Integer> knowledgePoints){
		StringBuffer kps = new StringBuffer("(");
		for(int i =0; i<knowledgePoints.size();i++) {
			kps.append(knowledgePoints.get(i));
			if(i!=knowledgePoints.size()-1) {
				kps.append(",");
			}
		}
		kps.append(")");
		List<Question> questions = new ArrayList<Question>();
		try {
			questions.addAll(this.getSingleByKnowledgePoints(kps.toString()));
			questions.addAll(this.getMultiByKnowledgePoints(kps.toString()));
			questions.addAll(this.getBlankByKnowledgePoints(kps.toString()));
			questions.addAll(this.getJudgementByKnowledgePoints(kps.toString()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(Question q: questions) {
			System.out.println(q.toString());
		}
		
		return questions;
		
	}
	
	private List<Question> getJudgementByKnowledgePoints(String kps) throws SQLException {
		List<Question> judgements = new ArrayList<Question>();
		String judgementSql = "select judgementid, jmark, difficulty, Knowledgepoint from kaoshi_judgement where KnowledgePoint in" + kps.toString();
		PreparedStatement ps = this.conn.prepareStatement(judgementSql);
		ResultSet rs = ps.executeQuery();
		Question question;
		while(rs.next()) {
			question = new Question();
			question.setId("judgement_" + rs.getString("judgementid"));
			question.setType(4);
			question.setScore(rs.getInt("jmark"));
			question.setDifficulty(rs.getDouble("difficulty"));
			List<Integer> kpInquestion = new ArrayList<Integer>();
			kpInquestion.add(rs.getInt("Knowledgepoint"));
			question.setKnowledgePoints(kpInquestion);
			judgements.add(question);
		}
		return judgements;
		
	}

	private List<Question> getBlankByKnowledgePoints(String kps) throws SQLException {
		List<Question> blanks = new ArrayList<Question>();
		String blankSql = "select blankid, bmark, difficulty, Knowledgepoint from kaoshi_blank where KnowledgePoint in" + kps.toString();
		PreparedStatement ps = this.conn.prepareStatement(blankSql);
		ResultSet rs = ps.executeQuery();
		Question question;
		while(rs.next()) {
			question = new Question();
			question.setId("blank_" + rs.getString("blankid"));
			question.setType(3);
			question.setScore(rs.getInt("bmark"));
			question.setDifficulty(rs.getDouble("difficulty"));
			List<Integer> kpInquestion = new ArrayList<Integer>();
			kpInquestion.add(rs.getInt("Knowledgepoint"));
			question.setKnowledgePoints(kpInquestion);
			blanks.add(question);
		}
		return blanks;
	}

	private List<Question> getMultiByKnowledgePoints(String kps) throws SQLException {
		List<Question> multis = new ArrayList<Question>();
		String multiSql = "select multiid, mmark, difficulty, Knowledgepoint from kaoshi_multi where KnowledgePoint in" + kps.toString();
		PreparedStatement ps = this.conn.prepareStatement(multiSql);
		ResultSet rs = ps.executeQuery();
		Question question;
		while(rs.next()) {
			question = new Question();
			question.setId("multi_" + rs.getString("multiid"));
			question.setType(2);
			question.setScore(rs.getInt("mmark"));
			question.setDifficulty(rs.getDouble("difficulty"));
			List<Integer> kpInquestion = new ArrayList<Integer>();
			kpInquestion.add(rs.getInt("Knowledgepoint"));
			question.setKnowledgePoints(kpInquestion);
			multis.add(question);
		}
		return multis;
	}

	private List<Question> getSingleByKnowledgePoints(String kps) throws SQLException {
		List<Question> singles = new ArrayList<Question>();
		String singleSql = "select singleid, smark, difficulty, Knowledgepoint from kaoshi_single where KnowledgePoint in" + kps.toString();
		PreparedStatement ps = this.conn.prepareStatement(singleSql);
		ResultSet rs = ps.executeQuery();
		Question question;
		while(rs.next()) {
			question = new Question();
			question.setId("single_" + rs.getString("singleid"));
			question.setType(1);
			question.setScore(rs.getInt("smark"));
			question.setDifficulty(rs.getDouble("difficulty"));
			List<Integer> kpInquestion = new ArrayList<Integer>();
			kpInquestion.add(rs.getInt("Knowledgepoint"));
			question.setKnowledgePoints(kpInquestion);
			singles.add(question);
		}
		return singles;
		
	}

	public List<QuestionDetails> getQuestionByTypeAndId(List<Question> questions) {
		
		StringBuffer singles = new StringBuffer("(");
		StringBuffer multis = new StringBuffer("(");
		StringBuffer blanks = new StringBuffer("(");
		StringBuffer judgements = new StringBuffer("(");
		for(Question q:questions){
			System.out.println(q.getId());
			String[] s = q.getId().split("_");
			String type = s[0];
			int id = Integer.parseInt(s[1]);
			switch(type){
				case "single":singles.append(id+",");break;
				case "multi":multis.append(id + ",");break;
				case "blank":blanks.append(id + ",");break;
				case "judgement":judgements.append(id + ",");break;
			}
		}
		List<QuestionDetails> questionDetails = new ArrayList<QuestionDetails>();
		
		System.out.println("singles:" + singles.toString());
		if(singles.toString().contains(",")){
			singles.replace(singles.lastIndexOf(","), singles.lastIndexOf(",")+1, ")");
			questionDetails.addAll(getSinglesByIds(singles.toString()));
		}
		
		System.out.println("multis:" + multis.toString());
		if(multis.toString().contains(",")){
			multis.replace(multis.lastIndexOf(","), multis.lastIndexOf(",")+1, ")");
			questionDetails.addAll(getMultisByIds(multis.toString()));;
		}
		
		System.out.println("blanks:" + blanks.toString());
		if(blanks.toString().contains(",")){
			blanks.replace(blanks.lastIndexOf(","), blanks.lastIndexOf(",")+1, ")");
			questionDetails.addAll(getBlanksByIds(blanks.toString()));
		}
		
		System.out.println("judgements:" + judgements.toString());
		if(judgements.toString().contains(",")){
			judgements.replace(judgements.lastIndexOf(","), judgements.lastIndexOf(",")+1, ")");
			questionDetails.addAll(getJudgementsByIds(judgements.toString()));
		}
		
		return questionDetails;
		
	}

	private List<? extends QuestionDetails> getJudgementsByIds(String judgementsIds) {
		List<QuestionDetails> judgements = new ArrayList<QuestionDetails>();
		String sql  = "select judgementid, jtitle, jmark from kaoshi_judgement where judgementid in" + judgementsIds;
		System.out.println(sql);
		PreparedStatement ps;
		ResultSet rs;
		QuestionDetails qd = null;
		try {
			ps = this.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				qd = new QuestionDetails();
				qd.setId("judgement_" +rs.getString("judgementid"));
				qd.setType("judgement");
				qd.setTitle(rs.getString("jtitle"));
				qd.setScore(rs.getString("jmark"));
				judgements.add(qd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return judgements;
	}

	private List<? extends QuestionDetails> getBlanksByIds(String blanksIds) {
		List<QuestionDetails> blanks = new ArrayList<QuestionDetails>();
		String sql = "select blankid, bfronttitle, bbacktitle, bmark from kaoshi_blank where blankid in" + blanksIds;
		System.out.println(sql);
		PreparedStatement ps;
		ResultSet rs;
		QuestionDetails qd = null;
		try {
			ps = this.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				qd = new QuestionDetails();
				qd.setId("blank_" + rs.getString("blankid"));
				qd.setType("blank");
				qd.setTitle(rs.getString("bfronttitle") + "________________" + rs.getString("bbacktitle"));
				qd.setScore(rs.getString("bmark"));
				blanks.add(qd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return blanks;
	}

	private List<? extends QuestionDetails> getMultisByIds(String multisIds) {
		List<QuestionDetails> multis = new ArrayList<QuestionDetails>();
		String sql = "select multiid, msubject, moptionA,moptionB,moptionC,moptionD,mmark from kaoshi_multi where multiid in "  + multisIds;
		System.out.println(sql);
		PreparedStatement ps;
		ResultSet rs;
		QuestionDetails qd = null;
		try {
			ps = this.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				qd = new QuestionDetails();
				qd.setId("multi_" + rs.getString("multiid"));
				qd.setType("multi");
				qd.setTitle(rs.getString("msubject"));
				qd.setOptionA(rs.getString("moptionA"));
				qd.setOptionB(rs.getString("moptionB"));
				qd.setOptionC(rs.getString("moptionC"));
				qd.setOptionD(rs.getString("moptionD"));
				qd.setScore(rs.getString("mmark"));
				multis.add(qd);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return multis;
		
	}

	private List<? extends QuestionDetails> getSinglesByIds(String singlesIds) {
		List<QuestionDetails> singles = new ArrayList<QuestionDetails>();
		String sql = "select singleid, ssubject, soptionA,soptionB,soptionC,soptionD,smark from kaoshi_single where singleid in " + singlesIds;
		System.out.println(sql);
		PreparedStatement ps;
		ResultSet rs;
		QuestionDetails qd = null;
		try {
			ps = this.conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()){
				qd = new QuestionDetails();
				qd.setId("single_" + rs.getString("singleid"));
				qd.setType("single");
				qd.setTitle(rs.getString("ssubject"));
				qd.setOptionA(rs.getString("soptionA"));
				qd.setOptionB(rs.getString("soptionB"));
				qd.setOptionC(rs.getString("soptionC"));
				qd.setOptionD(rs.getString("soptionD"));
				qd.setScore(rs.getString("smark"));
				singles.add(qd);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return singles;
	}


	public List<Question> getQuestionByExamId(String examid) {
		List<Question> questions = new ArrayList<Question>();
		String sql = "select questiontype, questionid from kaoshi_examquestions where examid = ?";
		DataBase db = new DataBase();
		PreparedStatement ps;
		ResultSet rs;
		Question question;
		try {
			ps= db.getConnection().prepareStatement(sql);
			ps.setObject(1, examid);
			rs = ps.executeQuery();
			while(rs.next()){
				question = new Question();
				question.setId(rs.getString("questiontype") + "_" + rs.getInt("questionid"));
				questions.add(question);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questions;
		
	}


}
