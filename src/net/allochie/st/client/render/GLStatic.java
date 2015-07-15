package net.allochie.st.client.render;

import java.nio.FloatBuffer;

import net.allochie.st.client.ClientViewport;
import net.allochie.st.shared.math.Vector3;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class GLStatic {

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

	public static void glWriteAxis() {
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3f(1.0f, 0.0f, 0.0f);
		GL11.glVertex3f(0.0f, 0.0f, 0.0f);
		GL11.glVertex3f(1.0f, 0.0f, 0.0f);

		GL11.glColor3f(0.0f, 1.0f, 0.0f);
		GL11.glVertex3f(0.0f, 0.0f, 0.0f);
		GL11.glVertex3f(0.0f, 1.0f, 0.0f);

		GL11.glColor3f(0.0f, 0.0f, 1.0f);
		GL11.glVertex3f(0.0f, 0.0f, 0.0f);
		GL11.glVertex3f(0.0f, 0.0f, 1.0f);
		GL11.glEnd();
	}

}
