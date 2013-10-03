package com.ardais.bigr.web.kc;

import com.gulfstreambio.kc.web.support.ElementRenderer;
import com.gulfstreambio.kc.web.support.ElementRendererFactory;
import com.gulfstreambio.kc.web.support.FormContext;

public class BigrElementRendererFactory implements ElementRendererFactory {

  private ElementRenderer _renderer = new BigrElementRenderer();
  
  public BigrElementRendererFactory() {
    super();
  }

  public ElementRenderer getElementRenderer(String elementId, FormContext formContext) {
    return _renderer;
  }

}
