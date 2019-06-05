package ga;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;


public class Population {
	private Individual[] population;
	private double populationFitness = 0.00;
	
	public Population(int size) {
		this.populationFitness = 0.00;
		this.population = new Individual[size];
	}
	
	/**获得种群的所有个体
	 * @return 返回种群所有个体的数组
	 */
	public Individual[] getIndividuals() {
		return this.population;
	}
	
	/**获得种群适应度
	 * @return 返回种群适应度
	 */
	public double getPopulationFitness() {
		return populationFitness;
	}
	
	/**设置种群适应度
	 * @param populationFitness 传入种群适应度
	 */
	public void setPopulationFitness(double populationFitness) {
		this.populationFitness = populationFitness;
	}
	
	/**获得种群大小
	 * @return 返回种群大小
	 */
	public int size() {
		return this.population.length;
	}
	
	/**该方法先将种群按适应度排序（适应度高的排在数组的前面，即下标为0的个体适应度最高），然后返回第offset个最合适的个体
	 * @param offset 位移（偏移量）
	 * @return 第offset个最合适的个体
	 */
	public Individual getFittest(int offset) {
		Arrays.sort(this.population, new Comparator<Individual>() {

			@Override
			public int compare(Individual o1, Individual o2) {
				if(o1.getFitness() > o2.getFitness()) {
					return -1;
				}else if(o1.getFitness() < o2.getFitness()) {
					return 1;
				}
				return 0;
			}
		});
		return this.population[offset];
	}
	
	/**将种群中的offset位置的个体设置成指定的个体individual
	 * @param offset 指定位置
	 * @param individual 指定个体
	 */
	public void setInividual(int offset, Individual individual) {
		this.population[offset] = individual;
	}
	
	public Individual getIndividual(int index) {
		return population[index];
	}
	
	/**
	 * 打乱种群中各个体的顺序
	 */
	public void shuffle(){
		Random rand = new Random();
		for(int i=this.population.length-1; i>0; i--) {
			int index = rand.nextInt(i+1);
			 Individual temp = population[i];
			 population[i] = population[index];
			 population[index] = temp;
		}
	}
	
	
}
