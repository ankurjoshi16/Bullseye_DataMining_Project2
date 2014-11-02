package com.datamining.project2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.datamining.project2Utils.ProjectUtils;

public class KMeansAlgorithm {

	private String fileName;
	private double sse;
	private int iterations;
	private List<ProjectCluster> clusters;
	private Map<Integer, DataPoint> initialKMeans;

	public KMeansAlgorithm(String fileName, int numOfClusters,
			String pipeDelimRowNums, double sse, int iterations)
			throws NumberFormatException, IOException {
		this.sse = sse;
		this.iterations = iterations;
		this.fileName = fileName;
		initialKMeans = ProjectUtils.readFileToInitialMapNorm(fileName);
		String[] rowNums = pipeDelimRowNums.split("\\|");
		clusters = new ArrayList<ProjectCluster>();
		for (int i = 0; i < numOfClusters; i++) {
			ProjectCluster kmc = new ProjectCluster(initialKMeans.get(
					Integer.parseInt(rowNums[i])).getCoordinates());
			clusters.add(kmc);
		}
		
	}

	public void runKMeansAlgorithm() throws NumberFormatException, IOException {
		int loop = 0;
		while (true) {
			loop++;
			List<Double> kD;
			for (DataPoint dp : initialKMeans.values()) {
				kD = new ArrayList<Double>();
				for (int i = 0; i < clusters.size(); i++) {
					kD.add(ProjectUtils.getEuclideanDistance(
							dp.getCoordinates(), clusters.get(i).getCentriod()));
				}
				double min = Collections.min(kD);
				int index = kD.indexOf(new Double(min));
				clusters.get(index).addPoint(dp);
			}
			//For check
			System.out.println("For Iteration " + (loop-1) );
			for(ProjectCluster pc:clusters){
				System.out.println(pc.getFinalCentriod());
			}
			
			
			if (loop >= iterations + 1
					|| ((ProjectUtils.getSSE(clusters) <= sse) && clusters
							.size() > 0)) {
				break;
			}
			List<ProjectCluster> clusters2 = new ArrayList<ProjectCluster>();
			for (int i = 0; i < clusters.size(); i++) {
				ProjectCluster kmc = new ProjectCluster(clusters.get(i)
						.getFinalCentriod());
				clusters2.add(kmc);
			}
			clusters = clusters2;
		}
		OutputObject oo = new OutputObject();
		oo.outputStr = oo.outputStr + "Total Iterations Executed for KMeans : "
				+ loop;
		oo.outputStr = oo.outputStr + "\n"
				+ "Final SSE at the time of Convergence: "
				+ ProjectUtils.getSSE(clusters);
		oo.outputStr = oo.outputStr + "\n"
				+ "External Index /Jaccard Coefficient: "
				+ ProjectUtils.calculateExternalIndex(fileName, clusters);
		for (int i = 0; i < clusters.size(); i++) {
			oo.outputStr = oo.outputStr + "\n" + "Size of Cluster:"
					+ clusters.get(i).getAllClusterPoints().size()
					+ " , ClusterPoints :" + clusters.get(i).getAllKeys();
		}
		System.out.println(oo.outputStr);
		System.out.println(ProjectUtils.calculateCorrelation(fileName,
				clusters, initialKMeans));
	}

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		// TODO Auto-generated method stub
		KMeansAlgorithm kmc = new KMeansAlgorithm("iyer.txt", 10,
				"1|12|44|66|77|92|201|240|268|386",Double.MIN_VALUE, 25);
		kmc.runKMeansAlgorithm();
	}

}
