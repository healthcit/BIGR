var bigrBaseFunctions =
{
	doAjaxRequest : function(url, params, callback)
	{
		new Ajax.Request(
			url,
			{
				onSuccess: function(transport)
				{
					var result = transport.responseText;
					setTimeout(function () { callback(result, transport); }, 1);
				},
				method: 'get',
				parameters: params,
				evalScripts: true
			});
	},

	updateOptionsFromJson: function(selectId, json)
	{
		var select = $(selectId);
		select.options.length = 0;
		for (var i = 0; i < json.options.length; i++)
		{
			var option = json.options[i];
			var element = new Option(option.label, option.value);
			if (option.selected)
			{
				element.selected = true;
			}
			select.options[select.length] = element;
		}
	},

	setSelectedOption: function(selectId, value)
	{
		var options = $(selectId).options;
		for (var i = 0; i < options.length; i++)
		{
			if (options[i].value == value)
			{
				options[i].selected = true;
			}
		}
	},

	getParametersFromForm: function(form, obj)
	{
		var elements = form.elements;
		for(var i = 0; i < elements.length; i++)
		{
			var elem = elements[i];

			if (elem.type == 'select-multiple')
			{
				obj[elem.name + '[]'] = this.getSelectedValues(elem);
			}
			else if (elem.type == 'radio')
			{
				if (elem.checked)
				{
					obj[elem.name] = elem.value;
				}
			}
			else if (elem.type == 'checkbox')
			{
				if (elem.checked)
				{
					if (!(obj[elem.name + '[]'] instanceof Array))
					{
						obj[elem.name + '[]'] = new Array();
					}
					obj[elem.name + '[]'].push(elem.value);

					if (obj[elem.name] != null)
					{
						delete obj[elem.name];
					}
				}
			}
			else if (elem.type != 'button' && elem.type != 'submit')
			{
				if (elem.value != '')
				{
					obj[elem.name] = elem.value;
				}
			}
		}
	},

	getSelectedValues: function(elem)
	{
		var array = new Array();
		for (var x = 0; x < elem.length; x++)
		{
			if (elem[x].selected)
			{
				array.push(elem[x].value);
			}
		}
		return array;
	},

	createHiddenForm: function(method, action)
	{
		var form = document.createElement("form");
		form.setAttribute("method", method);
		form.setAttribute("action", action);
		form.style.display = 'none';
		return form;
	},

	createHiddenFormInput: function(name, value)
	{
		var hiddenField = document.createElement("input");
		hiddenField.setAttribute("type", "hidden");
		hiddenField.setAttribute("name", name);
		hiddenField.setAttribute("value", value);
		return hiddenField;
	}
};