package com.toviah.composites;

import Jama.*;

public class Lamina {
//
	double fiberTensileModulusL;
	double fiberPoissonRatioL;
	double fiberShearModulusL;
	double matrixTensileModulusL;
	double matrixPoissonRatioL;
	double matrixShearModulusL;
//
	double compTensileModulusL;
	double compTensileModulusT;
	double compShearModulusLT;
	double compShearModulusTT;
	double compPoissonRatioLT;
	double compPoissonRatioTT;
	
	double fiberVolume;
	double compositeVolume;
	double resinVolume;
	double thickness;
	double angle;
	double fiberVolumeFraction;
	
	double [][] transformation = new double[3][3];
	double [][] compliance = new double[3][3];
	double angleCompTensileModulusX;
	double angleCompTensileModulusY;
	double angleCompShearModulusXY;
	double angleCompPoissonRatioXY;
	
	double compositeVolumePercentage;
	
	Matrix complianceMatrix;
	
	Matrix stiffnessMatrix;
	
	Matrix angleStiffnessMatrix;
	Matrix angleComplianceMatrix;
	
	void initialize() {
		//Set up all variables that need to be calculated.
		compTensileModulusL = fiberTensileModulusL/(2*(1+fiberPoissonRatioL));
		compTensileModulusT = (fiberTensileModulusL * matrixTensileModulusL)/(matrixTensileModulusL * fiberVolumeFraction + fiberTensileModulusL * (1 - fiberVolumeFraction));
		compShearModulusLT = fiberVolumeFraction * fiberShearModulusL + (1 - fiberVolumeFraction) * matrixShearModulusL;
		compShearModulusTT = (fiberShearModulusL * matrixShearModulusL) / (matrixShearModulusL * fiberVolumeFraction + fiberShearModulusL * (1 - fiberVolumeFraction));
		compPoissonRatioLT = fiberPoissonRatioL * matrixPoissonRatioL + (1 - fiberVolumeFraction) * matrixPoissonRatioL;
		resinVolume = compositeVolume - fiberVolume;
		
		//Set the transformation matrix
		transformation[0][0] = Math.pow(Math.cos(angle), 2);
		transformation[0][1] = Math.pow(Math.sin(angle), 2);
		transformation[0][2] = 2 * Math.sin(angle) * Math.cos(angle);
		transformation[1][0] = Math.pow(Math.sin(angle), 2);
		transformation[1][1] = Math.pow(Math.cos(angle), 2);
		transformation[1][2] = -2 * Math.sin(angle) * Math.cos(angle);
		transformation[2][0] = -1 * Math.sin(angle) * Math.cos(angle);
		transformation[2][1] = Math.sin(angle) * Math.cos(angle);
		transformation[2][2] = Math.pow(Math.cos(angle), 2) - Math.pow(Math.sin(angle), 2);
		Matrix transformationMatrix = new Matrix(transformation);
		
		//Set compliance matrix
		compliance[0][0] = 1 / compTensileModulusL;
		compliance[0][1] = -1 * compPoissonRatioLT / compTensileModulusL;
		compliance[0][2] = 0;
		compliance[1][0] = -1 * compPoissonRatioLT / compTensileModulusL;
		compliance[1][1] = 1 / compTensileModulusT;
		compliance[1][2] = 0;
		compliance[2][0] = 0;
		compliance[2][1] = 0;
		compliance[2][2] = 1 / compShearModulusLT;
		complianceMatrix = new Matrix(compliance);
		
		stiffnessMatrix = complianceMatrix.inverse();
		
		Matrix transformationMatrixInvTrans = transformationMatrix.inverse().transpose();
		angleStiffnessMatrix = transformationMatrix.times(stiffnessMatrix).times(transformationMatrixInvTrans);
		angleComplianceMatrix = angleStiffnessMatrix.inverse();
		double [][] angleCompliance = angleComplianceMatrix.getArray();
		angleCompTensileModulusX = 1 / angleCompliance[0][0];
		angleCompTensileModulusY = 1 / angleCompliance[1][1];
		angleCompShearModulusXY = 1 / angleCompliance[2][2];
		angleCompPoissonRatioXY = angleCompliance[0][1] / angleCompliance[0][0];
	}
}