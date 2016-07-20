Ext.define('am.manager.view.ManagerWestLayout', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.managerwestlayout',
    layout : 'accordion',
    margin : "5 5 5 5",
    frame : true,
    items : [ {
	title : '信息管理',
	xtype : 'treepanel',
	rootVisible : false,
	root : {
	    expanded : true,
	    children : [ {
		text : "用户管理",
		id : 'usermanagerlayout',
		leaf : true
	    }, {
		text : "权限信息",
		id : 'authoritymanagerlayout',
		leaf : true
	    }, {
		text : "短信接口",
		id : 'smsinterfacemanagerlayout',
		leaf : true
	    }, {
		text : "充值信息",
		id : 'rechareginformationlayout',
		leaf : true
	    }, {
		text : "充值信息X",
		id : 'smsmainlayout',
		leaf : true
	    } ]
	}
    }, {
	title : '短信管理',
	xtype : 'treepanel',
	rootVisible : false,
	root : {
	    expanded : true,
	    children : [ {
		text : "发送记录",
		id : 'smsmainlayout',
		leaf : true
	    } ]
	}
    } ]
})