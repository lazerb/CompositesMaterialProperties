package com.toviah.composites;

import java.util.ArrayList;

import Jama.Matrix;

public class Woven {
	ArrayList<LaminaUniD> layup = new ArrayList<LaminaUniD>();
	double[] layupAngles = new double[] {0, 90};
	
	double areaWarp;
	double areaFill;
	
	LaminaUniD Warp () {
		LaminaUniD layer = new LaminaUniD();
		layup.add(layer);
		return layer;
	}
	
	LaminaUniD Fill () {
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
