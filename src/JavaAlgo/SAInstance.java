package JavaAlgo;

import java.util.Random;

interface SAInstance {
    float getCost();

    boolean init(Random rd);

    void genNeighbor(Random rd);

    float getNeighborCost();

    float getProbFactor();

    void update();
}
