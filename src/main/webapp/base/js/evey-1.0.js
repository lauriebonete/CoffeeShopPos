/**
 * Created by Laurie on 11/5/2015.
 */


var evey = (function(){

    return {
        getPath : function() {
            var path = window.location.pathname.replace(/\/$/, '');
            return window.location.protocol + '//' + window.location.host + path;
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

        getMapping : function(){
            return window.location.pathname.replace(/\/$/, '');
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

        buildJson: function(name){
            var object = new Object();
            return object[name]
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
            'search-clear': "#search-clr-crud-btn"
        }, options);

        return this.each(function(){

            Object.getPrototypeOf(document.createComment('')).getAttribute = function() {}

            var crudForm = $(this).find(settings['form']);

            var updateForm = $(this).find(settings['updateForm'])

            var offCanvas = $(this).find(settings['offCanvas'])

            var mainBody = $(this).find(settings['mainBody']);

            var deleteAction = $(this).find(settings['remove']);

            var home = evey.getHome();

            $(crudForm).on("valid.fndtn.abide",function(){
                $(this).find("i.loader").toggleClass("hide");
                $(this).find("span.btn-label").toggleClass("hide");

                $(this).find(".button").toggleClass("disabled");
                $(this).find(".button").attr("disabled",true);

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
                            $('#crud-modal').foundation('reveal', 'close');
                            angular.element(".main-body").scope().searchEntity(data.result);
                            angular.element(".main-body").scope().$apply();

                            $(this).find("i.loader").toggleClass("hide");
                            $(this).find("span.btn-label").toggleClass("hide");

                            $(this).find(".button").toggleClass("disabled");
                            $(this).find(".button").removeAttr("disabled",true);

                            promptSuccess();
                        } else {

                            $(this).find("i.loader").toggleClass("hide");
                            $(this).find("span.btn-label").toggleClass("hide");

                            $(this).find(".button").toggleClass("disabled");
                            $(this).find(".button").removeAttr("disabled",true);
                            promptAlert();
                        }
                    },
                    error : function(data) {
                        $(this).find("i.loader").toggleClass("hide");
                        $(this).find("span.btn-label").toggleClass("hide");

                        $(this).find(".button").toggleClass("disabled");
                        $(this).find(".button").removeAttr("disabled",true);
                        promptAlert();
                    },
                });
            });

            $(updateForm).on("valid.fndtn.abide",function(){
                $(this).find("i.loader").toggleClass("hide");
                $(this).find("span.btn-label").toggleClass("hide");

                $(this).find(".button").toggleClass("disabled");
                $(this).find(".button").attr("disabled",true);

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
                            $('#update-modal').foundation('reveal', 'close');
                            angular.element(".main-body").scope().updateEntity(data.result);
                            angular.element(".main-body").scope().$apply();

                            promptSuccess();

                            $(this).find("i.loader").toggleClass("hide");
                            $(this).find("span.btn-label").toggleClass("hide");

                            $(this).find(".button").toggleClass("disabled");
                            $(this).find(".button").removeAttr("disabled",true);
                        } else {
                            promptAlert();
                            $(this).find("i.loader").toggleClass("hide");
                            $(this).find("span.btn-label").toggleClass("hide");

                            $(this).find(".button").toggleClass("disabled");
                            $(this).find(".button").removeAttr("disabled",true);
                        }
                    },
                    error : function(data) {
                        promptAlert();
                        $(this).find("i.loader").toggleClass("hide");
                        $(this).find("span.btn-label").toggleClass("hide");

                        $(this).find(".button").toggleClass("disabled");
                        $(this).find(".button").removeAttr("disabled",true);
                    },
                });
            });

            var selectedDelete;
            $(this).on("click", settings['remove'], function(){
                selectedDelete = $(this).data("id");
            });

            $(".remove-record").on('click',function(){
                angular.element(".main-body").scope().deleteAction(selectedDelete,evey.getMapping());
                angular.element(".main-body").scope().$apply();

                promptSuccess();
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
                        var paginateThis = evey.paginatePage(data);
                        paginateThis["currentPage"] = 1;
                        paginate(paginateThis, settings["pagination"]);
                    }
                })
            });

            $(this).on("click",settings["clear"], function(){
                evey.clearForm($(this).parents("form"));
            });
            $(this).on("click",settings["search-clear"], function(){
                evey.clearForm($(this).parents("form"));
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
        $(previous).addClass("arrow");

        var next = $('<li>');
        $(next).addClass("arrow");
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

            $(newPage).find("a").on("click",function(){

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

            $(pagination).append($(newPage));
        }

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

    var promptSuccess = function() {
        toastr.options.positionClass = 'toast-bottom-full-width';
        toastr.options.closeMethod = 'fadeOut';
        toastr.options.closeDuration = 300;
        toastr.options.closeEasing = 'swing';
        toastr.success('Transaction Successful!');

    };

    var promptAlert = function() {
        toastr.options.positionClass = 'toast-bottom-full-width';
        toastr.options.closeMethod = 'fadeOut';
        toastr.options.closeDuration = 300;
        toastr.options.closeEasing = 'swing';
        toastr.error('Transaction Failed! Double check your inputs and try again.');
    };

})(jQuery);

