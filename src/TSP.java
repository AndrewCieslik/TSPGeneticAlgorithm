import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TSP {
    static int numberOfCities = 0;
    static Random random = new Random();
    static int populationSize = 10;
    static int generations = 18;
    static double mutationRate = 0.1;
    static List<City> cities;
    static int[][] distanceMatrix;

    public static void main(String[] args) {

        cities = readCitiesFromFile("bier127.tsp");
        numberOfCities = 2;

        distanceMatrix = new int[numberOfCities][numberOfCities];
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                distanceMatrix[i][j] = manhattanDistance(cities.get(i), cities.get(j));
            }
        }

       Population newPop = new Population();
       for(int i =0; i < numberOfCities; i++){
           System.out.print(newPop.paths.get(0).path.get(i) + "-");
       }
       System.out.println(newPop.paths.get(0).length());
    }



    static int manhattanDistance(City city1, City city2) {
        return Math.abs(city1.x - city1.y) + Math.abs(city2.x - city2.y);
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