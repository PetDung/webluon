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
let renderListProduct =()=>{
    let dataOrders = getProductToLocalStorage();
    let listProduct = dataOrders.listProduct;
    let count = dataOrders.count;
    let sumPrice = dataOrders.sumPrice;
    let html ="";
    listProduct.forEach(product=>{
        html+=`
                    <li class="list-group-item d-flex justify-content-between lh-condensed">
                                    <div>
                                        <h6 class="my-0">${product.name}</h6>
                                        <small class="text-muted">
                                            <button type="button" data-id=${product.id} class="icon-plus icon" onclick="plus(this)">
                                                <i class="fa-solid fa-plus">
                                                </i>
                                            </button>
                                            <span data-id=${product.id} type="number" class="text-count">${count[product.id]}</span>
                                            <button type="button" data-id=${product.id} class="icon-minus icon" onclick="minus(this)">
                                                <i class="fa-solid fa-minus"></i>
                                            </button>
                                        </small>
                                        <span data-id="1"></span>
                                        <span data-quanty="2"></span>
                                    </div>
                                    <span class="text-muted">${product.price}</span>
                    </li>                        
               `
    })
    let  checkoutCart = document.querySelector(".checkout-cart");
    if ( html != "") {
        checkoutCart.innerHTML=html + `<span class="sum-price-of-checkout" >Tổng tiền ${sumPrice}</span>`;
    }
}
renderListProduct();
let plus =(e)=>{
    let id = e.getAttribute("data-id")
    let currentNumber = Number(e.nextElementSibling.innerHTML);
    if(currentNumber< 10){
        currentNumber +=1;
        e.nextElementSibling.innerHTML=currentNumber;
        changeQuanty(id,currentNumber)
    }
}
let minus =(e)=>{
    let id = e.getAttribute("data-id")
    let currentNumber =Number(e.previousElementSibling.innerHTML);
    if(currentNumber >1){
        currentNumber -=1;
        e.previousElementSibling.innerHTML=currentNumber;
        changeQuanty(id,currentNumber)
    }
}

let changeQuanty=(id,quanty)=>{
    id=parseInt(id);
    quanty= parseInt(quanty);
    let dataOrders = getProductToLocalStorage();
    let listProduct = dataOrders.listProduct;
    let count = dataOrders.count;
    let sumPrice = dataOrders.sumPrice;
    let total = dataOrders.total;
    total = (total -  count[id]) + quanty;
    listProduct.forEach(product=>{
        if(product.id === id ) sumPrice = (sumPrice - product.price*count[id]) + (product.price*quanty) ;
    })
    count[id] = quanty;
    localStorage.setItem("count",JSON.stringify(count));
    localStorage.setItem("sumPrice",JSON.stringify(sumPrice));
    localStorage.setItem("total",JSON.stringify(total));
    renderListProduct();
}
let formCheckout = document.querySelector(".checkout");
function validatePhoneNumber(phoneNumber) {
    var regexPattern = /^([0]|84|^)[0-9]{8,9}$/;
    return regexPattern.test(phoneNumber);
}
formCheckout.onsubmit =(e)=>{
    e.preventDefault();
    let isSubmit = true;
    let inputs = document.querySelectorAll(".check");
    inputs.forEach(input=>{
        if(!input.value){
           let s=  " Vui nhập " + input.getAttribute("placeholder").toLocaleLowerCase();
           isSubmit=false;
           input.nextElementSibling.innerHTML=s;
           input.oninput=()=>{
               if(input.value){
                   input.nextElementSibling.innerHTML="";
               }
               else{
                   input.nextElementSibling.innerHTML=s;
               }
           }
        }
        else if(input.getAttribute("name") === "kh_dienthoai"){
            if(!validatePhoneNumber(input.value))
                input.nextElementSibling.innerHTML="Vui lòng nhập đúng số điện thoại";
        }else isSubmit=true;
    })
    if(isSubmit) {
        let name =$("#kh_ten").val();
        let address = $("#kh_diachi").val();
        let numberPhone = $("#kh_dienthoai").val();
        let email = $("#kh_email").val();
        let typeRadios = document.getElementsByName("httt_ma");
        let selectedGenderValue;

        for (let i = 0; i < typeRadios.length; i++) {
            if (typeRadios[i].checked) {
                selectedGenderValue = typeRadios[i].value;
                break;
            }
        }
        let type = selectedGenderValue;
        let dataProduct = getProductToLocalStorage();
        let listProduct = dataProduct.listProduct;
        let quanty = dataProduct.count;
        let sumPrice = dataProduct.sumPrice
        let formSubmit ={
            name,
            address,
            numberPhone,
            email,
            type,
            listProduct,
            quanty,
            sumPrice,

        }
        $.ajax({
            url: '/checkout/order',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formSubmit),
            success: function(response) {
                alert("Submit thành công!");
            },
            error: function(xhr, status, error) {
                console.log(error);
            }
        });
    };
}