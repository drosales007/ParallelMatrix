package org.matrix.parallel;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.matrix.common.IfaceLUDecompose;
import org.matrix.common.Matrix;
import org.matrix.common.MatrixConfig;


public class LUDecomposeParallel2DBlock implements IfaceLUDecompose {
	public static ExecutorService threadPool = Executors.newCachedThreadPool();
	
	//1Thread 1x1
	//2Thread 2x1
	//3Thread 2x1
	//4Thread 2x2
	//5Thread 2x2
	//6Thread 2x2
	//7Thread 2x2
	//8Thread 4x2
	//9-15 Threads 4x2
	//16 Threads 4x4
	int[] lookUPTableR = {1,2,2,2,2,2,2,4,4,4,4,4,4,4,4,4};
	int[] lookUPTableC = {1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,4};
	
	@Override
	public Matrix[] LUDecompose(Matrix A) {
		
		if(!A.isSquare()){
			return null;
		}
	
		MatrixParallel permM = new MatrixParallel(A.getNumRows(),1);
		for(int i=0; i<permM.getNumRows(); i++){
			permM.setElem(i, 0, i);
		}
				
		//Create threads
		MatrixConfig mc = MatrixConfig.getMatrixConfig();
		int n = A.getNumRows();
		int numTRows = 0, numTCols = 0 ;
		int wX = 0, wY = 0;
		if(mc.getMaxThreads() <=16){
			numTRows = lookUPTableR[mc.getMaxThreads()-1];
			numTCols = lookUPTableC[mc.getMaxThreads()-1];
			wX = n/numTCols + (n%numTCols>0?1:0);
			wY = n/numTRows + (n%numTRows>0?1:0);
		} else{
			//\\todo
			//Find the closest power of 2 to mc.getMaxThreads()
		}
		
		/*System.out.println("Number of threads: " + numTRows*numTCols);
		System.out.println("Number of Thread Rows: " + numTRows);
		System.out.println("Number of Thread Cols: " + numTCols);
		System.out.println("wX: " + wX);
		System.out.println("wY: " + wY);*/
		
		Thread[][] ts = new Thread[numTRows][numTCols];
		workerThread[][] ms = new workerThread[numTRows][numTCols];
		CyclicBarrier msBarrier = new CyclicBarrier(numTRows*numTCols);		
		for(int x=0; x<numTRows; x++){
			for(int y=0; y<numTCols; y++){
				ms[x][y] = new workerThread(A, permM, x, y, wX, wY, msBarrier);
				ts[x][y] = new Thread(ms[x][y]);
			}
		}
		
		//Start MS threads
		for(int x=0; x<numTRows; x++){
			for(int y=0; y<numTCols; y++){				
				ts[x][y].start();
			}
		}
		
		//Join All threads
		try {
			for(int x=0; x<numTRows; x++){
				for(int y=0; y<numTCols; y++){				
					ts[x][y].join();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
							
		MatrixParallel[] ret = new MatrixParallel[2];
		ret[0] = (MatrixParallel)A;
		ret[1] = (MatrixParallel)permM;
		return ret;		
	}
	
	class workerThread implements Runnable{
		Matrix A, p;
		int tidx, tidy;
		int wX, wY;
		CyclicBarrier barrier;
		boolean singular;
		
		workerThread(Matrix A, Matrix p, int tidx, int tidy, int wX, int wY, CyclicBarrier b){
			this.A = A;
			this.p = p;
			this.tidx = tidx;
			this.tidy = tidy;
			this.wX = wX;
			this.wY = wY;
			this.barrier = b;
			this.singular = false;
		}
		
		@Override
		public void run() {	
			int k = 0;
			int n = A.getNumRows();
			int startCol = tidy*wX;
			int endCol = tidy*wX + wX-1;
			int startRow = tidx*wY;
			int endRow = tidx*wY + wY-1;
			
			//System.out.println("tid(" + tidx + "," + tidy + "):" +  " wX:" + wX + " wY:" + wY + 
			//		"sC: " + startCol + "eC: " + endCol + "sR: " + startRow + "eR: " + endRow);
			
			while(k<n && !singular){
				//Do pivot search(Only 1 thread, tidx ==0, tidy==0)
				if(tidx == 0 && tidy == 0){
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
				/*try {								
					barrier.await();				
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			
				if((k+1)>=startRow && (k+1)<=endRow){
					double div = A.getElem(k, k);
					int c;
					if(startCol > k+1){
						c = startCol;
					} else{
						c = k + 1;
					}
					while(c<=endCol && c<n){
						//Do division
						A.setElem(k, c, A.getElem(k, c)/div);
						c++;
					}
				}*/
			
				//Barrier
				try {								
					barrier.await();				
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			
				//Do multiply and subtract
				int r;
				if(startRow > k+1){
					r = startRow;
				} else{
					r = k + 1;
				}
				while(r<=endRow && r<n){
					int c;
					if(startCol > k+1){
						c = startCol;
					} else{
						c = k + 1;
					}
					while(c<=endCol && c<n){
						double val = A.getElem(r, c) - A.getElem(r, k)*A.getElem(k, c);
						A.setElem(r, c, val);
						c++;
					}
					r++;
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
