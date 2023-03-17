package test;

public class SimulatedAnnealing implements Runnable {

    public static final double coolingRate = 0.01;
    public double INITIALTEMPERATURE = 999;
    public static final double MINTEMPERATURE = 0;

    Route currentRoute;
    App app;
    double temperature;
    String finalRoute = "";
    String details = "Details: \n\n";
    int iteration;

    public SimulatedAnnealing(Route currentRoute, App app) {
        this.currentRoute = currentRoute;
        this.app = app;
        this.temperature = INITIALTEMPERATURE;
        iteration = 0;
    }

    @Override
    public void run() {
        Route shortestRoute = new Route(currentRoute);
        Route adjacentRoute;
        while (temperature > MINTEMPERATURE) {
            System.out.print(currentRoute + " " + currentRoute.getTotalStringDistance() + " " + String.format("%.2f", temperature));
            adjacentRoute = obtainAdjacentRoute(new Route(currentRoute));
            if (currentRoute.getTotalDistance() < shortestRoute.getTotalDistance()) {
                shortestRoute = new Route(currentRoute);
            }
            if (acceptRoute(currentRoute.getTotalDistance(), adjacentRoute.getTotalDistance(), temperature)) {
                currentRoute = new Route(adjacentRoute);
            }
            sleep();
            app.pain(currentRoute);
            if (temperature <= 0.01) {
                temperature = 0;
            }
            String temp2 = String.format("%.2f", temperature);
            app.label.setText("Temperature: " + temp2 + "  Distance: " + currentRoute.getTotalStringDistance() + " KM");
            details += "\n#" + iteration + "\n" + getLine() + "\n";
            iteration++;
            temperature *= (1 - coolingRate);
            
        }
        finalRoute = getLine();
        app.text.setText(details);
        app.text2.setText("Final Result: \n\n" + finalRoute);
    }

    private boolean acceptRoute(double currentDistance, double adjacentDistance, double temperature) {

        String decision = null;
        boolean shorterDistance = true;
        boolean acceptRouteFlag = false;
        double acceptanceProbability = 1.0;

        if (adjacentDistance >= currentDistance) {
            acceptanceProbability = Math.exp(-(adjacentDistance - currentDistance) / temperature);
            shorterDistance = false;
        }
        double randomNumb = Math.random();
        if (acceptanceProbability >= randomNumb) {
            acceptRouteFlag = true;
        }
        if (shorterDistance) {
            decision = "Proceed (Shorter Adjacent Route)";
        } else if (acceptRouteFlag) {
            decision = "Proceed (Random # <= Prob Function)";
        } else {
            decision = "Stay (Random # > Prob Function)";
        }
        System.out.println(" " + String.format("%.2f", acceptanceProbability) + " |"
                + String.format("%.2f", randomNumb) + " " + decision);
        return acceptRouteFlag;
    }

    private Route obtainAdjacentRoute(Route route) {
        int x1 = 0, x2 = 0;
        while (x1 == x2) {
            x1 = (int) (route.getCities().size() * Math.random());
            x2 = (int) (route.getCities().size() * Math.random());
        }
        node city1 = route.getCities().get(x1);
        node city2 = route.getCities().get(x2);
        route.getCities().set(x2, city1);
        route.getCities().set(x1, city2);
        return route;
    }

    private String getLine() {
        return "Route: " + currentRoute.toString() + "\nDistance: " + currentRoute.getTotalStringDistance() + " KM"
                +getCost(currentRoute.getTotalDistance());
    }

    public void sleep() {
        try {
            Thread.sleep(app.getDelay());
        } catch (InterruptedException e) {
            System.out.println("Failed to Sleep?");
        }
    }
    
    private String getCost(double distance) {
        if(app.getKMperLiter() == 0)
            return "";
        double SR = (distance/app.getKMperLiter()) *2.18;
        return "\nCost: "+String.format("%.2f", SR)+" SR";
    }
    
    public void changeTemp(double temp){
        
        temperature = temp;
    
    }
}
