'use strict';
/**
 * @ngdoc function
 * @name CheckInApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the LoginCtrl
 */
var app=angular.module('CheckInApp');

app.controller('MonReqGraphCtrl',['$scope', '$state', 'CheckInServ', 'localStorageService', '$interval', function($scope,$state,CheckInServ, localStorageService,$interval) {
	
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

	$scope.getGraph = function() {
		//$scope.loader();
		$scope.userName=localStorageService.get('userName');
		CheckInServ.monReqGraph(localStorageService.get('userCd'))
		    .then(function(response) {
		    var obj = JSON.parse(JSON.stringify(response.data));
		    	if(!("Session" in obj)) {
				      console.log(obj.Data.count);
				      var dataValues=[];
				      var countArr=obj.Data.count.split("=");
				      var monthArr=obj.Data.month.split("=");
				      for(var i=0;i<countArr.length;i++) {
						countArr[i]=parseInt(countArr[i]);
					  }
					  for(var i=0;i<monthArr.length;i++) {
						var result = {};
						result['name'] = monthArr[i];
						result['y'] = countArr[i];
						dataValues.push(result);
					  }
				      $scope.drawGraph(dataValues);
				} else {
						$state.go('login');
				}		      
		    }, function (err) {
		      console.log(err);
		    })
    }
    
    $scope.startingFuncs = function() {
    	console.log("Graph");
		$scope.getGraph();
    }
    
    /*$scope.loader = function() {
	    $('.loader-item').fadeIn(''); // will first fade in the loading animation
    	$('#pageloader').fadeIn(''); // will fade in the white DIV that covers the website.
		$('.loader-item').fadeOut('slow'); // will first fade out the loading animation
       	$('#pageloader').delay(100).fadeOut('slow'); // will fade out the white DIV that covers the website.
	}*/
	
	$scope.drawGraph = function (obj) {
	    $('#container').highcharts({
	        chart: {
	            type: 'pie',
	            options3d: {
	                enabled: true,
	                alpha: 45
	            }
	        },
	        title: {
	            text: 'Month vs Requests Graph'
	        },
	        tooltip: {
            	pointFormat: 'Request(s): <b>{point.y}<b>'
        	},
	        plotOptions: {
	            pie: {
		            allowPointSelect: true,
	                innerSize: 100,
	                cursor: 'pointer',
                	depth: 45,
                	dataLabels: {
                   	enabled: true,
                    	format: '{point.name}'
                	}
	            }
	        },
	        series: [{
		        type: 'pie',
	            name: 'Request(s)',
	            data: obj
	        }]
	    });
	}
	
}]);