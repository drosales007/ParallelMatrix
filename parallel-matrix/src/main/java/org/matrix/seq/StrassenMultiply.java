package org.matrix.seq;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

public class StrassenMultiply {
	
	public StrassenMultiply(){

	}

	public static String MatrixToString(double[][] matrix){
		String x = "";
		for (int i=0; i<matrix[0].length; i++){
			for (int j=0; j<matrix[i].length; j++){
				x = x + matrix[j][i] + "  ";
			}
			x = x + "\n";
		}
		return x;
	}

	public static double[][] GenerateQuadrant(double[][] matrix, int subSize, int quadrant){
		int x;
		int y;
		double[][] quad = new double[subSize][subSize];
		if (quadrant==1){
			x = 0;
			y = 0;
		}
		else if (quadrant==2){
			x = subSize;
			y = 0;
		}
		else if (quadrant==3){
			x = 0;
			y = subSize;
		}
		else {
			x = subSize;
			y = subSize;
		}

		for (int i=0+x; i<subSize+x; i++){
			for (int j=0+y; j<subSize+y; j++){
				quad[i-x][j-y] = matrix[i][j];
			}
		}
		return quad;
	}

	public static double[][] CombineQuadrants(int size, double[][] quad1, double[][] quad2, double[][] quad3, double[][] quad4){
		double[][] combinedMatrix = new double[size][size];
		for (int i=0; i<size/2; i++){
			for (int j=0; j<size/2; j++){
				combinedMatrix[i][j] = quad1[i][j];
			}
		}
		for (int i=size/2; i<size; i++){
			for (int j=0; j<size/2; j++){
				combinedMatrix[i][j] = quad2[i-size/2][j];
			}
		}
		for (int i=0; i<size/2; i++){
			for (int j=size/2; j<size; j++){
				combinedMatrix[i][j] = quad3[i][j-size/2];
			}
		}
		for (int i=size/2; i<size; i++){
			for (int j=size/2; j<size; j++){
				combinedMatrix[i][j] = quad4[i-size/2][j-size/2];
			}
		}
		return combinedMatrix;
	}

	public static double[][] MatrixMult(double[][] matrixA, double[][] matrixB){
		int rowA = matrixA[0].length;
		int colA = matrixA.length;
		int colB = matrixB.length;
		double[][] matrixC = new double[matrixA[0].length][matrixA[0].length];

		for (int rA=0; rA<rowA; rA++){
    		for (int cA=0; cA<colA; cA++){
      			for (int cB=0; cB<colB; cB++){
        			matrixC[rA][cA] = matrixC[rA][cA] + matrixA[cB][cA] * matrixB[rA][cB];
      			}
    		}
  		}
  		return matrixC;
	}

	public static double[][] MatrixAdd(double[][] matrixA, double[][] matrixB){
		double[][] matrixC = new double[matrixA.length][matrixA[0].length];
		for (int i=0; i<matrixA.length; i++){
			for (int j=0; j<matrixA[0].length; j++){
				matrixC[i][j] = matrixA[i][j] + matrixB[i][j];
			}
		}
		return matrixC;
	}

	public static double[][] MatrixSubtract(double[][] matrixA, double[][] matrixB){
		double[][] matrixC = new double[matrixA.length][matrixA[0].length];
		for (int i=0; i<matrixA.length; i++){
			for (int j=0; j<matrixA[0].length; j++){
				matrixC[i][j] = matrixA[i][j] - matrixB[i][j];
			}
		}
		return matrixC;
	}

