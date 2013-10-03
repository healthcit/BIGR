package com.gulfstreambio.kc.web.support;

public class KcElementRendererFactory implements ElementRendererFactory {

  private ElementRenderer _renderer = new KcElementRenderer();

  public KcElementRendererFactory() {
    super();
  }

  public ElementRenderer getElementRenderer(String elementId, FormContext formContext) {
    return _renderer;
  }

}
