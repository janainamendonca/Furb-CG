package jcmendonca.tetris;

import Jama.Matrix;

public class Teste {

	public static void main(String[] args) {
		
		Matrix matrix = new Matrix(TipoPeca.T.getMatriz());
		
		matrix.print(5, 0);
		
		Matrix t1 = new Matrix( reflect( matrix.transpose().getArrayCopy()));
		t1.print(5, 0);

		Matrix t2 = new Matrix( reflect( t1.transpose().getArrayCopy()));
		t2.print(5, 0);

		Matrix t3 = new Matrix( reflect( t2.transpose().getArrayCopy()));
		t3.print(5, 0);

		Matrix t4 = new Matrix( reflect( t3.transpose().getArrayCopy()));
		t4.print(5, 0);
		
		
//		
//		double[][] reflect = reflect(t1.getArray());
//		Matrix r = new Matrix(reflect);
//		r.print(5, 0);
//		
//		r.transpose().print(5,0);
	}
	
	
	private static double[][] reflect(double[][] a){
		double[][] b = new double[a.length][a[0].length];
		
		for (int i = 0; i < a.length; i++) {
			for (int j = a[0].length - 1, k = 0; j >= 0 ; j--, k++) {
				b[i][j] = a[i][k];
			}
		}
		
		return b;
	}
}
