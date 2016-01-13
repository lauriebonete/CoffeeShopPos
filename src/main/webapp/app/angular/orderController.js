/**
 * Created by Laurie on 12/1/2015.
 */
orderApp.controller("orderController", function ($scope, $http) {
    $scope.loadSideNav = function(url){
        $http.get(url).then(function successCallback(response) {
            $scope.sideNav = response.data;
            getAddOn(response.data);
            $scope.loadProduct('/order/getAllProduct');
        }, function errorCallback(response) {

        });
    };

    var getAddOn = function(data){
        for(var i=0;i<= data.length-1; i++){
            if(data[i] != null &&
            data[i].productGroupName == "Add-Ons"){
                $scope.addOnGroup = data[i];
                break;
            }
        }
    };

    var displayFirstGroup = function() {
        if($scope.sideNav != null &&
            $scope.sideNav != undefined){
            $scope.changeGroup($scope.sideNav[0].id);
        }
    };

    $scope.loadProduct = function(url){
        $http.get(url).then(function successCallback(response) {
            $scope.product = response.data.results;
            $scope.loadAddOn(response.data.results);
            $scope.allProduct = response.data.all;
            displayFirstGroup();
        }, function errorCallback(response) {

        });
    };

    $scope.retrieveProduct = function(id){
        for(var i=0; i<=$scope.allProduct.length-1;i++){
            if($scope.allProduct[i].id == id){
                $scope.addToCart = $scope.allProduct[i];
                break;
            }
        }
    };

    $scope.loadAddOn = function(data){
        $scope.productAddOn = [];
        for(var i=0; i<=data.length-1;i++){
            if(data[i].productGroupList !=null){
                for(var j=0;j<=data[i].productGroupList.length-1;j++){
                    if($scope.addOnGroup.id == data[i].productGroupList[j].id){
                        $scope.productAddOn.push(data[i]);
                        break;
                    }
                }
            }
        }
    };

    $scope.changeGroup = function(id){
        $scope.productFound = [];
        angular.forEach($scope.product, function (value, key) {
            angular.forEach(value.productGroupList, function(group, groupKey){
                if(group.id == id){
                    $scope.productFound.push(value);
                }
            });
        });
    };

    $scope.selectProduct = function(id){
        angular.forEach($scope.product, function (value, key) {
            if(value.id == id){
                $scope.selectedProduct = value;
                if(value.productUnder != null &&
                    value.productUnder.length >0){
                    $scope.sizeSelection = true;
                } else {
                    $scope.sizeSelection = false;
                }
                return;
            }
        });
    };

    $scope.countOrdered = function(count){
        $scope.orderedCount = count;
    };

    $scope.setOrderedCount = function(){
        $scope.orderedCount = 0;
    };

    $scope.getPriceBySelected = function(id){
        if($scope.selectedProduct.price != null &&
            $scope.selectedProduct.price != undefined &&
            $scope.selectedProduct.price){
            for(var i= 0; i<=$scope.selectedProduct.price.length-1;i++){
                if($scope.selectedProduct.price[i].size.id == id){
                    $scope.price = $scope.selectedProduct.price[i].price;
                    break;
                }
            }
        }
    }
});