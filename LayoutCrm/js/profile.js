$(document).ready(function() {

    function manageData() {
        $.ajax({
            url : "http://localhost:8080/crm/api/profile",
            method : "GET"
        }).done(function(result) {
            $("#profileTable tbody").empty()
            $.each(result, function(i, val) {
                var row = `<tr>
                                <td>${i+1}</td>
                                <td>${val.name}</td>
                                <td>${val.jobName}</td>
                                <td>${datePatternForUser(val.startDate)}</td>
                                <td>${datePatternForUser(val.endDate)}</td>
                                <td>${val.statusName}</td>
                                <td>
                                    <a href="#profileFormModal" class="btn btn-sm btn-primary btn-edit-profile" data-toggle="modal" 
                                        task-id=${val.id} status=${val.statusId}>Cập nhật</a>
                                </td>
                            </tr>`
                $("#profileTable tbody").append(row)
            })
        })
    }

    function getToastSuccess(result) {
        $.toast({
            heading: 'Success',
            position: 'top-center',
            text: result.message,
            showHideTransition: 'slide',
            icon: 'success'
        })
    }

    function getToastError(result) {
        $.toast({
            heading: 'Error',
            position: 'top-center',
            text: result.message,
            showHideTransition: 'fade',
            icon: 'error',
        })
    }

    function datePatternForUser(date) {
        var split = date.split("-")
        var year = split[0]
        var month = split[1]
        var day = split[2]
        var pattern = day.concat("-", month.concat("-", year))
        return pattern
    }

    manageData()

    $("body").on('click', '.btn-edit-profile', function() {
        var taskId = $(this).attr("task-id")
        var statusId = $(this).attr("status")
        var name = $(this).parent("td").prev("td").prev("td").prev("td").prev("td").prev("td").text()
        var job = $(this).parent("td").prev("td").prev("td").prev("td").prev("td").text()
        var startDate = $(this).parent("td").prev("td").prev("td").prev("td").text()
        var endDate = $(this).parent("td").prev("td").prev("td").text()
        $("#id").val(taskId)
        $("#name").val(name)
        $("#job").val(job)
        $("#startDate").val(startDate)
        $("#endDate").val(endDate)
        $("#statusSelect").val(statusId)
    })

    $("#btn-save-profile").click(function(e) {
        e.preventDefault()
        var dataId = $("#id").val()
        var dataStatusId = $("#statusSelect").val()
        $.ajax({
            url : "http://localhost:8080/crm/api/profile",
            method : "PUT",
            data: JSON.stringify({
                id : dataId,
                statusId : dataStatusId
            })
        }).done(function(result) {
            if(result.isSuccess == true) {
                getToastSuccess(result)
                manageData()
            } else {
                getToastError(result)
            }
            $("#profileFormModal").modal('hide')
        })
    })

    $(".non-start").text("33.33%")
    $(".non-start").css("width", "73.33%")
})