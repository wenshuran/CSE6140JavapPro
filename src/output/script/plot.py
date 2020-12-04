import matplotlib.pyplot as plt 
import os 
import glob 
import numpy as np
import sys

ALGOS = ["LS1", "LS2"]

GRAPHS = ["power", "star2", "jazz", "dummy1", "dummy2", "karate", 
            "football", "delaunay_n10", "email", "netscience", "star", "hep-th", "as-22july06"]

OPTSOL = {"power": 2203, "star2": 4542, "jazz": 158, "dummy1": 2, "dummy2":3, "karate": 14, 
            "football": 94, "delaunay_n10": 703, "email": 594, "netscience": 899, "star": 6902, "hep-th":3926, "as-22july06":3303}

SELECTED_GRAPHS = ["dummy1", "dummy2", "karate", 
            "football", "jazz", "delaunay_n10", "email", "netscience",  "power", "hep-th", "star",  "star2",  "as-22july06"]

TIME_CKPT = [1, 2, 4, 8, 12, 16, 32, 64, 128, 512, 1024, 4096, 1e6]
QUALITY_CKPT = [1.0, 0.75, 0.5, 0.25, 0.2, 0.15, 0.1, 0.05, 0.025, 1e-5]

os.system('javac main/java/*.java')

regen = 0 if len(sys.argv) < 2 else int(sys.argv[1])

error = {}

log = {}

for algo in ALGOS:
    error[algo] = []
    log[algo] = []
    for graph in SELECTED_GRAPHS:

        root_path = f'output/{algo}_{graph}'
        if not os.path.exists(root_path):
            os.makedirs(root_path)

        
        if regen == 1:
            for f in glob.glob(f'{root_path}/*.*'):
                os.remove(f)

        if len(os.listdir(root_path)) == 0:
            os.system(f'java main.java.JavaAlgo -inst {graph}.graph -alg {algo} -time 60 -seed 6140')
        
        metricMat = np.zeros([len(TIME_CKPT), len(QUALITY_CKPT)])
        solList = glob.glob(f'{root_path}/*.sol')
        traceList = glob.glob(f'{root_path}/*.trace')

        num_instance = len(traceList)
                
        # print(len(solList))
        err_sum = 0
        time_sum = 0
        opt_sum = 0
        opt_log = []
        for trace in traceList:
            cached_ti = 0
            cached_qi = 0
            localMat = np.zeros([len(TIME_CKPT), len(QUALITY_CKPT)])
            f = open(trace, 'r')
            traceData = f.readlines()
            error_loc = 0
            time_loc = 0
            opt_loc = 0
            # print(len(traceData))
            for record in traceData:
                t = float(record.split(' ')[0][:-1]) * 1000
                q = float(record.split(' ')[1]) / (1.0 * OPTSOL[graph]) - 1.0
                error_loc = q
                time_loc = t
                opt_loc = float(record.split(' ')[1])
                ti = cached_ti
                while ti < len(TIME_CKPT):
                    if TIME_CKPT[ti] > t:
                        break 
                    ti += 1

                qi = cached_qi
                while qi < len(QUALITY_CKPT):
                    if QUALITY_CKPT[qi] < q:
                        break 
                    qi += 1
                
                cached_ti = ti 
                cached_qi = qi
                
                localMat[ti:, :qi] = 1
            metricMat += localMat
            err_sum += error_loc
            time_sum += time_loc
            opt_sum += opt_loc
            opt_log.append(opt_loc)

        err_sum /= num_instance
        time_sum /= num_instance
        opt_sum /= num_instance

        log[algo].append(opt_log)
        # print(f"Matric for algo: {algo} graph: {graph}:")
        # print(metricMat)
        # print(f"OPT: {opt_sum}, ERR: {err_sum}, TIME: {time_sum}")


        def drawQRTD(metricMat):
            x = np.array(TIME_CKPT)
            ys = np.zeros([len(QUALITY_CKPT), len(TIME_CKPT)])

            for qi in range(len(QUALITY_CKPT)):
                ys[qi] = metricMat[:, qi] / num_instance
            
            # ys = np.flip(ys, axis=0)
            ys = ys[1 : -1, :-3]
            x = x[:-3]

            fig, ax = plt.subplots()  # Create a figure and an axes.
            for i in range(ys.shape[0]):
                ax.plot(x, ys[i, :], label=f'Q={QUALITY_CKPT[i+1]}')  # Plot some data on the axes.
            ax.set_xlabel('Elapsed Time / ms')  # Add an x-label to the axes.
            ax.set_ylabel('P(solve)')  # Add a y-label to the axes.
            ax.set_title(f"Qualified RTDs for {algo} on {graph}")  # Add a title to the axes.
            ax.legend()  # Add a legend.

            plt.savefig(f'output/plots/{graph}_{algo}_QRTD.png')
            plt.clf()
        
        def drawSQD(metricMat):
            x = np.array(QUALITY_CKPT)
            ys = np.zeros([len(TIME_CKPT), len(QUALITY_CKPT)])
            x = x[1:-1]

            for ti in range(len(TIME_CKPT)):
                ys[ti] = metricMat[ti, :] / num_instance
            
            # ys = np.flip(ys, axis=1)
            ys = ys[:-2, 1:-1]
            fig, ax = plt.subplots()  # Create a figure and an axes.
            for i in range(ys.shape[0]):
                ax.plot(x, ys[i, :], label=f'T={TIME_CKPT[i]} ms')  # Plot some data on the axes.
            ax.set_xlabel('relative solution quality / %')  # Add an x-label to the axes.
            ax.set_ylabel('P(solve)')  # Add a y-label to the axes.
            ax.set_title(f"Solution quality distribution for {algo} on {graph}")  # Add a title to the axes.
            ax.legend()  # Add a legend.

            plt.savefig(f'output/plots/{graph}_{algo}_SQD.png')
            plt.clf()


        drawSQD(metricMat)
        drawQRTD(metricMat)

        error[algo].append(err_sum)

print(error)

print(log)

def drawErrComp(error):
    x = np.array(SELECTED_GRAPHS)
    x = np.flip(x)

    fig, ax = plt.subplots()  # Create a figure and an axes.
    for algo in ALGOS:
        ax.barh(x, np.flip(error[algo]), label=f'{algo}')  # Plot some data on the axes.
    ax.set_xlabel('Graphs')  # Add an x-label to the axes.
    ax.set_ylabel('Relative solution quality / %')  # Add a y-label to the axes.
    ax.set_title(f"Relative Solution Quality Comparison")  # Add a title to the axes.
    ax.legend()  # Add a legend.
    plt.tight_layout()
    plt.savefig(f'output/plots/comp.png')
    plt.clf()

drawErrComp(error)

def drawBox(log):

    for gi in range(len(SELECTED_GRAPHS)):
        
        data = []

        labels = []

        for algo in ALGOS:

            data.append(log[algo][gi])
            labels.append(algo)
    
        fig, ax = plt.subplots()  # Create a figure and an axes.
        ax.boxplot(data, labels=labels)
        ax.set_xlabel('Algorithms')  # Add an x-label to the axes.
        ax.set_ylabel('VC size')  # Add a y-label to the axes.
        ax.set_title(f"Box Plot for Results on {SELECTED_GRAPHS[gi]}")  # Add a title to the axes.

        plt.savefig(f'output/plots/{SELECTED_GRAPHS[gi]}_Box.png')
        plt.clf()

drawBox(log)