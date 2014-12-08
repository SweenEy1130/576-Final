package testOPENCV;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;


public class testIlluminance {
	static boolean [] cartoon = new boolean [60];
	static ArrayList<Integer> cartoonFile = new ArrayList<Integer>();
	byte[] bytes;

	public static double low_freq_sum(byte[] bytes) {
		long sum = 0;
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
						sum+=Y[x][y]/100;

					}
				}


			}
		}

		return sum/100.0;

	}

public static boolean WriteJSON(){
		File writename = new File("cartoon.json"); 
	    try {
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			out.write("[");
		    for(int j = 0; j < cartoonFile.size() - 1; j++) {
		      	out.write(cartoonFile.get(j) + ".jpg,");
		    }
		    out.write(cartoonFile.get(cartoonFile.size() - 1) + "]\r\n");
		       
		    out.flush(); 
		    out.close();  
	    } catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}	

	public static void main(String[] args) throws InterruptedException {
		List<Integer> illu = new ArrayList<Integer>();
		try {

			for (int j =1; j <= 60; j++) {

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
				if (low_freq_sum(bytes) > 600)
					{
					illu.add(j);
					cartoon[j-1]=true;
					cartoonFile.add(j);
					}
			}
			System.out.println(illu.size());
			WriteJSON();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

} 
