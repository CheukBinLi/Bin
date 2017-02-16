Ext.define('am.authority.view.AuthorityMainLayout', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.authorityMainLayout',
	layout : 'border',
	defaults : {
		bodyBorder : false,
		margin : "5 5 5 -2",
		padding : '0 0 0 0'
	},
	frame : true,
	items :
		[ {
			title : '短信菜单',
			region : 'west', // position for region
			width : 200,
			xtype : 'authorityLeftLayout',
			split : true
		}, {
			region : 'center', // position for region
			xtype : 'authoritycenterlayout',
			split : true
		} ]
})