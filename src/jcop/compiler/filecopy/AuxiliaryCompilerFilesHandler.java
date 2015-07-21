package jcop.compiler.filecopy;

import static jcop.Globals.Types.*;
import static jcop.Globals.Types.CONTEXT_COMPOSITION;

import java.awt.peer.SystemTrayPeer;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jcop.Globals;
import jcop.Globals.CompilerOps;
import jcop.Globals.GraphFiles;
import jcop.Globals.Msg;
import jcop.compiler.CompilerConfiguration;
import jcop.compiler.CompilerMessageStream;
import jcop.compiler.JarAccess;
import jcop.generation.jcopaspect.JCopAspect;
import jcop.output.AspectSourceCodeGeneration;
import AST.Program;

public class AuxiliaryCompilerFilesHandler {	
	private FileLookup fileHandler;
	private FileDeletion deletion;
	private StringBuffer workingDir;
	private boolean isJCop;
	private FileGenerationHandler auxFileGeneration;

	private static AuxiliaryCompilerFilesHandler instance;
	
	public static AuxiliaryCompilerFilesHandler getInstance(boolean isJCop) {
		if (instance == null)
			instance = new AuxiliaryCompilerFilesHandler(isJCop);
		return instance;
	}
	
	public static AuxiliaryCompilerFilesHandler getInstance() {
		return getInstance(true);		
	}	
	
	private AuxiliaryCompilerFilesHandler(boolean isJCop) {
		this.isJCop = isJCop;
	
		fileHandler = isJarVersion()
			? new JarFileLookup(isJCop)
			: new FileLookup(isJCop);		
	}	
	
	private FileGenerationHandler getFileGeneration() {
		if(auxFileGeneration == null)
			auxFileGeneration = new FileGenerationHandler();
		return auxFileGeneration;
	}
	
	private FileDeletion getDeletionHandler() {
		if(deletion == null)
			deletion = new  FileDeletion();
		return deletion;
	}
	
	public void createFiles() {		
		createJCopFolder();		
		initProgram();				
		copyLayerClassesToFolder(new File(getWorkingDir().toString()));
		if (CompilerConfiguration.getInstance().generateAggGraph())
			copyAggFilesToFolder(new File(getWorkingDir().toString()));
	}


	public void writeAspectsToDisc() {		
		new AspectSourceCodeGeneration(getJCopBinDir(), getFileGeneration())
				.writeToFile(JCopAspect.getInstance());		
		
	}	

	private File createJCopFolder() {
		File jcopFolder = new File(getJCopFolderName());
		getFileGeneration().generateDir(jcopFolder);
		return jcopFolder;
	}


	private String getJCopFolderName() {		
		return new StringBuffer(getWorkingDir())
			.append(File.separatorChar)
			.append(Globals.jcopFolder)
			.append(File.separatorChar).toString();
	}

	public void cleanup() {				
		getFileGeneration().deleteAllFiles();	
	}
	
    // could be a problem if no bin path is specified
	public void removeJCopSpecificClassFiles() {
		getDeletionHandler().deleteClassfilesInDir(new File(getBinDir().toString()));				
	}

	private void initProgram() {
		//String rootDir = getWorkingDir();
		addAspectRuntimeToClasspath();
		//removeJCopSpecificClassFiles();	
	}
	
	// modified by hiro
	private void copyLayerClassesToFolder(File destinationFolder) {	
		for (String sourceFile : fileHandler.collectFileNames(Globals.jcopFilesSrcFolder, getJavaClassFolderName(isJCop), "java")) {			
			copyFileToFolder(destinationFolder, sourceFile, getJavaClassFolderName(isJCop));		
		}
		for (String sourceFile : fileHandler.collectFileNames(Globals.jcopFilesSrcFolder, getJavaClassFolderName(isJCop), "jcop")) {			
			copyFileToFolder(destinationFolder, sourceFile, getJavaClassFolderName(isJCop));		
		}
	}
	
