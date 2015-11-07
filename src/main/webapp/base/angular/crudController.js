/**
 * Created by Laurie on 11/6/2015.
 */
crudApp.controller("crudController",function($scope, $http){
    $scope.loadDropDown = function(url) {
        $http.get(url).then(function successCallback(response){
            $scope.dropdown = response.data;
        }, function errorCallback(response) {

        })
    }

    $scope.loadTable = function(url) {
        $http.get(url).then(function successCallback(response){
            $scope.records = response.data.results;
        }, function errorCallback(response) {

        })
    }
});

