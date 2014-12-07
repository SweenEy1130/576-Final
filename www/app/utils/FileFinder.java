package utils;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.charset.Charset;

public class FileFinder {
	public static ArrayList<String> find(String path, String reg){
		ArrayList<String> ans = new ArrayList<String>();
		Pattern pat = Pattern.compile(reg);
		File file = new File(path);
		File[] arr = file.listFiles();
		for(int i = 0;i < arr.length;i++){
			Matcher mat = pat.matcher(arr[i].getAbsolutePath());

			if(mat.matches()){
				ans.add(arr[i].getName());
			}
		}
		return ans;
	}

	public static ArrayList<Boolean> JSONParse(String selector) throws IOException{
		InputStream    fis;
		BufferedReader br;

		fis = new FileInputStream("label/" + selector);
		br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
		String arr = br.readLine();
		String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
		ArrayList<Boolean> results = new ArrayList<Boolean>();

		for (int i = 0; i < items.length; i++) {
		    try {
		    	if (items[i].equals("true"))
		    		results.add(true);
		    	else 
		    		results.add(false);
		    } catch (NumberFormatException nfe) {};
		}

		// Done with the file
		br.close();
		return results;
	}
}