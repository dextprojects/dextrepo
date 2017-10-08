'use strict';
/**
 * @ngdoc function
 * @name EximApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the LoginCtrl
 */
var app=angular.module('EximApp');

app.controller('LoginCtrl', ['$scope', '$state', 'CheckInServ','localStorageService',function($scope,$state,CheckInServ, localStorageService) {
	
	$scope.submit = function() {
		CheckInServ.checkLogin($scope.userID,$scope.password)
		    .then(function(response) {
		      var obj = JSON.parse(JSON.stringify(response.data));
		      console.log("---"+JSON.stringify(response.data));
		      if(obj.loginSuccess=='Y') {
		      	console.log("---"+obj.empName);
		      	localStorageService.set('userName', obj.empName);
		      	localStorageService.set('userCd', obj.usercd);
		        $state.go('dashboard.home');
		      } else {
		        $scope.status="Email/Password is incorrect.."
		      }
		    }, function (err) {
		      console.log(err);
		    })
    }
    
    $scope.checkEnter = function (e) { 
		var characterCode; 
	
		if(e && e.which){ 
			e = e
			characterCode = e.which 
		} else{
			e = event
			characterCode = e.keyCode 
		}
			
		if(characterCode == 13){ 
			$scope.submit();
			return false; 
		} else{
			return true; 
		}
	}

}])
