package com.toviah.composites;

import java.util.ArrayList;

import Jama.Matrix;

public class Composite {
	ArrayList<Lamina> layup = new ArrayList<Lamina>();
	double[] layupAngles;
	Lamina newLamina() {
		Lamina layer = new Lamina();
		layup.add(layer);
		return layer;
	}
	
	double totalThickness = 0;
	double [][] emptyMatrix = new double[3][3];
	Matrix compositeStiffnessMatrix;
	
	void calculateComposite () {
		compositeStiffnessMatrix = new Matrix(emptyMatrix);
		
		Lamina currentLamina = new Lamina();
		for (int i=0; i < layup.size(); i++) {
			currentLamina = layup.get(i);
			totalThickness += currentLamina.thickness;
		}
		for (int i=0; i < layup.size(); i++) {
			currentLamina = layup.get(i);
			currentLamina.compositeVolumePercentage = currentLamina.thickness / totalThickness;
			compositeStiffnessMatrix.plusEquals(currentLamina.stiffnessMatrix.times(currentLamina.compositeVolumePercentage));
		}
	}
}
