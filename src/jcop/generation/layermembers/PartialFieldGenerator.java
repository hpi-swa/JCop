package jcop.generation.layermembers;

import jcop.Globals.ID;
import static jcop.Globals.Types.*;
import jcop.compiler.JCopTypes.JCopAccess;
import AST.Access;
import AST.AssignSimpleExpr;
import AST.ClassInstanceExpr;
import AST.Expr;
import AST.ExprStmt;
import AST.FieldDeclaration;
import AST.LayerDecl;
import AST.LayerDeclaration;
import AST.List;
import AST.MethodDecl;
import AST.ParTypeAccess;
import AST.ParameterDeclaration;
import AST.PrimitiveType;
import AST.ReturnStmt;
import AST.Stmt;
import AST.StringLiteral;
import AST.TypeDecl;
import AST.VarAccess;

public class PartialFieldGenerator extends LayerMemberGenerator {	
	private FieldDeclaration partialField;
	private FieldDeclaration baseField;
	private LayerDeclaration layer;
	
	public PartialFieldGenerator(FieldDeclaration partialField, FieldDeclaration baseField, LayerDeclaration layer) {
		this.partialField = partialField;
		this.baseField = baseField;
		this.layer = layer;
	}
	
	public Stmt generatePartialFieldMetaClassInstantiation() {
		FieldDeclaration field = partialField;
		String generatedMethodName = generateDelegationMethodName(field);
		Expr metaClass =  new ClassInstanceExpr(
				JCopAccess.get(PARTIAL_FIELD), 
				createMetaClassInstantiationArgs(field, field.getID(),field.getModifiers())					
					.add(new StringLiteral(generatedMethodName)));
		return new ExprStmt(
				new VarAccess("partialMethodSignatures").qualifiesAccess(
						createMethodAccess("put", new StringLiteral("TODO"), metaClass)));
	}

	public FieldDeclaration createBaseField() {		
			FieldDeclaration field = partialField.fullCopy();			
			field.setModifiers(createPublicModifierFor(field));			
			return field;
		}
	
	public FieldDeclaration createLayeredStateField(FieldDeclaration wrappee) {
		Access typeAccess = createTypeAccess();
		String fieldName = getWrapperFieldName(wrappee);
		FieldDeclaration layeredStateDecl = createLayerdStateFieldFor(fieldName, typeAccess);
	//	moveFieldInitToInitializer(newBaseStateAccess);
		return layeredStateDecl;
	}
	
//	public MethodDecl createWrapperMethodForBaseField() {
//		return createWrapperMethodFor(baseField, new List<ParameterDeclaration>());
//	}
	
	public  MethodDecl createWrapperMethodFor(FieldDeclaration toBeWrapped) {
		//String id = getWrapperMethodName(fieldDecl);
		
		return new MethodDecl(
				createPublicModifierFor(toBeWrapped), 
				//toBeWrapped.type().createQualifiedAccess(), 
				(Access)toBeWrapped.getTypeAccess().fullCopy(),
				toBeWrapped.getID(),
				new List<ParameterDeclaration>(), 
				new List<Access>(),
				genOptBlock(
						new ReturnStmt(new VarAccess(toBeWrapped.name()))));		
	}
	
//	public  MethodDecl createWrapperMethodFor(FieldDeclaration fieldDecl) {		
//		return createWrapperMethodFor(fieldDecl);
//	}
	
//	private List<ParameterDeclaration> generateWrapperMethodParams() {
//		LayerClassGenerator layerGen =	new LayerClassGenerator(layer);
//		List<ParameterDeclaration> params = new List<ParameterDeclaration>();
//		params.add(layerGen.createLayerParam());
//		return params;
//	}

	public ParTypeAccess createTypeAccess() {
		// LayeredState<FieldType>
		Access fieldTypeAccess = createFieldTypeAccess();			
		return new ParTypeAccess(
				JCopAccess.get(LAYERED_STATE), 
				new List<Access>().add(fieldTypeAccess));
	}	
	
	private Access createFieldTypeAccess() {
		TypeDecl type = partialField.type();
		TypeDecl boxed = 
			type.isPrimitive()
			? ((PrimitiveType)type).boxed()
			//TODO kann ich auch das machen? :
			//? type.boxed()					
		    : type;
		return boxed.createQualifiedAccess();		
	}

	
	private FieldDeclaration createLayerdStateFieldFor(String fieldName, Access newBaseStateAccess) {
		FieldDeclaration dec =  
			new FieldDeclaration (
					createPublicModifierFor(partialField),
					newBaseStateAccess,					
					fieldName);
		return dec;
	}	
	
	public  String getWrapperMethodName(FieldDeclaration wrappee) {
		return ID.wrappedMethodPrefix + wrappee.getID(); 
	}
	
	public String getWrapperFieldName(FieldDeclaration field) {
		String layerName = isBaseField(field)
			? "forBase" 
			: "forLayer_" + layer.getID();			
		String id = partialField.getID();
		return "field_" + id + "_" + layerName;
	}

	private boolean isBaseField(FieldDeclaration field) {
		return field == baseField;
	}

	

	public Stmt createLayeredStateInit(FieldDeclaration initSource, FieldDeclaration initTarget) {		
		List<Expr> initArgs = new List<Expr>();
		if (initSource.hasInit())
			initArgs.add(initSource.getInit());
		return new ExprStmt(	new AssignSimpleExpr(
				new VarAccess(initTarget.getID()), 
				          new ClassInstanceExpr(createTypeAccess(), initArgs)));
	}

	public void setBaseField(FieldDeclaration baseField) {
			this.baseField = baseField;
	}

	

//public FieldDeclaration createWrapperField() {
//	Access newBaseStateAccess = createTypeAccess();
//	FieldDeclaration newBaseState = 
//		new FieldDeclaration(
//				Generation.createPublicModifierFor(field), 
//				newBaseStateAccess,
//				createWrapperFieldName());		
//	return newBaseState;
//}
	
//	private String createWrapperMethodName() {
//		return "get$$$" + field.getID();
//	}
	
//	public MethodDecl createWrapperMethodForLayeredState() {			
//		MethodDecl wrapper =  new MethodDecl(
//			createModifiers("public"),
//			createLayeredStateAccess(),
//			createWrapperMethodName(),				
//			createLayerParameter(),
//			new List<Access>(),
//			createOptBlock(new ReturnStmt(new VarAccess(getWrapperFieldID()))));
//		return wrapper;
//	}	
	}
	
	

