package vc.algo;

import java.util.Random;
import java.util.logging.Logger;


class SA {

    private double T_init;
    private double cool_rate;
    private double T_limit;

    public SA() {
        T_init = 1000000;
        cool_rate = 0.97;
        T_limit = 0.0;
    }

    public SA(double T_init, double cool_rate, double T_limit) {
        this.T_init = T_init;
        this.cool_rate = cool_rate;
        this.T_limit = T_limit;
    }

    public void run(SAInstance instance, float cutoff, int seed){
        this.run(instance, cutoff, seed, false);
    }

    public void run(SAInstance instance, float cutoff, int seed, boolean verbose){

        Random rd = new Random();
        rd.setSeed(seed);
        String last_update = new String("");

        if(!instance.init(rd)){
            Logger.getGlobal().info("Initialization Failed!");
            System.exit(-1);
        }
        else{
            System.out.println("SA Instance initialized cost: " + instance.getCost());
        }

        float best_cost = instance.getCost();

        long start_t = System.currentTimeMillis();
        long elapsed_t_milis = 0;
        float elapsed_t = 0;

        double T = T_init;

        while (elapsed_t < cutoff && T > T_limit) {
            instance.genNeighbor(rd);
            double delta_F = instance.getNeighborCost() - instance.getCost();

//            System.out.printf("%.0f  %.0f %n", instance.getNeighborCost(), instance.getCost());

            if(delta_F < 0){
                instance.update();
            } else {
                double p = Math.exp(-delta_F * instance.getProbFactor() / T);
                if (p > rd.nextDouble()){
                    instance.update();
                }
            }

            elapsed_t_milis = System.currentTimeMillis() - start_t;
            elapsed_t = (float) elapsed_t_milis / 1000;

            float cur_cost = instance.getCost();
            // write to trace
            if (cur_cost < best_cost) {
                best_cost = cur_cost;
                last_update = "elapsed time: "+elapsed_t+", current cost: "+cur_cost+"\n";
                if(verbose) {
                    System.out.printf("elapsed time: %.6f, current cost: %.0f%n", elapsed_t, cur_cost);
                }
            }
            if (elapsed_t > cutoff) {
                break;
            }

            T *= cool_rate;

        }

        System.out.printf("Exit at time %.6f s%n", elapsed_t);
        System.out.println("    latest update record: "+last_update);

    }

}

