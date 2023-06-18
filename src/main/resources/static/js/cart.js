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
             <div class="col-12 my-product ">
                <div class="img">
                    <img class="img_my-product" src="/img/product.jpg" alt="No Img">
                </div>
                <div class="info_my-product">
                    <div class="name-product">
                        <h4>${product.name}</h4>
                    </div>
                    <div class="price m-3">
                        Đơn Giá: <span>${product.price}</span>đ
                    </div>
                    <div class="quanty m-3" >
                        <button type="button" data-id=${product.id} class="icon-plus icon" onclick="plus(this)">
                            <i class="fa-solid fa-plus">
                            </i>
                        </button>
                           <span data-id=${product.id} type="number" class="text-count m-3">${count[product.id]}</span>
                            <button type="button" data-id=${product.id} class="icon-minus icon" onclick="minus(this)">
                                 <i class="fa-solid fa-minus"></i>               
                            </button>                                                 
                    </div>
                    <div class="sum-price m-3">
                        Tổng đơn giá: <span>${Number(product.price) * Number(count[parseInt(product.id)])}</span>đ
                    </div>
                </div>
                <div class="remove-product">
                    <i data-id=${product.id} class="fa-solid fa-trash" onclick="removeProduct(this)"></i>
                </div>
            </div>                       
               `
    })
    let  prodcuts = document.querySelector(".prodcuts");
    if ( html != "") {
        prodcuts.innerHTML=html + `<span class="sum-price-of-checkout" >Tổng tiền ${sumPrice}đ</span>`;
    }else {
        prodcuts.innerHTML = "<p>Giỏ Hàng Trống</p>" +
                             "<a href='/product'>Mua hàng tại đây</a>"
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

let removeProduct =(e)=>{
    let id = e.getAttribute("data-id")
    changeQuanty(id,0);

}

let changeQuanty=(id,quanty)=>{
    id=parseInt(id);
    quanty= parseInt(quanty);
    let dataOrders = getProductToLocalStorage();
    let listProduct = dataOrders.listProduct;
    let count = dataOrders.count;
    let sumPrice = dataOrders.sumPrice;
    let total = dataOrders.total;
    if(quanty===0){
        let index;
        for(let i =0 ; i<listProduct.length; i++){
            if(listProduct[i].id === id  ) index = i;
        }
        let item =listProduct[index];
        sumPrice -= item.price * count[id];
        total -=count[id];
        count[id] =0;
        listProduct.splice(index,1);
    }

    else{
        total = (total -  count[id]) + quanty;
        listProduct.forEach(product=>{
            if(product.id === id ) sumPrice = (sumPrice - product.price*count[id]) + (product.price*quanty) ;
        })
        count[id] = quanty;
    }
    localStorage.setItem("listProduct",JSON.stringify(listProduct));
    localStorage.setItem("count",JSON.stringify(count));
    localStorage.setItem("sumPrice",JSON.stringify(sumPrice));
    localStorage.setItem("total",JSON.stringify(total));
    renderListProduct();
}