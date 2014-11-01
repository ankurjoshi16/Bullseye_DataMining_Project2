package com.datamining.project2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.datamining.project2Utils.ProjectUtils;

public class HirAggSingleLinkAlgorithm {

	private String fileName;
	private int cutLevel;
	private List<ProjectCluster> clusters;
	private String mergeOrder = "";

	public HirAggSingleLinkAlgorithm(String fileName, int cutLevel) {

		clusters = new ArrayList<ProjectCluster>();
		this.fileName = fileName;
		this.cutLevel = cutLevel;
	}

	@SuppressWarnings("unused")
	private List<ProjectCluster> getInitialClusters()
			throws NumberFormatException, IOException {

		Map<Integer, DataPoint> initialMap = ProjectUtils
				.readFileToInitialMapNorm(fileName);

		for (Integer key : initialMap.keySet()) {
			ProjectCluster pc = new ProjectCluster();
			pc.identifier = key.toString();
			pc.addPoint(initialMap.get(key));
			clusters.add(pc);
		}
		return clusters;
	}

	@SuppressWarnings("unused")
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
	}
}
