import java.util.Random;

public class OXCrossing implements Crossing{
    public Path cross(Path mom, Path dad) {
        Random random = new Random();
        int minSegment = (int) (TSP.numberOfCities * 0.4);
        int maxSegment = (int) (TSP.numberOfCities * 0.6);
        int segmentSize = random.nextInt(maxSegment - minSegment + 1) + minSegment;
        int firstIndexSegment = random.nextInt(TSP.numberOfCities - segmentSize);

        int indexAfterSegment = firstIndexSegment + segmentSize;

        Path child = new Path();
        for (int i = 0; i < child.cities.size(); i++) {
            child.cities.set(i, null);
        }

        for (int i = firstIndexSegment; i < indexAfterSegment; i++) {
            child.cities.set(i, mom.cities.get(i));
        }

        Path dad_with_wrapping = new Path();
        dad_with_wrapping.cities.clear();
        dad_with_wrapping.cities.addAll(dad.cities.subList(indexAfterSegment, TSP.numberOfCities));
        dad_with_wrapping.cities.addAll(dad.cities.subList(0, indexAfterSegment));

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

}
