import sys
import os
import json

print(sys.argv)


resultDir = sys.argv[1]

resultS = ""

print("Summary results from {} ".format(resultDir))
print("Files from {}: ({}) {}".format(resultDir,size(os.listdir(resultDir)), os.listdir(resultDir) ))
for file in os.listdir(resultDir):

	if str(file).startswith("results_") and str(file).endswith("json"):
		abpath = os.path.join(resultDir, file)

		with open(abpath) as f:
			data = json.load(f)
			resultS+= "|{}|{}|{}|{}|{}|\n".format(data["bugid"],data["flacoco_sol"],data["flacoco_time"],data["gzoltar_sol"],data["gzoltar_time"] )


outFileSummary = os.path.join(resultDir, "summary.md")
with open(outFileSummary, "a") as fout:
	fout.write(resultS)
	fout.close()

print(resultS)



