import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opencv.core.Core;


public class main {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		siftcatt catt = new siftcatt();
		siftcat cat = new siftcat();
		//catt.start();
		ArrayList<ArrayList> catagory = new ArrayList<ArrayList>();
		catagory = cat.start();
        histCat hcat = new histCat(catagory);
        ArrayList<ArrayList> afterhist = hcat.catSingle();
        finalcat fcat = new finalcat(hcat.findhist(), afterhist);
        ArrayList<ArrayList> delsingle = fcat.start();

        File writename = new File("Sift.json"); 
        try {
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			out.write(delsingle.size() + "\r\n");
			int count = 0;
			for(int i = 0; i < delsingle.size(); i++) {
				count += delsingle.get(i).size();
				out.write("[");
				for(int j = 0; j < delsingle.get(i).size() - 1; j++) {
					out.write((int)delsingle.get(i).get(j) + 1 + ".jpg,");
				}
				out.write(delsingle.get(i).get(delsingle.get(i).size() - 1) + ".jpg]\r\n");
			}
	        out.flush(); 
	        out.close();  
	        System.out.println(count);
		} catch (IOException e) {
			e.printStackTrace();
		} 
        
	}
	
}
