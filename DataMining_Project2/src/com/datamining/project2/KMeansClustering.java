package com.datamining.project2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.datamining.project2Utils.ProjectUtils;

public class KMeansClustering {

	private int k;
	private String inputFile;
	private Map<Integer, List<Double>> kMeansBasicIteration;

	public KMeansClustering(int k, String inputFile) throws IOException {
		this.k = k;
		this.inputFile = inputFile;
		kMeansBasicIteration = new LinkedHashMap<Integer, List<Double>>();
	}

	public void runKMeansClusteringAlgorithm() throws IOException {

		BufferedReader bufferedReader = null;

		String readLine;
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
		
		List<Map<Integer, List<Double>>> kClusters = new ArrayList<Map<Integer,List<Double>>>();
		
		int tempCounter= kMeansBasicIteration.size()/k;
		int count = 1;
		
		List<Integer> removeList = new ArrayList<Integer>();
		while(tempCounter*count<=kMeansBasicIteration.size()){
			Map<Integer, List<Double>> singleCluster = new LinkedHashMap<Integer, List<Double>>();
			int key = new ArrayList<Integer>(kMeansBasicIteration.keySet()).get(tempCounter*count-1);
			removeList.add(key);
			singleCluster.put(key, kMeansBasicIteration.get(key));
			kClusters.add(singleCluster);
			count++;	
		}
		
		for(int key:kMeansBasicIteration.keySet()){
			int index = ProjectUtils.getClusterWithMaximumProximity(kMeansBasicIteration.get(key), kClusters);
			kClusters.get(index).put(key, kMeansBasicIteration.get(key));	
		}
		
		List<List<Double>> kCentroids = new ArrayList<List<Double>>();
		List<List<Double>> prevKCentroids = new ArrayList<List<Double>>();
		
		while(true){
		prevKCentroids.clear();
		prevKCentroids.addAll(kCentroids);
		kCentroids.clear();
		for(int n=0;n<kClusters.size();n++){
		List<Double> nthCentroid= ProjectUtils.getCentroid(kClusters.get(n));
		kCentroids.add(nthCentroid);			
		}
		
		if(kCentroids.equals(prevKCentroids)){
			break;
		}
		
		for(int n=0; n<kClusters.size() ; n++){
			kClusters.get(n).clear();
		}
		
		for(int key:kMeansBasicIteration.keySet()){
			int index = ProjectUtils.getClusterWithMaximumProximityToCentroid(kMeansBasicIteration.get(key), kCentroids);
			kClusters.get(index).put(key, kMeansBasicIteration.get(key));	
		}
		}
		
		
		//System.out.println(kMeansBasicIteration);
		System.out.println(kClusters.get(0).size());
		System.out.println(kClusters.get(1).size());
		System.out.println(kClusters.get(2).size());
		System.out.println(kClusters.get(3).size());
		System.out.println(kClusters.get(4).size());


	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		KMeansClustering kMeansClustering = new KMeansClustering(5, "cho.txt");
		kMeansClustering.runKMeansClusteringAlgorithm();

	}

}
