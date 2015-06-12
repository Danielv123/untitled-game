package net.allochie.st.client.render;

import net.allochie.st.client.ClientViewport;
import net.allochie.st.shared.math.Vector3;

public class RayCast {

	public static Vector3[] throwRay(ClientViewport viewport, int mouseX, int mouseY) {
		float winY = viewport.height - mouseY;
		Vector3[] ray = { Vector3.zero, Vector3.zero };
		ray[0] = GLStatic.unproject(viewport.modelMat, viewport.projMat, viewport, new Vector3(mouseX, winY, 0.0f));
		ray[1] = GLStatic.unproject(viewport.modelMat, viewport.projMat, viewport, new Vector3(mouseX, winY, 1.0f));
		return ray;
	}
}
