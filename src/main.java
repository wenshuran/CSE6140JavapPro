import algo.SAVC;

import java.io.FileNotFoundException;
import java.util.logging.Logger;

public class main {

    public static void main(String []args) throws FileNotFoundException {
        Logger.getGlobal().info("Start Minimum Vertex Cover Test ...");
        System.out.println("================= SA algorithm ===================");
        SAVC sa = new SAVC("data\\delaunay_n10.graph");
        sa.run(2,189, false);
        sa.printResult();
    }
}
