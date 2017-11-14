### Description

<#list model.parameters as param>
<#if param.names?has_content>
* **<#list param.names as name>${name}<#sep>, </#sep></#list>**<#if param.valueName?has_content> *=* *${param.valueName}*</#if>

${"  " }<#if param.description?has_content>${param.description}</#if>

${"  " }<#if param.required>*Required*</#if>
<#else>
* **${param.valueName!"argument"}**

${"  " }<#if param.description?has_content>${param.description}</#if>

${"  " }<#if param.required>*Required*</#if>
</#if>
</#list>
