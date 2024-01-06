import java.util.*;

public class Population {
    private List<Path> pathsList;
    private HashMap<Integer, Integer> parents;


    public Population() {
        this.pathsList = new ArrayList<>();
        for (int i = 0; i < TSP.populationSize; i++) {
            Path newPath = new Path();
            pathsList.add(newPath);
            this.parents = new HashMap<>();
        }
    }

    Population copy(Population pop) {
        Population copy = new Population();
        copy.pathsList = new ArrayList<>(pop.pathsList);
        return copy;
    }

    void print() {
        for (int pathNum = 0; pathNum < TSP.populationSize; pathNum++) {
            for (int i = 0; i < TSP.numberOfCities; i++) {
                System.out.print(pathsList.get(pathNum).cities.get(i) + "-");
            }
            System.out.println("");
        }
    }

    void crossingOX() {
        int minSegment = (int) (TSP.numberOfCities * 0.1);
        int maxSegment = (int) (TSP.numberOfCities * 0.6);
        System.out.println("MinSegment: " + minSegment);
        System.out.println("MaxSegment: " + maxSegment);

        Random rnd = new Random();
        int segmentSize = rnd.nextInt(maxSegment - minSegment + 1) + minSegment;
        System.out.println("SegmentSize: " + segmentSize);
        int firstIndexSegment = rnd.nextInt(TSP.numberOfCities - segmentSize);
        int indexAfterSegment = firstIndexSegment + segmentSize;
        System.out.println("First index segment: " + firstIndexSegment);
        System.out.println(" index after segment: " + indexAfterSegment);
        System.out.println("Number od cities: " + TSP.numberOfCities);

        generateUniquePairs();
        double randomProb;
        int mom = 0;
        int dad = 0;

        //1.Crossing
        for (Integer key : parents.keySet()) {
            randomProb = Math.random();
            mom = key;
            dad = parents.get(key);

            if (randomProb < TSP.crossingProb) {
            }

        }

        Path child = new Path();
        for (int i = 0; i < child.cities.size(); i++) {
            child.cities.set(i, null);
        }

        for (int i = firstIndexSegment; i < indexAfterSegment; i++) {
            child.cities.set(i, pathsList.get(mom).cities.get(i));
        }

        Path dad_with_wrapping = new Path();
        dad_with_wrapping.cities.clear();
        dad_with_wrapping.cities.addAll(pathsList.get(dad).cities.subList(indexAfterSegment, TSP.numberOfCities));
        dad_with_wrapping.cities.addAll(pathsList.get(dad).cities.subList(0, indexAfterSegment));

        for (int i = indexAfterSegment; i < child.cities.size(); i++) {
            for (int j = 0; j < dad_with_wrapping.cities.size(); j++) {
                if (!child.cities.contains(dad_with_wrapping.cities.get(j))) {
                    child.cities.set(i, dad_with_wrapping.cities.get(j));
                    break;
                }
            }
        }
//        for (int i = 0; i < indexAfterSegment; i++) {
//            for (int j = 0; j < indexAfterSegment; j++) {
//                if (!child.cities.contains(dad_with_wrapping.cities.get(j))) {
//                    child.cities.set(i, dad_with_wrapping.cities.get(j));
//                }
//            }
//        }
        System.out.println("Mom: " + pathsList.get(mom).cities);
        System.out.println("Dad: " + pathsList.get(dad).cities);
        System.out.println("wrp: " + dad_with_wrapping.cities);

        System.out.println("Kid: " + child.cities);
    }


    void generateUniquePairs() {
        List<Integer> paths = new ArrayList<>();
        for (int i = 0; i < TSP.populationSize; i++) {
            paths.add(i);
        }

        Collections.shuffle(paths);

        int endIndex = paths.size();
        if (paths.size() % 2 != 0) {
            endIndex--;
        }
        for (int i = 0; i < endIndex; i += 2) {
            int key = paths.get(i);
            int value = paths.get(i + 1);
            parents.put(key, value);
        }
    }
}
