package com.cheuks.bin.anythingtest.data_structure;

public class MonteCarloPI {

	public static double MontePi(int point) {
		int sum = 0;//点数
		double x, y;
		for (int i = 0; i < point; i++) {
			x = Math.random();
			y = Math.random();
			if ((x * x + y * y) <= 1)
				sum++;
		}

		return 4.0 * sum / point;
	}

	public static void main(String[] args) {
		System.out.println("PI:" + MontePi(50000000));
	}

}
