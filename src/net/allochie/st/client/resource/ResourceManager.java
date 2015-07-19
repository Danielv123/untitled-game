package net.allochie.st.client.resource;

import java.io.IOException;
import java.io.InputStream;

public class ResourceManager {

	public ResourceManager() {
		// TODO Auto-generated constructor stub
	}

	public InputStream getStreamForLocation(ResourceLocation location) throws IOException {
		String domain = location.getDomain();
		if (domain.equals("simtower")) {
			return getTowerStreamForLocation(location);
		} else if (domain.equals("game")) {
			return getLocalStreamForLocation(location);
		} else
			throw new IOException("Unsupported resource domain: " + domain);
	}

	private InputStream getLocalStreamForLocation(ResourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}

	private InputStream getTowerStreamForLocation(ResourceLocation location) {
		// TODO Auto-generated method stub
		return null;
	}
}
