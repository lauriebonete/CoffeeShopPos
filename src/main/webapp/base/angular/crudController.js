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

    $scope.loadTable = function(data) {
        $scope.fullRecords = data.completeList;
        $scope.records = data.slice;
    }

    $scope.changePage = function(page, max){
        var end = (page*max)-1;
        var start = (page*max)-max;
        $scope.records = $scope.fullRecords.slice(start,end);
    }

   $scope.editAction = function(id) {
       angular.forEach($scope.records,function(value,key){
           if(value.id == id) {
               $scope.recordFound = value;
               return;
           }
       });
    }

    $scope.addAction = function(newEntity) {
        $scope.records = $scope.records.concat(newEntity);
    }

    $scope.searchEntity = function(data) {
        $scope.records = data.results;
    }

    $scope.deleteAction = function(id,url) {
        var deleteEntity = {
            method: "DELETE",
            url: url+"/"+id
        };

        $http(deleteEntity).then(function successCallback(response){
            $('#delete-modal').foundation('reveal', 'close');
            $scope.records.splice( $.inArray(id,$scope.records) ,1 );

        }, function errorCallback(response) {
            console.log("here");
        })
    }

    $scope.feedDropdown = function(data) {
        $scope.dropdown = data;
    }
});


