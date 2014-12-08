package opencvtest;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.features2d.*;

public class main {
    
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    try {
	        final int originWidth = 352;
	        final int originHeight = 288;
	        final int originSize = 352 * 288;
	        
	        File root = new File("/users/tzhong/desktop/dataset");
	        File[] files = root.listFiles();
	        Mat[] mats = new Mat[300];
	        int filenum = 1;
	        for(File file: files) {   
	            if(file.isHidden()) continue;
		        InputStream is = new FileInputStream(file);
		        
		        byte[] bytes = new byte[originSize * 3];
		        byte[][][] matBytes = new byte[3][originHeight][originWidth];
		        
		        int numRead=is.read(bytes, 0, originSize * 3);
		        if(numRead > 0) {
		        	int bytesCount = 0;
		        	for(int k = 0; k < 3; k++) {
		        		for(int y = 0; y < originHeight; y++) {
		        			for(int x = 0; x < originWidth; x++) {
		        				matBytes[k][y][x] = bytes[bytesCount];
		        				bytesCount++;
		        			}
		        		}
		    		}
		        }
		        mats[filenum - 1] = new Mat(288, 352, CvType.CV_8UC3);
		        byte[] pixel = new byte[3];
		        for(int y = 0; y < originHeight; y++) {
		        	for(int x = 0; x < originWidth; x++) {
		        		pixel[0] = matBytes[2][y][x];
		        		pixel[1] = matBytes[1][y][x];
		        		pixel[2] = matBytes[0][y][x];
		        		mats[filenum - 1].put(y, x, pixel);
		        	}
		        }
		        //String filename = "jpgs/" + filenum + ".jpg";
		        //Highgui.imwrite(filename, mats[filenum - 1]);
		        is.close();
		        filenum++;
	        }
	        siftCalculator sift = new siftCalculator(mats);
	        int ans = 0;
	        File writename = new File("SiftOutput.txt"); 
            writename.createNewFile(); 
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            for(int i = 0; i < filenum - 2; i++) {
            	out.write(i + " : ");
            	System.out.println(i);
            	for(int j = i + 1; j < filenum - 1; j++) {
            		ans = sift.calPointsNum(i, j);
            		out.write(ans + " ");
            		//System.out.println(i + " " + j + ":" + ans);
            	}
            	out.write("\r\n");
            }
            out.flush(); 
            out.close();  
	        
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}

}
