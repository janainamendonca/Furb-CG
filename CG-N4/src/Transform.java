import java.lang.Math;

// Internal matrix element organization reference
//             [ matrix[0] matrix[4] matrix[8]  matrix[12] ]
// Transform = [ matrix[1] matrix[5] matrix[9]  matrix[13] ]
//             [ matrix[2] matrix[6] matrix[10] matrix[14] ]
//             [ matrix[3] matrix[7] matrix[11] matrix[15] ]

public final class Transform {
	static final double RAS_DEG_TO_RAD = 0.017453292519943295769236907684886;

	private double[] matrix = { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 };

	public Transform() {
	}

	public void MakeIdentity() {
		for (int i = 0; i < 16; ++i) {
			matrix[i] = 0.0;
			matrix[0] = matrix[5] = matrix[10] = matrix[15] = 1.0;
		}
	}

	public void MakeTranslation(Point translationVector) {
		MakeIdentity();
		matrix[12] = translationVector.getX();
		matrix[13] = translationVector.getY();
		matrix[14] = translationVector.getZ();
	}

	public void MakeXRotation(double radians) {
		MakeIdentity();
		matrix[5] = Math.cos(radians);
		matrix[9] = -Math.sin(radians);
		matrix[6] = Math.sin(radians);
		matrix[10] = Math.cos(radians);
	}

	public void MakeYRotation(double radians) {
		MakeIdentity();
		matrix[0] = Math.cos(radians);
		matrix[8] = Math.sin(radians);
		matrix[2] = -Math.sin(radians);
		matrix[10] = Math.cos(radians);
	}

	public void MakeZRotation(double radians) {
		MakeIdentity();
		matrix[0] = Math.cos(radians);
		matrix[4] = -Math.sin(radians);
		matrix[1] = Math.sin(radians);
		matrix[5] = Math.cos(radians);
	}

	public void MakeScale(double sX, double sY, double sZ) {
		MakeIdentity();
		matrix[0] = sX;
		matrix[5] = sY;
		matrix[10] = sZ;
	}

	public Point transformPoint(Point point) {
		Point pointResult = new Point(matrix[0] * point.getX() + matrix[4]
				* point.getY() + matrix[8] * point.getZ() + matrix[12]
				* point.getW(), matrix[1] * point.getX() + matrix[5]
				* point.getY() + matrix[9] * point.getZ() + matrix[13]
				* point.getW(), matrix[2] * point.getX() + matrix[6]
				* point.getY() + matrix[10] * point.getZ() + matrix[14]
				* point.getW(), matrix[3] * point.getX() + matrix[7]
				* point.getY() + matrix[11] * point.getZ() + matrix[15]
				* point.getW());
		return pointResult;
	}

	public Transform transformMatrix(Transform t) {
		Transform result = new Transform();
		for (int i = 0; i < 16; ++i)
			result.matrix[i] = matrix[i % 4] * t.matrix[i / 4 * 4]
					+ matrix[(i % 4) + 4] * t.matrix[i / 4 * 4 + 1]
					+ matrix[(i % 4) + 8] * t.matrix[i / 4 * 4 + 2]
					+ matrix[(i % 4) + 12] * t.matrix[i / 4 * 4 + 3];
		return result;
	}

	public double GetElement(int index) {
		return matrix[index];
	}

	public void SetElement(int index, double value) {
		matrix[index] = value;
	}

	public double[] GetDate() {
		return matrix;
	}

	public void SetData(double[] data) {
		int i;

		for (i = 0; i < 16; i++) {
			matrix[i] = (data[i]);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("|" + GetElement(0) + " | " + GetElement(1) + " | "
				+ this.GetElement(2) + " | " + this.GetElement(3));
		sb.append('\n');
		sb.append("|" + this.GetElement(4) + " | " + this.GetElement(5) + " | "
				+ this.GetElement(6) + " | " + this.GetElement(7));
		sb.append('\n');
		sb.append("|" + this.GetElement(8) + " | " + this.GetElement(9) + " | "
				+ this.GetElement(10) + " | " + this.GetElement(11));
		sb.append('\n');
		sb.append("|" + this.GetElement(12) + " | " + this.GetElement(13)
				+ " | " + this.GetElement(14) + " | " + this.GetElement(15));
		sb.append('\n');

		return sb.toString();
	}

}