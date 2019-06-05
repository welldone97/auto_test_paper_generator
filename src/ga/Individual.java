package ga;

import java.util.Iterator;
import java.util.List;

public class Individual {
	private int id;
	private double fitness;
	private double difficulty;
	private int questionCount;
	private int totalScore;
	private double kpCoverage;
	private List<Question> questions;


	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public double getDifficulty() {
		double diff = 0.00;
		for(Question q:questions) {
			diff += q.getDifficulty()*q.getScore();
		}
		return diff/this.getTotalScore();
	}

	public int getTotalScore() {
		int sum = 0;
		if(this.questions!=null) {
			for(Question q:questions) {
				sum += q.getScore();
			}
		}
		
		return sum;
	}
	
	public double getKpCoverage() {
		return kpCoverage;
	}
	
	public void setKpCoverage(double kpCoverage) {
		this.kpCoverage = kpCoverage;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public int getQuestionCount() {
		return this.questions.size();
	}
	
	public void setGene(int index, Question question) {
		this.getQuestions().set(index, question);
	}
	
	public Question getGene(int index) {
		return this.getQuestions().get(index);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
//		sb.append("--------------------------------------");
		sb.append("个体id:" + id 
				+ "难度：" + this.getDifficulty() 
				+ "适应度：" + fitness 
				+ "总分：" + this.getTotalScore() 
				+ "总题数：" + this.getQuestionCount() 
				+ "知识点覆盖度：" + kpCoverage
				+ getTypeScoreMapping());
//		sb.append("--------------------------------------");
		return sb.toString();
	}

	public void setTotalScore(int i) {
		this.totalScore = i;
	}
	
	public void setQuestion(int index, Question question){
		this.questions.set(index, question);
	}
	
	public Question getQuestion(int index) {
		return this.questions.get(index);
	}
	
	public String getTypeScoreMapping(){
		int score1 = 0;
		int score2 = 0;
		int score3 = 0;
		int score4 = 0;
		for(Question q:this.getQuestions()) {
			switch(q.getType()) {
			case 1:score1 += q.getScore();break;
			case 2:score2 += q.getScore();break;
			case 3:score3 += q.getScore();break;
			case 4:score4 += q.getScore();break;
			}
		}
		String result = "题型1分数：" + score1 + ", ";
		result += "题型2分数：" + score2 + ", ";
		result += "题型3分数：" + score3 + ", ";
		result += "题型4分数：" + score4 + ", ";
		return result;
	}
	
}
