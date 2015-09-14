package org.matrix.parallel;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

import org.matrix.common.IfaceMult;
import org.matrix.common.Matrix;

public class MultParallel2 implements IfaceMult,Callable<Boolean> {
	public static ExecutorService threadPool = Executors.newCachedThreadPool();
	static final ReentrantLock resultLock = new ReentrantLock();
	
	int numThreads;
	Matrix A;
	Matrix B;
	MatrixParallel C;
	int startRowInResult;
	int startColInResult;
	
    public MultParallel2() {
		this.numThreads = 8;
	}
    
    public MultParallel2(Matrix A, Matrix B, MatrixParallel C, int startRowInResult, int startColInResult) {
    	this.A = A;
    	this.B = B;
    	this.C = C;
    	this.startRowInResult = startRowInResult;
    	this.startColInResult = startColInResult;
    }

	
	public Boolean call() {
		
		//System.out.println("Writing C start row: " + startRowInResult + " and col: " + startColInResult);
        //System.out.println("C rows: " + C.matrix.length + " cols: " + C.matrix[0].length);
		int numAR = A.matrix.length;
		int numBC = B.matrix[0].length;
		int numAC = A.matrix[0].length;
		if(numAC != B.matrix.length){
			return false;
		}
		int tempColRes = startColInResult;
		for(int i=0; i<numAR; i++) {
			for(int j=0; j<numBC; j++) {
			    double value = 0;
			    for(int k=0; k < numAC; k++) {
				    value = value + (A.matrix[i][k] * B.matrix[k][j]);
				}
			    resultLock.lock();
			    if((startRowInResult < C.matrix.length) && (startColInResult < C.matrix[0].length)) {
 			        //System.out.println("new value: " + value + "\nC " + startRowInResult + " " + startColInResult);
			        C.matrix[startRowInResult][startColInResult] = C.matrix[startRowInResult][startColInResult] + value;
		
			    } 
			    resultLock.unlock();
			    startColInResult++;
			}
		    startRowInResult++;
		    startColInResult = tempColRes;
		}
		return true;
	}
	
	@Override
	public Matrix multiply(Matrix A, Matrix B) {
	
		//System.out.println("A:\n" + A.toString());
		//System.out.println("B:\n" + B.toString());
		int numAR = A.matrix.length;
		int numBC = B.matrix[0].length;
		int numAC = A.matrix[0].length;
		int numBR = B.matrix.length;
		if(numAC != numBR){
			return null;
		} 
		MatrixParallel C = new MatrixParallel(numAR,numBC);	
		boolean evenOut1 = false;
		boolean evenOut2 = false;
		boolean evenOut3 = false;
		if(numAR%2 != 0) {
			numAR++;
			evenOut1=true;
		}
		if(numBC%2 != 0) {
			numBC++;
			evenOut2=true;
		}
		if(numAC%2 != 0) {
			numAC++;
			numBR++;
			evenOut3=true;
		}
		if(evenOut1 || evenOut3) {
		    Matrix tempA = A;
		    A = new MatrixParallel(numAR,numBR);
		    A.setSubMatrix(0, 0, tempA);
		}
		if(evenOut2 || evenOut3) {
		    Matrix tempB = B;
		    B = new MatrixParallel(numAC,numBC);
		    B.setSubMatrix(0, 0, tempB);			
		}
		
		//System.out.println("A:\n" + A.toString());
		//System.out.println("B:\n" + B.toString());
		Matrix[][] subA = new MatrixParallel[2][2];
		Matrix[][] subB = new MatrixParallel[2][2];
		for(int i=0; i<2; i++) {
			for(int j=0; j<2; j++) { 
				subA[i][j] = ((MatrixParallel)A).createSubMatrixRange((i*(numAR/2)), ((i*(numAR/2))+((numAR/2)-1)), (j*(numAC/2)), ((j*(numAC/2))+((numAC/2)-1)));
				subB[i][j] = ((MatrixParallel)B).createSubMatrixRange((i*(numAC/2)), ((i*(numAC/2))+((numAC/2)-1)), (j*(numBC/2)), ((j*(numBC/2))+((numBC/2)-1)));
                //System.out.println("subA " + i + " " + j + ":\n" + subA[i][j].toString());
                //System.out.println("subB " + i + " " + j + ":\n" + subB[i][j].toString());
			}
		}
		ArrayList<Future<Boolean>> finished = new ArrayList<Future<Boolean>>();
		for(int i=0; i<2; i++) {
			for(int j=0; j<2; j++) { 
				for(int k=0; k<2; k++) {
		            //System.out.println("Multiply A: \n" + subA[i][k].toString());
		            //System.out.println("Multiply B: \n" + subB[k][j].toString());
					MultParallel2 s = new MultParallel2(subA[i][k],subB[k][j],C,((numAR/2)*i),((numBC/2)*j));
					//System.out.println("Submitted thread");
					Future<Boolean> f = threadPool.submit(s);
					finished.add(f);
				}
			}
		}
		for(int i=0; i<finished.size(); i++) {
			try {
			    finished.get(i).get();
			} catch(Exception e) {
				e.printStackTrace();
			}
			//System.out.println("Thread: " + i + " is done.");
		}

		return C.createSubMatrixRange(0,numAR-1,0,numBC-1);
	}

}
