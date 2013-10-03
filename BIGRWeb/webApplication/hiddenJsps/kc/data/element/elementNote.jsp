<%@ page import="com.ardais.bigr.api.ApiFunctions" %>
<%@ page import="com.ardais.bigr.api.Escaper" %>
<%@ page import="com.ardais.bigr.util.UniqueIdGenerator" %>
<%@ page import="com.gulfstreambio.kc.det.DataElementTaxonomy" %>
<%@ page import="com.gulfstreambio.kc.web.support.DataFormElementContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContext" %>
<%@ page import="com.gulfstreambio.kc.web.support.FormContextStack" %>
<%@ page import="com.gulfstreambio.kc.web.support.KcUiContext" %>
<%
FormContextStack stack = FormContextStack.getFormContextStack(request);
FormContext formContext = stack.peek();
DataFormElementContext elementContext = formContext.getDataFormElementContext();

UniqueIdGenerator idgen = KcUiContext.getKcUiContext(request).getUniqueIdGenerator();
String name = idgen.getUniqueId();
String id = idgen.getUniqueId();
String containerId = idgen.getUniqueId();
elementContext.setHtmlIdNote(id);
elementContext.setHtmlIdNoteContainer(containerId);

// Get the value of the note.
String noteValue = elementContext.getValueNote();
noteValue = (noteValue == null) ? "" : noteValue;

// Determine if the note textarea should be displayed.  If the note has a value
// or the value of the data element is "See Note" then it should be displayed.
// The latter situation can arise (the value is "See Note" but there is no note)
// if the user selected "See Note" but then did not supply a note (an error condition), 
// in which case we want to show the textarea to allow a note to be entered.
boolean displayNote = (!ApiFunctions.isEmpty(noteValue));
if (!displayNote) {
  if (DataElementTaxonomy.SYSTEM_STANDARD_VALUE_SEE_NOTE.equals(elementContext.getValue())) {
    displayNote = true;
  }
}
%>
<div id="<%=containerId%>" class="kcElementNote" <%=displayNote ? "" : "style=\"display: none\""%>>
	Note: <br>
	<textarea id="<%=id%>" name="<%=name%>" cols="90" rows="5"><%=Escaper.htmlEscape(noteValue)%></textarea>
</div>
  