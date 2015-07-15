package net.allochie.st.shared.math;

public class Ray3 {

	public Vector3 origin;
	public Vector3 direction;

	public static Ray3 fromLine(Vector3 a, Vector3 b) {
		Vector3 bb = b.sub(a).normalize();
		return new Ray3(a, bb);
	}

	public Ray3(Vector3 origin, Vector3 direction) {
		this.origin = origin;
		this.direction = direction;
	}

	public Vector3 getPointAtDistance(double dist) {
		return origin.add(direction.mul(dist));
	}

}
