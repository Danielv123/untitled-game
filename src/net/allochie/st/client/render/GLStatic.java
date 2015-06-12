package net.allochie.st.client.render;

import java.nio.FloatBuffer;

import net.allochie.st.client.ClientViewport;
import net.allochie.st.shared.math.Vector3;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class GLStatic {

	private static FloatBuffer fb = BufferUtils.createFloatBuffer(16);
	private static Matrix4f m = new Matrix4f();

	private static float[] mulMatrix(Matrix4f f, float[] in) {
		float[] out = new float[4];
		f.get(fb);
		for (int i = 0; i < 4; i++) {
			out[i] = in[0] * fb.get(0 * 4 + i) + in[1] * fb.get(1 * 4 + i) + in[2] * fb.get(2 * 4 + i) + in[3]
					* fb.get(3 * 4 + i);
		}
		return out;
	}

	public static Vector3 project(Matrix4f modelMatrix, Matrix4f projMatrix, ClientViewport viewport, Vector3 in) {
		float[] matrix = { (float) in.x, (float) in.y, (float) in.z, 1.0f };
		float[] m0 = mulMatrix(modelMatrix, matrix);
		float[] m1 = mulMatrix(projMatrix, m0);

		if (m1[3] == 0.0f)
			throw new IllegalArgumentException("bad projection!");
		m1[3] = (1.0f / m1[3]) * 0.5f;

		m1[0] = m1[0] * m1[3] + 0.5f;
		m1[1] = m1[1] * m1[3] + 0.5f;
		m1[2] = m1[2] * m1[3] + 0.5f;

		return new Vector3(m1[0] * viewport.width + viewport.x, m1[1] * viewport.height + viewport.y, m1[2]);
	}

	public static Vector3 unproject(Matrix4f modelMatrix, Matrix4f projMatrix, ClientViewport viewport, Vector3 in) {
		Matrix4f mat = new Matrix4f();
		Matrix4f.mul(modelMatrix, projMatrix, mat);
		float matrix[] = { (float) in.x, (float) in.y, (float) in.z, 1.0f };

		matrix[0] = (matrix[0] - viewport.x) / viewport.width;
		matrix[1] = (matrix[1] - viewport.y) / viewport.height;
		matrix[0] = (matrix[0] * 2.0f) - 1.0f;
		matrix[1] = (matrix[1] * 2.0f) - 1.0f;
		matrix[2] = (matrix[2] * 2.0f) - 1.0f;

		float[] out = mulMatrix(mat, matrix);

		if (out[3] == 0.0)
			throw new IllegalArgumentException("bad projection!");
		out[3] = 1.0f / out[3];

		return new Vector3(out[0] * out[3], out[1] * out[3], out[2] * out[3]);
	}

	public static Matrix4f glProjection(float fovy, float aspect, float zNear, float zFar) {
		m.setPerspective(fovy, aspect, zNear, zFar).get(fb);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glLoadMatrixf(fb);
		return new Matrix4f(m);
	}

	public static Matrix4f glModel(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ,
			float upX, float upY, float upZ) {
		m.setLookAt(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ).get(fb);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glLoadMatrixf(fb);
		GL11.glDepthRange(0.0f, 1.0f);
		return new Matrix4f(m);
	}

	public static void glWriteColorCube() {
		GL11.glBegin(GL11.GL_QUADS);
		// Front Face
		GL11.glColor3f(0.0f, 1.0f, 1.0f);
		GL11.glNormal3f(0.0f, 0.0f, 1.0f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(1.0f, -1.0f, 1.0f);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(1.0f, 1.0f, 1.0f);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
		// Back Face
		GL11.glColor3f(1.0f, 1.0f, 0.0f);
		GL11.glNormal3f(0.0f, 0.0f, -1.0f);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(-1.0f, -1.0f, -1.0f);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(-1.0f, 1.0f, -1.0f);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(1.0f, 1.0f, -1.0f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(1.0f, -1.0f, -1.0f);
		// Top Face
		GL11.glColor3f(1.0f, 0.0f, 1.0f);
		GL11.glNormal3f(0.0f, 1.0f, 0.0f);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(-1.0f, 1.0f, -1.0f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(1.0f, 1.0f, 1.0f);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(1.0f, 1.0f, -1.0f);
		// Bottom Face
		GL11.glColor3f(0.0f, 0.0f, 1.0f);
		GL11.glNormal3f(0.0f, -1.0f, 0.0f);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(-1.0f, -1.0f, -1.0f);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(1.0f, -1.0f, -1.0f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(1.0f, -1.0f, 1.0f);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
		// Right face
		GL11.glColor3f(1.0f, 0.0f, 0.0f);
		GL11.glNormal3f(1.0f, 0.0f, 0.0f);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(1.0f, -1.0f, -1.0f);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(1.0f, 1.0f, -1.0f);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(1.0f, 1.0f, 1.0f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(1.0f, -1.0f, 1.0f);
		// Left Face
		GL11.glColor3f(0.0f, 1.0f, 0.0f);
		GL11.glNormal3f(-1.0f, 0.0f, 0.0f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(-1.0f, -1.0f, -1.0f);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(-1.0f, 1.0f, -1.0f);

		GL11.glEnd();
	}

	public static void glWriteCube() {
		GL11.glBegin(GL11.GL_QUADS);
		// Front Face
		GL11.glNormal3f(0.0f, 0.0f, 1.0f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(1.0f, -1.0f, 1.0f);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(1.0f, 1.0f, 1.0f);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
		// Back Face
		GL11.glNormal3f(0.0f, 0.0f, -1.0f);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(-1.0f, -1.0f, -1.0f);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(-1.0f, 1.0f, -1.0f);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(1.0f, 1.0f, -1.0f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(1.0f, -1.0f, -1.0f);
		// Top Face
		GL11.glNormal3f(0.0f, 1.0f, 0.0f);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(-1.0f, 1.0f, -1.0f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(1.0f, 1.0f, 1.0f);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(1.0f, 1.0f, -1.0f);
		// Bottom Face
		GL11.glNormal3f(0.0f, -1.0f, 0.0f);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(-1.0f, -1.0f, -1.0f);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(1.0f, -1.0f, -1.0f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(1.0f, -1.0f, 1.0f);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
		// Right face
		GL11.glNormal3f(1.0f, 0.0f, 0.0f);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(1.0f, -1.0f, -1.0f);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(1.0f, 1.0f, -1.0f);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(1.0f, 1.0f, 1.0f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(1.0f, -1.0f, 1.0f);
		// Left Face
		GL11.glNormal3f(-1.0f, 0.0f, 0.0f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(-1.0f, -1.0f, -1.0f);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(-1.0f, 1.0f, 1.0f);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(-1.0f, 1.0f, -1.0f);

		GL11.glEnd();
	}

	public static void glWriteWall() {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glNormal3f(0.0f, 0.0f, 0.0f);

		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(-1.0f, -1.0f, 0.0f);

		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(1.0f, -1.0f, 0.0f);

		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(1.0f, 1.0f, 0.0f);

		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(-1.0f, 1.0f, 0.0f);

		GL11.glEnd();
	}

}
