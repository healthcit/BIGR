package com.ardais.bigr.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ardais.bigr.api.ApiFunctions;
import com.ardais.bigr.security.SecurityInfo;
import com.gulfstreambio.kc.form.def.query.QueryFormDefinition;
import com.gulfstreambio.kc.web.support.FormContext;
import com.gulfstreambio.kc.web.support.FormContextStack;
import com.gulfstreambio.kc.web.support.KcUiContext;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * This class contains various Web-related static utility methods.
 */
public class WebUtils {

  // Since all methods are static, we'll never instantiate this class.
  private WebUtils() {
    super();
  }

  /**
   * Returns the user's security information.  The user's role and privileges 
   * are set into the session when the user logs in to BIGR, and are stored in
   * a <code>SecurityInfo</code> instance.  Here we retrieve it from the 
   * session and return it if we find one there.  Otherwise we create a new 
   * <code>SecurityInfo</code> with a null username and account, the "NONE" role
   * and no privileges, which is used to represent users that aren't logged in.
   * If there isn't currently an active HTTP session, this method doesn't 
   * create one.  In particular it doesn't store a <code>SecurityInfo</code> 
   * object on the session if there isn't one there already.  The login code is 
   * the only code that writes security information to the session.
   * 
   * @param  request  the HTTP request
   * @return  The <code>SecurityInfo</code> object containing the user's role and
   *                   privileges.
   */
  public static SecurityInfo getSecurityInfo(HttpServletRequest request) {
    return getSecurityInfo(request.getSession(false));
  }

  /**
   * Returns the user's security information.  The user's role and privileges 
   * are set into the session when the user logs in to BIGR, and are stored in
   * a <code>SecurityInfo</code> instance.  Here we retrieve it from the 
   * session and return it if we find one there.  Otherwise we create a new 
   * <code>SecurityInfo</code> with a null username and account, the "NONE" role
   * and no privileges, which is used to represent users that aren't logged in.
   * If there isn't currently an active HTTP session, this method doesn't 
   * create one.  In particular it doesn't store a <code>SecurityInfo</code> 
   * object on the session if there isn't one there already.  The login code is 
   * the only code that writes security information to the session.
   * 
   * @param  session  the HTTP session
   * @return  The <code>SecurityInfo</code> object containing the user's role and
   *                   privileges.
   */
  public static SecurityInfo getSecurityInfo(HttpSession session) {
    SecurityInfo securityInfo = null;

    if (session != null) {
      securityInfo = (SecurityInfo) session.getAttribute(SecurityInfo.SECURITY_KEY);
    }

    if (securityInfo == null) {
      securityInfo = new SecurityInfo();
    }

    return securityInfo;
  }

  public static void commonKcQuerySetup(HttpServletRequest request,
                                        String txType,
                                        QueryFormDefinition form) {
    commonKcQuerySetup(request, txType, form, null);
  }
  
  public static void commonKcQuerySetup(HttpServletRequest request, 
                                        String txType,
                                        QueryFormDefinition form,
                                        String dataElementId) {

    FormContextStack stack = FormContextStack.getFormContextStack(request);
    FormContext formContext = stack.peek();
    if (form != null) {
      formContext.setQueryFormDefinition(form);

      if (!ApiFunctions.isEmpty(dataElementId)) {
        formContext.setQueryFormDefinitionDataElement(form.getQueryDataElement(dataElementId));
      }
    }
    stack.push(formContext);

    KcUiContext kcUiContext = KcUiContext.getKcUiContext(request);
    String contextPath = request.getContextPath();
    kcUiContext.setAdePopupUrl(contextPath + "/library/kc/ade/popup.do?txType=" + txType);

    // Commented out per MR 8945.  If we supply this URL then the UI services will use AJAX
    // to get each page of the query form definition.  The problem is that if we use AJAX and the 
    // user does not visit a tab/page, then the values on that tab will not be submitted.
    // If we determine a means of safely using AJAX, then this comment should be removed and
    // the subsequent line of code should be uncommented.
//    kcUiContext.setPageLinkBaseUrl(
//        contextPath 
//        + "/library/kc/getQueryFormPage.do?" 
//        + RequestParameterConstants.FORM_DEFINITION_ID
//        + "="
//        + form.getFormDefinitionId()
//        + "&txType="
//        + txType);
  }

	public static String convertToGetParams(Map parameterMap, List<String> exclude)
	{
		String result = "";
		if (parameterMap != null)
		{
			for (Object key : parameterMap.keySet())
			{
				String stringKey = key.toString();
				if (!exclude.contains(stringKey))
				{
					stringKey = escapeCodes(stringKey);
					final String[] param = (String[])parameterMap.get(key);
					if (param != null && param.length > 0)
					{
						for (String p : param)
						{
							if (StringUtils.isNotBlank(result))
							{
								result += "&";
							}
							final String stringValue = escapeCodes(p.replace("\"", "'"));
							result += stringKey;
							if (param.length > 1)
							{
								result += "[]";
							}
							result += "=" + stringValue;
						}
					}
				}
			}
		}
		return result;
	}

	public static String escapeCodes(String value)
	{
		if (value == null) return null;
		return value
			.replace("&", "%26")
			.replace("[", "%5B")
			.replace("]", "%5D")
			.replace("|", "%7C")
			.replace("?", "%3F")
			.replace("#", "%23")
			.replace("=", "%3D");
	}
}
