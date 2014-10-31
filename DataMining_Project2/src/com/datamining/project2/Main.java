package com.datamining.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
	//new line added
	private String dmProject;
	private String dmProject2;
	private String shashank;
	private String ankur;
	private String ameya;
	
	public static void main(String[] args){
		/*List<DataPoint> list = new ArrayList<DataPoint>();
		DataPoint dp = new DataPoint();
		dp.distance = 3.12;
		list.add(dp);
		
		dp = new DataPoint();
		dp.distance = 1.12;
		list.add(dp);
		
		for(DataPoint dpt:list){
			System.out.println(dpt.distance);
		}
		
		Collections.sort(list);
		
		for(DataPoint dpt:list){
			System.out.println(dpt.distance);
		}*/
		
		List<Integer> tp = new ArrayList<Integer>();
		tp.add(2);
		tp.add(3);
		int j =0;
		for(int i=0;i<tp.size();i++){
			if(j==0){
				tp.add(4);
				tp.add(8);
				j=1;
			}
			System.out.println(tp.get(i));
		}
	}
}
