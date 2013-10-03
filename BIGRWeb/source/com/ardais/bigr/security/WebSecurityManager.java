package com.ardais.bigr.security;

import javax.servlet.http.HttpSession;

import com.ardais.bigr.util.WebUtils;

public class WebSecurityManager {

    /**
     * This class can't be instantiated so its constructor is private.
     */
    private WebSecurityManager() {
    }

       /**
     * Returns an indication if the logged in user has the specified privilege.
     * If there is no user currently logged in, the <code>false</code> is returned.
     *
     * @param  privilege  the privilege (OBJECT_ID in the database)
     * @return <code>true</code> if the logged in user/account has
     *			the specified privilege; <code>false</code> otherWebUtilise.
     */
    public static boolean hasPrivilege(HttpSession session, String privilege) {
        SecurityInfo securityInfo = WebUtils.getSecurityInfo(session);
        
        return securityInfo.isHasPrivilege(privilege);
    }
}
