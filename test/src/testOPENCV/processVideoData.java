package testOPENCV;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class processVideoData {
	public static ArrayList<Integer> key_frame =new ArrayList<Integer>();
	public static ArrayList<Integer> filtered_key_frame =new ArrayList<Integer>();
static boolean [] selected = new boolean[300];
	public static void main(String []  args) throws Exception{
		
		read("09.txt");
		if(key_frame.contains(280)!=true)
		key_frame.add(300);
		if(key_frame.contains(20)!=true&&key_frame.contains(0)!=true)
		key_frame.add(1);
		Collections.sort(key_frame);
		
		for(int i=0;i<key_frame.size();i++){
			System.out.println(key_frame.get(i));
		}
		WriteJSON();
	}
public static void read(String fileName) throws Exception{
	InputStream    fis;
	BufferedReader br;
	String         line;
    String[] ss = new String[300];

	fis = new FileInputStream(fileName);
	br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
	while ((line = br.readLine()) != null) {
	    // Deal with the line
ss=line.split(" ");
HashMap<Integer,Integer> hm = new HashMap<Integer,Integer>();
for(int i=0;i<ss.length;i++){
	hm.put(i, Integer.valueOf(ss[i]));
}
List<Map.Entry<Integer,Integer>> list=new ArrayList<Entry<Integer, Integer>>();  
list.addAll(hm.entrySet());  

ValueComparator vc=new ValueComparator();  
Collections.sort(list,vc);  
//System.out.println(list);
int i=0;
for(Iterator<Map.Entry<Integer,Integer>> it=list.iterator();it.hasNext();)  
{  
	String temp= it.next().toString();
	int t = temp.indexOf('=');
	String index=temp.substring(0, t);
	int value =Integer.valueOf(temp.substring(t+1));	
	int frame_num = Integer.valueOf(index);
	if(frame_num!=0)
	key_frame.add(frame_num*20);
    i++;
    if(i>5)
	break;
} 
	}
	// Done with the file
	br.close();
	br = null;
	fis = null;
}
public static boolean WriteJSON(){
	File writename = new File("video9.json"); 
    try {
		writename.createNewFile();
		BufferedWriter out = new BufferedWriter(new FileWriter(writename));
		out.write("[");
	    for(int j = 0; j < key_frame.size() - 1; j++) {
	      	out.write("9."+key_frame.get(j) + ".jpg,");
	    }
	    out.write("9."+key_frame.get(key_frame.size() - 1) + ".jpg]\r\n");
	       
	    out.flush(); 
	    out.close();  
    } catch (IOException e) {
		e.printStackTrace();
	}

	return true;
}	


}
class ValueComparator implements Comparator<Map.Entry<Integer, Integer>>  
{  
    public int compare(Map.Entry<Integer, Integer> mp1, Map.Entry<Integer, Integer> mp2)   
    {  
        return  mp1.getValue()-mp2.getValue();  
    }  
}  
