package jcmendonca.tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.IntBuffer;
import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import jcmendonca.common.Transform;
import Jama.Matrix;

import com.sun.opengl.util.GLUT;

public class Tetris implements KeyListener {

	private Tabuleiro tabuleiro;

	private Peca pecaAtual;

	private Peca proximaPeca;

	private Random random;

	private int linhaAtual;

	private int colunaAtual;

	private float velocidadeJogo;

	private Timer timer;

	private int nivel = 1;

	private int pontos;

	private boolean isNovoJogo;

	private boolean isGameOver;

	private static final long FRAME_TIME = 1000L / 50L;

	private Renderizador renderer;

	public Tetris() {
		tabuleiro = new Tabuleiro();
	}

	public void iniciarJogo(Renderizador main) {
		this.renderer = main;
		random = new Random();
		isNovoJogo = true;
		this.velocidadeJogo = 1.0f;

		// Timer do jogo, fica pausado até o usuário pressionar enter
		this.timer = new Timer(velocidadeJogo);
		timer.setPaused(true);

		while (true) {
			long inicio = System.currentTimeMillis();

			// atualiza o tempo
			timer.update();

			// se terminou um ciclo no timer, a peça atual pode ser movida para baixo.
			if (timer.hasElapsedCycle()) {
				moveAbaixo();
				main.display();
			}

			sleep(inicio);

		}
	}

	private void resetGame() {
		this.pontos = 0;
		this.velocidadeJogo = nivel;
		atualizaProximaPeca();
		this.isNovoJogo = false;
		this.isGameOver = false;
		tabuleiro = new Tabuleiro();
		timer.reset();
		timer.setCyclesPerSecond(velocidadeJogo);

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
			isGameOver = true;
			timer.setPaused(true);
			System.out.println("Game over");
		} else {
			System.out.println("nova peça");
			colocaPeca();

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
			System.out.println("move abaixo");
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

			int linhasPreenchidas = tabuleiro.getLinhasPreenchidas();
			if (linhasPreenchidas > 0) {
				pontos += Math.pow(linhasPreenchidas, 2);
			}

			System.out.println("Colisão");

			this.velocidadeJogo += 0.035f;
			timer.setCyclesPerSecond(velocidadeJogo);

			timer.reset();

			insereNovaPeca();
		}
	}

	public void desenhar(GL gl, GLUT glut, IntBuffer idsTextura) {

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

		for (int i = 0; i < matrizTabuleiro.length; i++) {
			for (int j = 0; j < matrizTabuleiro[0].length; j++) {

				double valor = matrizTabuleiro[i][j];
				if (valor != 0) {

					desenhaCubo(gl, idsTextura, i, j, valor);
				}
			}
		}

		if (isGameOver) {
			// avisa que deu game over

			desenhaGameOver(gl, glut);

		} else {
			// desenha a próxima peça
			desenhaProximaPeca(gl, idsTextura);

		}

		// qtd de pontos
		desenhaQtdPontos(gl, glut);

		//nível do jogo

		String texto = "Nível";
		gl.glRasterPos3f(10, 3, 2f);
		desenhaTexto(glut, texto);

		texto = String.valueOf(nivel);
		gl.glRasterPos3f(11, 1, 2f);
		desenhaTexto(glut, texto);

		if (isNovoJogo) {

			texto = "Escolha um nível";
			gl.glRasterPos3f(-10, 0, 3f);
			desenhaTexto(glut, texto);

			texto = "Pressione Enter para iniciar o jogo";
			gl.glRasterPos3f(-10, -2, 3f);
			desenhaTexto(glut, texto);

		}
	}

	private void desenhaGameOver(GL gl, GLUT glut) {
		String texto = "Game Over";
		gl.glRasterPos3f(-3, 0, 3f);
		desenhaTexto(glut, texto);

		texto = "Pressione Enter para novo jogo";
		gl.glRasterPos3f(-10, -2, 3f);
		desenhaTexto(glut, texto);
	}

	private void desenhaQtdPontos(GL gl, GLUT glut) {
		String texto = "Pontuação";
		gl.glRasterPos3f(8, 7, 2f);
		desenhaTexto(glut, texto);

		texto = String.valueOf(pontos);
		gl.glRasterPos3f(11, 5, 2f);
		desenhaTexto(glut, texto);
	}

	private void desenhaProximaPeca(GL gl, IntBuffer idsTextura) {
		if (proximaPeca != null) {

			// desenha a próxima peça

			double[][] matrizProximaPeca = proximaPeca.getMatrizAtual();

			if (proximaPeca.getTipoPeca() == TipoPeca.T) {
				matrizProximaPeca = proximaPeca.getMatrizRotacao();
				matrizProximaPeca = new Matrix(matrizProximaPeca).transpose().getArray();
				matrizProximaPeca = Peca.reflect(matrizProximaPeca);
			} else if (proximaPeca.getTipoPeca() == TipoPeca.Z || proximaPeca.getTipoPeca() == TipoPeca.S) {
				matrizProximaPeca = Peca.reflect(matrizProximaPeca);
			} else if (proximaPeca.getTipoPeca() == TipoPeca.L) {
				matrizProximaPeca = proximaPeca.getMatrizRotacao();
				matrizProximaPeca = new Matrix(matrizProximaPeca).transpose().getArray();
			} else if (proximaPeca.getTipoPeca() == TipoPeca.J) {
				matrizProximaPeca = proximaPeca.getMatrizRotacao();
				matrizProximaPeca = new Matrix(matrizProximaPeca).transpose().getArray();
				Peca.reflect(matrizProximaPeca);
			}

			int textura = proximaPeca.getTipoPeca().getId() - 1;

			for (int i = 0; i < matrizProximaPeca.length; i++) {
				for (int j = 0; j < matrizProximaPeca[0].length; j++) {

					if (matrizProximaPeca[i][j] != 0) {

						desenhaCubo(gl, idsTextura, j + 10, i + 10, textura, proximaPeca.getTipoPeca().getCor());

					}

				}

			}

		}
	}

	private void desenhaTexto(GLUT glut, String texto) {
		//desenha letra por letra na tela
		try {
			for (int i = 0; i < texto.length(); i++)
				glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18, texto.charAt(i));
		} catch (Exception e) {
		}
	}

	private void desenhaCubo(GL gl, IntBuffer idsTextura, float x, float y, int textura, float[] cor) {
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

		gl.glBindTexture(GL.GL_TEXTURE_2D, idsTextura.get(textura));
		gl.glEnable(GL.GL_TEXTURE_2D); // Habilita uso de textura

		gerarCubo(gl);

		gl.glDisable(GL.GL_TEXTURE_2D); //	Desabilita uso de textura

		gl.glPopMatrix();
	}

	private void desenhaCubo(GL gl, IntBuffer idsTextura, int i, int j, double valor) {
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

		desenhaCubo(gl, idsTextura, x, y, textura, cor);

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

			if (isNovoJogo) {
				if (nivel < 10) {
					nivel++;
					renderer.display();
				}
			} else {
				rotaciona();
			}

			break;
		case KeyEvent.VK_DOWN:
			if (isNovoJogo) {
				if (nivel > 1) {
					nivel--;
					renderer.display();
				}
			} else {
				moveAbaixo();
			}
			break;
		case KeyEvent.VK_P:
			timer.setPaused(!timer.isPaused());
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	private void sleep(long inicio) {
		long delta = (System.currentTimeMillis() - inicio);
		if (delta < FRAME_TIME) {
			try {

				Thread.sleep(FRAME_TIME - delta);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
