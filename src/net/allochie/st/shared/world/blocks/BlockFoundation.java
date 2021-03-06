package net.allochie.st.shared.world.blocks;

import net.allochie.st.shared.render.ITexture;
import net.allochie.st.shared.render.ITextureProvider;
import net.allochie.st.shared.world.Block;
import net.allochie.st.shared.world.IWorldAccess;

public class BlockFoundation extends Block {

	ITexture backTex;

	public BlockFoundation(int blockid) {
		super(blockid);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeTextures(ITextureProvider provider) {
		// TODO Auto-generated method stub
		System.err.println("Initializingt textures...");
		backTex = provider.loadTexture("test.png");
	}

	@Override
	public boolean renderBack(IWorldAccess world, int x, int y) {
		return true;
	}

	@Override
	public ITexture getRenderTexture(IWorldAccess world, int x, int y) {
		return backTex;
	}

	@Override
	public boolean[] renderWalls(IWorldAccess world, int x, int y) {
		// TODO Auto-generated method stub
		return new boolean[] { false, false };
	}

	@Override
	public ITexture[] getWallTexture(IWorldAccess world, int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean renderCeiling(IWorldAccess world, int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ITexture getCeilingTexture(IWorldAccess world, int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

}
