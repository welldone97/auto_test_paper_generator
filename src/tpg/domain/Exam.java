package tpg.domain;

import java.util.List;

public class Exam {
	private String examid;
	private String kcbianhao;
	private String ename;
	private String examMethod;
	private String duration;
	private String singlenum;
	private String multinum;
	private String blanknum;
	private String judgementnum;
	private String score;
	private String addtime;
	private String ga_run_info;
	private ExamQuestions questions;
	private String difficulty;
	private List<KnowledgePoint> knowledgePoints;
	private String courseName;
	
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getExamMethod() {
		return examMethod;
	}
	public void setExamMethod(String examMethod) {
		this.examMethod = examMethod;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getSinglenum() {
		return singlenum;
	}
	public void setSinglenum(String singlenum) {
		this.singlenum = singlenum;
	}
	public String getMultinum() {
		return multinum;
	}
	public void setMultinum(String multinum) {
		this.multinum = multinum;
	}
	public String getBlanknum() {
		return blanknum;
	}
	public void setBlanknum(String blanknum) {
		this.blanknum = blanknum;
	}
	public String getJudgementnum() {
		return judgementnum;
	}
	public void setJudgementnum(String judgementnum) {
		this.judgementnum = judgementnum;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getGa_run_info() {
		return ga_run_info;
	}
	public void setGa_run_info(String ga_run_info) {
		this.ga_run_info = ga_run_info;
	}
	public ExamQuestions getQuestions() {
		return questions;
	}
	public void setQuestions(ExamQuestions questions) {
		this.questions = questions;
	}
	public String getKcbianhao() {
		return kcbianhao;
	}
	public void setKcbianhao(String kcbianhao) {
		this.kcbianhao = kcbianhao;
	}
	public String getExamid() {
		return examid;
	}
	public void setExamid(String examid) {
		this.examid = examid;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public List<KnowledgePoint> getKnowledgePoints() {
		return knowledgePoints;
	}
	public void setKnowledgePoints(List<KnowledgePoint> knowledgePoints) {
		this.knowledgePoints = knowledgePoints;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	
}
