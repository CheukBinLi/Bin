// 修改、查看、添加
Ext.define('am.user.view.QueryUserLayout', {
	extend : 'Ext.window.Window',
	alias : 'widget.queryuserlayout',
	title : '查看用户',
	closable : true,
	closeAction : 'hide',
	isModify : true,// 是否编辑
	// width : 300,
	// height : 300,
	items : [ {
		xtype : 'panel',
		frame : true,
		layout : {
			align : 'middle',
			pack : 'center',
			type : 'hbox'
		},
		items : [ {
			xtype : 'form',
			frame : true,
			defaults : {
				// padding : " 30 30 30 30"
				margin : '20 50 20 50',
				msgTarget : 'side',
				labelWidth : 60,
				readOnly : true,
				disabled : true
			},
			items : [ {
				xtype : 'textfield',
				fieldLabel : 'ID',
				name : 'id'
			}, {
				xtype : 'textfield',
				fieldLabel : '帐号',
				name : 'userName'
			}, {
				xtype : 'textfield',
				fieldLabel : '余额',
				name : 'balance'
			}, {
				xtype : 'textfield',
				fieldLabel : '短信接口',
				name : 'smsInterface'
			}, {
				xtype : 'textfield',
				fieldLabel : '权限',
				name : 'authority',
				renderer : function(value) {
					var store = Ext.StoreMgr.get('authoritystore');
					var dispaly = "";
					store.each(function(record) {
						if (record.data.id == value) {
							dispaly = record.data.name;
						}
					});
					return dispaly;
				}
			}, {
				xtype : 'textfield',
				fieldLabel : '备注',
				name : 'remark'
			} ],
			buttonAlign : 'center',
			buttons : [ /*
						 * { margin : '10 0 20 0', text : '保存', ref : 'save' },
						 */{
				// margin : '10 0 20 30',
				text : '返回',
				ref : 'esc',
				rootPanel : 'queryuserlayout'
			} ]
		} ]
	} ],
	listeners : {
		'show' : function(winform, eOpts) {
			// 短信接口
			var smsInterface = this.down('textfield[name=smsInterface]');
			var store = Ext.StoreMgr.get('smsinterfacestore');
			var dispaly = "";
			store.each(function(record) {
				if (record.data.id == smsInterface.getValue()) {
					dispaly = record.data.name;
				}
			});
			smsInterface.setValue(dispaly);
			// 权限
			var authority = this.down('textfield[name=authority]');
			var store1 = Ext.StoreMgr.get('authoritystore');
			var dispaly1 = "";
			store1.each(function(record) {
				if (record.data.id == authority.getValue()) {
					dispaly1 = record.data.name;
				}
			});
			authority.setValue(dispaly1);
		}
	},
	initComponent : function() {
		this.callParent(arguments);
	}
});
