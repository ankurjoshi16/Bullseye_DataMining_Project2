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

		double min = getEuclideanDistance(
				dataPoint,
				kClusters.get(0).get(
						new ArrayList<Integer>(kClusters.get(0).keySet())
								.get(0)));
		int index = 0;

		for (int i = 1; i < kClusters.size(); i++) {
			double temp = getEuclideanDistance(
					dataPoint,
					kClusters.get(i).get(
							new ArrayList<Integer>(kClusters.get(i).keySet())
									.get(0)));
			if (temp < min) {
				min = temp;
				index = i;
			}
		}

		return index;
	}

	public static int getClusterWithMaximumProximityToCentroid(
			List<Double> dataPoint, List<List<Double>> kCentroids) {

		double min = getEuclideanDistance(dataPoint, kCentroids.get(0));
		int index = 0;

		for (int i = 1; i < kCentroids.size(); i++) {
			double temp = getEuclideanDistance(dataPoint, kCentroids.get(i));
			if (temp < min) {
				min = temp;
				index = i;
			}
		}
		return index;
	}

	public static List<Double> getCentroid(Map<Integer, List<Double>> map) {
		List<Double> centroid = new ArrayList<Double>();
		Map.Entry<Integer, List<Double>> nextentry = map.entrySet().iterator()
				.next();
		int size = (nextentry).getValue().size();
		for (int i = 0; i < size; i++) {
			double[] coordinates = new double[map.size()];
			int k = 0;
			Double sum = 0.0;
			for (Map.Entry<Integer, List<Double>> entry : map.entrySet()) {
				coordinates[k++] = entry.getValue().get(i);
			}
			for (int j = 0; j < coordinates.length; j++)
				sum += coordinates[j];
			sum = sum / coordinates.length;
			centroid.add(sum);
		}
		return centroid;
	}
	
	public static List<List<Double>> getInitialCen(Map<Integer,List<Double>> map, int num){
    	
    	List<List<Double>> init = new ArrayList<List<Double>>(num);
    	
    	for (int i = 0; i < num; i++) {
    	    List<Double> list = new ArrayList<Double>();
    	    init.add(list);
    	}
    	
    	//System.out.println(init.size());
    	Map.Entry<Integer,List<Double>> nextentry = map.entrySet().iterator().next();
		int size = (nextentry).getValue().size();
    	
		for (int i=0; i<size; i++)
		{
			ArrayList<Double> coordinates=new ArrayList<Double>();
			
			for (Map.Entry<Integer,List<Double>> entry : map.entrySet()) {
				coordinates.add(entry.getValue().get(i));
		    }
			
			Double max=Collections.max(coordinates);
			Double min=Collections.min(coordinates);
			
			//System.out.println("Max:"+max+"Min:"+min);
			Double diff=(max-min)/num;
			
			for(List<Double> a: init)
			{
				a.add(min);
				min=min+diff;
			}
			
		}
    
    	return init;
    }
}
