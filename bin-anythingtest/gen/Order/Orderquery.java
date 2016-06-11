<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE QueryList SYSTEM "Query.dtd">
<QueryList package="project.freehelp.common.entity.Order">
	<Alias name="project.freehelp.common.entity.Order" Alias="Order" />
	<Query name="list" type="HQL" freemarkFormat="true" Alias="true">
		<![CDATA[ 
			FROM Order A 
			WHERE 
			1=1
			<#if id??>
				<#if like??>
					and A.id like '%:id%'
				<#else>
					and A.id=:id
				</#if>
			</#if>
			<#if total??>
				<#if like??>
					and A.total like '%:total%'
				<#else>
					and A.total=:total
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
			<#if master??>
				<#if like??>
					and A.master like '%:master%'
				<#else>
					and A.master=:master
				</#if>
			</#if>
			<#if house??>
				<#if like??>
					and A.house like '%:house%'
				<#else>
					and A.house=:house
				</#if>
			</#if>
			<#if steward??>
				<#if like??>
					and A.steward like '%:steward%'
				<#else>
					and A.steward=:steward
				</#if>
			</#if>
			<#if service??>
				<#if like??>
					and A.service like '%:service%'
				<#else>
					and A.service=:service
				</#if>
			</#if>
		]]>
	</Query>
</QueryList>