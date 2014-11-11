package jcmendonca.tetris;

import java.awt.Color;

public enum TipoPeca {

	T(new double[][] {
	/**/{ 0, 1, 0 },
	/**/{ 1, 1, 1 } }, 1, Color.PINK),

	Z(new double[][] {
	/**/{ 2, 2, 0 },
	/**/{ 0, 2, 2 } }, 2, Color.RED),

	L(new double[][] {
	/**/{ 0, 0, 3 },
	/**/{ 3, 3, 3 } }, 3, Color.BLUE),

	O(new double[][] {
	/**/{ 4, 4 },
	/**/{ 4, 4 } }, 4, Color.YELLOW),

	I(new double[][] {
	/**/{ 5, 5, 5, 5 } }, 5, Color.CYAN),

	J(new double[][] {
	/**/{ 6, 0, 0 },
	/**/{ 6, 6, 6 } }, 6, Color.ORANGE),

	S(new double[][] {
	/**/{ 0, 7, 7 },
	/**/{ 7, 7, 0 } }, 7, Color.GREEN);

	private double[][] matriz;
	private int id;
	private Color cor;

	private TipoPeca(double[][] matriz, int id, Color cor) {
		this.matriz = matriz;
		this.id = id;
		this.cor = cor;
	}

	public double[][] getMatriz() {
		return matriz;
	}

	public int getId() {
		return id;
	}

	public Color getCor() {
		return cor;
	}
}
