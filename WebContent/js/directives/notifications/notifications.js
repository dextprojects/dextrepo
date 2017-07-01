'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */
angular.module('CheckInApp')
	.directive('notifications',function(){
		return {
        templateUrl:'js/directives/notifications/notifications.html',
        restrict: 'E',
        replace: true,
    	}
	});


