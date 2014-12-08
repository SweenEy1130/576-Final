import java.io.File;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


public class histogram {
	private Mat[] mats;
	private Mat[] hists;
	private final int totalsize = 300;
	public histogram() {
		mats = new Mat[totalsize+1];
		hists = new Mat[totalsize+1];
		
		MatOfInt histSize=new MatOfInt(50,60);
		MatOfInt channels = new MatOfInt(0, 1);
		MatOfFloat histRanges = new MatOfFloat( 0,180,0,256 );
		
		String path = "/users/tzhong/desktop/jpgs/";
        for(int i = 1; i < 301; i++) {
        	String newpath = path + i + ".jpg";
        	mats[i] = Highgui.imread(newpath);
        	hists[i] = new Mat();
        	
    		Imgproc.cvtColor(mats[i], mats[i], Imgproc.COLOR_RGB2HSV);
            mats[i].convertTo(mats[i], CvType.CV_32F);
            ArrayList<Mat> bgr_planes1= new ArrayList<Mat>();
            Core.split(mats[i], bgr_planes1);
            boolean accumulate = false;
            Imgproc.calcHist(bgr_planes1, channels, new Mat(), hists[i], histSize, histRanges, accumulate);
        	Core.normalize(hists[i], hists[i], 0, hists[i].rows(), Core.NORM_MINMAX, -1, new Mat());
    	    hists[i].convertTo(hists[i], CvType.CV_32F);
        }
	}
	public double calsim(int p1, int p2) {
		double ans = Imgproc.compareHist(hists[p1+1], hists[p2+1], Imgproc.CV_COMP_BHATTACHARYYA);
		return ans;
	}
	public Mat[] findhist() {
		return hists;
	}
}
