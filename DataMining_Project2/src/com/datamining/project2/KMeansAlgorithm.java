package com.datamining.project2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import PCA_BullsEye.PCA;

import com.datamining.project2Utils.ProjectUtils;
import com.mathworks.toolbox.javabuilder.MWException;

public class KMeansAlgorithm {

	private String fileName;
	private double sse;
	private double sseThr;
	private int iterations;
	private List<ProjectCluster> clusters;
	private Map<Integer, DataPoint> initialKMeans;

	public KMeansAlgorithm(String fileName, int numOfClusters,
			String pipeDelimRowNums, double sseThr, int iterations)
			throws NumberFormatException, IOException {

		this.sseThr = sseThr;
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

	public OutputObject runKMeansAlgorithm() throws NumberFormatException,
			IOException, MWException {
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
			double newSSE = ProjectUtils.getSSE(clusters);
			if (loop >= iterations + 1 || (Math.abs(newSSE - sse) <= sseThr)) {
				break;
			}
			sse = newSSE;
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
				+ (loop-1);
		oo.outputStr = oo.outputStr + "\n"
				+ "Final SSE at the time of Convergence: "
				+ ProjectUtils.getSSE(clusters);
		oo.outputStr = oo.outputStr + "\n"
				+ "External Index /Jaccard Coefficient: "
				+ ProjectUtils.calculateExternalIndex(fileName, clusters);
		oo.outputStr = oo.outputStr
				+ "\n"
				+ "Correaltion with Distance Matrix: "
				+ ProjectUtils.calculateCorrelation(fileName, clusters,
						initialKMeans);

		for (int i = 0; i < clusters.size(); i++) {
			oo.outputStr = oo.outputStr + "\n" + "Size of Cluster:"
					+ clusters.get(i).getAllClusterPoints().size()
					+ " , ClusterPoints :" + clusters.get(i).getAllKeys();
		}

		return oo;

	}

	public void plotPca() throws IOException, MWException {

		ProjectUtils.writeFileForPCA(clusters);
		PCA pca = new PCA();
		pca.PCA("pca.txt", fileName);
	}

	public static void main(String[] args) throws NumberFormatException,
			IOException, MWException {
		// TODO Auto-generated method stub
		KMeansAlgorithm kmc = new KMeansAlgorithm("cho.txt", 5,
				"1|76|148|250|382", 0.001, 50);
		kmc.runKMeansAlgorithm();
	}

}
