package br.unisinos.parthenos.generator.analyzer.pojoui.entity.mixin.creator;

import br.unisinos.parthenos.generator.enumerator.PojoUIEdgeLabel;
import br.unisinos.parthenos.generator.prolog.fact.Edge;
import br.unisinos.parthenos.generator.prolog.fact.Vertex;
import br.unisinos.parthenos.generator.prolog.term.Atom;

public interface VisibleCreator {
  boolean isVisible();

  default Edge createVisibleEdge(Vertex elementVertex) {
    final String visible = Boolean.toString(this.isVisible());
    final Atom visibleAtom = new Atom(visible);
    return new Edge(elementVertex.getLabel(), PojoUIEdgeLabel.VISIBLE, visibleAtom);
  }
}
