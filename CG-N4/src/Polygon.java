import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

/**
 * Representa um Polígono.<br>
 * Pode ser aberto ou fechado, dependendo de sua configuração. <br>
 * Permite adicionar polígonos filhos, que herdam cor e são transformados junto
 * com o pai.<br>
 * 
 * @author Janaina C Mendonça Lima
 * 
 */
public class Polygon {

	public static int POLIGONO_FECHADO = GL.GL_LINE_LOOP;
	public static int POLIGONO_ABERTO = GL.GL_LINE_STRIP;

	private List<Point> points;
	private int primitive;
	private float[] color;
	private List<Polygon> polygons;

	private BBox bbox;
	private boolean selected;
	private Point selectedVertex;
	
	private Polygon parent;

	private Transform matriz = new Transform();

	public Polygon(Point initialPoint) {
		this.primitive = POLIGONO_FECHADO;
		polygons = new ArrayList<Polygon>();
		points = new ArrayList<Point>();
		color = new float[] { 0, 0, 0 };
		bbox = new BBox();
		bbox.setXMin(initialPoint.getX());
		bbox.setXMax(initialPoint.getX());
		bbox.setYMax(initialPoint.getX());
		bbox.setYMin(initialPoint.getX());

		if (initialPoint != null) {
			addPoint(initialPoint);
		}
	}

	public int getPrimitive() {
		return primitive;
	}

	/**
	 * Define se o polígono será aberto ou fechado.
	 * 
	 * @param primitive
	 *            <code>2</code> para polígono fechado, <code>3</code> para
	 *            poligono aberto
	 */
	public void setPrimitive(int primitive) {
		this.primitive = primitive;
	}

	public float[] getColor() {
		return color;
	}

	/**
	 * Define a cor para este polígono e seus filhos
	 * 
	 * @param color
	 *            a cor
	 */
	public void setColor(float[] color) {
		this.color = color;
		for (Polygon p : polygons) {
			p.setColor(color);
		}
	}

	/**
	 * Define a cor para este polígono e seus filhos
	 * 
	 * @param color
	 *            a cor
	 */
	public void setColor(float r, float g, float b) {
		this.color = new float[] { r, g, b };
		for (Polygon p : polygons) {
			p.setColor(color);
		}
	}

	/**
	 * Retorna a lista de pontos que forma este polígono
	 * 
	 * @return lista de pontos
	 */
	public List<Point> getPoints() {
		return points;
	}

	/**
	 * Retorna os polígonos filhos deste polígono
	 * 
	 * @return uma lista com os polígonos filhos, uma lista vazia se não hover
	 *         filhos.
	 */
	public List<Polygon> getPolygons() {
		return polygons;
	}

	/**
	 * Adiciona o polígono informado como filho deste polígono
	 * 
	 * @param polygon
	 *            poligono filho
	 */
	public void addPolygon(Polygon polygon) {
		this.polygons.add(polygon);
		polygon.setParent(this);
	}

	/**
	 * Adiciona um vértice (ponto) a este poligono
	 * 
	 * @param point
	 *            vértice a ser adicionado
	 */
	public void addPoint(Point point) {
		this.points.add(point);

		// recalcular bbox
		calculateBBox();
	}

	/**
	 * recalcula a bbox deste poligono
	 */
	private void calculateBBox() {

		if (points.size() > 0) {
			Point p = points.get(0);
			bbox.setXMax(p.getX());
			bbox.setXMin(p.getX());
			bbox.setYMax(p.getY());
			bbox.setYMin(p.getY());
		}

		for (Point p : points) {

			if (p.getX() < bbox.getXMin()) {
				bbox.setXMin(p.getX());
			}

			if (p.getX() > bbox.getXMax()) {
				bbox.setXMax(p.getX());
			}

			if (p.getY() < bbox.getYMin()) {
				bbox.setYMin(p.getY());
			}

			if (p.getY() > bbox.getYMax()) {
				bbox.setYMax(p.getY());
			}

		}

	}

	/**
	 * Remove o ponto do poligono
	 * 
	 * @param index
	 */
	public void removePoint(int index) {
		this.points.remove(index);
	}

	public void removeLastPoint() {
		this.points.remove(this.points.size() - 1);
	}

	public BBox getBbox() {
		return bbox;
	}

