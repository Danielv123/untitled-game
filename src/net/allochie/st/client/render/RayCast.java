package net.allochie.st.client.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import net.allochie.st.client.ClientViewport;
import net.allochie.st.shared.math.Vector3;

public class RayCast {

	/**
	 * Casts a ray in the viewport. The ray is cast from the cursor's
	 * coordinates at the near-depth specified to the end of the far buffer, or
	 * the far value specified.
	 * 
	 * @param viewport
	 *            The game viewport
	 * @param mouseX
	 *            The mouse x-coordinate
	 * @param mouseY
	 *            The mouse y-coordinate
	 * @param near
	 *            The near depth
	 * @param far
	 *            The far depth
	 * @return The cast ray array; the near coordinate, the far coordinate and
	 *         the first-hit depth z coordinate vector
	 */
	public static Vector3[] throwRay(ClientViewport viewport, float mouseX, float mouseY, float near, float far) {
		FloatBuffer model = BufferUtils.createFloatBuffer(16);
		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		IntBuffer vpx = BufferUtils.createIntBuffer(16);

		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, model);
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
		GL11.glGetInteger(GL11.GL_VIEWPORT, vpx);

		FloatBuffer posNear = BufferUtils.createFloatBuffer(16);
		FloatBuffer posFar = BufferUtils.createFloatBuffer(16);
		FloatBuffer z = BufferUtils.createFloatBuffer(1);

		GLU.gluUnProject(mouseX, mouseY, near, model, projection, vpx, posNear);
		GLU.gluUnProject(mouseX, mouseY, far, model, projection, vpx, posFar);

		GL11.glReadPixels((int) mouseX, (int) mouseY, 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, z);

		return new Vector3[] { new Vector3(posNear.get(), posNear.get(), posNear.get()),
				new Vector3(posFar.get(), posFar.get(), posFar.get()), new Vector3(0, 0, z.get()) };
	}

}
