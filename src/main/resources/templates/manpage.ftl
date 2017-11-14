'\" t
.\" (The preceding line is a note to broken versions of man to tell
.\" them to pre-process this man page with tbl)
.\" Man page for pmap.
.\" Licensed under version 2 of the GNU General Public License.
.\" Written by Albert Cahalan.
.\"
.TH $PROGRAMNAME "1" "$MONTH $YEAR"
.SH NAME
$PROGRAMNAME \- $PROGRAMDESCRIPTION
.SH SYNOPSIS
.B $PROGRAMNAME
[\fIoptions\fR] <#if model.nameless.present>
                \fI${model.nameless.get.valueName!argument}\fR
</#if>

.SH DESCRIPTION
$DESCRIPTION

.SH OPTIONS
.TP
<#if model.nameless.present>
\fB${model.nameless.get.valueName!argument}\fR
${model.nameless.get.description}
</#if>

<#list model.namefull as param>
.TP
<#list param.names as name>\fB\${name}\fR<#sep>, </#sep></#list> <#if param.valueName?has_content>\fI${param.valueName}\fR</#if>
${param.description}
</#list>
