// 用户管理store
Ext.define('am.user.store.UserManagerStore', {
			extend : 'Ext.data.Store',
			fields : [{
						name : 'id',
						type : 'int'
					}, {
						name : 'userName',
						type : 'string'
					}, {
						name : 'password',
						type : 'string'
					}, {
						name : 'authority',
						type : 'String'
					}, {
						name : 'balance',

						type : 'int'
					}, {
						name : 'smsInterface',
						type : 'String'
					}, {
						name : 'remark',
						type : 'String'
					}],
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : 'usermanager/allUser.html',
				reader : {
					type : 'json'
					// root : 'users'
				}
			},
			autoLoad : true
		});
Ext.create('am.user.store.UserManagerStore').load();
// private int id;
// private String userName;
// private String password;
// private int authority;// 权限
// private int balance;// 余额
// private int smsInterface;// 短信接口
// private String remark;// 备注
