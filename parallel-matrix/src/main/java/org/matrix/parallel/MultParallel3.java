package org.matrix.parallel;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.matrix.common.Matrix;
import org.matrix.common.IfaceMult;


public class MultParallel3 implements IfaceMult,Runnable {
	public static ExecutorService threadPool = Executors.newFixedThreadPool(50);
	double[] cA;
	double[] cB;	
	int m;
	int n;
	int o;
	static Matrix endResult;
	
	public MultParallel3() {
		super();		
	}
	
	public MultParallel3(double[] cA,double[]cB,int k, int i, int j) {
		super();
		this.cA = cA;
		this.cB = cB;
		this.m = i;
		this.n = j;
		this.o = k;
	}
	
	public void run() {
		Double thisValue = 0.0;
		for(int p=0; p<o; p++) {
			thisValue  = thisValue + cA[p] * cB[p];
		}
		endResult.setElem(m, n, thisValue);
	}


	@Override
	public Matrix multiply(Matrix A, Matrix B) {
		if(A.matrix.length != B.matrix[0].length && A.matrix[0].length != B.matrix.length 
				&& A.matrix.length != A.matrix[0].length) 
			return null;
		int length = A.matrix.length;
		endResult = new MatrixParallel(length,length);
		for (int i=0; i<length; i++) {
			for(int j=0; j<length; j++) {
				MultParallel3 s = new MultParallel3(A.getRow(i),B.getColumn(j),length,i,j);
				threadPool.submit(s);
			}
		}
		return endResult;
	}
}
