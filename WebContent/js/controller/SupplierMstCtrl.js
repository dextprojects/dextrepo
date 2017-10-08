'use strict';
/**
 * @ngdoc function
 * @name EximApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the LoginCtrl
 */
var app=angular.module('EximApp');

app.controller('SupplierMstCtrl',['$scope', '$state', 'MasterServ', 'localStorageService', '$interval', function($scope,$state,CheckInServ, localStorageService,$interval) {
	
	$scope.userCd=localStorageService.get('userCd');
	$scope.master = {};
	
    $("#EntryType").bootstrapSwitch({
	    onText: 'New',
	    offText: 'Modify'
    }).on('switchChange.bootstrapSwitch', function(event, state) {
	  	document.getElementById("suplist").disabled = state;
	  	if(state) {
		  	document.getElementById("suppliername").style.display='block';
		  	document.getElementById("supplierNameDisp").style.display='none';
		} else {
			document.getElementById("suppliername").style.display='none';
			document.getElementById("supplierNameDisp").style.display='block';
		}
	});
	
	$("#activeDeactive").bootstrapSwitch({
	    onText: 'Active',
	    offText: 'De-Active'
    }).on('switchChange.bootstrapSwitch', function(event, state) {
    	console.log(state);
	  	if(state)
		  	$scope.getSuppliers("N");
		else
			$scope.getSuppliers("Y");
	});
	
	$scope.setTraders = function(tradersData) {
		$('.js-example-basic-single').select2({
			placeholder: "Select a trader",
			allowClear: false,
			data: tradersData
		});
	}

	$scope.startingFuncs = function() {
		$scope.loader();
		$scope.getTraders();
    }
    
	$scope.getSuppliers = function(delFlag) {
		CheckInServ.getSuppliers($scope.userCd,delFlag)
		    .then(function(response) {
		    var obj = JSON.parse(JSON.stringify(response.data));
		    	if(!("Session" in obj)) {
			      console.log(obj.Data);	
			      $scope.Suppliers=obj.Data;
				} else {
					$state.go('login');
				}		      
		    }, function (err) {
		      console.log(err);
		    })
	}
	
	$scope.getTraders = function() {
		CheckInServ.getTradersOfSuppliers($scope.userCd)
		    .then(function(response) {
		    var obj = JSON.parse(JSON.stringify(response.data));
		    	if(!("Session" in obj)) {
		        	$scope.setTraders(obj.Data);
				} else {
					$state.go('login');
				}		      
		    }, function (err) {
		      console.log(err);
		    })
	}
	
	$scope.saveData = function() {
	
	}
	
	$scope.checkData = function() {
	
	}
	
	$scope.loader = function() {
	    $('.loader-item').fadeIn(''); // will first fade in the loading animation
    	$('#pageloader').fadeIn(''); // will fade in the white DIV that covers the website.
		$('.loader-item').fadeOut('slow'); // will first fade out the loading animation
       	$('#pageloader').delay(100).fadeOut('slow'); // will fade out the white DIV that covers the website.
	}
	
	$scope.reset = function() {
        $scope.user = angular.copy($scope.master);
     }
}]);