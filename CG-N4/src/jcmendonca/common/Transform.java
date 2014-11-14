package jcmendonca.common;

public class Transform {

	private double[] matrix = { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 };

	public Transform() {
	}

	public void makeIdentity() {
		for (int i = 0; i < 16; ++i) {
			matrix[i] = 0.0;
			matrix[0] = matrix[5] = matrix[10] = matrix[15] = 1.0;
		}
	}

	public void makeTranslation(float x, float y, float z) {
		makeIdentity();
		matrix[12] = x;
		matrix[13] = y;
		matrix[14] = z;
	}

	public void makeXRotation(float radians) {
		makeIdentity();
		matrix[5] = Math.cos(radians);
		matrix[9] = -Math.sin(radians);
		matrix[6] = Math.sin(radians);
		matrix[10] = Math.cos(radians);
	}

	public void makeYRotation(float radians) {
		makeIdentity();
		matrix[0] = Math.cos(radians);
		matrix[8] = Math.sin(radians);
		matrix[2] = -Math.sin(radians);
		matrix[10] = Math.cos(radians);
	}

	public void makeZRotation(float radians) {
		makeIdentity();
		matrix[0] = Math.cos(radians);
		matrix[4] = -Math.sin(radians);
		matrix[1] = Math.sin(radians);
		matrix[5] = Math.cos(radians);
	}

	public void makeScale(float sX, float sY, float sZ) {
		makeIdentity();
		matrix[0] = sX;
		matrix[5] = sY;
		matrix[10] = sZ;
	}

	public Transform transformMatrix(Transform t) {
		Transform result = new Transform();
		for (int i = 0; i < 16; ++i)
			result.matrix[i] = matrix[i % 4] * t.matrix[i / 4 * 4] + matrix[(i % 4) + 4] * t.matrix[i / 4 * 4 + 1] + matrix[(i % 4) + 8] * t.matrix[i / 4 * 4 + 2]
					+ matrix[(i % 4) + 12] * t.matrix[i / 4 * 4 + 3];
		return result;
	}

	public double getElement(int index) {
		return matrix[index];
	}

	public void setElement(int index, double value) {
		matrix[index] = value;
	}

	public double[] getDate() {
		return matrix;
	}

	public void setData(double[] data) {
		int i;

		for (i = 0; i < 16; i++) {
			matrix[i] = (data[i]);
		}
	}
}