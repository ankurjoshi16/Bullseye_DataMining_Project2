package com.datamining.project2Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.math3.ml.distance.EuclideanDistance;

import com.datamining.project2.DataPoint;
import com.datamining.project2.ProjectCluster;

public class ProjectUtils {
	private static Set<Integer> allPossibleValues = new HashSet<Integer>();

	public static void main(String[] args) throws NumberFormatException,
			IOException {

		List<Double> tp1 = new ArrayList<Double>();
		tp1.add(8.0);
		tp1.add(5.0);

		List<Double> tp2 = new ArrayList<Double>();
		tp2.add(4.0);
		tp2.add(2.0);

		// System.out.println(getEuclideanDistance(tp1, tp2));

		//getNormalizedFile("cho.txt");
		getInitialCentriodsForMR("cho.txt", 5);

	}

	public static double getEuclideanDistance(List<Double> firstList,
			List<Double> secondList) {

		double sum = 0;
		for (int i = 0; i < firstList.size(); i++) {
			sum = sum + Math.pow((firstList.get(i) - secondList.get(i)), 2);
		}
		return Math.sqrt(sum);

		/*
		 * EuclideanDistance ed = new EuclideanDistance(); double[] leftArray =
		 * new double[firstList.size()]; for (int i = 0; i < firstList.size();
		 * i++) { leftArray[i] = firstList.get(i); }
		 * 
		 * double[] rightArray = new double[secondList.size()]; for (int i = 0;
		 * i < secondList.size(); i++) { rightArray[i] = secondList.get(i); }
		 * return ed.compute(leftArray, rightArray);
		 */
	}

	public static double getSquaredError(List<Double> firstList,
			List<Double> secondList) {

		double sum = 0;
		for (int i = 0; i < firstList.size(); i++) {
			sum = sum + Math.pow((firstList.get(i) - secondList.get(i)), 2);
		}
		return sum;
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

		/*
		 * List<Double> allEntries = new LinkedList<Double>();
		 * 
		 * for (List<Double> ltp : kCentroids) {
		 * allEntries.add(getEuclideanDistance(ltp, dataPoint)); } double min =
		 * Collections.min(allEntries);
		 * 
		 * for (int i = 0; i < allEntries.size(); i++) { if (allEntries.get(i)
		 * == min) { return i; } }
		 * System.out.println("Yesssssssssssssssssssssss"); return -1;
		 */
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

	public static List<Double> getCentroidWithDataPoints(List<List<Double>> ip) {
		List<Double> centroid = new ArrayList<Double>();

		if (null == ip || 0 == ip.size()) {
			return centroid;
		}
		int dimensions = ip.get(0).size();
		for (int i = 0; i < dimensions; i++) {
			double[] coordinates = new double[ip.size()];
			int k = 0;
			Double sum = 0.0;

			for (List<Double> dp : ip) {
				coordinates[k++] = dp.get(i);
			}
			for (int j = 0; j < coordinates.length; j++)
				sum += coordinates[j];
			sum = sum / coordinates.length;
			centroid.add(sum);
		}
		return centroid;
	}

	/*
	 * public static List<Double> getCentroid(Collection<List<Double>> input) {
	 * 
	 * List<List<Double>> inputList = new LinkedList<List<Double>>(input);
	 * List<Double> centroid = new ArrayList<Double>();
	 * 
	 * int listSize = inputList.get(0).size();
	 * 
	 * for (int i = 0; i < listSize; i++) { double sum = 0; for (int j = 0; j <
	 * inputList.size(); j++) { sum = sum + inputList.get(j).get(i); }
	 * centroid.add(sum / inputList.size()); } return centroid; }
	 */

	// returns Epsilon value of all points sorted by distnace
	public static double getEpsilion(List<Double> distanceList) {
		if (distanceList == null)
			return -1;
		else {
			Double[] dList = distanceList.toArray(new Double[0]);
			double maxDiff = Double.MIN_VALUE;
			int index = -1;

			for (int i = 0; i < dList.length - 1; i++) {
				double diff = dList[i + 1] - dList[i];
				if (diff > maxDiff) {
					maxDiff = diff;
					index = i;
				}

			}
			for (int i = 0; i < dList.length; i++) {
				// System.out.println(dList[i]);
			}

			return dList[index];
		}
	}

	public static Map<Integer, DataPoint> readFileToInitialMap(String fileName)
			throws NumberFormatException, IOException {

		Map<Integer, DataPoint> initialMap = new LinkedHashMap<Integer, DataPoint>();
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
			initialMap.put(tempKey, dp);

		}
		bufferedReader.close();
		return initialMap;
	}

	public static Map<Integer, DataPoint> readFileToInitialMapNorm(
			String fileName) throws NumberFormatException, IOException {

		Map<Integer, DataPoint> initialMap = readFileToInitialMap(fileName);

		List<List<Double>> basicList = new ArrayList<List<Double>>();

		for (DataPoint tm : initialMap.values()) {
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
		return initialMap;
	}

	public static double getSSE(List<ProjectCluster> clusters) {

		double sse = 0;
		for (ProjectCluster kmc : clusters) {
			for (DataPoint dp : kmc.getAllClusterPoints()) {
				sse = sse
						+ getSquaredError(dp.getCoordinates(),
								kmc.getCentriod());
			}
		}

		return sse;
	}

	public static File getNormalizedFile(String fileName)
			throws NumberFormatException, IOException {

		Map<Integer, DataPoint> normalizedMap = readFileToInitialMapNorm(fileName);
		File file = new File("MrInput");
		if (!file.exists()) {
			file.delete();
		}
		file.createNewFile();
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		StringBuilder str = new StringBuilder();
		for (int key : normalizedMap.keySet()) {
			String temp = "";
			temp = "\n" + temp + key;
			for (double d : normalizedMap.get(key).getCoordinates()) {
				temp = temp + "\t" + Double.toString(d);
			}
			str.append(temp);
		}
		bw.write(str.toString().substring(1));
		bw.close();
		return file;
	}

	public static List<List<Double>> getInitialCentriodsForMR(String fileName,
			int k) throws NumberFormatException, IOException {
		Map<Integer, DataPoint> normalizedMap = readFileToInitialMapNorm(fileName);
		List<List<Double>> initialCentriods = new ArrayList<List<Double>>();
		List<Map<Integer, List<Double>>> kClusters = new ArrayList<Map<Integer, List<Double>>>();
		for (int i = 0; i < k; i++) {
			Map<Integer, List<Double>> singleCluster = new LinkedHashMap<Integer, List<Double>>();
			kClusters.add(singleCluster);
		}

		for (int key : normalizedMap.keySet()) {
			kClusters.get(generateRandomNumberInRange(k)).put(key,
					normalizedMap.get(key).getCoordinates());
		}

		for (int i = 0; i < kClusters.size(); i++) {
			initialCentriods
					.add(getCentroidWithDataPoints(new ArrayList<List<Double>>(
							kClusters.get(i).values())));
		}
		return initialCentriods;
	}

	public static int generateRandomNumberInRange(int range) {

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
