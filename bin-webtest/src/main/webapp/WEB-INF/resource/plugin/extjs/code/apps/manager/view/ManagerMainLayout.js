Ext.define('am.manager.view.ManagerMainLayout', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.managermainlayout',
			layout : 'border',
			defaults : {
				bodyBorder : false,
				margin : "5 5 5 5",
				padding : '0 0 0 0'
			},
			frame : true,
			items : [{
						height : 100,
						region : 'north', // position for region
						xtype : 'managertoplayout',
						split : true
					}, {
						title : '短信菜单',
						region : 'west', // position for region
						width : 200,
						xtype : 'managerwestlayout',
						split : true
					}, {
						region : 'center', // position for region
						xtype : 'managercenterlayout',
						split : true
					}]
		})