package com.toviah.composites;

public class Main {
	public static void main(String[] args) throws CloneNotSupportedException {
		Composite comp = new Composite();
		comp.layupAngles = new double[] {0, 45, -45, 90};
		
		for (int i = 0; i < comp.layupAngles.length; i++) {
			Lamina layer = comp.newLamina();
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
		comp.compositeStiffnessMatrix.print(5, 0);
	}
}