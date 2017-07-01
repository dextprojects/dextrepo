'use strict';
/**
 * @ngdoc function
 * @name CheckInApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the LoginCtrl
 */
var app=angular.module('CheckInApp');

app.controller('NoticeCtrl',['$scope', '$state', 'CheckInServ', 'localStorageService', '$interval','REST_BASE_URL', function($scope,$state,CheckInServ, localStorageService,$interval,REST_BASE_URL) {
	
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
	
	$('#sandbox-container input').datepicker({
		format: 'dd/mm/yyyy',
		todayHighlight: true,
		autoclose: true,
    	clearBtn: true
	});
	
	$("#houseCD").select2({
	    minimumInputLength: 1,
	    ajax: {
	        url: REST_BASE_URL+"?option=getDirectHouse",
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
	
	$scope.saveNotice = function() {
		var flag=true;
		var msg='Error: ';
		if($scope.effDt==null || $scope.effDt==undefined || $scope.effDt=='') {
			flag=false;
			msg+='\nPlease Enter Effective Date.';
		}
		if($scope.subject==null || $scope.subject==undefined || $scope.subject=='') {
			flag=false;
			msg+='\nPlease Enter Subject.';
		}
		if($scope.description==null || $scope.description==undefined || $scope.description=='') {
			flag=false;
			msg+='\nPlease Enter Description.';
		}
		if(flag) {
			$scope.houseCD = ($scope.houseCD==undefined?'':$scope.houseCD);
			var obj1 = {"loginId": localStorageService.get('userCd')};
			obj1["effDt"]=$scope.effDt;
			obj1["toId"]=$scope.houseCD;
			obj1["notSubject"]=$scope.subject;
			obj1["notDetail"]=$scope.description;
			console.log(obj1);
			CheckInServ.saveNotice(obj1)
		    .then(function(response) {
		    var obj = JSON.parse(JSON.stringify(response.data));
		    	if(!("Session" in obj)) {
			      var Data = JSON.parse(JSON.stringify(obj.Data));
			      console.log(Data.status);
			      if(Data.status=='1') {
				      $(".alert-success").show().delay(1000).addClass("in").fadeOut(6000);
				      $scope.isSaving = false;
				      $scope.resetNotice();
				  } else {
				      $(".alert-danger").show().delay(1000).addClass("in").fadeOut(6000);
				  }
				} else {
					$state.go('login');
				}		      
		    }, function (err) {
		      console.log(err);
		    })
		} else {
			alert(msg)
		}
		
	}
	
	$scope.resetNotice = function() {
		$("#houseCD").select2('');	
		$scope.effDt='';
		$scope.houseCD='';
		$scope.subject='';
		$scope.description='';
	}
	
	
	$scope.getNotice = function() {
		console.log("-"+REST_BASE_URL);
		$scope.loader();
		$scope.userName=localStorageService.get('userName');
		CheckInServ.getNotices(localStorageService.get('userCd'))
		    .then(function(response) {
		    var obj = JSON.parse(JSON.stringify(response.data));
		    	if(!("Session" in obj)) {
			      $scope.allNotices=obj.Data;
				} else {
					$state.go('login');
				}		      
		    }, function (err) {
		      console.log(err);
		    })
    }
    
    $scope.startingFuncs = function() {
		$scope.getNotice();
    }
    
    $scope.loader = function() {
	    $('.loader-item').fadeIn(''); // will first fade in the loading animation
    	$('#pageloader').fadeIn(''); // will fade in the white DIV that covers the website.
		$('.loader-item').fadeOut('slow'); // will first fade out the loading animation
       	$('#pageloader').delay(100).fadeOut('slow'); // will fade out the white DIV that covers the website.
	}
}]);