package jcmendonca.tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.IntBuffer;
import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import jcmendonca.common.Transform;

import com.sun.opengl.util.GLUT;

public class Tetris implements KeyListener {

	private Tabuleiro tabuleiro;

	private Peca pecaAtual;

	private Peca proximaPeca;

	private Random random;

	private int linhaAtual;

	private int colunaAtual;

	private Main main;

	private float velocidadeJogo;

	private jcmendonca.tetris.Clock logicTimer;

	private int nivel;

	private int pontos;

	private boolean isNovoJogo;

	private boolean isGameOver;

	/**
	 * The number of milliseconds per frame.
	 */
	private static final long FRAME_TIME = 1000L / 50L;

	public Tetris() {

		//		double[][] matrizTabuleiro = //
		//		{ // .............0, 1, 2, 3, 4, 5, 6, 7, 8, 9
		//		/* .1 */{ 4, 4, 0, 0, 0, 0, 0, 0, 0, 0 },
		//		/* .2 */{ 4, 4, 0, 0, 0, 0, 0, 0, 0, 0 },
		//		/* .3 */{ 7, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		//		/* .4 */{ 7, 7, 0, 0, 0, 0, 0, 0, 0, 0 },
		//		/* .5 */{ 6, 7, 0, 0, 0, 0, 0, 0, 0, 0 },
		//		/* .6 */{ 6, 6, 6, 0, 0, 0, 0, 0, 0, 0 },
		//		/* .7 */{ 5, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		//		/* .8 */{ 5, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		//		/* .9 */{ 5, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		//		/* 10 */{ 5, 7, 7, 0, 0, 0, 0, 0, 0, 0 },
		//		/* 11 */{ 7, 7, 0, 0, 0, 0, 0, 0, 0, 0 },
		//		/* 12 */{ 3, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		//		/* 13 */{ 3, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		//		/* 14 */{ 3, 3, 0, 0, 0, 0, 0, 0, 0, 0 },
		//		/* 15 */{ 1, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
		//		/* 16 */{ 5, 1, 7, 7, 0, 0, 0, 0, 0, 0 },
		//		/* 17 */{ 5, 7, 7, 6, 0, 0, 5, 5, 5, 5 },
		//		/* 18 */{ 5, 6, 6, 6, 0, 4, 4, 0, 0, 0 },
		//		/* 19 */{ 5, 1, 2, 2, 0, 4, 4, 3, 4, 4 },
		//		/* 20 */{ 1, 1, 1, 2, 2, 3, 3, 3, 4, 4 } //
		//
		//		};
		//
		//		this.tabuleiro = new Tabuleiro(matrizTabuleiro);
		tabuleiro = new Tabuleiro();

	}

