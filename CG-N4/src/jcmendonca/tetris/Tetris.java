package jcmendonca.tetris;

import java.util.Random;

public class Tetris {

	private Tabuleiro tabuleiro;

	private Peca pecaAtual;

	private Peca proximaPeca;

	private Random random;

	private int linhaAtual;

	private int colunaAtual;

	public Tetris() {
		this.tabuleiro = new Tabuleiro();
		random = new Random();
	}

	public Peca getPecaAtual() {
		return pecaAtual;
	}

	public Peca getProximaPeca() {
		return proximaPeca;
	}

	public void atualizaProximaPeca() {
		this.proximaPeca = new Peca(TipoPeca.values()[random.nextInt(TipoPeca.values().length)]);
	}

	public void setPecaAtual(Peca peca) {
		this.pecaAtual = peca;
	}

	public void moveEsquerda() {
		if (tabuleiro.cabePeca(pecaAtual, linhaAtual, colunaAtual - 1)) {
			colunaAtual--;
			colocaPeca();
		}
	}

	public void moveDireita() {
		if (tabuleiro.cabePeca(pecaAtual, linhaAtual, colunaAtual + 1)) {
			colunaAtual++;
			colocaPeca();
		}
	}

	public void moveAbaixo() {
		if (tabuleiro.cabePeca(pecaAtual, linhaAtual + 1, colunaAtual)) {
			linhaAtual++;
			colocaPeca();
		}
	}

	public void rotaciona() {
		double[][] rotacao = pecaAtual.getMatrizRotacao();
		if (tabuleiro.cabePeca(rotacao, linhaAtual, colunaAtual)) {
			pecaAtual.rotate();
			colocaPeca();
		}
	}

	private void colocaPeca() {
		boolean colisao = tabuleiro.colocaPeca(pecaAtual, linhaAtual, colunaAtual);

		if (colisao) {
			setPecaAtual(proximaPeca);
			atualizaProximaPeca();
		}
	}
}
