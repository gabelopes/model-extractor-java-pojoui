package br.unisinos.parthenos.generator.analyzer.pojoui.entity.mixin.creator;

import br.unisinos.parthenos.generator.enumerator.PojoUIEdgeLabel;
import br.unisinos.parthenos.generator.prolog.fact.Edge;
import br.unisinos.parthenos.generator.prolog.fact.Vertex;
import br.unisinos.parthenos.generator.prolog.term.Number;

public interface PositionCreator {
  int getPosition();

  default Edge createPositionEdge(Vertex elementVertex) {
    final Number<Integer> position = new Number<>(this.getPosition());
    return new Edge(elementVertex.getLabel(), PojoUIEdgeLabel.POSITION, position);
  }
}
