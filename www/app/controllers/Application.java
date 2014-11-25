package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import java.util.ArrayList;

import utils.FileFinder;

public class Application extends Controller {

    public static Result index() {
        String path = "public/img/", pattern = "\\S+\\.jpg";

        FileFinder ff = new FileFinder();
        ArrayList<String> filenames = ff.find(path, pattern);

        return ok(index.render(filenames));
    }

}
