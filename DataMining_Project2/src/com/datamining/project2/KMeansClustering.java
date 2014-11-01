package com.datamining.project2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
			System.out.println(tempKey+"    "+tempList);
		}
		System.out.println(kMeansBasicIteration.size());
		System.out.println(kMeansBasicIteration);

		
		List<List<Double>> basicList = new ArrayList<List<Double>>(
				kMeansBasicIteration.values());
		System.out.println(basicList.get(0).size());

		for (int i = 0; i < basicList.get(0).size(); i++) {
			List<Double> tList = new ArrayList<Double>();
			for (List<Double> ltp : basicList) {
				tList.add(ltp.get(i));
			}
			double sumOfSquares =0;
			for(double d:tList){
				sumOfSquares = sumOfSquares + Math.pow(d, 2);
			}
			double sumSquareRoot = Math.sqrt(sumOfSquares); 
			for (List<Double> ltp : basicList) {
				double temp = ltp.get(i);
				double norm = (temp) / sumSquareRoot;
				ltp.remove(i);
				ltp.add(i, norm);
			}
		}
		System.out.println(kMeansBasicIteration);
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
		for(int i=0;i<kClusters.size();i++){
			System.out.println(kClusters.get(i).size());
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

		while (true) {
			System.out.println(iteration);
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
// 67 , 135 , 75 ,54 , 55
// 58 , 49 , 138 , 91 ,50
// 70, 70 ,94,78 ,73
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		KMeansClustering kMeansClustering = new KMeansClustering(5, "cho.txt");
		//kMeansClustering.runKMeansClusteringAlgorithm();
		kMeansClustering.runKMeansWithPurDataPointsInRandomeClusterInit();
		kMeansClustering.runKMeansWithDataPointsChoppedInNSlots();
		// kMeansClustering.runKMeansWithScaledInitialClusters();

	}

	
	public void runKMeansWithDataPointsChoppedInNSlots() {
		int iter=0;
		List<Map<Integer, List<Double>>> kClusters = new ArrayList<Map<Integer, List<Double>>>();
		for (int i = 0; i < k; i++) {
			Map<Integer, List<Double>> singleCluster = new LinkedHashMap<Integer, List<Double>>();
			kClusters.add(singleCluster);
		}
		int loop = 0;
		int count = 0;
		for (Integer key : kMeansBasicIteration.keySet()) {
			count++;
			if (count > (kMeansBasicIteration.size() / k) * (loop + 1)) {
				loop = loop + 1;
			}
			if (loop > kClusters.size() - 1) {
				loop = kClusters.size() - 1;
			}
			kClusters.get(loop).put(key, kMeansBasicIteration.get(key));
		}
	
		List<List<Double>> kCentroids = new ArrayList<List<Double>>();
		List<List<Double>> prevKCentroids = new ArrayList<List<Double>>();
		double[] sseCompare = new double[2];
		while (true) {
			System.out.println(iter++);
			prevKCentroids.clear();
			prevKCentroids.addAll(kCentroids);
			kCentroids.clear();
			for (Integer n = 0; n < kClusters.size(); n++) {
				List<Double> nthCentroid = ProjectUtils.getCentroid(kClusters
						.get(n));
				kCentroids.add(nthCentroid);
			}
		
			/*
			 * boolean flag = true; for (int i = 0; i < kCentroids.size(); i++)
			 * { if (prevKCentroids.size() == 0) { flag = false; break; } double
			 * temp = ProjectUtils.getEuclideanDistance( kCentroids.get(i),
			 * prevKCentroids.get(i)); if (temp > 0.001) { flag = false; break;
			 * } } if (flag == true) { break; }
			 */

			double sse = 0;
			for (int n = 0; n < kClusters.size(); n++) {
				List<List<Double>> tempList = new LinkedList<List<Double>>(
						kClusters.get(n).values());
				List<Double> tempCentriod = kCentroids.get(n);
				for (List<Double> tp : tempList) {
					sse = sse + ProjectUtils.getSquaredError(tp, tempCentriod);
				}

			}
			if (sseCompare[0]==0.00){
				sseCompare[0]= sse;
			}else{
				sseCompare[1]=sse;
				if(sseCompare[0]-sseCompare[1]<0.02){
					System.out.println("Great" + sseCompare[0]);
					break;
				}
				else{
					sseCompare[0]=sseCompare[1];
				}
			}
			
			for (int n = 0; n < kClusters.size(); n++) {
				kClusters.get(n).clear();
			}
			for (Integer key : kMeansBasicIteration.keySet()) {
				int index = ProjectUtils
						.getClusterWithMaximumProximityToCentroid(
								kMeansBasicIteration.get(key), kCentroids);
				kClusters.get(index).put(key, kMeansBasicIteration.get(key));
			}
			
		}
		for (int i = 0; i < kClusters.size(); i++)
			System.out.println(kClusters.get(i).keySet().size() + "    "
					+ kClusters.get(i).keySet());
		System.out.println(kCentroids);
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
