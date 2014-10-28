package com.datamining.project2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.datamining.project2Utils.ProjectUtils;

public class KMeansClustering {

	private int k;
	private String inputFile;
	private Map<Integer, List<Double>> kMeansBasicIteration;
	private Set<Integer> allPossibleValues = new HashSet<Integer>();

	public KMeansClustering(int k, String inputFile) throws IOException {
		this.k = k;
		this.inputFile = inputFile;
		kMeansBasicIteration = new LinkedHashMap<Integer, List<Double>>();
		String readLine;
		BufferedReader bufferedReader = null;

		FileReader fileReader = new FileReader(inputFile);
		bufferedReader = new BufferedReader(fileReader);
		List<Double> tempList;
		while ((readLine = bufferedReader.readLine()) != null) {
			String[] tempArray = readLine.split("\t");
			int tempKey = Integer.parseInt(tempArray[0]);
			tempList = new LinkedList<Double>();
			for (int i = 2; i < tempArray.length; i++) {
				tempList.add(Double.parseDouble(tempArray[i]));
			}
			kMeansBasicIteration.put(tempKey, tempList);
		}
		bufferedReader.close();
	}

	public void runKMeansClusteringAlgorithm() throws IOException {

		List<Map<Integer, List<Double>>> kClusters = new ArrayList<Map<Integer, List<Double>>>();

		int tempCounter = kMeansBasicIteration.size() / k;
		int count = 1;

		List<List<Double>> kCentroids = new ArrayList<List<Double>>();
		List<List<Double>> prevKCentroids = new ArrayList<List<Double>>();

		while (tempCounter * count <= kMeansBasicIteration.size()) {
			int key = new ArrayList<Integer>(kMeansBasicIteration.keySet())
					.get(tempCounter * count - 1);
			kCentroids.add(kMeansBasicIteration.get(key));
			Map<Integer, List<Double>> singleCluster = new LinkedHashMap<Integer, List<Double>>();
			kClusters.add(singleCluster);
			count++;
		}

		int iteration = 1;
		while (true) {
			if (iteration != 1) {
				prevKCentroids.clear();
				prevKCentroids.addAll(kCentroids);
				kCentroids.clear();
				for (int n = 0; n < kClusters.size(); n++) {
					List<Double> nthCentroid = ProjectUtils
							.getCentroid(kClusters.get(n));
					kCentroids.add(nthCentroid);
				}

				if (kCentroids.equals(prevKCentroids)) {
					break;
				}

				for (int n = 0; n < kClusters.size(); n++) {
					kClusters.get(n).clear();
				}
			}
			iteration++;
			for (int key : kMeansBasicIteration.keySet()) {
				int index = ProjectUtils
						.getClusterWithMaximumProximityToCentroid(
								kMeansBasicIteration.get(key), kCentroids);
				kClusters.get(index).put(key, kMeansBasicIteration.get(key));
			}
		}

		System.out.println(prevKCentroids);
		System.out.println(kCentroids);

		System.out.println(kMeansBasicIteration);
		for (int i = 0; i < kClusters.size(); i++)
			System.out.println(kClusters.get(i).keySet().size() + "    "
					+ kClusters.get(i).keySet());

	}

	public void runKMeansWithPurDataPointsInRandomeClusterInit() {

		List<Map<Integer, List<Double>>> kClusters = new ArrayList<Map<Integer, List<Double>>>();

		for (int i = 0; i < k; i++) {
			Map<Integer, List<Double>> singleCluster = new LinkedHashMap<Integer, List<Double>>();
			kClusters.add(singleCluster);
		}
		for (int key : kMeansBasicIteration.keySet()) {
			kClusters.get(generateRandomNumberInRange(k)).put(key,
					kMeansBasicIteration.get(key));
		}

		List<List<Double>> kCentroids = new ArrayList<List<Double>>();
		List<List<Double>> prevKCentroids = new ArrayList<List<Double>>();

		for (int i = 0; i < kClusters.size(); i++) {
			kCentroids.add(ProjectUtils.getCentroid(kClusters.get(i)));
		}

		int iteration = 0;

		for (int i = 0; i < kClusters.size(); i++)
			System.out.println(kClusters.get(i).keySet().size() + "    "
					+ kClusters.get(i).keySet());

		while (true) {

			prevKCentroids.clear();
			prevKCentroids.addAll(kCentroids);
			kCentroids.clear();
			for (int n = 0; n < kClusters.size(); n++) {
				List<Double> nthCentroid = ProjectUtils.getCentroid(kClusters
						.get(n));
				kCentroids.add(nthCentroid);
			}

			if (kCentroids.equals(prevKCentroids) && iteration != 0) {
				break;
			}

			for (int n = 0; n < kClusters.size(); n++) {
				kClusters.get(n).clear();
			}

			iteration++;
			for (int key : kMeansBasicIteration.keySet()) {
				int index = ProjectUtils
						.getClusterWithMaximumProximityToCentroid(
								kMeansBasicIteration.get(key), kCentroids);
				kClusters.get(index).put(key, kMeansBasicIteration.get(key));
			}
		}
		for (int i = 0; i < kClusters.size(); i++)
			System.out.println(kClusters.get(i).keySet().size() + "    "
					+ kClusters.get(i).keySet());
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		KMeansClustering kMeansClustering = new KMeansClustering(5, "cho.txt");
		// kMeansClustering.runKMeansClusteringAlgorithm();
		//kMeansClustering.runKMeansWithPurDataPointsInRandomeClusterInit();
		kMeansClustering.runKMeansWithDataPointsChoppedInNSlots();

	}

