<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE QueryList SYSTEM "Query.dtd">
<QueryList package="project.master.user.User">
	<Alias name="project.master.user.User" Alias="User" />
	<Query name="list" type="HQL" freemarkFormat="true" Alias="true">
		<![CDATA[ 
			FROM User A 
			WHERE 
			1=1
			<#if id??>
				<#if like??>
					and A.id like '%:id%'
				<#else>
					and A.id=:id
				</#if>
			</#if>
			<#if status??>
				<#if like??>
					and A.status like '%:status%'
				<#else>
					and A.status=:status
				</#if>
			</#if>
			<#if phone??>
				<#if like??>
					and A.phone like '%:phone%'
				<#else>
					and A.phone=:phone
				</#if>
			</#if>
			<#if password??>
				<#if like??>
					and A.password like '%:password%'
				<#else>
					and A.password=:password
				</#if>
			</#if>
			<#if lastLogin??>
				<#if like??>
					and A.lastLogin like '%:lastLogin%'
				<#else>
					and A.lastLogin=:lastLogin
				</#if>
			</#if>
		]]>
	</Query>
</QueryList>