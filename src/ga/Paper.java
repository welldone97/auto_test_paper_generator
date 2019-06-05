package ga;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Paper {
	private int id;
	private int totalScore;
	private List<Integer> knowledgePoints;
	private double difficulty;
	private Map<Integer,Integer> typeCountMapping;
	private Map<Integer,Integer> typeScoreMapping;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotalScore() {
		int totalScore = 0;
		Set<Integer> keySet= typeScoreMapping.keySet();
		for(Integer key:keySet){
			totalScore += typeScoreMapping.get(key);
		}
		return totalScore;
	}
	
	public double getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(double difficulty) {
		this.difficulty = difficulty;
	}
	public Map<Integer, Integer> getTypeCountMapping() {
		return typeCountMapping;
	}
	public void setTypeCountMapping(Map<Integer, Integer> typeCountMapping) {
		this.typeCountMapping = typeCountMapping;
	}
	
	public int getQuestionCount() {
		int questionCount = 0;
		Set<Integer> types = typeCountMapping.keySet();
		for(Integer type : types) {
			questionCount += typeCountMapping.get(type);
		}
		return questionCount;
	}
	public List<Integer> getKnowledgePoints() {
		return knowledgePoints;
	}
	public void setKnowledgePoints(List<Integer> knowledgePoints) {
		this.knowledgePoints = knowledgePoints;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("-------试卷-------");
		sb.append("\n总分：" + this.getTotalScore());
		sb.append("\n֪知识点");
		for(Integer kp : knowledgePoints) {
			sb.append(kp);
			sb.append(" ");
		}
		sb.append("\n难度：" + difficulty);
		sb.append("\n题型");
		Set<Integer> keys = typeCountMapping.keySet();
		for(Integer key : keys) {
			sb.append(key);
			sb.append(":");
			sb.append(typeCountMapping.get(key));
			sb.append("   ");
		}
		return sb.toString(); 
	}
	public Map<Integer,Integer> getTypeScoreMapping() {
		return typeScoreMapping;
	}
	public void setTypeScoreMapping(Map<Integer,Integer> typeScoreMapping) {
		this.typeScoreMapping = typeScoreMapping;
	}
}
