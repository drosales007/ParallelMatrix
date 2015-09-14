package org.matrix.seq;

import org.matrix.common.IfaceMult;
import org.matrix.common.Matrix;

public class MultSeq implements IfaceMult {

	@Override
	public Matrix multiply(Matrix A, Matrix B) {
		
		if(A.getNumColumns() != B.getNumRows()){
			return null;
		}
		
		int numAR = A.getNumRows();
		int numBC = B.getNumColumns();
		int numAC = A.getNumColumns();
		MatrixSeq C = new MatrixSeq(numAR,numBC);
		
		for(int i=0; i<numAR; i++) {
			for(int j=0; j<numBC; j++) {
			    double value = 0;
			    for(int k=0; k < numAC; k++) {
				    value = value + (A.getElem(i, k) * B.getElem(k, j));
				}
				C.setElem(i, j, value);
			}
		}		
		return C;
	}
	
}
