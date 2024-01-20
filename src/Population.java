import java.util.*;

public class Population {
    static Random random = new Random();
    List<Path> pathsList;
    HashMap<Integer, Integer> parents;
    Crosser crosser;
    Mutator mutator;

    public Population() {
        this.pathsList = new ArrayList<>();
        for (int i = 0; i < TSP.populationSize; i++) {
            Path newPath = new Path();
            pathsList.add(newPath);
        }
        this.parents = new HashMap<>();
        this.crosser = new SCXCrosser();
        this.mutator = new Opt2Mutator();
    }

    void print() {
        for (Path path : pathsList) {
            System.out.println(path.cities);
            System.out.println("Length: " + path.length());
        }
    }

    Population evolution() {
        Population duringEvoGen = new Population();
        //1.Crossing
        double randomProb;
        int mom;
        int dad;
        for (Path path : pathsList) {
            randomProb = Math.random();
            mom = random.nextInt(TSP.cities.size());
            do {
                dad = random.nextInt(TSP.cities.size());
            } while (dad == mom);

            if (randomProb < TSP.crossingProb) {
                duringEvoGen.pathsList.add(crosser.cross(pathsList.get(mom), pathsList.get(dad)));
            }
        }

        //2.Mutation
        for (Path path : duringEvoGen.pathsList) {
            randomProb = Math.random();
            if (randomProb < TSP.mutationProb) {
                mutator.mutate(path);
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
        newGen.pathsList.addAll(elite);

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
        pop.pathsList.sort(Comparator.comparingInt(Path::length));
        int eliteCount = Math.max(1, (int) (pop.pathsList.size() * TSP.elitePercent / 100.0));
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


