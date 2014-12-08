package testOPENCV;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class testDCT {
	public static double[][] DCT(int[][] a) {
		double output[][] = new double[8][8];
		for (int v = 0; v < 8; v++) {
			for (int u = 0; u < 8; u++) {
				for (int x = 0; x < 8; x++) {
					for (int y = 0; y < 8; y++) {
						output[v][u] += ((double) a[x][y])
								* Math.cos(((double) (2 * x + 1) * (double) u * Math.PI)
										/ (double) 16)
								* Math.cos(((double) (2 * y + 1) * (double) v * Math.PI)
										/ (double) 16);

					}
				}
				output[v][u] *= (double) (0.25)
						* ((u == 0) ? ((double) 1.0 / Math.sqrt(2))
								: (double) 1.0)
						* ((v == 0) ? ((double) 1.0 / Math.sqrt(2))
								: (double) 1.0);
			}
		}

		return output;
	}

	public static double low_freq_sum(byte[] bytes) {
		double avg_DCT = 0.0;
		for (int i = 0; i < 44; i++) {
			for (int j = 0; j < 36; j++) {
				int[][] arrayR = new int[8][8];
				int[][] arrayG = new int[8][8];
				int[][] arrayB = new int[8][8];
				int[][] Y = new int[8][8];
				int[][] U = new int[8][8];
				int[][] V = new int[8][8];

				int col = j * 8;
				int row = i * 8;
				for (int x = 0; x < 8; x++) {
					for (int y = 0; y < 8; y++) {
						arrayR[x][y] = bytes[352 * (row + x) + (col + y)] & 0xff;
						arrayG[x][y] = bytes[352 * 288 + 288 * (row + x)
								+ (col + y)] & 0xff;
						arrayB[x][y] = bytes[352 * 288 * 2 + 288 * (row + x)
								+ (col + y)] & 0xff;
						Y[x][y] = (int) (0.299 * arrayR[x][y] + 0.587
								* arrayG[x][y] + 0.114 * arrayB[x][y]);
						U[x][y] = (int) (-0.147 * arrayR[x][y] - 0.289
								* arrayG[x][y] + 0.436 * arrayB[x][y]);
						V[x][y] = (int) (0.615 * arrayR[x][y] - 0.515
								* arrayG[x][y] - 0.1 * arrayB[x][y]);
						arrayR[x][y] = Y[x][y];
						arrayG[x][y] = U[x][y];
						arrayB[x][y] = V[x][y];

					}
				}

				double[][] tempY = DCT(Y);
				double[][] tempU = DCT(U);
				double[][] tempV = DCT(V);
				double[] dct_result_Y = zigzag_conversion(tempY);
				double[] dct_result_U = zigzag_conversion(tempU);
				double[] dct_result_V = zigzag_conversion(tempV);
				long sum = 0;
				for (int i1 = 1; i1 <= 3; i1++) {
					sum += Math.pow(dct_result_U[i1], 2)
							+ Math.pow(dct_result_V[i1], 2);
				}
				avg_DCT += sum;
			}
		}

		return avg_DCT / 100.0;

	}

	public static double[] zigzag_conversion(double[][] a) {

		double[] result = new double[64];
		for (int j = 0; j < 8; j++) {
			int startIndex = 0;
			for (int k = 0; k < j; k++)
				startIndex += (k + 1);
			if (j % 2 == 0) {
				for (int l = 0; l < j + 1; l++)
					result[startIndex + l] = a[l][j - l];
			} else {
				for (int l = 0; l < j + 1; l++) {
					result[startIndex + l] = a[j - l][l];
				}
			}
		}
		for (int j = 1; j < 8; j++) {
			int startIndex = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8;
			for (int k = 1; k < j; k++)
				startIndex += 8 - k;
			if (j % 2 == 1) {
				for (int l = 0; l < 8 - j; l++) {
					result[startIndex + l] = a[l + j][7 - l];

				}
			} else {
				for (int l = 0; l < 8 - j; l++)
					result[startIndex + l] = a[7 - l][l + j];
			}

		}

		return result;

	}

	byte[] bytes;

	public static void main(String[] args) throws InterruptedException {
		List<Integer> low_freq_list = new ArrayList<Integer>();

		try {

			for (int j = 20; j <= 60; j++) {

				String path = (j <= 9) ? new String(
						"/Users/Wendy/Desktop/CS576_Project_Dataset_1/image00")
						: new String(
								"/Users/Wendy/Desktop/CS576_Project_Dataset_1/image0");
				String pathOfImg = path + j + ".rgb";
				File file = new File(pathOfImg);

				InputStream is = new FileInputStream(file);
				long len = file.length();
				final byte[] bytes = new byte[(int) len];
				int offset = 0;
				int numRead = 0;
				while (offset < bytes.length
						&& (numRead = is.read(bytes, offset, bytes.length
								- offset)) >= 0) {
					offset += numRead;
				}
				System.out.println(j + "   " + low_freq_sum(bytes) + " ");
				if (low_freq_sum(bytes) < 425000)
					low_freq_list.add(j);
			}
			System.out.println(low_freq_list.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

} 
