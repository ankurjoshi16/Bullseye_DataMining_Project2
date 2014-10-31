package com.datamining.project2;

import java.util.ArrayList;

import org.apache.commons.math3.ml.distance.EuclideanDistance;

public class checkD {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String temp = "1|2|5|4|9";
		String[] rowNums = temp.split("\\|");
		
		for(int i=0;i<5;i++){
		System.out.println(rowNums[i]);
		}
		
		 double[] gTMean1 = { -0.013477612, -0.236238806, -0.527208955,
		 -0.614208955, -0.497820896, -0.405029851, -0.364268657, -0.107164179,
		  0.371029851, 0.662074627, 0.296716418, 0.042432836, -0.061402985,
		  0.004671642, 0.127447761, 0.425671642 };
		 

		double[] gtMean1 = { -0.07397402597402598, -0.15685714285714283,
				-0.29003896103896104, -0.4467792207792208, -0.4418701298701298,
				-0.3830779220779222, -0.39202597402597406,
				-0.20558441558441556, 0.19440259740259752, 0.7070000000000001,
				0.34688311688311685, 0.06274025974025976, -0.05512987012987014,
				-0.043168831168831176, 0.021675324675324698,
				0.28207792207792215 };

		/*
		 * double[] gTMean5 = { -0.596727273, -0.676236364, -0.884909091,
		 * -0.642909091, -0.292181818, 0.130181818, 0.657272727, 0.893454545,
		 * 0.715636364, -0.124, -0.362672727, -0.225090909, 0.005890909,
		 * 0.339272727, 0.559036364, 0.470727273 };
		 */

		double[] gtMean2 = { -0.42141558441558447, 0.04000000000000002,
				0.944935064935065, 0.4174025974025974, -0.09857142857142856,
				-0.23350649350649352, -0.5298701298701299, -0.7625974025974027,
				-0.5141558441558441, 0.7637662337662338, 0.6287922077922078,
				0.2555714285714285, 0.00977922077922078, -0.21089610389610391,
				-0.47712987012987007, -0.30012987012987 };

		/*
		 * double[] pt5 = { -1.04, 0.13, 0.51, -0.44, -0.88, -0.32, 0.21, 0.95,
		 * 1.07, 0.38, 0.01, -0.13, -0.78, -0.13, 0.092, 0 };
		 */

		double[] pt2 = { -0.21, 0.19, 0.86, 0.04, -0.35, -0.39, -0.51, -0.2,
				0.0, 0.77, 0.41, 0.14, -0.45, -1.23, -0.325, 0.0 };
		
		EuclideanDistance ed = new EuclideanDistance();
		System.out.println(ed.compute(gtMean1, pt2));
		System.out.println(ed.compute(gtMean2, pt2));
		System.out.println(ed.compute(gTMean1, pt2));


	}
}
