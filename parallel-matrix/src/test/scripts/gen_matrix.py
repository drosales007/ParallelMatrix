import argparse
import random
import numpy.linalg
import scipy.linalg

file1="A"
file2="B"
file3="ADD_A_B"
file4="MULT_A_B"
file5="Inv_A"
file6="Inv_B"
file7="LU_A"
file8="LU_B"
file9="DET_A"
file10="DET_B"

maxROWA=10
maxCOLA=10
maxROWB=10
maxCOLB=10
maxROWA=4
maxCOLA=4
maxROWB=4
maxCOLB=4
maxDim=8192
noFloat=1

def main():

	d = 1
	while d < maxDim:
		#Generate Matrix A
		A = [[0 for col in range(d)] for row in range(d)]
		fname =  str(d) + "_" + str(d) +  "_" + file1  
		f = open(fname, 'w')
		f.write(str(d)+" "+str(d))
		f.write("\n")

		for r in range(0,d):
			for c in range(0,d):
				if noFloat > 0:
					val = random.randint(0,10)
				else:
					val = random.uniform(0.0,1000000.0)
				A[r][c] = val
				f.write(str(val)+" ");
		
			f.write("\n");
	
		f.close();

		#Generate Matrix B
		B = [[0 for col in range(d)] for row in range(d)]
		fname =  str(d) + "_" + str(d) + "_" + file2 
		f = open(fname, 'w')
		f.write(str(d)+" "+str(d))
		f.write("\n")

		for r in range(0,d):
			for c in range(0,d):
				if noFloat > 0:
					val = random.randint(0,10)
				else:
					val = random.uniform(0.0,1000000.0)
				B[r][c] = val
				f.write(str(val)+" ");
		
			f.write("\n");
		f.close();
		
		d = d*2

	#Generate A+B
	if (maxROWA != maxROWB) and (maxCOLA != maxCOLB):
		print "Addition can't be done on input"
	else:
		fname =  str(maxROWB) + "_" + str(maxCOLB) + "_" + file3 
		f = open(fname, 'w')
		f.write(str(maxROWA)+" "+str(maxCOLA))
		f.write("\n")

		for r in range(0,maxROWA):
			for c in range(0,maxCOLA):
				val = A[r][c] + B[r][c]
				f.write(str(val)+" ");
		
			f.write("\n");
		f.close();

	#Generate MULT
	if (maxCOLA != maxROWB):
		print "Multiplication can't be done for given input"
	else:
		fname =  str(maxCOLA) + "_" + str(maxROWB) +  "_" + file4  
		f = open(fname, 'w')
		f.write(str(maxROWA)+" "+str(maxCOLB))
		f.write("\n")
		for i in range(0,maxROWA):
			for j in range(0,maxCOLB):
				val = 0
				for c in range(0,maxCOLA): 
					val += A[i][c]*B[c][j];
				
				f.write(str(val)+" ");
		
		f.write("\n");
		f.close();

	#Generate Determinant A
	if(maxROWA != maxCOLA):
		print "Determinant can't be generated for A"
	else:
		array = list() 
		for i in range(0,maxROWA):
			rowEntry = list()
			for j in range(0,maxCOLA):
				rowEntry.append((A[i][j]))
			array.append(rowEntry)

		det = numpy.linalg.det(array)
		
		fname =   str(maxROWA) + "_" + str(maxCOLA) + "_" + file9
		f = open(fname, 'w')
		f.write("\n")
		f.write(str(det))
		f.write("\n")

	#Generate Determinant B
	if(maxROWB != maxCOLB):
		print "Determinant can't be generated for B"
	else:
		array = list() 
		for i in range(0,maxROWB):
			rowEntry = list()
			for j in range(0,maxCOLB):
				rowEntry.append((B[i][j]))
			array.append(rowEntry)

		det = numpy.linalg.det(array)
		
		fname =   str(maxROWB) + "_" + str(maxCOLB) + "_" + file10
		f = open(fname, 'w')
		f.write(str(maxROWB)+" "+str(maxCOLB))
		f.write("\n")
		f.write(str(det))
		f.write("\n")


	#Generate Inverse of A and B
	if(maxROWA != maxCOLA):
		print "Inverse can't be generated for A"
	else:
		array = list() 
		for i in range(0,maxROWA):
			rowEntry = list()
			for j in range(0,maxCOLA):
				rowEntry.append((A[i][j]))
			array.append(rowEntry)

		inv = numpy.linalg.inv(array)
		
		fname =   str(maxROWA) + "_" + str(maxCOLA) + "_" + file5
		f = open(fname, 'w')
		f.write(str(maxROWA)+" "+str(maxCOLA))
		f.write("\n")
		f.write(str(inv))
		f.write("\n")

	if(maxROWB != maxCOLB):
		print "Inverse can't be generated for B"
	else:
		array = list() 
		for i in range(0,maxROWB):
			rowEntry = list()
			for j in range(0,maxCOLB):
				rowEntry.append((B[i][j]))
			array.append(rowEntry)

		inv = numpy.linalg.inv(array)
		
		fname =   str(maxROWB) + "_" + str(maxCOLB) + "_" + file6
		f = open(fname, 'w')
		f.write(str(maxROWB)+" "+str(maxCOLB))
		f.write("\n")
		f.write(str(inv))
		f.write("\n")

	#Generate LU decomposition of A and B
	if(maxROWA != maxCOLA):
		print "Inverse can't be generated for A"
	else:
		array = list() 
		for i in range(0,maxROWA):
			rowEntry = list()
			for j in range(0,maxCOLA):
				rowEntry.append((A[i][j]))
			array.append(rowEntry)

		P,L,U = scipy.linalg.lu(array)
		fname =   str(maxROWA) + "_" + str(maxCOLA) + "_" + file7
		f = open(fname, 'w')
		f.write(str(maxROWA)+" "+str(maxCOLA))
		f.write("\n")
		f.write(str(P))
		f.write("\n")
		f.write(str(L))
		f.write("\n")
		f.write(str(U))
		f.write("\n")

		array = list() 
		for i in range(0,maxROWB):
			rowEntry = list()
			for j in range(0,maxCOLB):
				rowEntry.append((B[i][j]))
			array.append(rowEntry)

		P,L,U = scipy.linalg.lu(array)
		fname =   str(maxROWB) + "_" + str(maxCOLB) + "_" + file8
		f = open(fname, 'w')
		f.write(str(maxROWB)+" "+str(maxCOLB))
		f.write("\n")
		f.write(str(L))
		f.write("\n")
		f.write(str(U))
		f.write("\n")
		f.write(str(P))
		f.write("\n")

if __name__ == "__main__":
    main()

