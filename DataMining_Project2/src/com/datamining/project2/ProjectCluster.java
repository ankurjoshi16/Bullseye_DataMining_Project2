package com.datamining.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.datamining.project2Utils.ProjectUtils;

public class ProjectCluster implements Comparable<ProjectCluster>{

	private List<Double> centriod;
	private List<DataPoint> clusterPoints = new ArrayList<DataPoint>();
	public double minDiff;
	public String identifier;
	public ProjectCluster nearestC;
	public int index;

	public ProjectCluster() {
		clusterPoints = new ArrayList<DataPoint>();
	}

	public ProjectCluster(List<Double> centriod) {
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

	public List<Integer> getAllKeys() {
		List<Integer> keys = new ArrayList<Integer>();
		for (DataPoint dp : getAllClusterPoints()) {
			keys.add(dp.getIndex());
		}
		return keys;
	}

	public void addPoints(List<DataPoint> points) {
		for (DataPoint dp : points) {
			clusterPoints.add(dp);
		}
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

	public void getMinDiff(List<ProjectCluster> ip) {
		Map<Double, ProjectCluster> matrix = new LinkedHashMap<Double, ProjectCluster>();
		for (int i = 0; i < ip.size(); i++) {
			if (this == ip.get(i)) {
				continue;
			} else {
				double minDistance = Double.MAX_VALUE;
				for (DataPoint dp : this.getAllClusterPoints()) {
					for (DataPoint dp2 : ip.get(i).getAllClusterPoints()) {
						double temp = ProjectUtils.getEuclideanDistance(
								dp.getCoordinates(), dp2.getCoordinates());
						if (temp < minDistance) {
							minDistance = temp;
						}
					}
				}
				matrix.put(minDistance, ip.get(i));
			}
		}

		double totalMin = Collections.min(matrix.keySet());
		this.minDiff = totalMin;
		this.nearestC = matrix.get(totalMin);
	}

	@Override
	public int compareTo(ProjectCluster o) {
		// TODO Auto-generated method stub
		if(this.minDiff==o.minDiff)
			return 0;
		else if(this.minDiff<o.minDiff)
			return -1;
		else return 1;
	}

}
