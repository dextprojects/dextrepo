'use strict';
/**
 * @ngdoc function
 * @name EximApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the LoginCtrl
 */
var app=angular.module('EximApp');

app.controller('BuyerMstCtrl',['$scope', '$state', 'MasterServ', 'localStorageService', '$interval', function($scope,$state,CheckInServ, localStorageService,$interval) {
	
	$scope.userCd=localStorageService.get('userCd');
	$scope.value = "";
	$scope.master = {};
	$('#datetimepicker1').datetimepicker({
		format: 'DD/MM/YYYY'
	});
	$('#datetimepicker2').datetimepicker({
		format: 'DD/MM/YYYY',
		useCurrent: false
	});
	 $("#datetimepicker1").on("dp.change", function (e) {
         $('#datetimepicker2').data("DateTimePicker").minDate(e.date);
     });
     $("#datetimepicker2").on("dp.change", function (e) {
         $('#datetimepicker1').data("DateTimePicker").maxDate(e.date);
     });
	$("#EntryType").bootstrapSwitch({
	    onText: 'New',
	    offText: 'Modify'
    }).on('switchChange.bootstrapSwitch', function(event, state) {
	  	console.log(state);
	  	document.getElementById("buylist").disabled = state;
	  	if(state) {
		  	document.getElementById("buyername").style.display='block';
		  	document.getElementById("buyerNameDisp").style.display='none';
		} else {
			document.getElementById("buyername").style.display='none';
			document.getElementById("buyerNameDisp").style.display='block';
		}
	});
    
    $("#activeDeactive").bootstrapSwitch({
	    onText: 'Active',
	    offText: 'De-Active'
    }).on('switchChange.bootstrapSwitch', function(event, state) {
    	console.log(state);
	  	if(state)
		  	$scope.getBuyers("N");
		else
			$scope.getBuyers("Y");
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
    
	$scope.getBuyers = function(delFlag) {
		CheckInServ.getBuyers($scope.userCd,delFlag)
		    .then(function(response) {
		    var obj = JSON.parse(JSON.stringify(response.data));
		    	if(!("Session" in obj)) {
			      console.log(obj.Data);	
			      $scope.buyers=obj.Data;
				} else {
					$state.go('login');
				}		      
		    }, function (err) {
		      console.log(err);
		    })
	}
	
	$scope.getTraders = function() {
		CheckInServ.getTradersOfBuyers($scope.userCd)
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
	
	$(document.body).on("change","#buyerNameSel",function(){
		CheckInServ.getBuyerDetail($scope.userCd,this.value)
		    .then(function(response) {
		    var obj = JSON.parse(JSON.stringify(response.data));
		    	if(!("Session" in obj)) {
		        	
				} else {
					$state.go('login');
				}		      
		    }, function (err) {
		      console.log(err);
		    })
		
	});
	
	$scope.loader = function() {
	    $('.loader-item').fadeIn(''); // will first fade in the loading animation
    	$('#pageloader').fadeIn(''); // will fade in the white DIV that covers the website.
		$('.loader-item').fadeOut('slow'); // will first fade out the loading animation
       	$('#pageloader').delay(100).fadeOut('slow'); // will fade out the white DIV that covers the website.
	}
	
	$scope.reset = function() {
		console.log('---Reset---');	
        $scope.user = angular.copy($scope.master);
     }
}]);