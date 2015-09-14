package org.matrix.seq;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.matrix.common.Matrix;

public class TestMatrixLURow {

	MatrixSeq m1,m11,m2,m3;
	
	@Before
	public void setUp() throws Exception {
		m1 = new MatrixSeq(3,3);
		double[][] row = {{3,2,7},{2,3,1},{3,4,1}};
		m1.setRow(row[0], 0);
		m1.setRow(row[1], 1);
		m1.setRow(row[2], 2);
		
		m11 = new MatrixSeq(3,3);
		double[][] am11 = {{3,2,7},{2,3,1},{3,4,1}};
		m11.setRow(am11[0], 0);
		m11.setRow(am11[1], 1);
		m11.setRow(am11[2], 2);
		
		m2 = new MatrixSeq(3,3);
		double[][] m = {{3,2,5},{7,6,3}, {9,1,7}};
		m2.setRow(m[0], 0);
		m2.setRow(m[1], 1);
		m2.setRow(m[2], 2);
	
		m3 = new MatrixSeq(3,3);
		double[][] a3 = {{5,2,3},{7,6,3}, {9,1,7}};
		m3.setRow(a3[0], 0);
		m3.setRow(a3[1], 1);
		m3.setRow(a3[2], 2);
	}
	
	 String getFile(String fileName) {
		StringBuilder result = new StringBuilder("");
		 
		//Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		 
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}
		 
