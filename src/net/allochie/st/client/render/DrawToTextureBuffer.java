package net.allochie.st.client.render;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

import org.lwjgl.opengl.ARBFramebufferObject;

public class DrawToTextureBuffer {
	public int texture;
	public int fbo;
	public int depth;

	private int virtual_width;
	private int virtual_height;

	public DrawToTextureBuffer(int w, int h) {
		this.fbo = EXTFramebufferObject.glGenFramebuffersEXT();
		this.texture = GL11.glGenTextures();
		this.depth = EXTFramebufferObject.glGenRenderbuffersEXT();
		this.virtual_width = w;
		this.virtual_height = h;
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, fbo);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, virtual_width, virtual_height, 0, GL11.GL_RGBA,
				GL11.GL_INT, (java.nio.ByteBuffer) null);
		EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
				EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, texture, 0);
		EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, depth);
		EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT,
				ARBFramebufferObject.GL_DEPTH24_STENCIL8, w, h);
		EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
				ARBFramebufferObject.GL_DEPTH_STENCIL_ATTACHMENT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, depth);
		int status = EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT);
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);

	}

	public void enter() {
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, fbo);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public void exit() {
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
	}

	public void updateResolution(int width, int height) {
		this.virtual_width = width;
		this.virtual_height = height;
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, fbo);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_INT,
				(java.nio.ByteBuffer) null);
		EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, depth);
		EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT,
				ARBFramebufferObject.GL_DEPTH24_STENCIL8, width, height);
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
	}

	public int getWidth() {
		return virtual_width;
	}

	public int getHeight() {
		return virtual_height;
	}

	public void dispose() {
		try {
			EXTFramebufferObject.glDeleteFramebuffersEXT(fbo);
			GL11.glDeleteTextures(texture);
			EXTFramebufferObject.glDeleteRenderbuffersEXT(depth);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
