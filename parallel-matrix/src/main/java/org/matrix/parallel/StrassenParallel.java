package org.matrix.parallel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.concurrent.*;
import java.util.*;

public class StrassenParallel {
	
	public StrassenParallel(){

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

	public static class GenerateQuadrant implements Callable<double[][]>{

		double[][] matrix;
		int subSize;
		int quadrant;

		public GenerateQuadrant(double[][] matrix, int subSize, int quadrant){
			this.matrix = matrix;
			this.subSize = subSize;
			this.quadrant = quadrant;
		}

		public double[][] call(){
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

	public static class MatrixMult implements Callable<double[][]>{

		int rowA;
		int colA;
		int rowB;
		int colB;
		double[][] matrixA;
		double[][] matrixB;
		double[][] matrixC;

		public MatrixMult(double[][] matrixA, double[][] matrixB){
			this.rowA = matrixA[0].length;
			this.colA = matrixA.length;
			this.rowB = matrixB[0].length;
			this.colB = matrixB.length;
			this.matrixA = matrixA;
			this.matrixB = matrixB;
			this.matrixC = new double[matrixA[0].length][matrixA[0].length];
		}

		public double[][] call(){

			for (int rA=0; rA<rowA; rA++){
    			for (int cA=0; cA<colA; cA++){
      				for (int cB=0; cB<colB; cB++){
        				matrixC[rA][cA] = matrixC[rA][cA] + matrixA[cB][cA] * matrixB[rA][cB];
      				}
    			}
  			}
  			return matrixC;
		}
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
		ExecutorService service = Executors.newCachedThreadPool();
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

		double[][] p1 = new double[subSize][subSize];
		double[][] p2 = new double[subSize][subSize];
		double[][] p3 = new double[subSize][subSize];
		double[][] p4 = new double[subSize][subSize];
		double[][] p5 = new double[subSize][subSize];
		double[][] p6 = new double[subSize][subSize];
		double[][] p7 = new double[subSize][subSize];

		List<Future<double[][]>> quadListA = new ArrayList<Future<double[][]>>();
		List<Future<double[][]>> quadListB = new ArrayList<Future<double[][]>>();
		List<Future<double[][]>> quadListC = new ArrayList<Future<double[][]>>();
		for (int i=1; i<5; i++){
			Future<double[][]> q1 = service.submit(new GenerateQuadrant(initA,subSize,i));
			quadListA.add(q1);
			Future<double[][]> q2 = service.submit(new GenerateQuadrant(initB,subSize,i));
			quadListB.add(q2);
		}
		try{
			a = quadListA.get(0).get();
			b = quadListA.get(1).get();
			c = quadListA.get(2).get();
			d = quadListA.get(3).get();
			e = quadListB.get(0).get();
			f = quadListB.get(1).get();
			g = quadListB.get(2).get();
			h = quadListB.get(3).get();
		} catch (Exception exc){
			exc.printStackTrace();
		}

		Future<double[][]> q3;
		q3 = service.submit(new MatrixMult(a,MatrixSubtract(f,h)));
		quadListC.add(q3);
		q3 = service.submit(new MatrixMult(MatrixAdd(a,b),h));
		quadListC.add(q3);
		q3 = service.submit(new MatrixMult(MatrixAdd(c,d),e));
		quadListC.add(q3);
		q3 = service.submit(new MatrixMult(d,MatrixSubtract(g,e)));
		quadListC.add(q3);
		q3 = service.submit(new MatrixMult(MatrixAdd(a,d),MatrixAdd(e,h)));
		quadListC.add(q3);
		q3 = service.submit(new MatrixMult(MatrixSubtract(b,d),MatrixAdd(g,h)));
		quadListC.add(q3);
		q3 = service.submit(new MatrixMult(MatrixSubtract(a,c),MatrixAdd(e,f)));
		quadListC.add(q3);

		try{
			p1 = quadListC.get(0).get();
			p2 = quadListC.get(1).get();
			p3 = quadListC.get(2).get();
			p4 = quadListC.get(3).get();
			p5 = quadListC.get(4).get();
			p6 = quadListC.get(5).get();
			p7 = quadListC.get(6).get();
		} catch (Exception exc){
			exc.printStackTrace();
		}

		quad1 = MatrixAdd(MatrixSubtract(MatrixAdd(p5,p4),p2),p6);
		quad2 = MatrixAdd(p1,p2);
		quad3 = MatrixAdd(p3,p4);
		quad4 = MatrixSubtract(MatrixSubtract(MatrixAdd(p1,p5),p3),p7);

		service.shutdownNow();
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