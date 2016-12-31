// 修改数据
var modifyingDataByGrid = function(grid, u, callbackFunction) {
	var store = grid.getStore();
	var changeStore = store.getUpdatedRecords();
	var arr =
		[];
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

var $simpleAjax = {
	base : function(Url, ArrayParams, MethodType, Success, fail, sync) {
		Ext.Ajax.request({
			url : Url,
			async : sync ? false : true,
			params : {
				'data' : Ext.encode(Params)
			},
			method : MethodType,
			reader : {
				type : 'json'
			// ,root : 'users'
			},
			success : function(response, opts) {
				// var falgs = response.responseText == 'ok' ? true : false;
				if (Success)
					Success(response, opts);

			},
			failure : function(response, opts) {
				if (fail)
					return fail(response, opts);
			}
		});
	},
	modifyGrid : function(Url, Grid, Success, fali) {
		var store = Grid.getStore();
		var changeStore = store.getUpdatedRecords();
		var arrParams =
			[];
		// grid.update();
		for (var i = 0; i < changeStore.length; i++) {
			arrParams.push(changeStore[i].data);
		}
		this.base(Url, arrParams, "POST", function(response, opts) {
			if (response.responseText.success == 'true')
				store.commitChanges();
			if (Success)
				Success(response, opts);
		}, function(response, opts) {
			Ext.Msg.alert('错误提示', '末知异常。/n请查看控制台信息。');
			console.log('response:' + response);
		});
	},
	submitForm : function(Url, Form, Success, fali) {
		if (form.isValid())
			return;
		var values = Form.getValues();
		this.base(Url, values, "POST", function(response, opts) {
			if (Success)
				Success(response, opts);
		}, function(response, opts) {
			Ext.Msg.alert('错误提示', '末知异常。/n请查看控制台信息。');
			console.log('response:' + response);
		});
	}

}

var $map = function() {
	var table = new Array();
	return {
		put : function(key, value) {
			var result = table[key];
			table[key] = value;
			return result;
		},
		get : function(key) {
			return table[key];
		}
	}
}
var $urlData = {
	data : $map(),
	get : function(key) {
		return this.data[key];
	},
	add : function(key, value) {
		result = this.data[key];
		this.data[key] = value;
		return result;
	},
	loadData : function(url, params, methodType, root) {
		this.data = $map();
		// ajax加载
	}
}
