$("#tree").on("click","li",function () {
    $(this).addClass("active").siblings().removeClass('active');
    // alert($(this).parent().find("i").attr("id"));
    var id = $(this).attr("id");

    if(id != undefined){
        // if (confirm("请先进行登录")){
        //     window.location.href="login.html"
        // }
        alert("请先进行登录")
    }
})