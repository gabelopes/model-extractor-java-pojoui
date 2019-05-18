package br.unisinos.parthenos.generator.analyzer.pojoui;

import br.unisinos.parthenos.generator.analyzer.java.JavaFactAnalyzer;
import br.unisinos.parthenos.generator.annotation.Language;
import br.unisinos.parthenos.generator.enumerator.SourceLanguage;

import java.io.File;

@Language(SourceLanguage.JAVA)
public class PojoUIFactAnalyzer extends JavaFactAnalyzer {
  public PojoUIFactAnalyzer(File sourceFile) {
    super(sourceFile);
  }
}
