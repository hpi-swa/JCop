package jcop.generation;

import jcop.Globals;
import jcop.Globals.LoggingProperties;
import jcop.Globals.Msg;
import jcop.compiler.CompilerConfiguration;
import AST.Block;
import AST.Expr;
import AST.ExprStmt;
import AST.List;
import AST.MethodAccess;
import AST.MethodDecl;
import AST.Program;
import AST.Stmt;
import AST.StringLiteral;
import AST.TypeAccess;

public class RunTimeLoggingGenerator extends Generator  {		
	private static RunTimeLoggingGenerator instance = new RunTimeLoggingGenerator();
	
	public static RunTimeLoggingGenerator getInstance() {
		return RunTimeLoggingGenerator.instance;
	}
	
	protected RunTimeLoggingGenerator() {
		// nothing to do
	}

	private Block genLoggingMessage(Block block, String message, String methodName) {
		if(!Program.hasOption(Globals.CompilerOps.runtimeLogging))
			return block;	
		
		MethodAccess loggerAccess = genLoggerMethodAccess();		
		
		message = message.concat(" ").concat(methodName);
		List<Stmt> statements = block.fullCopy().getStmtList();		
		statements.insertChild(new ExprStmt(
			genLoggerTypeAccess().qualifiesAccess(
				loggerAccess.qualifiesAccess(
						createMethodAccess("info", new StringLiteral(message))))),
			0);				
		return new Block(statements);
	}
	
	private Expr genLoggerTypeAccess() {
		return new TypeAccess("java.util.logging", "Logger");
	}

	private MethodAccess genLoggerMethodAccess() {
		return anonymousLogger()
				?  createMethodAccess("getAnonymousLogger")
				:   createMethodAccess("getLogger", new StringLiteral(LoggingProperties.loggerName));
	}

	private boolean anonymousLogger() {
		return (LoggingProperties.loggerName == null || "".equals(LoggingProperties.loggerName));
	}

	public Block createBlockWithLoggingMessage(MethodDecl method, String layerName) {	  
		Block block = method.getBlock().fullCopy();		
		return this.genLoggingMessage(block, Msg.logAccessPartialMethod, method.getFullQualifiedName() + " in layer " + layerName);
	}
	
	public Block genLayeredMethodBlock(Block block, String methodName) {		
		return this.genLoggingMessage(block, Msg.logAccessLayeredMethod, methodName);
	}
	
	public Block genBaseMethodBlock(Block block, String methodName) {
		return this.genLoggingMessage(block, Msg.logAccessBaseMethod, methodName);
	}

}
