'use strict';
var app = angular.module('EximApp');

app.factory("MasterServ", function ($http, REST_BASE_URL) {

	var _getMenu = function (user_cd) {
		var obj={loginId: user_cd};
		var url = REST_BASE_URL + "?module=Login&option=getMenu&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	var _getBuyers = function (user_cd,delFlag) {
		var obj={loginId: user_cd,delFlag: delFlag};
		var url = REST_BASE_URL + "?module=Master&option=getBuyers&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	var _getSuppliers = function (user_cd,delFlag) {
		var obj={loginId: user_cd,delFlag: delFlag};
		var url = REST_BASE_URL + "?module=Master&option=getSuppliers&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	var _getTradersOfBuyers = function (user_cd) {
		var obj={loginId: user_cd};
		var url = REST_BASE_URL + "?module=Master&option=getTradersOfBuyers&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	var _getTradersOfSuppliers = function (user_cd) {
		var obj={loginId: user_cd};
		var url = REST_BASE_URL + "?module=Master&option=getTradersOfSuppliers&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	var _getBuyerDetail = function(user_cd,buyer_cd) {
		var obj={loginId: user_cd,buyer_cd: buyer_cd};
		var url = REST_BASE_URL + "?module=Master&option=getBuyerDetail&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	$("#supplierNameSel").select2({
	    minimumInputLength: 1,
	    ajax: {
	        url: REST_BASE_URL +"?module=Master&option=getDirectSupplier&Param="+JSON.stringify({loginId: '100010',search: $(".select2-search__field").val()}),
	        dataType: 'json',
	        type: "POST",
	        quietMillis: 50,
	        delay: 250,
	        data: function (term) {
	            return {
	                term: term
	            };
	        }
	    }
	});
	
	$("#buyerNameSel").select2({
	    minimumInputLength: 1,
	    ajax: {
	        url: REST_BASE_URL +"?module=Master&option=getDirectBuyer&Param="+JSON.stringify({loginId: '100010',search: $(".select2-search__field").val()}),
	        dataType: 'json',
	        type: "POST",
	        quietMillis: 50,
	        delay: 250,
	        data: function (term) {
	            return {
	                term: term
	            };
	        }
	    }
	});
	
	var _updateRequest = function (obj) {
		console.log('UpdateReq: '+obj);
		var url = REST_BASE_URL + "?option=SubmitChangeEntry&Param="+JSON.stringify(obj);
		return $http.post(url)
	}
	
	return {
		updateRequest: _updateRequest,
		getMenu: _getMenu,
		getBuyers: _getBuyers,
		getSuppliers: _getSuppliers,
		getTradersOfBuyers: _getTradersOfBuyers,
		getTradersOfSuppliers: _getTradersOfSuppliers,
		getBuyerDetail: _getBuyerDetail
	}
})