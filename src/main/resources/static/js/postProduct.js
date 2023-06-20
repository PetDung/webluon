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
        isPost && $.ajax({
            url: '/admin/post-product',
            type: 'POST',
            data: formData,
            enctype: 'multipart/form-data',
            contentType: false,
            processData: false,
            success: function(product) {
                alert("Đăng thành công");
                inputs.forEach(input=>{
                    input.value="";
                    const previewImage = document.getElementById('preview-image');
                    previewImage.setAttribute("src","");

                })
            },
            error: function(xhr, status, error) {
                console.error('Error: ' , error);
            },
        });
})