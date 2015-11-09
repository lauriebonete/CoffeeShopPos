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

   $scope.editAction = function(id) {
       angular.forEach($scope.records,function(value,key){
           if(value.id == id) {
               $scope.recordFound = value;
               return;
           }
       });
    }

    $scope.deleteAction = function(id,url) {
        var deleteEntity = {
            method: "DELETE",
            url: url+"/"+id
        };

        $http(deleteEntity).then(function successCallback(response){
            $('#delete-modal').foundation('reveal', 'close');
        }, function errorCallback(response) {

        })
    }
});


