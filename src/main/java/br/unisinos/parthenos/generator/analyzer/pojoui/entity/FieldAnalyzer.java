package br.unisinos.parthenos.generator.analyzer.pojoui.entity;

import br.unisinos.parthenos.generator.analyzer.java.representation.QualifiedName;
import br.unisinos.parthenos.generator.enumerator.PojoUIEdgeLabel;
import br.unisinos.parthenos.generator.enumerator.PojoUIVertexDescriptor;
import br.unisinos.parthenos.generator.enumerator.VertexDescriptor;
import br.unisinos.parthenos.generator.prolog.fact.Edge;
import br.unisinos.parthenos.generator.prolog.fact.Fact;
import br.unisinos.parthenos.generator.prolog.fact.Vertex;
import br.unisinos.parthenos.generator.prolog.knowledgeBase.KnowledgeBase;
import br.unisinos.parthenos.pojoui.annotation.Field;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Set;

@Getter
public class FieldAnalyzer extends ElementAnalyzer {
  private static final String FIELD_PREFIX = "field:";

  private Vertex panelVertex;
  private Vertex attributeVertex;
  private VariableDeclarator variableDeclarator;

  public FieldAnalyzer(Vertex panelVertex, Vertex attributeVertex, FieldDeclaration fieldDeclaration, VariableDeclarator variableDeclarator) {
    super(fieldDeclaration);
    this.panelVertex = panelVertex;
    this.attributeVertex = attributeVertex;
    this.variableDeclarator = variableDeclarator;
  }

  @Override
  public String getElementName() {
    return this.getVariableDeclarator().getNameAsString();
  }

  @Override
  public Class<? extends Annotation> getAnnotationClass() {
    return Field.class;
  }

  @Override
  public VertexDescriptor getDescriptor() {
    return PojoUIVertexDescriptor.FIELD;
  }

  @Override
  public QualifiedName getQualifiedName() {
    final String prefixedAttributeQualifiedName = FIELD_PREFIX + this.getAttributeVertex().getLabel().getContent();
    return QualifiedName.from(prefixedAttributeQualifiedName);
  }

  private Edge createPanelConnectingEdge(Vertex fieldVertex) {
    return new Edge(this.getPanelVertex().getLabel(), PojoUIEdgeLabel.FIELD, fieldVertex.getLabel());
  }

  private Edge createAttributeConnectingEdge(Vertex fieldVertex) {
    return new Edge(this.getAttributeVertex().getLabel(), PojoUIEdgeLabel.FIELD, fieldVertex.getLabel());
  }

  @Override
  public Set<Fact> retrieveFacts() {
    if (!this.hasAnnotation()) { return Collections.emptySet(); }

    final Set<Fact> fieldFacts = super.retrieveFacts();
    final Vertex fieldVertex = KnowledgeBase.from(fieldFacts).findAnyVertex();

    fieldFacts.add(this.createPanelConnectingEdge(fieldVertex));
    fieldFacts.add(this.createAttributeConnectingEdge(fieldVertex));

    return fieldFacts;
  }
}
