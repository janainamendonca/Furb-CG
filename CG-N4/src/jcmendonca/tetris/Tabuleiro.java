package jcmendonca.tetris;

import Jama.Matrix;

public class Tabuleiro {

	private double[][] tabuleiro;
	private int qtdLinhas;
	private int qtdColunas;
	private int linhasPreenchidas = 0;

	public Tabuleiro() {
		this(new double[20][10]);
	}

	public Tabuleiro(double[][] tabuleiro) {
		this.tabuleiro = tabuleiro;
		this.qtdLinhas = tabuleiro.length;
		this.qtdColunas = tabuleiro[0].length;
	}

	public boolean cabePeca(Peca peca, int linha, int coluna) {
		limpaPecaTemporaria();
		double[][] matrizPeca = peca.getMatrizAtual();

		return cabePeca(matrizPeca, linha, coluna);
	}

	public boolean cabePeca(double[][] matrizPeca, int linha, int coluna) {

		if (linha < 0 || coluna < 0) {
			return false;
		}

		for (int i = 0; i < matrizPeca.length; i++) {
			for (int j = 0; j < matrizPeca[0].length; j++) {

				if (matrizPeca[i][j] != 0) {

					if ((i + linha >= qtdLinhas) || (j + coluna) >= qtdColunas || tabuleiro[i + linha][j + coluna] > 0) {
						return false;
					}

				}
			}
		}

		return true;
	}

	public boolean colocaPeca(Peca peca, int linha, int coluna) {
		limpaPecaTemporaria();
		double[][] matrizPeca = peca.getMatrizAtual();
		boolean colisao = false;

		for (int i = 0; i < matrizPeca.length; i++) {
			for (int j = 0; j < matrizPeca[0].length; j++) {

				if (matrizPeca[i][j] != 0) {

					tabuleiro[i + linha][j + coluna] = -1;

					if (linha + i == qtdLinhas - 1) {
						colisao = true; //parou na ultima linha
					} else {

						if (tabuleiro[i + linha + 1][j] != 0) {
							colisao = true; //parou 
						}

					}

				}
			}
		}

		if (colisao) {
			colocaPecaDefinitivo(peca.getTipoPeca());
			//verificar linha preenchida

			verificaLinhaPreenchida();
		}

		return colisao;
	}

	private void colocaPecaDefinitivo(TipoPeca tipoPeca) {
		for (int i = 0; i < tabuleiro.length; i++) {
			for (int j = 0; j < tabuleiro[0].length; j++) {
				if (tabuleiro[i][j] == -1) {
					tabuleiro[i][j] = tipoPeca.getId();
				}
			}
		}
	}

	private void limpaPecaTemporaria() {
		for (int i = 0; i < tabuleiro.length; i++) {
			for (int j = 0; j < tabuleiro[0].length; j++) {
				if (tabuleiro[i][j] == -1) {
					tabuleiro[i][j] = 0;
				}
			}
		}
	}

	private void verificaLinhaPreenchida() {
		for (int i = 0; i < qtdLinhas; i++) {

			boolean vazio = false;

			for (int j = 0; j < qtdColunas; j++) {

				if (tabuleiro[i][j] == 0) {
					vazio = true;
					break;
				}
			}

			if (!vazio) {
				//abaixar todas as linhas acima desta

				removeLinha(i);
				linhasPreenchidas++;
			}
		}
	}

	public int getLinhasPreenchidas() {
		return linhasPreenchidas;
	}

	void removeLinha(int linha) {

		double[] linhaAnterior = new double[qtdColunas];

		for (int i = 0; i <= linha; i++) {

			for (int j = 0; j < qtdColunas; j++) {

				double aux = tabuleiro[i][j];
				tabuleiro[i][j] = linhaAnterior[j];
				linhaAnterior[j] = aux;

			}

		}

	}

	public void print() {
		new Matrix(tabuleiro).print(2, 0);
	}

	double[][] getMatrizTabuleiro() {
		return tabuleiro;
	}

}
