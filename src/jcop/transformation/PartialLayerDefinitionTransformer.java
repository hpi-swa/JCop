package jcop.transformation;

import java.util.ArrayList;

import jcop.generation.PartialLayerDefinitionGenerator;
import jcop.generation.jcopaspect.JCopAspect;
import AST.BodyDecl;
import AST.ClassDecl;
import AST.FieldDeclaration;
import AST.LayerDecl;
import AST.MethodDecl;

public class PartialLayerDefinitionTransformer extends Transformer
{
  private LayerDecl layer;
  private java.util.List<MethodDecl> methods;
  private java.util.List<FieldDeclaration> fields;
  private PartialLayerDefinitionGenerator gen;

  public PartialLayerDefinitionTransformer(LayerDecl layer)
  {
    this.layer = layer;
    this.gen = new PartialLayerDefinitionGenerator(layer);
    initLayerMembers();
  }

  protected ClassDecl transform() {
    JCopAspect.getInstance().addLayerImport(this.layer);

    for (MethodDecl pmd : this.methods) {
      PartialMethodTransformer methodTransformer = new PartialMethodTransformer(this.layer, pmd);
      MethodDecl decl = methodTransformer.transform();
      addLayerMemberToEnclosingClass(decl);
    }
    for (FieldDeclaration partialField : this.fields) {
      PartialFieldTransformer fieldTransformer = new PartialFieldTransformer(this.layer, partialField);
      fieldTransformer.transform();
    }
    this.layer.setBodyDeclList(new AST.List());
    return this.gen.createDummyClass();
  }

  private void initLayerMembers() {
    AST.List<BodyDecl> members = this.layer.getBodyDeclListNoTransform();
    this.methods = new ArrayList();
    this.fields = new ArrayList();
    for (BodyDecl member : members) {
      if ((member instanceof MethodDecl))
        this.methods.add((MethodDecl)member);
      if ((member instanceof FieldDeclaration))
        this.fields.add((FieldDeclaration)member);
    }
  }

  private void addLayerMemberToEnclosingClass(MethodDecl decl) {
    ClassDecl host = (ClassDecl)this.layer.topLevelType();
    host.resetCache();
    host.addMemberMethod(decl);
    host.resetCache();
  }
}