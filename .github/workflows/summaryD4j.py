import sys
import os
import json
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
print(sys.argv)



def mannWhitney(gzoltarTimes, flacocoTimes):
	print("mannWhitney: ")
	## https://machinelearningmastery.com/nonparametric-statistical-significance-tests-in-python/
	from scipy.stats import mannwhitneyu
	# compare samples
	stat, p = mannwhitneyu(gzoltarTimes, flacocoTimes)
	print('Statistics mannwhitneyu=%.3f, p=%.3f' % (stat, p))
	# interpret
	alpha = 0.05
	if p > alpha:
		print('Same distribution (fail to reject H0)')
	else:
		print('Different distribution (reject H0)')

def computeWilcoxon(gzoltarTimes, flacocoTimes):
	print("Number of samples {}".format(len(flacocoTimes)))
	#
	from scipy.stats import wilcoxon
	# compare samples
	print("wilcoxon: ")
	stat, p = wilcoxon(gzoltarTimes, flacocoTimes)
	print('Statistics wilcoxon=%.3f, p=%.3f' % (stat, p))
	# interpret
	alpha = 0.05
	if p > alpha:
		print('Same distribution (fail to reject H0)')
	else:
		print('Different distribution (reject H0)')

def checkNormalityOfData(data):

	from scipy import stats
	k2, p = stats.normaltest(data)
	alpha = 5e-2
	print("alpha {},  p = {:g}".format(alpha, p))
	if p < alpha:  # null hypothesis: x comes from a normal distribution
		print("The null hypothesis can be rejected (null hypothesis: x comes from a normal distribution)")
	else:
		print("The null hypothesis cannot be rejected (null hypothesis: x comes from a normal distribution)")
	plt.hist(data)
	plt.show()

def plotDistribution(gzoltarTimes, flacocoTimes):
	global ax
	ax = plt.gca()
	# plt.xlabel('X axis label')
	plt.ylabel('Time of Repair approach to find a patch (in Seconds)')
	plt.title('Comparison of Astor using GZoltar and Flacoco')
	# sns.boxplot(x="variable", y="value", data=pd.melt(df))
	data_group1 = [gzoltarTimes, flacocoTimes]
	plt.boxplot(data_group1, labels=['Gzoltar', 'Flacoco'])
	plt.violinplot(data_group1)
	plt.show()



def plotScatterCorrelation(gzoltarTimes, flacocoTimes):
	global ax
	ax = plt.gca()
	# plt.xlabel('X axis label')
	plt.ylabel('Time of Repair approach to find a patch (in Seconds)')
	# plt.title('Comparison of Astor using GZoltar and Flacoco')
	##https://machinelearningmastery.com/how-to-use-correlation-to-understand-the-relationship-between-variables/
	from scipy.stats import spearmanr
	corr, pp = spearmanr(gzoltarTimes, flacocoTimes)
	print('Spearmans correlation: {}, {}'.format(corr, pp))
	plt.xlabel('Time of Repair approach using Gzoltar (in seconds)')
	plt.ylabel('Time of Repair approach using Flacoco (in seconds)')
	plt.scatter(gzoltarTimes, flacocoTimes)
	for i, txt in enumerate(names):
		ax.annotate(txt, (gzoltarTimes[i], flacocoTimes[i]))

	plt.show()




### Main
resultDir = sys.argv[1]
#| bugid  | Flacoco finds solution  | Flacoco time  |  Gzoltar finds solution | Gzoltar time  |
#|---|---|---|---|---|

resultS = " bugid  | Flacoco finds solution  | Flacoco time  |  Gzoltar finds solution | Gzoltar time  |\n"
resultS+= "|---|---|---|---|---|"
print("Summary results from {} ".format(resultDir))
print("Files from {}: ({}) {}".format(resultDir,len(os.listdir(resultDir)), os.listdir(resultDir) ))
flacocoTimes =  []
gzoltarTimes =  []

fasterFlacoco = []
fasterGzoltar = []
equalsFaster = []
names = []
for file in os.listdir(resultDir):

	if str(file).startswith("results_") and str(file).endswith("json"):
		abpath = os.path.join(resultDir, file)

		with open(abpath) as f:
			data = json.load(f)
			
			isRepairedFlacoco = data["flacoco_sol"]
			timeFlacoco =   data["flacoco_time"] if isRepairedFlacoco.strip().lower() == 'true'  else "-"
			isRepairedGzoltar = data["gzoltar_sol"]
			timeGzoltar = data["gzoltar_time"] if isRepairedGzoltar.strip().lower() == 'true'  else "-"


			resultS+= "|{}_{}|{}|{}|{}|{}|\n".format(data["bugid"].strip(),data["approach"].strip(),isRepairedFlacoco,timeFlacoco,isRepairedGzoltar, timeGzoltar )

			if timeFlacoco is not "-" and timeGzoltar is not "-":

				flacocoTimes.append(timeFlacoco)
				gzoltarTimes.append(timeGzoltar)
				names.append(data["bugid"].strip()+"_"+data["approach"].strip() )
				if timeGzoltar < timeFlacoco:
					fasterGzoltar.append(data["bugid"])
				elif timeGzoltar > timeFlacoco:
					fasterFlacoco.append(data["bugid"])
				else:
					equalsFaster.append(data["bugid"])

outFileSummary = os.path.join(resultDir, "summary.md")
with open(outFileSummary, "a") as fout:
	fout.write(resultS)
	fout.close()



print("Gzoltar Normality")
checkNormalityOfData(gzoltarTimes)
print("Flacoco Normality")
checkNormalityOfData(flacocoTimes)

print("Faster gzoltar {}".format(len(fasterGzoltar)))
print("Faster flacoco {}".format(len(fasterFlacoco)))
print("equals{}".format(len(equalsFaster)))


mannWhitney(gzoltarTimes, flacocoTimes)
computeWilcoxon(gzoltarTimes, flacocoTimes)
plotScatterCorrelation(gzoltarTimes, flacocoTimes)
plotDistribution(gzoltarTimes, flacocoTimes)

print(resultS)



