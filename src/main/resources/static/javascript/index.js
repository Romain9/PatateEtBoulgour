
function asyncSearch() {
    var search = $('#search').val();
    console.log("value: "+$('#label').attr('href'));
    
    $.ajax({
        type: "POST",
        url: "/",
        data: {
            search: search
        },
        beforeSend: function() {

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

