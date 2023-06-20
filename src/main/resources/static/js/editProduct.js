$('#product-form').on('submit', function(event) {
    event.preventDefault();
    let isPost = true;
    let inputs =document.querySelectorAll(".input");
    inputs.forEach(input=>{
        let span = input.nextElementSibling;
        span.style.color = "red";
        if (!input.value){
            span.innerHTML ="Trường này còn rỗng";
            isPost =false;
        }
        else{
            span.innerHTML="";
            isPost = true;
        }
    })
    var formData = new FormData(this);
    let id =document.querySelector("#id").getAttribute("data-id");
    isPost && $.ajax({
        url: `/admin/edit/${id}`,
        type: 'POST',
        data: formData,
        enctype: 'multipart/form-data',
        contentType: false,
        processData: false,
        success: function(product) {
            alert("Cập nhật thành công");
        },
        error: function(xhr, status, error) {
            console.error('Error: ' , error);
        },
    });
})