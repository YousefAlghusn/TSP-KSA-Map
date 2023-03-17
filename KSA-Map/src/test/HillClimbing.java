package test;

public class HillClimbing implements Runnable {

    public int Max = 100;
    Route currentRoute;
    App app;
    boolean normal = true;
    String finalRoute = "";
    String details = "Details: \n\n";
    //String cities = "";
    public HillClimbing(Route currentRoute, App app) {
        this.currentRoute = currentRoute;
        this.app = app;
     //   cities = currentRoute.getCitiesString();
    }

    @Override
    public void run() {

        Route adjacentRoute;
        int count = 0, iteration = 0;
        String compareRoutes = null;
        while (count < Max) {
            adjacentRoute = obtainAdjacentRoute(new Route(currentRoute));
            if (adjacentRoute.getTotalDistance() < currentRoute.getTotalDistance()) {
                compareRoutes = "<= (proceed)";
                count++;
                if(normal)
                    count = 0;
                currentRoute = new Route(adjacentRoute);
            } else {
                compareRoutes = "> (stay) - iteration # " + count++;
            }
            System.out.println("     |" + compareRoutes);
            System.out.println(currentRoute + " |    " + currentRoute.getTotalDistance());
            sleep();
            iteration++;
            app.pain(currentRoute);
            app.label.setText("#" + iteration+ "  Distance: " + currentRoute.getTotalStringDistance() + " KM");

            details += "\n#" + iteration + "\n" + getLine() + "\n";
            
        }
        if (count == Max) {
            System.out.println("     |maybe max");
        } else {
            System.out.println("     |" + compareRoutes);
        }
        finalRoute += getLine();
        app.text.setText(details);
        app.text2.setText("Final Result: \n\n" + finalRoute);
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

    public void sleep() {
        try {
            Thread.sleep(app.getDelay());
        } catch (InterruptedException e) {
            System.out.println("Failed to Sleep?");
        }
    }

    private String getLine() {
        return "Route: " + currentRoute.toString() + "\nDistance: " + currentRoute.getTotalStringDistance() + " KM"
                +getCost(currentRoute.getTotalDistance());
    }
    
    public void changeMax(int newMax){
            Max = newMax;
            normal = false;
    }
    private String getCost(double distance) {
        if(app.getKMperLiter() == 0)
            return "";
        double SR = (distance/app.getKMperLiter()) *2.18;
        return "\nCost: "+String.format("%.2f", SR)+" SR";
    }
}
