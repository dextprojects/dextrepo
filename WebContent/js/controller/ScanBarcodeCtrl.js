'use strict';
/**
 * @ngdoc function
 * @name CheckInApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the LoginCtrl
 */
var app=angular.module('CheckInApp');

app.controller('ScanBarcodeCtrl',['$scope', '$state', 'CheckInServ', 'localStorageService', '$interval', function($scope,$state,CheckInServ, localStorageService,$interval) {
	
	$scope.timeVal=10000;
	
	$scope.checkLogin = function() {
		console.log(localStorageService.get('userCd'));
		if(localStorageService.get('userCd')==null) {
			$state.go('login');
		} else {
			CheckInServ.checkSession(localStorageService.get('userCd'))
		    .then(function(response) {
		      var obj = JSON.parse(JSON.stringify(response.data));
		    	if(obj.session==false) {
			    	$state.go('login');
			    	localStorageService.clearAll(); 
		    	} else {
		    		console.log('::OK::');
		    		return true;
		    	}
		    }, function (err) {
		      console.log(err);
		    })
		}
	}

	$scope.getUsers = function() {
		$scope.loader();
		$scope.userName=localStorageService.get('userName');
		CheckInServ.getBarcodes(localStorageService.get('userCd'))
		    .then(function(response) {
		    var obj = JSON.parse(JSON.stringify(response.data));
		    	if(!("Session" in obj)) {
			      $scope.allBarcodes=obj.Data;
			      console.log($scope.allUsers);
				} else {
					$state.go('login');
				}		      
		    }, function (err) {
		      console.log(err);
		    })
    }
    
    $scope.startingFuncs = function() {
    	console.log("Scanned Barcode");
		$scope.getUsers();
    }
    
    $scope.loader = function() {
	    $('.loader-item').fadeIn(''); // will first fade in the loading animation
    	$('#pageloader').fadeIn(''); // will fade in the white DIV that covers the website.
		$('.loader-item').fadeOut('slow'); // will first fade out the loading animation
       	$('#pageloader').delay(100).fadeOut('slow'); // will fade out the white DIV that covers the website.
	}
}]);