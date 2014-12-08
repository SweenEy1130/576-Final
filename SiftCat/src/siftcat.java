import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class siftcat {
	public ArrayList<ArrayList> start() {
		File file = new File("SiftOutput.txt");
        BufferedReader reader = null;
        int[][] match = new int[300][300];
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 0;
            while ((tempString = reader.readLine()) != null) {
            	int count = 0;
            	int startSpace = tempString.indexOf(" ", 0);
            	while(startSpace != -1 && startSpace < tempString.length() - 1) {
            		int endSpace = tempString.indexOf(" ", startSpace + 1);
            		String sub = tempString.substring(startSpace + 1, endSpace);
            		//System.out.println(sub);
            		match[line][count] = Integer.parseInt(sub);
            		count++;
            		startSpace = endSpace;
            	}
                line++;
                //System.out.println(line + ":" + count);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        
        int groupMax = 25;
        
        int[][] countPoints = new int[301][301];
        for(int i = 0; i < 299; i++) {
        	for(int j = 0; i + j + 1 < 300; j++) {
        		countPoints[i][i+j+1] = match[i][j];
        		countPoints[i+j+1][i] = match[i][j];
        	}
        }
        for(int i = 1; i < 301; i++) {
        	countPoints[i][i] = -1;
        }
        
        ArrayList records = new ArrayList();
        ArrayList<ArrayList> finalrecords = new ArrayList<ArrayList>();
        boolean[] mark = new boolean[301];
        boolean[] finalmark = new boolean[301];
        double keyRatio = 0;
        int keyCount = 20;
        while(keyCount > 0) {
        	records = new ArrayList();        
	        mark = new boolean[301];
	        while(true) {
	        	//select center points randomly
	            int ran = 0;
	            int ranCount = 1000; //finishing condition
	            boolean sign = false;
	            while(true) {
	            	ran = (int)(Math.random() * 240 + 60);
	            	if(mark[ran] == true)
	            		continue;
	            	if(ranCount-- < 0)
	            		break;
	            	int count = 0;
	            	for(int i = 1; i < 301; i++) {
	            		if(mark[i] == true || i == ran)
	            			continue;
	            		if(countPoints[i][ran] > 30) {
	            			count++;
	            		}
	            	}
	            	if(count > 4) { //choosing condition
	            		sign = true;
	            		break;
	            	}
	            }
	            if(sign == false) //finish random process
	            	break;
	            ArrayList<Integer> list = new ArrayList<Integer>();
	            list.add(ran);
	            for(int i = 1; i < 300; i++) { // find neighbors
	            	if(mark[i] == true || i == ran)
	            		continue;
	            	if(countPoints[i][ran] > 20) {
	            		list.add(i);
	            	}
	            }
	            if(list.size() <= groupMax) { // group size is no more than 20, record directly
	            	//System.out.print(ran + " : ");
	            	for(int i = 0; i < list.size(); i++) {
	            		mark[list.get(i)] = true;
	            		//System.out.print(" " + list.get(i));
	            	}
	            	//System.out.print("\r\n");
	            	records.add(list);
	            }
	            else { // group size if more than 25, delete points
		            ArrayList<Integer> sumlist = new ArrayList<Integer>();
		            for(int i = 0; i < list.size(); i++) {
		            	int countsum = 0;
		            	for(int j = 0; j < list.size(); j++) {
		            		if(j == i)
		            			continue;
		            		countsum += countPoints[list.get(i)][list.get(j)];
		            	}
		            	sumlist.add(countsum);
		            }
		            Collections.sort(sumlist);
		            int b = sumlist.get(sumlist.size() - groupMax - 1);
		            ArrayList<Integer> dellist = new ArrayList<Integer>();
		            for(int i = 0; i < list.size(); i++) {
		            	if(sumlist.get(i) <= b) {
		            		continue;
		            	}
		            	dellist.add(list.get(i)); // records the remaining points
		            }
		            //System.out.print(ran + " : ");
	            	for(int i = 0; i < dellist.size(); i++) {
	            		mark[dellist.get(i)] = true;
	            		//System.out.print(" " + dellist.get(i));
	            	}
	            	//System.out.print("\r\n");
	            	records.add(dellist);
	            }
	        }
	        
	        //add the single points into the groups
	        int recordSize = records.size();
	        for(int i = 0; i < 300; i++) {
	        	if(mark[i] == true)
	        		continue;
	        	for(int j = 0; j < recordSize; j++) {
	        		ArrayList<Integer> tempList = (ArrayList<Integer>)records.get(j);
	        		int groupSize = tempList.size();
	        		int gcount = 0;
	        		for(int k = 0; k < groupSize; k++) {
	        			if(match[i][tempList.get(k)] > 16)
	        				gcount++;
	        		}
	        		if(gcount * 3 >= groupSize) { // the points can be the neighbor of more than one third of the group
	        			tempList.add(i);
	        			records.set(j, tempList);
	        			mark[i] = true;
	        			break;
	        		}
	        	}
	        }
	        System.out.print("\r\n");
//	        for(int i = 0; i < recordSize; i++) {
//	        	ArrayList<Integer> tempList = (ArrayList<Integer>)records.get(i);
//	        	int groupSize = tempList.size();
//	        	for(int j = 0; j < groupSize; j++) {
//	        		System.out.print(tempList.get(j) + " ");
//	        	}
//	        	System.out.print("\r\n");
//	        }
	        int singleSize = 0;
	        for(int i = 60; i < 300; i++) {
	        	if(mark[i] == false) {
	        		singleSize++;
//	        		System.out.println(i);
	        	}
	        }
	        double x = (double)recordSize * (double)recordSize * (double)(240 - singleSize) / (double)singleSize;
	        if(keyRatio < x) {
	        	keyRatio = x;
	        	keyCount = 10;
	        	System.out.println(x);
	        	finalrecords = records;
	        	for(int i = 0; i < 300; i++)
	        		finalmark[i] = mark[i];
	        }
	        else {
	        	keyCount--;
	        }
        }
        
        for(int i = 60; i < 300; i++) {
        	if(finalmark[i] == true)
        		continue;
        	ArrayList<Integer> newList = new ArrayList<Integer>();
        	newList.add(i);
        	for(int j = i + 1; j < 300; j++) {
        		if(match[i][j] > 20) {
        			finalmark[i] = true;
        			finalmark[j] = true;
        			newList.add(j);
        		}
        	}
        	finalrecords.add(newList);
        }
        
        int count = 0;
        for(int i = 0; i < finalrecords.size(); i++) {
        	ArrayList<Integer> tempList = (ArrayList<Integer>)finalrecords.get(i);
        	int groupSize = tempList.size();
        	for(int j = 0; j < groupSize; j++) {
        		System.out.print(tempList.get(j) + " ");
        		count++;
        	}
        	System.out.print("\r\n");
        }
        System.out.print(count + "hi!!!!");
//        for(int i = 60; i < 300; i++) {
//        	if(finalmark[i] == false) {
//        		System.out.println(i);
//        	}
//        }
//        File writename = new File("sift.json"); 
//        try {
//			writename.createNewFile();
//			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
//			int[] arr; 
//			out.write(finalrecords.size() + "\r\n");
//			for(int i = 0; i < finalrecords.size(); i++) {
//				out.write("[");
//	        	ArrayList<Integer> tempList = (ArrayList<Integer>)finalrecords.get(i);
//	        	int groupSize = tempList.size();
//	        	for(int j = 0; j < groupSize - 1; j++) {
//	        		out.write(tempList.get(j) + 1 + ".jpg,");
//	        	}
//	        	out.write(tempList.get(groupSize - 1) + 1 + ".jpg]\r\n");
//	        }
//	        out.flush(); 
//	        out.close();  
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
        return finalrecords;
	}
}
