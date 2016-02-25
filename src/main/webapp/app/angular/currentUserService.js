/**
 * Created by Laurie on 2/24/2016.
 */
var loginApp = angular.module("loginApp",[]);
loginApp.service("currentUserService", function($http){

    this.getAllAccessOfUser = function(loggedUser){
        var accessRight = [];
        if(loggedUser!=null &&
            loggedUser.userRole !=null){
            $.each(loggedUser.userRole, function(i,role){
                if(role.authorities != null &
                    role.authorities.length>0){
                    $.each(role.authorities,function(i,authority){
                        accessRight.push(authority.access);
                    });
                }
            });
        }
        return accessRight;
    };

    this.getLoggedUser = function(){
        return $http.get("/login/get-logged-user").then(function successCallback(response){
            return response.data;
        }, function errorCallback(response){

        });
    };
});