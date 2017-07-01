angular
  .module('CheckInApp', 
  			['ngRoute',
  			'ui.router',
  			'oc.lazyLoad',
  			'LocalStorageModule',
  			'angular-loading-bar'])
  			
.constant("REST_BASE_URL","http://localhost:8080/CheckIn/CheckInWeb")
.constant("REST_BASE_URL_NOTIF","http://localhost:8080/CheckIn/NotificationServlet")
  			
.config(['$stateProvider','$urlRouterProvider','$ocLazyLoadProvider', 'localStorageServiceProvider',function ($stateProvider,$urlRouterProvider,$ocLazyLoadProvider,localStorageServiceProvider) {
	localStorageServiceProvider.setPrefix('CheckInApp');
	
	$ocLazyLoadProvider.config({
      debug:false,
      events:true,
    });
    
	$urlRouterProvider.otherwise('/login');

	$stateProvider
	.state('login',{
        url:'/login',
        controller: 'LoginCtrl',
        templateUrl:'Login.html',
        location: false,
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'CheckInApp',
              files:[
              'js/controller/LoginCtrl.js',
              'js/service/CheckInServ.js'
              ]
            }),
            $ocLazyLoad.load({
                name:'ngAnimate',
                files:['bower_components/angular-animate/angular-animate.js']
            })
          }
        }
      })
      
      /*.state('requests',{
        url:'/requests',
        controller: 'RequestsCtrl',
        templateUrl:'Requests.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'CheckInApp',
              files:[
              'js/controller/RequestsCtrl.js',
              'js/service/CheckInServ.js'
              ]
            }),
            $ocLazyLoad.load({
                name:'ngAnimate',
                files:['bower_components/angular-animate/angular-animate.js']
            })
          }
        }
      })*/
      
      .state('dashboard',{
        url:'/dashboard',
        controller: 'DashboardCtrl',
        templateUrl:'dashboard.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'CheckInApp',
              files:[
              'js/controller/DashboardCtrl.js',
              'js/service/CheckInServ.js'
              ]
            }),
            $ocLazyLoad.load({
                name:'ngAnimate',
                files:['bower_components/angular-animate/angular-animate.js']
            })
          }
        }
      })
      
      .state('dashboard.home',{
        url:'/home',
        templateUrl:'main.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'CheckInApp'
            }),
            $ocLazyLoad.load({
                name:'ngAnimate',
                files:['bower_components/angular-animate/angular-animate.js']
            })
          }
        }
      })
      
      .state('dashboard.requests',{
        url:'/requests',
        controller: 'RequestsCtrl',
        templateUrl:'Requests.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'CheckInApp',
              files:[
              'js/controller/RequestsCtrl.js',
              'js/service/CheckInServ.js'
              ]
            }),
            $ocLazyLoad.load({
                name:'ngAnimate',
                files:['bower_components/angular-animate/angular-animate.js']
            })
          }
        }
      })
      
      .state('dashboard.allUsers',{
        url:'/allUsers',
        controller: 'AllUsersCtrl',
        templateUrl:'AllUsers.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'CheckInApp',
              files:[
              	'js/controller/AllUsersCtrl.js',
              	'js/service/CheckInServ.js'
              ]
            }),
            $ocLazyLoad.load({
                name:'ngAnimate',
                files:['bower_components/angular-animate/angular-animate.js']
            })
          }
        }
      })
      
      .state('dashboard.allHouses',{
        url:'/allHouses',
        controller: 'AllHousesCtrl',
        templateUrl:'AllHouses.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'CheckInApp'
            }),
            $ocLazyLoad.load({
                name:'ngAnimate',
                files:['bower_components/angular-animate/angular-animate.js']
            })
          }
        }
      })
      
      .state('dashboard.notice',{
        url:'/NoticeBoard',
        controller: 'NoticeCtrl',
        templateUrl:'NoticeBoard.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'CheckInApp'
            }),
            $ocLazyLoad.load({
                name:'ngAnimate',
                files:['bower_components/angular-animate/angular-animate.js']
            })
          }
        }
      })
      
      .state('dashboard.scanBarcode',{
        url:'/scanBarcode',
        controller: 'ScanBarcodeCtrl',
        templateUrl:'ScanBarcode.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'CheckInApp',
              files:[
              	'js/controller/ScanBarcodeCtrl.js',
              	'js/service/CheckInServ.js'
              ]
            }),
            $ocLazyLoad.load({
                name:'ngAnimate',
                files:['bower_components/angular-animate/angular-animate.js']
            })
          }
        }
      })
      
      .state('dashboard.monthReqGraph',{
        url:'/MonthReqGraph',
        controller: 'MonReqGraphCtrl',
        templateUrl:'MonReqGraph.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'CheckInApp',
              files:[
              	'js/controller/MonReqGraphCtrl.js',
              	'js/service/CheckInServ.js'
              ]
            }),
            $ocLazyLoad.load({
                name:'ngAnimate',
                files:['bower_components/angular-animate/angular-animate.js']
            })
          }
        }
      })
      
}])