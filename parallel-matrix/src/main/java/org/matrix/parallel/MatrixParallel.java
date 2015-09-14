package org.matrix.parallel;

import org.matrix.common.Matrix;
import org.matrix.seq.MatrixSeq;

import java.util.concurrent.*;
import java.util.*;
//import org.matrix.Parallel.AddSeq;
import org.matrix.seq.DeterminantSeq;
//import org.matrix.Parallel.InverseSeq;
//import org.matrix.Parallel.LUDecomposeSeq;
//import org.matrix.Parallel.LinearSolverSeq;
//import org.matrix.Parallel.MatrixSeq;
//import org.matrix.Parallel.MultSeq;

public class MatrixParallel extends Matrix {
	
	public static ExecutorService threadPool = Executors.newCachedThreadPool();
	int numThreads = 4;

	
	public MatrixParallel(String filename) {
		super(filename);
		//this.mOpAdd = new AddSeq();
//		this.mOpDeterminant = new DeterminantParallel();
		//this.mOpInverse = new InverseSeq();
		//this.mOpLinearSolver = new LinearSolverSeq();
		//this.mOpLUDecompose = new LUDecomposeSeq();
		this.mOpMult = new MultParallel3();
	}
	
	public MatrixParallel(int rows, int columns) {
		super(rows, columns);
		//this.mOpAdd = new AddSeq();
//		this.mOpDeterminant = new DeterminantParallel();
		//this.mOpInverse = new InverseSeq();
		//this.mOpLinearSolver = new LinearSolverSeq();
		//this.mOpLUDecompose = new LUDecomposeSeq();
		this.mOpMult = new MultParallel3();
	}
	
	public MatrixParallel(int rows, int columns, boolean zerod, int max){
		super(rows,columns,zerod,max);
		//this.mOpAdd = new AddSeq();
//		this.mOpDeterminant = new DeterminantParallel();
		//this.mOpInverse = new InverseSeq();
		//this.mOpLinearSolver = new LinearSolverSeq();
		//this.mOpLUDecompose = new LUDecomposeSeq();
		this.mOpMult = new MultParallel3();
	}

	public Matrix scalarMultiply(double scalar) {
		Matrix result = new MatrixParallel(getNumRows(),getNumColumns(),true,0);
		for (int i=0; i<getNumRows(); i++) {
			for (int j=0; j<getNumColumns(); j++) {
	    		result.setElem(i, j, scalar*matrix[i][j]);
	    	}
	    }	    
		return result;
	}
	
	public Matrix scalarMultiplyThreaded(double scalar) {	
		Matrix result = new MatrixParallel(getNumRows(),getNumColumns(),true,0);
		ArrayList<Future<double[]>> threadVals = new ArrayList<Future<double[]>>();
		for (int i=0; i<getNumRows(); i++) {
			MultiplyScalarThread s = new MultiplyScalarThread(matrix[i],scalar);
			Future<double[]> f1 = threadPool.submit(s);
			threadVals.add(f1);
	    }	 
	    for (int j=0; j<threadVals.size(); j++){
	    	int i = j/getNumRows();
	    	int k = j%getNumColumns();
	        try {
	        	result.setRow((threadVals.get(j)).get(),i);
	        }
	        catch(Exception exc) {
	          System.err.println(exc);
	        }
	        
	    }
	    threadPool.shutdown();
		return result;
	}
	
	public Matrix createSubMatrix(int excludeRow, int excludeCol) {	   
	    int r = -1;
	    Matrix result = new MatrixParallel((getNumRows()-1),(getNumColumns()-1),true,0);
	    for (int i=0;i<getNumRows();i++) {
	        if (i==excludeRow)
	            continue;
	            r++;
	            int c = -1;
	        for (int j=0;j<getNumColumns();j++) {
	            if (j==excludeCol)
	                continue;
	            result.setElem(r, ++c, matrix[i][j]);
	        }
	    }
	    return result;
	}
	
	public Matrix createSubMatrixRange(int includeStartRow,int includeEndRow, int includeStartCol, int includeEndCol) {	   
	    int row = -1;
	    //System.out.println("This matrix: \n" + this.toString());
	    Matrix result = new MatrixParallel((includeEndRow-includeStartRow+1),(includeEndCol-includeStartCol+1));
	    for (int i=0;i<getNumRows();i++) {
	        if (i<includeStartRow || i>(includeEndRow+1)) {
	        	//System.out.println("Skipping row: " + i);
	            continue;
	        }
	            row++;
	            int col = -1;
	        for (int j=0;j<getNumColumns();j++) {
	            if (j<includeStartCol || j>(includeEndCol+1))	{
	            	//System.out.println("Skipping row: " + i + " col: " + j);
	                continue;
	            }
	            if(i>=getNumRows() || j>=getNumColumns()) {
	            	result.setElem(row,++col, matrix[i][j]);
	            } else {
	                result.setElem(row, ++col, matrix[i][j]);
	            }
	        }
	    }
	    //System.out.println("Result:\n" + result.toString());
	    return result;
	}
	
	public Matrix cofactor() {
		MatrixParallel subMat = new MatrixParallel((getNumRows()-1),(getNumColumns()-1));
		MatrixParallel cofactorResult = new MatrixParallel(getNumRows(),getNumColumns(),true,0);
		for(int i=0; i<getNumRows(); i++) {
			for(int j=0; j<getNumColumns(); j++) {
				subMat = (MatrixParallel)createSubMatrix(i,j);
				try {
				   cofactorResult.setElem(i, j, (flipOdd(i+j)*subMat.determinant()));
				} catch(Exception e) {
					System.out.println(e.toString());
				}
			}
		}
        return cofactorResult;
	}
	

	
	public Matrix transpose() {
		Matrix result = new MatrixParallel(getNumRows(),getNumColumns(),true,0);
		for(int i=0; i<getNumRows(); i++) {
			for(int j=0; j<getNumColumns(); j++) {
				result.setElem(i, j, matrix[j][i]);
			}
		}
		return result;
	}
	
	public static void main(String[] args) {


	}
	
}
