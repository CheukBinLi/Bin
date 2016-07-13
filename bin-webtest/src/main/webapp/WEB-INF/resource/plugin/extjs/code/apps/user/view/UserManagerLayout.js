// 用户管理
Ext.define('am.user.view.UserManagerLayout', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.usermanagerlayout',
	forceFit : true,// 强制列自适应成可用宽度
	frame : true,
	layout : 'fit',
	closable : true,
	closeAction : 'destroy',
	selType : 'checkboxmodel',
	multiSelect : true,
	deleteUrl : 'usermanager/deleteUser.html',
	modifyingUrl : 'usermanager/modifyingUser.html',
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		items : [ {
			xtype : 'button',
			ref : 'createlayout',// 组件xtype
			rtype : 'createuserlayout',// 组件容器事件
			rootPanel : 'usermanagerlayout',// 组件父容器
			text : '添加',
			margin : '0 0 0 5'
		}, {
			xtype : 'button',
			text : '刪除',
			ref : 'delete',
			margin : '0 0 0 5'
		}, {
			xtype : 'button',
			text : '保存',
			ref : 'saveAll',
			rootPanel : 'usermanagerlayout',
			margin : '0 0 0 5'
		}, {
			xtype : 'triggerfield',
			fieldLabel : '查找 ',
			labelAlign : 'right',
			labelWidth : 50,
			emptyText : '输入用户名',
			triggerCls : 'x-form-trigger',
			// data:[{id:'1',name:'小小a'}],
			onTriggerClick : function() {
				var grid = this.up('usermanagerlayout');
				// gird的store
				var store = grid.getStore();
				// 清空过滤器
				store.clearFilter(false);
				// 正则
				var cc;
				if (query.getValue().length > 0) {
					cc = eval('/' + query.getValue() + '/');
				} else {
					cc = query.getValue();
				}
				// 字段过滤
				store.filter('userName', cc);
				// Ext.Msg.alert('Status', this.getValue());
			},
			listeners : {
				'change' : function(query, newValue, oldValue, eOpts) {
					var grid = query.up('usermanagerlayout');
					var store = grid.getStore();
					// 清空过滤器
					store.clearFilter(false);
					var cc;
					if (query.getValue().length > 0) {
						cc = eval('/' + query.getValue() + '/');
					} else {
						cc = query.getValue();
					}
					store.filter('userName', cc);
				}
			}
		} ]
	} ],
	columns : [ {
		text : '行',
		xtype : 'rownumberer'
	}, {
		text : '编号',
		dataIndex : 'id',
		hidden : true
	}, {
		text : '帐号名',
		dataIndex : 'userName'
	}, {
		text : '权限',
		dataIndex : 'authority',
		field : {
			xtype : 'combobox',
			editable : false,
			store : 'am.user.store.AuthorityStore',
			displayField : 'name',
			valueField : 'id'
		},
		renderer : function(value) {
			var store = Ext.StoreMgr.get('authoritystore');
			var dispaly = "";
			store.each(function(record) {
				if (record.data.id == value) {
					dispaly = record.data.name;
				}
			});
			return "<font color='green'>" + dispaly + "</font>";
		}
	}, {
		text : '余额',
		dataIndex : 'balance',
		field : {
			xtype : 'numberfield',
			minValue : 0,
			allowDecimals : false,// 禁用小数
			listeners : {
				'focus' : function(tf, e, eOpts) {
					tf.selectText();
				}
			}
		}
	}, {
		text : '短信接口',
		dataIndex : 'smsInterface',
		field : {
			xtype : 'combobox',
			editable : false,// 不能编辑
			// forceSelection : true,
			lazyRender : true,
			store : 'am.user.store.SmsInterfaceStore',
			displayField : 'name',
			valueField : 'id'
		},
		renderer : function(value) {
			var store = Ext.StoreMgr.get('smsinterfacestore');
			var dispaly = "";
			store.each(function(record) {
				if (record.data.id == value) {
					dispaly = record.data.name;
				}
			});
			return "<font color='green'>" + dispaly + "</font>";
		}
	}, {
		text : '备注',
		dataIndex : 'remark',
		field : {
			xtype : 'textfield',
			listeners : {
				'focus' : function(tf, e, eOpts) {
					tf.selectText();
				}
			}
		}

	}, {
		text : '操作',
		xtype : 'actioncolumn',
		items : [ {
			tooltip : 'xxx'
		} ]
	} ],
	store : 'am.user.store.UserManagerStore',
	plugins : [ Ext.create('Ext.grid.plugin.CellEditing', {
		clicksToEdit : 2
	}) ],
	listeners : {},
	initComponent : function() {
		this.callParent(arguments);
	}
});
