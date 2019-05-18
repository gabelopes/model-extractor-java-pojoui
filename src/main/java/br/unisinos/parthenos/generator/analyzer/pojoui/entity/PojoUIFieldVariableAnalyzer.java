package br.unisinos.parthenos.generator.analyzer.pojoui.entity;

import br.unisinos.parthenos.generator.analyzer.java.entities.FieldVariableAnalyzer;
import br.unisinos.parthenos.generator.prolog.fact.Fact;
import br.unisinos.parthenos.generator.prolog.fact.Vertex;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
public class PojoUIFieldVariableAnalyzer extends FieldVariableAnalyzer {
  private Vertex panelVertex;
  private FieldDeclaration fieldDeclaration;

  public PojoUIFieldVariableAnalyzer(Vertex typeVertex, Vertex panelVertex, FieldDeclaration fieldDeclaration, VariableDeclarator variableDeclarator, List<Modifier> modifiers) {
    super(typeVertex, variableDeclarator, modifiers);
    this.panelVertex = panelVertex;
    this.fieldDeclaration = fieldDeclaration;
  }

  protected Set<Fact> createFieldFacts() {
    final Vertex fieldVertex = this.createVertex();
    final FieldAnalyzer fieldAnalyzer = new FieldAnalyzer(this.getPanelVertex(), fieldVertex, this.getFieldDeclaration(), this.getVariableDeclarator());

    return fieldAnalyzer.retrieveFacts();
  }

  @Override
  public Set<Fact> retrieveFacts() {
    final Set<Fact> fieldFacts = super.retrieveFacts();

    fieldFacts.addAll(this.createFieldFacts());

    return fieldFacts;
  }
}
