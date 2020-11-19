package vc;

import vc.algo.SAVC;

import java.io.FileNotFoundException;
import java.util.logging.Logger;

public class Main {

    public static void main(String []args) throws FileNotFoundException {
        Logger.getGlobal().info("Start Minimum Vertex Cover Test ...");
        System.out.println("================= SA algorithm ===================");
        SAVC sa = new SAVC("data\\jazz.graph");
        sa.run(6,6140, false);
        System.out.println("Checking the result is still a VC: " + sa.checkVC());
        sa.printResult();
    }
}
