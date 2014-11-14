package jcmendonca.tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;
import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import jcmendonca.common.Point;

import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.texture.TextureData;

public class Main implements GLEventListener, KeyListener {

	/**/
	private GL gl;
	private GLU glu;
	private GLUT glut;
	private GLAutoDrawable glDrawable;

	/**/

	private String[] texturas = new String[] { //
	"texturas/amarelo.jpg", //
			"texturas/azul.jpg", //
			"texturas/ciano.jpg", //
			"texturas/vermelho.jpg", //
			"texturas/pink.jpg", //
			"texturas/verde.jpg", //
			"texturas/laranja.jpg", //
	};

	private IntBuffer idsTextura;

	/**/
	Tetris tetris = new Tetris();
	private Camera camera;

	public void init(GLAutoDrawable drawable) {
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glut = new GLUT();
		glDrawable.setGL(new DebugGL(gl));

		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		camera = new Camera(-200.0f, 200.0f, -300.0f, 300.0f);
		camera.setEye(new Point(0, 0, 30));
		camera.setCenter(new Point());

		float posLight[] = { 5.0f, 5.0f, 10.0f, 0.0f };
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, posLight, 0);
		gl.glEnable(GL.GL_LIGHT0);

		gl.glEnable(GL.GL_CULL_FACE);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glShadeModel(GL.GL_SMOOTH);

		gerarTexturas();

	}

	private void gerarTexturas() {

		//Gera identificadores de textura
		idsTextura = BufferUtil.newIntBuffer(texturas.length);
		gl.glGenTextures(texturas.length, idsTextura);
		idsTextura.rewind();

		for (int i = 0; i < texturas.length; i++) {

			//Carrega imagem da textura
			BufferedImage image = loadImage(texturas[i]);

			// Obtem largura e altura
			int widthImg = image.getWidth();
			int heightImg = image.getHeight();

			// Gera uma nova TextureData...
			TextureData td = new TextureData(0, 0, false, image);

			// ...e obtém um ByteBuffer a partir dela
			ByteBuffer buffer = (ByteBuffer) td.getBuffer();

			//Especifica a textura corrente usando seu identificador
			gl.glBindTexture(GL.GL_TEXTURE_2D, idsTextura.get(i));

			//Envio da textura para OpenGL
			gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, widthImg, heightImg, 0, GL.GL_BGR, GL.GL_UNSIGNED_BYTE, buffer);

			//Define os filtros de magnificação e minificação
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);

		}

	}

	private BufferedImage loadImage(String fileName) {

		// Tenta carregar o arquivo
		try {
			return ImageIO.read(new File(fileName));
		} catch (IOException e) {
			throw new RuntimeException("Erro na leitura do arquivo " + fileName);
		}

	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(60, 1, 0.1, 100);
	}

	public void display() {
		if (glDrawable != null) {

			glDrawable.display();
		}
	}

	public void display(GLAutoDrawable drawable) {
		System.out.println("Desenhando...");
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		// configurar camera

		Point eye = camera.getEye();
		Point center = camera.getCenter();
		Point up = camera.getUp();

		glu.gluLookAt(eye.getX(), eye.getY(), eye.getZ(), center.getX(), center.getY(), center.getZ(), up.getX(), up.getY(), up.getZ());

		//		drawAxis();

		tetris.desenhar(gl, glu, glut, idsTextura);
		//		tetris.desenhar2(gl, glu, glut);

		gl.glFlush();
		System.out.println("Fim desenho...");
	}

	public void drawAxis() {
		// eixo X - Red
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(10.0f, 0.0f, 0.0f);
		gl.glEnd();
		// eixo Y - Green
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(0.0f, 10.0f, 0.0f);
		gl.glEnd();
		// eixo Z - Blue
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(0.0f, 0.0f, 0.0f);
		gl.glVertex3f(0.0f, 0.0f, 10.0f);
		gl.glEnd();
	}

	public void iniciaJogo() {
		tetris.iniciarJogo(this);
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {

		case KeyEvent.VK_ESCAPE:
			System.exit(1);
			break;
		case KeyEvent.VK_1:
			camera.setEye(new Point(20, 20, 20));
			break;
		case KeyEvent.VK_2:
			camera.setEye(new Point(0, 0, 30));
			break;
		}
		glDrawable.display();
	}

	// metodo definido na interface GLEventListener.
	// "render" feito quando o modo ou dispositivo de exibicao associado foi
	// alterado.
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// System.out.println(" --- displayChanged ---");
	}

	/*
	 * M�todos n�o implementados
	 */

	public void keyReleased(KeyEvent arg0) {
		// System.out.println(" --- keyReleased ---");
	}

	public void keyTyped(KeyEvent arg0) {
		// System.out.println(" --- keyTyped ---");
	}

}
