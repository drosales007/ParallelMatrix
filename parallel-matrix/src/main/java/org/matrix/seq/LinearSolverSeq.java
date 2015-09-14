package org.matrix.seq;

import org.matrix.common.IfaceLinearSolver;
import org.matrix.common.Matrix;

public class LinearSolverSeq implements IfaceLinearSolver {

	@Override
	public Matrix linearSolver(Matrix A, Matrix B) {
		// TODO Auto-generated method stub
		Matrix invA = ((MatrixSeq)A).inverse();
		Matrix result = invA.multiply(B);
		return result;
	}
	
}

