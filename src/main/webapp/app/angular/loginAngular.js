/**
 * Created by Laurie on 3/24/2016.
 */
angular.module("loginApp",[])
    .controller("loginController", ["$scope","loginService", function($scope,loginService){
        $scope.show = false;
        var url = function(){
            var paramList = loginService.getUrlPattern();
            var response = "";
            switch (paramList["login_response"]){
                case "access_denied":
                    response= "Please check your credentials.";
                        break;
                case "multiple_login":
                    response= "This account is already logged in.";
                        break;
                case "session_logout":
                    response="Your session ended. Please log-in again to continue.";
                        break;
                case "success_logout":
                    response="You have successfully logout.";
                    break;

            }
            $scope.response = response;
            $scope.status = paramList["error"];
            $scope.show = true;
        };

        url();
    }])
    .service("loginService",function(){
        this.getUrlPattern = function(){
            return evey.getUrlParams();
        }
    });