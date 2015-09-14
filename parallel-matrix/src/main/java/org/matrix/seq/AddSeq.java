package org.matrix.seq;

import org.matrix.common.IfaceAdd;
import org.matrix.common.Matrix;

public class AddSeq implements IfaceAdd {

	@Override
	public Matrix add(Matrix A, Matrix B) {

		if((A.getNumRows() != B.getNumRows()) || (A.getNumColumns() != B.getNumColumns())) {
			return null;
		}
		
		int numR = A.getNumRows();
		int numC = A.getNumColumns();		
		MatrixSeq C = new MatrixSeq(numR,numC); 
		for(int i=0; i<numR; i++) {
			for(int j=0; j<numC; j++) {
				C.setElem(i,j, (A.getElem(i,j) + B.getElem(i,j)));
			} 
		}
		return C;
	}

}
