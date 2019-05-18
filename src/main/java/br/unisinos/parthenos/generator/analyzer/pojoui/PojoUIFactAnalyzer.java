package br.unisinos.parthenos.generator.analyzer.pojoui;

import br.unisinos.parthenos.generator.analyzer.FactAnalyzer;
import br.unisinos.parthenos.generator.analyzer.java.JavaFactAnalyzer;
import br.unisinos.parthenos.generator.analyzer.pojoui.entity.PojoUIClassAnalyzer;
import br.unisinos.parthenos.generator.annotation.Language;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.io.File;

@Language("pojo-ui")
public class PojoUIFactAnalyzer extends JavaFactAnalyzer {
  public PojoUIFactAnalyzer(File sourceFile) {
    super(sourceFile);
  }

  @Override
  protected FactAnalyzer getTypeAnalyzer(ClassOrInterfaceDeclaration typeDeclaration) {
    return new PojoUIClassAnalyzer(typeDeclaration);
  }
}
