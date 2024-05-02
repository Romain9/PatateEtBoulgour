
function asyncSearch(element) {
    var option = $('#sortOptions').find(':selected').val();
    var search = $('#search').val();
    var nb = $('#activities').data("page");

    if (element.tagName == "INPUT") {
        nb = 0;
    }
    else {
        nb++;
    }
    
    $.ajax({
        type: "POST",
        url: "/",
        data: {
            option: option,
            search: search,
            page: nb
        },
        success: function (response) {
            $("body")
            $('#activities').replaceWith(response);
        },
        error: function(xhr, status, error) {
            console.log("xhr: "+xhr+"status: "+status+"\nerror: "+error);
            console.error('Error searching activities: ' + error);
        }
    });
}

function loadDatalist (endpoint, datalist) {
    $.ajax({
        url: '/api/search/' + endpoint,
        type: 'GET',
        dataType: 'json',
        success: function(response) {

            $('#' + datalist).empty();

            $.each(response, function(index, value) {
                $('#' + datalist).append('<option value="' + value + '">');
            });
        },
        error: function(xhr, status, error) {
            console.error('Erreur: ' + error);
        }
    });
}

