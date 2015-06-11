package net.allochie.st.shared.render;

public interface ITexture {

	public ITexture derive(int u0, int v0, int u1, int v1);

	public void bind();

	public void release();

}
