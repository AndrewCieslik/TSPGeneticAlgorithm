import java.util.ArrayList;
import java.util.List;

public class Population {
    List<Path> pathsList;

    public Population() {
        this.pathsList = new ArrayList<>();
        for (int i = 0; i < TSP.populationSize; i++) {
            Path newPath = new Path();
            pathsList.add(newPath);
        }
    }
}
