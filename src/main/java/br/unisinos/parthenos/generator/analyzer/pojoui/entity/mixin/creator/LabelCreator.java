package br.unisinos.parthenos.generator.analyzer.pojoui.entity.mixin.creator;

import br.unisinos.parthenos.generator.enumerator.PojoUIEdgeLabel;
import br.unisinos.parthenos.generator.prolog.fact.Edge;
import br.unisinos.parthenos.generator.prolog.fact.Vertex;

public interface LabelCreator {
  String getLabel();

  default Edge createLabelEdge(Vertex elementVertex) {
    return new Edge(elementVertex.getLabel(), PojoUIEdgeLabel.LABEL, this.getLabel());
  }
}
