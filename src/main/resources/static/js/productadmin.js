let products = document.querySelectorAll(".product");
products.forEach(product=>{
    let id=product.querySelector("span").getAttribute("data-id");
    product.addEventListener('click', function(event) {
        if (event.target.classList.contains('fa-trash')) {
            $.ajax({
                url: `/admin/delete/${id}`,
                method: 'DELETE',
                success: function(result) {
                    product.remove();
                    alert('Product deleted successfully');
                },
                error: function(err) {
                    alert("Không thành công");
                }
            });
        }
        else if (event.target.classList.contains('fa-eye')) {
            $.ajax({
                url: `/admin/hidden/${id}`,
                method: 'PUT',
                success: function(result) {
                    product.remove();
                    alert('Thành Công');
                },
                error: function(err) {
                    alert("Không thành công");
                }
            });
        }
        else{
            location.href=`/admin/listproduct/${id}`;
        }
    });
})