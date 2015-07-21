/**
 * Copyright (c) 2009 Software Architecture Group
 *                    Hasso-Plattner-Institute, Potsdam, Germany
 *  
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package jcop.transformation;

import AST.ASTNode;
import AST.Access;
import AST.ArrayDecl;
import AST.ArrayTypeAccess;
import AST.CompilationUnit;
import AST.Expr;
import AST.List;
import AST.Modifier;
import AST.Modifiers;
import AST.ParameterDeclaration;
import AST.Program;
import AST.TypeAccess;
import AST.TypeDecl;

public class ASTTools extends ASTNode<ASTNode> {
	public static class Generation {	
		
		public static int modifierAsInt(Modifiers m) {		
			int modVal = 0;
			if (m.isPublic()) modVal += 0x0001;
			if (m.isPrivate()) modVal += 0x0002;			
			if (m.isProtected()) modVal += 0x0004;
			if (m.isStatic()) modVal += 0x0008;
			if (m.isFinal()) modVal += 0x0010;
			if (m.isSynchronized()) modVal += 0x0020;
			if (m.isNative()) modVal += 0x0100;
			if (m.isAbstract()) modVal += 0x0400;
			if (m.isStrictfp()) modVal += 0x0800;
			return modVal;
		}
//		public static String getFullQualifiedName(TypeDecl decl) {
//			return ASTTools.Generation.getFullQualifiedNameInt(decl).toString();
//		}
//		public static String getFullQualifiedName(Access access) {
//			return ASTTools.Generation.getFullQualifiedName(access.type());
//			//access.packageName() + "." + access.typeName();
//		}
		public static Modifiers removeStaticModifier(Modifiers modifiers) {					
			for (int i = 0; i < modifiers.getModifierList().getNumChild(); i++) {
				Modifier modifier = modifiers.getModifier(i);
				if (modifier.getID().equals("static"))
					modifiers.getModifierList().removeChild(i);
			}			
			return modifiers;
		}
	}

	public static class Lookup{

			public static Program lookupProgram(ASTNode node) {
				ASTNode parent = node.getParent();
				while (!(parent instanceof Program)) 
					parent = parent.getParent();				
				return (Program) parent;
			}
			
			public static CompilationUnit lookupCompilationUnit(TypeDecl decl) {
				return (CompilationUnit) decl.topLevelType().getParent().getParent();			
			}

			public static CompilationUnit lookupCompilationUnitForPackage(ASTNode node, String pckg) {
				Program prog = lookupProgram(node);
				for(Object cu : prog.getCompilationUnitList()) {
					if (((CompilationUnit)cu).getPackageDecl().equals(pckg)) {
						return (CompilationUnit)cu;				
					}
				}
				return null;		
			}

			public static TypeDecl lookupTypeDecl(ASTNode node, String pckg, String typeName) {
				CompilationUnit unit = ASTTools.Lookup.lookupCompilationUnitForPackage(node, pckg);
				return Lookup.lookupTypeDecl(unit, pckg, typeName);
			}

			public static TypeDecl lookupTypeDecl(CompilationUnit unit, String pckg, String typeName) {
				if(unit != null){
					for(TypeDecl typeDecl : unit.getTypeDeclList()) {
						if (typeDecl.getID().equals(typeName)) 
							return typeDecl;				
					}
				}
				return null;
			}
	}
}
