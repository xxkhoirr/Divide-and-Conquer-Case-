package DivideNConquer2;

import java.util.*;

public class MatrixMultiplication {

	private static Scanner in;

	public static void main(String[] args) {
		System.out.print("Inputkan dimensi matriks : ");
		in = new Scanner(System.in);
		int n = in.nextInt();
		int[][] A, B;
		
		A = generateMatrix(n);
		System.out.println("Matrix A :");
		displayMatrix(A, n);

		B = generateMatrix(n);
		System.out.println("Matrix B :");
		displayMatrix(B, n);

		System.out.println("-----------------\nMatrix Mult :\n");
		System.out.println("1. Divide & Conquer : ");
		displayMatrix(multiply(A, B), n);
		System.out.println();
		System.out.println("2. Classic Matrix Multiplication : ");
		displayMatrix(classicMM(A, B, n), n);
		long start = System.nanoTime();
		multiply(A, B);
		long end = System.nanoTime();
		System.out.println();
		System.out.println("Running Time Divide&Conquer : " + (end - start));
		start = System.nanoTime();
		classicMM(A, B, n);
		end = System.nanoTime();
		System.out.println("Running time Classic : " + (end - start));

	}

	public static int[][] generateMatrix(int n) {
		Random r = new Random();
		int[][] matrix = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = r.nextInt(100);
			}
		}
		return matrix;
	}

	public static void displayMatrix(int[][] matrix, int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.printf("%10d", matrix[i][j]);
			}
			System.out.println();
		}
	}

	public static int[][] classicMM(int[][] A, int[][] B, int n) {
		int[][] C = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = 0;
			}
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					C[i][j] += A[i][k] * B[k][j];
				}
			}
		}
		return C;
	}

	static int[][] multiply(int[][] A, int[][] B) {
		int n = A.length;
		int[][] C = new int[n][n];

		if (n == 1) {
			C[0][0] = A[0][0] * B[0][0];
			return C;
		} else if (n % 2 == 0) {
			int[][] A11 = new int[n / 2][n / 2];
			int[][] A12 = new int[n / 2][n / 2];
			int[][] A21 = new int[n / 2][n / 2];
			int[][] A22 = new int[n / 2][n / 2];
			int[][] B11 = new int[n / 2][n / 2];
			int[][] B12 = new int[n / 2][n / 2];
			int[][] B21 = new int[n / 2][n / 2];
			int[][] B22 = new int[n / 2][n / 2];

			deconstructMatrix(A, A11, 0, 0);
			deconstructMatrix(A, A12, 0, n / 2);
			deconstructMatrix(A, A21, n / 2, 0);
			deconstructMatrix(A, A22, n / 2, n / 2);
			deconstructMatrix(B, B11, 0, 0);
			deconstructMatrix(B, B12, 0, n / 2);
			deconstructMatrix(B, B21, n / 2, 0);
			deconstructMatrix(B, B22, n / 2, n / 2);

			int[][] C11 = addMatrix(multiply(A11, B11), multiply(A12, B21), n / 2);
			int[][] C12 = addMatrix(multiply(A11, B12), multiply(A12, B22), n / 2);
			int[][] C21 = addMatrix(multiply(A21, B11), multiply(A22, B21), n / 2);
			int[][] C22 = addMatrix(multiply(A21, B12), multiply(A22, B22), n / 2);

			constructMatrix(C11, C, 0, 0);
			constructMatrix(C12, C, 0, n / 2);
			constructMatrix(C21, C, n / 2, 0);
			constructMatrix(C22, C, n / 2, n / 2);

		} else {
			int[][] copy1 = new int[n + 1][n + 1];
			int[][] copy2 = new int[n + 1][n + 2];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					copy1[i][j] = A[i][j];
					copy2[i][j] = B[i][j];
				}
			}
			int[][] copy3 = multiply(copy1, copy2);
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					C[i][j] = copy3[i][j];
				}
			}
		}
		return C;

	}

	private static int[][] addMatrix(int[][] A, int[][] B, int n) {

		int[][] C = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] + B[i][j];
			}
		}
		return C;
	}

	private static void constructMatrix(int[][] initialMatrix, int[][] newMatrix, int a, int b) {

		int y = b;

		for (int i = 0; i < initialMatrix.length; i++) {
			for (int j = 0; j < initialMatrix.length; j++) {
				newMatrix[a][y++] = initialMatrix[i][j];
			}
			y = b;
			a++;
		}
	}

	private static void deconstructMatrix(int[][] initialMatrix, int[][] newMatrix, int a, int b) {

		int y = b;
		for (int i = 0; i < newMatrix.length; i++) {
			for (int j = 0; j < newMatrix.length; j++) {
				newMatrix[i][j] = initialMatrix[a][y++];
			}
			y = b;
			a++;
		}
	}

}