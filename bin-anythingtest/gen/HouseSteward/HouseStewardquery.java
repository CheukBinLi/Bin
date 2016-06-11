<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE QueryList SYSTEM "Query.dtd">
<QueryList package="project.freehelp.common.entity.HouseSteward">
	<Alias name="project.freehelp.common.entity.HouseSteward" Alias="HouseSteward" />
	<Query name="list" type="HQL" freemarkFormat="true" Alias="true">
		<![CDATA[ 
			FROM HouseSteward A 
			WHERE 
			1=1
			<#if id??>
				<#if like??>
					and A.id like '%:id%'
				<#else>
					and A.id=:id
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
		]]>
	</Query>
</QueryList>