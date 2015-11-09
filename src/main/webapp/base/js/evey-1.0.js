/**
 * Created by Laurie on 11/5/2015.
 */


var evey = (function(){

    return {
        getPath : function() {
            var path = window.location.pathname.replace(/\/$/, '');
            return window.location.protocol + '//' + window.location.host + path;
        },

        getHome : function() {
            return window.location.protocol;
        },

        getMapping : function(){
            return window.location.pathname.replace(/\/$/, '');
        },

        JSONnify : function(form) {
            var jsonObject = new Object();
            console.log($(form).find("input"));
            $.each($(form).find("input"),function(i,input){
                if($(input).is(":checkbox")) {
                    if($(input).val() == "on") {
                        jsonObject[$(input).attr("name")] = true;
                    } else {
                        jsonObject[$(input).attr("name")] = false;
                    }
                } else {
                    jsonObject[$(input).attr("name")] = $(input).val();
                }
            });

            $.each($(form).find("select"),function(i,select){
                jsonObject[$(select).attr("name")] = $(select).val();
            });
            return jsonObject;
        }
    }
})();

(function($){
    $.fn.EVEYfy = function(options) {

        var settings = $.extend({
            'search'     : '#crud-search',
            'form'       : '#crud-form',
            'view'       : '#view-body',
            'results'    : '#crud-result',
            'status'     : '.status',
            'pagination' : '.pagination',
            'add'        : '#form-add-btn',
            'edit'       : '.edit-action',
            'display'    : '.display-action',
            'remove'     : '.delete-action',
            'saveCallback' : null,
            'showFormCallback' : null,
            'offCanvas'    : '.off-canvas-list',
            'mainBody' : '.main-body',
            'updateForm' : '#update-form'
        }, options);

        return this.each(function(){

            var crudForm = $(this).find(settings['form']);

            var updateForm = $(this).find(settings['updateForm'])

            var offCanvas = $(this).find(settings['offCanvas'])

            var mainBody = $(this).find(settings['mainBody']);

            var deleteAction = $(this).find(settings['remove']);

            var home = evey.getHome();

            $.each($(offCanvas).find("li a.canvas"),function(i, a){
                var $reference = $(a).data("mapping");
                $(a).on("click", function(){
                    $.ajax({
                        url: home+$reference,
                        type: "GET",
                        success: function(data){
                            window.location = data;
                        }
                    })
                    });
            });

            $(crudForm).on("valid.fndtn.abide",function(){
                var path = evey.getPath();

                var jsonForm = evey.JSONnify(crudForm);
                $.ajax({
                    url: path,
                    type: "POST",
                    dataType: "JSON",
                    data: jsonForm,
                    success : function(data) {
                        console.log(data);
                    }
                });
            });

            $(updateForm).on("valid.fndtn.abide",function(){
                var path = evey.getPath();

                var jsonForm = evey.JSONnify(updateForm);
                $.ajax({
                    url: path,
                    type: "POST",
                    dataType: "JSON",
                    data: jsonForm,
                    success : function(data) {
                        console.log(data);
                    }
                });
            });

            $(this).on("click", settings['remove'], function(){
                var deleteId = $(this).data("id");
                $(".remove-record").on('click',function(){
                    angular.element(".main-body").scope().deleteAction(deleteId,evey.getMapping());
                    angular.element(".main-body").scope().$apply();
                });
            });

        });
    }
})(jQuery);

