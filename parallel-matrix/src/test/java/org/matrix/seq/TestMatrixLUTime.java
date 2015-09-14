package org.matrix.seq;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.matrix.common.Matrix;

public class TestMatrixLUTime {

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
	public void testLUColumnTime(){		
		for(int i=2048; i>0; i=i/2){
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
			
			long t = end - start;
			System.out.println("END: Name=SeqLUColumn, Dimension=" + s + "Time(ms)="+t);
			System.out.println();
		}
	}
	
	@Test
	public void testLURowTime(){
		for(int i=2048; i>0; i=i/2){
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
			
			long t = end - start;
			System.out.println("END: Name=SeqLUColumn, Dimension=" + s + "Time(ms)="+t);
			System.out.println();
		}
	}

}
