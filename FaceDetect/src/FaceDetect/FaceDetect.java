package FaceDetect;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

/*
 * This is the implementation of face detection using OpenCV
 * To run this java file, you need to specify the path of .xml file in line 45 and 46.
 * The input is .jpg file which you can set the path in line 34 and 39
 * The output is the sum of faces detected by two algorithm(Since if I only use one algorithm , some faces cannot be detected. So I use two and if
 * the sum is larger than 0, then the image contains a human face.
 * The result is kind of accurate, but some images cannot be detected.(Harry Porters)
 */
public class FaceDetect {
	// Total image numbers
	public final static int IMGNUM = 60;
	// Face plus plus account info
	public final static String API_KEY = "ad5a7c90a874993c7094f33b946590a6";
	public final static String API_SECRET = "0TE_QrafELVq-84AORn5Qair4Bd5oYcs";
	static boolean [] faceArray = new boolean[IMGNUM];
	static ArrayList<String> faceFile = new ArrayList<String>();

	public static void main(String[] args){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		 String path = "D:\\Workplace\\csci576-final\\www\\public\\img\\";

		 OpenCVDetect(path);
		 FaceppDetect(path);
		 for (int i = 0; i < IMGNUM; i++)
		 	if (faceArray[i])
		 		faceFile.add(i+1+".jpg");
		 WriteToDisk();
	}

	public static boolean WriteToDisk(){
		File writename = new File("face.json"); 
	    try {
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			out.write("[");
		        
		    for(int j = 0; j < faceFile.size() - 1; j++) {
		      	out.write(faceFile.get(j) + ",");
		    }
		    out.write(faceFile.get(faceFile.size() - 1) + "]\r\n");
		       
		    out.flush(); 
		    out.close();  
	    } catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	public static void FaceppDetect(String path){
		 HttpRequests httpRequests = new HttpRequests(API_KEY, API_SECRET, false, true);

		 for (int i = 1; i <= IMGNUM; i++){
	 	 if (faceArray[i-1] == true) continue;
	 	 File imgFile = new File(path+i+".jpg");
			if(!imgFile.exists()){
			  System.out.println("File:" + i + ".jpg doesn't exists!");
			}
			PostParameters postParameters = new PostParameters().setImg(imgFile).setAttribute("all");
			JSONObject result = null;
			try {
			  result = httpRequests.request("detection", "detect", postParameters);
			} catch (FaceppParseException e1) {
			  e1.printStackTrace();
			}

			try {
		  	 if (result.getJSONArray("face").length() != 0)
		  		 faceArray[i-1] = true;
			 // System.out.println(i + " th image has face: " + result.get("face").toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		 }
	}

	public static void OpenCVDetect(String path){
	  for(int j=1;j<=IMGNUM;j++){
		 String pathOfImg = path+j+".jpg";
			Mat image = Highgui.imread(pathOfImg);
			if (image.empty()) {
				System.out.println("Can not load images\n");
				return;
			}
			if (Detect(image) > 0){
		  	 faceArray[j-1] = true;
			}
			// System.out.println(j+" th image has face: "+ Detect(image)); // Detect  the number of HUMAN FACE.
	  }
	}

	public static int Detect(Mat image){
	  CascadeClassifier faceDetector1 = new CascadeClassifier("lib//lbpcascade_frontalface.xml");
	  CascadeClassifier faceDetector2 = new CascadeClassifier("lib//haarcascade_frontalface_alt.xml");

	  MatOfRect faceDetections1 = new MatOfRect();
	  faceDetector1.detectMultiScale(image, faceDetections1);

	  MatOfRect faceDetections2 = new MatOfRect();
	  faceDetector2.detectMultiScale(image, faceDetections2);

	  return (faceDetections1.toArray().length + faceDetections2.toArray().length);
	 }

}