/**
 * Created by Laurie on 1/18/2016.
 */
orderApp.directive("selectDefaultSize", function(){
    return function(scope, element, attrs){
        if(scope.$first){
            console.log(element, attrs);
            $(element).prop("checked",true).click();
            if($(element).is(":checked")){
                var price = attrs.price;
                var priceClass = $("#modifyOrder .price");
                $(priceClass).attr("data-list-id", attrs.listId);
                $(priceClass).attr("data-price",Number(price));
                $(priceClass).text(Number(price)+"php");
                $(priceClass).removeClass("hide");
            }

            scope.clickSize($.parseJSON(attrs.ingredient));
        }
    };
});