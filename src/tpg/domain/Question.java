package tpg.domain;

public class Question {
	private int id;
	private int subjectId;
	private int knowledgePointId;
	private int questionTypeId;
	private String title;
	private String answer;
	private String imageAnswer;
	private double difficulty;
	private String insertTime;
	private String pictures;
	
	private String questionType;
	private String subject;
	private String knowledgePoint;
	
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	
	private int score;
	
	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public Question() { }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
		this.setSubject(subjectId);
	}

	public int getKnowledgePointId() {
		return knowledgePointId;
	}

	public void setKnowledgePointId(int knowledgePointId) {
		this.knowledgePointId = knowledgePointId;
		this.knowledgePoint = KnowledgePoint.getKnowledgePointById(knowledgePointId);
	}

	public int getQuestionTypeId() {
		return questionTypeId;
	}

	public void setQuestionTypeId(int questionTypeId) {
		this.questionTypeId = questionTypeId;
		setQuestionType(questionTypeId);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public double getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(double difficulty) {
		this.difficulty = difficulty;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

	public void setQuestionType(int questionTypeId) {
		this.questionType = QuestionType.numToChineseString(questionTypeId);
	}
	
	public String getQuestionType() {
		return questionType;
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(int subjectId) {
		this.subject = Subject.getSubjectById(subjectId);
	}
	
	


	@Override
	public String toString() {
		return "Question [id=" + id + ", subjectId=" + subjectId + ", knowledgePointId=" + knowledgePointId
				+ ", questionTypeId=" + questionTypeId + ", title=" + title + ", answer=" + answer + ", difficulty="
				+ difficulty + ", insertTime=" + insertTime + ", pictures=" + pictures + ", questionType="
				+ questionType + ", subject=" + subject + ", knowledgePoint=" + knowledgePoint + "]";
	}

	public String getKnowledgePoint() {
		return knowledgePoint;
	}

	public void setKnowledgePoint(int knowledgePointId) {
		this.knowledgePoint = KnowledgePoint.getKnowledgePointById(knowledgePointId);
	}

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public String getImageAnswer() {
		return imageAnswer;
	}

	public void setImageAnswer(String imageAnswer) {
		this.imageAnswer = imageAnswer;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
