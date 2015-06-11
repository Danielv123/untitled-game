package net.allochie.st.client.pe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.allochie.st.client.pe.PEResourceTable.ResourceType;

public class PEResourceExtractor {

	private PEResourceTable table;

	public PEResourceExtractor(File file) throws IOException {
		table = new PEResourceTable();
		table.loadResources(file);
	}

	public void prepareAll() throws IOException {
		Short[] types = table.getResourceTypes();
		for (short type : types) {
			Short[] resources = table.getResourceIds(type);
			for (short id : resources) {
				InputStream resource = table.getResourceStream(type, id);
				ResourceType nameType = ResourceType.typeof(type);
				String namefile;
				if (nameType == ResourceType.UNKNOWN)
					namefile = nameType.name() + "/" + type + "_" + id + "." + nameType.ext;
				else
					namefile = nameType.name() + "/" + id + "." + nameType.ext;
				File which = new File("binary/src/" + namefile);
				if (!which.getParentFile().exists())
					which.getParentFile().mkdir();
				FileOutputStream fos = new FileOutputStream(which);
				passthroughStandard(resource, fos);
				resource.close();
				fos.close();
			}
		}
	}

	private void passthroughStandard(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) != -1)
			out.write(buffer, 0, len);
	}
}
