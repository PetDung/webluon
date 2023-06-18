// product
function getProductToLocalStorage(){
    let listProduct = JSON.parse(localStorage.getItem("listProduct"));
    let count =JSON.parse(localStorage.getItem("count"));
    let sumPrice =JSON.parse(localStorage.getItem("sumPrice"));
    let total =JSON.parse(localStorage.getItem("total"));
    if(listProduct){
        return {
            total,
            listProduct,
            sumPrice,
            count,
        }
    }
    return {
        total: 0,
        listProduct: [],
        sumPrice: 0,
        count:[],
    }
}
 function renderCart(){
    let cart = document.querySelector(".quanty");
    let total = getProductToLocalStorage().total;
    cart.innerHTML=total;

    let sumPrice= getProductToLocalStorage().sumPrice;
    let listProduct = getProductToLocalStorage().listProduct;
    let count =getProductToLocalStorage().count;
    console.log(getProductToLocalStorage());
    let elistProduct = document.querySelector(".list-product");
    let esumPrice =document.querySelector(".sum-price");
    let html="";
    listProduct.forEach((product)=>{
        html +=`
                    <div class="item-product">
                          <div class="item-img">
                             <img src="/img/product.jpg" alt="">
                          </div>
                             <div class="item-info">
                                  <span class="name">${product.name}</span>
                                  <span class="sum">Số lương: <span>${count[product.id]}</span></span>
                                  <span class="price">Giá: <span>${product.price}</span></span>
                          </div> 
                          <div data-id='${product.id}' onclick="handleDelete(this)" class="icon-clear"><i class="fa-regular fa-circle-xmark"></i></div>         
                    </div>`
    })
    elistProduct.innerHTML=html;
    esumPrice.innerHTML=sumPrice;
}
function  addCartProduct(id,name,price,quanty=1){
    const product = {
        name,
        id : parseInt(id),
        price: Number(price),
    }
    quanty=Number(quanty);
    let length = product.id + 1;
    let dataProduct = getProductToLocalStorage();
    let listProduct = dataProduct.listProduct;
    let count =dataProduct.count;
    let sumPrice =dataProduct.sumPrice + product.price*quanty;
    let total =dataProduct.total + quanty ;
    if(listProduct.length && count[product.id]){
        count[product.id] +=quanty;
    }else if(count[product.id] === 0){
        listProduct.push(product);
        count[id] +=quanty;
    }else if(!listProduct.length && !count.length)  {
        count = new Array(length);
        count.fill(0);
        listProduct.push(product);
        count[product.id]+= quanty;
    }else{
        let tempCount = new Array(length);
        tempCount.fill(0);
        tempCount[product.id] +=quanty;
        listProduct.forEach(item=>{
            tempCount[item.id]=count[item.id];
        })
        listProduct.push(product);
        count = tempCount;
    }
    localStorage.setItem("listProduct",JSON.stringify(listProduct));
    localStorage.setItem("count",JSON.stringify(count));
    localStorage.setItem("sumPrice",JSON.stringify(sumPrice));
    localStorage.setItem("total",JSON.stringify(total));
    renderCart();
}
function removeProduct(id){
    id = parseInt(id);
    let dataProduct = getProductToLocalStorage();
    let listProduct = dataProduct.listProduct;
    let count =dataProduct.count;
    let sumPrice =dataProduct.sumPrice;
    let total =dataProduct.total ;

    let index;
    for(let i =0 ; i<listProduct.length; i++){
        if(listProduct[i].id === id  ) index = i;
    }
    let item =listProduct[index];
    sumPrice -= item.price * count[id];
    total -=count[id];
    count[id] =0;
    listProduct.splice(index,1);
    localStorage.setItem("listProduct",JSON.stringify(listProduct));
    localStorage.setItem("count",JSON.stringify(count));
    localStorage.setItem("sumPrice",JSON.stringify(sumPrice));
    localStorage.setItem("total",JSON.stringify(total));
    renderCart();
}

renderCart();
let products = document.querySelectorAll(".product");
products.forEach(product=>{
    let id=product.querySelector("span").getAttribute("data-id");
    let name =product.querySelector(".name").innerHTML
    let price =product.querySelector(".price").innerHTML
    product.addEventListener('click', function(event) {
        if (event.target.classList.contains('fa-cart-plus')) {
            addCartProduct(id,name,price);
        } else if (event.target.classList.contains('btn-product')) {
            location.href=`/checkout`;
        }
        else{
            location.href=`/${id}`;
        }
    });
})

let close =document.querySelector(".icon-clear")
function handleDelete(elemen){
    removeProduct(elemen.getAttribute("data-id"));
}

//handlerClick Cart
let cart = document.querySelector(".click-cart")
cart.onclick=()=>{
    document.querySelector(".show-cart").classList.toggle("showCart");
}
let add = document.querySelector("#add");
if(add){
    add.onclick=()=>{
        let id = add.getAttribute("data-id");
        let quanty = document.querySelector(".text-count").value;
        quanty = parseInt(quanty);
        let name = document.querySelector("#name").innerHTML;
        let price = document.querySelector("#price").getAttribute("data-price");
        addCartProduct(id,name,price,quanty);
    }
}
document.addEventListener('gesturestart', function (e) {
    e.preventDefault();
});