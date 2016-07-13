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
	// multiSelect : false,
	// private int id;
	// private String userName;
	// private String password;
	// private int authority;// 权限
	// private int balance;// 余额
	// private int smsInterface;// 短信接口
	// private String remark;// 备注
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		items : [ {
			xtype : 'button',
			ref : 'createuserlayout',// 组件xtype
			con : 'usermanagerlayoutToptoolbar',// 组件容器事件
			rootPanel : 'usermanagerlayout',// 组件父容器
			text : '添加',
			margin : '0 0 0 5'
		}, {
			xtype : 'button',
			ref : 'modifyuserlayout',
			con : 'usermanagerlayoutToptoolbar',
			rootPanel : 'usermanagerlayout',
			text : '修改',
			margin : '0 0 0 5'
		}, {
			xtype : 'button',
			ref : 'queryuserlayout',
			con : 'usermanagerlayoutToptoolbar',
			rootPanel : 'usermanagerlayout',
			text : '查看',
			margin : '0 0 0 5'
		}, {
			xtype : 'button',
			text : '刪除',
			margin : '0 0 0 5'
		}, {
			xtype : 'triggerfield',
			fieldLabel : '查找 ',
			labelAlign : 'right',
			labelWidth : 50,
			emptyText : '输入用户名',
			triggerCls : 'x-form-trigger',
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
				change : function(query, newValue, oldValue, eOpts) {
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
	} /*
		 * , { xtype : 'pagingtoolbar', dock : 'bottom', store :
		 * 'am.user.store.UserManagerStore', displayInfo : true, pageSize:10 }
		 */],
	columns : [ {
		text : '行',
		xtype : 'rownumberer'
	}, {
		text : '编号',
		dataIndex : 'id'
	}, {
		text : '帐号名',
		dataIndex : 'userName'
	}, {
		text : '权限',
		dataIndex : 'authority',
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
		dataIndex : 'balance'
	}, {
		text : '短信接口',
		dataIndex : 'smsInterface',
		renderer : function(value) {
			var store = Ext.StoreMgr.get('smsinterfacestore');
			var dispaly = "";
			store.each(function(record) {
				if (record.data.id == value) {
					dispaly = record.data.name;
				}
			});
			return "<font color='green'>" + dispaly + "</font>";
			// if (value) {
			// return "<font color='green'>on</font>";
			// }
			// return "<font color='red'>off</font>";
		}
	}, {
		text : '备注',
		dataIndex : 'remark'
	}, {
		text : '操作',
		xtype : 'actioncolumn',
		items : [ {
			tooltip : 'xxx'
		} ]
	} ],
	store : 'am.user.store.UserManagerStore',
	plugins:[
			 Ext.create('Ext.grid.plugin.CellEditing', {clicksToEdit: 2})
			],	
	listeners : {},
	initComponent : function() {
		this.callParent(arguments);
	}
});
