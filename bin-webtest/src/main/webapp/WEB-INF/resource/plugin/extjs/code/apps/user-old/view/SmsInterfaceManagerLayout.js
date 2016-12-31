// 权限管理
Ext.define('am.user.view.SmsInterfaceManagerLayout', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.smsinterfacemanagerlayout',
	forceFit : true,// 强制列自适应成可用宽度
	frame : true,
	layout : 'fit',
	closable : true,
	closeAction : 'destroy',
	selType : 'checkboxmodel',
	multiSelect : true,
	deleteUrl:'smsmanager/deleteSmsInterface.html',
	modifyingUrl:'smsmanager/modifyingSmsInterface.html',
	dockedItems : [{
				xtype : 'toolbar',
				dock : 'top',
				items : [{
							xtype : 'button',
							ref : 'createlayout',// 组件xtype
							rtype:'createsmsInterfacelayout',// 组件容器事件
							rootPanel : 'smsinterfacemanagerlayout',// 组件父容器
							text : '添加',
							margin : '0 0 0 5'
						}, {
							xtype : 'button',
							text : '刪除',
							rootPanel : 'smsinterfacemanagerlayout',
							margin : '0 0 0 5'
						}, {
							xtype : 'button',
							text : '保存',
							ref : 'saveAll',
							rootPanel : 'smsinterfacemanagerlayout',
							margin : '0 0 0 5'
						}, {
							xtype : 'triggerfield',
							fieldLabel : '查找 ',
							labelAlign : 'right',
							labelWidth : 50,
							emptyText : '输入接口名称',
							triggerCls : 'x-form-trigger',
							onTriggerClick : function() {
								var grid = this.up('smsinterfacemanagerlayout');
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
								store.filter('name', cc);
								// Ext.Msg.alert('Status', this.getValue());
							},
							listeners : {
								'change' : function(query, newValue, oldValue,
										eOpts) {
									var grid = query
											.up('smsinterfacemanagerlayout');
									var store = grid.getStore();
									// 清空过滤器
									store.clearFilter(false);
									var cc;
									if (query.getValue().length > 0) {
										cc = eval('/' + query.getValue() + '/');
									} else {
										cc = query.getValue();
									}
									store.filter('name', cc);
								}
							}
						}]
			}],
	columns : [{
				text : '行',
				xtype : 'rownumberer'
			}, {
				text : '编号',
				dataIndex : 'id'
			}, {
				text : '接口名',
				dataIndex : 'name',
				field : {
					xtype : 'textfield',
					listeners : {
						'focus' : function(tf, e, eOpts) {
							tf.selectText();
						}
					}
				}
			}, {
				text : '接口完整名称(Class类名字)',
				dataIndex : 'className',
				field : {
					xtype : 'textfield',
					listeners : {
						'focus' : function(tf, e, eOpts) {
							tf.selectText();
						}
					}
				}
			}, {
				text : '接口帐号',
				dataIndex : 'userName',
				field : {
					xtype : 'textfield',
					listeners : {
						'focus' : function(tf, e, eOpts) {
							tf.selectText();
						}
					}
				}
			}, {
				text : '接口密码',
				dataIndex : 'password',
				renderer : function(value, p, r) {
					return '**********'
				},
				field : {
					xtype : 'textfield',
					inputType : 'password',
					listeners : {
						'focus' : function(tf, e, eOpts) {
							tf.selectText();
						}
					}
				}
			}, {
				text : '接口平台后台地址',
				dataIndex : 'url',
				field : {
					xtype : 'textfield',
					listeners : {
						'focus' : function(tf, e, eOpts) {
							tf.selectText();
						}
					}
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
				items : [{
							tooltip : 'xxx'
						}]
			}],
	store : 'am.user.store.SmsInterfaceStore',
	plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
				clicksToEdit : 2
			})],
	listeners : {},
	initComponent : function() {
		this.callParent(arguments);
//		this.getStore().load();
	}
});
