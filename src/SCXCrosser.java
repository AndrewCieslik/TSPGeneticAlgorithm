import java.util.Collections;

public class SCXCrosser implements Crosser {
    public Path cross(Path mom, Path dad) {
        Path child = new Path();
        child.cities.clear();
        Path sorted = new Path();
        Collections.sort(sorted.cities);

        child.cities.add(mom.cities.get(0));
        for (int i = 1; i < TSP.numberOfCities; i++) {
            int previousCity = child.cities.get(i - 1);
            int momPreviousCityIndex = mom.cities.indexOf(previousCity);
            int dadPreviousCityIndex = dad.cities.indexOf(previousCity);

            int momCity = mom.cities.get((momPreviousCityIndex + 1) % TSP.numberOfCities);
            if (child.cities.contains(momCity)) {
                for (int j = 0; j < sorted.length(); j++) {
                    if (!child.cities.contains(sorted.cities.get(j))) {
                        momCity = sorted.cities.get(j);
                        break;
                    }
                }
            }
            int dadCity = dad.cities.get((dadPreviousCityIndex + 1) % TSP.numberOfCities);
            if (child.cities.contains(dadCity)) {
                for (int j = 0; j < sorted.length(); j++) {
                    if (!child.cities.contains(sorted.cities.get(j))) {
                        dadCity = sorted.cities.get(j);
                        break;
                    }
                }
            }
            if (TSP.distanceMatrix[previousCity][momCity] < TSP.distanceMatrix[previousCity][dadCity]) {
                child.cities.add(momCity);
            } else {
                child.cities.add(dadCity);
            }
        }
        return child;
    }
}
