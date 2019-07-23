package br.unisinos.parthenos.generator.analyzer.pojoui.entity;

import br.unisinos.parthenos.generator.analyzer.FactAnalyzer;
import br.unisinos.parthenos.generator.analyzer.java.entities.mixins.creator.VertexCreator;
import br.unisinos.parthenos.generator.analyzer.pojoui.entity.mixin.creator.LabelCreator;
import br.unisinos.parthenos.generator.analyzer.pojoui.entity.mixin.creator.PositionCreator;
import br.unisinos.parthenos.generator.analyzer.pojoui.entity.mixin.creator.VisibleCreator;
import br.unisinos.parthenos.generator.prolog.fact.Fact;
import br.unisinos.parthenos.generator.prolog.fact.Vertex;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@AllArgsConstructor
public abstract class ElementAnalyzer implements FactAnalyzer, VertexCreator, LabelCreator, VisibleCreator, PositionCreator {
  private NodeWithAnnotations<?> node;

  public abstract String getElementName();
  public abstract Class<? extends Annotation> getAnnotationClass();

  private AnnotationExpr getElementAnnotation() {
    return this.getNode().getAnnotationByClass(this.getAnnotationClass()).orElse(null);
  }

  private Expression getAnnotationExpressionByName(NormalAnnotationExpr annotationExpression, String name) {
    return annotationExpression
      .getPairs()
      .stream()
      .filter((pair) -> Objects.equals(pair.getName().getIdentifier(), name))
      .map(MemberValuePair::getValue)
      .findFirst()
      .orElse(null);
  }

  private Expression getAnnotationExpression(String name) {
    final AnnotationExpr annotationExpression = this.getElementAnnotation();

    if (annotationExpression.isMarkerAnnotationExpr()) {
      return null;
    }

    if (annotationExpression.isSingleMemberAnnotationExpr()) {
      return ((SingleMemberAnnotationExpr) annotationExpression).getMemberValue();
    }

    return this.getAnnotationExpressionByName((NormalAnnotationExpr) annotationExpression, name);
  }

  public boolean hasAnnotation() {
    return this.getElementAnnotation() != null;
  }

  private int getUnaryExpressionInteger(UnaryExpr unaryExpr) {
    if (!unaryExpr.isPrefix() || !unaryExpr.getExpression().isIntegerLiteralExpr()) { return 0; }

    final UnaryExpr.Operator operator = unaryExpr.getOperator();
    final int value = ((IntegerLiteralExpr) unaryExpr.getExpression()).asInt();

    if (operator == UnaryExpr.Operator.MINUS) {
      return -value;
    }

    return value;
  }

  @Override
  public String getLabel() {
    final Expression labelExpression = this.getAnnotationExpression("label");

    if (labelExpression != null && labelExpression.isStringLiteralExpr()) {
      return ((StringLiteralExpr) labelExpression).asString();
    }

    return this.getElementName();
  }

  @Override
  public int getPosition() {
    final Expression positionExpression = this.getAnnotationExpression("position");

    if (positionExpression == null ||
      !(positionExpression.isIntegerLiteralExpr() || positionExpression.isUnaryExpr())) {
      return 0;
    }

    if (positionExpression.isUnaryExpr()) {
      return this.getUnaryExpressionInteger((UnaryExpr) positionExpression);
    }

    return ((IntegerLiteralExpr) positionExpression).asInt();
  }

  @Override
  public boolean isVisible() {
    final Expression visibleExpression = this.getAnnotationExpression("visible");

    if (visibleExpression != null && visibleExpression.isBooleanLiteralExpr()) {
      return ((BooleanLiteralExpr) visibleExpression).getValue();
    }

    return true;
  }

  @Override
  public Set<Fact> retrieveFacts() {
    final Set<Fact> elementFacts = new HashSet<>();
    final Vertex elementVertex = this.createVertex();

    elementFacts.add(elementVertex);
    elementFacts.add(this.createLabelEdge(elementVertex));
    elementFacts.add(this.createVisibleEdge(elementVertex));
    elementFacts.add(this.createPositionEdge(elementVertex));

    return elementFacts;
  }
}
