'use strict';
/**
 * @ngdoc function
 * @name EximApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the LoginCtrl
 */
var app=angular.module('EximApp');

app.controller('DashboardCtrl',['$scope', '$state', 'CheckInServ', 'localStorageService', '$interval', function($scope,$state,CheckInServ, localStorageService,$interval) {
	
	$scope.userName=localStorageService.get('userName');
	
	$scope.doLogout = function() {
		localStorageService.clearAll();
		CheckInServ.logOut(localStorageService.get('userCd'))
		    .then(function(response) {
		    var obj = JSON.parse(JSON.stringify(response.data));
		    	if(!("Session" in obj)) {
			      console.log(obj.Data);
			      $state.go('login');
				} else {
					$state.go('login');
				}		      
		    }, function (err) {
		      console.log(err);
		    })
	}
	
	$scope.startingFuncs = function() {
		$scope.getMenu();
    }
	
	$scope.getMenu = function() {
		CheckInServ.getMenu(localStorageService.get('userCd'))
		    .then(function(response) {
		    var obj = JSON.parse(JSON.stringify(response.data));
		    	if(!("Session" in obj)) {
			      console.log(obj.Data);	
			      $scope.menu=obj.Data;
				} else {
					$state.go('login');
				}		      
		    }, function (err) {
		      console.log(err);
		    })
	}
	
	
}]);