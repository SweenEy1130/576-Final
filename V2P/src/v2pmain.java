import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;


public class v2pmain {
	
	public static ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	public static ArrayList<Integer> feature_index =new ArrayList<Integer>();
	public static ArrayList<Integer> similarity_record =new ArrayList<Integer>();

	public static void main(String[] args) {

			String fileName = "/users/tzhong/desktop/CS576_Project_Videos_3/video10.rgb";
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		   	int width = 352;
			int height = 288;
			
		    
		    try {
			    File file = new File(fileName);
			    InputStream is = new FileInputStream(file);
			    
			    byte[] bytes = new byte[3*352*288];
			    
		        int numRead = 0;
		        int offset=0;
		        int num = 1;
		        String spath = "/users/tzhong/desktop/CS576_Project_Videos_3/out10/10.";
		        while (offset < file.length() ) {
		        	Mat mat = new Mat(288, 352, CvType.CV_8UC3);
		        	System.currentTimeMillis();
		        	int ind = 0;
		        	numRead =is.read(bytes);
		        	byte[] pixel = new byte[3];
		        	for(int y = 0; y < 288; y++){
		        			for(int x = 0; x < 352; x++){
		        		        byte r = bytes[ind];
		        				byte g = bytes[ind+352*288];
		        				byte b = bytes[ind+352*288*2]; 
		        				pixel[2] = r;
		        				pixel[1] = g;
		        				pixel[0] = b;
		        				mat.put(y, x, pixel);
		        				ind++;
		        			}
		        		}
		        	Highgui.imwrite(spath + num + ".jpg", mat);
		        	num++;
		        	offset += numRead;
		        }
				is.close();
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		    
		   }

}
