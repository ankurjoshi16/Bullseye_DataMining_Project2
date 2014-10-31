package com.datamining.project2;

import java.util.ArrayList;
import java.util.List;

public class DataPoint implements Comparable<DataPoint> {

	private int index;
	public double distance;
	public boolean isNoise=false;
	public boolean isPartOfCluster = false;
	public boolean isVisited = false;


	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	private List<Double> coordinates = new ArrayList<Double>();

	

	public List<Double> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Double> coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public int compareTo(DataPoint o) {
		// TODO Auto-generated method stub
		if (this.distance > o.distance) {
			return 1;
		} else if (this.distance < o.distance) {
			return -1;
		} else {
			return 0;
		}

	}

}
