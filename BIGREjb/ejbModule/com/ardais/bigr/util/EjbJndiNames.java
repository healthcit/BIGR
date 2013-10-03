/*
 * Created on Nov 23, 2009
 *
 */
package com.ardais.bigr.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import com.ardais.bigr.api.ApiProperties;
import com.ardais.bigr.api.ApiResources;

/**
 * @author SValin
 *
 */
public class EjbJndiNames {

  private static EjbJndiNames instance;
  private HashMap bindings = null;
  private static final String DEFAULT_BINDINGS_CAPACITY = "128";
  
  private EjbJndiNames() throws NamingException, ClassNotFoundException {
    String[] packages = retrieveEjbPackages();
    this.bindings = retrieveEjbJndiBindings(packages);
  }
  
  public static synchronized EjbJndiNames getInstance() throws NamingException, ClassNotFoundException {
    if (EjbJndiNames.instance == null) {
      EjbJndiNames.instance = new EjbJndiNames();
    }
    return EjbJndiNames.instance;
  }
  
  private String[] retrieveEjbPackages() {
    LinkedList tokenList = new LinkedList();
    String packages = ApiProperties.getProperty(ApiResources.API_JNDI_EJB_PACKAGES);
    String delimiters = ApiProperties.getProperty(ApiResources.API_JNDI_EJB_PACKAGE_DELIMITERS);
    StringTokenizer st = new StringTokenizer(packages, delimiters);
    while(st.hasMoreTokens()) {
      String token = st.nextToken();
      tokenList.add(token.trim());
    }
    String[] tokens = new String[tokenList.size()];
    tokens = (String[]) tokenList.toArray(tokens);
    return tokens;
  }
  
  private HashMap retrieveEjbJndiBindings(String[] packages) throws NamingException, ClassNotFoundException {
    String bindingsCapacity = ApiProperties.getProperty(ApiResources.API_JNDI_EJB_BINDINGS_CAPACITY, DEFAULT_BINDINGS_CAPACITY);
    HashMap bindings = null;
    try {
      int capacity = Integer.parseInt(bindingsCapacity);
      bindings = new HashMap(capacity);
    }
    catch(NumberFormatException ex) {
      bindings = new HashMap();
    }
    Context ctx = new InitialContext();
    String ctxPath = "";
    ArrayList visitedContexts = new ArrayList();
    findBindings(packages, ctx, visitedContexts, ctxPath, bindings, true);
    return bindings;
  }

  private void findBindings(String[] packages, Context ctx, ArrayList visitedContexts, String ctxPath, HashMap bindings, boolean root) throws NamingException, ClassNotFoundException {
    Context currentContext = (Context) ctx.lookup(ctxPath);
    String nameInNamespace = currentContext.getNameInNamespace();
    if (visitedContexts.contains(nameInNamespace)) {
      return;
    } else {
      visitedContexts.add(nameInNamespace);
    }
    NamingEnumeration ne = ctx.list(ctxPath);
    LinkedList subcontexts = new LinkedList();
    while(ne.hasMore()) {
      NameClassPair ncp = (NameClassPair) ne.next();
      String name = ncp.getName();
      String className = ncp.getClassName();
      if ("javax.naming.Context".equals(className)) {
        subcontexts.add(name);
      } else {
        for (int i=0; i<packages.length; i++) {
          String currPackage = packages[i];
          if (className.startsWith(currPackage)) {
            String jndiName = ctxPath + "/" + name;
            Class theClass = Class.forName(className);
            bindings.put(theClass, jndiName);
            break;
          }
        }
      }
    }
    Iterator iter = subcontexts.iterator();
    while(iter.hasNext()) {
      String subCtx = (String) iter.next();
      String nextCtxPath = ctxPath + ((root ? "" : "/") + subCtx);
      findBindings(packages, ctx, visitedContexts, nextCtxPath, bindings, false);
    }
  }
  
  public String get(Class key) {
    String jndiName = (String) this.bindings.get(key);
    return jndiName;
  }
}
