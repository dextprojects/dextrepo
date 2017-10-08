'use strict';
/**
 * @ngdoc function
 * @name EximApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the LoginCtrl
 */
var app=angular.module('EximApp');

app.controller('RequestsCtrl',['$scope', '$state', 'CheckInServ', 'localStorageService', '$interval', function($scope,$state,CheckInServ, localStorageService,$interval) {
	
	$scope.status=[{Name: "NEW"},{Name:"CHECKED"},{Name:"PROGRESS"},{Name:"DONE"}];
	$scope.remarkData=[];
	$scope.statusData=[];
	$scope.changedId="";
	$scope.selStatus="";
	$scope.userName="";
	$scope.notifCount = "";
	$scope.timeVal=10000;
	$scope.count=0;
	
	$scope.loader = function() {
	    $('.loader-item').fadeIn(''); // will first fade in the loading animation
    	$('#pageloader').fadeIn(''); // will fade in the white DIV that covers the website.
		$('.loader-item').fadeOut('slow'); // will first fade out the loading animation
       	$('#pageloader').delay(100).fadeOut('slow'); // will fade out the white DIV that covers the website.
	}
	
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
	
	$scope.notif = function() {
		var timer = $interval(
		$scope.notifStart,$scope.timeVal);
	}
	
	$scope.notifStart = function() {
		if(localStorageService.get('userCd')!=null) {
				CheckInServ.notifReq(localStorageService.get('userCd'))
			    .then(function(response) {
			      var obj = JSON.parse(JSON.stringify(response.data));
			    	if(!("Session" in obj)) {
				    	$scope.notifCount = obj.Data.NewReq;
				    	if($scope.notifCount==0) {
				    		$scope.notifCount=null;
				    		$("#element").tooltip().attr('data-original-title', "");
				    	} else {
					    	$("#element").tooltip().attr('data-original-title', $scope.notifCount+" New Request(s)");
					    }
			    	}
			    }, function (err) {
			      console.log(err);
			    })
			}
	}
	
	$scope.doLogout = function() {
		localStorageService.clearAll();
		$state.go('login');
	}
	
	$scope.getRequests = function() {
		$scope.loader();
		$scope.userName=localStorageService.get('userName');
		CheckInServ.getReq(localStorageService.get('userCd'))
		    .then(function(response) {
		    var obj = JSON.parse(JSON.stringify(response.data));
		    	if(!("Session" in obj)) {
			      console.log(obj);
			      $scope.allRequests=obj.Requests;
			      
				} else {
					$state.go('login');
				}		      
		    }, function (err) {
		      console.log(err);
		    })
    }
    
    $scope.startingFuncs = function() {
		//$scope.notifStart();
		//$scope.notif();
		$scope.getRequests();
    }
    
    $scope.setVal = function(obj, reqId) {
    	$scope.ModalReqId = reqId;
    	$scope.reqIdData = obj;
    }
    
    $scope.updateValue = function(reqId,reqSub,id,reqStatus) {
    	if(angular.isUndefined($scope.statusData[id])){
    		$scope.statusData[id] = reqStatus;
    	}
    	if(!angular.isUndefined($scope.statusData[id]) && !angular.isUndefined($scope.remarkData[id]) && $scope.statusData[id]!='') {
	    		$scope.isSaving = true;
	    		var obj = {compl_id: reqId, status: $scope.statusData[id], loginId: localStorageService.get('userCd'), subject: reqSub,remarks:$scope.remarkData[id]};
				CheckInServ.updateRequest(obj)
			    .then(function(response) {
			      var obj1 = JSON.parse(JSON.stringify(response.data));
			      if(!("Session" in obj1)) {
				      if("compl_id" in obj1) {
				      	$("#compId").text(obj1.compl_id);
				      	CheckInServ.getReq(localStorageService.get('userCd'))
					    .then(function(response) {
					      var obj = JSON.parse(JSON.stringify(response.data));
					      $scope.allRequests=obj.Requests;
					      $scope.statusData[id]="";
					      $scope.remarkData[id]="";
					      $scope.changedId=obj1.compl_id;
					      $(".alert-success").show().delay(1000).addClass("in").fadeOut(6000);
					      $scope.isSaving = false;
					      $scope.notifStart();
					    }, function (err) {
					      console.log(err);
					    })
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
    		alert("Please Select New Status and Enter Remark..!");
    	}
    }
}]);