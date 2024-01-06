import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TSP {
    static int numberOfCities = 0;
    static Random random = new Random();
    static int populationSize = 2;
    static int generations = 18;
    static double mutationProb = 0.9;
    static double crossingProb = 0.9;
    static List<City> cities;
    static int[][] distanceMatrix;
    static long startTime = System.currentTimeMillis();
    static long duration = 1 * 10 * 1000;


    public static void main(String[] args) {

        cities = readCitiesFromFile("bier127.tsp");
        numberOfCities = 10;

        distanceMatrix = new int[numberOfCities][numberOfCities];
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                distanceMatrix[i][j] = manhattanDistance(cities.get(i), cities.get(j));
            }
        }
        Population newPop = new Population();
        Population tempPop = new Population();
        Population bestPop = new Population();
        int newLength = 0;
        int tempLength = 0;
        int bestLength = 0;
        int worstLength = 0;

        //while (System.currentTimeMillis() - startTime < duration) {
            newPop.crossingOX();
        //}
//
//            System.out.println("NewPop: " + newPop.pathsList.get(0).length());
//            newLength = newPop.pathsList.get(0).length();
//
//            tempPop = tempPop.copy(newPop);
//            tempPop.pathsList.get(0).mutation2opt();
//            System.out.println("Mutation");
//
//            System.out.println("tempPop: " + tempPop.pathsList.get(0).length());
//            tempLength = tempPop.pathsList.get(0).length();
//
//            if (tempLength < newLength) { //mutacja polepszyla
//                bestLength = tempLength;
//                newPop = newPop.copy(tempPop);
//            }
//            if (worstLength < tempLength) {
//                worstLength = tempLength;
//            }
//            if (worstLength < newLength) {
//                worstLength = newLength;
//            }
////        }
//            System.out.println("Best: " + bestLength);
//            System.out.println("Worst: " + worstLength);
//        }

    }

    static int manhattanDistance(City city1, City city2) {
        return Math.abs(city2.x - city1.x) + Math.abs(city2.y - city1.y);
    }

    static List<List<Integer>> selection(List<List<Integer>> population, List<Double> fitnessScores) {
        // Implementacja selekcji (np. ruletka)
        // ...
        return null;
    }

    static List<List<Integer>> pmxCrossover(List<Integer> parent1, List<Integer> parent2) {
        // Implementacja krzyżowania PMX
        // ...
        return null;
    }

    static void mutate(List<Integer> path) {
        // Implementacja mutacji
        // ...
    }

    static List<City> readCitiesFromFile(String inputFile) {
        List<City> cities = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            boolean startReading = false;
            while ((line = br.readLine()) != null) {
                if (line.equals("NODE_COORD_SECTION")) {
                    startReading = true;
                    continue;
                }
                if (line.equals("EOF")) {
                    break;
                }
                if (startReading) {
                    String[] parts = line.trim().split("\\s+");
                    int index = Integer.parseInt(parts[0]);
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    City city = new City(index, x, y);
                    cities.add(city);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }
}
