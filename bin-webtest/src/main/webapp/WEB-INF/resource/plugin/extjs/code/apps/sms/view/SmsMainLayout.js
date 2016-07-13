Ext.define('am.sms.view.SmsMainLayout', {
			extend : 'Ext.grid.Panel',
			alias : 'widget.smsmainlayout',
			forceFit : true,// 强制列自适应成可用宽度
			frame : true,
			layout : 'fit',
			closable : true,
			closeAction : 'destroy',
			selType : 'checkboxmodel',
			multiSelect : true,
			columns : [{
						text : '行',
						xtype : 'rownumberer'
					}, {
						text : '编号',
						dataIndex : 'id',
						hidden : true
					}, {
						text : '用户编号',
						dataIndex : 'userid'
					}, {
						text : '发送号码',
						dataIndex : 'numbers'
					}, {
						text : '短信内容',
						dataIndex : 'message'
					}, {
						text : '扣减数量',
						dataIndex : 'count'
					}, {
						text : '状态',
						dataIndex : 'state'
					}, {
						text : '发送时间',
						dataIndex : 'dateTime'
					}, {
						text : '备注',
						dataIndex : 'remark'
					}],
			store : 'am.sms.store.SmsLogStore',
			initComponent : function() {
				this.callParent(arguments);
			}
		})
// private int id;
// private int userid;// 用户ID
// private String numbers;// 号码集
// private String message;// 短信内容
// private int count;// 扣减数
// private int state;// 状态
// private Date dateTime;
// private String remark;
