Ext
		.define(
				'am.login.view.LoginLayout',
				{
					extend : 'Ext.panel.Panel',
					alias : 'widget.loginlayout',
					layout : {
						align : 'middle',
						pack : 'center',
						type : 'hbox'
					},
					bodyStyle : 'background:#ffc;padding:10px;',
					items : [ {
						width : 350,
						maxWidth : 350,
						height : 250,
						maxHeight : 250,
						frame : true,
						layout : {
							align : 'middle',
							pack : 'center',
							type : 'hbox'
						},
						items : [ {
							xtype : 'form',
							defaults : {
								// closable : true,
								// closeAction : 'destroy',
								padding : " 30 30 30 30",
								bodyBorder : false,
								blankText : '不能为空',
								msgTarget : 'side',
								minWidth : 80
							},
							items : [
									{
										margin : '20 0 0 0',
										xtype : 'textfield',
										name : 'phone',
										fieldLabel : '帐号',
										labelWidth : 50,
										minLengthText : 6,
										allowBlank : false
									},
									{
										xtype : 'textfield',
										name : 'password',
										fieldLabel : '密码',
										labelWidth : 50,
										inputType : 'password',
										minLengthText : 1,
										allowBlank : false
									},
									{
										extend : 'Ext.panel.Panel',
										border : 0,
										layout : {
											type : 'hbox',
											align : 'stretch'
										},
										items : [
												{
													xtype : 'textfield',
													name : 'verificationcode',
													fieldLabel : '验证码',
													labelWidth : 50,
													width : 140,
													minLengthText : 1,
													blankText : '请输入验证码',
													allowBlank : false
												},
												{
													xtype : 'image',
													margin : '0 0 0 5',
													height : 30,
													width : 60,
													listeners : {
														click : {
															element : 'el',
															fn : function(a, b,
																	c) {
																b
																		.setAttribute(
																				"src",
																				'verificationcode?'
																						+ Math
																								.random());
															}
														}
													},
													initComponent : function() {
														this
																.setSrc('verificationcode');
													}
												} ]
									} ],
							buttonAlign : 'center',
							buttons : [ {
								text : '登录',
								ref : 'login'
							}, {
								text : '重置',
								ref : 'reset'
							} ]
						} ],
						listeners : {
							'afterrender' : function(login, eOpts) {
								simpleAjax(null, "verification", function(response) {
									if(response.responseText=="OK"){
										var app = login.up('viewport');
										app.removeAll();
										app.add(Ext.createWidget('managermainlayout'));
									}
								});
							}
						}
					} ]
				});
