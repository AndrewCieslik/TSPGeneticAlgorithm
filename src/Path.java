import java.util.*;

public class Path {
    List<Integer> cities;
    int lastCityIndex;
    Random random = new Random();

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

        for (int i = 0; i < cities.size() - 1; i++) {
            int city1 = cities.get(i); //5
            int city2 = cities.get(i + 1); //10
            length += TSP.distanceMatrix[city1][city2];
        }
        length += TSP.distanceMatrix[cities.size() - 1][cities.get(0)];
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

    void inversion_mutation() {
        int minSegment = (int) (TSP.numberOfCities * 0.4);
        int maxSegment = (int) (TSP.numberOfCities * 0.6);
        int segmentSize = random.nextInt(maxSegment - minSegment + 1) + minSegment;
        int firstIndexSegment = random.nextInt(cities.size() - segmentSize);
        List<Integer> segment;
        segment = cities.subList(firstIndexSegment, firstIndexSegment + segmentSize);
        Collections.reverse(segment);
        for (int i = 0; i < segmentSize; i++) {
            cities.set(firstIndexSegment + i, segment.get(i));
        }
    }

    Path copy() {
        Path newPath = new Path();
        newPath.cities.clear();
        newPath.cities.addAll(cities);

        return newPath;
    }
}