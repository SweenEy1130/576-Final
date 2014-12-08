package controllers;

import play.mvc.*;
import views.html.*;

import java.io.IOException;
import java.util.ArrayList;

import utils.FileFinder;
import play.Logger;

public class Application extends Controller {

	public static Result welcome() throws IOException{
		// String path = "public/img/", pattern = "\\S+\\.jpg";
		// ArrayList<String> filenames = FileFinder.find(path, pattern);
		ArrayList<String> filenames = new ArrayList<String>();
		String video = FileFinder.VideoParseAll("video.json").get(0).get(0);
		String face = FileFinder.JSONParse("face.json").get(2);
		String scene = FileFinder.JSONParse("scene.json").get(5);
		String cartoon = FileFinder.JSONParse("cartoon.json").get(5);
		String sift = FileFinder.SIFTParseAll("sift.json").get(1).get(2);

		return ok(welcome.render(face, scene, sift, cartoon, video));
	}

	public static Result videoSlide() throws IOException{
		// String path	= "public/video/", pattern = "\\S+\\.jpg";
		// ArrayList<String> filenames = FileFinder.find(path, pattern);

		ArrayList<ArrayList<String> > filenames = FileFinder.VideoParseAll("video.json");
		// ArrayList<String> filenames = new ArrayList<String>();
		// for (int i = 1; i <= 10; i++) {
			// filenames.add(i+".1.jpg");
		// }
		for (int i = 0; i < filenames.size(); i++) {
			Logger.debug(filenames.get(i).get(0));
		}

		return ok(video.render(filenames));
	}

	public static Result slide(){
		String path = "public/img/", pattern = "\\S+\\.jpg";
		ArrayList<String> filenames = FileFinder.find(path, pattern);
		return ok(slide.render(filenames));
	}

	public static Result faceSlide() throws IOException{
		ArrayList<String> filenames = FileFinder.JSONParse("face.json");
		return ok(slide.render(filenames));
	}

	public static Result sceneSlide() throws IOException{
		ArrayList<String> filenames = FileFinder.JSONParse("scene.json");
		return ok(slide.render(filenames));
	}

	public static Result cartoonSlide() throws IOException{
		ArrayList<String> filenames = FileFinder.JSONParse("cartoon.json");
		return ok(slide.render(filenames));
	}

	public static Result siftSlide(int classno) throws IOException{
		ArrayList<String> filenames = FileFinder.SIFTParse("sift.json", classno);
		return ok(slide.render(filenames));
	}

	public static Result siftSlideAll() throws IOException{
		ArrayList<ArrayList<String> > siftclass = FileFinder.SIFTParseAll("sift.json");

		return ok(thumbnail.render(siftclass));
	}
}
