package com.ardais.bigr.util;

import javax.ejb.EJBHome;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

public class EjbHomes {

  public static EJBHome getHome(Class homeClass) throws NamingException, ClassNotFoundException {
    EJBHome home = null;

    EjbJndiNames ejn = EjbJndiNames.getInstance();
    String jndiName = ejn.get(homeClass);

    EJBHomeFactory factory = EJBHomeFactory.getInstance();
    home = factory.lookup(jndiName, homeClass);
    
    return home;
  }
}
