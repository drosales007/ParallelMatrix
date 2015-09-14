package org.matrix.parallel;

import java.util.concurrent.Callable;

public class MultiplyScalarThread implements Callable<double[]> {
	
	double scalar;
	double[] part;
	
	public MultiplyScalarThread(double[] part,double scalar) {
		this.part = part;
	    this.scalar = scalar;	
	}
	

	public double[] call() throws Exception {
		for(int i=0; i< part.length; i++) {
			part[i]=part[i]*scalar;
		}
		return part;
	}

}
