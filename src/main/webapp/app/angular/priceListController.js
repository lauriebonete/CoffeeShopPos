/**
 * Created by Laurie on 12/16/2015.
 */
crudApp.controller("priceListController", function ($scope, $http) {
    $scope.loadProducts = function(){
        $http.get("/product/findAll").then(function successCallback(response) {
            $scope.products = response.data.results;
        }, function errorCallback(response) {

        })
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

    $scope.productSelected = null;
});