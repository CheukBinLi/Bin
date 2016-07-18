// 修改数据
var modifyingDataByGrid = function(grid, u, callbackFunction) {
	var store = grid.getStore();
	var changeStore = store.getUpdatedRecords();
	var arr = [];
	// grid.update();
	for (var i = 0; i < changeStore.length; i++) {
		arr.push(changeStore[i].data);
	}
	if (arr.length > 0) {
		Ext.Ajax.request({
			url : u,
			async : true,
			params : {
				'data' : Ext.encode(arr)
			},
			method : 'POST',
			reader : {
				type : 'json'
			// root : 'users'
			},
			success : function(response) {
				var falgs = response.responseText == 'ok' ? true : false;
				if (falgs) {
					store.commitChanges();
				}
				callbackFunction(falgs);
				return falgs;
			},
			failure : function(res) {
				callbackFunction(falgs);
				return false;
			}

		});
	}
};

// 添加数据
var createDataByForm = function(form, u, callbackFunction) {
	if (form.isValid()) {
		var values = form.getValues();
		Ext.Ajax.request({
			url : u,
			async : true,
			params : {
				'data' : Ext.encode(values)
			},
			method : 'POST',
			reader : {
				type : 'json'
			// root : 'users'
			},
			success : function(response) {
				var falgs = response.responseText == 'ok' ? true : false;
				callbackFunction(falgs);
				return falgs;
			},
			failure : function(res) {
				callbackFunction(false);
				return false;
			}

		});
	}
}

var simpleAjax = function(data, u, callbackFunction) {
	Ext.Ajax.request({
		url : u,
		async : true,
		params : {
			'data' : Ext.encode(data)
		},
		method : 'POST',
		reader : {
			type : 'json'
		// root : 'users'
		},
		success : function(response) {
			// var falgs = response.responseText == 'ok' ? true : false;
			return callbackFunction(response);

		},
		failure : function(res) {
			callbackFunction(false);
			return false;
		}
	});
}