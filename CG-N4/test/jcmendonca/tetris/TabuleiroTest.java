package jcmendonca.tetris;

import static org.junit.Assert.*;
import jcmendonca.tetris.Peca;
import jcmendonca.tetris.Tabuleiro;
import jcmendonca.tetris.TipoPeca;

import org.junit.Test;

public class TabuleiroTest {

	@Test
	public void testCabePecaI() {

		Tabuleiro tabuleiro = new Tabuleiro(getNewMatrizTabuleiro());

		Peca peca = new Peca(TipoPeca.I);

		assertTrue(tabuleiro.cabePeca(peca, 15, 0));
		assertFalse(tabuleiro.cabePeca(peca, 15, 8));
		assertTrue(tabuleiro.cabePeca(peca, 15, 0));
		assertTrue(tabuleiro.cabePeca(peca, 15, 4));
		assertTrue(tabuleiro.cabePeca(peca, 0, 3));

		peca.rotate();

		assertTrue(tabuleiro.cabePeca(peca, 11, 0));
		assertFalse(tabuleiro.cabePeca(peca, 15, 0));
		assertTrue(tabuleiro.cabePeca(peca, 0, 0));
		assertTrue(tabuleiro.cabePeca(peca, 0, 8));

	}

	@Test
	public void testCabePecaT() {
		Tabuleiro tabuleiro = new Tabuleiro(getNewMatrizTabuleiro());
		Peca peca = new Peca(TipoPeca.T);
		assertTrue(tabuleiro.cabePeca(peca, 0, 3));
		assertTrue(tabuleiro.cabePeca(peca, 0, 6));
		assertTrue(tabuleiro.cabePeca(peca, 14, 0));
		assertTrue(tabuleiro.cabePeca(peca, 14, 6));

		assertFalse(tabuleiro.cabePeca(peca, 15, 0));
		assertFalse(tabuleiro.cabePeca(peca, 15, 6));

		peca.rotate();

		assertTrue(tabuleiro.cabePeca(peca, 0, 0));
		assertTrue(tabuleiro.cabePeca(peca, 0, 7));
		assertTrue(tabuleiro.cabePeca(peca, 13, 5));

		assertFalse(tabuleiro.cabePeca(peca, 14, 5));

	}

	@Test
	public void testCabePeca() throws Exception {
		Tabuleiro tabuleiro = new Tabuleiro(getNewMatrizTabuleiro());

		tabuleiro.colocaPeca(new Peca(TipoPeca.I), 15, 5);
		tabuleiro.colocaPeca(new Peca(TipoPeca.T), 14, 0);

		Peca pecaO = new Peca(TipoPeca.O);
		assertTrue(tabuleiro.cabePeca(pecaO, 14, 3));

		tabuleiro.colocaPeca(pecaO, 14, 3);

		tabuleiro.print();

	}

	@Test
	public void testRemoveLinha() {
		double[][] matrizTabuleiro = //
		{ // .............0, 1, 2, 3, 4, 5, 6, 7, 8
		/*-------- .0 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .1 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .2 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .3 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .4 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .5 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .6 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .7 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .8 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .9 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 10 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 11 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 12 */{ 0, 0, 2, 2, 2, 0, 0, 0, 1 },//
				/* 13 */{ 2, 2, 2, 2, 3, 3, 4, 4, 1 },//
				/* 14 */{ 0, 2, 0, 3, 3, 0, 4, 4, 1 },//
				/* 15 */{ 1, 1, 1, 1, 0, 0, 0, 0, 1 } //
		};
		Tabuleiro tabuleiro = new Tabuleiro(matrizTabuleiro);

		tabuleiro.print();

		tabuleiro.removeLinha(13);

		double[][] matrizTabuleiro2 = //
		{ // .............0, 1, 2, 3, 4, 5, 6, 7, 8
		/*-------- .0 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .1 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .2 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .3 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .4 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .5 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .6 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .7 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .8 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .9 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 10 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 11 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 12 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 13 */{ 0, 0, 2, 2, 2, 0, 0, 0, 1 },//
				/* 14 */{ 0, 2, 0, 3, 3, 0, 4, 4, 1 },//
				/* 15 */{ 1, 1, 1, 1, 0, 0, 0, 0, 1 } //
		};

		assertArrayEquals(matrizTabuleiro2, tabuleiro.getMatrizTabuleiro());

		tabuleiro.print();
	}

