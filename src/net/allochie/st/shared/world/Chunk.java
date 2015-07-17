package net.allochie.st.shared.world;

import java.util.Arrays;
import java.util.List;

import net.allochie.st.shared.math.Vector2;

public class Chunk {

	private ChunkCoord position;

	public int width, height;
	public int[][] blocks;
	public int[][] blockData;
	public List<Tile> tiles;

	public Chunk(int width, int height) {
		this.width = width;
		this.height = height;
		this.blocks = new int[width][height];
		this.blockData = new int[width][height];
	}

	public void resize(int newWidth, int newHeight) {
		// TODO Auto-generated method stub
	}

	public void setPosition(ChunkCoord coord) {
		this.position = coord;
	}

	public ChunkCoord getPosition() {
		return position;
	}

	public Vector2 getSize() {
		return new Vector2(width, height);
	}

	public boolean setBlock(Vector2 blockCoords, int type, int data) {
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

	public void fillFromData(int[][] blocks2, int[][] blockData2) {
		for (int v = 0; v < blocks2.length; v++) {
			System.arraycopy(blocks2[v], 0, blocks[v], 0, blocks[v].length);
			System.arraycopy(blockData2[v], 0, blockData[v], 0, blockData[v].length);
		}
	}

}
