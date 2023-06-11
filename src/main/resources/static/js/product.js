let products = document.querySelectorAll(".product");
products.forEach(product=>{
    let id=product.querySelector("span").getAttribute("data-id");
    let name =product.querySelector(".name").innerHTML
    let price =product.querySelector(".price").innerHTML
    product.addEventListener('click', function(event) {
        if (event.target.classList.contains('fa-cart-plus')) {
            addCartProduct(id,name,price);
        } else if (event.target.classList.contains('btn-product')) {
            location.href=`/buy/${id}`;
        }
        else{
            location.href=`/${id}`;
        }
    });
})