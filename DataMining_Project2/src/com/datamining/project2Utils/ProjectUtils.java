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
import java.util.TreeMap;

import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

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

		getNormalizedFile("iyer.txt");
		// getInitialCentriodsForMR("cho.txt", 5);

	}

	public static double getEuclideanDistance(List<Double> firstList,
			List<Double> secondList) {

		double sum = 0;
		for (int i = 0; i < firstList.size(); i++) {
			sum = sum + Math.pow((firstList.get(i) - secondList.get(i)), 2);
		}
		return Math.sqrt(sum);

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
			dp.gT = Integer.parseInt(tempArray[1]);
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

	public static double calculateExternalIndex(String fileName,
			List<ProjectCluster> cResult) throws NumberFormatException,
			IOException {

		Map<Integer, ProjectCluster> cMap = new TreeMap<Integer, ProjectCluster>();
		Map<Integer, Integer> gMap = new TreeMap<Integer,Integer>(getGMap(fileName));
		
		if (null == cResult || 0 == cResult.size()) {
			return 0.0;
		}
		for (ProjectCluster pc : cResult) {
			for (int a : pc.getAllKeys()) {
				cMap.put(a, pc);
			}
		}

		System.out.println("Jaccard  :"+gMap.size() + "   " +cMap.size());
		System.out.println();

		List<Integer> keys = new ArrayList<Integer>(cMap.keySet());
		int[][] cMatrix = new int[gMap.size()][gMap.size()];
		int[][] gMatrix = new int[gMap.size()][gMap.size()];

		for (int i = 0; i < keys.size(); i++) {
			for (int j = 0; j < keys.size(); j++) {
				if (cMap.get(keys.get(i)) == cMap.get(keys.get(j))) {
					cMatrix[i][j] = 1;
				}
			}
		}

		keys = new ArrayList<Integer>(gMap.keySet());
		for (int i = 0; i < keys.size(); i++) {
			for (int j = 0; j < keys.size(); j++) {
				if (gMap.get(keys.get(i)) == gMap.get(keys.get(j))) {
					gMatrix[i][j] = 1;
				}
			}
		}
		int ss = 0, sd = 0, ds = 0;
		for (int i = 0; i < keys.size(); i++) {
			for (int j = 0; j < keys.size(); j++) {
				if ((1 == cMatrix[i][j]) && (1 == gMatrix[i][j])) {
					ss++;
				} else if ((1 == cMatrix[i][j]) && (0 == gMatrix[i][j])) {
					sd++;
				} else if ((0 == cMatrix[i][j]) && (1 == gMatrix[i][j])) {
					ds++;
				}
			}
		}

		double jc = (double) (ss) / (ss + sd + ds);
		return jc;
	}

	public static Map<Integer, Integer> getGMap(String fileName)
			throws NumberFormatException, IOException {
		Map<Integer, Integer> gMap = new TreeMap<Integer, Integer>();
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String readLine;
		while ((readLine = bufferedReader.readLine()) != null) {
			String[] tempArray = readLine.split("\t");
			int tempKey = Integer.parseInt(tempArray[0]);
			int cId = Integer.parseInt(tempArray[1]);
			gMap.put(tempKey, cId);
		}
		bufferedReader.close();
		return gMap;
	}

	public static double calculateCorrelation(String fileName,
			List<ProjectCluster> cResult, Map<Integer, DataPoint> ipMap)
			throws NumberFormatException, IOException {

		Map<Integer, ProjectCluster> cMap = new TreeMap<Integer, ProjectCluster>();
		List<Integer> outliers = new ArrayList<Integer>();
		System.out.println("ipMap "+ipMap.size());
		if (null == cResult || 0 == cResult.size()) {
			return 0.0;
		}
		for (ProjectCluster pc : cResult) {
			if(pc.index==-1){
				outliers.addAll(pc.getAllKeys());
			}
			for (int a : pc.getAllKeys()) {
				cMap.put(a, pc);
			}
		}
		System.out.println("cMap Size "+cMap.size());

		List<Integer> keys = new ArrayList<Integer>(cMap.keySet());

		List<Double> inVector = new ArrayList<Double>();
		List<Double> diVector = new ArrayList<Double>();

		for (int i = 0; i < keys.size(); i++) {
			for (int j = 0; j < keys.size(); j++) {
				if(outliers.contains(keys.get(i))){
					inVector.add(0.0);
				}
				else if (cMap.get(keys.get(i)) == cMap.get(keys.get(j))) {
					inVector.add(1.0);
				} else {
					inVector.add(0.0);
				}
			}
		}

		keys = new ArrayList<Integer>(cMap.keySet());

		for (int i = 0; i < keys.size(); i++) {
			for (int j = 0; j < keys.size(); j++) {

				double td = ProjectUtils.getEuclideanDistance(
						ipMap.get(keys.get(i)).getCoordinates(),
						ipMap.get(keys.get(j)).getCoordinates());
				diVector.add(td);
			}
		}

		double diMean = getMean(diVector);
		double inMean = getMean(inVector);

		System.out.println("inVector"+inVector.size());
		System.out.println("diVector"+diVector.size());

		double N = 0, D1 = 0, D2 = 0;
		for (int i = 0; i < inVector.size(); i++) {
			N = N + ((inVector.get(i) - inMean) * (diVector.get(i) - diMean));
			D1 = D1 + ((inVector.get(i) - inMean) * (inVector.get(i) - inMean));
			D2 = D2 + ((diVector.get(i) - diMean) * (diVector.get(i) - diMean));
		}

		double cor = N / Math.sqrt(D1 * D2);

		return cor;
	}

	public static void writeFileForPCA(List<ProjectCluster> clusters)
			throws IOException {

		File file = new File("pca.txt");
		if (!file.exists()) {
			file.delete();
		}
		file.createNewFile();
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		StringBuilder str = new StringBuilder();

		for (int i = 0; i < clusters.size(); i++) {
			for (DataPoint dp : clusters.get(i).getAllClusterPoints()) {
				String temp = "";
				temp = "\n" + temp + dp.getIndex() + "\t" + (i + 1);
				for (double d : dp.getCoordinates()) {
					temp = temp + "\t" + Double.toString(d);
				}
				str.append(temp);
			}

		}

		bw.write(str.toString().substring(1));
		bw.close();

	}

	public List<ProjectCluster> createClustersFromCentriods(String fileName,
			List<List<Double>> centriods) throws NumberFormatException,
			IOException {

		if (null == fileName || null == centriods) {
			return null;
		}

		Map<Integer, DataPoint> normalizedMap = readFileToInitialMapNorm(fileName);
		List<ProjectCluster> clusters = new ArrayList<ProjectCluster>();

		for (int i = 0; i < centriods.size(); i++) {
			ProjectCluster kmc = new ProjectCluster(centriods.get(i));
			clusters.add(kmc);
		}

		List<Double> kD;
		for (DataPoint dp : normalizedMap.values()) {
			kD = new ArrayList<Double>();
			for (int i = 0; i < clusters.size(); i++) {
				kD.add(ProjectUtils.getEuclideanDistance(dp.getCoordinates(),
						clusters.get(i).getCentriod()));
			}
			double min = Collections.min(kD);
			int index = kD.indexOf(new Double(min));
			clusters.get(index).addPoint(dp);
		}

		return clusters;
	}

	public List<List<Double>> getCentriodsForDemo(int k, String fileName,
			String pipeDelimRowNums) throws NumberFormatException, IOException {

		List<List<Double>> centriods = new ArrayList<List<Double>>();
		Map<Integer, DataPoint> normalizedMap = readFileToInitialMapNorm(fileName);
		String[] rowNums = pipeDelimRowNums.split("\\|");
		for (int i = 0; i < k; i++) {
			centriods.add(normalizedMap.get(Integer.parseInt(rowNums[i]))
					.getCoordinates());
		}

		return centriods;

	}

	public static double getMean(List<Double> ipList) {

		double temp = 0;
		for (int i = 0; i < ipList.size(); i++) {
			temp = temp + ipList.get(i);
		}

		return temp / ipList.size();
	}
}
