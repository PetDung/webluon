$(document).ready(function(){
    $('#eye').click(function(){
        $(this).toggleClass('open');
        $(this).children('i').toggleClass('fa-eye-slash fa-eye');
        if($(this).hasClass('open')){
            $(this).prev().attr('type', 'text');
        }else{
            $(this).prev().attr('type', 'password');
        }
    });
    let span = document.querySelector("#log");
    span.style.color="red";
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const check = urlParams.get('succ');
    if(String(check) ==="1"){
        span.innerHTML="Tài Khoản Hoặc Mật Khẩu Không Đúng";
    }
    $("#form-login").on('submit', function(event){
        event.preventDefault();
        let inputs = document.querySelectorAll(".form-input");
        let isCheck =true;
        inputs.forEach(input=>{
            if(!input.value){
                span.innerHTML="Vui lòng nhập đầy đủ thông tin";
                isCheck=false
            }else{
                span.innerHTML="";
                isCheck=true;
            }
        })
        isCheck && this.submit();
    })
});