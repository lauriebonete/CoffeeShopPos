/**
 * Created by Laurie on 1/24/2016.
 */
crudApp.controller("categoryController", function ($scope, $http) {

    $scope.loadCategory = function(data){
        $scope.category = data;
    };

    $scope.categoryConfig =
    {
        valueField : 'id',
        labelField : 'value',
        searchField: ['value'],
        delimiter : '|',
        placeholder : 'Pick something',
        onInitialize : function (selectize) {
            // receives the selectize object as an argument
        },
        maxItems:1,
    };

    $scope.categoryModel = 0;

});