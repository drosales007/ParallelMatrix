package org.matrix.common;


//Singleton class for matrix configuration
public class MatrixConfig {
	private static int maxThreads;
	private static int maxHWThreads;
	private static MatrixConfig self=null;
	
	//Private constructor makes this singleton
	private MatrixConfig(){		
	}
	
	//Use this to get a handle to MatrixConfig object.
	//There should only be one instance
	public static MatrixConfig getMatrixConfig(){
		if(self == null){
			self = new MatrixConfig();
		}
		return self;
	}
	
	//Max Threads
	public void setMaxThreads(int n){
		maxThreads = n;
	}
	
	public int getMaxThreads(){
		return maxThreads;
	}
	
	//Max threads in HW
	//Example i7 has 4 cores with Hyper-Threading, so total 8
	//Set it according to the machine
	public void setMaxHWThreads(int n){
		maxHWThreads = n;
	}
	
	public int getMaxHWThreads(){
		return maxHWThreads;
	}
	
}
