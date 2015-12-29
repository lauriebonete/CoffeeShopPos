/**
 * Created by Laurie on 12/16/2015.
 */
crudApp.controller("priceSetController", function ($scope, $http) {
    $scope.loadProductGroup = function(data) {
        $scope.productGroupOption = data;
    };

    $scope.loadPromoGroup = function(data) {
        $scope.promoGroupOption = data;
    };

    $scope.loadProduct = function(data){
        $scope.productOption = data;
    }

    $scope.productConfig =
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
    };

    $scope.productGroup = 0;
    $scope.promoGroup = 0;
    $scope.product = 0;
});