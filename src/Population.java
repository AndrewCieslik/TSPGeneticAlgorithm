import java.util.ArrayList;
import java.util.List;

public class Population {
    List<Path> paths;

    public Population() {
        this.paths = new ArrayList<>();
        for (int i = 0; i < TSP.populationSize; i++) {
            Path newPath = new Path();
            paths.add(newPath);
        }
    }
}
