package jcop.compiler;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;

import jcop.Globals;
import jcop.Globals.CompilerOps;

public class UsageMessage {
	private String usage;
	private UsageLines usageLines;
	private int gapLength = 3;
	private static UsageMessage singleton;
	
	public static UsageMessage getInstance() {
		if (singleton == null)
			singleton = new UsageMessage();
		return singleton;
	}

	private UsageMessage() {
		init();
	}
	
	private String getCreateDate() {		
		File f = JarAccess.getInstance().getJarLocation();
		Date date = new  Date(f.lastModified());
		return date.toString(); 
		
		
	}
		
	private void init() {		
		usage = 
			"JCop compiler " + Globals.version 
			+ " (created " + getCreateDate() + ")\n"
			+ "Usage: jcopc [-options] class\n"
			+ "where possible options include:\n";
		usageLines = new UsageLines();
		
		addUsageLine(CompilerOps.layerpath + " <path>", 
			"Specify where to find layer source files");
		addUsageLine(CompilerOps.classpath + " <path>", 
			"Specify where to find user class files and annotation processors");	
		addUsageLine(CompilerOps.sourcepath + " <path>", 
			"Specify where to find input source files. Only required if the" +
			" sources aren't located in the working directory");								
		addUsageLine(CompilerOps.xmlOutlinePath + "<path>", 
			"Generate an outline in XML format (useful for development");
		addUsageLine(CompilerOps.xmlCILOutline, 
			"Generate an outline in XML format (useful for development environments");			
		addUsageLine(CompilerOps.compiletimeLogging, 
			"Output JCop specific messages about what the compiler is doing");			
		addUsageLine(CompilerOps.runtimeLogging,
			"Loggs layer activation and composition information at runtime");
		addUsageLine(CompilerOps.verbose, 
			"Output messages about what the compiler is doing");			
		addUsageLine(CompilerOps.help,
			"Print a synopsis of standard options");
		addUsageLine(CompilerOps.dumpSources + " <path>", 
			"Dumps Java source files of the compiled classes into the specified folder.");
		addUsageLine(CompilerOps.aspectInfo, 
			"Output messages about aspect weaving");			
		addUsageLine(CompilerOps.version, 
			"Print version information.");		
	}

	private void addUsageLine(String key, String description) {
		usageLines.add(new UsageLine(key, description));
	}
	
	@Override
	public String toString() {
		return usage + usageLines.toString();		
	}
	
	class UsageLines extends LinkedList<UsageLine> {
				
		int getMaxKeyLength() {
			int max = 0;
			for (UsageLine line : this) 
				max = Math.max(max, line.getKeyLength());
			return max + gapLength;
		}
		int getMaxUsageLength() {
			int max = 0;
			for (UsageLine line : this) 
				max = Math.max(max, line.getUsageLength());
			return max;
		}
		
		public String toString() {
			int maxKeys = getMaxKeyLength();
			int maxUsages = getMaxUsageLength();
			StringBuffer b = new StringBuffer();
			for (UsageLine line : this) 
				b.append(line.toString(maxKeys, 60));
			return b.toString();
		}
		
	}
	
	
	class UsageLine {
		private String key;
		private String usage;
		
		UsageLine(String key, String usage) {
			this.key = key;
			this.usage = usage;
		}
		
		int getKeyLength() {
			return key.length();
		}
		
		int getUsageLength() {
			return usage.length();
		}
		
		String getKey() {
			return this.key;
		}
		
		String getUsage() {
			return this.usage;
		}
		
		public StringBuffer toString(int mKeyLength, int mUsageLength) {
			return getKeyWithOffset(mKeyLength).
			 	append(getUsageWithOffset(mKeyLength, mUsageLength));
		}
		
		private StringBuffer getUsageWithOffset(int mKeylength, int mUsageLangth) {
			StringBuffer b = new StringBuffer();
			String[] lines = splitUsageAfter(mUsageLangth);
			b.append(lines[0]);
			b.append("\n");
			for(int i = 1; i < lines.length; i++) {
				b.append(getBlankOffset(mKeylength));
				b.append(lines[i]);
				b.append("\n");
			}
			return b;
		}
		
		private String[] splitUsageAfter(int lineLength) {			
			int usageLength = getUsage().length();
			LinkedList<String> list = new LinkedList<String>();
			int i = 0;
			while(i < usageLength) {
				if (usageLength-i > lineLength) {
					String rawSubstr = getUsage().substring(i, i + lineLength);
					int index = rawSubstr.lastIndexOf(" ");
					String fineSubstr = getUsage().substring(i, index);									
					list.add(fineSubstr + getBlankOffset(lineLength - fineSubstr.length()));
					i += index;
				}
				else {
					String offset = getBlankOffset(lineLength - getUsage().length()).toString();
					list.add(getUsage().substring(i).trim() + offset);
					i = usageLength;
				}
			}
			return list.toArray(new String[0]);
		}
		
		

		private StringBuffer getKeyWithOffset(int length) {
			int offset = length - getKey().length();
			StringBuffer b = new StringBuffer(getKey());
			b.append(getBlankOffset(offset));							
			return b;
		}

		private StringBuffer getBlankOffset(int offset) {
			StringBuffer b = new StringBuffer();			
			for (int i = 0; i < offset; i++)
				b.append(" ");
			return b;
		}
	}
}
