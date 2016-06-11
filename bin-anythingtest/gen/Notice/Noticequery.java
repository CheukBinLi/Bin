<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE QueryList SYSTEM "Query.dtd">
<QueryList package="project.freehelp.common.entity.Notice">
	<Alias name="project.freehelp.common.entity.Notice" Alias="Notice" />
	<Query name="list" type="HQL" freemarkFormat="true" Alias="true">
		<![CDATA[ 
			FROM Notice A 
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
			<#if senderID??>
				<#if like??>
					and A.senderID like '%:senderID%'
				<#else>
					and A.senderID=:senderID
				</#if>
			</#if>
			<#if sender??>
				<#if like??>
					and A.sender like '%:sender%'
				<#else>
					and A.sender=:sender
				</#if>
			</#if>
			<#if receiverID??>
				<#if like??>
					and A.receiverID like '%:receiverID%'
				<#else>
					and A.receiverID=:receiverID
				</#if>
			</#if>
			<#if msg??>
				<#if like??>
					and A.msg like '%:msg%'
				<#else>
					and A.msg=:msg
				</#if>
			</#if>
			<#if receiver??>
				<#if like??>
					and A.receiver like '%:receiver%'
				<#else>
					and A.receiver=:receiver
				</#if>
			</#if>
		]]>
	</Query>
</QueryList>