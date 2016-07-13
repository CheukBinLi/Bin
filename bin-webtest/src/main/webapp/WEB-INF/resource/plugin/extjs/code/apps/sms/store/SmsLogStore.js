// 权限信息集
Ext.define('am.sms.store.SmsLogStore', {
			extend : 'Ext.data.Store',
			storeId : 'smslogstore',
			fields : [{
						name : 'id',
						type : 'int'
					}, {
						name : 'userid',
						type : 'string'
					}, {
						name : 'numbers',
						type : 'string'
					}, {
						name : 'message',
						type : 'string'
					}, {
						name : 'count',
						type : 'string'
					}, {
						name : 'state',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					}, {
						name : 'dateTime',
						type : 'string'
					}, {
						name : 'remark',
						type : 'String'
					}],
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : 'smsmanager/allSmsLog.html',
				reader : {
					type : 'json'
					// root : 'users'
				}
			},
			autoLoad : true
		});
// 创建数据集
//Ext.create('am.sms.store.SmsLogStore').load();
