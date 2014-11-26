package jcmendonca.tetris;

public enum TipoPeca {

	T(new double[][] {
	/**/{ 0, 1, 0 },
	/**/{ 1, 1, 1 } }, 1, new float[] { 1, 0.6f, 0.6f }), //

	Z(new double[][] {
	/**/{ 2, 2, 0 },
	/**/{ 0, 2, 2 } }, 2, new float[] { 1, 0, 0 }), //

	L(new double[][] {
	/**/{ 0, 0, 3 },
	/**/{ 3, 3, 3 } }, 3, new float[] { 0, 0, 1 }), //

	O(new double[][] {
	/**/{ 4, 4 },
	/**/{ 4, 4 } }, 4, new float[] { 1, 1, 0 }), //

	I(new double[][] {
	/**/{ 5, 5, 5, 5 } }, 5, new float[] { 0, 1, 1 }), // 

	J(new double[][] {
	/**/{ 6, 0, 0 },
	/**/{ 6, 6, 6 } }, 6, new float[] { 1, 0.7f, 0 }), // 

	S(new double[][] {
	/**/{ 0, 7, 7 },
	/**/{ 7, 7, 0 } }, 7, new float[] { 0, 1, 0 }); //

	private double[][] matriz;
	private int id;
	private float[] cor;

	private TipoPeca(double[][] matriz, int id, float[] cor) {
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

	public float[] getCor() {
		return cor;
	}

	public static float[] getCorById(double id) {

		switch ((int) id) {
		case 1:
			return T.getCor();
		case 2:
			return Z.getCor();
		case 3:
			return L.getCor();
		case 4:
			return O.getCor();
		case 5:
			return I.getCor();
		case 6:
			return J.getCor();
		case 7:
			return S.getCor();

		default:
			break;
		}

		return null;

	}
}
