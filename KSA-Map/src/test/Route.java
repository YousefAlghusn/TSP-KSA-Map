package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Route {
	private boolean fitnessChange = true;
	private double fitness = 0;
	private ArrayList<node> cities = new ArrayList<node>();
	
	
	
	public Route(ArrayList<node> cities) {
		this.cities. addAll(cities);
		Collections.shuffle(this.cities);
	}
	public Route (Route route){ route.cities.stream().forEach(x -> cities.add(x)); }
	
	public Route(GeneticAlgorithm ga) {
		ga.getInitialRoute().forEach(x->cities.add(null));
	}
	
	public ArrayList<node> getCities() { return cities; }
	
	public double getTotalDistance() {
		
		int citiesSize = this.cities.size();
		
		return this.cities.stream().mapToDouble(x -> {
			int cityIndex = this.cities.indexOf(x);
			double returnValue = 0;
			if (cityIndex < citiesSize - 1) 
				returnValue = x.getDistanceTo(this.cities.get(cityIndex + 1));
			return returnValue;
		}).sum() + this.cities.get(citiesSize - 1).getDistanceTo(this.cities.get(0));
	}
	public String getTotalStringDistance() {
		String returnValue = String.format("%.2f", this.getTotalDistance());
		return returnValue;
	}
	public String toString() { 
		String path ="[";
		for(node e: cities)
			path+=e.getName()+"→";
                path+=cities.get(0).getName()+"]";
             
		return path ;
		
	}
        public String getCitiesString() { 
		String path ="{";
		for(node e: cities)
			path+=e.getName()+",";
                path+="}";
             
		return path ;
		
	}
	
	public double getFitness() {
		if(fitnessChange == true) {
			fitness = (1/getTotalDistance())*5000;
			fitnessChange =false;
		}
		return fitness;
	}
	}

