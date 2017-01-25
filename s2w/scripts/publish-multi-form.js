/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var s2w_vars= {
    
submitURL : "/s2w/IotaRegisterWebForm"

};

$(function() {//document ready
    var icons = {
        header: "ui-icon-circle-arrow-e",
        activeHeader: "ui-icon-circle-arrow-s"
    };

    $("#accordion").accordion({
        icons: icons,
        collapsible: true,
        heightStyle: "content"
    });

    $(document).ready(function() {
        $("#accordion").accordion('activate', 0);
    });
});

function LoadPage1() {
    $('#siteloader').hide();
    $('#siteloader').load("/s2w/register/Resource-v2.html", {}, function() {
        $(this).fadeIn('normal');
        initializeForm();
    });

    $.getScript("/s2w/scripts/form-minimap.js", function() {
        load();     //loads map       
    });


}
function LoadPage2() {
    $('#siteloader').hide();
    $('#siteloader').load("/s2w/register/Entity-v2.html", {}, function() {
        $(this).fadeIn('normal');
        initializeForm();
    });


}
function LoadPage3() {
    $('#siteloader').hide();
    $('#siteloader').load("/s2w/register/Service-v2.html", {}, function() {
        $(this).fadeIn('normal');
        initializeForm();
    });
}

function initializeForm() {

    readyForSubmit();

    // alert($('input[name=crudMethod]').val());
    //if UPDATE!!!
    if ($('input[name=crudMethod]').val() === 'update') {

        s2w_vars.submitURL = "/s2w/IotaUpdateWebForm";

        var button = $('<button>', {
            text: "retrieve", //set text 1 to 10
            id: "getDescButton",
            click: function(e) {
                e.preventDefault(); //will prevent "submit"
                $('#mainform').trigger("reset");
                var descId = $('#hasID').val();
                var desctype = $('#desctype').val();
                // alert(desctype);

                jQuery.ajaxSetup({
                    beforeSend: function() {
                        $('#processing1').show();
                    },
                    complete: function() {
                        $('#processing1').hide();
                    },
                    success: function() {
                    }
                });

                var request = $.ajax({
                    async: true,
                    type: "GET",
                    url: s2w_vars.submitURL,
                    data: {desctype: desctype, id: descId}
                });
                request.done(function(response) {
                    //alert(response);
                    var formFields = jQuery.parseJSON(response);
                    $.each(formFields, function(key, value) {
                        // alert(key + ' -> ' + value);                            
                        var field = document.getElementById(key);
                        if (field === "type") {
                            var splitNS = field.split('#');
                            field = splitNS[1];
                        }


                        $(field).val(value);
                    });

                });
                request.fail(function(jqXHR, textStatus) {
                    alert("Request failed: " + textStatus);
                });
            }
        });
        var existingdiv1 = document.getElementById('getDescButtonHolder');
        $(existingdiv1).append(button);
        existingdiv1 = document.getElementById('getDescProcess');
        $(existingdiv1).append('<img id="processing1" src="/s2w/images/processing.gif" />');
        $('#processing1').hide();
    }


}



function readyForSubmit() {

    $(document).on("click", "#submit2", function(e) {
        e.preventDefault();
        $('#resultFormats').empty();
        $('#resulttextbox').empty();
        var form = $('#mainform');

        if (form[0].checkValidity()) {
            
            jQuery.ajaxSetup({
                    beforeSend: function() {
                        $('#processing').show();
                    },
                    complete: function() {
                        $('#processing').hide();
                    },
                    success: function() {
                    }
                });

            var req = $.ajax({
                type: "POST",
                url: s2w_vars.submitURL,
                processData: false,
                contentType: form.attr('enctype'),
                data: form.serialize()
            });
            req.done(function(response) {
                //alert(data);
                var formatLinks = jQuery.parseJSON(response);
                $('#resultFormats').append("<p>Select a format:</p>");
                $.each(formatLinks, function(key, value) {
                    // alert(key + ' -> ' + value);

                    var button = $('<button>', {
                        text: key, //set text 1 to 10
                        id: key,
                        click: function() {

                            //alert(value);                                     
                            getDescription(value);
                        }
                    });
                    $('#resultFormats').append(button);
                });
                $('#resultFormats').append('<img id="processing" src="/s2w/images/processing.gif" />');
                $('#processing').hide();
                
            });
            req.fail(function(jqXHR, textStatus, errorThrown) {
                //alert("Request failed: " + jqXHR.responseText);
                $('<iframe />', {
                    name: 'myFrame',
                    id: 'myFrame',
                    frameborder: 1,
                    width: '60%',
                    srcdoc: jqXHR.responseText
                }).appendTo('#resultFormats');
                //$('#resultFormats').html("object with ID \""+$('#hasID').val()+"\" already exists!, please use another ID");
            });

            $("#accordion").accordion('activate', 1);
            $("#section1").focus();
        }
        else {
            console.log("please enter all fields");
            alert("Please enter at least: ID, Name, Longitude and Latitude \nFields requiring URI must follow correct syntax or left empty");
        }
    });
}

function getDescription(value) {

    $('#resulttextbox').empty();
    $.ajax({
        type: "GET",
        dataType: "text",
        url: value //name of servlet                
    }).done(function(result) {
        $('#resulttextbox').val(result);
    });

}

