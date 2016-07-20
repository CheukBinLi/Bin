Ext.define("am.manager.controller.ManagerController", {
			extend : 'Ext.app.Controller',
			init : function() {
				var self = this;
				// 添加到tab
				var createTab = function(info) {
					// info[xtypeName,xtypeTitle,rootViewXtype]
					if (info) {
						var viewXtype = info.rootViewXtype.down(info.xtypeName);
						// 空即创建
						if (!viewXtype) {
							viewXtype=Ext.widget(info.xtypeName);
							//viewXtype = Ext.createWidget(info.xtypeName);
							viewXtype.setTitle(info.xtypeTitle);
							info.rootViewXtype.add(viewXtype);
						}
						// 激活窗体
						info.rootViewXtype.setActiveTab(viewXtype);
					}
				}
				this.control({ // 点击树菜单
					'treepanel' : {
						itemclick : function(treeitem, record, item, index, e,
								eOpts) {
							var rootViewXtype = treeitem
									.up('managermainlayout')
									.down('managercenterlayout');
							// alert(record.data['id']);
							var info = {
								'xtypeName' : record.data['id'],
								'xtypeTitle' : record.data['text'],
								'rootViewXtype' : rootViewXtype
							};
							createTab(info);
						}
					},
					'managertoplayout button[ref=exit]' : {
						click : function(button, e, eOpts) {
							location.href = location.href + 'exit/exit.html';
							// location.href = location.pathname;
						}
					}
				});
			},
			views : ['am.manager.view.ManagerMainLayout',
					'am.manager.view.ManagerCenterLayout',
					'am.manager.view.ManagerWestLayout',
					'am.manager.view.ManagerTopLayout']
		});