package br.unisinos.parthenos.generator.enumerator;

import br.unisinos.parthenos.generator.pool.SourceLanguage;

public enum PojoUISourceLanguage implements SourceLanguage {
  POJO_UI(".java");

  private String[] extensions;

  PojoUISourceLanguage(String... extensions) {
    this.extensions = extensions;
  }

  @Override
  public String getName() {
    return this.name().replace("_", "-").toLowerCase();
  }

  @Override
  public String[] getExtensions() {
    return extensions;
  }
}
