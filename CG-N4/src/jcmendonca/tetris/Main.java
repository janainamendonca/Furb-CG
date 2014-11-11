package jcmendonca.tetris;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private int primitive;
	private boolean stopMoveVertex;

	// "render" feito logo apos a inicializacao do contexto OpenGL.
	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));

		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);


		// ----------------------

		// Polygon polygon = new Polygon(new Point(54, 183));
		// polygon.addPoint(new Point(72, 9));
		// polygon.addPoint(new Point(83, 115));
		// polygon.addPoint(new Point(233, 139));
		// world.addPolygon(polygon);
		
		// polygon = new Polygon(new Point(-50, 50));
		// polygon.addPoint(new Point(-100, 150));
		// polygon.addPoint(new Point(-200, 20));
		// polygon.addPoint(new Point(-100, 20));
		// polygon.addPoint(new Point(-150, 50));
		// world.addPolygon(polygon);

	}

	// metodo definido na interface GLEventListener.
	// "render" feito pelo cliente OpenGL.
	public void display(GLAutoDrawable arg0) {
		System.out.println("Desenhando...");
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		// configurar window
		Camera camera = new Camera(-250.0f, 250.0f, -250.0f, 250.0f);
		glu.gluOrtho2D(camera.getMinX(), camera.getMaxX(), camera.getMinY(),
				camera.getMaxY());

		displaySRU();

		Tabuleiro tabuleiro = new Tabuleiro();
		
		tabuleiro.desenhar(gl);
		

		gl.glFlush();
		System.out.println("Fim desenho...");
	}

	public void displaySRU() {
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(-200.0f, 0.0f);
		gl.glVertex2f(200.0f, 0.0f);
		gl.glEnd();
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_LINES);
		gl.glVertex2f(0.0f, -200.0f);
		gl.glVertex2f(0.0f, 200.0f);
		gl.glEnd();
	}

	public void keyPressed(KeyEvent e) {
		
	}


	// metodo definido na interface GLEventListener.
	// "render" feito depois que a janela foi redimensionada.
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
	}

	// metodo definido na interface GLEventListener.
	// "render" feito quando o modo ou dispositivo de exibicao associado foi
	// alterado.
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// System.out.println(" --- displayChanged ---");
	}

	/*
	 * Métodos não implementados
	 */




	public void keyReleased(KeyEvent arg0) {
		// System.out.println(" --- keyReleased ---");
	}

	public void keyTyped(KeyEvent arg0) {
		// System.out.println(" --- keyTyped ---");
	}

}
