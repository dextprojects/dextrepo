'use strict';
/**
 * @ngdoc function
 * @name CheckInApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the LoginCtrl
 */
var app=angular.module('CheckInApp');

app.controller('AllHousesCtrl',['$scope', '$state', 'CheckInServ', 'localStorageService', '$interval', function($scope,$state,CheckInServ, localStorageService,$interval) {
	
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

	$scope.getHouses = function() {
		$scope.loader();
		$scope.userName=localStorageService.get('userName');
		CheckInServ.allHouses(localStorageService.get('userCd'))
		    .then(function(response) {
		    var obj = JSON.parse(JSON.stringify(response.data));
		    	if(!("Session" in obj)) {
			      $scope.allHouses=obj.Data;
			      console.log($scope.allHouses);
				} else {
					$state.go('login');
				}		      
		    }, function (err) {
		      console.log(err);
		    })
    }
    
    $scope.startingFuncs = function() {
    	console.log("HOUSES");
		$scope.getHouses();
    }
    
    $scope.resetValue = function(houseCd) {
    	$scope.loader();
		$scope.userName=localStorageService.get('userName');
		var obj={"loginId":localStorageService.get('userCd'),"houseCd":houseCd}
		CheckInServ.resetHouse(obj)
		    .then(function(response) {
		    var obj = JSON.parse(JSON.stringify(response.data));
		    	if(!("Session" in obj)) {
			      //console.log(obj);
			      $scope.allHouses=obj.Data;
				} else {
					$state.go('login');
				}		      
		    }, function (err) {
		      console.log(err);
		    })
    } 
    
    $scope.loader = function() {
	    $('.loader-item').fadeIn(''); // will first fade in the loading animation
    	$('#pageloader').fadeIn(''); // will fade in the white DIV that covers the website.
		$('.loader-item').fadeOut('slow'); // will first fade out the loading animation
       	$('#pageloader').delay(100).fadeOut('slow'); // will fade out the white DIV that covers the website.
	}
}]);