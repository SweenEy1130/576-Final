import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.opencv.core.Mat;


public class finalcat {
	private final int totalsize = 300;
	private int[][] match;
	private Mat[] hists;
	private int[][] countPoints;
	private ArrayList<ArrayList> origin;
	
	public finalcat(Mat[] h, ArrayList<ArrayList> o) {
		match = new int[totalsize][totalsize];
        countPoints = new int[301][301];
		hists = new Mat[totalsize + 1];
		
		hists = h;
		origin = o;
		
		File file = new File("SiftOutput.txt");
	    BufferedReader reader = null;
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
	    
        for(int i = 0; i < 299; i++) {
        	for(int j = 0; i + j + 1 < 300; j++) {
        		countPoints[i+1][i+j+2] = match[i][j];
        		countPoints[i+j+2][i+1] = match[i][j];
        	}
        }
	}
	
	public ArrayList<ArrayList> start() {    
        ArrayList<Integer> single = new ArrayList<Integer>();
        for(int i = origin.size() - 1; i >= 0; i--) {
        	if(origin.get(i).size() == 1) {
        		single.add((int)origin.get(i).get(0));
        		origin.remove(i);
        	}
        }
        for(int i = single.size() - 1; i >= 0; i--) {
        	int index = single.get(i);
        	int max = 0;
        	int maxbro = -1;
        	for(int j = 60; j < totalsize; j++) {
        		if(countPoints[index + 1][j + 1] > max) {
        			max = countPoints[index + 1][j + 1];
        			maxbro = j;
        		}
        	}
        	if(max > 35) {
        		boolean flag = false;
        		boolean flag2 = false;
        		for(int t = 0; t < origin.size(); t++) {
        			if(flag == true)
        				break;
        			for(int s = 0; s < origin.get(t).size(); s++) {
        				if((int)origin.get(t).get(s) == maxbro) {
        					origin.get(t).add(index);
        					flag = true;
        					flag2 = true;
        					single.remove(i);
        					break;
        				}
        			}
        		}
        		if(flag2 == false) {
        			for(int m = i - 1; m >= 0; m--) {
        				if(single.get(m) == maxbro) {
        					ArrayList<Integer> temp = new ArrayList<Integer>();
        					temp.add(index);
        					temp.add(maxbro);
        					origin.add(temp);
        					single.remove(i);
        					single.remove(m);
        					i--;
        					break;
        				}
        			}
        		}
        	}
        }
        
//		for(int i = 0; i < origin.size(); i++) {
//			for(int j = 0; j < origin.get(i).size(); j++) {
//				System.out.print(origin.get(i).get(j) + " ");
//			}
//			System.out.println();
//		}
//		for(int i = 0; i < single.size(); i++) {
//			System.out.println(single.get(i));
//		}
//		System.out.println();
		
		histogram hist = new histogram();
		for(int i = single.size() - 1; i >= 0; i--) {
			System.out.println(single.size());
        	int index = single.get(i);
        	double min = 1;
        	int maxbro = -1;
        	for(int j = 60; j < totalsize; j++) {
        		if(j == index)
        			continue;
        		double tt = hist.calsim(index, j);
        		if(tt < min) {
        			min = tt;
        			maxbro = j;
        		}
        	}
        	System.out.println(maxbro);
    		boolean flag = false;
    		boolean flag2 = false;
    		for(int t = 0; t < origin.size(); t++) {
    			if(flag == true)
    				break;
    			for(int s = 0; s < origin.get(t).size(); s++) {
    				if((int)origin.get(t).get(s) == maxbro) {
    					origin.get(t).add(index);
    					flag = true;
    					flag2 = true;
    					single.remove(i);
    					break;
    				}
    			}
    		}
    		if(flag2 == false) {
    			for(int m = i - 1; m >= 0; m--) {
    				System.out.println(single.get(m));
    				if(single.get(m) == maxbro) {
    					ArrayList<Integer> temp = new ArrayList<Integer>();
    					temp.add(index);
    					temp.add(maxbro);
    					origin.add(temp);
    					single.remove(i);
    					single.remove(m);
    					i--;
    					break;
    				}
    			}
    		}
        }
		
		for(int i = 0; i < origin.size(); i++) {
			for(int j = 0; j < origin.get(i).size(); j++) {
				System.out.print(origin.get(i).get(j) + " ");
			}
			System.out.println();
		}
        
        ArrayList<ArrayList> ans = origin;
        return ans;
	}
}
