import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Opt2Mutator implements Mutator {
    public void mutate(Path path) {
        Random random = new Random();
        int nodeA, nodeB, nodeC, nodeD;
        int lastCityIndex = path.cities.size() - 1;

        do {
            nodeA = random.nextInt(lastCityIndex - 1);
            nodeB = nodeA + 1;
            nodeC = random.nextInt(lastCityIndex - 1);
            nodeD = nodeC + 1;

        } while (nodeB >= nodeC);

        List<Integer> pathFromCtoB_reverse = path.cities.subList(nodeB, nodeC + 1);
        Collections.reverse(pathFromCtoB_reverse);
    }
}
