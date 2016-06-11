<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE QueryList SYSTEM "Query.dtd">
<QueryList package="project.freehelp.common.entity.Dictionary">
	<Alias name="project.freehelp.common.entity.Dictionary" Alias="Dictionary" />
	<Query name="list" type="HQL" freemarkFormat="true" Alias="true">
		<![CDATA[ 
			FROM Dictionary A 
			WHERE 
			1=1
			<#if value??>
				<#if like??>
					and A.value like '%:value%'
				<#else>
					and A.value=:value
				</#if>
			</#if>
			<#if key??>
				<#if like??>
					and A.key like '%:key%'
				<#else>
					and A.key=:key
				</#if>
			</#if>
			<#if parent??>
				<#if like??>
					and A.parent like '%:parent%'
				<#else>
					and A.parent=:parent
				</#if>
			</#if>
			<#if attachment2??>
				<#if like??>
					and A.attachment2 like '%:attachment2%'
				<#else>
					and A.attachment2=:attachment2
				</#if>
			</#if>
			<#if status??>
				<#if like??>
					and A.status like '%:status%'
				<#else>
					and A.status=:status
				</#if>
			</#if>
			<#if remark??>
				<#if like??>
					and A.remark like '%:remark%'
				<#else>
					and A.remark=:remark
				</#if>
			</#if>
			<#if attachment??>
				<#if like??>
					and A.attachment like '%:attachment%'
				<#else>
					and A.attachment=:attachment
				</#if>
			</#if>
		]]>
	</Query>
</QueryList>