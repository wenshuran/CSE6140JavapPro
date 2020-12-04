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

TIME_CKPT = [1, 2, 4, 8, 12, 16, 32, 64, 128, 512, 1024, 4096, 1e6]
QUALITY_CKPT = [1.0, 0.75, 0.5, 0.25, 0.2, 0.15, 0.1, 0.05, 0.04, 1e-5]

os.system('javac main/java/*.java')

regen = 0 if len(sys.argv) < 2 else int(sys.argv[1])

for algo in ALGOS:
    for graph in GRAPHS:

        root_path = f'output/{algo}_{graph}'
        if not os.path.exists(root_path):
            os.makedirs(root_path)

        
        if regen == 1:
            for f in glob.glob(f'{root_path}/*.*'):
                os.remove(f)

        if len(os.listdir(root_path)) == 0:
            os.system(f'java main.java.JavaAlgo -inst {graph}.graph -alg {algo} -time 60 -seed 6140')
        # try:
        metricMat = np.zeros([len(TIME_CKPT), len(QUALITY_CKPT)])
        solList = glob.glob(f'{root_path}/*.sol')
        traceList = glob.glob(f'{root_path}/*.trace')

        num_instance = len(traceList)
                
        # print(len(solList))
        err_sum = 0
        time_sum = 0
        opt_sum = 0
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

        err_sum /= num_instance
        time_sum /= num_instance
        opt_sum /= num_instance
        print(f"Matric for algo: {algo} graph: {graph}:")
        # print(metricMat)
        print(f"OPT: {opt_sum}, ERR: {err_sum}, TIME: {time_sum}")

        def drawRTD(metricMat):
            x = np.array(TIME_CKPT)
            ys = np.zeros([len(QUALITY_CKPT), len(TIME_CKPT)])

            for qi in range(len(QUALITY_CKPT)):
                ys[qi] = metricMat[:, qi] / num_instance
            
            ys = np.flip(ys, axis=0)
        
        def drawQRTO(metricMat):
            x = np.array(QUALITY_CKPT)
            ys = np.zeros([len(TIME_CKPT), len(QUALITY_CKPT)])

            for ti in range(len(TIME_CKPT)):
                ys[ti] = metricMat[ti, :] / num_instance
            
            ys = np.flip(ys, axis=1)


        
        drawRTD(metricMat)
        drawQRTO(metricMat)
        print("\n")


        # except:
        #     print(f"error for algo: {algo} graph: {graph}")