// 修改、查看、添加
Ext.define('am.user.view.ModifyUserLayout', {
	extend : 'Ext.window.Window',
	alias : 'widget.modifyuserlayout',
	title : '修改用户资料',
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
				labelWidth : 60
			},
			items : [ {
				xtype : 'textfield',
				fieldLabel : 'ID',
				name : 'id',
				readOnly : true,
				disabled : true
			}, {
				xtype : 'textfield',
				fieldLabel : '帐号',
				name : 'userName',
				readOnly : true,
				disabled : true
			}, {
				xtype : 'numberfield',
				minValue : 0,
				allowDecimals : false,// 禁用小数
				fieldLabel : '余额',
				name : 'balance'
			}, {
				xtype : 'combobox',
				fieldLabel : '短信接口',
				editable : false,
				name : 'smsInterface',
				displayField : 'name',
				valueField : 'id',
				store : 'am.user.store.SmsInterfaceStore'
			}, {
				xtype : 'combobox',
				fieldLabel : '权限',
				name : 'authority',
				editable : false,
				displayField : 'name',
				valueField : 'id',
				store : 'am.user.store.AuthorityStore'
			}, {
				xtype : 'textfield',
				fieldLabel : '备注',
				name : 'remark'
			} ],
			buttonAlign : 'center',
			buttons : [ {
				margin : '10 0 20 0',
				text : '保存',
				ref : 'save'
			}, {
				margin : '10 0 20 30',
				text : '返回',
				ref : 'esc',
				rootPanel : 'modifyuserlayout'
			} ]
		} ]
	} ],
	listeners : {
		'show' : function(winform, eOpts) {
			// 短信接口
			var smsInterface = this.down('combobox[name=smsInterface]');
			var store = Ext.StoreMgr.get('smsinterfacestore');
			var dispaly = "";
			store.each(function(record) {
				if (record.data.id == smsInterface.getValue()) {
					dispaly = record.data.name;
				}
			});
			smsInterface.select(dispaly);
			// 权限
			var authority = this.down('combobox[name=authority]');
			var store1 = Ext.StoreMgr.get('authoritystore');
			var dispaly1 = "";
			store1.each(function(record) {
				if (record.data.id == authority.getValue()) {
					dispaly1 = record.data.name;
				}
			});
			authority.select(dispaly1);
		}
	},
	initComponent : function() {
		this.callParent(arguments);
	}
});
