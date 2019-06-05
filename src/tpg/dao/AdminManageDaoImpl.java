package tpg.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.record.DBCellRecord;

import tpg.common.DataBase;
import tpg.common.DataBaseUtil;
import tpg.domain.Blank;
import tpg.domain.Comprehensive;
import tpg.domain.Judgement;
import tpg.domain.KnowledgePoint;
import tpg.domain.MultiChoice;
import tpg.domain.Question;
import tpg.domain.QuestionType;
import tpg.domain.ShortAnswer;
import tpg.domain.SingleChoice;
import tpg.domain.Subject;

public class AdminManageDaoImpl implements AdminManageDao {

	private String[] tables = {
			"question_single_choice ",
			"question_multi_choice ",
			"question_blank ",
			"question_judgement ",
			"question_short_answer ",
			"question_comprehensive "
			};
	
	@Override
	public List<QuestionType> findAllQuestionsType() {
		List<QuestionType> questionTypeList = null;
		DataBaseUtil db = new DataBaseUtil();
		String sql = "select * from question_type;";
		Class<QuestionType> clazz = QuestionType.class;
		questionTypeList = db.query(sql, null, clazz);
		db.closeConnection();
		return questionTypeList;
	}

	@Override
	public List<Subject> findAllSubjects() {
		List<Subject> subjectList = null;
		DataBaseUtil db = new DataBaseUtil();
		
		String sql = "select * from subject";
		Class<Subject> clazz = Subject.class;
		subjectList = db.query(sql, null, clazz);
		db.closeConnection();
		
		return subjectList;
	}

	@Override
	public List<KnowledgePoint> findKnowledgePointsBySubjectId(String subjectId) {
		List<KnowledgePoint> knowledgePointList = null;
		DataBaseUtil db = new DataBaseUtil();

		String sql = "select * from knowledge_point where subjectId = ?;";
		Object[] params = new Object[]{subjectId};
		Class<KnowledgePoint> clazz = KnowledgePoint.class;
		knowledgePointList = db.query(sql, params, clazz);
		db.closeConnection();
		return knowledgePointList;
	}

	@Override
	public boolean insertSingleChoice(Map<String, String> paramMap) {
		DataBaseUtil db = new DataBaseUtil();
		String sql = "insert into kaoshi_single (kcbianhao, ssubject, soptionA, soptionB, soptionC, soptionD, sanswer, smark, difficulty, Knowledgepoint, addtime, gonghao)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?);";
		String kcbianhao = paramMap.get("kcbianhao");
		String ssubject = paramMap.get("ssubject");
		String soptionA = paramMap.get("soptionA");
		String soptionB = paramMap.get("soptionB");
		String soptionC = paramMap.get("soptionC");
		String soptionD = paramMap.get("soptionD");
		String sanswer = paramMap.get("sanswer");
		String smark = paramMap.get("smark");
		String difficulty = paramMap.get("difficulty");
		String Knowledgepoint = paramMap.get("Knowledgepoint");
		String addtime = (new Date().toLocaleString());
		String gonghao = paramMap.get("gonghao");
		System.out.println(sql);
		Object[] params = {kcbianhao, ssubject, soptionA, soptionB, soptionC, soptionD, sanswer, smark, difficulty, Knowledgepoint, addtime, gonghao};
		int effectRow = db.update(sql, params);
		
		return effectRow>0;
	}

	@Override
	public boolean insertMultiChoice(Map<String, String> paramMap) {
		DataBaseUtil db = new DataBaseUtil();
		String sql = "insert into kaoshi_multi (kcbianhao, msubject, moptionA, moptionB, moptionC, moptionD, manswer, mmark, difficulty, Knowledgepoint, addtime, gonghao)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?);";
		String kcbianhao = paramMap.get("kcbianhao");
		String msubject = paramMap.get("msubject");
		String moptionA = paramMap.get("moptionA");
		String moptionB = paramMap.get("moptionB");
		String moptionC = paramMap.get("moptionC");
		String moptionD = paramMap.get("moptionD");
		String manswer = paramMap.get("manswer");
		String mmark = paramMap.get("mmark");
		String difficulty = paramMap.get("difficulty");
		String Knowledgepoint = paramMap.get("Knowledgepoint");
		String addtime = (new Date().toLocaleString());
		String gonghao = paramMap.get("gonghao");
		System.out.println(sql);
		Object[] params = {kcbianhao, msubject, moptionA, moptionB, moptionC, moptionD, manswer, mmark, difficulty, Knowledgepoint, addtime, gonghao};
		int effectRow = db.update(sql, params);
		
		return effectRow>0;
	}

