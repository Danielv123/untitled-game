package net.allochie.st.shared.math;

public class Vector2 {
	/**
	 * The zero vector.
	 */
	public static final Vector2 zero = new Vector2(0, 0);

	/**
	 * The identity vector.
	 */
	public static Vector2 ident = new Vector2(1, 1);

	/**
	 * The unit vector in the x-axis.
	 */

	public static final Vector2 unitX = new Vector2(1, 0);
	/**
	 * The unit vector in the y-axis.
	 */

	public static final Vector2 unitY = new Vector2(0, 1);

	/**
	 * The unit vector in the negative x-axis.
	 */
	public static final Vector2 unitNX = new Vector2(-1, 0);

	/**
	 * The unit vector in the negative y-axis.
	 */
	public static final Vector2 unitNY = new Vector2(0, -1);

	/**
	 * The x-component of the vector.
	 */
	public final double x;

	/**
	 * The y-component of the vector.
	 */
	public final double y;

	/**
	 * Creates a new vector.
	 *
	 * @param x
	 *            The x-component of the vector.
	 * @param y
	 *            The y-component of the vector.
	 */
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Vector2(" + x + "," + y + ")";
	}

	/**
	 * Copy the Vector2.
	 *
	 * @return A copy of this Vector2.
	 */
	public Vector2 copy() {
		return new Vector2(x, y);
	}

	/**
	 * Adds the specified components to this Vector2, returns a new Vector2
	 * product.
	 *
	 * @param x
	 *            The x-component.
	 * @param y
	 *            The y-component.
	 * @return The Vector2 product.
	 */
	public Vector2 add(double x, double y) {
		return new Vector2(this.x + x, this.y + y);
	}

	/**
	 * Adds the specified Vector2 to this Vector2, returns a new Vector2
	 * product.
	 *
	 * @param v
	 *            The foreign Vector2.
	 * @return The Vector2 product.
	 */
	public Vector2 add(Vector2 v) {
		return add(v.x, v.y);
	}

	/**
	 * Subtracts the specified components to this Vector2, returns a new Vector2
	 * product.
	 *
	 * @param x
	 *            The x-component.
	 * @param y
	 *            The y-component.
	 * @return The Vector2 product.
	 */
	public Vector2 sub(double x, double y) {
		return new Vector2(this.x - x, this.y - y);
	}

	/**
	 * Subtracts the specified Vector2 to this Vector2, returns a new Vector2
	 * product.
	 *
	 * @param v
	 *            The foreign Vector2.
	 * @return The Vector2 product.
	 */
	public Vector2 sub(Vector2 v) {
		return sub(v.x, v.y);
	}

	/**
	 * Multiplies the components of this Vector2 by the constant c, returns a
	 * new Vector2 multiplication product.
	 *
	 * @param c
	 *            The constant.
	 * @return The Vector2 multiplication product.
	 */
	public Vector2 mul(double c) {
		return new Vector2(c * x, c * y);
	}

	/**
	 * Divides the components of this Vector2 by the constant c, returns a new
	 * Vector2 division product.
	 *
	 * @param c
	 *            The constant.
	 * @return The Vector2 division product.
	 */
	public Vector2 div(double c) {
		return new Vector2(x / c, y / c);
	}

	/**
	 * Determine the dot-product of this Vector2 with respect to another
	 * Vector2.
	 *
	 * @param v
	 *            The foreign Vector2 object.
	 * @return The dot product (this.x * that.x + y * that.y).
	 */
	public double dot(Vector2 v) {
		return x * v.x + y * v.y;
	}

	/**
	 * Determine the minimum vector components of this Vector2 and another
	 * Vector2, returns a new Vector2 minimum.
	 *
	 * @param v
	 *            The foreign Vector2.
	 * @return The smallest Vector2 object.
	 */
	public Vector2 min(Vector2 v) {
		return new Vector2(Math.min(x, v.x), Math.min(y, v.y));
	}

	/**
	 * Determine the maximum vector components of this Vector2 and another
	 * Vector2, returns a new Vector2 maximum.
	 *
	 * @param v
	 *            The foreign Vector2.
	 * @return The largest Vector2 object.
	 */
	public Vector2 max(Vector2 v) {
		return new Vector2(Math.max(x, v.x), Math.max(y, v.y));
	}

	/**
	 * Determine the distance between this Vector2 and another Vector2.
	 *
	 * @param v
	 *            The foreign Vector2.
	 * @return The distance between this Vector2 and the foreign Vector2.
	 */
	public double distanceTo(Vector2 v) {
		double dx = x - v.x, dy = y - v.y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * Determines the length of this Vector2.
	 *
	 * @return The length of this Vector2.
	 */
	public double mag() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	/**
	 * Returns the unit vector for this vector.
	 *
	 * @return The unit vector for this vector.
	 */
	public Vector2 unitV() {
		return new Vector2(x / mag(), y / mag());
	}

	/**
	 * Calculates the floored x-component of this Vector2.
	 *
	 * @return The floored x-component of this Vector2.
	 */
	public int floorX() {
		return (int) Math.round(x);
	}

	/**
	 * Calculates the floored y-component of this Vector2.
	 *
	 * @return The floored y-component of this Vector2.
	 */
	public int floorY() {
		return (int) Math.round(y);
	}

	/**
	 * Computes the angle pre normal.
	 *
	 * @param mul
	 *            The other Vector2.
	 * @return The angle pre normal.
	 */
	public float anglePNorm(Vector2 mul) {
		return (float) Math.acos(dot(mul));
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Vector2))
			return false;
		Vector2 that = (Vector2) o;
		return x == that.x && y == that.y;
	}
}
