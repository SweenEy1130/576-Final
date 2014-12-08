package testOPENCV;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

public class readFile {
	public static void main(String []  args) throws Exception{
		read("demo.txt");
	}
public static void read(String fileName) throws Exception{
	InputStream    fis;
	BufferedReader br;
	String         line;
    String[] ss = new String[20];

	fis = new FileInputStream(fileName);
	br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
	while ((line = br.readLine()) != null) {
	    // Deal with the line
ss=line.split(",");
for(int i=0;i<ss.length;i++)
	System.out.println(ss[i]);
	}

	// Done with the file
	br.close();
	br = null;
	fis = null;
}
}
