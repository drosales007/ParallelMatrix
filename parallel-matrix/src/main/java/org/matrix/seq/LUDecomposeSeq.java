package org.matrix.seq;

import org.matrix.common.IfaceLUDecompose;
import org.matrix.common.Matrix;

public class LUDecomposeSeq implements IfaceLUDecompose {

	@Override
	public Matrix[] LUDecompose(Matrix A) {
		// TODO Auto-generated method stub
		if(!A.isSquare()){
			return null;
		}
		int n = A.getNumRows();
		//Create L and U matrix
		MatrixSeq L = new MatrixSeq(n,n);
		MatrixSeq U = new MatrixSeq(n,n);
		
		//Lii = 1 for i=1 to N;
		for(int i=0; i<n; i++){
			L.setElem(i, i, 1);
		}
		
		//For each j = 1, 2, 3 ⋯ N , perform the following operation:
		//a) For i = 1, 2, 3 ⋯ N solve for Uij as				
		//	Uij = Aij − ∑ Lik*Ukj (for k=1 to i-1)
		//Note: when i = 1 , the summation term in equation (3.52) is taken to be zero.
		
		for(int j=0; j<n; j++){
			int i = 0;
			for(i=0; i<=j; i++){
				double val = 0.0;
				for(int k=0; k<i; k++){
					val += L.getElem(i, k)*U.getElem(k, j);
				}
				val = A.getElem(i, j) - val;
				U.setElem(i, j,val);
			}
		
			//b) For i = j + 1, j + 2, to N solve for Lij as;
			//Lij = ([Aij − ∑ Lik Ukj])/Ujj (for k=1 to j-1)
			for(i=j; i<n; i++){
				double val = 0.0;
				for(int k=0; k<j; k++){
					val += L.getElem(i, k)*U.getElem(k, j);
				}
				val = A.getElem(i, j) - val;
				L.setElem(i, j, (val)/U.getElem(j, j));
			}
		}
		//Return L and U
		MatrixSeq[] ret = new MatrixSeq[2];
		ret[0] = L;
		ret[1] = U;
		return ret;
	}

}
