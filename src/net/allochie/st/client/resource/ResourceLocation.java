package net.allochie.st.client.resource;

public class ResourceLocation {

	private String domain;
	private String name;

	public ResourceLocation(String name) {
		this("game", name);
	}

	public ResourceLocation(String domain, String name) {
		this.domain = domain;
		this.name = name;
	}

	public String getDomain() {
		return domain;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return domain + ":" + name;
	}

}
