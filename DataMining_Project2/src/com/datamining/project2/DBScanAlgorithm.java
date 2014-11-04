package com.datamining.project2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import PCA_BullsEye.PCA;

import com.datamining.project2Utils.ProjectUtils;
import com.mathworks.toolbox.javabuilder.MWException;

public class DBScanAlgorithm {

	private String fileName;
	private double epsilon = 0.257;
	private int minPoints = 1;
	private Map<Integer, DataPoint> initialDBScan;
	private List<ProjectCluster> pClusters;
	private List<Integer> outliers = new ArrayList<Integer>();
	private List<Integer> clustered = new ArrayList<Integer>();
	private OutputObject oo = new OutputObject();

	public DBScanAlgorithm(String fileName) throws NumberFormatException,
			IOException {
		this.fileName = fileName;
		initialDBScan = ProjectUtils.readFileToInitialMap(fileName);
	}

	public DBScanAlgorithm(String fileName, double epsilon, int minPoints,
			boolean norm) throws NumberFormatException, IOException {
		this.fileName = fileName;
		this.epsilon = epsilon;
		this.minPoints = minPoints;
		if (true == norm) {
			initialDBScan = ProjectUtils.readFileToInitialMapNorm(fileName);
		} else {
			initialDBScan = ProjectUtils.readFileToInitialMap(fileName);
		}
	}

	public OutputObject runDBScanAlgorithm() throws NumberFormatException,
			IOException {
		pClusters = new ArrayList<ProjectCluster>();
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
					ProjectCluster pNextCluster = new ProjectCluster();
					pClusters.add(pNextCluster);
					expandCluster(a, neighborPts, pNextCluster);
				}

			}

		}
		oo.outputStr = oo.outputStr
				+ "Total number of clusters formed after running DBScan Algorithm: "
				+ pClusters.size();
		for (int i = 0; i < pClusters.size(); i++) {

			oo.outputStr = oo.outputStr + "\n" + "Cluster Size: "
					+ pClusters.get(i).getAllClusterPoints().size()
					+ " , Cluster Points: " + pClusters.get(i).getAllKeys();

			clustered.addAll(pClusters.get(i).getAllKeys());
		}

		oo.outputStr = oo.outputStr + "\n" + "Total points clustered: "
				+ clustered.size();

		for (int a : clustered) {
			if (outliers.contains(a)) {
				outliers.remove(new Integer(a));
				initialDBScan.get(a).isNoise = false;
			}
		}

		ProjectCluster pc = new ProjectCluster();
		pClusters.add(pc);
		for (int o : outliers) {
			pc.index = -1;
			pc.addPoint(initialDBScan.get(o));
		}

		oo.outputStr = oo.outputStr + "\n" + "Total Outliers Found: "
				+ outliers.size();

		oo.outputStr = oo.outputStr + "\n"
				+ "External Index /Jaccard Coefficient: "
				+ ProjectUtils.calculateExternalIndex(fileName, pClusters);

		oo.outputStr = oo.outputStr
				+ "\n"
				+ "Correaltion with Distance Matrix: "
				+ ProjectUtils.calculateCorrelation(fileName, pClusters,
						initialDBScan);

		return oo;
	}

	public void plotPca() throws IOException, MWException {

		ProjectUtils.writeFileForPCA(pClusters);
		PCA pca = new PCA();
		pca.PCA("pca.txt", fileName);
	}

	private void expandCluster(int point, List<Integer> neighbors,
			ProjectCluster nextCluster) {

		nextCluster.addPoint(initialDBScan.get(point));
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
				nextCluster.addPoint(initialDBScan.get(head));
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

	public double getEpsilon() {
		List<Double> mainList = new ArrayList<Double>();
		for (int i : initialDBScan.keySet()) {
			List<Double> distances = new ArrayList<Double>();
			for (int j : initialDBScan.keySet()) {
				distances.add(ProjectUtils.getEuclideanDistance(initialDBScan
						.get(i).getCoordinates(), initialDBScan.get(j)
						.getCoordinates()));
			}
			Collections.sort(distances);
			mainList.add(distances.get(minPoints+1));
		}
		
		Collections.sort(mainList);
		return ProjectUtils.getEpsilion(mainList);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<Integer> getOutliers() throws NumberFormatException,
			IOException {
		outliers = new ArrayList<Integer>();
		runDBScanAlgorithm();
		return outliers;
	}

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		DBScanAlgorithm dbscan = new DBScanAlgorithm("dataset1.txt");
		dbscan.getEpsilon();
	}

}
