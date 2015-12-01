/**
 * Created by Laurie on 12/1/2015.
 */
orderApp.controller("orderController", function ($scope, $http) {
    $scope.loadSideNav = function(url){
        console.log(url);
        $http.get(url).then(function successCallback(response) {
            $scope.sideNav = response.data;
        }, function errorCallback(response) {

        });
    }
});