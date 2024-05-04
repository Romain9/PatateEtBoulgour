
var nb = 0;

function asyncSearch(element) {
    var option = $('#sortOptions').find(':selected').val();
    var search = $('#search').val();
    var nb = $('#activities').data("page");

    if (element != null) {
        if (element.tagName == "INPUT") {
            nb = 0;
        }
        else {
            nb++;
        }
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

function applyRating (id, value) {
    $.ajax({
        url: '/api/activity/rating/' + id + "/" +value,
        type: 'GET',
        dataType: 'json'
    });
}

function findRatingFor(id) {
    $.ajax({
        url: '/api/activity/rating/' + id,
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            $('#rating' + response + '-' + id).prop("checked", true);
        }
    });
}

function handleActivityStatusChange(id, element, reloadMode = false) {


    let url = '/api/activity/'
    let elementClass = $(element).hasClass('removeActivity') ? 'removeActivity' : 'addActivity';

    if (elementClass === 'removeActivity') {
        url += 'remove/' + id;
    } else {
        url += 'add/' + id;
    }

    $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            if (reloadMode) {
                location.reload()
            } else {
                if (elementClass === 'removeActivity') {
                    $(element).removeClass('removeActivity').addClass('addActivity').text('Ajouter activité')
                    $('activityId-' + id).hide()
                } else {
                    $(element).removeClass('addActivity').addClass('removeActivity').text('Supprimer activité')
                    $('activityId-' + id).show()
                }
            }
        },
        error: function(xhr, status, error) {
            // This function will be called if there is an error
            console.log('Error:', xhr.responseText, xhr.status);
        }
    });

}

function toRight() {
    var activities = document.getElementsByClassName("acti");

    var translateLog = activities[0].style.transform;
    if (activities.length > 1 && translateLog.split(' ')[0] !== "") {
        for (let i = 0 ; i < activities.length ; i++) {
            activities[i].style.transform = translateLog.replace("translateX(-100%)", "");
        }
    }
}

function toLeft() {
    var activities = document.getElementsByClassName("acti");
    var translateLog = activities[0].style.transform;
    if (activities.length > 1 && translateLog.split(' ')[0] == "") {
        for (let i = 0 ; i < activities.length ; i++) {
            activities[i].style.transform += 'translateX(-100%)';
        }
    }
}