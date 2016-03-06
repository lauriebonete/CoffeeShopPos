/**
 * Created by Laurie on 3/6/2016.
 */
/**
 * Created by Laurie on 2/25/2016.
 */
angular.module("aboutApp", ['loginApp'])
    .controller("aboutController",['currentUserService','aboutService','$scope',function(currentUserService,aboutService,$scope){

        var initUserAccess = function(){
            $scope.accessList = [];
            var dataResponse = currentUserService.getLoggedUser();
            dataResponse.then(function(result){
                $scope.loggedUser = result.user;
                console.log($scope.loggedUser);
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

        $scope.getAboutDetails = function(){
            var results = aboutService.getProperties();
            results.then(function(result){
                $scope.about = result;
            });
        };

        initUserAccess();
    }])
    .service("aboutService",function($http){
        this.getProperties = function(){
            return $http.get("/about/get-properties").then(function successCallback(response){
                return response.data;
            }, function errorCallback(response){

            });
        };
    });