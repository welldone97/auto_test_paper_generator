package ga;

import java.util.List;

import tpg.dao.QuestionDao;

public class PaperMaker {
	public static Individual make(Paper userWantPaper) {
		GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.01, 0.95, 2, 20);
		
		List<Question> qList = getQuestionList(userWantPaper.getKnowledgePoints());
		Population population = ga.initPopulation(100, userWantPaper, qList);
		
		ga.evalPopulation(population, userWantPaper);
		
		int generation = 1;
		while(ga.isTerminationConditionMet(population) == false && generation < 9999) {
			System.out.println(generation + "代 " +"Best solution: " + population.getFittest(0).toString());
			try {
				population = ga.crossoverPopulation(population);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			population = ga.mutatePopulation(population);
			ga.evalPopulation(population, userWantPaper);
			generation++;
		}
		System.out.println("Found solution in " + generation + " generations");
		System.out.println("Best solution: " + population.getFittest(0).toString());
		
		List<Question> questions = population.getFittest(0).getQuestions();
		System.out.println("----------------------------------------------");
		for(Question q:questions) {
			System.out.println(q);
		}
		return population.getFittest(0);
	}
	
	private static List<Question> getQuestionList(List<Integer> kps) {
		QuestionDao qd = new QuestionDao();
		return qd.getQuestionsByKnowledgePoints(kps);
	}
}
