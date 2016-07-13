Ext.define('am.manager.view.ManagerCenterLayout', {
			extend : 'Ext.tab.Panel',
			alias : 'widget.managercenterlayout',
			layout : 'fit',
			margin : "0 0 0 0",
			padding : '0 0 0 0',
			frame : true,
			defaults : {
				closable : true,
				closeAction : 'destroy',
				bodyBorder : false
			},
			items : [{
						title : '首页通知',
						closable : false
					}]
		})