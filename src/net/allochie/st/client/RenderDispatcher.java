package net.allochie.st.client;

import java.io.IOException;

import net.allochie.st.client.render.strategy.StencilBufferStrategy;
import net.allochie.st.client.render.texture.GLPNGTextureLoader;
import net.allochie.st.shared.render.ITexture;

public class RenderDispatcher {

	protected GLPNGTextureLoader pngTextureLoader = new GLPNGTextureLoader();
	protected StencilBufferStrategy stencilBuffer;

	public void prepare(ClientGame theGame) {
		stencilBuffer = new StencilBufferStrategy(800, 600);
		ITexture tex = null, tex2 = null;
		try {
			tex = pngTextureLoader.getTexture("test.png");
			tex2 = pngTextureLoader.getTexture("noidea.png");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void gameResized(ClientGame theGame, int width, int height) {
		stencilBuffer.updateResolution(width, height);
	}

	public void renderGame(ClientGame theGame) {

	}

	private void renderGameWorld(ClientGame theGame, ClientViewport viewport, ClientWorld world) {

	}

	private void renderInterface() {
	}

}
