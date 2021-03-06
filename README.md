# Minimum Vertex Cover
Java implementation of Gatech CSE6140 group project. All four algorithms are tested on Mac / Linux.

## Compile
At the project root directory run:
```
javac $(find . -name "*.java")
```

## Run MVC
Change directory to `/src`, then run `java main.java.JavaAlgo -inst <filename> -alg [BnB|Approx|LS1|LS2] -time <cutoff in seconds> -seed <random seed>`.

Note that  with BnB, you still have to provide a `-seed <random seed>` even though the seed will not be used at all.

An example shows below:

```bash
cd src    # now at /path/to/project/src/
java main.java.JavaAlgo -inst jazz.graph -alg LS2 -time 600 -seed 9
```
