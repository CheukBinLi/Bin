Ext.define("am.authority.controller.AuthorityController", {
	extend : 'Ext.app.Controller',
	init : function() {
		this.control({
			'form button[ref=login]' : {
				click : function(button, e, eOpts) {
					var app = button.up('viewport');
					var formBase = button.up('form').getForm();
					var cc = formBase.getValues();
					if (cc.phone.length < 1 || cc.password.length < 1) {
						Ext.Msg.alert('请输入正确的帐号/密码！');
						return;
					}
					// alert(123);
					// 验证帐号
					Ext.Ajax.request({
						async : false,
						method : 'POST',
						url : 'login',
						type : 'ajax',
						params : {
							phone : cc.phone,
							password : cc.password
						},
						success : function(response) {
							if (response.responseText.toUpperCase() == 'OK') {
								// app.removeAll();
								// app.add(Ext.createWidget('managermainlayout'));
								location.reload();
							}
						}
					});
				}
			},
			'form button[ref=reset]' : {
				click : function(button, e, eOpts) {
					var app = button.up('viewport');
					var formBase = button.up('form').getForm();
					formBase.reset();
				}
			}
		});
	},
	views :
		[ 'am.authority.view.AuthorityLeftLayout', 'am.authority.view.AuthorityMainLayout', 'am.authority.view.AuthorityCenterLayout' ],
	stores :
		[ 'am.authority.store.AuthorityLeftLayoutStore' ]
});