// 用户板块控制器
Ext.define("am.user.controller.UserController", {
	extend : 'Ext.app.Controller',
	init : function() {
		this.control({
			'button[ref=createlayout]' : {
				click : function(button, e, eOpts) {
					// 父容器
					var rootPanel = button.up(button.rootPanel);
					// 容器里面找窗口
					var cc = rootPanel.down(button.ref);
					if (null == cc || 'undefined' == cc) {
						cc = Ext.createWidget(button.rtype);
						rootPanel.add(cc);
					} else if (!cc.isHidden()) {
						return;
					}
					var x = cc.down('form');
					x.loadRecord([ {} ]);
					// cc.items.items[0].items.items[0].disabled = true;
					cc.show();
				}
			},
//			'button[ref=esc]' : {
//				click : function(button, e, eOpts) {
//					button.up(button.rootPanel).close();
//				}
//			},
			// 用户
			'usermanagerlayout button[ref=saveAll]' : {
				click : function(button, e, eOpts) {
					var grid = button.up(button.rootPanel);
					modifyingDataByGrid(grid, grid.modifyingUrl, function(falgs) {
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
					var createlayout = button.up('createuserlayout');
					var form = button.up('form').getForm();
					createDataByForm(form, createlayout.addUrl, function(falgs) {
						if (falgs) {
							var grid = button.up('usermanagerlayout');
							var store = grid.getStore();
							store.insert(0, form.getValues());
							grid.update();
							form.reset();
							createlayout.close();
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
					if (grid.getSelectionModel().getCount() == 0) {
						Ext.Msg.alert('提示', '最少选择一个项');
						return;
					}
					if (grid.getSelectionModel().getCount() == 0) {
						Ext.Msg.alert('提示', '最少选择一个项');
						return;
					}
					Ext.Msg.show({
						title : 'Save Changes?',
						msg : '确认删除数据（' + grid.getSelectionModel().getCount() + '）条',
						buttons : Ext.Msg.YESNO,
						icon : Ext.Msg.QUESTION,
						fn : function(btn) {
							if (btn == 'yes') {
								var array = grid.getSelectionModel().getSelection();
								var data = [];
								Ext.Array.each(array, function(record) {
									// data.push((eval('{id:'+record.data.id+'}')));
									data.push(record.data);
								});
								simpleAjax(data, grid.deleteUrl, function(falgs) {
									if (falgs) {
										var store = grid.getStore();
										store.remove(array);
										grid.update();
										Ext.Msg.alert('提示', '删除成功');
									} else {
										Ext.Msg.alert('提示', '删除失败');
									}
								});
							}
						}
					});

				}
			},
			// 权限
			'authoritymanagerlayout button[ref=saveAll]' : {
				click : function(button, e, eOpts) {
					var grid = button.up(button.rootPanel);
					modifyingDataByGrid(grid, grid.modifyingUrl, function(falgs) {
						if (falgs) {
							Ext.Msg.alert('提示', '保存成功');
						} else {
							Ext.Msg.alert('提示', '保存失败');
						}
						return;
					});
				}
			}, // 添加权限(window)
			'createauthoritylayout button[ref=save]' : {
				click : function(button, e, eOpts) {
					var createlayout = button.up('createauthoritylayout');
					var form = button.up('form').getForm();
					createDataByForm(form, createlayout.addUrl, function(falgs) {
						if (falgs) {
							var grid = button.up('authoritymanagerlayout');
							var store = grid.getStore();
							store.insert(0, form.getValues());
							grid.update();
							form.reset();
							createlayout.close();
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
					if (grid.getSelectionModel().getCount() == 0) {
						Ext.Msg.alert('提示', '最少选择一个项');
						return;
					}
					Ext.Msg.show({
						title : 'Save Changes?',
						msg : '确认删除数据（' + grid.getSelectionModel().getCount() + '）条',
						buttons : Ext.Msg.YESNO,
						icon : Ext.Msg.QUESTION,
						fn : function(btn) {
							if (btn == 'yes') {
								var array = grid.getSelectionModel().getSelection();
								var data = [];
								Ext.Array.each(array, function(record) {
									// data.push((eval('{id:'+record.data.id+'}')));
									data.push(record.data);
								});
								simpleAjax(data, grid.deleteUrl, function(falgs) {
									if (falgs) {
										var store = grid.getStore();
										store.remove(array);
										grid.update();
										Ext.Msg.alert('提示', '删除成功');
									} else {
										Ext.Msg.alert('提示', '删除失败');
									}
								});
							}
						}
					});

				}
			},
			// 短信接口
			'smsinterfacemanagerlayout button[ref=saveAll]' : {
				click : function(button, e, eOpts) {
					var grid = button.up(button.rootPanel);
					modifyingDataByGrid(grid, grid.modifyingUrl, function(falgs) {
						if (falgs) {
							Ext.Msg.alert('提示', '保存成功');
						} else {
							Ext.Msg.alert('提示', '保存失败');
						}
						return;
					});
				}
			}, // 添加短信接口信息(window)
			'createsmsInterfacelayout button[ref=save]' : {
				click : function(button, e, eOpts) {
					var createlayout = button.up('createsmsInterfacelayout');
					var form = button.up('form').getForm();
					createDataByForm(form, createlayout.addUrl, function(falgs) {
						if (falgs) {
							var grid = button.up('smsinterfacemanagerlayout');
							var store = grid.getStore();
							store.insert(0, form.getValues());
							grid.update();
							form.reset();
							createlayout.close();
							Ext.Msg.alert('提示', '添加成功');
						} else {
							Ext.Msg.alert('提示', '添加失败');
						}
					});
				}
			},
			// 删除
			'smsinterfacemanagerlayout button[ref=delete]' : {
				click : function(button, e, eOpts) {
					var grid = button.up('smsinterfacemanagerlayout');
					if (grid.getSelectionModel().getCount() == 0) {
						Ext.Msg.alert('提示', '最少选择一个项');
						return;
					}
					Ext.Msg.show({
						title : 'Save Changes?',
						msg : '确认删除数据（' + grid.getSelectionModel().getCount() + '）条',
						buttons : Ext.Msg.YESNO,
						icon : Ext.Msg.QUESTION,
						fn : function(btn) {
							if (btn == 'yes') {
								var array = grid.getSelectionModel().getSelection();
								var data = [];
								Ext.Array.each(array, function(record) {
									// data.push((eval('{id:'+record.data.id+'}')));
									data.push(record.data);
								});
								simpleAjax(data, grid.deleteUrl, function(falgs) {
									if (falgs) {
										var store = grid.getStore();
										store.remove(array);
										grid.update();
										Ext.Msg.alert('提示', '删除成功');
									} else {
										Ext.Msg.alert('提示', '删除失败');
									}
								});
							}
						}
					});

				}
			}
		});
	},
	views : [ 'am.user.view.UserManagerLayout', 'am.user.view.RecharegInformationLayout', 'am.user.view.ModifyUserLayout', 'am.user.view.CreateUserLayout', 'am.user.view.QueryUserLayout',
			'am.user.view.AuthorityManagerLayout', 'am.user.view.SmsInterfaceManagerLayout', 'am.user.view.CreateAuthorityLayout', 'am.user.view.CreateSmsInterfaceLayout' ],
	stores : [ 'am.user.store.UserManagerStore', 'am.user.store.SmsInterfaceStore', 'am.user.store.AuthorityStore' ]
});