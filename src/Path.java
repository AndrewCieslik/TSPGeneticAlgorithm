import java.util.*;

public class Path {
    List<Integer> cities;
    int lastCityIndex;

    public Path() {
        this.cities = new ArrayList<>();
        for (int i = 0; i < TSP.numberOfCities; i++) {
            cities.add(TSP.cities.get(i).index);
        }
        Collections.shuffle(cities);
        this.lastCityIndex = cities.size() - 1;
    }

    int length() {
        int length = 0;

        for (int i = 0; i < lastCityIndex; i++) {
            int city1 = cities.get(i);
            int city2 = cities.get(i + 1);
            length += TSP.distanceMatrix[city1 - 1][city2 - 1];
        }
        length += TSP.distanceMatrix[lastCityIndex][0];
        return length;
    }
}