package test;

import java.util.ArrayList;
import java.util.stream.IntStream; 

	public class Population {
		
		private ArrayList<Route> routes = new ArrayList<Route>(GeneticAlgorithm.POPULATION_SIZE); 
		
		public Population(int populationSize, ArrayList<node> cities) {
		IntStream.range(0, populationSize).forEach(x -> routes.add(new Route(cities)));
		}
		
		public Population(int populationSize, GeneticAlgorithm geneticAlgorithm) {
			IntStream.range(0, populationSize).forEach(
					x -> routes.add(new Route (geneticAlgorithm.getInitialRoute())));
		}
		
		public ArrayList<Route> getRoutes() { return routes;} 
		
		public void sortRoutesByFitness() { 
			routes.sort((routel, route2) -> {
			int flag = 0; 
			if (routel.getFitness() > route2.getFitness()) 
				flag = -1; 
			else if (routel.getFitness() < route2.getFitness()) 
				flag = 1; 
			return flag;
			});
		}
}
