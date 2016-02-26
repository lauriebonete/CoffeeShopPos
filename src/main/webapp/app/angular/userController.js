/**
 * Created by Laurie on 2/26/2016.
 */
crudApp.controller("userController", function($scope){
    $scope.userRoleConfig =
    {
        valueField : 'id',
        labelField : 'roleName',
        searchField: ['roleName'],
        delimiter : '|',
        placeholder : 'Pick something',
        plugins: ['remove_button'],
        onInitialize : function (selectize) {
            // receives the selectize object as an argument
        }
    };

    $scope.loadUserRole = function(data) {
        $scope.userRoleOption = data;
    };

    $scope.userRole = 0;
});