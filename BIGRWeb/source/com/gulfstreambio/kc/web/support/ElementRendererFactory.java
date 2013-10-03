package com.gulfstreambio.kc.web.support;

/**
 * An interface that defines a factory method for obtaining a UI renderer for an element.
 */
public interface ElementRendererFactory {

  /**
   * Returns the element renderer for the element with the specified id.
   * 
   * @param elementId  The identifier of the element.  For a KC data element this is its CUI;
   * for a host element this is its host id. 
   * @param formContext  The {@link FormContext}, which contains the element to be rendered along
   * with other relevant KC context. 
   * @return  The <code>ElementRenderer</code>.
   */
  ElementRenderer getElementRenderer(String elementId, FormContext formContext);
}
