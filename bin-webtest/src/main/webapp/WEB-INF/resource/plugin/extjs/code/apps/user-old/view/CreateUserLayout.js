// 修改、查看、添加
Ext.define('am.user.view.CreateUserLayout', {
	extend : 'Ext.window.Window',
	alias : 'widget.createuserlayout',
	title : '添加新用户',
	closable : true,
	closeAction : 'hide',
	isModify : true,// 是否编辑
	addUrl:'usermanager/addUser.html',
	// width : 300,
	// height : 300,
	items : [ {
		xtype : 'panel',
		frame : true,
		layout : {
			align : 'middle',
			pack : 'center',
			type : 'hbox'
		},
		items : [ {
			xtype : 'form',
			frame : true,
			defaults : {
				// padding : " 30 30 30 30"
				margin : '20 50 20 50',
				msgTarget : 'side',
				labelWidth : 60
			},
			items : [ {
				xtype : 'hidden',
				fieldLabel : '编号',
				name : 'id',
				value : '-1'
				,submitValue : false// 不提交
			}, {
				xtype : 'textfield',
				fieldLabel : '帐号',
				name : 'userName',
				allowBlank : false
			}, {
				xtype : 'textfield',
				fieldLabel : '密码',
				name : 'password',
				allowBlank : false,
				minLength : 6
			}, {
				xtype : 'numberfield',
				fieldLabel : '余额',
				value : 0,
				minValue : 0,
				allowDecimals : false,// 禁用小数
				allowBlank : false,
				name : 'balance'
			}, {
				xtype : 'combobox',
				fieldLabel : '短信接口',
				allowBlank : false,
				editable : false,
				name : 'smsInterface',
				displayField : 'name',
				valueField : 'id',
				store : 'am.user.store.SmsInterfaceStore'
			}, {
				xtype : 'combobox',
				fieldLabel : '权限',
				name : 'authority',
				editable : false,
				allowBlank : false,
				displayField : 'name',
				valueField : 'id',
				store : 'am.user.store.AuthorityStore'
			}, {
				xtype : 'textfield',
				fieldLabel : '备注',
				name : 'remark'
			} ],
			buttonAlign : 'center',
			buttons : [ {
				margin : '10 0 20 0',
				text : '保存',
				ref : 'save'
			}, {
				margin : '10 0 20 30',
				text : '返回',
				ref : 'esc',
				rootPanel : 'createuserlayout'
			} ]
		} ]
	} ]
});
