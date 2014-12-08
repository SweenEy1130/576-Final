package testOPENCV;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.FeatureDetector;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class testColorHist {

   public static void main(String[] args) {
	   boolean [] cover = new boolean[300];
       System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
       String path = "/Users/Wendy/Desktop/out4/4.";
       new HashMap<Integer,Integer>();
       for(int i=1;i<=280;i=i+20){    
    	   int max=0;
    
    	  String pathOfImg1 = path+i+".jpg";
    	  String pathOfImg2 = path+(i+20)+".jpg";
    	  Mat image1 = Highgui.imread(pathOfImg1);
    	  Mat image2 = Highgui.imread(pathOfImg2);
          double sim =similarity_colorHist(image1,image2);//Output the similarity coefficient based on color histogram
          int sim1 = (int) (sim*100);
      //  System.out.println(sim1);
  
        System.out.println(sim);
    
      
       }
   }

public static  double similarity_colorHist(Mat img1, Mat img2){
	{
	        Imgproc.cvtColor(img1, img1, Imgproc.COLOR_RGB2HSV); 
	        Imgproc.cvtColor(img2, img2, Imgproc.COLOR_RGB2HSV); 
	        img1.convertTo(img1, CvType.CV_32F);
	        img2.convertTo(img2, CvType.CV_32F);
	        Mat hist1 = new Mat();
	        Mat hist2 = new Mat();

			MatOfInt histSize=new MatOfInt(50,60);
			MatOfInt channels = new MatOfInt(0, 1);
			
			MatOfFloat histRanges = new MatOfFloat( 0,180,0,256 );
	        
	        
	        ArrayList<Mat> bgr_planes1= new ArrayList<Mat>();
	        ArrayList<Mat> bgr_planes2= new ArrayList<Mat>();
	        Core.split(img1, bgr_planes1);
	        Core.split(img2, bgr_planes2);
	        
	        boolean accumulate = false;
	        Imgproc.calcHist(bgr_planes1, channels, new Mat(), hist1, histSize, histRanges, accumulate);
        	Core.normalize(hist1, hist1, 0, hist1.rows(), Core.NORM_MINMAX, -1, new Mat());
	        Imgproc.calcHist(bgr_planes2, channels, new Mat(), hist2, histSize, histRanges, accumulate);
        	Core.normalize(hist2, hist2, 0, hist2.rows(), Core.NORM_MINMAX, -1, new Mat());
		        img1.convertTo(img1, CvType.CV_32F);
		        img2.convertTo(img2, CvType.CV_32F);
		        hist1.convertTo(hist1, CvType.CV_32F);
		        hist2.convertTo(hist2, CvType.CV_32F);
		
		        /** Compute the difference of histogram values**/
	        	double compare =1- Imgproc.compareHist(hist1, hist2, Imgproc.CV_COMP_BHATTACHARYYA);

	        	return compare;
	}

}

}