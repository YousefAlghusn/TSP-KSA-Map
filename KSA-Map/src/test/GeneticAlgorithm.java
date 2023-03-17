package test;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class GeneticAlgorithm implements Runnable {

    public static final double MUTATION_RATE = 0.25;
    public static final int TOURNAMENT_SELECTION_SIZE = 3;
    public static final int POPULATION_SIZE = 10;
    public static final int NUMB_OF_ELITE_ROUTES = 1;
    public int NUMB_OF_GENERATIONS = 50;
    private ArrayList<node> initialRoute = null;

    App app;
    Route currentRoute;

    String finalRoute = "";
    String details = "Details: \n\n";

    public GeneticAlgorithm(ArrayList<node> initialRoute, App app) {
        this.initialRoute = initialRoute;
        this.app = app;
        currentRoute = new Route(initialRoute);
    }

    public ArrayList<node> getInitialRoute() {
        return initialRoute;
    }

    @Override
    public void run() {

        Population population = new Population(POPULATION_SIZE, initialRoute);
        population.sortRoutesByFitness();
        int generationNumber = 0;
        while (generationNumber < NUMB_OF_GENERATIONS) {

            population = evolve(population);
            population.sortRoutesByFitness();
            currentRoute = population.getRoutes().get(0);

            sleep();
            app.pain(currentRoute);
            generationNumber++;
            app.label.setText("#" + generationNumber + "  Distance: " + currentRoute.getTotalStringDistance()
                    + " KM  Fitness: " + String.format("%.3f", currentRoute.getFitness()));
            details += "\n\n                              (Generation #" + generationNumber + ")\n\n" + popLines(population);

        }
        finalRoute += getLine();
        app.text2.setText("Final Result: \n\n" + finalRoute);
        app.text.setText(details);
    }

    public Population evolve(Population population) {
        return mutatePopulation(crossoverPopulation(population));
    }

    Population crossoverPopulation(Population population) {

        Population crossoverPopulation = new Population(population.getRoutes().size(), this);

        IntStream.range(0, NUMB_OF_ELITE_ROUTES).forEach(x -> crossoverPopulation.getRoutes().set(x, population.getRoutes().get(x)));
        IntStream.range(NUMB_OF_ELITE_ROUTES, crossoverPopulation.getRoutes().size()).forEach(x -> {
            Route routel = selectTournamentPopulation(population).getRoutes().get(0);
            Route route2 = selectTournamentPopulation(population).getRoutes().get(0);
            crossoverPopulation.getRoutes().set(x, crossoverRoute(routel, route2));
        });
        return crossoverPopulation;
    }

    Population mutatePopulation(Population population) {
        population.getRoutes().stream().filter(x -> population.getRoutes().indexOf(x) >= NUMB_OF_ELITE_ROUTES).forEach(x -> mutateRoute(x));
        return population;
    }

    // an example crossover of routel and route2: 
    // route1: [New York, San Fransisco, Houston, Chicago, Boston, Austin, Seattle, Denver, Dallas, Los Angeles] 
    // route2: [Los Angeles, Seattle, Austin, Boston, Denver, New York, Houston, Dallas, San Fransisco, Chicago]
    // intermediate crossoverRoute: [New York, San Francisco, Houston, Chicago, Boston, null, null, null, null, null] 
    // final crossoverRoute: [New York, San Francisco, Houston, Chicago, Boston, Los Angeles, Seattle, Austin, Denver, Dallas]
    Route crossoverRoute(Route route1, Route route2) {

        Route crossoverRoute = new Route(this);
        Route tempRoute1 = route1;
        Route tempRoute2 = route2;
        if (Math.random() < 0.5) {
            tempRoute1 = route2;
            tempRoute2 = route1;
        }
        for (int x = 0; x < crossoverRoute.getCities().size() / 2; x++) {
            crossoverRoute.getCities().set(x, tempRoute1.getCities().get(x));
        }

        return fillNullsInCrossoverRoute(crossoverRoute, tempRoute2);
    }

    // crossoverRoute: (Seattle, Houston, Denver, Los Angeles, San Ecansisco, null, null, null, null, null]
    // route : [Los Angeles, Chicago, Austin, Houston, Denver, San Francisco, Seattle, Boston, New York, Dallas]
    // crossoverRoute: Seattle, Houston, Denver, Los Angeles, San Francisco, Chicago, Austin, Boston, New York, Dallas]
    private Route fillNullsInCrossoverRoute(Route crossoverRoute, Route route) {

        route.getCities().stream().filter(x -> !crossoverRoute.getCities().contains(x)).forEach(
                cityX -> {
                    for (int y = 0; y < route.getCities().size(); y++) {
                        if (crossoverRoute.getCities().get(y) == null) {
                            crossoverRoute.getCities().set(y, cityX);
                            break;
                        }
                    }
                });
        return crossoverRoute;
    }

    //an example route mutation
    // original route: (Boston, Denver, Los Angeles, Austin, New York, Seattle, Chicago, San Ecansisse, Dallas, Houston]
    // mutated route: [Boston, Denver, New York, Austin, Los Angeles, Seattle, San Francisco, Chicago, Dallas, Houston]
    Route mutateRoute(Route route) {

        route.getCities().stream().filter(x -> Math.random() < MUTATION_RATE).forEach(cityX -> {
            int y = (int) (route.getCities().size() * Math.random());
            node cityY = route.getCities().get(y);
            route.getCities().set(route.getCities().indexOf(cityX), cityY);
            route.getCities().set(y, cityX);
        });
        return route;
    }

    Population selectTournamentPopulation(Population population) {

        Population tournamentPopulation = new Population(TOURNAMENT_SELECTION_SIZE, this);
        IntStream.range(0, TOURNAMENT_SELECTION_SIZE).forEach(x -> tournamentPopulation.getRoutes().set(
                x, population.getRoutes().get((int) (Math.random() * population.getRoutes().size()))));
        tournamentPopulation.sortRoutesByFitness();
        return tournamentPopulation;
    }

    public void sleep() {
        try {
            Thread.sleep(app.getDelay());
        } catch (InterruptedException e) {
            System.out.println("Failed to Sleep?");
        }
    }

    private String getLine() {
       
        return "Route: " + currentRoute.toString() + "\nDistance: " + currentRoute.getTotalStringDistance()
                + " KM\nFitness: " + String.format("%.3f", currentRoute.getFitness())
                +getCost(currentRoute.getTotalDistance());
    }

    private String popLines(Population population) {
        String temp = "";
        for (Route route : population.getRoutes()) {
            temp += "Route: " + route.toString() + "\nDistance: " + route.getTotalStringDistance() 
                    + " KM\nFitness: " + String.format("%.3f", route.getFitness()) 
                    + getCost(route.getTotalDistance())+"\n\n";

        }

        return temp;
    }

    private String getCost(double distance) {
        if(app.getKMperLiter() == 0)
            return "";
        double SR = (distance/app.getKMperLiter()) *2.18;
        return "\nCost: "+String.format("%.2f", SR)+" SR";
    }
    
    public void changeNum(int change){NUMB_OF_GENERATIONS = change;}

}
