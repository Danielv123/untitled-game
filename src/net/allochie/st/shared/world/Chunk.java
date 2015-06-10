package net.allochie.st.shared.world;

import java.util.List;

import net.allochie.st.shared.math.Vector2;

public class Chunk {

	private ChunkCoord position;

	private int width, height;
	private int[][] blocks;
	private int[][] blockData;
	private List<Tile> tiles;

	public Chunk(int width, int height) {
		this.width = width;
		this.height = height;
		this.blocks = new int[width][height];
		this.blockData = new int[width][height];
	}

	public void resize(int newWidth, int newHeight) {
		// TODO Auto-generated method stub
	}

	public ChunkCoord getPosition() {
		return position;
	}

	public boolean setBlock(Vector2 blockCoords, Block block, int data) {
		int type = Block.getTypeOfBlock(block);
		int myType = blocks[blockCoords.floorX()][blockCoords.floorY()];
		int myData = blockData[blockCoords.floorX()][blockCoords.floorY()];

		if (type != myType || data != myData) {
			blocks[blockCoords.floorX()][blockCoords.floorY()] = type;
			blockData[blockCoords.floorX()][blockCoords.floorY()] = data;
			return true;
		}
		return false;
	}

	public Block getBlock(Vector2 blockCoords) {
		int type = blocks[blockCoords.floorX()][blockCoords.floorY()];
		return Block.getBlockByType(type);
	}

	public int getBlockData(Vector2 blockCoords) {
		return blockData[blockCoords.floorX()][blockCoords.floorY()];
	}

}
