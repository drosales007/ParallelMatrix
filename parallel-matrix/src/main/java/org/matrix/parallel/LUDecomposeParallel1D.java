package org.matrix.parallel;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.matrix.common.IfaceLUDecompose;
import org.matrix.common.Matrix;


class workerThreadSync{
	static final ReentrantLock lock = new ReentrantLock();
	final Condition pivotQ  = lock.newCondition(); 
	final Condition divideQ  = lock.newCondition(); 
	final Condition msQ  = lock.newCondition(); 
	int mTaskActive;
	boolean mSingular;
	
	workerThreadSync(){
		mTaskActive = 0;
		mSingular = false;
	}
	
	public void setSingular(boolean v){
		mSingular = v;
	}
	
	public boolean getSingular(){
		return mSingular;
	}
		
	public void waitPivot(){
		lock.lock();
		try {
			while(mTaskActive != 0 && mTaskActive != 4){
				pivotQ.await();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			lock.unlock();
		}
	}
	
	public void signalPivot(){		
		lock.lock();
		try {
			pivotQ.signalAll();
		} finally{
			lock.unlock();
		}
		
	}
	
	public void waitDivide(){
		lock.lock();
		try {
			while(mTaskActive != 1 && mTaskActive != 4){
				divideQ.await();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			lock.unlock();
		}
	}
	
	public void signalDivide(){
		lock.lock();
		try {
			divideQ.signalAll();
		} finally{
			lock.unlock();
		}
	}
	
	public void waitMS(){
		lock.lock();
		try {
			while(mTaskActive != 2 && mTaskActive != 4){
				msQ.await();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			lock.unlock();
		}
	}
	
	public void signalMS(){		
		lock.lock();
		try {
			msQ.signalAll();
		} finally{
			lock.unlock();
		}
		
	}
		
	public void setTaskActive(int v){
		lock.lock();
		try {
			mTaskActive = v;
		} finally{
			lock.unlock();
		}
	}	
}

class workerThread1 implements Runnable{
	Matrix A, p;
	int tid, numT, type;
	workerThreadSync wts;
	CyclicBarrier barrier;
	
	workerThread1(Matrix A, Matrix p, int tid, int numT, int type, workerThreadSync wts, CyclicBarrier b){
		this.A = A;
		this.p = p;
		this.tid = tid;
		this.numT = numT;
		this.type = type;
		this.wts = wts;
		this.barrier = b;
	}
	
	@Override
	public void run() {	
		if(type == 0){
			ps(); //pivot-search
		} else if(type ==1){
			divide(); //divide
		} else {
			ms(); //multiply-subtract
		}
	}
	
	public void ps(){
		int k = 0;
		int n = A.getNumRows();
		while(k<n){
			//Do Pivoting
			int idx=k;
			double max = 0;
			for(int i=k; i<n; i++){
				if(Math.abs(A.getElem(i, k)) > max){
					idx = i;
					max = A.getElem(i, k);
				}
			}
			//If pivot is zero, then matrix is singular
			if(max == 0){
				wts.setSingular(true);
				k = n;
				wts.setTaskActive(4);
				wts.signalDivide();
				wts.signalMS();
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
			
				//Barrier(Wait for all pivot threads to finish)
				//\todo
			
				//Signal divide threads(if id=1)
				if(tid == 1){
					wts.setTaskActive(1);
					wts.signalDivide();
				}
			
				//Wait until signaled
				wts.waitPivot();
			
				//Next iteration
				k++;
			}
		}
	}
	
	public void divide() {
		int k = 0;
		int n = A.getNumRows();
		//Initial Wait
		wts.waitDivide();
		
		while (k<n && !wts.getSingular()){			
			//Do division
			double div = A.getElem(k, k);
			for(int i=k+1; i<n; i++){
				A.setElem(k, i, A.getElem(k, i)/div);
			}
			
			//Barrier(wait for all divide threads to finish)
			//\todo
			
			//Signal MS Threads(if id=1)
			if(tid == 1){
				wts.setTaskActive(2);
				wts.signalMS();
			}

			//Wait
			wts.waitDivide();
			
			//Next iteration
			k++;
		}		
	}
	
	public void ms(){
		int k = 0;
		int n = A.getNumRows();
		//Wait
		wts.waitMS();
		
		while (k<n && !wts.getSingular()){						
			//Determine starting row for each thread
			//based on kth iteration and thread-id(tid)
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
			
			//Do multiply and subtract
			for(int r = ((b*numT) + tid);r<n;r+=numT){
				for(int c=k+1;c<n;c++){
					double val = A.getElem(r, c) - A.getElem(r, k)*A.getElem(k, c);
					A.setElem(r, c, val);
				}
				System.out.println("tid=" + tid + "k=" + k + "row=" + r);
			}
			
			//Barrier(wait for all MS threads to finish)
			try {								
				barrier.await();				
			} catch (InterruptedException | BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Signal Divide Threads(if id=0)
			if(tid == 1){				
				//Pivot thread won't signal waiting divide threads
				if(k==n-1){
					wts.setTaskActive(4);
					wts.signalPivot();
					wts.signalDivide();
				} else{
					wts.setTaskActive(0);
					wts.signalPivot();
				}
			}

			//Wait
			wts.waitMS();
			k++;
		}
	}
}

public class LUDecomposeParallel1D implements IfaceLUDecompose {
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
		
		//Create Pivot-Search Threads
		//\todo use more than one thread
		CyclicBarrier pBarrier = new CyclicBarrier(1);
		workerThread1 p = new workerThread1(A,permM,1,1,0,wts,pBarrier);
		Thread tp = new Thread(p);
				
		//Create Divide threads
		//\todo create more than one
		CyclicBarrier dBarrier = new CyclicBarrier(1);
		workerThread1 d = new workerThread1(A,permM,1,1,1, wts, dBarrier);
		Thread td = new Thread(d);
		
		//Create MS threads
		//todo determine how many threads to use
		int n = A.getNumRows();
		int numT = 0;
		if(n<8){
			numT = 2;
		} else{
			numT = 2;
		}
		
		System.out.println("Number of threads: " + numT);
		
		Thread[] ts = new Thread[numT];
		workerThread1[] ms = new workerThread1[numT];
		CyclicBarrier msBarrier = new CyclicBarrier(numT);		
		for(int i=0; i<numT; i++){
			ms[i] = new workerThread1(A, permM, i+1, numT, 2, wts, msBarrier);
			 ts[i] = new Thread(ms[i]);
		}
		
		//Start Pivot Threads
		tp.start();
		
		//Start Divide threads
		td.start();
		
		//Start MS threads
		for(int i=0; i<numT; i++){
			ts[i].start();;
		}
		
		//Join All threads
		try {
			tp.join();
			td.join();
			for(int i=0; i<numT; i++){
				ts[i].join();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
							
		// TODO Auto-generated method stub
		MatrixParallel[] ret = new MatrixParallel[2];
		ret[0] = (MatrixParallel)A;
		ret[1] = (MatrixParallel)permM;
		if(wts.getSingular()){
			return null;
		} else {
			return ret;
		}
	}

}
