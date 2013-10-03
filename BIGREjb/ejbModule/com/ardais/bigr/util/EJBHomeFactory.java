package com.ardais.bigr.util;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJBHome;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

public class EJBHomeFactory {

  private static EJBHomeFactory instance;
  private Map homeInterfaces;
  private Context context;

  private EJBHomeFactory() throws NamingException {
    homeInterfaces = new HashMap();
    context = new InitialContext();
  }

  public static EJBHomeFactory getInstance() throws NamingException {
    if (instance == null) {
      instance = new EJBHomeFactory();
    }
    return instance;
  }

  public EJBHome lookup(String jndiName, Class homeInterfaceClass) throws NamingException {
    EJBHome homeInterface = (EJBHome) homeInterfaces.get(homeInterfaceClass);
    if (homeInterface == null) {
      Object obj = context.lookup(jndiName);
      homeInterface = (EJBHome) PortableRemoteObject.narrow(obj, homeInterfaceClass);
      homeInterfaces.put(homeInterfaceClass, homeInterface);
    }
    return homeInterface;
  }
}
