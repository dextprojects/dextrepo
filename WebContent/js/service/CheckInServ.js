'use strict';
var app = angular.module('EximApp');

app.factory("CheckInServ", function ($http, REST_BASE_URL) {

	var _checkLogin=function (id, password) {
		var obj = {loginId: id,password: password};
		console.log(obj);
		var url = REST_BASE_URL + "?module=Login&option=checkLogin&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	var _getReq = function (user_cd) {
		var obj={loginId: user_cd};
		console.log('GetReq: '+obj);
		var url = REST_BASE_URL + "?option=getCiRequests&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	var _getMenu = function (user_cd) {
		var obj={loginId: user_cd};
		var url = REST_BASE_URL + "?module=Login&option=getMenu&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	var _logOut = function (user_cd) {
		var obj={loginId: user_cd};
		var url = REST_BASE_URL + "?module=Login&option=logOut&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	var _notifReq = function (user_cd) {
		var obj={loginId: user_cd};
		var url = REST_BASE_URL + "?option=getNotif&Param="+JSON.stringify(obj);
		var data ="";
		return $http.post(url,data,{ignoreLoadingBar: true})
	}
	
	var _updateRequest = function (obj) {
		console.log('UpdateReq: '+obj);
		//var url = REST_BASE_URL + "?option=SubmitChangeEntry&compl_id="+compl_id+"&status="+status+"&user_cd="+user_cd+"&subject="+subject+"&remarks="+remarks;
		var url = REST_BASE_URL + "?option=SubmitChangeEntry&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	var _checkSession = function (id) {
		var obj = {loginId: id};
		var url = REST_BASE_URL + "?option=checkSession&Param="+JSON.stringify(obj);
		return $http.post(url);
	}
	
	var _allUser = function (user_cd) {
		var obj={loginId: user_cd};
		var url = REST_BASE_URL + "?option=getAllUsers&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	var _getBarcodes = function (user_cd) {
		var obj={loginId: user_cd};
		var url = REST_BASE_URL + "?option=getScannedBarcode&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	var _monReqGraph = function (user_cd) {
		var obj={loginId: user_cd};
		var url = REST_BASE_URL + "?option=getMonReqGraph&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	return {
		checkLogin: _checkLogin,
		updateRequest: _updateRequest,
		getReq: _getReq,
		notifReq: _notifReq,
		checkSession: _checkSession,
		allUsers: _allUser,
		monReqGraph: _monReqGraph,
		getBarcodes: _getBarcodes,
		getMenu: _getMenu,
		logOut: _logOut
		
	}
})