import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TSP {
    static int numberOfCities = 0;
    static int populationSize = 1000;
    static int iterations = 10;
    static double mutationProb = 0.9;
    static double crossingProb = 0.9;
    static List<City> cities;
    static int[][] distanceMatrix;
    static long startTime;
    static long duration = 1 * 60 * 1000;

    public static void main(String[] args) throws IOException {

        cities = readCitiesFromFile("bier127.tsp");
        numberOfCities = cities.size();
        FileWriter writer = new FileWriter("results.txt");

        distanceMatrix = new int[numberOfCities + 1][numberOfCities + 1];
        distanceMatrix[0][0] = 0;
        for (int i = 1; i <= numberOfCities; i++) {
            for (int j = 1; j <= numberOfCities; j++) {
                distanceMatrix[i][j] = manhattanDistance(cities.get(i - 1), cities.get(j - 1));
            }
        }

        while (iterations > 0) {
            Population newPop = new Population();
            Population newGen;
            startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < duration) {
                newGen = newPop.evolution();
                newPop = newGen.copy();
            }
            writer.write(newPop.theBest());
            System.out.println(newPop.theBest());
            writer.write(System.lineSeparator());
            iterations--;
        }
        writer.close();
    }

    static int manhattanDistance(City city1, City city2) {
        return Math.abs(city2.x - city1.x) + Math.abs(city2.y - city1.y);
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
