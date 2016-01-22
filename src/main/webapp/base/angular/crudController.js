/**
 * Created by Laurie on 11/6/2015.
 */
crudApp.controller("crudController", function ($scope, $http) {


    $scope.cacheEntities = function(url){
        $scope.entities = [];

        var urls = url.split(",");
        $.each(urls, function(i, urlParse){
            $http.get(urlParse).then(function successCallback(response){
                $scope.entities.push(response.data);
            })
        });
    };

    $scope.loadDropDown = function (url) {
        $http.get(url).then(function successCallback(response) {
            $scope.dropdown = response.data;
        }, function errorCallback(response) {

        })
    };

    $scope.loadTable = function (data) {
        $scope.fullRecords = data.completeList;
        $scope.records = data.slice;
        $scope.listSize = data.completeList.length;
        $scope.startIndex = 1;
        $scope.maxItem = data.slice.length;
    };

    $scope.changePage = function (page, max) {
        var end = (page * max) - 1;
        var start = (page * max) - max;
        $scope.records = $scope.fullRecords.slice(start, end+1);
        $scope.startIndex = start + 1;
        if ($scope.fullRecords.length < end + 1) {
            $scope.maxItem = $scope.fullRecords.length;
        } else {
            $scope.maxItem = end + 1;
        }
    };

    $scope.editAction = function (id) {
        angular.forEach($scope.records, function (value, key) {
            if (value.id == id) {
                $scope.recordFound = value;
                return;
            }
        });
    };

    $scope.restock = function(id){
        angular.forEach($scope.records, function (value, key) {
            if (value.item.id == id) {
                $scope.stock = value;
                return;
            }
        });
    };

    $scope.addAction = function (newEntity) {
        $scope.records = $scope.records.concat(newEntity);
    };

    $scope.searchEntity = function (data) {
        $scope.records.push(data);
    };

    $scope.updateEntity = function(data) {
        for(var i=0; i<=$scope.fullRecords.length-1;i++){
            if($scope.fullRecords[i].id==data.id){
                $scope.fullRecords[i] = data;
            }
        }

        for(var i=0; i<=$scope.records.length-1;i++){
            if($scope.records[i].id==data.id){
                $scope.records[i] = data;
            }
        }
    };

    $scope.deleteAction = function (id, url) {
        var deleteEntity = {
            method: "DELETE",
            url: url + "/" + id
        };

        $http(deleteEntity).then(function successCallback(response) {
            $('#delete-modal').foundation('reveal', 'close');
            $scope.records.splice($.inArray(id, $scope.records), 1);

        }, function errorCallback(response) {
        })
    };

    $scope.feedDropdown = function (data) {
        $scope.dropdown = data;
    };

    $scope.selectizeConfig =
    {
        valueField : 'id',
        labelField : 'value',
        searchField: ['value'],
        delimiter : '|',
        placeholder : 'Pick something',
        plugins: ['remove_button'],
        onInitialize : function (selectize) {
            // receives the selectize object as an argument
        }
    };

});


