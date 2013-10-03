package com.gulfstreambio.kc.web.support;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

/**
 * A stack that holds {@link FormContext} instances, which are used to provide context for
 * user interface renderers such as JSP tags and JSP fragments.  The stack is created lazily
 * when the static {@link #getFormContextStack(getFormContextStack) getFormContextStack} method
 * is called, and stored in a request attribute so that it is available for the duration of a
 * request.  Callers that change <code>FormContext</code> instances perform typical push and pop 
 * operations to manipulate the stack, and callers that rely on <code>FormContext</code> instances
 * to provide context for rendering their user interface pieces perform a peek operation to get
 * the <code>FormContext</code> instance on the top of the stack.
 */
public class FormContextStack {

  public static final String FORM_CONTEXT_STACK = "com.gulfstreambio.formContextStack";
  
  private List _stack;
  
  private FormContextStack() {
    super();
    _stack = new ArrayList();
  }

  /**
   * Returns the form context stack, creating it if necessary.  The stack is held in
   * the request, and is thus available for the duration of a request.
   * 
   * @param request  the servlet request
   * @return  The <code>FormContextStack</code>.
   */
  public static FormContextStack getFormContextStack(ServletRequest request) {
    FormContextStack stack = 
      (FormContextStack) request.getAttribute(FormContextStack.FORM_CONTEXT_STACK);
    if (stack == null) {
      stack = new FormContextStack();
      request.setAttribute(FormContextStack.FORM_CONTEXT_STACK, stack);
    }
    return stack;
  }

  /**
   * Returns the form context on the top of the stack.  If the stack is empty, creates a new
   * form context and pushes it on the stack.  This is the only way to create a new form context.
   * 
   * @return  The <code>FormContext</code>.
   */
  public FormContext peek() {
    if (_stack.isEmpty()) {
      push(new FormContext());
    }
    return (FormContext) _stack.get(_stack.size() - 1);
  }

  /**
   * Pushes the specified form context on the stack.  The actual implementation is to create a
   * copy of the specified context and push that on the stack. 
   * 
   * @param   the <code>FormContext</code>
   * @return  The copy of the <code>FormContext</code> that was pushed.
   */
  public FormContext push(FormContext context) {
    FormContext newContext = context.createCopy();
    _stack.add(newContext);
    return newContext;
  }

  /**
   * Removes and returns the form context on the top of the stack.  
   *  
   * @return  The <code>FormContext</code>, or null if the stack is empty.
   */
  public FormContext pop() {
    return (!_stack.isEmpty()) ? (FormContext) _stack.remove(_stack.size() - 1) : null;
  }

}
