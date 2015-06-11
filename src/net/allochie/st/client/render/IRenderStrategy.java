package net.allochie.st.client.render;

public interface IRenderStrategy {

	public void enter(IRenderContext ctx);

	public void exit(IRenderContext ctx);

	public void dispose(IRenderContext ctx);

}
