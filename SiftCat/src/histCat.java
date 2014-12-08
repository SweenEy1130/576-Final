import java.util.ArrayList;

import org.opencv.core.Mat;


public class histCat {
	private ArrayList<ArrayList> catagory;
	histogram hist = new histogram();
	public histCat(ArrayList c) {
		catagory = c;
	}
	public ArrayList catSingle() {
		ArrayList<ArrayList> ans = new ArrayList<ArrayList>();
		ArrayList<Integer> tempArr = new ArrayList<Integer>();
		for(int i = 0; i < catagory.size(); i++) {
			ArrayList temp = (ArrayList)catagory.get(i);
			if(temp.size() == 1) {
				tempArr.add((int)temp.get(0));
			}
		}
		boolean[] mark = new boolean[tempArr.size()];
		for(int i = 0; i < tempArr.size(); i++) {
			if(mark[i] == true)
				continue;
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.add(tempArr.get(i));
			mark[i] = true;
			for(int j = i + 1; j < tempArr.size(); j++) {
				if(hist.calsim(tempArr.get(i), tempArr.get(j)) < 0.45) {
					temp.add(tempArr.get(j));
					mark[j] = true;
				}
			}
			ans.add(temp);
		}
		ArrayList<ArrayList> finalans = new ArrayList<ArrayList>();
		for(int i = 0; i < catagory.size(); i++) {
			if(catagory.get(i).size() > 1) {
				finalans.add(catagory.get(i));
			}
		}
		for(int i = 0; i < ans.size(); i++) {
			finalans.add(ans.get(i));
		}
		int count = 0;
		for(int i = 0; i < finalans.size(); i++) {
			for(int j = 0; j < finalans.get(i).size(); j++) {
				System.out.print(finalans.get(i).get(j) + " ");
				count++;
			}
			System.out.println("T T");
		}
		System.out.println(count);
		return finalans;
	}
	public Mat[] findhist() {
		return hist.findhist();
	}
}
