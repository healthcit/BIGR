<?xml version="1.0" encoding="UTF-8"?>
<schema xmlns="http://purl.oclc.org/dsdl/schematron">
	<pattern>
		<rule
			context="//dataElementInt|//dataElementFloat|//dataElementDate|//dataElementVpd|//dataElementReport|//dataElementText|//dataElementCv">
			<assert test="count(ancestor::category[@databaseType])=1">The <name/> must have exactly one
				category ancestor that has the databaseType attribute defined.</assert>
		</rule>
	</pattern>
</schema>
