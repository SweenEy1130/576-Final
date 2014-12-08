package opencvtest;

import java.util.LinkedList;
import java.util.List;

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

public class siftCalculator {
	private Mat[] mats;
	private MatOfKeyPoint[] keypoints;
	private Mat[] descriptors;
	private DescriptorExtractor extractor;
	private FeatureDetector detector;
	private DescriptorMatcher matcher;
	public siftCalculator(Mat[] matIn) {
		mats = matIn;
		extractor = DescriptorExtractor.create(DescriptorExtractor.SIFT);
		detector = FeatureDetector.create(FeatureDetector.SIFT);
		matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
		keypoints = new MatOfKeyPoint[mats.length];
		descriptors = new Mat[mats.length];
		for(int i = 0; i < 300; i++) {
			keypoints[i] = new MatOfKeyPoint();
			detector.detect(mats[i], keypoints[i]);
			descriptors[i] = new Mat();
			extractor.compute(mats[i], keypoints[i], descriptors[i]);
			System.out.println(i + "is over.");
		}
	}
	public int calPointsNum(int first, int second) {

	    MatOfDMatch matches = new MatOfDMatch();
	    Mat temp1 = descriptors[first];
	    Mat temp2 = descriptors[second];
	       
	    matcher.match(temp1, temp2, matches);
	    List<DMatch> matchesList = matches.toList();
	    

	    Double max_dist = 0.0;
	    Double min_dist = 50.0;

	    for(int i = 0; i < temp1.rows(); i++){
	        Double dist = (double) matchesList.get(i).distance;
	        if(dist < min_dist) min_dist = dist;
	        if(dist > max_dist) max_dist = dist;
	    }
	    
	    int ans = 0;
	    for(int i = 0; i < temp1.rows(); i++){
	        if(matchesList.get(i).distance < 3*min_dist ){
	        	ans++;
	        }
	    }
	    
	    return ans;
	}
}
