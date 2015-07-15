package net.allochie.st.shared.math;

public class AABB {

	private final Vector3 max;
	private final Vector3 min;

	/**
	 * Creates a new instance from a minimum point and a maximum point.
	 */
	public AABB(Vector3 min, Vector3 max) {
		this.min = min;
		this.max = max;
	}

	public Vector3 intersectsRay(Ray3 ray, float minDist, float maxDist) {
		Vector3 invDir = new Vector3(1f / ray.direction.x, 1f / ray.direction.y, 1f / ray.direction.z);

		boolean signDirX = invDir.x < 0;
		boolean signDirY = invDir.y < 0;
		boolean signDirZ = invDir.z < 0;

		Vector3 bbox = signDirX ? max : min;
		double tmin = (bbox.x - ray.origin.x) * invDir.x;
		bbox = signDirX ? min : max;
		double tmax = (bbox.x - ray.origin.x) * invDir.x;
		bbox = signDirY ? max : min;
		double tymin = (bbox.y - ray.origin.y) * invDir.y;
		bbox = signDirY ? min : max;
		double tymax = (bbox.y - ray.origin.y) * invDir.y;

		if ((tmin > tymax) || (tymin > tmax))
			return null;

		if (tymin > tmin)
			tmin = tymin;
		if (tymax < tmax)
			tmax = tymax;

		bbox = signDirZ ? max : min;
		double tzmin = (bbox.z - ray.origin.z) * invDir.z;
		bbox = signDirZ ? min : max;
		double tzmax = (bbox.z - ray.origin.z) * invDir.z;

		if ((tmin > tzmax) || (tzmin > tmax))
			return null;

		if (tzmin > tmin)
			tmin = tzmin;
		if (tzmax < tmax)
			tmax = tzmax;
		if ((tmin < maxDist) && (tmax > minDist))
			return ray.getPointAtDistance(tmin);
		return null;
	}
}
