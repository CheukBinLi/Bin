// 权限管理
Ext.define('am.user.view.AuthorityManagerLayout', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.authoritymanagerlayout',
	forceFit : true,// 强制列自适应成可用宽度
	frame : true,
	layout : 'fit',
	closable : true,
	closeAction : 'destroy',
	selType : 'checkboxmodel',
	multiSelect : true,
	deleteUrl : 'usermanager/deleteAuthority.html',
	modifyingUrl : 'usermanager/modifyingAuthority.html',
	dockedItems : [{
				xtype : 'toolbar',
				dock : 'top',
				items : [{
							xtype : 'button',
							ref : 'createlayout',// 组件xtype
							rtype : 'createauthoritylayout',// 组件容器事件
							rootPanel : 'authoritymanagerlayout',// 组件父容器
							text : '添加',
							margin : '0 0 0 5'
						}, {
							xtype : 'button',
							text : '刪除',
							ref : 'delete',
							rootPanel : 'authoritymanagerlayout',
							margin : '0 0 0 5'
						}, {
							xtype : 'button',
							text : '保存',
							ref : 'saveAll',
							rootPanel : 'authoritymanagerlayout',
							margin : '0 0 0 5'
						}, {
							xtype : 'triggerfield',
							fieldLabel : '查找 ',
							labelAlign : 'right',
							labelWidth : 50,
							emptyText : '输入权限名称',
							triggerCls : 'x-form-trigger',
							onTriggerClick : function() {
								var grid = this.up('authoritymanagerlayout');
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
											.up('authoritymanagerlayout');
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
			} /*
				 * , { xtype : 'pagingtoolbar', dock : 'bottom', store :
				 * 'am.user.store.UserManagerStore', displayInfo : true,
				 * pageSize:10 }
				 */],
	// private int id;
	// private String name;//age:超级管理员
	// private String controllMap;// 备用功能数组
	// private String remark;
	columns : [{
				text : '行',
				xtype : 'rownumberer'
			}, {
				text : '编号',
				dataIndex : 'id',
				hidden : true
			}, {
				text : '权限名',
				dataIndex : 'name',
				field : {
					xtype : 'textfield',
					allowBlank : false,
					listeners : {
						'focus' : function(tf, e, eOpts) {
							tf.selectText();
						}
					}
				}
			}, {
				text : '备用功能数组',
				dataIndex : 'controllMap',
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
	store : 'am.user.store.AuthorityStore',
	plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
				clicksToEdit : 2
			})],
	listeners : {},
	initComponent : function() {
		this.callParent(arguments);
//		this.getStore().load();
	}
});