	public static double[][] Multiply(double[][] initA, double[][] initB){
		double[][] prod;
		int size = initA.length;

		int subSize = size/2;
		double[][] quad1 = new double[subSize][subSize];
		double[][] quad2 = new double[subSize][subSize];
		double[][] quad3 = new double[subSize][subSize];
		double[][] quad4 = new double[subSize][subSize];

		double[][] a = new double[subSize][subSize];
		double[][] b = new double[subSize][subSize];
		double[][] c = new double[subSize][subSize];
		double[][] d = new double[subSize][subSize];
		double[][] e = new double[subSize][subSize];
		double[][] f = new double[subSize][subSize];
		double[][] g = new double[subSize][subSize];
		double[][] h = new double[subSize][subSize];

		a = GenerateQuadrant(initA,subSize,1);
		b = GenerateQuadrant(initA,subSize,2);
		c = GenerateQuadrant(initA,subSize,3);
		d = GenerateQuadrant(initA,subSize,4);
		e = GenerateQuadrant(initB,subSize,1);
		f = GenerateQuadrant(initB,subSize,2);
		g = GenerateQuadrant(initB,subSize,3);
		h = GenerateQuadrant(initB,subSize,4);

		double[][] p1 = MatrixMult(a,MatrixSubtract(f,h));
		double[][] p2 = MatrixMult(MatrixAdd(a,b),h);
		double[][] p3 = MatrixMult(MatrixAdd(c,d),e);
		double[][] p4 = MatrixMult(d,MatrixSubtract(g,e));
		double[][] p5 = MatrixMult(MatrixAdd(a,d),MatrixAdd(e,h));
		double[][] p6 = MatrixMult(MatrixSubtract(b,d),MatrixAdd(g,h));
		double[][] p7 = MatrixMult(MatrixSubtract(a,c),MatrixAdd(e,f));

		quad1 = MatrixAdd(MatrixSubtract(MatrixAdd(p5,p4),p2),p6);
		quad2 = MatrixAdd(p1,p2);
		quad3 = MatrixAdd(p3,p4);
		quad4 = MatrixSubtract(MatrixSubtract(MatrixAdd(p1,p5),p3),p7);

		prod = CombineQuadrants(size,quad1,quad2,quad3,quad4);
		return prod;
	}

	public static double[][] readStrassenMatrixFromFile(String filename) {
		double[][] thisMatrix = null;
		try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
		    String line=null;
		    int rows=0;
		    int columns=0;
		    StringTokenizer st = null;
    		try {
			    if((line = br.readLine()) != null) {
			    	st = new StringTokenizer(line, " ");	
			    	rows = Integer.parseInt(st.nextToken());
			    	columns = Integer.parseInt(st.nextToken());
			    }
				thisMatrix = new double[rows][columns];
			    int i=0;
			    while((line = br.readLine()) != null) {
			    	st = new StringTokenizer(line, " ");
			    	int j=0;
			    	while(st.hasMoreTokens() && j < columns) {
			    			thisMatrix[i][j] = Integer.parseInt(st.nextToken().trim());
			    			j++;
			    	}
			    	i++;
			    }
			    br.close();
	    	} catch ( Exception e) {
	    			e.printStackTrace();
	    			br.close();
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return thisMatrix;
	}
	
	public static void main(String[] args){
		
		double[][] initA;
		double[][] initB;
		long startTime;
		long endTime;
		long executeTimeMS;
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\8_8_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\8_8_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 8x8: " + executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\16_16_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\16_16_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 16x16: " + executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\32_32_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\32_32_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 32x32: " + executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\64_64_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\64_64_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 64x64: " + executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\128_128_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\128_128_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 128x128: " + executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\256_256_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\256_256_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 256x256: " + executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\512_512_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\512_512_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 512x512: " + executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\1024_1024_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\1024_1024_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 1024x1024: " + executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\2048_2048_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\2048_2048_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 2048x2048: " + executeTimeMS);
        
        initA = readStrassenMatrixFromFile(".\\src\\test\\resources\\4096_4096_A");
        initB = readStrassenMatrixFromFile(".\\src\\test\\resources\\4096_4096_A");
		startTime = System.currentTimeMillis();
		Multiply(initA,initB);
		endTime = System.currentTimeMillis();
        executeTimeMS = endTime - startTime;
        System.out.println("Time for strassens 4096x4096: " + executeTimeMS);
	}
}