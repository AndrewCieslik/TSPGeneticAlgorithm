import java.util.Collections;
import java.util.List;
import java.util.Random;

public class InversionMutator implements Mutator {
    public void mutate(Path path) {
        Random random = new Random();
        int minSegment = (int) (TSP.numberOfCities * 0.4);
        int maxSegment = (int) (TSP.numberOfCities * 0.6);
        int segmentSize = random.nextInt(maxSegment - minSegment + 1) + minSegment;
        int firstIndexSegment = random.nextInt(path.cities.size() - segmentSize);
        List<Integer> segment;
        segment = path.cities.subList(firstIndexSegment, firstIndexSegment + segmentSize);
        Collections.reverse(segment);
        for (int i = 0; i < segmentSize; i++) {
            path.cities.set(firstIndexSegment + i, segment.get(i));
        }
    }
}
