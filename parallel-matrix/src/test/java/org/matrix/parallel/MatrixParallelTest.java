package org.matrix.parallel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.matrix.common.Matrix;
import java.io.*;
import java.util.*;


public class MatrixParallelTest {


	MatrixParallel m1;
	MatrixParallel m2;
	MatrixParallel m3;
	MatrixParallel m4;
	MatrixParallel m5;
	MatrixParallel m6;
	
	@Before
	public void setUp() throws Exception {
		m1 = new MatrixParallel(4,4,false,10);
		m2 = new MatrixParallel(4,4,false,5);
		m3 = new MatrixParallel(3,4,false,5);
		m4 = new MatrixParallel(3,3,true,0);
		m5 = new MatrixParallel(3,3,true,0);
		m6 = new MatrixParallel(3,1,true,0);
		double[] arow = {1,2,3};
		double[] brow = {0,4,5};
		double[] crow = {1,0,6};
		m4.setRow(arow, 0);
		m4.setRow(brow, 1);
		m4.setRow(crow, 2);	
		double[] drow = {1,1,1};
		double[] erow = {0,2,5};
		double[] frow = {2,5,-1};
		m5.setRow(drow, 0);
		m5.setRow(erow, 1);
		m5.setRow(frow, 2);
		m6.setElem(0, 0, 6);
		m6.setElem(1, 0, -4);
		m6.setElem(2, 0, 27);
	}
	
	@Test
	public void testToString() {
		String expected = "0.0 0.0 0.0 0.0 \n0.0 1.0 2.0 3.0 \n0.0 2.0 4.0 6.0 \n0.0 3.0 6.0 9.0 \n";
		assertEquals(m1.toString(), expected);
	}
	
	@Test
	public void testSetRow() {
		Matrix replaceRow = new MatrixParallel(4,4,false,10);
		double[] row = {5,5,5,5};
		replaceRow.setRow(row, 3);
		String expected = "0.0 0.0 0.0 0.0 \n0.0 1.0 2.0 3.0 \n0.0 2.0 4.0 6.0 \n5.0 5.0 5.0 5.0 \n";
		assertEquals(expected,replaceRow.toString());
		
	}
	@Test
	public void testSetElem(){
		Matrix thisMatrix = new MatrixParallel(4,4,true,0);
		for(int i=0;i<thisMatrix.getNumRows();i++) {
			for(int j=0; j<thisMatrix.getNumColumns();j++) {
				thisMatrix.setElem(i, j, ((i*j)%10));
			}
		}
		String expected = "0.0 0.0 0.0 0.0 \n0.0 1.0 2.0 3.0 \n0.0 2.0 4.0 6.0 \n0.0 3.0 6.0 9.0 \n";
		assertEquals(expected,thisMatrix.toString());
	}
	
	@Test
	public void testGetNumRows() {
		Matrix newMat = new MatrixParallel(7,3,true,0);
		assertEquals(newMat.getNumRows(),7);
	}
	
	@Test
	public void testMatrixFromFile(){
/*		Matrix fileMatrix = new MatrixParallel("C:\\Users\\Amy\\git\\ParallelMatrix\\parallel-matrix\\src\\test\\resources\\10_10_A");		
        System.out.println("Matrix from file: \n" + fileMatrix.toString());*/
	}
	
	@Test
	public void testGetNumColumns() {
		Matrix newMat = new MatrixParallel(7,3,true,0);
		assertEquals(newMat.getNumColumns(),3);
	}
	
	@Test
	public void testGetRow() {
		double[] rowSample = {0,2,4,6};
		double[] temp = m1.getRow(2);
		for(int i=0; i<temp.length; i++) {
			assert(temp[i] == rowSample[i]);
		}
	}
	@Test
	public void testGetColumn() {
		double[] colSample = {0,2,4,6};
		double[] temp = m1.getColumn(2);
		for(int i=0; i<temp.length; i++) {
			assert(temp[i] == colSample[i]);
		}
	}

	@Test
	public void testGetElem() {
		assert(m1.getElem(2, 2)== 4);
	}
	
	@Test
	public void testIsSquare() {
		assert(m1.isSquare());
	}
	
	@Test 
	public void testIsNotSquare() {
		assert(!m3.isSquare());
	}
	
	@Test
	public void testCreateSubMatrix() {
		String result = (m2.createSubMatrix(0, 0)).toString();
		String expected = "1.0 2.0 3.0 \n2.0 4.0 1.0 \n3.0 1.0 4.0 \n";
		assertTrue(expected.equals(result));
	}
	
	@Test 
	public void testCreateSubMatrixRange() {
/*		MatrixParallel mat = new MatrixParallel(10,10,false,15);
		String result = (mat.createSubMatrixRange(2, 5, 3, 6)).toString();
		String expected = "6.0 8.0 10.0 12.0 \n9.0 12.0 0.0 3.0 \n12.0 1.0 5.0 9.0 \n0.0 5.0 10.0 0.0 \n";
		assertTrue(expected.equals(result));*/
	}
	
	@Test
	public void testCofactor() {
		String result = (m4.cofactor()).toString();
		String expected = "24.0 5.0 -4.0 \n-12.0 3.0 2.0 \n-2.0 -5.0 4.0 \n";
		assertTrue(expected.equals(result));	
	}
	
	@Test
	public void testTranspose() {
		String result = (m2.transpose()).toString();
		String expected = "0.0 0.0 0.0 0.0 \n0.0 1.0 2.0 3.0 \n0.0 2.0 4.0 1.0 \n0.0 3.0 1.0 4.0 \n";
		assertTrue(expected.equals(result));	
	}
	@Test
	public void testScalarMultiply() {
		String result = (m2.scalarMultiply(5)).toString();
		String expected = "0.0 0.0 0.0 0.0 \n0.0 5.0 10.0 15.0 \n0.0 10.0 20.0 5.0 \n0.0 15.0 5.0 20.0 \n";
		assertTrue(expected.equals(result));		
	}
	
	@Test
	public void timeScalarMultiply() {
		MatrixParallel thousand = new MatrixParallel(1000,1000,false,15);
		long start = System.currentTimeMillis();
		String result = (thousand.scalarMultiply(5)).toString();
		//System.out.println("Result:\n" + result);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for sequential scalar multiply is: " + timer);
	}

	@Test
	public void timeScalarMultiplyThreaded() {
		MatrixParallel thousand = new MatrixParallel(1000,1000,false,15);
		long start = System.currentTimeMillis();
		String result = (thousand.scalarMultiplyThreaded(5)).toString();
		//System.out.println("Result:\n" + result);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel scalar multiply is: " + timer);
	}


	@Test
	public void testLinearSolve() {
/*		String matrixResult = (m5.linearSolve(m6)).toString();
		String expected = "4.999999999999999 \n3.0 \n-1.9999999999999998 \n";
		assertTrue(matrixResult.equals(expected));*/
	}
	
	@Test
	public void testInverse() {
/*		Matrix matrixResult = m5.inverse();
		System.out.println("M1: \n" + m5.toString());
		String expected = "1.2857142857142856 -0.2857142857142857 -0.14285714285714285 \n-0.47619047619047616 0.14285714285714285 0.23809523809523808 \n0.19047619047619047 0.14285714285714285 -0.09523809523809523 \n";
        System.out.println("Result: \n" + matrixResult.toString());
		assertEquals(matrixResult.toString(),expected);	*/	
	}
	
}
