package net.allochie.st.client.render;

import net.allochie.st.client.ClientViewport;
import net.allochie.st.shared.math.Vector3;

public class RayCast {

	public static Vector3[] throwRay(ClientViewport viewport, float mouseX, float mouseY, float near, float far) {
		float winY = viewport.height - mouseY;
		Vector3[] ray = { Vector3.zero, Vector3.zero };
		ray[0] = GLStatic.unproject(viewport.modelMat, viewport.projMat, viewport, new Vector3(mouseX, winY, near));
		ray[1] = GLStatic.unproject(viewport.modelMat, viewport.projMat, viewport, new Vector3(mouseX, winY, far));
		ray[1] = ray[1].sub(ray[0]);
		return ray;
	}

}
