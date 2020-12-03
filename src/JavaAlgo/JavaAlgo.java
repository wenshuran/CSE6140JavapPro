package JavaAlgo;

import java.io.File;

public class JavaAlgo {
    public static void main(String[] args) {
        File file=new File("./output");
        if(!file.exists()){//if the dir not exist
            file.mkdir();//create the dir
        }
        try {
            String filename = args[1];
            String algoName = args[3];
            int time = Integer.parseInt(args[5]);
            int seed = Integer.parseInt(args[7]);
            Algo algo = null;
            switch (algoName){
                case "BnB":
                    algo = new BranchAndBound();
                    break;
                case "Approx":
                    algo = new Approx();
                    break;
                case "LS1":
                    algo = new SAVC();
                    break;
                case "LS2":
                    algo = new HillClimbing();
                    break;
                default:
                    System.out.println("ERROR: unimplemented algorithm");
            }
            algo.run(filename, time, seed);

        }catch (Exception e){
            System.out.println("Wrong arguments: " + e.toString());
        }
    }
}
