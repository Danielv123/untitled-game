package net.allochie.st.client.pe;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;

public class PEResourceTable {

	public static enum ResourceType {
		/** Unknown type */
		UNKNOWN((short) -1, "bin"),
		/** DOS palette */
		PALETTE((short) 0xFF03, "pal"),
		/** WAVE sound */
		WAVE((short) 0xFF0A, "snd"),
		/** Classic bitmap file */
		BITMAP((short) 0x8002, "bmp"),
		/** PCS bitmap file */
		PCS((short) 0xFF02, "bmp"),
		/** Language data */
		LANG((short) 0x8005, "dat"),
		/** Long text blob */
		TEXTBLOB((short) 0xFF09, "dat"),
		/** Dialog text file */
		DLGLANG((short) 0xFF06, "dat");

		public final short type;
		public final String ext;

		ResourceType(short type, String ext) {
			this.type = type;
			this.ext = ext;
		}

		public static ResourceType typeof(short key) {
			for (ResourceType type : values())
				if (type.type == key)
					return type;
			return UNKNOWN;
		}
	}

	public static class Resource {
		int type, id, offset, length;
		byte[] data;

		public Resource(int type, int id, int offset, int length, byte[] data) {
			this.type = type;
			this.id = id;
			this.offset = offset;
			this.length = length;
			this.data = data;
		}
	}

	private HashMap<Short, HashMap<Short, Resource>> resourceMap = new HashMap<Short, HashMap<Short, Resource>>();

	public Short[] getResourceTypes() {
		return (Short[]) resourceMap.keySet().toArray(new Short[0]);
	}

	public Short[] getResourceIds(short resourceType) {
		HashMap<Short, Resource> typed = resourceMap.get(resourceType);
		return (Short[]) typed.keySet().toArray(new Short[0]);
	}

	public InputStream getResourceStream(short resourceType, short resourceId)
			throws FileNotFoundException {
		Resource rsrc = resourceMap.get(resourceType).get(resourceId);
		if (rsrc == null)
			throw new FileNotFoundException("No resource " + resourceId
					+ " under type " + resourceType);
		ByteArrayInputStream stream = new ByteArrayInputStream(rsrc.data);
		return stream;
	}

	public short getResourceType(short resourceType, short resourceId)
			throws FileNotFoundException {
		Resource rsrc = resourceMap.get(resourceType).get(resourceId);
		if (rsrc == null)
			throw new FileNotFoundException("No resource " + resourceId
					+ " under type " + resourceType);
		return (short) rsrc.type;
	}

	private int streamInt(RandomAccessFile file) throws IOException {
		int b0 = file.read();
		int b1 = file.read();
		int b2 = file.read();
		int b3 = file.read();
		return (int) ((b0) | (b1 << 8) | (b2 << 16) | (b3 << 24));
	}

	private short streamShort(RandomAccessFile file) throws IOException {
		int b0 = file.read();
		int b1 = file.read();
		return (short) ((b0) | (b1 << 8));
	}

	public void loadResources(File appFile) throws IOException {
		RandomAccessFile file = new RandomAccessFile(appFile, "r");

		file.seek(0x3C); // skip DOS header
		int off_sh = streamInt(file); // where is segex?
		file.seek(off_sh + 0x24); // go segex + 0x24
		short off_rt = streamShort(file); // where is table?
		file.seek(off_sh + 0x32); // skip more junk
		short lsa = streamShort(file); // what is logsec align?
		file.seek(off_sh + off_rt + 2); // go segex + table + word
		while (true) {
			short type = streamShort(file); // what type?
			short count = streamShort(file); // what counts?
			if (type == 0) // 0 = end of table
				break; // lea ->
			file.skipBytes(4); // skip junk dword

			for (int i = 0; i < count; i++) {
				short offset_aligned = streamShort(file);
				short length_aligned = streamShort(file);
				int offset = (int) offset_aligned << lsa; // shi << logsec
				int length = (int) length_aligned << lsa; // shi << logsec

				file.skipBytes(2); // unused word
				short id = streamShort(file); // file idx
				file.skipBytes(4); // skip reserved dword

				Resource r = new Resource(type, id, offset, length,
						new byte[length]);

				long ptr = file.getFilePointer(); // where now?
				file.seek(r.offset); // go to file
				int sz = file.read(r.data); // file => resource->data
				if (sz != r.data.length)
					System.out.println(" ** bad read ** ");
				file.seek(ptr); // back home again
				if (!resourceMap.containsKey(type))
					resourceMap.put(type, new HashMap<Short, Resource>());
				resourceMap.get(type).put(id, r);
			}
		}

		file.close();
	}

}
