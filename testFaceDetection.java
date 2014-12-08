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
/*
 * This is the implementation of face detection using OpenCV
 * To run this java file, you need to specify the path of .xml file in line 45 and 46.
 * The input is .jpg file which you can set the path in line 34 and 39
 * The output is the sum of faces detected by two algorithm(Since if I only use one algorithm , some faces cannot be detected. So I use two and if 
 * the sum is larger than 0, then the image contains a human face.
 * The result is kind of accurate, but some images cannot be detected.(Harry Porters)
 */
public class testFaceDetection {

   public static void main(String[] args) {
	   System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
       String path = "/Users/Wendy/Documents/workspace/testOPENCV/jpgs/";
     

       new HashMap<Integer,Integer>();
      for(int j=21;j<=60;j++){
    	  String pathOfImg = path+j+".jpg";
          Mat image = Highgui.imread(pathOfImg);      
          System.out.println(j+" th image has face: "+ faceDetection(image)); // Detect  the number of HUMAN FACE. 
     	
      }
  
   }
public static int faceDetection(Mat image){
    CascadeClassifier faceDetector1 = new CascadeClassifier("/Users/wendy/desktop/lbpcascade_frontalface.xml");
    CascadeClassifier faceDetector2 = new CascadeClassifier("/Users/wendy/desktop/haarcascade_frontalface_alt.xml");

    MatOfRect faceDetections1 = new MatOfRect();
    faceDetector1.detectMultiScale(image, faceDetections1);

    MatOfRect faceDetections2 = new MatOfRect();
    faceDetector2.detectMultiScale(image, faceDetections2);
  return (faceDetections1.toArray().length+faceDetections2.toArray().length);
    }

}