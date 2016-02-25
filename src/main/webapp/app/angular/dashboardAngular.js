/**
 * Created by Laurie on 2/25/2016.
 */
angular.module("dashboardApp", ['loginApp'])
    .controller("dashboardController",['currentUserService','$scope',function(currentUserService,$scope){

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