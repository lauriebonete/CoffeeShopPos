/**
 * Created by Laurie on 11/6/2015.
 */
crudApp.controller("crudController",['$scope','$timeout','$http','currentUserService',function ($scope,$timeout, $http, currentUserService) {


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
            console.log(response.data);
        }, function errorCallback(response) {

        })
    };

    $scope.loadTable = function (data) {
        $scope.fullRecords = data.completeList;
        $scope.records = data.slice;
        $scope.listSize = data.completeList.length;
        $scope.startIndex = 1;
        $scope.maxItem = data.slice.length;
        $scope.tableLimit = data.slice.length;
        $scope.numberOfPage = data.numberOfPage;
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
        editFunction(id);
    };

    function editFunction (id){
        angular.forEach($scope.records, function (value, key) {
            if (value.id == id) {
                console.log(value);
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
        $scope.fullRecords.push(data);
        $scope.listSize++;

        if(isNewPageNeeded($scope.fullRecords)){
            var page= $scope.numberOfPage+1;
            var newPage = $('<li>').append($('<a class="pages" data-page='+page+' data-max='+$scope.tableLimit+'>').text(page));
            $(newPage).insertBefore($("ul.pagination .arrow.next"));
        }
    };

    var isNewPageNeeded = function(fullRecords){
        var numberOfPage = fullRecords.length/$scope.tableLimit;
        numberOfPage = Math.ceil(numberOfPage);
        console.log(numberOfPage,$scope.numberOfPage);
        if($scope.numberOfPage<numberOfPage){
            return true;
        }
        return false;
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

    $scope.updateStock = function(data) {
        for(var i=0; i<=$scope.fullRecords.length-1;i++){
            if($scope.fullRecords[i].item.id==data.item.id){
                $scope.fullRecords[i] = data;
                break;
            }
        }

        for(var i=0; i<=$scope.records.length-1;i++){
            if($scope.records[i].item.id==data.item.id){
                $scope.records[i] = data;
                break;
            }
        }
    };

    $scope.deleteAction = function (id, url) {
        $("#delete-modal .loader").toggleClass("hide");
        $("#delete-modal span.delete-btn").toggleClass("hide");
        $("#delete-modal .button").toggleClass("disabled");
        $("#delete-modal .button").attr("disabled", true);
        var deleteEntity = {
            method: "DELETE",
            url: url + "/" + id
        };

        $http(deleteEntity).then(function successCallback(response) {
            $("#delete-modal .loader").toggleClass("hide");
            $("#delete-modal span.delete-btn").toggleClass("hide");
            $("#delete-modal .button").toggleClass("disabled");
            $("#delete-modal .button").removeAttr("disabled");
            if(response.data.status){
                evey.promptSuccess(response.data.message);

                $scope.records = $.grep($scope.records, function(value) {
                    return value.id != id;
                });

                $scope.fullRecords = $.grep($scope.fullRecords, function(value) {
                    return value.id != id;
                });
            } else {
                evey.promptAlert(response.data.message);
            }
            $('#delete-modal').foundation('reveal', 'close');
            /*$scope.records.splice($.inArray(id, $scope.records), 1);
            $scope.fullRecords.splice($.inArray(id, $scope.fullRecords), 1);*/
        }, function errorCallback(response) {
            $("#delete-modal .loader").toggleClass("hide");
            $("#delete-modal span.delete-btn").toggleClass("hide");
            $("#delete-modal .button").toggleClass("disabled");
            $("#delete-modal .button").removeAttr("disabled");
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

    var initUserAccess = function(){
        $scope.accessList = [];
        var dataResponse = currentUserService.getLoggedUser();
        dataResponse.then(function(result){
            $scope.loggedUser = result.user;
            var accessMap = {};
            $.each(currentUserService.getAllAccessOfUser(result.user),function(i,val){
                accessMap[val] = true;
            });
            accessMap['allow'] = true;

            $scope.accessList = accessMap;
        });

    };

    $scope.checkIfHasAccess = function(lookForThis){
        return $scope.accessList[lookForThis];
    };

    initUserAccess();
}]);


