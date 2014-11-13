package jcmendonca.tetris;

import jcmendonca.common.Point;

/**
 * Representa uma c�mera do espa�o gr�fico
 * 
 * @author Janaina C Mendon�a Lima
 * 
 */
public class Camera {

	private float minX;
	private float maxX;
	private float minY;
	private float maxY;

	private Point eye;
	private Point center;
	private Point up = new Point(0, 1, 0);

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

	public Point getEye() {
		return eye;
	}

	public void setEye(Point eye) {
		this.eye = eye;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public Point getUp() {
		return up;
	}
}
