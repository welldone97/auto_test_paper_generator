package ga;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.security.pkcs11.Secmod.DbMode;
import tpg.common.DataBase;
import tpg.dao.QuestionDao;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.01, 0.95, 2, 20);
		
		Paper userWantPaper = getUserWantPaper();
		Thread.sleep(1000);
		List<Question> qList = getQuestionList(userWantPaper.getKnowledgePoints());
		Population population = ga.initPopulation(100, userWantPaper, qList);
		
		ga.evalPopulation(population, userWantPaper);
		
		int generation = 1;
		while(ga.isTerminationConditionMet(population) == false && generation < 9999) {
			
			System.out.println(generation + "代 " +"Best solution: " + population.getFittest(0).toString());
//
			population = ga.crossoverPopulation(population);
//
			population = ga.mutatePopulation(population);
//
			ga.evalPopulation(population, userWantPaper);
//
			generation++;
		}
		System.out.println("Found solution in " + generation + " generations");
		System.out.println("Best solution: " + population.getFittest(0).toString());
		
		Thread.sleep(5000);
		List<Question> questions = population.getFittest(0).getQuestions();
		System.out.println("----------------------------------------------");
		for(Question q:questions) {
			System.out.println(q);
		}
	}

	private static List<Question> getQuestionList(List<Integer> kps) {
		QuestionDao qd = new QuestionDao();
		return qd.getQuestionsByKnowledgePoints(kps);
	}

	private static Paper getUserWantPaper() {
		double difficulty = 0.5;
		List<Integer> userWantKps = new ArrayList<Integer>();
		for(int i=0;i<40;i++) {
			userWantKps.add(i);
		}
		int totalScore = 100;
		Map<Integer, Integer> typeCountMapping = new HashMap<Integer, Integer>();
		typeCountMapping.put(1, 10);
		typeCountMapping.put(2, 10);
		typeCountMapping.put(3, 10);
		typeCountMapping.put(4, 10);
		
		Map<Integer,Integer> typeScoreMapping = new HashMap<Integer, Integer>();
		typeScoreMapping.put(1, 30);
		typeScoreMapping.put(2, 30);
		typeScoreMapping.put(3, 20);
		typeScoreMapping.put(4, 20);
		
		Paper paper = new Paper();
		paper.setDifficulty(difficulty);
		paper.setKnowledgePoints(userWantKps);
		paper.setTypeCountMapping(typeCountMapping);
		paper.setTypeScoreMapping(typeScoreMapping);
		paper.setId(1);
		System.out.println(paper.toString());
		return paper;
	}
}
