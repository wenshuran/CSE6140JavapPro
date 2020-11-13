package vc.algo;
import java.util.Random;

interface SAInstance {
    float getCost();
    void init(Random rd);
    void genNeighbor(Random rd);
    float getNeighborCost();
    float getProbFactor();
    void update();
}