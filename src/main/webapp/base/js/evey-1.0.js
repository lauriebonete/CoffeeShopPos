/**
 * Created by Laurie on 11/5/2015.
 */


var evey = (function(){

    return {
        getPath : function() {
            var path = window.location.pathname.replace(/\/$/, '');
            return window.location.protocol + '//' + window.location.host + path;
        },

        getUrlParams : function(){
            var path = window.location.search;
            if(path.indexOf("?")>=0){
                var urlParams = path.substring(path.indexOf("?")+1);
                var paramList = urlParams.split("&");
                var params = {};
                if(paramList!=undefined
                    && paramList!=null){
                    $.each(paramList,function(i,val){
                        var splitted = val.split("=");
                        params[splitted[0]] = splitted[1];
                    });
                }
                return params;
            }
        },

        getDate : function(){
            var d = new Date();

            var month = d.getMonth()+1;
            var day = d.getDate();
            var hours = d.getHours();

            var time = (hours>12 ? hours-12:hours) + ":" + d.getMinutes() + ":" + d.getSeconds() + (hours>12? " PM":" AM");

            var dateDisplay = (month<10 ? '0' : '') + month + '/' +
                (day<10 ? '0' : '') + day + '/' +
                d.getFullYear() + " " + time;

            return dateDisplay;
        },

        getHome : function() {
            return window.location.protocol;
        },

        closeParentModal : function(parent){
            $(parent).foundation('reveal', 'close');
        },

        getMapping : function(){
            return window.location.pathname.replace(/\/$/, '');
        },

        promptSuccess : function(message) {
            toastr.options.positionClass = 'toast-bottom-full-width';
            toastr.options.closeMethod = 'fadeOut';
            toastr.options.closeDuration = 200;
            toastr.options.closeEasing = 'swing';
            toastr.success(message);

        },

        promptAlert : function(message) {
            toastr.options.positionClass = 'toast-bottom-full-width';
            toastr.options.closeMethod = 'fadeOut';
            toastr.options.closeDuration = 200;
            toastr.options.closeEasing = 'swing';
            toastr.error(message);
        },

        JSONnify : function(form) {
            var jsonObject = new Object();
            $.each($(form).find("input"),function(i,input){
                if($(input).attr("name") != null &&
                    $(input).attr("name") != undefined &&
                    $(input).attr("name") != "" &&
                    !$(input).is(":disabled") &&
                    $(input).val() != null &&
                    $(input).val() != undefined) {
                    if($(input).parent("div.selectize-input").length<=0) {
                        if($(input).attr("name").indexOf (".")!= -1) {
                            var dottedName = $(input).attr("name").split(".");
                            var object = new Object();

                            if(jsonObject[dottedName[0]] != null){
                                object = jsonObject[dottedName[0]];
                            }

                            if($(input).is(":checkbox")) {
                                if($(input).is(":checked")) {
                                    object[dottedName[1]] = true;
                                } else {
                                    object[dottedName[1]] = false;
                                }
                            } else {
                                object[dottedName[1]] = $(input).val();
                            }
                            jsonObject[dottedName[0]] = object;

                        } else {
                            if($(input).is(":checkbox")) {
                                if($(input).is(":checked")) {
                                    jsonObject[$(input).attr("name")] = true;
                                } else {
                                    jsonObject[$(input).attr("name")] = false;
                                }
                            } else {
                                jsonObject[$(input).attr("name")] = $(input).val();
                            }
                        }
                    }
                }
            });

            $.each($(form).find(".list"),function(i,elem){

                if($(elem).attr("name")!=null &&
                    $(elem).attr("name")!=undefined &&
                    $(elem).attr("name")!= ""){

                    var list = [];
                    $.each($(elem).find("span.list-item"),function(j,item){
                        list.push($(item).text());
                    });
                    jsonObject[$(elem).attr("name")] = list;
                }
            });

            $.each($(form).find("selectize"), function (i,selectize) {
                if($(selectize).attr("name")!= null &&
                    $(selectize).attr("name")!= undefined &&
                    $(selectize).attr("name")!= "" &&
                    !$(selectize).is(":disabled") &&
                    $(selectize).val() != null &&
                    $(selectize).val() != "") {

                    if($(selectize).data("list") != null && $(selectize).data("list") != undefined && !$(selectize).data("list")){
                        var object = new Object();
                        object[$(selectize).data("attr")] = $(selectize).val();
                        jsonObject[$(selectize).attr("name")] = object;
                    } else {
                        var list = [];
                        var values = $(selectize).val().split("|");
                        for (var i = 0; i <= values.length - 1; i++) {
                            var object = new Object();
                            if(values[i] != null &&
                                values[i] != "" &&
                                values[i] != undefined &&
                                values[i].length > 0) {
                                object[$(selectize).data("attr")] = values[i];
                                list.push(object);
                            }
                        }
                        jsonObject[$(selectize).attr("name")] = list;
                    }
                }
            });

            $.each($(form).find("select"),function(i,select){
                if($(select).attr("name") != null &&
                    $(select).attr("name") != undefined &&
                    $(select).attr("name") != "" &&
                    !$(select).is(":disabled") &&
                    $(select).val() != "" &&
                    $(select).val() != null &&
                    $(select).val() != undefined) {

                    if($(select).val()!=null &&
                        $(select).val()!=undefined) {
                        if($(select).attr("name").indexOf (".")!= -1) {
                            var dottedName = $(select).attr("name").split(".");
                            var object = new Object();

                            if(jsonObject[dottedName[0]] != null){
                                object = jsonObject[dottedName[0]];
                            }

                            if($(select).val().indexOf(":")!=-1){
                                object[dottedName[1]] = $(select).val().substring($(select).val().indexOf(":")+1);
                            } else {
                                object[dottedName[1]] = $(select).val();
                            }


                            if($(select).data("list") != null && $(select).data("list") != undefined && !$(select).data("list")){
                                jsonObject[dottedName[0]] = object;
                            } else {
                                var list = [];
                                list.push(object);
                                jsonObject[dottedName[0]]  = list;
                            }

                        } else {
                            if($(select).val().indexOf(":")!=-1){
                                jsonObject[$(select).attr("name")] = $(select).val().substring($(select).val().indexOf(":")+1);
                            } else {
                                jsonObject[$(select).attr("name")] = $(select).val();
                            }
                        }
                    }
                }
            });

            console.log(JSON.stringify(jsonObject));
            return JSON.stringify(jsonObject);
        },

        clearForm: function (form) {
            $.each($(form).find("input"), function (i, input) {
                if ($(input).is(":checkbox")) {
                    if (!$(input).is(":checked")) {
                        $(input).click();
                    }
                } else if ($(input).attr("name")!="isBypassUnique"){
                   $(input).val("");
                }
            });

            $.each($(form).find("select"), function (i, select) {
                $(select).val($(select).find('option:first').val());
            });
        },

        paginatePage: function(data) {
            var numberOfPage = data.size/data.listSize;
            numberOfPage = Math.ceil(numberOfPage);

            var json = new Object();
            json["numberOfPage"] = numberOfPage;
            json["completeList"] = data.results;
            json["slice"] = data.results.slice(0,data.listSize);
            json["maxItem"] = data.listSize;
            return json;
        },

        sanitizeErrorContainer: function(form){
            $.each($(form).find("small.error"),function(i,small){
                var defaultError = $(small).attr("data-default-error");
                $(small).text(defaultError);
                $(small).removeAttr("data-default-error");
                $(small).parent().removeClass("error");
            });
        }
    }
})();

