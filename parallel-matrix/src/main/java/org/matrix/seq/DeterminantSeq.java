package org.matrix.seq;

import org.matrix.common.IfaceDeterminant;
import org.matrix.common.Matrix;

public class DeterminantSeq implements IfaceDeterminant {

	@Override
	public double determinant(Matrix A) throws Exception {
		MatrixSeq seqA = (MatrixSeq)A;
        if (!seqA.isSquare()) {
	        throw new Exception("Need square matrix."); //todo define matrix exception
        }
		if (seqA.getNumRows() == 1) {
			return A.getElem(0,0);
		}
		if (seqA.getNumRows()==2) {
		    return (seqA.getElem(0, 0) * seqA.getElem(1, 1)) - ( seqA.getElem(0, 1) * seqA.getElem(1, 0));
		}
		double sum = 0.0;
		for (int i=0; i<seqA.getNumColumns(); i++) {
		    sum += MatrixSeq.flipOdd(i) * seqA.getElem(0, i) * determinant(seqA.createSubMatrix(0, i));
		}
		return sum;
	}
	
}
