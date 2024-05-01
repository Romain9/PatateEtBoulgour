
function asyncSearch() {
    var search = $('#search').val();
    console.log($('#activities').children.length);
    $.ajax({
        type: "POST",
        url: "/search",
        data: {
            query: search
        },
        success: function (response) {
            $('#activities').html(response)
        },
        error: function(xhr, status, error) {
            console.error('Error searching activities: ' + error);
        }
    });
}