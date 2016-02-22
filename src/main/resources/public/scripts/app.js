var app = angular.module('companyapp', [ 'ngCookies', 'ngResource', 'ngSanitize',
		'ngRoute', "xeditable" ]);

app.config(function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'views/list.html',
		controller : 'ListCtrl'
	}).when('/companies/:companyID', {
		templateUrl : 'views/create.html',
		controller : 'EditCtrl'
	}).when('/create', {
		templateUrl : 'views/create.html',
		controller : 'CreateCtrl'
	}).otherwise({
		redirectTo : '/'
	})
});

app.controller('ListCtrl', function($scope, $http) {
	$http.get('/companies').success(function(data) {
		$scope.companies = data;
	}).error(function(data, status) {
		console.log('Error ' + data)
	})

/*	$scope.companyStatusChanged = function(todo) {
		console.log(todo);
		$http.put('/companies' + todo.id, todo).success(function(data) {
			console.log('status changed');
		}).error(function(data, status) {
			console.log('Error ' + data)
		})
	}
*/});

app.controller('CreateCtrl', function($scope, $http, $location) {
	$scope.company = {
		done : false
	};
	$scope.createCompany = function() {
		console.log($scope.company);
		$http.post('/companies', $scope.company).success(function(data) {
			$location.path('/');
		}).error(function(data, status) {
			console.log('Error ' + data)
		})
	}
});

app.controller('EditCtrl', [ '$scope', '$routeParams', '$http', '$location',
		function($scope, $routeParams, $http, $location) {

			$scope.createCompany = function() {
				console.log($scope.company);
				$http.put('/companies', $scope.company).success(function(data) {
					$location.path('/');
				}).error(function(data, status) {
					console.log('Error ' + data)
				})
			}

			companyID = $routeParams.companyID;
			$http.get('/companies/' + companyID).success(function(data) {
				$scope.company = data;
			});
		} ]);
