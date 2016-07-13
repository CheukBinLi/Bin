// 短信接口信息集
Ext.define('am.user.store.SmsInterfaceStore', {
			extend : 'Ext.data.Store',
			storeId : 'smsinterfacestore',
			// private int id;
			// private String name;//zhiqing
			// private String className;// 接口 类名
			// private String userName;
			// private String password;
			// private String url;// 短信平台后台网址
			// private String remark;
			fields : [{
						name : 'id',
						type : 'int'
					}, {
						name : 'name',
						type : 'string'
					}, {
						name : 'className',
						type : 'String'
					}, {
						name : 'userName',
						type : 'String'
					}, {
						name : 'password',
						type : 'String'
					}, {
						name : 'url',
						type : 'String'
					}, {
						name : 'remark',
						type : 'String'
					}],
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				// async : false,
				url : 'smsmanager/allSmsInterface.html',
				reader : {
					type : 'json'
					// root : 'users'
				}
			},
			autoLoad:true
		});
// 创建数据集
Ext.create('am.user.store.SmsInterfaceStore').load();
