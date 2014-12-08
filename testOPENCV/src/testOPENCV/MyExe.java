package testOPENCV;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;


public class MyExe {
   public static ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
   public static ArrayList<Integer> feature_index =new ArrayList<Integer>();
   public static ArrayList<Integer> similarity_record =new ArrayList<Integer>();

	public void run(Mat first, Mat second) {
		    Mat img_object = first; 
		    Mat img_scene = second;
		   Highgui.imwrite("first.jpg", img_object);
		   Highgui.imwrite("second.jpg", img_scene);
		    FeatureDetector detector = FeatureDetector.create(FeatureDetector.SIFT); //4 = SURF
		    MatOfKeyPoint keypoints_object = new MatOfKeyPoint();
		    MatOfKeyPoint keypoints_scene  = new MatOfKeyPoint();
		    detector.detect(img_object, keypoints_object);
		    detector.detect(img_scene, keypoints_scene);
		    DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.SIFT); //2 = SURF,3 = SIFT;
		    Mat descriptor_object = new Mat();
		    Mat descriptor_scene = new Mat() ;
		    extractor.compute(img_object, keypoints_object, descriptor_object);
		    extractor.compute(img_scene, keypoints_scene, descriptor_scene);
		    DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE); // 1 = FLANNBASED
		    MatOfDMatch matches = new MatOfDMatch();
		    matcher.match(descriptor_object, descriptor_scene, matches);
		    List<DMatch> matchesList = matches.toList();
		    Double max_dist = 0.0;
		    Double min_dist = 50.0;
		    for(int i = 0; i < descriptor_object.rows(); i++){
		        Double dist = (double) matchesList.get(i).distance;
		        if(dist < min_dist) min_dist = dist;
		        if(dist > max_dist) max_dist = dist;
		    }

		    LinkedList<DMatch> good_matches = new LinkedList<DMatch>();

		    for(int i = 0; i < descriptor_object.rows(); i++){
		        if(matchesList.get(i).distance < 3*min_dist ){
		            good_matches.addLast(matchesList.get(i));
		        }
		    }
           similarity_record.add(good_matches.size());
		
	
		}

   public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

	String fileName = "/Users/Wendy/Desktop/CS576_Project_Dataset_3/video10.rgb";
	float scale_width = 1;
	float scale_height = 1;
   	int width = (int) (352*scale_width);
	int height = (int) (288*scale_height);
	

    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    ArrayList<Mat> arrayOfH = new ArrayList<Mat>();
    // Use a label to display the image
    JFrame frame = new JFrame();
    JLabel label = new JLabel(new ImageIcon(img));
    frame.getContentPane().add(label, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);
    
    try {
	    File file = new File(fileName);
	    InputStream is = new FileInputStream(file);
	    
	    byte[] bytes = new byte[3*352*288];
	    
        int numRead = 0;
        int offset=0;
        int count=0;
        while (offset < file.length() ) {
        	System.currentTimeMillis();
        	int ind = 0;
        	numRead =is.read(bytes);
        	count++;
        		int[] input = new int[352*288];
            	Mat H = new Mat(288,352,CvType.CV_8U);
        		for(int y = 0; y < 288; y++){
        			for(int x = 0; x < 352; x++){
        		        byte r = bytes[ind];
        				byte g = bytes[ind+352*288];
        				byte b = bytes[ind+352*288*2]; 

        				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
    					int temp= (int) (0.299*(r & 0xff) + 0.587*(g & 0xff) + 0.114*(b & 0xff));
    			        input[y*352+x]= (temp & 0xff);
       				    H.put(y,x, input[ind]);
        				img.setRGB(x, y, pix);
        				ind++;

        			}
        		}
        		arrayOfH.add(H);
        	    		
        	frame.repaint();
        	offset += numRead;
        }
        
		is.close();
		
		for(int i=0;i<280;i=i+20){
			Mat h1 = arrayOfH.get(i);
			Mat h2 = arrayOfH.get(i+20);
		 new MyExe().run(h1, h2);
		}
		//
	  

			System.out.println("similarity_record .size is "+similarity_record.size());
		for(int i=0;i<images.size();i++){
			String path = "image"+i+".jpg";
			File outputfile = new File(path);
		ImageIO.write(images.get(i), "jpg", outputfile);
		}
		File result = new File("10.txt");
		 BufferedWriter out = new BufferedWriter(new FileWriter(result));  
         for(int k=0;k<similarity_record.size();k++){
        	 System.out.println("is writing "+ similarity_record.get(k));
        	 out.write(similarity_record.get(k) + " ");
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