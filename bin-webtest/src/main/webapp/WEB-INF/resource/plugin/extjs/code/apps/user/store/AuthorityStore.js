// 权限信息集
Ext.define('am.user.store.AuthorityStore', {
			extend : 'Ext.data.Store',
			storeId : 'authoritystore',
			fields : [{
						name : 'id',
						type : 'int'
					}, {
						name : 'name',
						type : 'string'
					}, {
						name : 'controllMap',
						type : 'String'
					}, {
						name : 'userName',
						type : 'String'
					}, {
						name : 'remark',
						type : 'String'
					}],
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : 'usermanager/allAuthority.html',
				reader : {
					type : 'json'
					// root : 'users'
				}
			},
			autoLoad : true
		});
// 创建数据集
Ext.create('am.user.store.AuthorityStore').load();