			scanner.close();
		} catch (IOException e) {
				e.printStackTrace();
		}
		 
		return result.toString();		 
	 }
	
	public MatrixSeq applyPermutation(Matrix A, Matrix p){
		MatrixSeq Ap = new MatrixSeq(A.getNumRows(),A.getNumColumns());
		
		for(int i=0;i<A.getNumRows();i++){
			int row = (int) p.getElem(i, 0);
			for(int j=0;j<A.getNumColumns();j++){
				Ap.setElem(i, j, A.getElem(row, j));
			}
		}
		
		return Ap;
	}
	
	public MatrixSeq[] getLU(Matrix A, boolean isLDiag){
		MatrixSeq L = new MatrixSeq(A.getNumRows(),A.getNumColumns());
		MatrixSeq U =  new MatrixSeq(A.getNumRows(),A.getNumColumns());
		
		for(int i=0; i<A.getNumRows(); i++){
			for(int j=0; j<A.getNumColumns(); j++){
				if(i>j){
					L.setElem(i, j, A.getElem(i, j));
				} else if(j>i){
					U.setElem(i, j, A.getElem(i, j));
				} else{
					if(isLDiag){
						L.setElem(i, j, A.getElem(i, j));
						U.setElem(i, j, 1);
					} else{
						U.setElem(i, j, A.getElem(i, j));
						L.setElem(i, j, 1);
					}
				}
			}
		}
		
		
		MatrixSeq[] ret = new MatrixSeq[2];
		ret[0] = L;
		ret[1] = U;
		
		return ret;
	}
	
	@Test
	public void testLURow(){ 
		m1.setOpLU(new LUDecomposeSeqRow());
		Matrix[] combLU = m1.LUDecompose();
		if(combLU != null){
			System.out.println("ORIGNAL MATRIX");
			System.out.println(m11.toString());
			System.out.println("COMBINED LU");
			System.out.println(combLU[0].toString());
			System.out.println("PERMUTATION");
			System.out.println(combLU[1].toString());
		}
		
		MatrixSeq Ap = applyPermutation(m11,combLU[1]);
		MatrixSeq[] LU = getLU(combLU[0], true);
		System.out.println(LU[0].toString());
		System.out.println(LU[1].toString());
		MatrixSeq LUMult = (MatrixSeq) LU[0].multiply(LU[1]);
		System.out.println();
		System.out.println("PERMUTATED ORIGNAL MATRIX");
		System.out.println(Ap.toString());
		System.out.println("Multiplied LU");
		System.out.println(LUMult.toString());
		if(!Ap.equals(LUMult)){			

			fail("Product of LU doesn't equal A");
		}
		
	}
	
	@Test
	public void testLURow_3_3(){
		System.out.println("Starting Test LURow_3_3");
		String mstr = getFile("3_3_A");
		
		MatrixSeq orig = new MatrixSeq(3,3);
		orig.setAllElem(mstr);
		
		MatrixSeq m = new MatrixSeq(3,3);		
		m.setAllElem(mstr);
		
		m.setOpLU(new LUDecomposeSeqRow());
		Matrix[] combLU = m.LUDecompose();
		if(combLU == null){
			fail("LU decomposition on singular matrix or non-square matrix");
		}
		
		MatrixSeq Ap = applyPermutation(orig,combLU[1]);
		MatrixSeq[] LU = getLU(combLU[0], true);
		System.out.println(LU[0].toString());
		System.out.println(LU[1].toString());
		MatrixSeq LUMult = (MatrixSeq) LU[0].multiply(LU[1]);
		System.out.println();
		System.out.println("PERMUTATED ORIGNAL MATRIX");
		System.out.println(Ap.toString());
		System.out.println("Multiplied LU");
		System.out.println(LUMult.toString());
		if(!Ap.equals(LUMult)){			
			fail("LURow_3_3 failed: Product of LU doesn't equal A");
		}
		System.out.println("END: LURow_3_3");
		
	}
	
	@Test
	public void testLURow_4_4(){
		System.out.println("START: LURow_4_4");
		String mstr = getFile("4_4_A");
		
		MatrixSeq orig = new MatrixSeq(4,4);
		orig.setAllElem(mstr);
		
		MatrixSeq m = new MatrixSeq(4,4);		
		m.setAllElem(mstr);
		
		m.setOpLU(new LUDecomposeSeqRow());
		Matrix[] combLU = m.LUDecompose();
		if(combLU == null){
			fail("LU decomposition on singular matrix or non-square matrix");
		}
		
		MatrixSeq Ap = applyPermutation(orig,combLU[1]);
		MatrixSeq[] LU = getLU(combLU[0], true);
		System.out.println(LU[0].toString());
		System.out.println(LU[1].toString());
		MatrixSeq LUMult = (MatrixSeq) LU[0].multiply(LU[1]);
		System.out.println();
		System.out.println("PERMUTATED ORIGNAL MATRIX");
		System.out.println(Ap.toString());
		System.out.println("Multiplied LU");
		System.out.println(LUMult.toString());
		if(!Ap.equals(LUMult)){			
			fail("LURow_4_4 failed: Product of LU doesn't equal A");
		}
		System.out.println("END: LURow_4_4");
		
	}
	
	@Test
	public void testLURow_10_10(){
		System.out.println("START: LURow_10_10");
		String mstr = getFile("10_10_A");
		
		MatrixSeq orig = new MatrixSeq(10,10);
		orig.setAllElem(mstr);
		
		MatrixSeq m = new MatrixSeq(10,10);		
		m.setAllElem(mstr);
		
		m.setOpLU(new LUDecomposeSeqRow());
		Matrix[] combLU = m.LUDecompose();
		if(combLU == null){
			fail("LU decomposition on singular matrix or non-square matrix");
		}
		
		MatrixSeq Ap = applyPermutation(orig,combLU[1]);
		MatrixSeq[] LU = getLU(combLU[0], true);
		System.out.println(LU[0].toString());
		System.out.println(LU[1].toString());
		MatrixSeq LUMult = (MatrixSeq) LU[0].multiply(LU[1]);
		System.out.println();
		System.out.println("PERMUTATED ORIGNAL MATRIX");
		System.out.println(Ap.toString());
		System.out.println("Multiplied LU");
		System.out.println(LUMult.toString());
		if(!Ap.equals(LUMult)){			
			fail("LURow_10_10 failed: Product of LU doesn't equal A");
		}
		System.out.println("END: LURow_10_10");
		
	}
	
	@Test
	public void testLURow_100_100(){
		System.out.println("START: LURow_100_100");
		String mstr = getFile("100_100_M1");
		
		MatrixSeq orig = new MatrixSeq(100,100);
		orig.setAllElem(mstr);
		
		MatrixSeq m = new MatrixSeq(100,100);		
		m.setAllElem(mstr);
		
		m.setOpLU(new LUDecomposeSeqRow());
		Matrix[] combLU = m.LUDecompose();
		if(combLU == null){
			fail("LU decomposition on singular matrix or non-square matrix");
		}
		
		MatrixSeq Ap = applyPermutation(orig,combLU[1]);
		MatrixSeq[] LU = getLU(combLU[0], true);
		//System.out.println(LU[0].toString());
		//System.out.println(LU[1].toString());
		MatrixSeq LUMult = (MatrixSeq) LU[0].multiply(LU[1]);
		/*System.out.println();
		System.out.println("PERMUTATED ORIGNAL MATRIX");
		System.out.println(Ap.toString());
		System.out.println("Multiplied LU");
		System.out.println(LUMult.toString());*/
		if(!Ap.equals(LUMult)){			
			fail("LURow_100_100 failed: Product of LU doesn't equal A");
		}
		System.out.println("END: LURow_100_100");
	}
	
	@Test
	public void testLURow_1000_1000(){
		System.out.println("START: LURow_1000_1000");
		String mstr = getFile("1000_1000_A");
		
		MatrixSeq orig = new MatrixSeq(1000,1000);
		orig.setAllElem(mstr);
		
		MatrixSeq m = new MatrixSeq(1000,1000);		
		m.setAllElem(mstr);
		
		m.setOpLU(new LUDecomposeSeqRow());
		Matrix[] combLU = m.LUDecompose();
		if(combLU == null){
			fail("LU decomposition on singular matrix or non-square matrix");
		}
		
		MatrixSeq Ap = applyPermutation(orig,combLU[1]);
		MatrixSeq[] LU = getLU(combLU[0], true);
		//System.out.println(LU[0].toString());
		//System.out.println(LU[1].toString());
		MatrixSeq LUMult = (MatrixSeq) LU[0].multiply(LU[1]);
		/*System.out.println();
		System.out.println("PERMUTATED ORIGNAL MATRIX");
		System.out.println(Ap.toString());
		System.out.println("Multiplied LU");
		System.out.println(LUMult.toString());*/
		if(!Ap.equals(LUMult)){			
			fail("LURow_1000_1000 failed: Product of LU doesn't equal A");
		}
		System.out.println("END: LURow_1000_1000");
	}
	
	/*@Test
	public void testLUColumn(){ 
		m1.setOpLU(new LUDecomposeSeqColumn());
		Matrix[] LU = m1.LUDecompose();
		if(LU != null){
			System.out.println(m1.toString());
			System.out.println(LU[0].toString());
			System.out.println(LU[1].toString());
		}
	}*/

}
