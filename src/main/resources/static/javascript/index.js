
function asyncSearch(element) {
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

