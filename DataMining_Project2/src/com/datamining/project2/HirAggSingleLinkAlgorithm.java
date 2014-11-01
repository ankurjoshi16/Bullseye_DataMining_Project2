package com.datamining.project2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.datamining.project2Utils.ProjectUtils;

public class HirAggSingleLinkAlgorithm {

	private String fileName;
	private int cutLevel;
	private List<ProjectCluster> clusters;
	private String mergeOrder = "";
	private String cutLevelCLusters = "";

	public HirAggSingleLinkAlgorithm(String fileName, int cutLevel) {

		clusters = new ArrayList<ProjectCluster>();
		this.fileName = fileName;
		this.cutLevel = cutLevel;
	}

	private void getInitialClusters()
			throws NumberFormatException, IOException {

		Map<Integer, DataPoint> initialMap = ProjectUtils
				.readFileToInitialMapNorm(fileName);

		for (Integer key : initialMap.keySet()) {
			ProjectCluster pc = new ProjectCluster();
			pc.identifier = key.toString();
			pc.addPoint(initialMap.get(key));
			clusters.add(pc);
		}
	}

	private void mergeClusters(ProjectCluster c1, ProjectCluster c2) {
		mergeOrder = mergeOrder + "\n" + c1.identifier + " ----> "
				+ c2.identifier;
		List<DataPoint> points = new ArrayList<DataPoint>(
				c1.getAllClusterPoints());
		points.addAll(c2.getAllClusterPoints());
		String identifier = c1.identifier + "|" + c2.identifier;
		ProjectCluster pc = new ProjectCluster();
		pc.identifier = identifier;
		pc.addPoints(points);
		clusters.remove(c1);
		clusters.remove(c2);
		clusters.add(pc);

		if (clusters.size() == cutLevel) {
			for (ProjectCluster pc2 : clusters) {
				cutLevelCLusters = cutLevelCLusters + "\n" + "Cluster Size: "
						+ pc2.getAllClusterPoints().size()
						+ " , Cluster Points are :" + pc2.getAllKeys();
			}
		}
	}

	public void runHeirarchical() throws NumberFormatException, IOException {

		PriorityQueue<ProjectCluster> clusterHeap = new PriorityQueue<ProjectCluster>();
		getInitialClusters();
		while (clusters.size() > 1) {
			for (ProjectCluster pTemp : clusters) {
				pTemp.getMinDiff(clusters);
				clusterHeap.add(pTemp);
			}

			ProjectCluster cl1 = clusterHeap.poll();
			ProjectCluster cl2 = cl1.nearestC;
			mergeClusters(cl1, cl2);
			clusterHeap.clear();
		}

		System.out.println(mergeOrder);
		System.out.println(cutLevelCLusters);
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		HirAggSingleLinkAlgorithm hca = new HirAggSingleLinkAlgorithm(
				"cho.txt", 5);
		hca.runHeirarchical();
	}
}
