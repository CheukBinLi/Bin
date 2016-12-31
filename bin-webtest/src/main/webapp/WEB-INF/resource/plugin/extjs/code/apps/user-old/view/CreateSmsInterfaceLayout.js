// 短信:修改、查看、添加
Ext.define('am.user.view.CreateSmsInterfaceLayout', {
	extend : 'Ext.window.Window',
	alias : 'widget.createsmsInterfacelayout',
	title : '新增短信接口信息',
	closable : true,
	closeAction : 'hide',
	isModify : true,// 是否编辑
	addUrl : 'smsmanager/addSmsInterface.html',
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
				labelWidth : 100
			},
			items : [ {
				xtype : 'hidden',
				fieldLabel : '编号',
				name : 'id',
				value : '-1',
				submitValue : false
			// 不提交
			}, {
				xtype : 'textfield',
				fieldLabel : '接口名',
				name : 'name',
				allowBlank : false
			}, {
				xtype : 'textfield',
				fieldLabel : '接口完整名称(Class类名字)',
				name:'className',
				allowBlank : false
			}, {
				xtype : 'textfield',
				fieldLabel : '接口帐号',
				name : 'userName',
				allowBlank : false
			}, {
				xtype : 'textfield',
				fieldLabel : '接口密码',
				inputType : 'password',
				name : 'password',
				allowBlank : false
			}, {
				xtype : 'textfield',
				fieldLabel : '接口平台后台地址',
				name : 'url',
				allowBlank : false
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
				rootPanel : 'createsmsInterfacelayout'
			} ]
		} ]
	} ]
});
