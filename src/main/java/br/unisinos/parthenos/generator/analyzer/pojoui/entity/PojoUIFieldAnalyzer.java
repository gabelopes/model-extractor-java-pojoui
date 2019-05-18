package br.unisinos.parthenos.generator.analyzer.pojoui.entity;

import br.unisinos.parthenos.generator.analyzer.java.entities.FieldAnalyzer;
import br.unisinos.parthenos.generator.prolog.fact.Fact;
import br.unisinos.parthenos.generator.prolog.fact.Vertex;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import lombok.Getter;

import java.util.Set;

@Getter
public class PojoUIFieldAnalyzer extends FieldAnalyzer {
  private Vertex panelVertex;

  public PojoUIFieldAnalyzer(Vertex typeVertex, Vertex panelVertex, FieldDeclaration fieldDeclaration) {
    super(typeVertex, fieldDeclaration);
    this.panelVertex = panelVertex;
  }

  @Override
  protected Set<Fact> createVariableFacts(VariableDeclarator variableDeclarator) {
    final PojoUIFieldVariableAnalyzer variableAnalyzer = new PojoUIFieldVariableAnalyzer(
      this.getTypeVertex(),
      this.getPanelVertex(),
      this.getFieldDeclaration(),
      variableDeclarator,
      this.getModifiers()
    );

    return variableAnalyzer.retrieveFacts();
  }
}
