package controllers;

import play.mvc.*;
import views.html.*;

import java.io.IOException;
import java.util.ArrayList;

import utils.FileFinder;
import play.Logger;

public class Application extends Controller {

    public static Result welcome() {
        String path = "public/img/", pattern = "\\S+\\.jpg";
        ArrayList<String> filenames = FileFinder.find(path, pattern);

        return ok(welcome.render(filenames));
    }

    public static Result slide(){
    	String path = "public/img/", pattern = "\\S+\\.jpg";
        ArrayList<String> filenames = FileFinder.find(path, pattern);
        return ok(slide.render(filenames));
    }
    
    public static Result faceSlide() throws IOException {
    	ArrayList<String> filenames = new ArrayList<String>();
        ArrayList<Boolean> selector = FileFinder.JSONParse("faceDetect.json");
        Logger.debug(Integer.toString(selector.size()));
        for (int i = 0; i < selector.size(); i++){
        	Logger.debug(Boolean.toString(selector.get(i)));
        	if (selector.get(i)){
        		filenames.add((i+1) + ".jpg");
        	}
        }
        return ok(slide.render(filenames));
    }
    
    public static Result cartoonSlide() throws IOException {
    	ArrayList<String> filenames = new ArrayList<String>();
        ArrayList<Boolean> selector = FileFinder.JSONParse("cartoon.json");
        Logger.debug(Integer.toString(selector.size()));
        for (int i = 0; i < selector.size(); i++){
        	Logger.debug(Boolean.toString(selector.get(i)));
        	if (selector.get(i)){
        		filenames.add((i+1) + ".jpg");
        	}
        }
        return ok(slide.render(filenames));
    }
}
