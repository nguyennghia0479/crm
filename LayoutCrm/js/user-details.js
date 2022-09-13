$(document).ready(function() {

    function getUrlParameter(sParam) {
        var sPageURL = window.location.search.substring(1),
            sURLVariables = sPageURL.split('&'),
            sParameterName;

        for (var i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
            }
        }
        return false;
    }

    function manageData() {
        var userId = getUrlParameter("id")
        $.ajax({
            url : `http://127.0.0.1:8080/crm/api/user-details?id=${userId}`,
            method: "GET"
        }).done(function(result) {
            $(".task-not-start-yet").empty()
            $(".task-in-progress").empty()
            $(".task-is-completed").empty()
            $.each(result, function(i, val) {
                var row = `<a href="#">
                                <div class="mail-contnet">
                                    <h5>${val.name}</h5>
                                    <span class="mail-desc">${val.jobName}</span>
                                    <span class="time">Bắt đầu: ${val.startDate}</span>
                                    <span class="time">Kết thúc: ${val.endDate}</span>
                                </div>
                            </a>`
                if(val.statusId == 1) {
                    $(".task-not-start-yet").append(row)
                } else if(val.statusId == 2) {
                    $(".task-in-progress").append(row)
                } else {
                    $(".task-is-completed").append(row)
                }
            })
        })
    }

    manageData()

})