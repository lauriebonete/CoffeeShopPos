/**
 * Created by Laurie on 3/23/2016.
 */
angular.module("userProfileApp", ['loginApp'])
    .controller("userProfileController",['currentUserService','userProfileService','$scope',function(currentUserService,userProfileService,$scope){

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
                $scope.userRole = userProfileService.getUserRole(result.user);
            });

        };

        $scope.checkIfHasAccess = function(lookForThis){
            return $scope.accessList[lookForThis];
        };

        initUserAccess();
    }])
    .service("userProfileService",function($http){
        this.getUserRole = function(loggedUser){
            var userRole = [];
            if(loggedUser!=null &&
                loggedUser.userRole !=null){
                $.each(loggedUser.userRole, function(i,role){
                    userRole.push(role.roleName);
                });
            }

            return userRole;
        };
    });