package JavaAlgo;

import java.io.FileNotFoundException;

interface Algo {
    public void run(String filename, int time, int seed) throws FileNotFoundException;
}
