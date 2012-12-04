﻿/**
* @author admin
*/

var testCase = {
	name: 'Simple Remote tests',
	_wait: {
		after: 5000
	},
	testRemoteAddressExists: function () {
		var url = application.httpServer.ipAddress;
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://194.98.194.72/testRequest?url=http://' + url + ':8081/testRemoteAddressExists');
		myXHR.send();
		Y.Assert.areSame('string', myXHR.responseText);
	},
	testRemotePortExists: function () {
		var url = application.httpServer.ipAddress;
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://194.98.194.72/testRequest?url=http://' + url + ':8081/testRemotePortExists');
		myXHR.send();
		Y.Assert.areSame('number', myXHR.responseText);
	},
	testLocalAddressExists: function () {
		var url = application.httpServer.ipAddress;
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://194.98.194.72/testRequest?url=http://' + url + ':8081/testLocalAddressExists');
		myXHR.send();
		Y.Assert.areSame('string', myXHR.responseText);
	},
	testLocalPortExists: function () {
		var url = application.httpServer.ipAddress;
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://194.98.194.72/testRequest?url=http://' + url + ':8081/testLocalPortExists');
		myXHR.send();
		Y.Assert.areSame('number', myXHR.responseText);
	},
	testIsSSLExists: function () {
		var url = application.httpServer.ipAddress;
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://194.98.194.72/testRequest?url=http://' + url + ':8081/testIsSSLExists');
		myXHR.send();
		Y.Assert.areSame('boolean', myXHR.responseText);
	},
	testRemoteAddressExists2: function () {
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://127.0.0.1:8081/testRemoteAddressExists');
		myXHR.send();
		Y.Assert.areSame('string', myXHR.responseText);
	},
	testRemotePortExists2: function () {
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://127.0.0.1:8081/testRemotePortExists');
		myXHR.send();
		Y.Assert.areSame('number', myXHR.responseText);
	},
	testLocalAddressExists2: function () {
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://127.0.0.1:8081/testLocalAddressExists');
		myXHR.send();
		Y.Assert.areSame('string', myXHR.responseText);
	},
	testLocalPortExists2: function () {
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://127.0.0.1:8081/testLocalPortExists');
		myXHR.send();
		Y.Assert.areSame('number', myXHR.responseText);
	},
	testIsSSLExists2: function () {
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://127.0.0.1:8081/testIsSSLExists');
		myXHR.send();
		Y.Assert.areSame('boolean', myXHR.responseText);
	},
	testRemoteAddressValueRemote: function () {
		var url = application.httpServer.ipAddress;
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://194.98.194.72/testRequest?url=http://' + url + ':8081/testRemoteAddressValue');
		myXHR.send();
		Y.Assert.areSame('::ffff:194.98.194.72', myXHR.responseText);
	},
	testRemoteAddressValueLocal: function () {
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://127.0.0.1:8081/testRemoteAddressValue');
		myXHR.send();
		Y.Assert.areSame('::ffff:127.0.0.1', myXHR.responseText);
	},
	testRemotePortValueRemote: function () {
		var url = application.httpServer.ipAddress;
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://194.98.194.72/testRequest?url=http://' + url + ':8081/testRemotePortValue');
		myXHR.send();
		Y.Assert.isTrue(parseInt(myXHR.responseText) >= 40000, 'remotePort should be >= 40000, actual value: ' + myXHR.responseText);
	},
	testRemotePortValueLocal: function () {
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://127.0.0.1:8081/testRemotePortValue');
		myXHR.send();
		Y.Assert.isTrue(parseInt(myXHR.responseText) >= 40000, 'remotePort should be >= 40000, actual value: ' + myXHR.responseText);
	},
	testLocalAddressValueRemote: function () {
		var url = application.httpServer.ipAddress;
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://194.98.194.72/testRequest?url=http://' + url + ':8081/testLocalAddressValue');
		myXHR.send();
		Y.Assert.areSame('::ffff:' + url, myXHR.responseText);
	},
	testLocalAddressValueLocal: function () {
		var url = application.httpServer.ipAddress;
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://127.0.0.1:8081/testLocalAddressValue');
		myXHR.send();
		Y.Assert.areSame('::ffff:127.0.0.1', myXHR.responseText);
	},
	testLocalPortValueRemote: function () {
		var url = application.httpServer.ipAddress;
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://194.98.194.72/testRequest?url=http://' + url + ':8081/testLocalPortValue');
		myXHR.send();
		Y.Assert.areSame(8081, parseInt(myXHR.responseText));
	},
	testLocalPortValueLocal: function () {
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://127.0.0.1:8081/testLocalPortValue');
		myXHR.send();
		Y.Assert.areSame(8081, parseInt(myXHR.responseText));
	},
	testIsSSLValueRemoteUnsecured: function () {
		var url = application.httpServer.ipAddress;
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://194.98.194.72/testRequest?url=http://' + url + ':8081/testIsSSLValue');
		myXHR.send();
		Y.Assert.areSame('false', myXHR.responseText);
	},
	testIsSSLValueLocalUnsecured: function () {
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://127.0.0.1:8081/testIsSSLValue');
		myXHR.send();
		Y.Assert.areSame('false', myXHR.responseText);
	},
	testIsSSLValueRemoteSecured: function () {
		var url = application.httpServer.ipAddress;
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'http://194.98.194.72/testRequest?url=https://' + url + ':8082/testIsSSLValue');
		myXHR.send();
		Y.Assert.areSame('true', myXHR.responseText);
	},
	testIsSSLValueLocalSecured: function () {
		var myXHR = new XMLHttpRequest();
		myXHR.open('GET', 'https://127.0.0.1:8082/testIsSSLValue');
		myXHR.send();
		Y.Assert.areSame('true', myXHR.responseText);
	}
};