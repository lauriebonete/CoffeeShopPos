/**
 * Created by Laurie on 11/6/2015.
 */
crudApp.controller("crudController", function ($scope, $http) {
    $scope.loadDropDown = function (url) {
        $http.get(url).then(function successCallback(response) {
            $scope.dropdown = response.data;
        }, function errorCallback(response) {

        })
    }

    $scope.loadTable = function (url) {
        $http.get(url).then(function successCallback(response) {
            $scope.records = response.data.results;
        }, function errorCallback(response) {

        });
    }

    $scope.editAction = function (id) {
        angular.forEach($scope.records, function (value, key) {
            if (value.id == id) {
                $scope.recordFound = value;
            }
        });
    }

    $scope.editClear = function() {
        $scope.recordFound = null;
    }

    $scope.loadGroup = function(url,dom) {
        $http.get("/reference/findReferenceLookUpByCategory/"+url).then(function successCallback(data) {
            var option = [];

            $.each($(data).attr("data"),function(i,val){
                option.push({value: $(val).attr("value"), id: $(val).attr("id")});
            });

            $(dom).selectize({
                persist: false,
                maxItems: null,
                valueField: 'id',
                labelField: 'value',
                searchField: ['value'],
                options: option
            });
        }, function errorCallback(response) {

        });
    }

    $scope.deleteAction = function (id, url) {
        var deleteEntity = {
            method: "DELETE",
            url: url + "/" + id
        };

        $http(deleteEntity).then(function successCallback(response) {
            remover($scope.records, response.data.remove);
            $('#delete-modal').foundation('reveal', 'close');
        }, function errorCallback(response) {

        });
    }

    $scope.addEntityToRecords = function(data) {
        $scope.records.push(data.record);
    }

    $scope.searchEntity = function(response) {
        $scope.records = null;
        $scope.records = response.records;
    }

    var remover = function (list, id) {

        var found = null;
        angular.forEach(list, function (value, key) {
            if (value.id == id) {
                found = key;
            }
        });
        if (found != null) {
            list.splice(found, 1);
        }
    }

});


