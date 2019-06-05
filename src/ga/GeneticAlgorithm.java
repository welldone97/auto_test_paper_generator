  package ga;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GeneticAlgorithm {
	private int populationSize;
	private double mutationRate;
	private double crossoverRate;
	private int elitismCount;
	private int tournamentSize;
	
	private List<Question> db;
	
	public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount, int tournamentSize) {
		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		this.crossoverRate = crossoverRate;
		this.elitismCount = elitismCount;
		this.tournamentSize = tournamentSize;
	}
	
	/**
	 * @param populationSize 
	 * @param paper	
	 * @param qList 
	 * @return
	 */
	public Population initPopulation(int populationSize, Paper paper, List<Question> qList) {
		this.db = qList;
		Population population = new Population(populationSize);
		Map<Integer, Integer> typeCountMapping = paper.getTypeCountMapping();
		
		Individual individual;
		Random rand = new Random();
		for(int i=0; i<populationSize; i++) {
			individual = new Individual();
			individual.setId(i+1);
			individual.setTotalScore(0);
			while(paper.getTotalScore()!=individual.getTotalScore()) {
				individual.setQuestions(new ArrayList<Question>());
				for(Integer type : typeCountMapping.keySet()) {
					int count = typeCountMapping.get(type);
					List<Question> oneTypeQuestion = this.getAppointedQuestionList(qList, type, paper.getKnowledgePoints());
					int totalScore = 0;
					List<Question> oneTypeQuestionList = null;
					do {
						oneTypeQuestionList = new ArrayList<Question>();
						totalScore = 0;
						Question temp = new Question();
						for(int k=0; k<count; k++) {
							int otpCount = oneTypeQuestion.size();

							int index = rand.nextInt(otpCount-k);
							Question problemAtIndex = oneTypeQuestion.get(index);
//							List<Question> questions = individual.getQuestions();
//							questions.add(problemAtIndex);
//							individual.setQuestions(questions);
							oneTypeQuestionList.add(problemAtIndex);
							totalScore+=problemAtIndex.getScore();

							temp = oneTypeQuestion.get(otpCount-1-k);	
							oneTypeQuestion.set(otpCount-1-k, problemAtIndex);
							oneTypeQuestion.set(index, temp);
						}
					}while(totalScore!=paper.getTypeScoreMapping().get(type));
					List<Question> questions = individual.getQuestions();
					questions.addAll(oneTypeQuestionList);
					individual.setQuestions(questions);
				}
			}
			population.setInividual(i, individual);
		}
		
		return population;
	}

	/** 
	 * @param qList
	 * @param type
	 * @param list
	 * @return
	 */
	private List<Question> getAppointedQuestionList(List<Question> qList, Integer type, List<Integer> knowledgePoints) {
		List<Question> subList = new ArrayList<Question>();
		for(Question q:qList) {
			if(q.getType() == type && knowledgePoints.containsAll(q.getKnowledgePoints())) {
				subList.add(q);
			}
		}
		return subList;
	}
	
	
	public void evalPopulation(Population population, Paper paper) {
		double populationFitness = 0.00;
		for(Individual individual : population.getIndividuals()) {
			populationFitness += calcFitness(individual,paper);
		}
		population.setPopulationFitness(populationFitness);
	}

	private double calcFitness(Individual individual, Paper paper) {
		double userWantDiff = paper.getDifficulty();
		double individualDiff = individual.getDifficulty();
		double kpCoverage = 0.00;
		kpCoverage = calcKpCoverage(individual, paper);
		
		double diffWeight = 0.5;
		double kpWeight = 0.5;
		double fitness = 1 
				- diffWeight * Math.abs(userWantDiff-individualDiff)
				- kpWeight * (1 - kpCoverage);
		individual.setKpCoverage(kpCoverage);
		individual.setFitness(fitness);
		return fitness;
	}

	private double calcKpCoverage(Individual individual, Paper paper) {
		double kpCoverage = 0.00;
		List<Integer> intersectKps = new ArrayList<Integer>();
		List<Integer> userWantKps = paper.getKnowledgePoints();
		List<Question> individualQuestions = individual.getQuestions();
		Set<Integer> individualKps = new HashSet<Integer>();
		
		for(Question q: individualQuestions) {
			individualKps.addAll(q.getKnowledgePoints());
		}
		
		for(Integer kp: individualKps) {
			if(userWantKps.contains(kp)) {
				intersectKps.add(kp);
			}
		}

		kpCoverage = (double)intersectKps.size()/(double)userWantKps.size();
		return kpCoverage;
	}

	public boolean isTerminationConditionMet(Population population) {
		if(population.getFittest(0).getFitness() > 0.98) {
			return true;
		}
		return false;
	}

	public Population crossoverPopulation(Population population) throws InterruptedException {
		Population newPopulation = new Population(population.size());
		for(int populationIndex=0; populationIndex<population.size(); populationIndex++) {
			Individual parent1 = population.getFittest(populationIndex);
			if(Math.random() < crossoverRate && populationIndex > elitismCount) {
				Individual offspring = population.getFittest(populationIndex);
				Individual parent2 = this.selectParent(population);
				
				for(int geneIndex=0; geneIndex<offspring.getQuestionCount(); geneIndex++) {
					if(Math.random()>0.5 
							&& parent1.getGene(geneIndex).getScore()==parent2.getGene(geneIndex).getScore()
							&& !offspring.getQuestions().contains(parent2.getGene(geneIndex))) {
						offspring.setGene(geneIndex, parent2.getGene(geneIndex));
					}
				}
				newPopulation.setInividual(populationIndex, offspring);
			}else {
				newPopulation.setInividual(populationIndex, parent1);
			}
		}
		
		return newPopulation;
	}

	private Individual selectParent(Population population) {
		Population tournament = new Population(tournamentSize);
		population.shuffle();
		for(int index=0; index<tournamentSize; index++) {
			Individual individual = population.getIndividual(index);
			tournament.setInividual(index, individual);
		}
		return tournament.getFittest(0);
	}

	public Population mutatePopulation(Population population) {
		Population newPopulation = new Population(population.size());
		for(int populationIndex=0; populationIndex<population.size(); populationIndex++) {
			Individual mutateIndividual = population.getFittest(populationIndex);
			
			double adaptiveMutationRate = this.mutationRate;
			if(mutateIndividual.getFitness() > population.getPopulationFitness()/population.size()) {
				double fitness1 = population.getFittest(0).getFitness() - mutateIndividual.getFitness();
				double fitness2 = population.getFittest(0).getFitness() - population.getPopulationFitness()/population.size();
				adaptiveMutationRate = (fitness1/fitness2) * this.mutationRate;
			}
			
			for(int geneIndex=0; geneIndex<mutateIndividual.getQuestionCount(); geneIndex++) {
				if(Math.random() < adaptiveMutationRate && populationIndex > elitismCount) {
		
					Question mutateGene = mutateIndividual.getGene(geneIndex);
					int mutateScore = mutateGene.getScore();
					int type = mutateGene.getType();
					List<Integer> kps = mutateGene.getKnowledgePoints();
					List<Question> appointedList = getAppointedQuestionList(db, type, kps, mutateScore);
					Question newQuestion = appointedList.get(new Random().nextInt(appointedList.size()));
					mutateIndividual.setGene(geneIndex, newQuestion);
				}
			}
			newPopulation.setInividual(populationIndex, mutateIndividual);
		}
		return newPopulation;
	}

	private List<Question> getAppointedQuestionList(List<Question> qList, int type, List<Integer> kps, int mutateScore) {
		List<Question> subList = new ArrayList<Question>();
		for(Question q:qList) {
			if(q.getType() == type 
					&& kps.containsAll(q.getKnowledgePoints())
					&& q.getScore()==mutateScore) {
				subList.add(q);
			}
		}
		return subList;
	}

	
	
	
}
