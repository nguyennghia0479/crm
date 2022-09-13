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
        var jobId = getUrlParameter("id")
        $.ajax({
            url : `http://127.0.0.1:8080/crm/api/job-details?id=${jobId}`,
            method: "GET"
        }).done(function(result) {
            $("#job-details").empty()
            $.each(result, function(index, value) {
               var row = `<div id=${value.id} class="row">
                                <div class="col-xs-12">
                                    <a href="#" class="group-title">
                                        <img width="30" src="plugins/images/users/pawandeep.jpg" class="img-circle" />
                                        <span>${value.fullName}</span>
                                    </a>
                                </div>
                                <div class="col-md-4">
                                    <div class="white-box">
                                        <h3 class="box-title">Chưa thực hiện</h3>
                                        <div class="message-center task-not-start-yet">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="white-box">
                                        <h3 class="box-title">Đang thực hiện</h3>
                                        <div class="message-center task-in-progress">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="white-box">
                                        <h3 class="box-title">Đã hoàn thành</h3>
                                        <div class="message-center task-is-completed">
                                        </div>
                                    </div>
                                </div>
                            </div>`
                $("#job-details").append(row)
                $.each(value.taskModels, function(i, val) {
                    var task = `<a href="#">
                                <div class="mail-contnet">
                                    <h5>${val.name}</h5> <span class="mail-desc">Just see the my admin!</span> <span
                                        class="time">9:30 AM</span>
                                </div>
                            </a>`
                    if(val.statusId == 1) {
                        $("[id=" + val.userId +"] .task-not-start-yet").append(task)
                    } else if(val.statusId == 2) {
                        $("[id=" + val.userId +"] .task-in-progress").append(task)
                    } else if(val.statusId == 3) {
                        $("[id=" + val.userId +"] .task-is-completed").append(task)
                    }
                })
            })
        })
    }

  manageData()
})