package jcmendonca.tetris;
/**
 * Representa uma câmera do espaço gráfico
 * 
 * @author Janaina C Mendonça Lima
 * 
 */
public class Camera {

	private float minX;
	private float maxX;
	private float minY;
	private float maxY;

	public Camera() {
		super();
	}

	public Camera(float xMin, float xMax, float yMin, float yMax) {
		super();
		this.minX = xMin;
		this.maxX = xMax;
		this.minY = yMin;
		this.maxY = yMax;
	}

	public float getMinX() {
		return minX;
	}

	public void setMinX(float minX) {
		this.minX = minX;
	}

	public float getMaxX() {
		return maxX;
	}

	public void setMaxX(float maxX) {
		this.maxX = maxX;
	}

	public float getMinY() {
		return minY;
	}

	public void setMinY(float minY) {
		this.minY = minY;
	}

	public float getMaxY() {
		return maxY;
	}

	public void setMaxY(float maxY) {
		this.maxY = maxY;
	}

}
