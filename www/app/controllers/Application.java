package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import java.util.ArrayList;

import utils.FileFinder;

public class Application extends Controller {

    public static Result welcome() {
        String path = "public/img/", pattern = "\\S+\\.jpg";

        FileFinder ff = new FileFinder();
        ArrayList<String> filenames = ff.find(path, pattern);

        return ok(welcome.render(filenames));
    }

    public static Result slide() {
        String path = "public/img/", pattern = "\\S+\\.jpg";

        FileFinder ff = new FileFinder();
        ArrayList<String> filenames = ff.find(path, pattern);

        return ok(slide.render(filenames));
    }
}
