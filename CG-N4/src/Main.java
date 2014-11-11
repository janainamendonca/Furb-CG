import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener, MouseListener,
		MouseMotionListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private Point mousePosition;
	private World world;
	private Polygon currentPolygon;
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

		world = new World();
		world.setCamera();

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
		Camera camera = world.getCamera();
		glu.gluOrtho2D(camera.getMinX(), camera.getMaxX(), camera.getMinY(),
				camera.getMaxY());

		displaySRU();

		for (Polygon polygon : world.getPolygons()) {
			polygon.desenhar(gl);
		}

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
		Polygon selectedPolygon = world.getSelectedPolygon();
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ENTER:
			if (currentPolygon != null) {
				// currentPolygon.removeLastPoint();
			}
			currentPolygon = null;
			stopMoveVertex = true;
			break;
		case KeyEvent.VK_DELETE:
			currentPolygon = null;
			world.removePolygon();
			break;
		case KeyEvent.VK_O:
			primitive = Polygon.POLIGONO_ABERTO;
			break;
		case KeyEvent.VK_C:
			primitive = Polygon.POLIGONO_FECHADO;
			break;
		case KeyEvent.VK_R:
			changeColor(1, 0, 0);
			break;
		case KeyEvent.VK_G:
			changeColor(0, 1, 0);
			break;

		case KeyEvent.VK_B:
			changeColor(0, 0, 1);
			break;
		case KeyEvent.VK_ESCAPE:
			world.deselectPolygon();
			break;
		case KeyEvent.VK_D:

			if (selectedPolygon != null
					&& selectedPolygon.getSelectedVertex() != null) {
				selectedPolygon.removeSelectedVertex();
			}
			break;
		case KeyEvent.VK_CONTROL:
			stopMoveVertex = true;
			break;
		/* Translação */
		case KeyEvent.VK_RIGHT:

			if (selectedPolygon != null) {
				selectedPolygon.translation(2.0, 0.0);
			}

			break;

		case KeyEvent.VK_LEFT:
			if (selectedPolygon != null) {
				selectedPolygon.translation(-2.0, 0);
			}
			break;

		case KeyEvent.VK_UP:
			if (selectedPolygon != null) {
				selectedPolygon.translation(0, 2);
			}
			break;

		case KeyEvent.VK_DOWN:
			if (selectedPolygon != null) {
				selectedPolygon.translation(0, -2);
			}
			break;

		/* Escala */
		case KeyEvent.VK_PAGE_UP:
			if (selectedPolygon != null) {
				selectedPolygon.scale(2.0, 2.0);
			}
			break;
		case KeyEvent.VK_PAGE_DOWN:
			if (selectedPolygon != null) {
				selectedPolygon.scale(0.5, 0.5);
			}
			break;

		/* Rotação */

		case KeyEvent.VK_1:
			if (selectedPolygon != null) {
				selectedPolygon.rotate();
			}
			break;
		default:

			break;
		}

		glDrawable.display();
	}

	private void changeColor(float r, float g, float b) {
		Polygon selectedPolygon = world.getSelectedPolygon();
		if (selectedPolygon != null) {
			selectedPolygon.setColor(r, g, b);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		Polygon selectedPolygon = world.getSelectedPolygon();
		if (selectedPolygon != null
				&& selectedPolygon.getSelectedVertex() != null
				&& !stopMoveVertex) {

			Point selectedPoint = getSelectedPoint(e);
			selectedPolygon.moveSelectedVertex(selectedPoint);

			glDrawable.display();
		} else if (currentPolygon != null) {
			currentPolygon.removeLastPoint();
			currentPolygon.addPoint(new Point(getX(e.getX()), getY(e.getY())));
			glDrawable.display();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			mousePosition = getSelectedPoint(e);

			Polygon selectedPolygon = world.getSelectedPolygon();
			if (selectedPolygon != null
					&& selectedPolygon.getSelectedVertex() != null
					&& !stopMoveVertex) {
				// não faz nada, esse click é para mover vertice selecionado

			} else {

				Point point = new Point(mousePosition.getX(),
						mousePosition.getY());
				if (currentPolygon == null) {
					currentPolygon = new Polygon(point);
					if (primitive > 0) {
						currentPolygon.setPrimitive(primitive);
					}

					if (selectedPolygon != null) {
						currentPolygon.setColor(selectedPolygon.getColor());
						selectedPolygon.addPolygon(currentPolygon);
					} else {
						world.addPolygon(currentPolygon);
					}

					world.setSelectedPolygon(currentPolygon);

				} else {
					currentPolygon.removeLastPoint();
					currentPolygon.addPoint(point);
				}

				currentPolygon.addPoint(point);

				glDrawable.display();
			}
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			stopMoveVertex = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {

			Point selectedPoint = getSelectedPoint(e);
			System.out.println(selectedPoint);

			world.deselectPolygon();
			Polygon selectedPolygon = world.selectPolygon(selectedPoint);

			if (selectedPolygon != null) {
				selectedPolygon.selectVertex(selectedPoint);
			}

			glDrawable.display();
		}
	}

	private Point getSelectedPoint(MouseEvent e) {
		return new Point(getX(e.getX()), getY(e.getY()));
	}

	private float getX(float x) {

		// return x - 250;

		return -240 + x;

	}

	private float getY(float y) {
		return 230 - y;

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

	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.println(getX(e.getX())+", "+ getY(e.getY()));
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		 System.out.println(getX(e.getX())+", "+ getY(e.getY()));

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// System.out.println("mouseExited");

	}

	public void keyReleased(KeyEvent arg0) {
		// System.out.println(" --- keyReleased ---");
	}

	public void keyTyped(KeyEvent arg0) {
		// System.out.println(" --- keyTyped ---");
	}

}
