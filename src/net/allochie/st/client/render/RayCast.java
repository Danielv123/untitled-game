package net.allochie.st.client.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import net.allochie.st.client.ClientViewport;
import net.allochie.st.shared.math.Vector3;

public class RayCast {

	public static Vector3[] throwRay(ClientViewport viewport, float mouseX, float mouseY, float near, float far) {
		FloatBuffer model = BufferUtils.createFloatBuffer(16);
		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		IntBuffer vpx = BufferUtils.createIntBuffer(16);

		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, model);
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
		GL11.glGetInteger(GL11.GL_VIEWPORT, vpx);

		FloatBuffer posNear = BufferUtils.createFloatBuffer(16);
		FloatBuffer posFar = BufferUtils.createFloatBuffer(16);

		GLU.gluUnProject(mouseX, mouseY, 0.0f, model, projection, vpx, posNear);
		GLU.gluUnProject(mouseX, mouseY, 1.0f, model, projection, vpx, posFar);

		return new Vector3[] { new Vector3(posFar.get(), posFar.get(), posFar.get()),
				new Vector3(posNear.get(), posNear.get(), posNear.get()) };
	}

}
