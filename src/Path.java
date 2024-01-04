import java.util.*;

public class Path {
    List<Integer> path;
    int lastCityIndex;

    public Path() {
        this.path = new ArrayList<>();
        for (int i = 0; i < TSP.numberOfCities; i++) {
            path.add(TSP.cities.get(i).index);
        }
        Collections.shuffle(path);
        this.lastCityIndex = path.size() - 1;
    }

    int length() {
        int length = 0;

        for (int i = 0; i < lastCityIndex; i++) {
            int city1 = path.get(i);
            int city2 = path.get(i + 1);
            length += TSP.distanceMatrix[city1 - 1][city2 - 1];
        }
        length += TSP.distanceMatrix[lastCityIndex][0];
        return length;
    }
}