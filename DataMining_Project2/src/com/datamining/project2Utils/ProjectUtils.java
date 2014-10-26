package com.datamining.project2Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// add method
	}

	public static double getEuclideanDistance(List<Double> firstList,
			List<Double> secondList) {
		double sum = 0;
		for (int i = 0; i < firstList.size(); i++) {
			sum = sum + Math.pow((firstList.get(i) - secondList.get(i)), 2);
		}
		return Math.sqrt(sum);
	}

	public static int getClusterWithMaximumProximity(List<Double> dataPoint,
			List<Map<Integer, List<Double>>> kClusters) {

		double min = getEuclideanDistance(dataPoint, kClusters.get(0).get(new ArrayList<Integer>
		(kClusters.get(0).keySet()).get(0)));
		int index= 0;
		
		for(int i=1;i<kClusters.size();i++){
			double temp = getEuclideanDistance(dataPoint, kClusters.get(i).get(new ArrayList<Integer>
			(kClusters.get(i).keySet()).get(0)));
			if(temp<min){
				min = temp;
				index = i;
			}
		}
		
		return index;
	}
}
