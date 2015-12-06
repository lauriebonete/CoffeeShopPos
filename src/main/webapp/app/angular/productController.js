/**
 * Created by Laurie on 12/6/2015.
 */
crudApp.controller("productController", function ($scope, $http) {
    $scope.loadSizes = function(data){
        $scope.size = data;
    };
});