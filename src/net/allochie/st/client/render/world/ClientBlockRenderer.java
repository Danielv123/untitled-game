package net.allochie.st.client.render.world;

import org.lwjgl.opengl.GL11;

import net.allochie.st.shared.render.ITexture;
import net.allochie.st.shared.world.Block;
import net.allochie.st.shared.world.Chunk;
import net.allochie.st.shared.world.World;

public class ClientBlockRenderer {

	public static void renderBlockInWorld(World aworld, Chunk achunk, Block ablock, int x, int y) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0.0f);
		renderBlockWithTexture(aworld, achunk, ablock, x, y);
		GL11.glPopMatrix();
	}

	private static void renderBlockWithTexture(World aworld, Chunk achunk, Block ablock, int x, int y) {
		boolean renderBack = ablock.renderBack(aworld, x, y);
		ITexture backTex = ablock.getRenderTexture(aworld, x, y);

		boolean[] renderWalls = ablock.renderWalls(aworld, x, y);
		ITexture[] wallTextures = ablock.getWallTexture(aworld, x, y);

		boolean renderCeiling = ablock.renderCeiling(aworld, x, y);
		ITexture ceilingTexture = ablock.getCeilingTexture(aworld, x, y);

		GL11.glPushMatrix();

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

		if (renderBack) {
			backTex.bind();
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(0.0f, 0.0f, 0.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(1.0f, 0.0f, 0.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(1.0f, 1.0f, 0.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(0.0f, 1.0f, 0.0f);
			GL11.glEnd();
			backTex.release();
		}

		GL11.glPopMatrix();
	}

}
