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

    void print(){
        for (int pathNum = 0; pathNum < TSP.populationSize; pathNum++) {
            for (int i = 0; i < TSP.numberOfCities; i++) {
                System.out.print(pathsList.get(pathNum).cities.get(i) + "-");
            }
            System.out.println("");
        }
    }

    void crossingOX(){
        int minSegment = (int) (TSP.numberOfCities * 0.1);
        int maxSegment = (int) (TSP.numberOfCities * 0.6);

        Random rnd = new Random();
        int segment = rnd.nextInt(maxSegment - minSegment + 1) + minSegment;
        System.out.println(segment);
        int startSegment = rnd.nextInt(TSP.numberOfCities - segment);

        //dobierz rodzicow
        Path child = new Path();

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
