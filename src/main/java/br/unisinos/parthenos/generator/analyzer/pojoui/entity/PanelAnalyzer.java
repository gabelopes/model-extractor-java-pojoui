package br.unisinos.parthenos.generator.analyzer.pojoui.entity;

import br.unisinos.parthenos.generator.analyzer.java.representation.QualifiedName;
import br.unisinos.parthenos.generator.enumerator.PojoUIEdgeLabel;
import br.unisinos.parthenos.generator.enumerator.PojoUIVertexDescriptor;
import br.unisinos.parthenos.generator.enumerator.VertexDescriptor;
import br.unisinos.parthenos.generator.prolog.fact.Edge;
import br.unisinos.parthenos.generator.prolog.fact.Fact;
import br.unisinos.parthenos.generator.prolog.fact.Vertex;
import br.unisinos.parthenos.generator.prolog.knowledgeBase.KnowledgeBase;
import br.unisinos.parthenos.pojoui.annotation.Panel;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Set;

@Getter
public class PanelAnalyzer extends ElementAnalyzer {
  private static final String PANEL_PREFIX = "panel:";

  private Vertex typeVertex;
  private TypeDeclaration<ClassOrInterfaceDeclaration> typeDeclaration;

  public PanelAnalyzer(Vertex typeVertex, TypeDeclaration<ClassOrInterfaceDeclaration> typeDeclaration) {
    super(typeDeclaration);
    this.typeVertex = typeVertex;
    this.typeDeclaration = typeDeclaration;
  }

  @Override
  public String getElementName() {
    return this.getTypeDeclaration().getNameAsString();
  }

  @Override
  public Class<? extends Annotation> getAnnotationClass() {
    return Panel.class;
  }

  @Override
  public VertexDescriptor getDescriptor() {
    return PojoUIVertexDescriptor.PANEL;
  }

  @Override
  public QualifiedName getQualifiedName() {
    final String prefixedTypeQualifiedName = PANEL_PREFIX + this.getTypeVertex().getLabel().getContent();
    return QualifiedName.from(prefixedTypeQualifiedName);
  }

  private Edge createConnectingEdge(Vertex panelVertex) {
    return new Edge(this.getTypeVertex().getLabel(), PojoUIEdgeLabel.PANEL, panelVertex.getLabel());
  }

  @Override
  public Set<Fact> retrieveFacts() {
    if (!this.hasAnnotation()) { return Collections.emptySet(); }

    final Set<Fact> panelFacts = super.retrieveFacts();
    final Vertex panelVertex = KnowledgeBase.from(panelFacts).findAnyVertex();

    panelFacts.add(this.createConnectingEdge(panelVertex));

    return panelFacts;
  }
}