	public void runKMeansWithScaledInitialClusters() {

		List<Map<Integer, List<Double>>> kClusters = new ArrayList<Map<Integer, List<Double>>>();

		for (int i = 0; i < k; i++) {
			Map<Integer, List<Double>> singleCluster = new LinkedHashMap<Integer, List<Double>>();
			kClusters.add(singleCluster);
		}
		System.out.println(kMeansBasicIteration.size());
		List<List<Double>> kCentroids = ProjectUtils.getInitialCen(
				kMeansBasicIteration, k);
		for(List<Double> list: kCentroids){
			System.out.println();
		}
		List<List<Double>> prevKCentroids = new ArrayList<List<Double>>();

		int iteration = 0;

		while (true) {
			System.out.println("iteration"+iteration);
			if (iteration != 0) {
				prevKCentroids.clear();
				prevKCentroids.addAll(kCentroids);
				kCentroids.clear();
				for (int n = 0; n < kClusters.size(); n++) {
					List<Double> nthCentroid = ProjectUtils
							.getCentroid(kClusters.get(n));
					kCentroids.add(nthCentroid);
				}

				if (kCentroids.equals(prevKCentroids)) {
					break;
				}

				for (int n = 0; n < kClusters.size(); n++) {
					kClusters.get(n).clear();
				}
			}
			iteration++;
			for (int key : kMeansBasicIteration.keySet()) {
				int index = ProjectUtils
						.getClusterWithMaximumProximityToCentroid(
								kMeansBasicIteration.get(key), kCentroids);
				kClusters.get(index).put(key, kMeansBasicIteration.get(key));
			}
			for (int i = 0; i < kClusters.size(); i++)
				System.out.println(kClusters.get(i).keySet().size() + "    "
						+ kClusters.get(i).keySet());
		}
		for (int i = 0; i < kClusters.size(); i++)
			System.out.println(kClusters.get(i).keySet().size() + "    "
					+ kClusters.get(i).keySet());
	}

	public void runKMeansWithDataPointsChoppedInNSlots() {

		List<Map<Integer, List<Double>>> kClusters = new ArrayList<Map<Integer, List<Double>>>();
		for (int i = 0; i < k; i++) {
			Map<Integer, List<Double>> singleCluster = new LinkedHashMap<Integer, List<Double>>();
			kClusters.add(singleCluster);
		}
		int loop =0;
		int count =0 ;
		for (int key : kMeansBasicIteration.keySet()) {
			count++;
			if(count>(kMeansBasicIteration.size()/k)*(loop+1)){
				loop = loop + 1;
			}
			if(loop>kClusters.size()-1){
				loop = kClusters.size()-1;
			}
			kClusters.get(loop).put(key,
					kMeansBasicIteration.get(key));
		}

		List<List<Double>> kCentroids = new ArrayList<List<Double>>();
		List<List<Double>> prevKCentroids = new ArrayList<List<Double>>();

		while (true) {
			prevKCentroids.clear();
			prevKCentroids.addAll(kCentroids);
			kCentroids.clear();
			for (int n = 0; n < kClusters.size(); n++) {
				List<Double> nthCentroid = ProjectUtils.getCentroid(kClusters
						.get(n));
				kCentroids.add(nthCentroid);
			}

			if (kCentroids.equals(prevKCentroids)) {
				break;
			}

			for (int n = 0; n < kClusters.size(); n++) {
				kClusters.get(n).clear();
			}
			for (int key : kMeansBasicIteration.keySet()) {
				int index = ProjectUtils
						.getClusterWithMaximumProximityToCentroid(
								kMeansBasicIteration.get(key), kCentroids);
				kClusters.get(index).put(key, kMeansBasicIteration.get(key));
			}
		}
		for (int i = 0; i < kClusters.size(); i++)
			System.out.println(kClusters.get(i).keySet().size() + "    "
					+ kClusters.get(i).keySet());
	}

	
	
	
	
	
	
	
	
	
	public int generateRandomNumberInRange(int range) {

		Random random = new Random();
		int temp = random.nextInt(range);
		if (allPossibleValues.size() == range) {
			allPossibleValues.clear();
		}
		while (true) {
			if (allPossibleValues.contains(temp)) {
				temp = random.nextInt(range);
			} else {
				allPossibleValues.add(temp);
				break;
			}
		}
		allPossibleValues.add(temp);
		return temp;
	}
}