	@Override
	public boolean insertBlank(Map<String, String> paramMap) {
		DataBaseUtil db = new DataBaseUtil();
		String sql = "insert into kaoshi_blank (kcbianhao, bfronttitle, bbacktitle, banswer, bmark, difficulty, Knowledgepoint, addtime, gonghao)"
				+ "values(?,?,?,?,?,?,?,?,?);";
		String kcbianhao = paramMap.get("kcbianhao");
		String bfronttitle = paramMap.get("bfronttitle");
		String bbacktitle = paramMap.get("bbacktitle");
		String banswer = paramMap.get("banswer");
		String bmark = paramMap.get("bmark");
		String difficulty = paramMap.get("difficulty");
		String Knowledgepoint = paramMap.get("Knowledgepoint");
		String addtime = (new Date().toLocaleString());
		String gonghao = paramMap.get("gonghao");
		System.out.println(sql);
		Object[] params = {kcbianhao, bfronttitle, bbacktitle, banswer, bmark, difficulty, Knowledgepoint, addtime, gonghao};
		int effectRow = db.update(sql, params);
		
		return effectRow>0;
	}

	@Override
	public boolean insertJudgement(Map<String, String> paramMap) {
		DataBaseUtil db = new DataBaseUtil();
		String sql = "insert into kaoshi_judgement (kcbianhao, jtitle, janswer, jmark, difficulty, Knowledgepoint, addtime, gonghao)"
				+ "values(?,?,?,?,?,?,?,?);";
		String kcbianhao = paramMap.get("kcbianhao");
		String jtitle = paramMap.get("jtitle");
		String janswer = paramMap.get("janswer");
		String jmark = paramMap.get("jmark");
		String difficulty = paramMap.get("difficulty");
		String Knowledgepoint = paramMap.get("Knowledgepoint");
		String addtime = (new Date().toLocaleString());
		String gonghao = paramMap.get("gonghao");
		System.out.println(sql);
		Object[] params = {kcbianhao, jtitle, janswer, jmark, difficulty, Knowledgepoint, addtime, gonghao};
		int effectRow = db.update(sql, params);
		
		return effectRow>0;	
	}

	@Override
	public boolean insertShortAnswer(Map<String, String> paramMap) {
		DataBaseUtil db = new DataBaseUtil();
		StringBuffer sql1 = new StringBuffer("insert into question_short_answer("); 
		StringBuffer sql2 = new StringBuffer("values(");
		Iterator<String> it = paramMap.keySet().iterator();
		Object[] params = new Object[paramMap.size()];
		System.out.println("遍历paramMap");
		int i=0;
		while(it.hasNext()){
			String key = it.next();
			sql1.append(key + ", ");
			String value = paramMap.get(key);
			sql2.append("?,");
			params[i] = value;
			i++;
			System.out.println(key + "=" + value);
		}
		sql1.append("insertTime)");
		sql2.append("NOW())");
		String finalSql = sql1.append(sql2).toString();
		System.out.println(finalSql);
		for(Object param : params){
			System.out.print(param + ", ");
		}
		int effectRow = db.update(finalSql, params);
		return (effectRow>0);		
	}
	
	@Override
	public boolean insertComprehensive(Map<String, String> paramMap) {
		DataBaseUtil db = new DataBaseUtil();
		StringBuffer sql1 = new StringBuffer("insert into question_comprehensive("); 
		StringBuffer sql2 = new StringBuffer("values(");
		Iterator<String> it = paramMap.keySet().iterator();
		Object[] params = new Object[paramMap.size()];
		System.out.println("遍历paramMap");
		int i=0;
		while(it.hasNext()){
			String key = it.next();
			sql1.append(key + ", ");
			String value = paramMap.get(key);
			sql2.append("?,");
			params[i] = value;
			i++;
			System.out.println(key + "=" + value);
		}
		sql1.append("insertTime)");
		sql2.append("NOW())");
		String finalSql = sql1.append(sql2).toString();
		System.out.println(finalSql);
		for(Object param : params){
			System.out.print(param + ", ");
		}
		int effectRow = db.update(finalSql, params);
		return (effectRow>0);		
	}

