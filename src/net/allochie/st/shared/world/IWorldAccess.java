package net.allochie.st.shared.world;

public interface IWorldAccess {

	public void setBlockInWorld(int x, int y, Block block, int data);

	public Block getBlockInWorld(int x, int y);

	public int getBlockDataInWorld(int x, int y);

	public void markBlockForUpdate(int x, int y);

}
