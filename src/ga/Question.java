package ga;

import java.util.List;

public class Question {
	private String id;
	private int type;
	private int score;
	private double difficulty;
	private List<Integer> knowledgePoints;
	
	public Question(String id, int type, int score, double diffiulty, List<Integer> knowledgePoints) {
		super();
		this.id = id;
		this.type = type;
		this.score = score;
		this.difficulty = diffiulty;
		this.setKnowledgePoints(knowledgePoints);
	}
	
	public Question() { }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public double getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(double diffiulty) {
		this.difficulty = diffiulty;
	}

	public List<Integer> getKnowledgePoints() {
		return knowledgePoints;
	}

	public void setKnowledgePoints(List<Integer> knowledgePoints) {
		this.knowledgePoints = knowledgePoints;
	}

	@Override
	public String toString() {
		return "id:" + id + "--" 
			+  "题型：" + type + "--" 
			 + "分数：" + score + "--" 
			 + "难度：" + difficulty + "--" 
 			 + "知识点：" + this.getKnowledgePoints().get(0);
	}
	
	
}
