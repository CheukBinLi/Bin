// 用户板块控制器
Ext.define("am.user.controller.UserController", {
	extend : 'Ext.app.Controller',
	init : function() {
		this.control({
			'usermanagerlayout button[con=usermanagerlayoutToptoolbar]' : {
				click : function(button, e, eOpts) {
					// createuserlayout
					var grid = button.up('usermanagerlayout');
					var store = grid.getSelectionModel().getSelection();
					if (store.length == 0 && button.ref != 'createlayout') {
						Ext.Msg.alert('提示', '请最点选需要操作的行！');
						return;
					}
					// 父容器
					var rootPanel = button.up(button.rootPanel);
					// 容器里面找窗口
					var cc = rootPanel.down(button.ref);
					if (null == cc || 'undefined' == cc) {
						cc = Ext.createWidget(button.ref);
						rootPanel.add(cc);
					} else if (!cc.isHidden()) {
						return;
					}
					// if (button.ref != 'createuserlayout') {
					if (button.ref == 'createlayout') {
						var x = cc.down('form');
						// x.loadRecord(store[0]);
						x.loadRecord([ {} ]);
					}
					// cc.items.items[0].items.items[0].disabled = true;
					cc.show();
				}
			},
			'button[ref=esc]' : {
				click : function(button, e, eOpts) {
					button.up(button.rootPanel).close();
				}
			},
			// 用户
			'usermanagerlayout button[ref=saveAll]' : {
				click : function(button, e, eOpts) {
					var grid = button.up(button.rootPanel);
					modifyingDataByGrid(grid, 'usermanager/modifyingUser.html', function(falgs) {
						if (falgs) {
							Ext.Msg.alert('提示', '保存成功');
						} else {
							Ext.Msg.alert('提示', '保存失败');
						}
						return;
					});
				}
			},
			// 添加用户(window)
			'createuserlayout button[ref=save]' : {
				click : function(button, e, eOpts) {
					var createuserlayout = button.up('createuserlayout');
					var form = button.up('form').getForm();
					createDataByForm(form, 'usermanager/addUser.html', function(falgs) {
						if (falgs) {
							var grid = button.up('usermanagerlayout');
							var store = grid.getStore();
							store.insert(0, form.getValues());
							grid.update();
							form.reset();
							createuserlayout.close();
							Ext.Msg.alert('提示', '添加成功');
						} else {
							Ext.Msg.alert('提示', '添加失败');
						}
					});
				}
			},
			// 删除
			'usermanagerlayout button[ref=delete]' : {
				click : function(button, e, eOpts) {
					var grid = button.up('usermanagerlayout');
					var array = grid.getSelectionModel().getSelection();
					var data = [];
					Ext.Array.each(array, function(record) {
						// data.push((eval('{id:'+record.data.id+'}')));
						data.push(record.data);
					});
					simpleAjax(data, 'usermanager/deleteUser.html', function(falgs) {
						if (falgs) {
							var grid = button.up('usermanagerlayout');
							var store = grid.getStore();
							store.remove(array);
							grid.update();
							Ext.Msg.alert('提示', '添加成功');
						} else {
							Ext.Msg.alert('提示', '添加失败');
						}
					});

				}
			},
			// 权限
			'authoritymanagerlayout button[ref=saveAll]' : {
				click : function(button, e, eOpts) {
					var grid = button.up(button.rootPanel);
					modifyingDataByGrid(grid, 'usermanager/modifyingAuthority.html', function(falgs) {
						if (falgs) {
							Ext.Msg.alert('提示', '保存成功');
						} else {
							Ext.Msg.alert('提示', '保存失败');
						}
						return;
					});
				}
			}, // 添加用户(window)
			'authoritymanagerlayout button[ref=save]' : {
				click : function(button, e, eOpts) {
					var createuserlayout = button.up('authoritymanagerlayout');
					var form = button.up('form').getForm();
					createDataByForm(form, 'usermanager/addAuthority.html', function(falgs) {
						if (falgs) {
							var grid = button.up('authoritymanagerlayout');
							var store = grid.getStore();
							store.insert(0, form.getValues());
							grid.update();
							form.reset();
							createuserlayout.close();
							Ext.Msg.alert('提示', '添加成功');
						} else {
							Ext.Msg.alert('提示', '添加失败');
						}
					});
				}
			},
			// 删除
			'authoritymanagerlayout button[ref=delete]' : {
				click : function(button, e, eOpts) {
					var grid = button.up('authoritymanagerlayout');
					var array = grid.getSelectionModel().getSelection();
					var data = [];
					Ext.Array.each(array, function(record) {
						// data.push((eval('{id:'+record.data.id+'}')));
						data.push(record.data);
					});
					simpleAjax(data, 'usermanager/deleteAuthority.html', function(falgs) {
						if (falgs) {
							var grid = button.up('authoritymanagerlayout');
							var store = grid.getStore();
							store.remove(array);
							grid.update();
							Ext.Msg.alert('提示', '添加成功');
						} else {
							Ext.Msg.alert('提示', '添加失败');
						}
					});

				}
			},
			// 短信接口
			'smsinterfacemanagerlayoutbutton[ref=saveAll]' : {
				click : function(button, e, eOpts) {
					var grid = button.up(button.rootPanel);
					if (modifyingDataByGrid(grid, 'usermanager/modifyingSmsInterface.html')) {
					}
				}
			}
		});
	},
	views : [ 'am.user.view.UserManagerLayout', 'am.user.view.RecharegInformationLayout', 'am.user.view.ModifyUserLayout', 'am.user.view.CreateUserLayout', 'am.user.view.QueryUserLayout',
			'am.user.view.AuthorityManagerLayout', 'am.user.view.SmsInterfaceManagerLayout' ],
	stores : [ 'am.user.store.UserManagerStore', 'am.user.store.SmsInterfaceStore', 'am.user.store.AuthorityStore' ]
});