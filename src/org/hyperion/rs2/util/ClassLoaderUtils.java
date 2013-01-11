package org.hyperion.rs2.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

public final class ClassLoaderUtils extends ClassLoader {
	
	private final LinkedList<String> classes = new LinkedList<String>();
	
    public ClassLoaderUtils(ClassLoader parent) {
        super(parent);
    }
	
	public Class<?> reloadClass(String path) throws ClassNotFoundException {
		if(!new File("./bin/" + path.replace(".", "/") + ".class").exists()) {
			return super.loadClass(path);
		} else if (!classes.contains(path)) {
			classes.add(path);
			return super.loadClass(path);
		}
		try {
			URLConnection connection = new URL("file:./bin/" + path.replace(".", "/") + ".class").openConnection();
			InputStream input = connection.getInputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int data = input.read();
			while(data != -1) {
				buffer.write(data);
				data = input.read();
			}
			input.close();
			byte[] classData = buffer.toByteArray();
			return defineClass(path, classData, 0, classData.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
