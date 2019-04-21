var slideVerify;
$(function() {
    $('input').iCheck({
        checkboxClass: 'icheckbox_square-blue',
        radioClass: 'iradio_square-blue',
        increaseArea: '20%' /* optional */
    });
});
$(function(){
    // console.log(parseFloat('1px'))

    var SlideVerifyPlug = window.slideVerifyPlug;
    slideVerify = new SlideVerifyPlug('#verify-wrap',{
        // wrapWidth:'450',//设置 容器的宽度 ,不设置的话，会设置成100%，需要自己在外层包层div,设置宽度，这是为了适应方便点；
        // initText:'请按住滑块',  //设置  初始的 显示文字
        // sucessText:'验证通过',//设置 验证通过 显示的文字
        getSuccessState:function(res){
            //当验证完成的时候 会 返回 res 值 true，只留了这个应该够用了
            console.log(res);
        }
    });
});

//记住密码功能
function sava(){
    // console.log($("input[type='checkbox']").is(':checked'))
    if($("input[type='checkbox']").is(':checked')){
        var name = $("#username").val();
        var paw = $("#password").val();
        $.cookie("rmbUser","true",{ expires: 7 });//存储一个七天期限的cookie
        $.cookie("username",name,{ expires: 7});
        $.cookie("password",paw,{ expires: 7 });
    }
    else {
        $.cookie("rmbUser", "false", { expire: -1 });
        $.cookie("username", "", { expires: -1 });
        $.cookie("password", "", { expires: -1 });
    }

}




document.querySelector('#login').addEventListener('click',function(e){
     if(slideVerify.slideFinishState == false){
         alert("请先进行验证！");
         e.preventDefault();
     }
 },false);

    $(document).ready(function () {
        $('button[data-toggle]').on('click', function () {
            var $target = $($(this).attr('data-toggle'));
            $target.toggle();
            if (!$target.is(':visible')) {
                // Enable the submit buttons in case additional fields are not valid
                $('#contactForm').data('bootstrapValidator').disableSubmitButtons(false);
            }
        });

        $('#contactForm').bootstrapValidator();

        if($.cookie("rmbUser") == "true"){
            // $("#ck_rmbUser").prop("checked",true);
            $("#username").val($.cookie("username"));
            $("#password").val($.cookie("password"));
        }
    });

