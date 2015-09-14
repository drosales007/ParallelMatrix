package org.matrix.seq;

import org.matrix.common.IfaceLUDecompose;
import org.matrix.common.Matrix;

public class LUDecomposeSeqColumn implements IfaceLUDecompose {

	@Override
	public Matrix[] LUDecompose(Matrix A) {
		if(!A.isSquare()){
			return null;
		}
		int n = A.getNumRows();
				
		//Permutation Matrix
		MatrixSeq p = new MatrixSeq(n,1);
		for(int i=0; i<n; i++){
			p.setElem(i, 0, i);
		}
		
		//Recursive loop k=1 to n
		for(int k=0; k<n; k++){
			//Find max pivot
			int idx = k;
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
			
			//Compute V
			for(int i=k+1; i<n; i++){
				A.setElem(i, k,  A.getElem(i, k)/A.getElem(k, k));
				//for j=k+1 to n
				for(int j=k+1; j<n; j++){
					//Aij = Aij - AikAkj
					double val = A.getElem(i, j) - A.getElem(i, k)*A.getElem(k, j);
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
