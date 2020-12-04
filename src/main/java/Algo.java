package main.java;

import java.io.FileNotFoundException;

public interface Algo {
    public void run(String filename, String outSol, String outTrace, int time, int seed) throws FileNotFoundException;
}
