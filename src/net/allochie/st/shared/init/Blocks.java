package net.allochie.st.shared.init;

import net.allochie.st.shared.render.ITextureProvider;
import net.allochie.st.shared.world.Block;
import net.allochie.st.shared.world.blocks.BlockAir;
import net.allochie.st.shared.world.blocks.BlockFoundation;

public class Blocks {

	public static void initBlocks(ITextureProvider provider) {
		Block.initBlocks(provider);
	}

	public static BlockAir air = new BlockAir(0);
	public static BlockFoundation foundation = new BlockFoundation(1);

}
