// 权限信息集
Ext.define('am.authority.store.AuthorityLeftLayoutStore', {
	extend : 'Ext.data.TreeStore',
	alias : 'widget.authorityLeftLayoutStore',
	root : {
		expanded : true,
		children :
			[ {
				text : 'detention',
				leaf : true
			}, {
				text : 'homework',
				expanded : true,
				children :
					[ {
						text : 'book report',
						leaf : true
					}, {
						text : 'algebra',
						leaf : true
					} ]
			}, {
				text : 'buy lottery tickets',
				leaf : true
			} ]
	}
// fields : [ {
// name : 'id',
// type : 'int'
// }]
});
// 创建数据集
// Ext.create('am.sms.store.SmsLogStore').load();
