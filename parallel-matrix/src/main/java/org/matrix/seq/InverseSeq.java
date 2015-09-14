package org.matrix.seq;

import org.matrix.common.IfaceInverse;
import org.matrix.common.Matrix;

public class InverseSeq implements IfaceInverse {

	@Override
	public Matrix inverse(Matrix A) {
		Matrix inverse = new MatrixSeq(A.getNumRows(), A.getNumColumns(), true, 0);
		double det = 0;
		try {
		    det = A.determinant();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		det = 1/det;
		Matrix adjointR = ((MatrixSeq)A).adjoint();
		inverse = ((MatrixSeq)adjointR).scalarMultiply(det);
		return inverse;
	}
	
}
