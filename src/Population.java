import java.util.*;

public class Population {
    static Random random = new Random();
    List<Path> pathsList;
    HashMap<Integer, Integer> parents;


    public Population() {
        this.pathsList = new ArrayList<>();
        for (int i = 0; i < TSP.populationSize; i++) {
            Path newPath = new Path();
            pathsList.add(newPath);
        }
        this.parents = new HashMap<>();
    }


    void print() {
        for (Path path : pathsList) {
            System.out.println(path.cities);
            System.out.println("Length: " + path.length());
        }
    }

    Path crossingOX(int mom, int dad, int segmentSize, int firstIndexSegment) {

        int indexAfterSegment = firstIndexSegment + segmentSize;

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
        for (int i = 0; i < indexAfterSegment; i++) {
            for (int j = 0; j < dad_with_wrapping.cities.size(); j++) {
                if (!child.cities.contains(dad_with_wrapping.cities.get(j))) {
                    child.cities.set(i, dad_with_wrapping.cities.get(j));
                    break;
                }
            }
        }
        return child;
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

    Population evolution() {
        //1.crossing:
        int minSegment = (int) (TSP.numberOfCities * 0.4);
        int maxSegment = (int) (TSP.numberOfCities * 0.6);
        int segmentSize = random.nextInt(maxSegment - minSegment + 1) + minSegment;
        int firstIndexSegment = random.nextInt(TSP.numberOfCities - segmentSize);

        generateUniquePairs();
        double randomProb;
        int mom;
        int dad;

        Population duringEvoGen = new Population();

        for (Integer key : parents.keySet()) {
            randomProb = Math.random();
            mom = key;
            dad = parents.get(key);

            if (randomProb < TSP.crossingProb) {
                duringEvoGen.pathsList.add(crossingOX(mom, dad, segmentSize, firstIndexSegment));
                duringEvoGen.pathsList.add(crossingOX(dad, mom, segmentSize, firstIndexSegment));
            }
        }

        //2.Mutation
        for (Path path : duringEvoGen.pathsList) {
            randomProb = Math.random();
            if (randomProb < TSP.mutationProb) {
                path.inversion_mutation();
            }
        }
        Population newGen = new Population();
        newGen.pathsList.clear();


        double sumInverseLength = 0;

        for (Path path : duringEvoGen.pathsList) {
            double length = path.length();
            sumInverseLength += 1 / length;
        }

        Map<Path, Double> indivProbMap = new HashMap<>();
        for (Path path : duringEvoGen.pathsList) {
            double length = path.length();
            double reversed_prob = (1 / length) / sumInverseLength;
            indivProbMap.put(path, reversed_prob);
        }

        List<Path> elite = findElite(duringEvoGen);
        duringEvoGen.pathsList.addAll(elite);

        while (newGen.pathsList.size() < TSP.populationSize) {
            double selectProb = Math.random();
            for (Path path : indivProbMap.keySet()) {
                if (selectProb < indivProbMap.get(path)) {
                    newGen.pathsList.add(path);
                    break;
                }
                selectProb -= indivProbMap.get(path);
            }
        }
        return newGen;
    }

    List<Path> findElite(Population pop) {
        List<Path> elite = new ArrayList<>();
        int percent = 2;
        pop.pathsList.sort(Comparator.comparingInt(Path::length));

        int eliteCount = Math.max(1, (int) (pop.pathsList.size() * percent / 100.0));
        elite.addAll(pop.pathsList.subList(0, eliteCount));

        return elite;
    }

    Population copy() {
        Population newPop = new Population();
        newPop.pathsList.clear();
        newPop.pathsList.addAll(pathsList);
        return newPop;
    }

    String theBest() {
        int bestLength = 1000000;
        Path bestPath = new Path();
        bestPath.cities.clear();
        for (int i = 0; i < TSP.populationSize; i++) {
            int length = pathsList.get(i).length();
            if (length < bestLength) {
                bestLength = length;
                bestPath = pathsList.get(i).copy();
            }
        }
        return bestLength + " " + bestPath.cities;
    }
}


