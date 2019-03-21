package com.toviah.composites;

import java.util.ArrayList;

import Jama.Matrix;

public class CompositeUniD {
	ArrayList<LaminaUniD> layup = new ArrayList<LaminaUniD>();
	double[] layupAngles;
	LaminaUniD newLamina() {
		LaminaUniD layer = new LaminaUniD();
		layup.add(layer);
		return layer;
	}
	
	double totalThickness = 0;
	double [][] emptyMatrix = new double[3][3];
	Matrix compositeStiffnessMatrix;
	
	void calculateComposite () {
		compositeStiffnessMatrix = new Matrix(emptyMatrix);
		LaminaUniD currentLamina = new LaminaUniD();
		for (int i=0; i < layup.size(); i++) {
			currentLamina = layup.get(i);
			totalThickness += currentLamina.thickness;
		}
		for (int i=0; i < layup.size(); i++) {
			currentLamina = layup.get(i);
			currentLamina.compositeVolumePercentage = currentLamina.thickness / totalThickness;
			compositeStiffnessMatrix.plusEquals(currentLamina.angleStiffnessMatrix.times(currentLamina.compositeVolumePercentage));
		}
	}
}
