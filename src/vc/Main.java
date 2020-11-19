package vc;

import vc.algo.SAVC;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Main {

    public static void main(String []args) throws FileNotFoundException {
        Logger.getGlobal().info("Start Minimum Vertex Cover Test ...");
        test_all_sa();
//        System.out.println("================= SA algorithm ===================");
//        SAVC sa = new SAVC("data\\jazz.graph");
//        sa.run(6,6140, false);
//        System.out.println("Checking the result is still a VC: " + sa.checkVC());
//        sa.printResult();
    }

    public static void test_all_sa() throws FileNotFoundException {
        File dataDir = new File("data");
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dataDir, String name) {
                return name.endsWith("graph");
            }
        };
        String[] children = dataDir.list(filter);
        if (children == null) {
            System.out.println("Target not exist!");
        }
        else {
            for (int i=0; i < children.length; i++) {
                String filename = children[i];
                System.out.println(filename);
                SAVC sa = new SAVC("data\\" + filename);
                sa.run(60,6140, false);
            }
        }

    }

}
