package jcop.compiler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class JarAccess {
	private static JarAccess instance = new JarAccess();
	private CompilerMessageStream msg =	CompilerMessageStream.getInstance();
	
	private JarAccess() { }	
	public static JarAccess getInstance() {
		return instance;
	}
		
	public File getJarLocation() {
		URI uri = createJarUri();
		return new File(uri);
	}
	
	private URI createJarUri() {
		Class jarClass = getClass();
		try {
			return jarClass.getProtectionDomain().getCodeSource().getLocation().toURI();
		} 
		catch (URISyntaxException e) {
			msg.log("JarAccess Error", "cannot find jar:", jarClass);
			e.printStackTrace();
			return null;
		}		
	}

	public  InputStream getJarInputStream(String fileName) {
		try {			
			JarFile jar = getJarFile();			
			ZipEntry zippedLayerClass = jar.getEntry(fileName);			
			return jar.getInputStream(zippedLayerClass);
		} 
		catch (Exception e) {
			msg.log("JarAccess Error", "cannot create input string for file:", fileName);
			e.printStackTrace();
			return null;
		}		
	}

	public JarFile getJarFile() throws URISyntaxException, IOException {		
		return new JarFile(getJarLocation());
	}

}