(function($){
    $.fn.EVEYfy = function(options) {

        var settings = $.extend({
            'search'     : '#crud-x',
            'form'       : '#crud-form',
            'view'       : '#view-body',
            'results'    : '#crud-result',
            'status'     : '.status',
            'pagination' : '.pagination',
            'add'        : '#form-add-btn',
            'update'    : '#form-update-btn',
            'edit'       : '.edit-action',
            'display'    : '.display-action',
            'remove'     : '.delete-action',
            'saveCallback' : null,
            'showFormCallback' : null,
            'offCanvas'    : '.off-canvas-list',
            'mainBody' : '.main-body',
            'updateForm' : '#update-form',
            'search-action': '#search-search-crud-btn',
            'pagination': '.pagination',
            'crud-table': '#crud-table',
            'search-clear': "#search-clr-crud-btn",
            'close-parent-modal': '.close-parent-modal',
            'ajax-loader': '.ajax-loader',
            'clear-form': '#form-clr-btn',
            'clear-update': '#update-clr-btn',
            'crud-add-button': "#crud-add-button"
        }, options);

        return this.each(function(){

            Object.getPrototypeOf(document.createComment('')).getAttribute = function() {}

            var crudForm = $(this).find(settings['form']);
            var updateForm = $(this).find(settings['updateForm']);
            var offCanvas = $(this).find(settings['offCanvas']);
            var mainBody = $(this).find(settings['mainBody']);
            var closeModal = $(this).find(settings["close-parent-modal"]);
            var add = $(this).find(settings["add"]);
            var crudAdd = $(this).find(settings["crud-add-button"]);
            var update = $(this).find(settings["update"]);
            var clearForm = $(this).find(settings['clear-form']);
            var clearFormUpdate = $(this).find(settings['clear-update']);

            $(clearForm).on("click",function(){
                evey.clearForm($(crudForm));
                evey.clearForm($(updateForm));
            });

            $(clearFormUpdate).on("click",function(){
                evey.clearForm($(updateForm));
            });

            $(add).on("click",function(){
                evey.sanitizeErrorContainer(crudForm);
            });

            $(crudAdd).on("click",function(){
                evey.sanitizeErrorContainer(crudForm);
            });

            $(update).on("click",function(){
                evey.sanitizeErrorContainer(updateForm);
            });

            $(crudForm).on("valid.fndtn.abide",function(){

                var crudForm = $(this);
                $(crudForm).find("i.loader").toggleClass("hide");
                $(crudForm).find("span.btn-label").toggleClass("hide");

                $(crudForm).find(".button").toggleClass("disabled");
                $(crudForm).find(".button").attr("disabled",true);

                var path = evey.getPath();

                var jsonForm = evey.JSONnify(crudForm);
                $.ajax({
                    url: path,
                    type: "POST",
                    dataType: "JSON",
                    data: jsonForm,
                    contentType: "application/json",
                    success : function(data) {
                        if(data.status) {
                            $('#crud-modal').foundation('reveal', 'close', {animation:'none'});
                            angular.element(".main-body").scope().searchEntity(data.result);
                            angular.element(".main-body").scope().$apply();
                            evey.promptSuccess(data.message);
                            evey.clearForm($(crudForm));
                        } else if (data.validatorError) {
                            $.each(data.errors,function(i,error){
                                var errorField = $('#crud-modal').find("[name='"+error.field+"']");
                                var errorDiv = $(errorField).parent().parent();
                                var errorContainer = $(errorDiv).find("small.error");
                                var defaultErrorMsg = $(errorContainer).text();
                                $(errorContainer).attr("data-default-error",defaultErrorMsg);
                                $(errorContainer).text(error.defaultMessage);
                                $(errorDiv).addClass("error");
                            });
                        } else {
                            evey.promptAlert(data.message);
                        }
                        $(crudForm).find("i.loader").toggleClass("hide");
                        $(crudForm).find("span.btn-label").toggleClass("hide");

                        $(crudForm).find(".button").toggleClass("disabled");
                        $(crudForm).find(".button").removeAttr("disabled");
                    }
                });
            });

            $(updateForm).on("valid.fndtn.abide",function(){

                var updateForm = $(this);

                $(updateForm).find("i.loader").toggleClass("hide");
                $(updateForm).find("span.btn-label").toggleClass("hide");

                $(updateForm).find(".button").toggleClass("disabled");
                $(updateForm).find(".button").attr("disabled",true);

                var path = evey.getPath();

                var jsonForm = evey.JSONnify(updateForm);
                $.ajax({
                    url: path,
                    type: "POST",
                    dataType: "JSON",
                    data: jsonForm,
                    contentType: "application/json",
                    success : function(data) {
                        if(data.status){
                            $('#update-modal').foundation('reveal', 'close', {animation:'none'});
                            angular.element(".main-body").scope().updateEntity(data.result);
                            angular.element(".main-body").scope().$apply();

                            evey.promptSuccess(data.message);
                            evey.clearForm($(updateForm));
                        } else if (data.validatorError) {
                            $.each(data.errors,function(i,error){
                                var errorField = $('#update-modal').find("[name='"+error.field+"']");
                                var errorDiv = $(errorField).parent().parent();
                                var errorContainer = $(errorDiv).find("small.error");
                                var defaultErrorMsg = $(errorContainer).text();
                                $(errorContainer).attr("data-default-error",defaultErrorMsg);
                                $(errorContainer).text(error.defaultMessage);
                                $(errorDiv).addClass("error");
                            });
                        } else {
                            evey.promptAlert(data.message);
                        }
                        $(updateForm).find("i.loader").toggleClass("hide");
                        $(updateForm).find("span.btn-label").toggleClass("hide");

                        $(updateForm).find(".button").toggleClass("disabled");
                        $(updateForm).find(".button").removeAttr("disabled",true);
                    }
                });
            });

            var selectedDelete;
            $(this).on("click", settings['remove'], function(){
                selectedDelete = $(this).data("id");
            });

            $(".remove-record").on('click',function(){
                angular.element(".main-body").scope().deleteAction(selectedDelete,evey.getMapping());
                angular.element(".main-body").scope().$apply();
            });

            $(this).on("click", settings['search-action'], function () {
               var jsonForm = evey.JSONnify($(this).parents("form"));
                var path = evey.getPath();
                $.ajax({
                    url: path+"/findEntity",
                    data: jsonForm,
                    type: "POST",
                    dataType:"JSON",
                    contentType: "application/json",
                    success: function(data) {
                        if(data.status){
                            var paginateThis = evey.paginatePage(data);
                            paginateThis["currentPage"] = 1;
                            paginate(paginateThis, settings["pagination"]);
                        } else {
                            evey.promptAlert(data.message);
                        }
                    }
                })
            });

            $(closeModal).on("click", function(){
                evey.closeParentModal($(this).parents("div.reveal-modal"));
            });

            $(this).on("click",settings["clear"], function(){
                evey.clearForm($(this).parents("form"));
            });
            $(this).on("click",settings["search-clear"], function(){
                evey.clearForm($(this).parents("form"));
            });
            $(this).on("click",settings["ajax-loader"],function(){
                if(!$(this).hasClass("disabled")){
                    var clickLoader = $(this);
                    $(clickLoader).find(".loader").toggleClass("hide");
                    $(clickLoader).find(".ajax-loader-label").toggleClass("hide");
                    $(clickLoader).toggleClass("disabled");
                    $(clickLoader).attr("disabled",true);

                    $(document).ready(function(){

                    }).on("ajaxStop.ajaxLoader",function(){
                        $(clickLoader).find(".loader").toggleClass("hide");
                        $(clickLoader).toggleClass("disabled");
                        $(clickLoader).find(".ajax-loader-label").toggleClass("hide");
                        $(clickLoader).removeAttr("disabled");

                        $(document).off("ajaxStop.ajaxLoader");
                    });
                }
            });

            $("a.navigation").on("click", function(){
                $(this).find(".navigation-icon").toggleClass("hide");
                $(this).find(".navigation-spinner").toggleClass("hide");
                var url = $(this).attr("data-url");
                $(this).attr("href", url);
            });


            $(document).ready(function() {

                var url = $(this).find(settings['crud-table']).data("url");
                if(url!=undefined && url!=null && url!="") {
                    $.ajax({
                        url: evey.getHome()+url,
                        dataType: "JSON",
                        type: "GET",
                        success: function (data) {
                            var paginateThis = evey.paginatePage(data);
                            paginateThis["currentPage"] = 1;
                            paginate(paginateThis, settings["pagination"]);
                        }
                    });
                }
            });
        });
    };

    var paginate = function(paginateThis, pagination){

        $(pagination).find("li").remove();

        var previous = $('<li>');
        $(previous).append($('<a>&laquo; Previous</a>'));
        $(previous).addClass("arrow previous");

        var next = $('<li>');
        $(next).addClass("arrow next");
        $(next).append($('<a>Next &raquo;</a>'));

        if(paginateThis.currentPage == 1) {
            $(previous).addClass("unavailable");
            $(previous).attr("aria-disabled",true);
        }

        $(pagination).append($(previous))

        for(var i=1;i<=paginateThis.numberOfPage;i++) {
            if(Number(paginateThis.currentPage) == Number(i)) {
                var newPage = $('<li class="current">').append($('<a class="pages" data-page='+i+' data-max='+paginateThis.maxItem+'>').text(i));
            } else {
                var newPage = $('<li>').append($('<a class="pages" data-page='+i+' data-max='+paginateThis.maxItem+'>').text(i));
            }

            $(pagination).append($(newPage));
        }

        $(document).ready(function(){

        }).on("click","a.pages",function(){

            var previous = $("ul.pagination li.previous");
            var next = $("ul.pagination li.next");

            $(this).parents("ul").find("li.current").removeClass("current");
            $(this).parent("li").addClass("current");

            var page = $(this).data("page");
            var max = $(this).data("max");

            if(Number(page) != 1) {
                $(previous).removeClass("unavailable");
                $(previous).attr("aria-disabled", false);
            } else {
                $(previous).addClass("unavailable");
                $(previous).attr("aria-disabled", true);
            }

            if(Number(page) != Number(paginateThis.numberOfPage)) {
                $(next).removeClass("unavailable");
                $(next).attr("aria-disabled", false);
            } else {
                $(next).addClass("unavailable");
                $(next).attr("aria-disabled", true);
            }

            angular.element(".main-body").scope().changePage(page, max);
            angular.element(".main-body").scope().$apply();
        });


        if(paginateThis.currentPage == paginateThis.numberOfPage) {
            $(next).addClass("unavailable");
            $(next).attr("aria-disabled",true);
        }

        $(pagination).append($(next));


        $(next).on("click", function(){
            $(pagination).find("li.current").next().find("a.pages").click();
        });

        $(previous).on("click", function(){
            $(pagination).find("li.current").prev().find("a.pages").click();
        });

        angular.element(".main-body").scope().loadTable(paginateThis);
        angular.element(".main-body").scope().$apply();
    };
})(jQuery);

