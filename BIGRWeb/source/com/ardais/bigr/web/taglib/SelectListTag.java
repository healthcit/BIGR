package com.ardais.bigr.web.taglib;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.ardais.bigr.api.Escaper;
import com.ardais.bigr.iltds.assistants.LegalValueSet;
import com.ardais.bigr.iltds.assistants.SelectList;

/**
 * Implements the BIGR <code>selectList</code> tag.
 */
public class SelectListTag
	extends BaseHandlerTag
	implements ChildPropertyParent {
	List _childProperties = new ArrayList();
	String _firstValue;
	String _firstDisplayValue;
  Set _excludedValues;
	LegalValueSet _legalValueSet;
	String _legalValueSetName;
	String _legalValueSetProperty;
	String _name;
	String _parentName;
	String _parentProperty;
	String _property;
	String _value;
  String[] _multiValues;
  String _multiple;
  String _size;
  // if _includeAlphaLookup is set to "true", a text box, a search and a refresh button will be
  // created to search for a text in the lsit.
  private String _includeAlphaLookup;
	/**
	 * Creates a new <code>SelectListTag</code>.
	 */
	public SelectListTag() {
		super();
	}
	/**
	 */
	public void addChildProperty(String property) {
		setChildProperty(property);
	}
	/**
	 * Writes the SELECT element to the HTTP response.
	 */
	public int doEndTag() throws JspException {

		// Get the legal value set.  This is required, so throw a
		// JspException if we can't determine the legal value set.
		if (_legalValueSet == null) {
			if (_legalValueSetProperty == null)
				throw new JspException("Error in tag selectList: one of attribute legalValueSet or legalValueSetProperty not specified");
			else if (_legalValueSetName != null)
				_legalValueSet =
					(LegalValueSet) RequestUtils.lookup(
						pageContext,
						_legalValueSetName,
						_legalValueSetProperty,
						null);
			else if (_name != null)
				_legalValueSet =
					(LegalValueSet) RequestUtils.lookup(
						pageContext,
						_name,
						_legalValueSetProperty,
						null);
			else
				throw new JspException("Error in tag selectList: one of attribute legalValueSet or legalValueSetName/legalValueSetProperty or name/legalValueSetProperty not specified");
		}
		
		

		// Get the value(s) of the item(s) that should be selected in the legal value set, if any.
		// This is optional.
		if ((_value == null) && (_multiValues == null) && (_name != null)) {
			Object value =
				RequestUtils.lookup(pageContext, _name, _property, null);
			if (value != null) {
        //if the value is a String, assign it to _value.  If the value is a String array,
        //assign it to _multiValues.
        if (value instanceof String) {
  				_value = value.toString();
        }
        if (value instanceof String[]) {
          _multiValues = (String[])value;
        }
      }
		}

		// Get the SELECT that is the parent of this SELECT, if any.  This is optional.
		Object parentValue = null;
		if (_parentProperty != null) {
			if (_parentName != null)
				parentValue =
					RequestUtils.lookup(
						pageContext,
						_parentName,
						_parentProperty,
						null);
			else if (_name != null)
				parentValue =
					RequestUtils.lookup(
						pageContext,
						_name,
						_parentProperty,
						null);
			else
				throw new JspException("Error in tag selectList: attribute parentProperty specified without parentName or name");
		}

		// If there is a dependent, then create the call to the event handler for the
		// onchange event of this parent SELECT.
		String onchange = null;
		if (_childProperties.size() > 0) {
			StringBuffer onchanges = new StringBuffer(64);
			for (int i = 0; i < _childProperties.size(); i++) {
				onchanges.append(
					"populateChildList('"
						+ (String) _childProperties.get(i)
						+ "', '"
						+ _property
						+ "', this.value);");
			}
			onchange = onchanges.toString();
		}

		// If there is no parent, then simply write the SELECT element.
		if (_parentProperty == null) {
			SelectList selectList = new SelectList(_property);
			//set the style and the styleClass
			
			selectList.setLegalValueSet(_legalValueSet);
			if (_value != null) {
				selectList.setSelectedValue(_value);
      }
      if (_multiValues != null) {
        selectList.setSelectedValues(_multiValues);
      }
			if ((_firstValue != null) || (_firstDisplayValue != null)) {
				String value = (_firstValue == null) ? "" : _firstValue;
				selectList.setFirstValue(value);
				value =
					(_firstDisplayValue == null)
						? _firstValue
						: _firstDisplayValue;
				selectList.setFirstDisplayValue(value);
			}
      if (_excludedValues != null) {
        selectList.setExcludedValues(_excludedValues);        
      }
      if (_multiple != null) {
        selectList.setMultiple(_multiple);
      }
      if (_size != null) {
        selectList.setSize(_size);
      }      
			if (onchange != null) {
				if (getOnchange() != null)
					setOnchange(onchange + getOnchange());
				else
					setOnchange(onchange);
			} else if (getOnchange() != null)
				setOnchange(getOnchange());
			selectList.setStyles(prepareStyles());
			selectList.setEvents(prepareEventHandlers());
			ResponseUtils.write(pageContext, selectList.generate());
      
      // if it supports text search, then create appropiate controls
      if ("true".equals(getIncludeAlphaLookup())) {
        SearchTextTag searchTag = new SearchTextTag(); 
        searchTag.setName("search" + _property);
        searchTag.setSearchButton("Search");
        searchTag.setRefreshButton("Refresh");
        searchTag.setRefresh_yn("Y");
        searchTag.setLength("20");
        searchTag.setSearchedField(_property);
        ResponseUtils.write(pageContext, searchTag.getHtml());
      }      
      
		}

		// If there is a parent, write an array with all of the possible
		// SELECT elements, one per value of the parent, and show the appropriate
		// one based on the parent's value.
		else {
      // if it supports text search, then create appropiate controls
      if ("true".equals(getIncludeAlphaLookup())) {
        ResponseUtils.write(pageContext, "<div id=\"");
        ResponseUtils.write(pageContext, _property);
        ResponseUtils.write(pageContext, "SearchRefreshDiv\">");
        SearchTextTag searchTag = new SearchTextTag(); 
        searchTag.setName("search" + _property);
        searchTag.setSearchButton("Search");
        searchTag.setRefreshButton("Refresh");
        searchTag.setRefresh_yn("Y");
        searchTag.setLength("20");
        searchTag.setSearchedField(_property);
        ResponseUtils.write(pageContext, searchTag.getHtml());
        ResponseUtils.write(pageContext, "</div>");        
      }
      
      ResponseUtils.write(pageContext, "<div id=\"");
      ResponseUtils.write(pageContext, _property);
      ResponseUtils.write(pageContext, "Div\"></div>");
			ResponseUtils.write(
				pageContext,
				"<script language=\"JavaScript\">");
			ResponseUtils.write(pageContext, "var ");
			ResponseUtils.write(pageContext, _property);
			ResponseUtils.write(pageContext, "LegalValues = new Array();");
			int numParentValues = _legalValueSet.getCount();
			for (int i = 0; i < numParentValues; i++) {
				ResponseUtils.write(pageContext, _property);
				ResponseUtils.write(pageContext, "LegalValues[\"");
				ResponseUtils.write(pageContext, _parentProperty);
				ResponseUtils.write(pageContext, ".");
				ResponseUtils.write(pageContext, _legalValueSet.getValue(i));
				ResponseUtils.write(pageContext, "\"] = \"");
				SelectList selectList = new SelectList(_property);
				selectList.setLegalValueSet(
					_legalValueSet.getSubLegalValueSet(i));
				if (onchange != null) {
					if (getOnchange() != null)
						setOnchange(onchange + getOnchange());
					else
						setOnchange(onchange);
				} else if (getOnchange() != null) {
					setOnchange(getOnchange());
				}
				if ((_firstValue != null) || (_firstDisplayValue != null)) {
					String value = (_firstValue == null) ? "" : _firstValue;
					selectList.setFirstValue(value);
					value =
						(_firstDisplayValue == null)
							? _firstValue
							: _firstDisplayValue;
					selectList.setFirstDisplayValue(value);
				}
        if (_excludedValues != null) {
          selectList.setExcludedValues(_excludedValues);        
        }
				selectList.setStyles(prepareStyles());
				selectList.setEvents(prepareEventHandlers());
			
				ResponseUtils.write(
					pageContext,
					Escaper.jsEscapeInScriptTag(selectList.generate()));
				ResponseUtils.write(pageContext, "\";");
			}

			if ((parentValue != null)
				&& (parentValue.toString().length() > 0)) {
				ResponseUtils.write(pageContext, "document.all.");
				ResponseUtils.write(pageContext, _property);
				ResponseUtils.write(pageContext, "Div.innerHTML = ");
				ResponseUtils.write(pageContext, _property);
				ResponseUtils.write(pageContext, "LegalValues['");
				ResponseUtils.write(pageContext, _parentProperty);
				ResponseUtils.write(pageContext, ".");
				ResponseUtils.write(pageContext, parentValue.toString());
				ResponseUtils.write(pageContext, "'];");
				if (_value != null) {
					ResponseUtils.write(
						pageContext,
						"for (var i = 0; i < document.all.");
					ResponseUtils.write(pageContext, _property);
					ResponseUtils.write(pageContext, ".length; i++) {");
					ResponseUtils.write(pageContext, "if (document.all.");
					ResponseUtils.write(pageContext, _property);
					ResponseUtils.write(pageContext, ".options[i].value == '");
					ResponseUtils.write(pageContext, _value);
					ResponseUtils.write(pageContext, "') { document.all.");
					ResponseUtils.write(pageContext, _property);
					ResponseUtils.write(
						pageContext,
						".options[i].selected = true; break; }}");
				}
			}
			ResponseUtils.write(pageContext, "</script>");
		}

		return EVAL_PAGE;
	}
	/**
	 * Release all allocated resources.
	 */
	public void release() {
		super.release();
		_legalValueSet = null;
		_legalValueSetName = null;
		_legalValueSetProperty = null;
    _excludedValues = null;
    _name = null;
		_property = null;
		_value = null;
    _includeAlphaLookup = null;
	}
	/**
	 */
	public void setChildProperty(String property) {
		_childProperties.add(property);
	}
	/**
	 */
	public void setFirstDisplayValue(String value) {
		_firstDisplayValue = value;
	}
	/**
	 */
	public void setFirstValue(String value) {
		if (value != null)
			_firstValue = value.trim();
	}
	/**
	 * Sets the <code>LegalValueSet</code>.
	 *
	 * @param  lvs  the <code>LegalValueSet</code>
	 */
	public void setLegalValueSet(LegalValueSet lvs) {
		_legalValueSet = lvs;
	}
	/**
	 * Sets the name of the bean that returns the the <code>LegalValueSet</code>
	 * via the property specified by the legalValueSetProperty attribute.
	 *
	 * @param  name   the bean name
	 */
	public void setLegalValueSetName(String name) {
		_legalValueSetName = name;
	}
	/**
	 * Sets the property of the bean specified by the name or legalValueSetName attribute
	 * that returns the <code>LegalValueSet</code>.
	 *
	 * @param  property  the bean property
	 */
	public void setLegalValueSetProperty(String property) {
		_legalValueSetProperty = property;
	}
	/**
	 * Sets the name of the bean that contains the value of the item in the 
	 * list that should be selected, and also, optionally, the <code>LegalValueSet</code>
	 * to be displayed as a SELECT element.
	 *
	 * @param  name  the bean name
	 */
	public void setName(String name) {
		_name = name;
	}
	public void setParentName(String name) {
		_parentName = name;
	}
	/**
	 */
	public void setParentProperty(String property) {
		_parentProperty = property;
	}
	/**
	 * Sets the name of the select list.  This is the value assigned to the
	 * SELECT element's name attribute, which is therefore also the name of
	 * the request parameter.  In addition, if the name attribute of this
	 * tag is also specified, then the property attribute is used to look 
	 * up the value that should be selected in the SELECT element, from the 
	 * bean specified by name.
	 *
	 * @param  property  the SELECT list name and bean property for determining the selected value 
	 */
	public void setProperty(String property) {
		_property = property;
	}
	/**
	 * Sets the value of the item in the list that should be selected.
	 *
	 * @param  value  the selected value
	 */
	public void setValue(String value) {
		_value = value;
	}

  /**
   * @param string
   */
  public void setMultiple(String string) {
    _multiple = string;
  }

  /**
   * @param string
   */
  public void setSize(String string) {
    _size = string;
  }

  /**
   * @param string
   */
  public void setIncludeAlphaLookup(String string) {
    _includeAlphaLookup = string;
  }

  /**
   * @return
   */
  public String getIncludeAlphaLookup() {
    return _includeAlphaLookup;
  }

  public void setExcludedValues(Set excludedValues) {
    _excludedValues = excludedValues;
  }
}
