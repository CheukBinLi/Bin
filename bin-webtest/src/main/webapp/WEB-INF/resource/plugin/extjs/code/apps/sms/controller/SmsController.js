Ext.define('am.sms.controller.SmsController', {
			extend : 'Ext.app.Controller',
			init : function() {
			},
			views : ['am.sms.view.SmsMainLayout'],
			stores : ['am.sms.store.SmsLogStore']
		});