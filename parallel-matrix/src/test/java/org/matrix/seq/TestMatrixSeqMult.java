package org.matrix.seq;

import org.junit.Test;
import org.matrix.common.Matrix;

public class TestMatrixSeqMult {

	
	@Test
	public void testTimed2x2MultSeq() {
		Matrix large1 = new MatrixSeq(".\\src\\test\\resources\\2_2_A");
		Matrix large2 = new MatrixSeq(".\\src\\test\\resources\\2_2_B");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for sequential 2x2 timed multiply is: " + timer);
	}

	@Test
	public void testTimed4x4MultSeq() {
		Matrix large1 = new MatrixSeq(".\\src\\test\\resources\\4_4_A");
		Matrix large2 = new MatrixSeq(".\\src\\test\\resources\\4_4_B");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for sequential 4x4 timed multiply is: " + timer);
	}
	
	@Test
	public void testTimed8x8MultSeq() {
		Matrix large1 = new MatrixSeq(".\\src\\test\\resources\\8_8_A");
		Matrix large2 = new MatrixSeq(".\\src\\test\\resources\\8_8_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for sequential 8x8 timed multiply is: " + timer);
	}
	
	@Test
	public void testTimed16x16MultSeq() {
		Matrix large1 = new MatrixSeq(".\\src\\test\\resources\\16_16_A");
		Matrix large2 = new MatrixSeq(".\\src\\test\\resources\\16_16_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for sequential 16x16 timed multiply is: " + timer);
	}
	
	@Test
	public void testTimed32x32MultSeq() {
		Matrix large1 = new MatrixSeq(".\\src\\test\\resources\\32_32_A");
		Matrix large2 = new MatrixSeq(".\\src\\test\\resources\\32_32_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for sequential 32x32 timed multiply is: " + timer);
	}
	
	@Test
	public void testTimed64x64MultSeq() {
		Matrix large1 = new MatrixSeq(".\\src\\test\\resources\\64_64_A");
		Matrix large2 = new MatrixSeq(".\\src\\test\\resources\\64_64_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for sequential 64x64 timed multiply is: " + timer);
	}
	
	@Test
	public void testTimed128x128MultSeq() {
		Matrix large1 = new MatrixSeq(".\\src\\test\\resources\\128_128_A");
		Matrix large2 = new MatrixSeq(".\\src\\test\\resources\\128_128_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for sequential 128x128 timed multiply is: " + timer);
	}
	
	@Test
	public void testTimed256x256MultSeq() {
		Matrix large1 = new MatrixSeq(".\\src\\test\\resources\\256_256_A");
		Matrix large2 = new MatrixSeq(".\\src\\test\\resources\\256_256_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for sequential 256x256 timed multiply is: " + timer);
	}
	
	@Test
	public void testTimed512x512MultSeq() {
		Matrix large1 = new MatrixSeq(".\\src\\test\\resources\\512_512_A");
		Matrix large2 = new MatrixSeq(".\\src\\test\\resources\\512_512_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for sequential 512x512 timed multiply is: " + timer);
	}

	@Test
	public void testTimed1024x1024MultSeq() {
		Matrix large1 = new MatrixSeq(".\\src\\test\\resources\\1024_1024_A");
		Matrix large2 = new MatrixSeq(".\\src\\test\\resources\\1024_1024_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for sequential 1024x1024 timed multiply is: " + timer);
	}
	
	@Test
	public void testTimed2048x2048MultSeq() {
		Matrix large1 = new MatrixSeq(".\\src\\test\\resources\\2048_2048_A");
		Matrix large2 = new MatrixSeq(".\\src\\test\\resources\\2048_2048_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for sequential 2048x2048 timed multiply is: " + timer);
	}
	
	@Test
	public void testTimed4096x4096MultSeq() {
		Matrix large1 = new MatrixSeq(".\\src\\test\\resources\\4096_4096_A");
		Matrix large2 = new MatrixSeq(".\\src\\test\\resources\\4096_4096_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for sequential 4096x4096 timed multiply is: " + timer);
	}
}
