/**
 * Created by Laurie on 11/5/2015.
 */


var evey = (function () {

    return {
        getPath: function () {
            var path = window.location.pathname.replace(/\/$/, '');
            return window.location.protocol + '//' + window.location.host + path;
        },

        getHome: function () {
            return window.location.protocol;
        },

        getMapping: function () {
            return window.location.pathname.replace(/\/$/, '');
        },

        JSONnify: function (form) {
            var jsonObject = new Object();
            $.each($(form).find("input"), function (i, input) {
                if ($(input).is(":checkbox")) {
                    if ($(input).is(":checked")) {
                        jsonObject[$(input).attr("name")] = true;
                    } else {
                        jsonObject[$(input).attr("name")] = false;
                    }
                } else {
                    jsonObject[$(input).attr("name")] = $(input).val();
                }
            });

            $.each($(form).find("select"), function (i, select) {
                jsonObject[$(select).attr("name")] = $(select).val();
            });
            return jsonObject;
        },

        clearForm: function (form) {
            $.each($(form).find("input"), function (i, input) {
                if ($(input).is(":checkbox")) {
                    if (!$(input).is(":checked")) {
                        $(input).click();
                    }
                } else {
                    $(input).val("");
                }
            });

            $.each($(form).find("select"), function (i, select) {
                $(select).val($(select).find('option:first').val());
            });
        }
    }
})();

(function ($) {
    $.fn.EVEYfy = function (options) {

        var settings = $.extend({
            'search': '#crud-search',
            'search-action' : '#search-search-crud-btn',
            'form': '#crud-form',
            'view': '#view-body',
            'results': '#crud-result',
            'status': '.status',
            'pagination': '.pagination',
            'add': '#form-add-btn',
            'edit': '.edit-action',
            'display': '.display-action',
            'remove': '.delete-action',
            'saveCallback': null,
            'showFormCallback': null,
            'offCanvas': '.off-canvas-list',
            'mainBody': '.main-body',
            'updateForm': '#update-form',
            'clear' : '.clear-button'
        }, options);

        return this.each(function () {

            Object.getPrototypeOf(document.createComment('')).getAttribute = function() {}

            var crudForm = $(this).find(settings['form']);

            var updateForm = $(this).find(settings['updateForm'])

            var offCanvas = $(this).find(settings['offCanvas'])

            var mainBody = $(this).find(settings['mainBody']);

            var deleteAction = $(this).find(settings['remove']);

            var home = evey.getHome();

            $(crudForm).on("valid.fndtn.abide", function () {
                var path = evey.getPath();

                var jsonForm = evey.JSONnify(crudForm);
                $.ajax({
                    url: path,
                    type: "POST",
                    dataType: "JSON",
                    data: jsonForm,
                    success: function (data) {
                        $('#display-modal .message').html($(data).attr("message"));
                        if($(data).attr("status")) {
                            $('#display-modal').addClass("success");
                            evey.clearForm(crudForm);
                            angular.element(".main-body").scope().addEntityToRecords(data);
                            angular.element(".main-body").scope().$apply();
                            $('#crud-modal').foundation('reveal', 'close');
                        } else {
                            $('#display-modal').addClass("alert");
                        }

                        $('#display-modal').removeClass('hide');
                        $('#display-modal').delay(3500).queue(function () {
                            $(this).removeClass("success").removeClass("alert");
                            $(this).addClass("hide").dequeue();
                        });
                    }
                });
            });

            $(updateForm).on("valid.fndtn.abide", function () {
                var path = evey.getPath();

                var jsonForm = evey.JSONnify(updateForm);
                $.ajax({
                    url: path,
                    type: "POST",
                    dataType: "JSON",
                    data: jsonForm,
                    success: function (data) {
                        $('#display-modal .message').html($(data).attr("message"));
                        if($(data).attr("status")) {
                            $('#display-modal').addClass("success");
                            evey.clearForm(crudForm);
                            angular.element(".main-body").scope().addEntityToRecords(data);
                            angular.element(".main-body").scope().$apply();
                            $('#crud-modal').foundation('reveal', 'close');
                        } else {
                            $('#display-modal').addClass("alert");
                        }

                        $('#display-modal').removeClass('hide');
                        $('#display-modal').delay(3500).queue(function () {
                            $(this).removeClass("success").removeClass("alert");
                            $(this).addClass("hide").dequeue();
                        });
                    }
                });
            });

            $(this).on("click", settings['remove'], function () {
                var deleteId = $(this).data("id");
                $(".remove-record").on('click', function () {
                    angular.element(".main-body").scope().deleteAction(deleteId, evey.getMapping());
                    angular.element(".main-body").scope().$apply();
                });
            });

            $(this).on("click", settings['search-action'], function () {
               var jsonForm = evey.JSONnify($(this).parents("form"));
                var path = evey.getPath();
                $.ajax({
                    url: path+"/findEntity",
                    data: jsonForm,
                    type: "GET",
                    dataType:"JSON",
                    success: function(data) {
                        angular.element(".main-body").scope().searchEntity(data);
                        angular.element(".main-body").scope().$apply();
                    }
                })
            })

            $(this).on("click",settings["clear"], function(){
                evey.clearForm($(this).parents("form"));
            });
        });
    }
})(jQuery);

