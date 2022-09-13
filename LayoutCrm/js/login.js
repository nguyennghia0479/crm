$(document).ready(function() {
    function setCookie(email) {
        $.cookie('email', email)
    }

    function clearCookie() {
        $.removeCookie("email", '')
    }


    $("#login").click(function(e) {
        e.preventDefault()
        var email = $("input[name='email']").val()
        setCookie(email)
        window.location.href = "/index.html"
    })

    $("#logout").click(function() {
        clearCookie()
        window.location.href = "/login.html"
    })
})