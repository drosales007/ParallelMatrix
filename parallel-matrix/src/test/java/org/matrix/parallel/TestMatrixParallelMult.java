package org.matrix.parallel;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.matrix.common.Matrix;

public class TestMatrixParallelMult {

	@Test
	public void testMultiplyBlock() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\10_10_A");
		large1.setOpMult(new MultParallel2());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\10_10_B");
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\10_10_MULT_A_B");
		Matrix matrixResult = large1.multiply(large2);
        assertTrue(matrixResult.toString().trim().equals(expected.toString().trim()));
	}
	@Test
	public void timeMultiplyBlocks500x500Threaded() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\500_500_A");
		large1.setOpMult(new MultParallel2());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\500_500_B");
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\500_500_A_B_MULT");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 500x500 block multiply is: " + timer);
		assertTrue(matrixResult.toString().trim().equals(expected.toString().trim()));
	}
	
	@Test
	public void timeMultiplyBlocks1000x1000Threaded() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\1000_1000_A");
		large1.setOpMult(new MultParallel2());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\1000_1000_B");
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\1000_1000_A_B_MULT");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 1000x1000 block multiply is: " + timer);
		assertTrue(matrixResult.toString().trim().equals(expected.toString().trim()));
	}
	
	@Test
	public void timeMultiplyBlocks10x10Threaded() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\10_10_A");
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\10_10_B");
		large1.setOpMult(new MultParallel2());
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\10_10_MULT_A_B");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 10x10 block multiply is: " + timer);
		assertTrue(matrixResult.toString().trim().equals(expected.toString().trim()));
	}
	
	@Test
	public void timeMultiplyBlocks200x200Threaded() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\200_200_A");
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\200_200_B");
		large1.setOpMult(new MultParallel2());
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\200_200_A_B_MULT");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 200x200 block multiply is: " + timer);
		assertTrue(matrixResult.toString().equals(expected.toString()));
	}
	
	@Test
	public void timeMultiplyBlocks300x300Threaded() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\300_300_A");
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\300_300_B");
		large1.setOpMult(new MultParallel2());
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\300_300_A_B_MULT");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 300x300 block multiply is: " + timer);
		assertTrue(matrixResult.toString().equals(expected.toString()));
	}
	
	@Test
	public void timeMultiplyDNS100x100Threaded() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\100_100_M1");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\100_100_M2");
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\100_100_M1_M2_MULT_Format");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		matrixResult.writeMatrixToFile("newtestout");
		System.out.println("The time for parallel 100x100 DNS multiply is: " + timer);
		assertTrue(matrixResult.toString().trim().equals(expected.toString().trim()));
	}
	
	@Test
	public void timeMultiplyDNS10x10Threaded() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\10_10_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\10_10_B");
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\10_10_MULT_A_B");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		matrixResult.writeMatrixToFile("10x10");
		System.out.println("The time for parallel 10x10 DNS multiply is: " + timer);
		assertTrue(matrixResult.toString().equals(expected.toString()));
	}
	
	@Test
	public void timeMultiplyDNS200x200Threaded() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\200_200_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\200_200_B");
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\200_200_A_B_MULT_Format");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 200x200 DNS multiply is: " + timer);
		assertTrue(matrixResult.toString().equals(expected.toString()));
	}
	
	@Test
	public void timeMultiplyDNS300x300Threaded() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\300_300_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\300_300_B");
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\300_300_A_B_MULT_DNS");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 300x300 DNS multiply is: " + timer);
		assertTrue(matrixResult.toString().equals(expected.toString()));
	}
	
	@Test
	public void timeMultiplyDNS500x500Threaded() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\500_500_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\500_500_B");
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\500_500_A_B_MULT_DNS");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 500x500 DNS multiply is: " + timer);
		assertTrue(matrixResult.toString().equals(expected.toString()));
	}
	
	@Test
	public void timeMultiplyDNS1000x1000Threaded() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\1000_1000_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\1000_1000_B");
		Matrix expected = new MatrixParallel(".\\src\\test\\resources\\1000_1000_A_B_MULT_DNS");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 1000x1000 DNS multiply is: " + timer);
		assertTrue(matrixResult.toString().equals(expected.toString()));
	}
	
	@Test
	public void benchMultiplyBlocks2x2() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\2_2_A");
		large1.setOpMult(new MultParallel2());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\2_2_B");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 2x2 block multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyBlocks4x4() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\4_4_A");
		large1.setOpMult(new MultParallel2());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\4_4_B");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 4x4 block multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyBlocks8x8() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\8_8_A");
		large1.setOpMult(new MultParallel2());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\8_8_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 8x8 block multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyBlocks16x16() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\16_16_A");
		large1.setOpMult(new MultParallel2());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\16_16_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 16x16 block multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyBlocks32x32() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\32_32_A");
		large1.setOpMult(new MultParallel2());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\32_32_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 32x32 block multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyBlocks64x64() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\64_64_A");
		large1.setOpMult(new MultParallel2());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\64_64_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 64x64 block multiply is: " + timer);
	}

	@Test
	public void benchMultiplyBlocks128x128() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\128_128_A");
		large1.setOpMult(new MultParallel2());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\128_128_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 128x128 block multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyBlocks256x256() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\256_256_A");
		large1.setOpMult(new MultParallel2());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\256_256_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 256x256 block multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyBlocks512x512() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\512_512_A");
		large1.setOpMult(new MultParallel2());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\512_512_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 512x512 block multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyBlocks1024x1024() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\1024_1024_A");
		large1.setOpMult(new MultParallel2());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\1024_1024_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 1024x1024 block multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyBlocks2048x2048() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\2048_2048_A");
		large1.setOpMult(new MultParallel2());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\2048_2048_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 2048x2048 block multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyBlocks4096x4096() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\4096_4096_A");
		large1.setOpMult(new MultParallel2());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\4096_4096_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 4096x4096 block multiply is: " + timer);
	}


	
	@Test
	public void benchMultiplyDNS2x2() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\2_2_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\2_2_B");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 2x2 DNS multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyDNS4x4() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\4_4_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\4_4_B");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 4x4 DNS multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyDNS8x8() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\8_8_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\8_8_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 8x8 DNS multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyDNS16x16() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\16_16_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\16_16_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 16x16 DNS multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyDNS32x32() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\32_32_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\32_32_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 32x32 DNS multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyDNS64x64() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\64_64_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\64_64_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 64x64 DNS multiply is: " + timer);
	}

	@Test
	public void benchMultiplyDNS128x128() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\128_128_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\128_128_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 128x128 DNS multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyDNS256x256() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\256_256_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\256_256_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 256x256 DNS multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyDNS512x512() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\512_512_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\512_512_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 512x512 DNS multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyDNS1024x1024() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\1024_1024_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\1024_1024_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 1024x1024 DNS multiply is: " + timer);
	}
	
	@Test
	public void benchMultiplyDNS2048x2048() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\2048_2048_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\2048_2048_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 2048x2048 DNS multiply is: " + timer);
	}
	@Test
	public void benchMultiplyDNS4096x4096() {
		Matrix large1 = new MatrixParallel(".\\src\\test\\resources\\4096_4096_A");
		large1.setOpMult(new MultParallel3());
		Matrix large2 = new MatrixParallel(".\\src\\test\\resources\\4096_4096_A");
		long start = System.currentTimeMillis();
		Matrix matrixResult = large1.multiply(large2);
		long end = System.currentTimeMillis();
		long timer = end-start;
		System.out.println("The time for parallel 4096x4096 DNS multiply is: " + timer);
	}
}