	@Test
	public void testPreencheLinha() {
		double[][] matrizTabuleiro = //
		{ // .............0, 1, 2, 3, 4, 5, 6, 7, 8
		/*-------- .0 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .1 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .2 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .3 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .4 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .5 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .6 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .7 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .8 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .9 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 10 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 11 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 12 */{ 0, 0, 0, 0, 0, 0, 0, 0, 1 },//
				/* 13 */{ 2, 2, 2, 0, 3, 3, 4, 4, 1 },//
				/* 14 */{ 0, 2, 0, 3, 3, 0, 4, 4, 1 },//
				/* 15 */{ 1, 1, 1, 1, 0, 0, 0, 0, 1 } //
		};
		Tabuleiro tabuleiro = new Tabuleiro(matrizTabuleiro);

		Peca peca = new Peca(TipoPeca.T);

		peca.rotate();
		peca.rotate();

		assertTrue(tabuleiro.cabePeca(peca, 12, 2));
		boolean colisao = tabuleiro.colocaPeca(peca, 12, 2);
		
		assertTrue(colisao);

		//		tabuleiro.removeLinha(13);

		double[][] matrizTabuleiro2 = //
		{ // .............0, 1, 2, 3, 4, 5, 6, 7, 8
		/*-------- .0 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .1 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .2 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .3 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .4 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .5 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .6 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .7 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .8 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .9 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 10 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 11 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 12 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 13 */{ 0, 0, 1, 1, 1, 0, 0, 0, 1 },//
				/* 14 */{ 0, 2, 0, 3, 3, 0, 4, 4, 1 },//
				/* 15 */{ 1, 1, 1, 1, 0, 0, 0, 0, 1 } //
		};

		tabuleiro.print();
		assertArrayEquals(matrizTabuleiro2, tabuleiro.getMatrizTabuleiro());
		assertEquals(1, tabuleiro.getLinhasPreenchidas());
	}

	@Test
	public void testPreencheLinhas() {
		double[][] matrizTabuleiro = //
		{ // .............0, 1, 2, 3, 4, 5, 6, 7, 8
		/*-------- .0 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .1 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .2 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .3 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .4 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .5 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .6 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .7 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .8 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .9 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 10 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 11 */{ 1, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 12 */{ 1, 1, 0, 0, 0, 0, 0, 0, 1 },//
				/* 13 */{ 2, 2, 0, 0, 0, 3, 4, 4, 1 },//
				/* 14 */{ 2, 2, 0, 3, 3, 3, 4, 4, 1 },//
				/* 15 */{ 1, 1, 1, 1, 0, 0, 0, 0, 1 } //
		};
		Tabuleiro tabuleiro = new Tabuleiro(matrizTabuleiro);

		Peca peca = new Peca(TipoPeca.L);

		peca.rotate();
		peca.rotate();

		assertTrue(tabuleiro.cabePeca(peca, 13, 2));
		boolean colisao = tabuleiro.colocaPeca(peca, 13, 2);

		assertTrue(colisao);
		//		tabuleiro.removeLinha(13);

		double[][] matrizTabuleiro2 = //
		{ // .............0, 1, 2, 3, 4, 5, 6, 7, 8
		/*-------- .0 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .1 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .2 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .3 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .4 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .5 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .6 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .7 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .8 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .9 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 10 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 11 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 12 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 13 */{ 1, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 14 */{ 1, 1, 0, 0, 0, 0, 0, 0, 1 },//
				/* 15 */{ 1, 1, 1, 1, 0, 0, 0, 0, 1 } //
		};

		tabuleiro.print();
		assertArrayEquals(matrizTabuleiro2, tabuleiro.getMatrizTabuleiro());
		assertEquals(2, tabuleiro.getLinhasPreenchidas());

	}

	private double[][] getNewMatrizTabuleiro() {
		double[][] matrizTabuleiro = //
		{ // .............0, 1, 2, 3, 4, 5, 6, 7, 8
		/*-------- .0 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .1 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .2 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .3 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .4 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .5 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .6 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .7 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .8 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* .9 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 10 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 11 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 12 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 13 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 14 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 },//
				/* 15 */{ 0, 0, 0, 0, 0, 0, 0, 0, 0 } //
		};
		return matrizTabuleiro;
	}

}
