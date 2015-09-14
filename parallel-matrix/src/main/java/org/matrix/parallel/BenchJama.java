package org.matrix.parallel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

import org.jblas.Decompose.LUDecomposition;

import cern.colt.matrix.tdouble.DoubleMatrix2D;
import cern.colt.matrix.tdouble.algo.DenseDoubleAlgebra;
import Jama.Matrix;

public class BenchJama { 
	
	public static Matrix readJamaMatrixFromFile(String filename) {
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
		return new Matrix(thisMatrix);
	}
	
	public static void main(String[] args) {
		
		Matrix matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\10_10_A");
		Matrix matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\10_10_B");
		Long start = System.currentTimeMillis();
		Matrix result = matrix.times(matrix2);
		Long end = System.currentTimeMillis();
		Long time = end - start;
		System.out.println("Time for Jama multiply 10x10 is: " + time);

		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\100_100_M1");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\100_100_M2");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 100x100 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\200_200_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\200_200_B");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 200x200 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\300_300_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\300_300_B");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 300x300 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\500_500_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\500_500_B");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 500x500 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\1000_1000_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\1000_1000_B");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 1000x1000 is: " + time);
		
//Multiply x2 Benchmarks
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\2_2_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\2_2_B");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 2x2 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\4_4_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\4_4_B");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 4x4 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\8_8_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\8_8_A");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 8x8 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\16_16_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\16_16_A");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 16x16 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\32_32_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\32_32_A");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 32x32 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\64_64_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\64_64_A");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 64x64 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\128_128_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\128_128_A");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 128x128 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\256_256_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\256_256_A");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 256x256 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\512_512_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\512_512_A");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 512x512 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\1024_1024_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\1024_1024_A");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 1024x1024 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\2048_2048_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\2048_2048_A");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 2048x2048 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\4096_4096_A");
		matrix2 = readJamaMatrixFromFile(".\\src\\test\\resources\\4096_4096_A");
		start = System.currentTimeMillis();
		result = matrix.times(matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama multiply 1024x1024 is: " + time);
		
//LU Benchmarks
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\2_2_A");
		start = System.currentTimeMillis();
		Jama.LUDecomposition resultLU = matrix.lu();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama LU 2x2 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\4_4_A");
		start = System.currentTimeMillis();
		resultLU = matrix.lu();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama LU 4x4 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\8_8_A");
		start = System.currentTimeMillis();
		resultLU = matrix.lu();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama LU 8x8 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\16_16_A");
		start = System.currentTimeMillis();
		resultLU = matrix.lu();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama LU 16x16 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\32_32_A");
		start = System.currentTimeMillis();
		resultLU = matrix.lu();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama LU 32x32 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\64_64_A");
		start = System.currentTimeMillis();
		resultLU = matrix.lu();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama LU 64x64 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\128_128_A");
		start = System.currentTimeMillis();
		resultLU = matrix.lu();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama LU 128x128 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\256_256_A");
		start = System.currentTimeMillis();
		resultLU = matrix.lu();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama LU 256x256 is: " + time);

		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\512_512_A");
		start = System.currentTimeMillis();
		resultLU = matrix.lu();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama LU 512x512 is: " + time);		
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\1024_1024_A");
		start = System.currentTimeMillis();
		resultLU = matrix.lu();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama LU 1024x1024 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\2048_2048_A");
		start = System.currentTimeMillis();
		resultLU = matrix.lu();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama LU 2048x2048 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\4096_4096_A");
		start = System.currentTimeMillis();
		resultLU = matrix.lu();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama LU 4096x4096 is: " + time);
		
//LU Benchmarks
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\2_2_A");
		start = System.currentTimeMillis();
		Double resultDet = matrix.det();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama Determinant 2x2 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\4_4_A");
		start = System.currentTimeMillis();
		resultDet = matrix.det();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama Determinant 4x4 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\8_8_A");
		start = System.currentTimeMillis();
		resultDet = matrix.det();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama Determinant 8x8 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\16_16_A");
		start = System.currentTimeMillis();
		resultDet = matrix.det();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama Determinant 16x16 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\32_32_A");
		start = System.currentTimeMillis();
		resultDet = matrix.det();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama Determinant 32x32 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\64_64_A");
		start = System.currentTimeMillis();
		resultDet = matrix.det();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama Determinant 64x64 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\128_128_A");
		start = System.currentTimeMillis();
		resultDet = matrix.det();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama Determinant 128x128 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\256_256_A");
		start = System.currentTimeMillis();
		resultDet = matrix.det();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama Determinant 256x256 is: " + time);

		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\512_512_A");
		start = System.currentTimeMillis();
		resultDet = matrix.det();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama Determinant 512x512 is: " + time);
		
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\1024_1024_A");
		start = System.currentTimeMillis();
		resultDet = matrix.det();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama Determinant 1024x1024 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\2048_2048_A");
		start = System.currentTimeMillis();
		resultDet = matrix.det();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama Determinant 2048x2048 is: " + time);
		
		matrix = readJamaMatrixFromFile(".\\src\\test\\resources\\4096_4096_A");
		start = System.currentTimeMillis();
		resultDet = matrix.det();
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for Jama Determinant 4096x4096 is: " + time);

	}
	
	

}
