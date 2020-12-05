package main.java;

import java.io.File;

import main.java.method.Approx.Approx;
import main.java.method.BnB.BranchAndBound;
import main.java.method.LS1.SAVC;
import main.java.method.LS2.HillClimbing;

public class JavaAlgo {
    public static void main(String[] args) {
    	System.out.println("running on..." + System.getProperty("user.dir"));
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
            
            String graphPrefix = filename.substring(0, filename.length() - 6);
            String inFileName = "src/data/" + filename;
            String outSol = "./output/";
            String outTrace = "./output/";
            
            switch (algoName){
                case "BnB":
                    algo = new BranchAndBound();
                    outSol = outSol+graphPrefix+"_BnB_"+time+".sol";
                    outTrace = outTrace+graphPrefix+"_BnB_"+time+".trace";
                    break;
                case "Approx":
                    algo = new Approx();
                    outSol = outSol+graphPrefix+"_Approx_"+time+"_"+seed+".sol";
                    outTrace = outTrace+graphPrefix+"_Approx_"+time+"_"+seed+".trace";
                    break;
                case "LS1":
                    algo = new SAVC();
                    outSol = outSol+graphPrefix+"_LS1_"+time+"_"+seed+".sol";
                    outTrace = outTrace+graphPrefix+"_LS1_"+time+"_"+seed+".trace";
                    break;
                case "LS2":
                    algo = new HillClimbing();
                    outSol = outSol+graphPrefix+"_LS2_"+time+"_"+seed+".sol";
                    outTrace = outTrace+graphPrefix+"_LS2_"+time+"_"+seed+".trace";
                    break;
                default:
                    System.out.println("ERROR: unimplemented algorithm");
            }
            algo.run(inFileName, outSol, outTrace, time, seed);

        }catch (Exception e){
            System.out.println("Wrong arguments: " + e.toString());
        }
    }
}
