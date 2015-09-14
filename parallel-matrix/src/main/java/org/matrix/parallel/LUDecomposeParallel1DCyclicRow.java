package org.matrix.parallel;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.matrix.common.IfaceLUDecompose;
import org.matrix.common.Matrix;
import org.matrix.common.MatrixConfig;

public class LUDecomposeParallel1DCyclicRow implements IfaceLUDecompose {
public static ExecutorService threadPool = Executors.newCachedThreadPool();
	
	@Override
	public Matrix[] LUDecompose(Matrix A) {
		
		if(!A.isSquare()){
			return null;
		}
	
		//Object used to sync pivot-search, divide and multiply-subtract threads
		workerThreadSync wts = new workerThreadSync();
		
		MatrixParallel permM = new MatrixParallel(A.getNumRows(),1);
		for(int i=0; i<permM.getNumRows(); i++){
			permM.setElem(i, 0, i);
		}
				
		//Create threads
		MatrixConfig mc = MatrixConfig.getMatrixConfig();
		int n = A.getNumRows();
		int numT = 0;
		if(n<mc.getMaxThreads()){
			numT = n;
		} else{
			numT = mc.getMaxThreads();
		}
		
		//System.out.println("Number of threads: " + numT);
		
		Thread[] ts = new Thread[numT];
		workerThread[] ms = new workerThread[numT];
		CyclicBarrier msBarrier = new CyclicBarrier(numT);		
		for(int i=0; i<numT; i++){
			ms[i] = new workerThread(A, permM, i+1, numT, msBarrier);
			 ts[i] = new Thread(ms[i]);
		}
		
		//Start MS threads
		for(int i=0; i<numT; i++){
			ts[i].start();;
		}
		
		//Join All threads
		try {
			for(int i=0; i<numT; i++){
				ts[i].join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
							
		MatrixParallel[] ret = new MatrixParallel[2];
		ret[0] = (MatrixParallel)A;
		ret[1] = (MatrixParallel)permM;
		if(wts.getSingular()){
			return null;
		} else {
			return ret;
		}
	}
	class workerThread implements Runnable{
		Matrix A, p;
		int tid, numT;
		CyclicBarrier barrier;
		boolean singular;
		
		workerThread(Matrix A, Matrix p, int tid, int numT, CyclicBarrier b){
			this.A = A;
			this.p = p;
			this.tid = tid;
			this.numT = numT;
			this.barrier = b;
			this.singular = false;
		}
		
		@Override
		public void run() {	
			int k = 0;
			int n = A.getNumRows();
			
			while(k<n && !singular){
				
				//Do pivot search(only 1 thread)
				if(tid==1){
					//Find max pivot
					int idx=k;
					double max = 0;
					for(int i=k; i<n; i++){
						if(Math.abs(A.getElem(i, k)) > max){
							idx = i;
							max = A.getElem(i, k);
						}
					}
					
					if(max == 0){
						singular = true;
					} else{
					
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
						
						//Do row division
						double div = A.getElem(k, k);
						for(int i=k+1; i<n; i++){
							A.setElem(k, i, A.getElem(k, i)/div);
						}
					}
				}
			
				//Barrier			
				try {								
					barrier.await();				
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
							
				//Determine your rows
				int b=0;
				if(k==0){
					b = 0;
				} else{
					if(k%numT + 1 > tid){
						b = k/numT + 1;
					} else{
						b = k/numT;
					}
				}			
									
				for(int r = ((b*numT) + tid);r<n;r+=numT){
					//Do multiply and subtract
					for(int c=k+1;c<n;c++){
						double val = A.getElem(r, c) - A.getElem(r, k)*A.getElem(k, c);
						A.setElem(r, c, val);
					}				
				}
			
				//Barrier			
				try {								
					barrier.await();				
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
				
				//Next iteration
				k++;			
			}
		}
	}
}


