package net.allochie.st.server;

import net.allochie.st.shared.network.NetworkManager;
import net.allochie.st.shared.network.packets.S03BlockChange;
import net.allochie.st.shared.world.Block;
import net.allochie.st.shared.world.World;
import net.allochie.st.shared.world.provider.ChunkProvider;

public class ServerWorld extends World {

	private NetworkManager network;

	public ServerWorld(ChunkProvider provider, NetworkManager network) {
		super(provider);
		this.network = network;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void markBlockForUpdate(int x, int y) {
		network.sendPacketToAllPlayers(new S03BlockChange(x, y, getBlockInWorld(x, y).blockid,
				getBlockDataInWorld(x, y)));
	}

}
