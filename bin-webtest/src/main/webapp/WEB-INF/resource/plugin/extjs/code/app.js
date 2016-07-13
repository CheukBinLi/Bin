Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.Loader.setConfig({
				enabled : true
			});
	Ext.application({
				// requires : ['Ext.container.Viewport'],
				name : 'am',
				appFolder : 'resource/plugin/extjs/code/apps',
				launch : function() {
					Ext.create('Ext.container.Viewport', {
								layout : 'fit',
								items : [{
											xtype : 'loginlayout'
										}]
							});
				},
				controllers : [
						'am.manager.controller.ManagerController',
						'am.login.controller.LoginController'
						]
			});
		//
	});