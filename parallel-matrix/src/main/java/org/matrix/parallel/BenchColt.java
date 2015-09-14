package org.matrix.parallel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

import cern.colt.matrix.tdouble.algo.DenseDoubleAlgebra;
import cern.colt.matrix.tdouble.algo.decomposition.DenseDoubleLUDecomposition;
import cern.colt.matrix.tdouble.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.tdouble.DoubleMatrix2D;

public class BenchColt {
	
	public static DoubleMatrix2D readColtMatrixFromFile(String filename) {
		DoubleMatrix2D thisMatrix=null;
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
				thisMatrix = new DenseDoubleMatrix2D(rows,columns);
			    int i=0;
			    while((line = br.readLine()) != null) {
			    	st = new StringTokenizer(line, " ");
			    	int j=0;
			    	while(st.hasMoreTokens() && j < columns) {
			    			thisMatrix.set(i,j,(Double.parseDouble(st.nextToken().trim())));
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
	
	public static void main(String[] args) {
		
// Multiply Benchmarks	
		DoubleMatrix2D matrix = readColtMatrixFromFile(".\\src\\test\\resources\\10_10_A");
		DoubleMatrix2D matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\10_10_B");
		DenseDoubleAlgebra algebra = new DenseDoubleAlgebra();
		Long start = System.currentTimeMillis();
		DoubleMatrix2D result = algebra.mult(matrix,matrix2);
		Long end = System.currentTimeMillis();
		Long time = end - start;
		System.out.println("Time for colt multiply 10x10 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\100_100_M1");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\100_100_M2");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 100x100 is: " + time);

		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\200_200_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\200_200_B");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 200x200 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\300_300_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\300_300_B");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 300x300 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\500_500_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\500_500_B");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 500x500 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\1000_1000_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\1000_1000_B");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 1000x1000 is: " + time);
// Multiply x2 Benchmarks	
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\2_2_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\2_2_B");
		algebra = new DenseDoubleAlgebra();
		 start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 2x2 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\4_4_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\4_4_B");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 4x4 is: " + time);

		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\8_8_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\8_8_A");
		algebra = new DenseDoubleAlgebra();
		 start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 8x8 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\16_16_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\16_16_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 16x16 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\32_32_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\32_32_A");
		algebra = new DenseDoubleAlgebra();
		 start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 32x32 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\64_64_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\64_64_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 64x64 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\128_128_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\128_128_A");
		algebra = new DenseDoubleAlgebra();
		 start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 128x128 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\256_256_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\256_256_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 256x256 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\512_512_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\512_512_A");
		algebra = new DenseDoubleAlgebra();
		 start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 512x512 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\1024_1024_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\1024_1024_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 1024x1024 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\2048_2048_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\2048_2048_A");
		algebra = new DenseDoubleAlgebra();
		 start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 2048x2048 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\4096_4096_A");
		matrix2 = readColtMatrixFromFile(".\\src\\test\\resources\\4096_4096_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		result = algebra.mult(matrix,matrix2);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt multiply 4096x4096 is: " + time);
		
//LU Benchmarks
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\2_2_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		DenseDoubleLUDecomposition resultLu = algebra.lu(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt lu 2x2 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\4_4_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultLu = algebra.lu(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt lu 4x4 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\8_8_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultLu = algebra.lu(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt lu 8x8 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\16_16_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultLu = algebra.lu(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt lu 16x16 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\32_32_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultLu = algebra.lu(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt lu 32x32 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\64_64_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultLu = algebra.lu(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt lu 64x64 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\128_128_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultLu = algebra.lu(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt lu 128x128 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\256_256_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultLu = algebra.lu(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt lu 256x256 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\512_512_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultLu = algebra.lu(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt lu 512x512 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\1024_1024_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultLu = algebra.lu(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt lu 1024x1024 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\2048_2048_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultLu = algebra.lu(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt lu 2048x2048 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\4096_4096_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultLu = algebra.lu(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt lu 4096x4096 is: " + time);
		
//Det Benchmarks
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\2_2_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		Double resultDet = algebra.det(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt lu 2x2 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\4_4_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultDet = algebra.det(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt det 4x4 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\16_16_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultDet = algebra.det(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt det 16x16 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\32_32_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultDet = algebra.det(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt det 32x32 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\64_64_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultDet = algebra.det(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt det 64x64 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\128_128_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultDet = algebra.det(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt det 128x128 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\256_256_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultDet = algebra.det(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt det 256x256 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\512_512_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultDet = algebra.det(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt det 512x512 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\1024_1024_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultDet = algebra.det(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt det 1024x1024 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\2048_2048_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultDet = algebra.det(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt det 2048x2048 is: " + time);
		
		matrix = readColtMatrixFromFile(".\\src\\test\\resources\\4096_4096_A");
		algebra = new DenseDoubleAlgebra();
		start = System.currentTimeMillis();
		resultDet = algebra.det(matrix);
		end = System.currentTimeMillis();
		time = end - start;
		System.out.println("Time for colt det 4096x4096 is: " + time);
	}
	
	
}
