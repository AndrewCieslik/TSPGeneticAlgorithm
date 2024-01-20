import java.util.*;

public class Path {
    List<Integer> cities;


    public Path() {
        this.cities = new ArrayList<>();
        for (int i = 0; i < TSP.numberOfCities; i++) {
            cities.add(TSP.cities.get(i).index);
        }
        Collections.shuffle(cities);
    }

    int length() {
        int length = 0;

        for (int i = 0; i < cities.size() - 1; i++) {
            int city1 = cities.get(i);
            int city2 = cities.get(i + 1);
            length += TSP.distanceMatrix[city1][city2];
        }
        length += TSP.distanceMatrix[cities.size() - 1][cities.get(0)];
        return length;
    }

    Path copy() {
        Path newPath = new Path();
        newPath.cities.clear();
        newPath.cities.addAll(cities);
        return newPath;
    }
}