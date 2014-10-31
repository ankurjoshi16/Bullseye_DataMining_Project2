package com.datamining.project2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.datamining.project2Utils.ProjectUtils;

public class DBScanAlgorithm {

	private String fileName;
	private double epsilon = 0.265;
	private int minPoints = 4;
	private Map<Integer, DataPoint> initialDBScan = new LinkedHashMap<Integer, DataPoint>();
	private List<LinkedHashMap<Integer, DataPoint>> clusters;
	private List<Integer> outliers = new ArrayList<Integer>();
	private List<Integer> clustered = new ArrayList<Integer>();

	private DBScanAlgorithm(String fileName) throws NumberFormatException,
			IOException {
		this.fileName = fileName;
		String readLine;
		DataPoint dp;
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<Double> tempList;
		while ((readLine = bufferedReader.readLine()) != null) {
			String[] tempArray = readLine.split("\t");
			int tempKey = Integer.parseInt(tempArray[0]);
			tempList = new LinkedList<Double>();
			for (int i = 2; i < tempArray.length; i++) {
				tempList.add(Double.parseDouble(tempArray[i]));
			}
			dp = new DataPoint();
			dp.setCoordinates(tempList);
			dp.setIndex(tempKey);
			initialDBScan.put(tempKey, dp);

		}
		bufferedReader.close();

		List<List<Double>> basicList = new ArrayList<List<Double>>();

		for (DataPoint tm : initialDBScan.values()) {
			basicList.add(tm.getCoordinates());
		}
		for (int i = 0; i < basicList.get(0).size(); i++) {
			List<Double> tList = new ArrayList<Double>();
			for (List<Double> ltp : basicList) {
				tList.add(ltp.get(i));
			}

			double min = Collections.min(tList);
			double max = Collections.max(tList);
			if (max != min) {
				for (List<Double> ltp : basicList) {
					double temp = ltp.get(i);
					double norm = (temp - min) / (max - min);
					ltp.remove(i);
					ltp.add(i, norm);
				}
			}
		}
	}

	public void runDBScanAlgorithm() throws NumberFormatException, IOException {
		clusters = new ArrayList<LinkedHashMap<Integer, DataPoint>>();
		for (int a : initialDBScan.keySet()) {
			if (initialDBScan.get(a).isVisited == true) {
				continue;
			}

			else {
				initialDBScan.get(a).isVisited = true;
				List<Integer> neighborPts = regionQuery(a);
				if (neighborPts.size() < minPoints) {

					initialDBScan.get(a).isNoise = true;
					outliers.add(a);

				} else {
					LinkedHashMap<Integer, DataPoint> nextCluster = new LinkedHashMap<Integer, DataPoint>();
					clusters.add(nextCluster);
					expandCluster(a, neighborPts, nextCluster);
				}

			}

		}
		for (int i = 0; i < clusters.size(); i++) {
			System.out.println(clusters.get(i).size() + "   "
					+ clusters.get(i).keySet());
			clustered.addAll(clusters.get(i).keySet());
		}
		System.out.println("Total points clustered: " + clustered.size());

		for (int a : clustered) {
			if (outliers.contains(a)) {
				outliers.remove(new Integer(a));
				initialDBScan.get(a).isNoise=false;
			}
		}

	}

	private void expandCluster(int point, List<Integer> neighbors,
			Map<Integer, DataPoint> nextCluster) {

		nextCluster.put(point, initialDBScan.get(point));
		initialDBScan.get(point).isPartOfCluster = true;
		Queue<Integer> neighborsQ = new LinkedList<Integer>(neighbors);
		while (!neighborsQ.isEmpty()) {
			int head = neighborsQ.remove();
			if (initialDBScan.get(head).isVisited == false) {
				initialDBScan.get(head).isVisited = true;
				List<Integer> neighborPts2 = regionQuery(head);
				if (neighborPts2.size() >= minPoints) {
					for (int b : neighborPts2) {
						if (!neighborsQ.contains(b)) {
							neighborsQ.add(b);
						}
					}
				}
			}
			if (initialDBScan.get(head).isPartOfCluster == false) {
				nextCluster.put(head, initialDBScan.get(head));
				initialDBScan.get(head).isPartOfCluster = true;
			}
		}
	}

	private List<Integer> regionQuery(int a) {
		List<Integer> neighbors = new ArrayList<Integer>();
		for (int b : initialDBScan.keySet()) {
			double temp = ProjectUtils.getEuclideanDistance(initialDBScan
					.get(a).getCoordinates(), initialDBScan.get(b)
					.getCoordinates());
			if (temp <= epsilon) {
				neighbors.add(b);
			}
		}
		return neighbors;
	}

	public double calculateEpsilon() {
		List<Double> nthNearestDistance = new ArrayList<Double>();
		for (int i : initialDBScan.keySet()) {
			List<DataPoint> dataPoints = new ArrayList<DataPoint>();
			for (int j : initialDBScan.keySet()) {
				if (i == j) {
					continue;
				}
				DataPoint tdp = initialDBScan.get(j);
				tdp.distance = ProjectUtils.getEuclideanDistance(initialDBScan
						.get(i).getCoordinates(), tdp.getCoordinates());
				dataPoints.add(tdp);
			}
			Collections.sort(dataPoints, Collections.reverseOrder());
			System.out.println(i + "  Output");

			for (DataPoint dp : dataPoints) {
				// System.out.print("  " + dp.distance);
			}
			nthNearestDistance.add(dataPoints.get(minPoints).distance);
		}
		Collections.sort(nthNearestDistance);
		System.out.println("Decide from" + nthNearestDistance);
		return ProjectUtils.getEpsilion(nthNearestDistance);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<Integer> getOutliers() throws NumberFormatException, IOException {
		outliers = new ArrayList<Integer>();
		runDBScanAlgorithm();
		return outliers;
	}

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		DBScanAlgorithm dbscan = new DBScanAlgorithm("cho.txt");
		// dbscan.runDBScanAlgorithm();
		// dbscan.calculateEpsilon();
		System.out.println("Total Outliers Found  : "+ dbscan.getOutliers().size());

	}

}
