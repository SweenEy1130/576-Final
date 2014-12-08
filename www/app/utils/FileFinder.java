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

	public static ArrayList<String> JSONParse(String selector) throws IOException{
		InputStream    fis;
		BufferedReader br;

		fis = new FileInputStream("label/" + selector);
		br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
		String arr = br.readLine();
		String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
		ArrayList<String> results = new ArrayList<String>();

		for (int i = 0; i < items.length; i++)
			results.add(items[i]);

		// Done with the file
		br.close();
		return results;
	}

	public static ArrayList<String> SIFTParse(String selector, int idx) throws IOException{
		InputStream    fis;
		BufferedReader br;

		fis = new FileInputStream("label/" + selector);
		br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
		String arr = null;
		for (int i = 0; i <= idx; i++) arr = br.readLine();
		String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
		ArrayList<String> results = new ArrayList<String>();

		for (int i = 0; i < items.length; i++)
    		results.add(items[i]);

		// Done with the file
		br.close();
		return results;
	}

	public static ArrayList<ArrayList<String>> SIFTParseAll(String selector) throws IOException{
		InputStream    fis;
		BufferedReader br;

		fis = new FileInputStream("label/" + selector);
		br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
		String arr = br.readLine();
		int n = Integer.valueOf(arr);

		ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();
		for (int k = 0; k < n; k++){
			arr = br.readLine();
			String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
			ArrayList<String> results = new ArrayList<String>();

			for (int i = 0; i < items.length; i++) {
				results.add(items[i]);
			}
			ret.add(results);
		}

		// Done with the file
		br.close();
		return ret;
	}

	public static ArrayList<ArrayList<String>> VideoParseAll(String selector) throws IOException{
		InputStream    fis;
		BufferedReader br;

		fis = new FileInputStream("label/" + selector);
		br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));

		ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();

		String arr;;
		while ((arr = br.readLine()) != null){
			String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
			ArrayList<String> results = new ArrayList<String>();

			for (int i = 0; i < items.length; i++) {
				results.add(items[i]);
			}
			ret.add(results);
		}

		// Done with the file
		br.close();
		return ret;
	}
}