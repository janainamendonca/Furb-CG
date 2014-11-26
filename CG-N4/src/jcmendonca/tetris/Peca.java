package jcmendonca.tetris;

import Jama.Matrix;

public class Peca {

	private TipoPeca tipoPeca;
	private double[][] matrizAtual;

	public Peca(TipoPeca tipoPeca) {
		matrizAtual = new Matrix(tipoPeca.getMatriz()).getArrayCopy();
		this.tipoPeca = tipoPeca;
	}

	public TipoPeca getTipoPeca() {
		return tipoPeca;
	}

	public void setTipoPeca(TipoPeca tipoPeca) {
		this.tipoPeca = tipoPeca;
	}

	public void rotate() {
		matrizAtual = reflect(new Matrix(matrizAtual).transpose().getArray());
	}

	public double[][] getMatrizRotacao() {

		return reflect(new Matrix(matrizAtual).transpose().getArray());
	}

	public double[][] getMatrizAtual() {
		return matrizAtual;
	}

	public static double[][] reflect(double[][] a) {
		double[][] b = new double[a.length][a[0].length];

		for (int i = 0; i < a.length; i++) {
			for (int j = a[0].length - 1, k = 0; j >= 0; j--, k++) {
				b[i][j] = a[i][k];
			}
		}

		return b;
	}

}
