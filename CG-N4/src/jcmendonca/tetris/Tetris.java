package jcmendonca.tetris;

import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.GLUT;

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

	public void desenhar(GL gl, GLU glu, GLUT glut) {

		float[] corGrade = new float[] { 0.7f, 0.7f, 0.7f };

		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, corGrade, 0);
		gl.glEnable(GL.GL_LIGHTING);

		gl.glPushMatrix();
		gl.glScalef(15, 30, 5);
		//		gl.glRotatef(45, 0, 1, 0);
		glut.glutWireCube(1.0f);
		gl.glPopMatrix();
		gl.glDisable(GL.GL_LIGHTING);

		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, new float[] { 1f, 0, 0 }, 0);
		gl.glEnable(GL.GL_LIGHTING);

		gl.glPushMatrix();
		gl.glTranslated(2.0f, 0.0f, 0f);
		glut.glutSolidCube(1.0f);
		gl.glPopMatrix();

		gl.glDisable(GL.GL_LIGHTING);


	}
}