	@Override
	public List<Question> findAppointedTitles(Map<String, String> paramsMap) {
		List<Question> results = new ArrayList<Question>();
		
		if(paramsMap.containsKey("questionTypeId")){
			String questionTypeId = paramsMap.get("questionTypeId"); 
			switch(questionTypeId){
				case QuestionType.SINGLE_CHOICE:results.addAll(getAppointedSingleChoice(paramsMap));break;
				case QuestionType.MULTI_CHOICE:results.addAll(getAppointedMultiChoice(paramsMap));break;
				case QuestionType.BLANK:results.addAll(getAppointedBlank(paramsMap));break;
				case QuestionType.JUDGEMENT:results.addAll(getAppointedJudgement(paramsMap));break;
			}
		}else{
			results.addAll(getAppointedSingleChoice(paramsMap));
			results.addAll(getAppointedMultiChoice(paramsMap));
			results.addAll(getAppointedBlank(paramsMap));
			results.addAll(getAppointedJudgement(paramsMap));
		}
		return results;
	}

	private List<Question> getAppointedJudgement(Map<String, String> paramsMap) {
		String sql = "select judgementid, kcbianhao, Knowledgepoint, jtitle, addtime from kaoshi_judgement where";
		if(paramsMap.containsKey("subjectId")){
			String kcbianhao = paramsMap.get("subjectId");
			sql += " kcbianhao = " + kcbianhao + " and ";
		}
		if(paramsMap.containsKey("knowledgePointId")){
			String Knowledgepoint = paramsMap.get("knowledgePointId");
			sql += " Knowledgepoint = " + Knowledgepoint + " and ";
		}
		if(paramsMap.containsKey("keyword")){
			String jtitle = paramsMap.get("keyword");
			sql += " jtitle like '%" + jtitle + "%' and ";
		}
		sql = sql.substring(0, sql.lastIndexOf("and"));
		System.out.println(sql);
		DataBase db = new DataBase();
		PreparedStatement ps;
		ResultSet rs;
		List<Question> judgements = new ArrayList<Question>();
		Question question;
		try {
			ps = db.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				question = new Question();
				question.setId(rs.getInt("judgementid"));
				question.setSubjectId(rs.getInt("kcbianhao"));
				question.setQuestionTypeId(Integer.parseInt(QuestionType.JUDGEMENT));
				question.setKnowledgePoint(rs.getInt("Knowledgepoint"));
				question.setTitle(rs.getString("jtitle"));
				question.setInsertTime(rs.getString("addtime"));
				judgements.add(question);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();
		
		return judgements;
	}

	private List<Question> getAppointedBlank(Map<String, String> paramsMap) {
		String sql = "select blankid, kcbianhao, Knowledgepoint, bfronttitle, bbacktitle, addtime from kaoshi_blank where";
		if(paramsMap.containsKey("subjectId")){
			String kcbianhao = paramsMap.get("subjectId");
			sql += " kcbianhao = " + kcbianhao + " and ";
		}
		if(paramsMap.containsKey("knowledgePointId")){
			String Knowledgepoint = paramsMap.get("knowledgePointId");
			sql += " Knowledgepoint = " + Knowledgepoint + " and ";
		}
		if(paramsMap.containsKey("keyword")){
			String bfronttitle = paramsMap.get("keyword");
			sql += " bfronttitle like '%" + bfronttitle + "%' and ";
		}
		sql = sql.substring(0, sql.lastIndexOf("and"));
		System.out.println(sql);
		DataBase db = new DataBase();
		PreparedStatement ps;
		ResultSet rs;
		List<Question> blanks = new ArrayList<Question>();
		Question question;
		try {
			ps = db.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				question = new Question();
				question.setId(rs.getInt("blankid"));
				question.setSubjectId(rs.getInt("kcbianhao"));
				question.setQuestionTypeId(Integer.parseInt(QuestionType.BLANK));
				question.setKnowledgePoint(rs.getInt("Knowledgepoint"));
				question.setTitle(rs.getString("bfronttitle") + rs.getString("bbacktitle"));
				question.setInsertTime(rs.getString("addtime"));
				blanks.add(question);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();
		
		return blanks;
	}

	private List<Question> getAppointedMultiChoice(Map<String, String> paramsMap) {
		String sql = "select multiid, kcbianhao, Knowledgepoint, msubject, addtime from kaoshi_multi where";
		if(paramsMap.containsKey("subjectId")){
			String kcbianhao = paramsMap.get("subjectId");
			sql += " kcbianhao = " + kcbianhao + " and ";
		}
		if(paramsMap.containsKey("knowledgePointId")){
			String Knowledgepoint = paramsMap.get("knowledgePointId");
			sql += " Knowledgepoint = " + Knowledgepoint + " and ";
		}
		if(paramsMap.containsKey("keyword")){
			String msubject = paramsMap.get("keyword");
			sql += " msubject like '%" + msubject + "%' and ";
		}
		sql = sql.substring(0, sql.lastIndexOf("and"));
		System.out.println(sql);
		DataBase db = new DataBase();
		PreparedStatement ps;
		ResultSet rs;
		List<Question> multis = new ArrayList<Question>();
		Question question;
		try {
			ps = db.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				question = new Question();
				question.setId(rs.getInt("multiid"));
				question.setSubjectId(rs.getInt("kcbianhao"));
				question.setQuestionTypeId(Integer.parseInt(QuestionType.MULTI_CHOICE));
				question.setKnowledgePoint(rs.getInt("Knowledgepoint"));
				question.setTitle(rs.getString("msubject"));
				question.setInsertTime(rs.getString("addtime"));
				multis.add(question);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();
		
		return multis;
	}

	private List<Question> getAppointedSingleChoice(Map<String, String> paramsMap) {
		String sql = "select singleid, kcbianhao, Knowledgepoint, ssubject, addtime from kaoshi_single where";
		if(paramsMap.containsKey("subjectId")){
			String kcbianhao = paramsMap.get("subjectId");
			sql += " kcbianhao = " + kcbianhao + " and ";
		}
		if(paramsMap.containsKey("knowledgePointId")){
			String Knowledgepoint = paramsMap.get("knowledgePointId");
			sql += " Knowledgepoint = " + Knowledgepoint + " and ";
		}
		if(paramsMap.containsKey("keyword")){
			String ssubject = paramsMap.get("keyword");
			sql += " ssubject like '%" + ssubject + "%' and ";
		}
		sql = sql.substring(0, sql.lastIndexOf("and"));
		System.out.println(sql);
		DataBase db = new DataBase();
		PreparedStatement ps;
		ResultSet rs;
		List<Question> singles = new ArrayList<Question>();
		Question question;
		try {
			ps = db.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				question = new Question();
				question.setId(rs.getInt("singleid"));
				question.setSubjectId(rs.getInt("kcbianhao"));
				question.setQuestionTypeId(Integer.parseInt(QuestionType.SINGLE_CHOICE));
				question.setKnowledgePoint(rs.getInt("Knowledgepoint"));
				question.setTitle(rs.getString("ssubject"));
				question.setInsertTime(rs.getString("addtime"));
				singles.add(question);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.closeConnection();
		
		return singles;
	}

	@Override
	public boolean deleteTitleById(Map<String, String> paramsMap) {
		boolean isSuccess = false;
		if(paramsMap!=null 
				&& paramsMap.containsKey("questionTypeId") 
				&& paramsMap.containsKey("id")){
			String id = paramsMap.get("id");
			switch(paramsMap.get("questionTypeId")){
				case QuestionType.SINGLE_CHOICE:isSuccess = deleteSingleById(id);break;
				case QuestionType.MULTI_CHOICE:isSuccess = deleteMultiById(id);break;
				case QuestionType.BLANK:isSuccess = deleteBlankById(id);break;
				case QuestionType.JUDGEMENT:isSuccess = deleteJudgementById(id);break;
				default:break;
			}
		}
		return isSuccess;
	}

	private boolean deleteJudgementById(String id) {
		String sql = "delete from kaoshi_judgement where judgementid = " + id;
		DataBaseUtil db = new DataBaseUtil();
		int effectRow = db.update(sql, null);
		db.closeConnection();
		return effectRow>0;
		
		
	}

	private boolean deleteBlankById(String id) {
		String sql = "delete from kaoshi_blank where blankid = " + id;
		DataBaseUtil db = new DataBaseUtil();
		int effectRow = db.update(sql, null);
		db.closeConnection();
		return effectRow>0;
	}

	private boolean deleteMultiById(String id) {
		String sql = "delete from kaoshi_multi where multiid = " + id;
		DataBaseUtil db = new DataBaseUtil();
		int effectRow = db.update(sql, null);
		db.closeConnection();
		return effectRow>0;
	}

	private boolean deleteSingleById(String id) {
		String sql = "delete from kaoshi_single where singleid = " + id;
		DataBaseUtil db = new DataBaseUtil();
		int effectRow = db.update(sql, null);
		db.closeConnection();
		return effectRow>0;
	}

	@Override
	public boolean updateSingleChoice(Map<String, String> paramMap) {
		String singleid = paramMap.get("singleid");
		String kcbianhao = paramMap.get("kcbianhao");
		String Knowledgepoint = paramMap.get("Knowledgepoint");
		String ssubject = paramMap.get("ssubject");
		String soptionA = paramMap.get("soptionA");
		String soptionB = paramMap.get("soptionB");
		String soptionC = paramMap.get("soptionC");
		String soptionD = paramMap.get("soptionD");
		String sanswer = paramMap.get("sanswer");
		String smark = paramMap.get("smark");
		String difficulty = paramMap.get("difficulty");
		String addtime = new Date().toLocaleString();
		String sql = "update kaoshi_single set kcbianhao = ?, Knowledgepoint = ?, ssubject = ?, soptionA = ?, soptionB = ?, soptionC = ?, soptionD = ?, sanswer = ?, smark = ?, difficulty = ?, addtime = ? where singleid = ?";
		System.out.println(sql);
		Object[] params = {kcbianhao, Knowledgepoint, ssubject, soptionA, soptionB, soptionC, soptionD, sanswer, smark, difficulty, addtime, singleid};
		DataBaseUtil db = new DataBaseUtil();
		int effectRow = db.update(sql, params);
		
		return effectRow>0;
	}

	@Override
	public boolean updateMultiChoice(Map<String, String> paramMap) {
		String multiid = paramMap.get("multiid");
		String kcbianhao = paramMap.get("kcbianhao");
		String Knowledgepoint = paramMap.get("Knowledgepoint");
		String msubject = paramMap.get("msubject");
		String moptionA = paramMap.get("moptionA");
		String moptionB = paramMap.get("moptionB");
		String moptionC = paramMap.get("moptionC");
		String moptionD = paramMap.get("moptionD");
		String manswer = paramMap.get("manswer");
		String mmark = paramMap.get("mmark");
		String difficulty = paramMap.get("difficulty");
		String addtime = new Date().toLocaleString();
		String sql = "update kaoshi_multi set kcbianhao = ?, Knowledgepoint = ?, msubject = ?, moptionA = ?, moptionB = ?, moptionC = ?, moptionD = ?, manswer = ?, mmark = ?, difficulty = ?, addtime = ? where multiid = ?";
		System.out.println(sql);
		Object[] params = {kcbianhao, Knowledgepoint, msubject, moptionA, moptionB, moptionC, moptionD, manswer, mmark, difficulty, addtime, multiid};
		DataBaseUtil db = new DataBaseUtil();
		int effectRow = db.update(sql, params);
		
		return effectRow>0;
	}

	@Override
	public boolean updateBlank(Map<String, String> paramMap) {
		String blankid = paramMap.get("blankid");
		String kcbianhao = paramMap.get("kcbianhao");
		String Knowledgepoint = paramMap.get("Knowledgepoint");
		String bfronttitle = paramMap.get("bfronttitle");
		String banswer = paramMap.get("banswer");
		String bmark = paramMap.get("bmark");
		String difficulty = paramMap.get("difficulty");
		String addtime = new Date().toLocaleString();
		String sql = "update kaoshi_blank set kcbianhao = ?, Knowledgepoint = ?, bfronttitle = ?, banswer = ?, bmark = ?, difficulty = ?, addtime = ? where blankid = ?";
		System.out.println(sql);
		Object[] params = {kcbianhao, Knowledgepoint, bfronttitle, banswer, bmark, difficulty, addtime, blankid};
		DataBaseUtil db = new DataBaseUtil();
		int effectRow = db.update(sql, params);
		
		return effectRow>0;
	}

	@Override
	public boolean updateJudegement(Map<String, String> paramMap) {
		String judgementid = paramMap.get("judgementid");
		String kcbianhao = paramMap.get("kcbianhao");
		String Knowledgepoint = paramMap.get("Knowledgepoint");
		String jtitle = paramMap.get("jtitle");
		String janswer = paramMap.get("janswer");
		String jmark = paramMap.get("jmark");
		String difficulty = paramMap.get("difficulty");
		String addtime = new Date().toLocaleString();
		String sql = "update kaoshi_judgement set kcbianhao = ?, Knowledgepoint = ?, jtitle = ?, janswer = ?, jmark = ?, difficulty = ?, addtime = ? where judgementid = ?";
		System.out.println(sql);
		Object[] params = {kcbianhao, Knowledgepoint, jtitle, janswer, jmark, difficulty, addtime, judgementid};
		DataBaseUtil db = new DataBaseUtil();
		int effectRow = db.update(sql, params);
		
		return effectRow>0;
	}

	@Override
	public boolean updateShortAnswer(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateComprehensive(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteTitleByKnowledgePointId(String id) {
		deleteSingleByKnowledgePointId(id);
		deleteMultiByKnowledgePointId(id);
		deleteBlankByKnowledgePointId(id);
		deleteJudgementByKnowledgePointId(id);
		return true;
	}

	private boolean deleteJudgementByKnowledgePointId(String id) {
		String sql = "delete from kaoshi_judgement where Knowledgepoint = ?";
		Object[] params = {id};
		DataBaseUtil db =  new DataBaseUtil();
		int effectRow = db.update(sql, params);
		return effectRow>0;
		
	}

	private boolean deleteBlankByKnowledgePointId(String id) {
		String sql = "delete from kaoshi_blank where Knowledgepoint = ?";
		Object[] params = {id};
		DataBaseUtil db =  new DataBaseUtil();
		int effectRow = db.update(sql, params);
		return effectRow>0;
		
	}

	private boolean deleteMultiByKnowledgePointId(String id) {
		String sql = "delete from kaoshi_multi where Knowledgepoint = ?";
		Object[] params = {id};
		DataBaseUtil db =  new DataBaseUtil();
		int effectRow = db.update(sql, params);
		return effectRow>0;
	}

	private boolean deleteSingleByKnowledgePointId(String id) {
		String sql = "delete from kaoshi_single where Knowledgepoint = ?";
		Object[] params = {id};
		DataBaseUtil db =  new DataBaseUtil();
		int effectRow = db.update(sql, params);
		return effectRow>0;
	}

	@Override
	public boolean deleteKnowledgePointById(String id) {
		String sql = "delete from knowledge_point where id = ?";
		Object[] params = {id};
		DataBaseUtil db = new DataBaseUtil();
		int effectRow = db.update(sql, params);
		return effectRow == 1;
	}

	@Override
	public Question findQuestionByQuestionTypeAndId(Map<String, String> paramsMap) {
		Question question = new Question();
		if(paramsMap.containsKey("questionTypeId")){
			String questionTypeId = paramsMap.get("questionTypeId");
			String id = paramsMap.get("id");
			switch(questionTypeId){
				case QuestionType.SINGLE_CHOICE:question = getSingleById(id);break;
				case QuestionType.MULTI_CHOICE:question = getMultiById(id);break;
				case QuestionType.BLANK:question = getBlankById(id);break;
				case QuestionType.JUDGEMENT:question = getJudgementById(id);break;
				default:System.out.println("default");break;
			}
		}
		return question;
	}

	private Question getJudgementById(String id) {
		String sql = "select judgementid, jtitle, janswer, jmark, kcbianhao, difficulty, Knowledgepoint from kaoshi_judgement where judgementid =" + id;
		System.out.println(sql);
		DataBase db = new DataBase();
		PreparedStatement ps;
		ResultSet rs;
		Question question = null;
		try {
			ps = db.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				question = new Question();
				question.setId(rs.getInt("judgementid"));
				question.setTitle(rs.getString("jtitle"));
				question.setAnswer(rs.getString("janswer"));
				question.setScore(rs.getInt("jmark"));
				question.setSubjectId(rs.getInt("kcbianhao"));
				question.setDifficulty(rs.getDouble("difficulty"));
				question.setKnowledgePointId(rs.getInt("Knowledgepoint"));
				question.setQuestionTypeId(Integer.parseInt(QuestionType.JUDGEMENT));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.closeConnection();
		return question;
	}

	private Question getBlankById(String id) {
		String sql = "select blankid, bfronttitle, bbacktitle, banswer, bmark, kcbianhao, difficulty, Knowledgepoint from kaoshi_blank where blankid =" + id;
		System.out.println(sql);
		DataBase db = new DataBase();
		PreparedStatement ps;
		ResultSet rs;
		Question question = null;
		try {
			ps = db.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				question = new Question();
				question.setId(rs.getInt("blankid"));
				question.setTitle(rs.getString("bfronttitle") + rs.getString("bbacktitle"));
				question.setAnswer(rs.getString("banswer"));
				question.setScore(rs.getInt("bmark"));
				question.setSubjectId(rs.getInt("kcbianhao"));
				question.setDifficulty(rs.getDouble("difficulty"));
				question.setKnowledgePointId(rs.getInt("Knowledgepoint"));
				question.setQuestionTypeId(Integer.parseInt(QuestionType.BLANK));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.closeConnection();
		return question;
	}

	private Question getMultiById(String id) {
		String sql = "select multiid, msubject, moptionA, moptionB, moptionC, moptionD, manswer, mmark, kcbianhao, difficulty, Knowledgepoint from kaoshi_multi where multiid =" + id;
		System.out.println(sql);
		DataBase db = new DataBase();
		PreparedStatement ps;
		ResultSet rs;
		Question question = null;
		try {
			ps = db.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				question = new Question();
				question.setId(rs.getInt("multiid"));
				question.setTitle(rs.getString("msubject"));
				question.setOptionA(rs.getString("moptionA"));
				question.setOptionB(rs.getString("moptionB"));
				question.setOptionC(rs.getString("moptionC"));
				question.setOptionD(rs.getString("moptionD"));
				question.setAnswer(rs.getString("manswer"));
				question.setScore(rs.getInt("mmark"));
				question.setSubjectId(rs.getInt("kcbianhao"));
				question.setDifficulty(rs.getDouble("difficulty"));
				question.setKnowledgePointId(rs.getInt("Knowledgepoint"));
				question.setQuestionTypeId(Integer.parseInt(QuestionType.MULTI_CHOICE));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.closeConnection();
		return question;
	}

	private Question getSingleById(String id) {
		String sql = "select singleid, ssubject, soptionA, soptionB, soptionC, soptionD, sanswer, smark, kcbianhao, difficulty, Knowledgepoint from kaoshi_single where singleid =" + id;
		System.out.println(sql);
		DataBase db = new DataBase();
		PreparedStatement ps;
		ResultSet rs;
		Question question = null;
		try {
			ps = db.getConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				question = new Question();
				question.setId(rs.getInt("singleid"));
				question.setTitle(rs.getString("ssubject"));
				question.setOptionA(rs.getString("soptionA"));
				question.setOptionB(rs.getString("soptionB"));
				question.setOptionC(rs.getString("soptionC"));
				question.setOptionD(rs.getString("soptionD"));
				question.setAnswer(rs.getString("sanswer"));
				question.setScore(rs.getInt("smark"));
				question.setSubjectId(rs.getInt("kcbianhao"));
				question.setDifficulty(rs.getDouble("difficulty"));
				question.setKnowledgePointId(rs.getInt("Knowledgepoint"));
				question.setQuestionTypeId(Integer.parseInt(QuestionType.SINGLE_CHOICE));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		db.closeConnection();
		return question;
	}

	@Override
	public boolean updateKnowledgePointById(Map<String, String> paramMap) {
		DataBaseUtil db = new DataBaseUtil();
		String id = paramMap.get("id");
		String name = paramMap.get("name");
		String sql = "update knowledge_point set name = ? where id = ?";
		Object[] params = {name, id};
		int effectRow = db.update(sql, params);
		return effectRow>0;
	}

	@Override
	public boolean insertKnowledgePoint(Map<String, String> paramMap) {
		DataBaseUtil db = new DataBaseUtil();
		String name = paramMap.get("name");
		String subjectId = paramMap.get("subjectId");
		String sql = "insert into knowledge_point(subjectId, name) values(?, ?)";
		Object[] params = {subjectId, name};
		int effectRow = db.update(sql, params);
		return effectRow>0;
	}

	@Override
	public boolean addSubject(String subjectName) {
		DataBaseUtil db = new DataBaseUtil();
		String sql = "insert into subject(name) values(?)";
		Object[] params = {subjectName};
		int effectRow = db.update(sql, params);
		return effectRow>0;
	}

	@Override
	public boolean deleteSubjectById(String id) {
		DataBaseUtil db = new DataBaseUtil();
		String sql = "delete from subject where id = ?";
		Object[] params = {id};
		int effectRow = db.update(sql, params);
		return effectRow>0;
	}

	

	
	

}
