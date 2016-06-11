<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE QueryList SYSTEM "Query.dtd">
<QueryList package="project.freehelp.common.entity.HouseInfo">
	<Alias name="project.freehelp.common.entity.HouseInfo" Alias="HouseInfo" />
	<Query name="list" type="HQL" freemarkFormat="true" Alias="true">
		<![CDATA[ 
			FROM HouseInfo A 
			WHERE 
			1=1
			<#if address??>
				<#if like??>
					and A.address like '%:address%'
				<#else>
					and A.address=:address
				</#if>
			</#if>
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
			<#if remark??>
				<#if like??>
					and A.remark like '%:remark%'
				<#else>
					and A.remark=:remark
				</#if>
			</#if>
			<#if bed??>
				<#if like??>
					and A.bed like '%:bed%'
				<#else>
					and A.bed=:bed
				</#if>
			</#if>
			<#if image??>
				<#if like??>
					and A.image like '%:image%'
				<#else>
					and A.image=:image
				</#if>
			</#if>
			<#if title??>
				<#if like??>
					and A.title like '%:title%'
				<#else>
					and A.title=:title
				</#if>
			</#if>
			<#if latitude??>
				<#if like??>
					and A.latitude like '%:latitude%'
				<#else>
					and A.latitude=:latitude
				</#if>
			</#if>
			<#if count??>
				<#if like??>
					and A.count like '%:count%'
				<#else>
					and A.count=:count
				</#if>
			</#if>
			<#if facility??>
				<#if like??>
					and A.facility like '%:facility%'
				<#else>
					and A.facility=:facility
				</#if>
			</#if>
			<#if price??>
				<#if like??>
					and A.price like '%:price%'
				<#else>
					and A.price=:price
				</#if>
			</#if>
			<#if pledge??>
				<#if like??>
					and A.pledge like '%:pledge%'
				<#else>
					and A.pledge=:pledge
				</#if>
			</#if>
			<#if area??>
				<#if like??>
					and A.area like '%:area%'
				<#else>
					and A.area=:area
				</#if>
			</#if>
			<#if longitude??>
				<#if like??>
					and A.longitude like '%:longitude%'
				<#else>
					and A.longitude=:longitude
				</#if>
			</#if>
			<#if master??>
				<#if like??>
					and A.master like '%:master%'
				<#else>
					and A.master=:master
				</#if>
			</#if>
			<#if traffic??>
				<#if like??>
					and A.traffic like '%:traffic%'
				<#else>
					and A.traffic=:traffic
				</#if>
			</#if>
		]]>
	</Query>
</QueryList>