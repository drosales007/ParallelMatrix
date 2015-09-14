package org.matrix.seq;

import org.matrix.common.*;
import org.matrix.parallel.DeterminantParallel;
import org.matrix.parallel.MultParallel;

//This is concrete class for MatrixSequential Algorithms that extens abstact class Matrix
public class MatrixSeq extends Matrix {

	public MatrixSeq(int rows, int columns) {
		super(rows, columns);
	
		this.mOpAdd = new AddSeq();
		this.mOpDeterminant = new DeterminantSeq();
		this.mOpInverse = new InverseSeq();
		this.mOpLinearSolver = new LinearSolverSeq();
		this.mOpLUDecompose = new LUDecomposeSeq();
		this.mOpMult = new MultSeq();
	}
	
	public MatrixSeq(String filename) {
		super(filename);
		this.mOpAdd = new AddSeq();
		this.mOpDeterminant = new DeterminantSeq();
		this.mOpInverse = new InverseSeq();
		this.mOpLinearSolver = new LinearSolverSeq();
		this.mOpLUDecompose = new LUDecomposeSeq();
		this.mOpMult = new MultSeq();
	}
	
	public MatrixSeq(int rows, int columns, boolean zerod, int max){
		super(rows,columns,zerod,max);
		this.mOpAdd = new AddSeq();
		this.mOpDeterminant = new DeterminantSeq();
		this.mOpInverse = new InverseSeq();
		this.mOpLinearSolver = new LinearSolverSeq();
		this.mOpLUDecompose = new LUDecomposeSeq();
		this.mOpMult = new MultSeq();
	}

	
	public Matrix adjoint() {
		Matrix result = ((MatrixSeq) this.cofactor()).transpose();
		return result;
	}
	    
	public Matrix scalarMultiply(double scalar) {
		Matrix result = new MatrixSeq(getNumRows(),getNumColumns(),true,0);
		for (int i=0; i<getNumRows(); i++) {
			for (int j=0; j<getNumColumns(); j++) {
	    		result.setElem(i, j, scalar*matrix[i][j]);
	    	}
	    }	    
		return result;
	}
	
	public Matrix transpose() {
		Matrix result = new MatrixSeq(getNumRows(),getNumColumns(),true,0);
		for(int i=0; i<getNumRows(); i++) {
			for(int j=0; j<getNumColumns(); j++) {
				result.setElem(i, j, matrix[j][i]);
			}
		}
		return result;
	}
	
	public Matrix createSubMatrix(int excludeRow, int excludeCol) {	   
	    int r = -1;
	    Matrix result = new MatrixSeq((getNumRows()-1),(getNumColumns()-1),true,0);
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
	
	public Matrix cofactor() {
		MatrixSeq subMat = new MatrixSeq((getNumRows()-1),(getNumColumns()-1));
		MatrixSeq cofactorResult = new MatrixSeq(getNumRows(),getNumColumns(),true,0);
		for(int i=0; i<getNumRows(); i++) {
			for(int j=0; j<getNumColumns(); j++) {
				subMat = (MatrixSeq)createSubMatrix(i,j);
				try {
				   cofactorResult.setElem(i, j, (flipOdd(i+j)*subMat.determinant()));
				} catch(Exception e) {
					System.out.println(e.toString());
				}
			}
		}
        return cofactorResult;
	}
	
	
	public static void main(String[] args) {
		System.out.println("test");

	}
	
}
