$(document).ready(function () {

    function manageData() {
        $.ajax({
            url: "http://localhost:8080/crm/api/user",
            method: "GET"
        }).done(function(result){
            $("#userTable tbody").empty()
            $.each(result, function(i, val){
                var fullname = `${val.fullName}`
                var firstName = fullname.split(' ').slice(0, -1).join(' ')
                var lastName = fullname.split(' ').slice(-1).join(' ')
                var row = `<tr>
                                <td>${i+1}</td>
                                <td>${firstName}</td>
                                <td>${lastName}</td>
                                <td>${val.email}</td>
                                <td>${val.roleName}</td>
                                <td width="20%">
                                    <a id="btn-edit-user" href="#userFormModal" class="btn btn-sm btn-primary" data-toggle="modal" 
                                        user-id=${val.id}>Sửa</a>
                                    <a href="#deleteModal" class="btn btn-sm btn-danger btn-delete-user" data-toggle="modal" user-id=${val.id}>Xóa</a>
                                    <a href="user-details.html" class="btn btn-sm btn-info">Xem</a>
                                </td>
                            </tr>`
                $("#userTable tbody").append(row)
            })
        })
    }

    function getSelectRole(roleId) {
        $.ajax({
            url: "http://localhost:8080/crm/api/role",
            method: "GET"
        }).done(function(result){
            $("#roleSelect").empty()
            $.each(result, function(i, val){
                var option = `<option value="${val.id}">${val.name}</option>`
                $("#roleSelect").append(option)
            })
            if(roleId != null)
                $("#roleSelect").val(roleId)
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

    manageData()

    $("#btn-add-user").click(function() {
        $("#id").val('')
        $("#fullName").val('')
        $("#email").val('')
        $("#password").val('')
        getSelectRole(null)
    })

    $("body").on('click', '#btn-edit-user', function(e) {
        e.preventDefault()
        var userId = $(this).attr("user-id")
        $.ajax({
            url : `http://localhost:8080/crm/api/user?id=${userId}`,
            method : "GET"
        }).done(function(result){
            $("#id").val(result.id)
            $("#fullName").val(result.fullName)
            $("#email").val(result.email)
            $("#password").val(result.password)
            getSelectRole(result.roleId)
        })
    })

    $("body").on('click', '.btn-delete-user', function(e) {
        var userId = $(this).attr("user-id")
        $(".confirm-delete").attr("user-id", userId)
    })

    $(".confirm-delete").click(function(e) {
        var userId = $(this).attr("user-id")
        var row = $(".btn-delete-user[user-id=" + userId + "]")
        $.ajax({
            url : `http://localhost:8080/crm/api/user?id=${userId}`,
            method : "DELETE"
        }).done(function(result){
           if(result.isSuccess == true) {
                row.closest('tr').remove()
                $('#deleteModal').modal('hide')
                getToastSuccess(result)
           } else {
                getToastError(result)
           }
        })
    })
})