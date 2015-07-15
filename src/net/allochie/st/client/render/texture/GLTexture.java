package net.allochie.st.client.render.texture;

import org.lwjgl.opengl.GL11;

import net.allochie.st.shared.render.ITexture;

public class GLTexture implements ITexture {

	private int glTextureId;
	private int glTargetEnum;

	private int width;
	private int height;
	private int texWidth;
	private int texHeight;
	private float widthRatio;
	private float heightRatio;

	private float u0, u1, v0, v1;

	public GLTexture(int glTargetEnum, int glTextureId) {
		this.glTargetEnum = glTargetEnum;
		this.glTextureId = glTextureId;
	}

	@Override
	public ITexture derive(int u0, int v0, int u1, int v1) {
		GLTexture tex0 = new GLTexture(glTargetEnum, glTextureId);
		tex0.setUVAB(u0, v0, u1, v1);
		return tex0;
	}

	@Override
	public void bind() {
		GL11.glBindTexture(glTargetEnum, glTextureId);
	}

	@Override
	public void release() {
		GL11.glBindTexture(glTargetEnum, 0);
	}

	/**
	 * Set the width of the image
	 * 
	 * @param width
	 *            The width of the image
	 */
	public void setWidth(int width) {
		this.width = width;
		if (texWidth != 0)
			widthRatio = ((float) width) / texWidth;
	}

	/**
	 * Set the width of this texture
	 * 
	 * @param texWidth
	 *            The width of the texture
	 */
	public void setTextureWidth(int texWidth) {
		this.texWidth = texWidth;
		if (texWidth != 0)
			widthRatio = ((float) width) / texWidth;
	}

	/**
	 * Get the width of the physical texture
	 * 
	 * @return The width of physical texture
	 */
	public float getWidth() {
		return widthRatio;
	}

	/**
	 * Set the height of the image
	 * 
	 * @param height
	 *            The height of the image
	 */
	public void setHeight(int height) {
		this.height = height;
		if (texHeight != 0)
			heightRatio = ((float) height) / texHeight;
	}

	/**
	 * Set the height of this texture
	 * 
	 * @param texHeight
	 *            The height of the texture
	 */
	public void setTextureHeight(int texHeight) {
		this.texHeight = texHeight;
		if (texHeight != 0)
			heightRatio = ((float) height) / texHeight;
	}

	/**
	 * Get the height of the physical texture
	 * 
	 * @return The height of physical texture
	 */
	public float getHeight() {
		return heightRatio;
	}

	protected void setUVAB(float u0, float v0, float u1, float v1) {
		this.u0 = u0;
		this.v0 = v0;
		this.u1 = u1;
		this.v1 = v1;
	}

	@Override
	public float u0() {
		return u0;
	}

	@Override
	public float v0() {
		return v0;
	}

	@Override
	public float u1() {
		return u1;
	}

	@Override
	public float v1() {
		return v1;
	}

}
