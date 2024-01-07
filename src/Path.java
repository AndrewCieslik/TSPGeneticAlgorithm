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

    void mutation2opt() {
        Random random = new Random();
        int nodeA, nodeB, nodeC, nodeD;

        do {
            nodeA = random.nextInt(lastCityIndex - 1);
            nodeC = random.nextInt(lastCityIndex - 1);
            nodeB = nodeA + 1;
            nodeD = nodeC + 1;
        } while (nodeB >= nodeC);

        List<Integer> pathFromFirstToNodeA = cities.subList(0, nodeB);
        List<Integer> pathFromCtoB_reverse = cities.subList(nodeB, nodeD);
        Collections.reverse(pathFromCtoB_reverse);
        List<Integer> pathFromDToLast = cities.subList(nodeD, cities.size());

        List<Integer> mutatedPath = new ArrayList<>();
        mutatedPath.addAll(pathFromFirstToNodeA);
        mutatedPath.addAll(pathFromCtoB_reverse);
        mutatedPath.addAll(pathFromDToLast);

        cities.clear();
        cities.addAll(mutatedPath);
    }

    Path copy() {
        Path newPath = new Path();
        newPath.cities.addAll(cities);
        return newPath;
    }

}