var crudApp = angular.module("crudApp", ['dropDownLoader']);



angular.module("dropDownLoader", [])
.factory('dropDownFactory',function($http){
        var load = function(url) {
            console.log(url);
            $http.jsonp(url)
                .success(function(data){
                    return data;
                });
        };

        return load;
    });