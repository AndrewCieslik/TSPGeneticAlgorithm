import java.util.ArrayList;
import java.util.List;

public class Population {
    List<Path> pathsList;

    public Population() {
        this.pathsList = new ArrayList<>();
        for (int i = 0; i < TSP.populationSize; i++) {
            Path newPath = new Path();
            pathsList.add(newPath);
        }
    }

    void print(){
        for (int pathNum = 0; pathNum < TSP.populationSize; pathNum++) {
            for (int i = 0; i < TSP.numberOfCities; i++) {
                System.out.print(pathsList.get(pathNum).cities.get(i) + "-");
            }
            System.out.println("");
        }
    }
}
