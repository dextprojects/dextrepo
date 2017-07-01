'use strict';
var app = angular.module('CheckInApp');

app.factory("CheckInServ", function ($http, REST_BASE_URL,REST_BASE_URL_NOTIF) {

	var _checkLogin=function (id, password) {
		var obj = {loginId: id,password: password};
		console.log(obj);
		var url = REST_BASE_URL + "?option=CheckLoginWeb&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	var _getReq = function (user_cd) {
		var obj={loginId: user_cd};
		console.log('GetReq: '+obj);
		var url = REST_BASE_URL + "?option=getCiRequests&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	var _notifReq = function (user_cd) {
		var obj={loginId: user_cd};
		var url = REST_BASE_URL_NOTIF + "?option=getNotif&Param="+JSON.stringify(obj);
		var data ="";
		return $http.post(url,data,{ignoreLoadingBar: true})
	}
	
	var _updateRequest = function (obj) {
		console.log('UpdateReq: '+obj);
		//var url = REST_BASE_URL + "?option=SubmitChangeEntry&compl_id="+compl_id+"&status="+status+"&user_cd="+user_cd+"&subject="+subject+"&remarks="+remarks;
		var url = REST_BASE_URL + "?option=SubmitChangeEntry&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	var _resetHouse = function (obj) {
		var url = REST_BASE_URL + "?option=resetHouse&Param="+JSON.stringify(obj);
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
	
	var _allHouses = function (user_cd) {
		var obj={loginId: user_cd};
		var url = REST_BASE_URL + "?option=getAllHouses&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	var _getNotices = function (user_cd) {
		var obj={loginId: user_cd};
		var url = REST_BASE_URL + "?option=getNotices&Param="+JSON.stringify(obj);
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
	
	var _saveNotice = function (obj) {
		var url = REST_BASE_URL + "?option=EnterNotice&Param="+JSON.stringify(obj);
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
		getNotices: _getNotices,
		saveNotice: _saveNotice,
		allHouses: _allHouses,
		resetHouse: _resetHouse
	}
})