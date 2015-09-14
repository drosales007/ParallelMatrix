package org.matrix.seq;

import org.matrix.common.IfaceLUDecompose;
import org.matrix.common.Matrix;

public class LUDecomposeSeqRow implements IfaceLUDecompose{

	@Override
	public Matrix[] LUDecompose(Matrix A) {
		// TODO Auto-generated method stub
		if(!A.isSquare()){
			return null;
		}
		int n = A.getNumRows();
		
		//This is a permutation matrix in condensed column-vector form.
		//An entry in a row corresponds to row that is permuted.
		//If none of rows are exchanged below, then entry in colum vector
		//will simply be 0,1,2,3,..n-1. 
		MatrixSeq p = new MatrixSeq(n, 1);
		for(int i=0; i<n; i++){
			p.setElem(i, 0, i);
		}
		
		//Row based LU decomposition
		for(int k=0; k<n; k++){

			//Do Pivoting
			int idx=k;
			double max = 0;
			for(int i=k; i<n; i++){
				if(Math.abs(A.getElem(i, k)) > max){
					idx = i;
					max = A.getElem(i, k);
				}
			}
			//If pivot is zero, then matrix is singular
			if(max == 0){
				return null;
			}
			
			//Exchange rows if max pivot is another row
			if(idx != k){
				//Update pivot vector
				double tmp = p.getElem(k, 0);
				double tmp1 = p.getElem(idx, 0);
				p.setElem(k, 0, tmp1);				
				p.setElem(idx, 0, tmp);
				
				//Exchange row
				for(int i=0; i<n; i++){
					tmp = A.getElem(k, i);
					tmp1 = A.getElem(idx, i);
					A.setElem(k, i, tmp1);
					A.setElem(idx, i, tmp);
				}
			}
							
			//Divide elements in kth row
			double v = A.getElem(k, k);
			for(int j=k+1; j<n; j++){
				A.setElem(k, j, A.getElem(k, j)/v);
			}
						
			//Mutiply and Subtract elements in submatrix of size n-k-1
			for(int i=k+1; i<n; i++){
				for(int j=k+1; j<n; j++){
					double val = A.getElem(i, j);
					val = val-A.getElem(i, k)*A.getElem(k, j);
					A.setElem(i, j, val);
				}
			}
		}
		
		MatrixSeq[] ret = new MatrixSeq[2];
		ret[0] = (MatrixSeq)A;
		ret[1] = (MatrixSeq)p;
		return ret;
	}

}
