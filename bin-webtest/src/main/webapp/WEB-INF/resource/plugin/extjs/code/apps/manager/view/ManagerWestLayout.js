Ext.define('am.manager.view.ManagerWestLayout', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.managerwestlayout',
	layout : 'accordion',
	margin : "5 5 5 5",
	frame : true,
	items :
		[ {
			title : '信息管理',
			xtype : 'treepanel',
			rootVisible : false,
			root : {
				expanded : true,
				children :
					[ {
						text : "用户管理",
						id : 'usermanagerlayout',
						leaf : true
					}, {
						text : "用户组信息",
						id : 'authorityMainLayout',
						leaf : true
					}, {
						text : "系统权限信息",
						id : 'smsinterfacemanagerlayout',
						leaf : true
					}, {
						text : "用户组权限管理",
						id : 'rechareginformationlayout',
						leaf : true
					} ]
			}
		}, {
			title : '短信管理',
			xtype : 'treepanel',
			rootVisible : false,
			root : {
				expanded : true,
				children :
					[ {
						text : "发送记录",
						id : 'smsmainlayout',
						leaf : true
					} ]
			}
		} ]
})