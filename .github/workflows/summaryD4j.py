import sys
import os
import json

print(sys.argv)


resultDir = sys.argv[1]

resultS = ""

print("Summary results from {} ".format(resultDir))
print("Files from {}: ({}) {}".format(resultDir,len(os.listdir(resultDir)), os.listdir(resultDir) ))
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


outFileSummary = os.path.join(resultDir, "summary.md")
with open(outFileSummary, "a") as fout:
	fout.write(resultS)
	fout.close()

print(resultS)



