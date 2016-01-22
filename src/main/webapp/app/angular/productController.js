/**
 * Created by Laurie on 12/6/2015.
 */
crudApp.controller("productController", function ($scope, $http) {
    $scope.loadSizes = function(data){
        $scope.size = data;
    };

    $scope.loadCategory = function(data){
        $scope.category = data;
    };

    $scope.productsConfig =
    {
        valueField : 'id',
        labelField : 'productName',
        searchField: ['productName'],
        delimiter : '|',
        placeholder : 'Pick something',
        plugins: ['remove_button'],
        onInitialize : function (selectize) {
            // receives the selectize object as an argument
        },
        maxItems:1,
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

    $scope.productGroupConfig =
    {
        valueField : 'id',
        labelField : 'productGroupName',
        searchField: ['productGroupName'],
        delimiter : '|',
        placeholder : 'Pick something',
        plugins: ['remove_button'],
        onInitialize : function (selectize) {
            // receives the selectize object as an argument
        },
    };

    $scope.loadProductGroup = function(data) {
        $scope.productGroupOption = data;
    };

    $scope.loadPromoGroup = function(data) {
        $scope.promoGroupOption = data;
    };

    $scope.productGroup = 0;
    $scope.promoGroup = 0;
});