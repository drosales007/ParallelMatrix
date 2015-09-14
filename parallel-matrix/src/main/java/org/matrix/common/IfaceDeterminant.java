package org.matrix.common;

public interface IfaceDeterminant {
	//You can't return a null so I think we need an exception here
	public double determinant(Matrix A) throws Exception; 
	

}
