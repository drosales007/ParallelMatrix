package org.matrix.parallel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

import org.jblas.Decompose;
import org.jblas.DoubleMatrix;

import Jama.Matrix;

public class BenchJBLAS {
	
	public static DoubleMatrix readJamaMatrixFromFile(String filename) {
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
			    			thisMatrix[i][j] = Double.parseDouble(st.nextToken().trim());
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
		return new DoubleMatrix(thisMatrix);
	}

	  public static void main(String[] args) {
		    DoubleMatrix A = readJamaMatrixFromFile(".\\src\\test\\resources\\10_10_A");
		    DoubleMatrix B = readJamaMatrixFromFile(".\\src\\test\\resources\\10_10_B");
			Long start = System.currentTimeMillis();
			DoubleMatrix result = A.mmul(B);
			Long end = System.currentTimeMillis();
			Long time = end - start;
			System.out.println("Time for JBLAS multiply 10x10 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\100_100_M1");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\100_100_M2");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 100x100 is: " + time);

			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\200_200_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\200_200_B");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 200x200 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\300_300_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\300_300_B");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 300x300 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\500_500_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\500_500_B");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 500x500 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\1000_1000_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\1000_1000_B");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 1000x1000 is: " + time);
//Matrix Multiply x2
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\2_2_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\2_2_B");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 2x2 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\4_4_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\4_4_B");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 4x4 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\8_8_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\8_8_A");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 8x8 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\16_16_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\16_16_A");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 16x16 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\32_32_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\32_32_A");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 32x32 is: " + time);

		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\64_64_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\64_64_A");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 64x64 is: " + time);

		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\128_128_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\128_128_A");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 128x128 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\256_256_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\256_256_A");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 256x256 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\512_512_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\512_512_A");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 512x512 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\1024_1024_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\1024_1024_A");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 1024x1024 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\2048_2048_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\2048_2048_A");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 2048x2048 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\4096_4096_A");
		    B = readJamaMatrixFromFile(".\\src\\test\\resources\\4096_4096_A");
			start = System.currentTimeMillis();
			result = A.mmul(B);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS multiply 4096x4096 is: " + time);
			
			
//LU Decomp
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\2_2_A");
		    Decompose lucalc = new Decompose();
			start = System.currentTimeMillis();
			Decompose.LUDecomposition<DoubleMatrix> resultLu = lucalc.lu(A);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS LU 2x2 is: " + time);	

		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\4_4_A");
		    //Decompose lucalc = new Decompose();
			start = System.currentTimeMillis();
			resultLu = lucalc.lu(A);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS LU 4x4 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\8_8_A");
			start = System.currentTimeMillis();
			resultLu = lucalc.lu(A);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS LU 8x8 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\16_16_A");
		    //Decompose lucalc = new Decompose();
			start = System.currentTimeMillis();
			resultLu = lucalc.lu(A);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS LU 16x16 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\32_32_A");
			start = System.currentTimeMillis();
			resultLu = lucalc.lu(A);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS LU 32x32 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\64_64_A");
		    //Decompose lucalc = new Decompose();
			start = System.currentTimeMillis();
			resultLu = lucalc.lu(A);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS LU 64x64 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\128_128_A");
			start = System.currentTimeMillis();
			resultLu = lucalc.lu(A);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS LU 128x128 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\256_256_A");
		    //Decompose lucalc = new Decompose();
			start = System.currentTimeMillis();
			resultLu = lucalc.lu(A);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS LU 256x256 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\512_512_A");
			start = System.currentTimeMillis();
			resultLu = lucalc.lu(A);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS LU 512x512 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\1024_1024_A");
		    //Decompose lucalc = new Decompose();
			start = System.currentTimeMillis();
			resultLu = lucalc.lu(A);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS LU 1024x1024 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\2048_2048_A");
			start = System.currentTimeMillis();
			resultLu = lucalc.lu(A);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS LU 2048x2048 is: " + time);
			
		    A = readJamaMatrixFromFile(".\\src\\test\\resources\\4096_4096_A");
			start = System.currentTimeMillis();
			resultLu = lucalc.lu(A);
			end = System.currentTimeMillis();
			time = end - start;
			System.out.println("Time for JBLAS LU 4096x4096 is: " + time);

		  }
}