	private void copyAggFilesToFolder(File destinationFolder) {
		File aggFolder = new File(createJCopFolder().getParentFile(), GraphFiles.aggFolder);		
		aggFolder.mkdirs();
		for (String file : fileHandler.collectFileNames("", "graph" + File.separator + "agg", "ggx")) 
			copyFileToFolder(createJCopFolder().getParentFile(), file, getJavaClassFolderName(isJCop));	
	}
	 
	private  String getJavaClassFolderName(boolean isJCop) {
		return Globals.jcopFilesDestFolder ;
	}	
		
	private void copyFileToFolder(File outputFolder, String source, String sourceFolder ) {		
		try {						
			File sourceFile = new File(source);			
			
			InputStream is = fileHandler.createInputStream(source);	

			File targetLayerFile = auxFileGeneration.generateFile(outputFolder, sourceFile, sourceFolder);			
			copyToFile(is, targetLayerFile);
			is.close();			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	private void copyToFile(InputStream is, File outputFile) {
		try {						
			CompilerMessageStream log = CompilerMessageStream.getInstance();			
			log.maybeLog(Msg.fileCreation, outputFile.getName(), " to ", outputFile.getParent());
			InputStream in = new BufferedInputStream(is);			
			OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile));
			byte[] buffer = new byte[2048];
			int nBytes;
			while ((nBytes = in.read(buffer)) > 0)
				out.write(buffer, 0, nBytes);
			out.flush();
			out.close();
			//log.closeTask();
		} 
		catch (IOException e) {
			CompilerMessageStream.getInstance().log(Msg.fileCreationFailed, outputFile.getAbsolutePath());
			//msg.println("cannot create " + outputFile.getAbsolutePath());
			e.printStackTrace();
			System.exit(0);
		}
	}
	
//	private StringBuffer getSourceDir() {
//		return getDir(CompilerOps.rootDir);
//	}
	
	private File getBinDir() {
		return getDir("-d");	
	}
	
	private File getJCopBinDir() {
		return new File(getBinDir(), Globals.jcopFolder);
	}
	
	private File getDir(String ops) {
		StringBuffer path = getWorkingDir();		
		if (CompilerConfiguration.getInstance().hasOption(ops))
			path = new StringBuffer(CompilerConfiguration.getInstance().getValueForOption(ops)); 	
		return new File(path.toString());
	}	
	
	public StringBuffer getWorkingDir() {
		if (workingDir == null) 
		    workingDir = createWorkingDir();				 
		return workingDir;
	}
	
	private StringBuffer createWorkingDir() {
		String dir = CompilerConfiguration.getInstance().hasValueForOption(CompilerOps.sourcepath)
						? CompilerConfiguration.getInstance().getValueForOption(CompilerOps.sourcepath)
						:  System.getProperty("user.dir");		
		return new StringBuffer(dir);
	}

	private void addAspectRuntimeToClasspath() {
		String javaClassPath = "java.class.path";
		File jarFile = JarAccess.getInstance().getJarLocation();
		String newJar = jarFile.getParent() + File.separator + Globals.Files.aspectjRT;
		String classpath = System.getProperties().getProperty(javaClassPath, null);	
		System.getProperties().setProperty(javaClassPath, classpath + File.pathSeparator + newJar);		
	}	

	private boolean isJarVersion() {
		try {
			return JarAccess.getInstance().getJarFile() != null;
		}
		catch (Exception e) {	}
		return false;
	}

	public void removeJCopAspectClassFile() {
		// recently changed from getBinDir to getJCopBinDir
		getDeletionHandler().deleteClassfilesInDir(new File(getJCopBinDir().toString()));		
	}

	private final String[] illFormedClasses = {JCOP, COMPOSITION, CONTEXT_COMPOSITION, LINKED_HASHTABLE};
	
	public void copyIllformedClassesToSourcedumpFolder(File destinationFolder) {		
		for (String file : fileHandler.collectFileNames(Globals.jcopFilesSrcFolder, getJavaClassFolderName(isJCop), "java")) {
			for (String illFormedClass : illFormedClasses) {			
				if (file.endsWith(File.separator + illFormedClass + ".java")) {					
					copyFileToFolder(destinationFolder, file, getJavaClassFolderName(isJCop));
				}
			}
		}
	}
}
