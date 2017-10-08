angular
  .module('EximApp', 
  			['ngRoute',
  			'ui.router',
  			'oc.lazyLoad',
  			'LocalStorageModule',
  			'angular-loading-bar'])
  			
.constant("REST_BASE_URL","http://localhost:8080/Exim2016/EximGateway")
  			
.config(['$stateProvider','$urlRouterProvider','$ocLazyLoadProvider', 'localStorageServiceProvider',function ($stateProvider,$urlRouterProvider,$ocLazyLoadProvider,localStorageServiceProvider) {
	localStorageServiceProvider.setPrefix('EximApp');
	
	$ocLazyLoadProvider.config({
      debug:false,
      events:true,
    });
    
	$urlRouterProvider.otherwise('/login');

	$stateProvider
	.state('login',{
        url:'/login',
        controller: 'LoginCtrl',
        templateUrl:'views/pages/Login.html',
        location: false,
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'EximApp'
            }),
            $ocLazyLoad.load({
                name:'ngAnimate',
                files:['bower_components/angular-animate/angular-animate.js']
            })
          }
        }
      })
      
      .state('dashboard',{
        url:'/dashboard',
        controller: 'DashboardCtrl',
        templateUrl:'views/dashboard/dashboard.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'EximApp'
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
        templateUrl:'views/dashboard/main.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'EximApp'
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
        templateUrl:'views/pages/Requests.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'EximApp'
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
        templateUrl:'views/pages/AllUsers.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'EximApp'
            }),
            $ocLazyLoad.load({
                name:'ngAnimate',
                files:['bower_components/angular-animate/angular-animate.js']
            })
          }
        }
      })
      
      .state('dashboard.BuyerMaster',{
        url:'/BuyerMaster',
        controller: 'BuyerMstCtrl',
        templateUrl:'views/master/BuyerMaster.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'EximApp'
            }),
            $ocLazyLoad.load({
                name:'ngAnimate',
                files:['bower_components/angular-animate/angular-animate.js']
            })
          }
        }
      })
      
      .state('dashboard.SupplierMaster',{
        url:'/SupplierMaster',
        controller: 'SupplierMstCtrl',
        templateUrl:'views/master/SupplierMaster.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'EximApp'
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
        templateUrl:'views/pages/ScanBarcode.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'EximApp'
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
        templateUrl:'views/pages/MonReqGraph.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'EximApp'
            }),
            $ocLazyLoad.load({
                name:'ngAnimate',
                files:['bower_components/angular-animate/angular-animate.js']
            })
          }
        }
      })
      
}])