	public void iniciarJogo(Main main) {
		this.main = main;
		random = new Random();
		isNovoJogo = true;
		//		this.tabuleiro = new Tabuleiro();
		this.velocidadeJogo = 1.0f;

		// Timer do jogo, fica pausado até o usuário pressionar enter
		this.logicTimer = new Clock(velocidadeJogo);
		logicTimer.setPaused(true);

		while (true) {
			long inicio = System.nanoTime();

			// atualiza o tempo
			logicTimer.update();

			// se passou o tempo da velocidade do jogo, a peça atual pode ser movida para baixo.
			if (logicTimer.hasElapsedCycle()) {
				atualizarJogo();
				main.display();
			}


			/*
			 * Sleep to cap the framerate.
			 */
			long delta = (System.nanoTime() - inicio) / 1000000L;
			if (delta < FRAME_TIME) {
				try {
					Thread.sleep(FRAME_TIME - delta);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	private void atualizarJogo() {
		moveAbaixo();
		System.out.println("move abaixo");
	}

	private void resetGame() {
		this.nivel = 1;
		this.pontos = 0;
		this.velocidadeJogo = 1.0f;
		atualizaProximaPeca();
		this.isNovoJogo = false;
		this.isGameOver = false;
		tabuleiro = new Tabuleiro();
		logicTimer.reset();
		logicTimer.setCyclesPerSecond(velocidadeJogo);

		linhaAtual = 0;
		colunaAtual = 4;

		insereNovaPeca();
	}

	public void insereNovaPeca() {
		linhaAtual = 0;
		colunaAtual = 4;

		setPecaAtual(proximaPeca);
		atualizaProximaPeca();

		if (!tabuleiro.cabePeca(pecaAtual, linhaAtual, colunaAtual)) {
			//game over
			//TODO tratar
			logicTimer.setPaused(true);
			System.out.println("Game over");
		} else {
			System.out.println("nova peça");
			tabuleiro.colocaPeca(pecaAtual, linhaAtual, colunaAtual);

		}

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

			System.out.println("Colisão");

			this.velocidadeJogo += 0.035f;
			logicTimer.setCyclesPerSecond(velocidadeJogo);

			logicTimer.reset();

			insereNovaPeca();
		}
	}

	public void desenhar(GL gl, GLU glu, GLUT glut, IntBuffer idsTextura) {

		float[] corGrade = new float[] { 0.7f, 0.7f, 0.7f };

		// desenha a grade
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, corGrade, 0);
		gl.glEnable(GL.GL_LIGHTING);

		gl.glPushMatrix();
		gl.glScalef(10, 20, 5);
		glut.glutWireCube(1.0f);
		gl.glPopMatrix();
		gl.glDisable(GL.GL_LIGHTING);

		// desenha as peças

		double[][] matrizTabuleiro = tabuleiro.getMatrizTabuleiro();

		//		desenhaCubo(gl, glut, idsTextura, 5, 5, 2);

		for (int i = 0; i < matrizTabuleiro.length; i++) {
			for (int j = 0; j < matrizTabuleiro[0].length; j++) {

				double valor = matrizTabuleiro[i][j];
				if (valor != 0) {

					desenhaCubo(gl, glut, idsTextura, i, j, valor);
				}
			}
		}

	}

	private void desenhaCubo(GL gl, GLUT glut, IntBuffer idsTextura, int i, int j, double valor) {
		float[] cor = null;
		int textura = 0;

		if (valor == -1) {
			cor = pecaAtual.getTipoPeca().getCor();
			textura = pecaAtual.getTipoPeca().getId() - 1;
		} else {
			cor = TipoPeca.getCorById(valor);
			textura = (int) (valor - 1);
		}

		float x = j - 4.5f;
		float y = 9.5f - i;

		gl.glPushMatrix();

		//Transladar 1
		Transform matrix1 = new Transform();
		matrix1.makeTranslation(x, y, 2f);
		gl.glMultMatrixd(matrix1.getDate(), 0);

		//Cubo 2
		gl.glShadeModel(GL.GL_FLAT);
		gl.glNormal3f(0.0f, 0.0f, 1.0f);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, cor, 0);
		gl.glColor3f(1f, 1f, 1f);

		gl.glBindTexture(GL.GL_TEXTURE_2D, idsTextura.get(textura)); //Posiciona na Textura Caixa de Madeira
		gl.glEnable(GL.GL_TEXTURE_2D); // Habilita uso de textura

		gerarCubo(gl);

		gl.glDisable(GL.GL_TEXTURE_2D); //	Desabilita uso de textura

		gl.glPopMatrix();
	}

	private void gerarCubo(GL gl) {

		float xMin = -0.5f;
		float xMax = 0.5f;
		float yMin = -0.5f;
		float yMax = 0.5f;
		float zMin = -0.5f;
		float zMax = 0.5f;

		// Face frontal
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3f(0, 0, 1);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(xMax, yMax, zMax);
		gl.glTexCoord2f(1.0f, 1.0f);

		gl.glVertex3f(xMin, yMax, zMax);
		gl.glTexCoord2f(1.0f, 0.0f);

		gl.glVertex3f(xMin, yMin, zMax);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(xMax, yMin, zMax);
		gl.glEnd();

		// Face posterior
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3f(0, 0, -1);
		gl.glTexCoord2f(1.0f, 0.0f);

		gl.glVertex3f(xMax, yMax, zMin);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(xMax, yMin, zMin);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(xMin, yMin, zMin);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(xMin, yMax, zMin);
		gl.glEnd();

		// Face lateral esquerda
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3f(-1, 0, 0);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(xMin, yMax, zMax);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(xMin, yMax, zMin);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(xMin, yMin, zMin);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(xMin, yMin, zMax);
		gl.glEnd();

		// Face lateral direita
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3f(1, 0, 0);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(xMax, yMax, zMax);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(xMax, yMin, zMax);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(xMax, yMin, zMin);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(xMax, yMax, zMin);
		gl.glEnd();

		// Face superior
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3f(0, 1, 0);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(xMin, yMax, zMin);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(xMin, yMax, zMax);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(xMax, yMax, zMax);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(xMax, yMax, zMin);
		gl.glEnd();

		// Face inferior
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3f(0, -1, 0);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(xMin, yMin, zMin);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(xMax, yMin, zMin);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(xMax, yMin, zMax);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(xMin, yMin, zMax);
		gl.glEnd();
	}

	public void desenhar2(GL gl, GLU glu, GLUT glut) {

		float[] corGrade = new float[] { 0.7f, 0.7f, 0.7f };

		// desenha a grade
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, corGrade, 0);
		gl.glEnable(GL.GL_LIGHTING);

		gl.glPushMatrix();
		gl.glScalef(10, 20, 5);
		//		gl.glRotatef(45, 0, 1, 0);
		glut.glutWireCube(1.0f);
		gl.glPopMatrix();
		gl.glDisable(GL.GL_LIGHTING);

		// desenha as peças

		double[][] matrizTabuleiro = tabuleiro.getMatrizTabuleiro();

		for (int i = 0; i < matrizTabuleiro.length; i++) {
			for (int j = 0; j < matrizTabuleiro[0].length; j++) {

				double valor = matrizTabuleiro[i][j];
				if (valor != 0) {

					float[] cor = null;

					if (valor == -1) {
						cor = pecaAtual.getTipoPeca().getCor();
					} else {
						cor = TipoPeca.getCorById(valor);
					}

					gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, cor, 0);

					gl.glEnable(GL.GL_LIGHTING);

					gl.glPushMatrix();

					float x = j - 4.5f;
					float y = 9.5f - i;

					gl.glTranslated(x, y, 2.0f);
					glut.glutSolidCube(1.0f);
					gl.glPopMatrix();

					gl.glDisable(GL.GL_LIGHTING);
				}
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ENTER:

			if (isGameOver || isNovoJogo) {
				resetGame();
			}
			break;

		case KeyEvent.VK_LEFT:
			moveEsquerda();
			break;

		case KeyEvent.VK_RIGHT:
			moveDireita();
			break;

		case KeyEvent.VK_UP:
			rotaciona();
			break;
		case KeyEvent.VK_DOWN:
			moveAbaixo();
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
