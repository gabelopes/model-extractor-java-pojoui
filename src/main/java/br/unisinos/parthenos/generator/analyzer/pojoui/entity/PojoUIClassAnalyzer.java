package br.unisinos.parthenos.generator.analyzer.pojoui.entity;

import br.unisinos.parthenos.generator.analyzer.java.entities.ClassAnalyzer;
import br.unisinos.parthenos.generator.prolog.fact.Fact;
import br.unisinos.parthenos.generator.prolog.fact.Vertex;
import br.unisinos.parthenos.generator.prolog.knowledgeBase.KnowledgeBase;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class PojoUIClassAnalyzer extends ClassAnalyzer {
  private Vertex panelVertex;

  public PojoUIClassAnalyzer(ClassOrInterfaceDeclaration typeDeclaration) {
    super(typeDeclaration);
  }

  @Override
  protected Set<Fact> createFieldFacts(Vertex typeVertex, FieldDeclaration fieldDeclaration) {
    final PojoUIFieldAnalyzer fieldAnalyzer = new PojoUIFieldAnalyzer(typeVertex, this.getPanelVertex(), fieldDeclaration);
    return fieldAnalyzer.retrieveFacts();
  }

  private Set<Fact> createPanelFacts(Vertex typeVertex) {
    final PanelAnalyzer panelAnalyzer = new PanelAnalyzer(typeVertex, this.getTypeDeclaration());
    return panelAnalyzer.retrieveFacts();
  }

  @Override
  public Set<Fact> retrieveSpecificFacts() {
    final Set<Fact> classFacts = new HashSet<>();
    final Vertex typeVertex = this.createVertex();

    final Set<Fact> panelFacts = this.createPanelFacts(typeVertex);
    final Vertex panelVertex = KnowledgeBase.from(panelFacts).findAnyVertex();

    this.setPanelVertex(panelVertex);

    classFacts.addAll(panelFacts);
    classFacts.addAll(super.retrieveSpecificFacts());

    return classFacts;
  }
}
