Ext.define('am.manager.view.ManagerTopLayout', {
			extend : 'Ext.panel.Panel',
			alias : 'widget.managertoplayout',
			layout : "absolute",
			margin : "0 0 0 0",
			padding : '0 0 0 0',
			frame : true,
			defaults : {
				// closable : true,
				closeAction : 'destroy',
				bodyBorder : false
			},
			items : [{
						x : Ext.getBody().getWidth() - 400,
						y : 60,
						// xtype : "displayfield",
						// id : "displaylogin0",
						// value : "<font color=red><b> 用户名：" + userName+
						// "</b></font>"
						xtype : 'label',
						text : '用户名：' /*+ userName*/
					}, {
						x : Ext.getBody().getWidth() - 300,
						y : 60,
						// xtype : "displayfield",
						// id : "displaylogin1",
						// value : "<font color=red><b>余额:</b></font>"
						xtype : 'label',
						text : '余额:'
					}, {
						border : 0,
						x : Ext.getBody().getWidth() - 150,
						y : 60,
						// width : 50,
						xtype : 'button',
						text : '刷新'
						// margin : '3 0 0 0',
					// id : "displaylogin2",
					// html : "<a href='#'
					// style='font-size:13px;font-weight:bold;background-color:
					// #DFE9F6;display: inline-block;display: block;color:
					// red;vertical-align: middle;'>刷新</a>"
				}	, {
						x : Ext.getBody().getWidth() - 100,
						y : 60,
						xtype : 'button',
						text : '退出系统',
						ref : 'exit'
						// xtype : "displayfield",
					// id : "displaylogin3",
					// value : "<a><font color=red><b>退出系统</b></font></a>",
				}]
		});
