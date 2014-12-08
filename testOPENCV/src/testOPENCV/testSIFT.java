package testOPENCV;

import java.util.LinkedList;
import java.util.List;

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


public class testSIFT
{
public void run(Mat pathObject, Mat pathScene, String pathResult) {

	
  //  System.out.println("\nRunning FindObject");

    Mat img_object = pathObject; //0 = CV_LOAD_IMAGE_GRAYSCALE
    Mat img_scene = pathScene;

    
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
    MatOfDMatch gm = new MatOfDMatch();


    for(int i = 0; i < descriptor_object.rows(); i++){
        if(matchesList.get(i).distance < 3*min_dist ){
            good_matches.addLast(matchesList.get(i));
        }
    }

    gm.fromList(good_matches);
    Mat img_matches = new Mat();
    Features2d.drawMatches(img_object, keypoints_object, img_scene, keypoints_scene, gm, img_matches, new Scalar(0,255,0), new Scalar(0,0,255), new MatOfByte(), 2);

    LinkedList<Point> objList = new LinkedList<Point>();
    LinkedList<Point> sceneList = new LinkedList<Point>();

    List<KeyPoint> keypoints_objectList = keypoints_object.toList();
    List<KeyPoint> keypoints_sceneList = keypoints_scene.toList();

    for(int i = 0; i<good_matches.size(); i++){
        objList.addLast(keypoints_objectList.get(good_matches.get(i).queryIdx).pt);
        sceneList.addLast(keypoints_sceneList.get(good_matches.get(i).trainIdx).pt);
    }

    MatOfPoint2f obj = new MatOfPoint2f();
    obj.fromList(objList);

    MatOfPoint2f scene = new MatOfPoint2f();
    scene.fromList(sceneList);
    
System.out.println(good_matches.size());
    Mat obj_corners = new Mat(4,1,CvType.CV_32FC2);
    Mat scene_corners = new Mat(4,1,CvType.CV_32FC2);

    obj_corners.put(0, 0, new double[] {0,0});
    obj_corners.put(1, 0, new double[] {img_object.cols(),0});
    obj_corners.put(2, 0, new double[] {img_object.cols(),img_object.rows()});
    obj_corners.put(3, 0, new double[] {0,img_object.rows()});

   // System.out.println(String.format("Writing %s", pathResult));
   // Highgui.imwrite(pathResult, img_matches);
}


public static void main(String[] args) {
	
	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	
	SIFT.ImgR logo = new SIFT.ImgR();
	SIFT.ImgR searchImg = new SIFT.ImgR();
	
	Mat H1 = new Mat(288,352,CvType.CV_8U);
	Mat H2 = new Mat(288,352,CvType.CV_8U);
	for (int k = 1; k <= 299; k++) {

		String path_of_file = new String(
						"/Users/Wendy/Documents/workspace/video/");		
		String img1 = path_of_file + k + ".rgb";
		String img2 = path_of_file +(k+1) + ".rgb";
System.out.print(k+" and "+(k+1)+" ");
	logo.readImage(img1);
	searchImg.readImage(img2); 
	int cnt=0;
	for(int i=0; i<288; i++)
	   {
		for (int j=0; j<352; j++)
		{
			 H1.put(i,j, logo.input[cnt]);
			 H2.put(i,j, searchImg.input[cnt]);
			 cnt++;
		} 
	   }	
		
   new testSIFT().run(H1, H2, "result.jpg");
	}
}

}
