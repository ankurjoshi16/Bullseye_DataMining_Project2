package com.datamining.project2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KMeansCluster {

	private List<Double> centriod;
	private List<DataPoint> clusterPoints = new ArrayList<DataPoint>();

	public KMeansCluster(List<Double> centriod) {
		this.centriod = centriod;
		clusterPoints = new ArrayList<DataPoint>();
	}

	public List<Double> getCentriod() {
		return centriod;
	}

	public void addPoint(DataPoint dp) {
		clusterPoints.add(dp);
	}

	public List<DataPoint> getAllClusterPoints() {
		return clusterPoints;
	}
	
	public List<Integer> getAllKeys(){
		List<Integer> keys = new ArrayList<Integer>();
		for(DataPoint dp : getAllClusterPoints()){
			keys.add(dp.getIndex());
		}
		return keys;
	}
	
	public List<Double> getFinalCentriod() {

		List<Double> centroid = new ArrayList<Double>();
		int dimensions = getAllClusterPoints().get(0).getCoordinates().size();
		int size = getAllClusterPoints().size();
		for (int i = 0; i < dimensions; i++) {
			double[] coordinates = new double[size];
			int k = 0;
			Double sum = 0.0;

			for (DataPoint dp : getAllClusterPoints()) {
				coordinates[k++] = dp.getCoordinates().get(i);
			}

			for (int j = 0; j < coordinates.length; j++)
				sum += coordinates[j];
			sum = sum / coordinates.length;
			centroid.add(sum);
		}
		return centroid;
	}

}