	/**
	 * Define se este poligono está ou não selecionado
	 * 
	 * @param selected
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	/**
	 * Desenha este polígono e seus filhos
	 * 
	 * @param gl
	 */
	public void desenhar(GL gl) {

		gl.glPushMatrix();
		gl.glMultMatrixd(matriz.GetDate(), 0);

		gl.glColor3f(color[0], color[1], color[2]);
		gl.glBegin(primitive);
		for (Point p : points) {
			gl.glVertex2d(p.getX(), p.getY());
		}
		gl.glEnd();

		if (isSelected()) {
			// desenha bbox

			System.out.println("Desenhando bbox");

			gl.glColor3f(1, 1, 0);

			gl.glBegin(GL.GL_LINE_LOOP);

			gl.glVertex2d(bbox.getXMin(), bbox.getYMin());
			gl.glVertex2d(bbox.getXMin(), bbox.getYMax());

			gl.glVertex2d(bbox.getXMax(), bbox.getYMax());
			gl.glVertex2d(bbox.getXMax(), bbox.getYMin());

			gl.glVertex2d(bbox.getXMin(), bbox.getYMin());

			gl.glEnd();

			if (selectedVertex != null) {
				gl.glColor3f(1.0f, 0.0f, 0.0f);
				gl.glPointSize(5.0f);
				gl.glBegin(GL.GL_POINTS);

				gl.glVertex2d(selectedVertex.getX() - 1, selectedVertex.getY());
				gl.glEnd();

			}
		}

		// percorrer os filhos e desenhar
		for (Polygon child : polygons) {
			child.desenhar(gl);
		}

		gl.glPopMatrix();

	}

	/**
	 * Seleciona o vértice mais próximo do ponto informado.
	 * 
	 * @param selectedPoint
	 */
	public void selectVertex(Point selectedPoint) {

		selectedVertex = points.get(0);
		double distance1 = -1;

		for (Point p : points) {

			if (p.getX() == selectedPoint.getX()
					&& p.getY() == selectedPoint.getY()) {
				selectedVertex = selectedPoint;
				break;
			}

			if (distance1 == -1) {
				distance1 = distance(selectedPoint, p);
			} else {

				double d = distance(selectedPoint, p);
				if (d < distance1) {
					distance1 = d;
					selectedVertex = p;
				}

			}

		}

	}

	public void moveSelectedVertex(Point moveTo) {
		selectedVertex.setX(moveTo.getX());
		selectedVertex.setY(moveTo.getY());
		calculateBBox();
	}

	public Point getSelectedVertex() {
		return selectedVertex;
	}

	private double distance(Point p1, Point p2) {
		double d = Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2)
				+ Math.pow(p2.getY() - p1.getY(), 2));
		return d;
	}

	public void removeSelectedVertex() {
		this.points.remove(selectedVertex);
		calculateBBox();

		selectedVertex = null;
	}

	public void translation(double x, double y) {
		Transform matrixTranslate = new Transform();

		Point point = new Point(x, y);
		matrixTranslate.MakeTranslation(point);
		matriz = matrixTranslate.transformMatrix(matriz);
	}

	public void scale(double xScale, double yScale) {
		//
		Transform matrixScale = new Transform();
		Transform matrixTranslate = new Transform();
		Transform matrixTranslateInverse = new Transform();
		Transform matrixGlobal = new Transform();

		// calculo do centro da bbox
		double x = (bbox.getXMin() + bbox.getXMax()) / 2;
		double y = (bbox.getYMin() + bbox.getYMax()) / 2;

		Point point = new Point(-x, -y);// translação para origem
		matrixTranslate.MakeTranslation(point);

		matrixScale.MakeScale(xScale, yScale, 1.0);

		// tranlação inversa, voltando a posição original
		point.setX(x);
		point.setY(y);
		matrixTranslateInverse.MakeTranslation(point);

		matrixGlobal = matrixTranslate.transformMatrix(matrixGlobal);
		matrixGlobal = matrixScale.transformMatrix(matrixGlobal);
		matrixGlobal = matrixTranslateInverse.transformMatrix(matrixGlobal);

		matriz = matriz.transformMatrix(matrixGlobal);
	}

	public void rotate() {
		//
		Transform matrixTranslate = new Transform();
		Transform matrixTranslateInverse = new Transform();
		Transform matrixRotate = new Transform();
		Transform matrixGlobal = new Transform();

		// calculo do centro da bbox
		double x = (bbox.getXMin() + bbox.getXMax()) / 2;
		double y = (bbox.getYMin() + bbox.getYMax()) / 2;

		Point point = new Point(-x, -y);// translação para origem
		matrixTranslate.MakeTranslation(point);

		matrixRotate.MakeZRotation(Transform.RAS_DEG_TO_RAD * 10);

		// tranlação inversa, voltando a posição original
		point.setX(x);
		point.setY(y);
		matrixTranslateInverse.MakeTranslation(point);

		matrixGlobal = matrixTranslate.transformMatrix(matrixGlobal);
		matrixGlobal = matrixRotate.transformMatrix(matrixGlobal);
		matrixGlobal = matrixTranslateInverse.transformMatrix(matrixGlobal);

		matriz = matriz.transformMatrix(matrixGlobal);

	}

	public Polygon getParent() {
		return parent;
	}

	public void setParent(Polygon parent) {
		this.parent = parent;
	}
}
