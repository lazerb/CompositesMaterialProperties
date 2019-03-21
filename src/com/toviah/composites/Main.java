package com.toviah.composites;

import java.util.Arrays;

public class Main {
	public static void main(String[] args) throws CloneNotSupportedException {
		CompositeUniD comp = new CompositeUniD();
		comp.layupAngles = new double[] {0, 45, -45, 90};
		
		for (int i = 0; i < comp.layupAngles.length; i++) {
			LaminaUniD layer = comp.newLamina();
			layer.fiberVolume = 13;
			layer.compositeVolume = 20;
			layer.fiberVolumeFraction = .4;
			layer.fiberTensileModulusL = 228000;
			layer.fiberShearModulusL = 95000;
			layer.fiberPoissonRatioL = .2;
			layer.matrixTensileModulusL = 3500;
			layer.matrixShearModulusL = 1250;
			layer.matrixPoissonRatioL = .33;
			layer.angle = Math.toRadians(comp.layupAngles[i]);
			layer.thickness = 1;
			layer.initialize();
		}
		comp.calculateComposite();
		System.out.println("Layup Angles:");
		System.out.println(Arrays.toString(comp.layupAngles));
		comp.compositeStiffnessMatrix.print(5, 0);
	}
}