// 修改、查看、添加
Ext.define('am.user.view.CreateAuthorityLayout', {
	extend : 'Ext.window.Window',
	alias : 'widget.createauthoritylayout',
	title : '新增权限组',
	closable : true,
	closeAction : 'hide',
	isModify : true,// 是否编辑
	addUrl : 'usermanager/addAuthority.html',
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
				fieldLabel : '权限名',
				name : 'name',
				allowBlank : false
			}, {
				xtype : 'textfield',
				fieldLabel : '备用功能数组',
				name : 'controllMap'
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
				rootPanel : 'createauthoritylayout'
			} ]
		} ]
	} ]
});
