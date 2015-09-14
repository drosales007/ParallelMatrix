package org.matrix.seq;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.matrix.common.Matrix;

public class TestMatrixLUColumn {
	
	@Before
	public void setUp() throws Exception {
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
	public void testLUColumn(){
		for(int i=1; i<4192; i=i*2){
			String s = i + "_" + i;
			System.out.println("START: Name=SeqLUColumn, Dimension=" + s);
			String mstr = getFile(s+"_A");
			
			MatrixSeq orig = new MatrixSeq(i,i);
			orig.setAllElem(mstr);
			
			MatrixSeq m = new MatrixSeq(i,i);		
			m.setAllElem(mstr);
			
			m.setOpLU(new LUDecomposeSeqColumn());
			
			long start = System.currentTimeMillis();
			Matrix[] combLU = m.LUDecompose();
			long end = System.currentTimeMillis();
			
			if(combLU == null){
				orig.toString();
				fail("LU decomposition on singular matrix or non-square matrix");
			}
			
			MatrixSeq Ap = applyPermutation(orig,combLU[1]);
			MatrixSeq[] LU = getLU(combLU[0], false);
			MatrixSeq LUMult = (MatrixSeq) LU[0].multiply(LU[1]);
	
			
			if(!Ap.equals(LUMult)){							
				System.out.println();
				System.out.println("LU DECOMPOSED");
				System.out.println(LU[0].toString());
				System.out.println(LU[1].toString());
				System.out.println("PERMUTED ORIGNAL MATRIX");
				System.out.println(Ap.toString());
				System.out.println("MULTIPLIED LU");
				System.out.println(LUMult.toString());
			}
			long t = end - start;
			System.out.println("END: Name=SeqLUColumn, Dimension=" + s + "Time(ms)="+t);
			System.out.println();
		}
	}
	
	/*@Test
	public void testLUColumn_2_2(){ 
		System.out.println("Starting Test LUColumn_2_2");
		String mstr = getFile("2_2_A");
		
		MatrixSeq orig = new MatrixSeq(2,2);
		orig.setAllElem(mstr);
		
		MatrixSeq m = new MatrixSeq(2,2);		
		m.setAllElem(mstr);
		
		m.setOpLU(new LUDecomposeSeqColumn());
		Matrix[] combLU = m.LUDecompose();
		if(combLU == null){
			fail("LU decomposition on singular matrix or non-square matrix");
		}
		
		MatrixSeq Ap = applyPermutation(orig,combLU[1]);
		MatrixSeq[] LU = getLU(combLU[0], false);
		MatrixSeq LUMult = (MatrixSeq) LU[0].multiply(LU[1]);

		
		if(!Ap.equals(LUMult)){			
			fail("LURow_3_3 failed: Product of LU doesn't equal A");
			System.out.println();
			System.out.println("LU Decomposed");
			System.out.println(LU[0].toString());
			System.out.println(LU[1].toString());
			System.out.println("PERMUTATED ORIGNAL MATRIX");
			System.out.println(Ap.toString());
			System.out.println("MULTIPLIED LU");
			System.out.println(LUMult.toString());
		}
		System.out.println("END: LUColumn_3_3");
		System.out.println();
		
	}
	
	@Test
	public void testLUColumn_3_3(){
		System.out.println("Starting Test LUColumn_3_3");
		String mstr = getFile("3_3_A");
		
		MatrixSeq orig = new MatrixSeq(3,3);
		orig.setAllElem(mstr);
		
		MatrixSeq m = new MatrixSeq(3,3);		
		m.setAllElem(mstr);
		
		m.setOpLU(new LUDecomposeSeqColumn());
		Matrix[] combLU = m.LUDecompose();
		if(combLU == null){
			fail("LU decomposition on singular matrix or non-square matrix");
		}
		
		MatrixSeq Ap = applyPermutation(orig,combLU[1]);
		MatrixSeq[] LU = getLU(combLU[0], false);
		MatrixSeq LUMult = (MatrixSeq) LU[0].multiply(LU[1]);

		if(!Ap.equals(LUMult)){			
			fail("LURow_3_3 failed: Product of LU doesn't equal A");
			System.out.println();
			System.out.println("LU Decomposed");
			System.out.println(LU[0].toString());
			System.out.println(LU[1].toString());
			System.out.println("PERMUTATED ORIGNAL MATRIX");
			System.out.println(Ap.toString());
			System.out.println("MULTIPLIED LU");
			System.out.println(LUMult.toString());
		}
		System.out.println("END: LUColumn_3_3");
		System.out.println();
		
	}
	
	@Test
	public void testLURow_4_4(){
		System.out.println("START: LUColumn_4_4");
		String mstr = getFile("4_4_A");
		
		MatrixSeq orig = new MatrixSeq(4,4);
		orig.setAllElem(mstr);
		
		MatrixSeq m = new MatrixSeq(4,4);		
		m.setAllElem(mstr);
		
		m.setOpLU(new LUDecomposeSeqColumn());
		Matrix[] combLU = m.LUDecompose();
		if(combLU == null){
			fail("LU decomposition on singular matrix or non-square matrix");
		}
		
		MatrixSeq Ap = applyPermutation(orig,combLU[1]);
		MatrixSeq[] LU = getLU(combLU[0], false);

		MatrixSeq LUMult = (MatrixSeq) LU[0].multiply(LU[1]);

		if(!Ap.equals(LUMult)){			
			fail("LUColumn_4_4 failed: Product of LU doesn't equal A");
			System.out.println("LU Decomposed");
			System.out.println(LU[0].toString());
			System.out.println(LU[1].toString());
			System.out.println();
			System.out.println("PERMUTATED ORIGNAL MATRIX");
			System.out.println(Ap.toString());
			System.out.println("Multiplied LU");
			System.out.println(LUMult.toString());
		}
		System.out.println("END: LUColumn_4_4");
		System.out.println();
		
	}
	
	@Test
	public void testLUColumn_10_10(){
		System.out.println("START: LUColumn_10_10");
		String mstr = getFile("10_10_A");
		
		MatrixSeq orig = new MatrixSeq(10,10);
		orig.setAllElem(mstr);
		
		MatrixSeq m = new MatrixSeq(10,10);		
		m.setAllElem(mstr);
		
		m.setOpLU(new LUDecomposeSeqColumn());
		Matrix[] combLU = m.LUDecompose();
		if(combLU == null){
			fail("LU decomposition on singular matrix or non-square matrix");
		}
		
		MatrixSeq Ap = applyPermutation(orig,combLU[1]);
		MatrixSeq[] LU = getLU(combLU[0], false);

		MatrixSeq LUMult = (MatrixSeq) LU[0].multiply(LU[1]);

		if(!Ap.equals(LUMult)){			
			fail("LUColumn_10_10 failed: Product of LU doesn't equal A");
			System.out.println("LU Decomposed");
			System.out.println(LU[0].toString());
			System.out.println(LU[1].toString());
			System.out.println();
			System.out.println("PERMUTATED ORIGNAL MATRIX");
			System.out.println(Ap.toString());
			System.out.println("Multiplied LU");
			System.out.println(LUMult.toString());
		}
		System.out.println("END: LUColumn_10_10");
		System.out.println();
		
	}
	
	@Test
	public void testLUColumn_100_100(){
		System.out.println("START: LUColumn_100_100");
		String mstr = getFile("100_100_M1");
		
		MatrixSeq orig = new MatrixSeq(100,100);
		orig.setAllElem(mstr);
		
		MatrixSeq m = new MatrixSeq(100,100);		
		m.setAllElem(mstr);
		
		m.setOpLU(new LUDecomposeSeqColumn());
		Matrix[] combLU = m.LUDecompose();
		if(combLU == null){
			fail("LU decomposition on singular matrix or non-square matrix");
		}
		
		MatrixSeq Ap = applyPermutation(orig,combLU[1]);
		MatrixSeq[] LU = getLU(combLU[0], false);

		MatrixSeq LUMult = (MatrixSeq) LU[0].multiply(LU[1]);

		if(!Ap.equals(LUMult)){			
			fail("LUColumn_100_100 failed: Product of LU doesn't equal A");
			//System.out.println(LU[0].toString());
			//System.out.println(LU[1].toString());
			System.out.println();
			System.out.println("PERMUTATED ORIGNAL MATRIX");
			System.out.println(Ap.toString());
			System.out.println("Multiplied LU");
			System.out.println(LUMult.toString());
		}
		System.out.println("END: LUColumn_100_100");
		System.out.println();
	}
	
	@Test
	public void testLUColumn_1000_1000(){
		System.out.println("START: LUColumn_1000_1000");
		String mstr = getFile("1000_1000_A");
		
		MatrixSeq orig = new MatrixSeq(1000,1000);
		orig.setAllElem(mstr);
		
		MatrixSeq m = new MatrixSeq(1000,1000);		
		m.setAllElem(mstr);
		
		m.setOpLU(new LUDecomposeSeqColumn());
		Matrix[] combLU = m.LUDecompose();
		if(combLU == null){
			fail("LU decomposition on singular matrix or non-square matrix");
		}
		
		MatrixSeq Ap = applyPermutation(orig,combLU[1]);
		MatrixSeq[] LU = getLU(combLU[0], false);

		MatrixSeq LUMult = (MatrixSeq) LU[0].multiply(LU[1]);

		if(!Ap.equals(LUMult)){			
			fail("LURow_1000_1000 failed: Product of LU doesn't equal A");
			//System.out.println(LU[0].toString());
			//System.out.println(LU[1].toString());
			System.out.println();
			System.out.println("PERMUTATED ORIGNAL MATRIX");
			System.out.println(Ap.toString());
			System.out.println("Multiplied LU");
			System.out.println(LUMult.toString());
		}
		System.out.println("END: LUColumn_1000_1000");
		System.out.println();
	}	
	*/
}
