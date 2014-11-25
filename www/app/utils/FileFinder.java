package utils;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class FileFinder {
    public FileFinder(){
        ans = new ArrayList<String>();
    }
    public ArrayList<String> ans;
    public ArrayList<String> find(String path,String reg){
        Pattern pat = Pattern.compile(reg);
        File file = new File(path);
        File[] arr = file.listFiles();
        for(int i = 0;i < arr.length;i++){
            if(arr[i].isDirectory()){
                find(arr[i].getAbsolutePath(), reg);
            }

            Matcher mat = pat.matcher(arr[i].getAbsolutePath());

            if(mat.matches()){
                ans.add(arr[i].getName());
            }
        }
        return ans;
    }
}