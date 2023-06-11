// count product

let plus =()=>{
    let textCount = document.querySelector(".text-count");
    let currentNumber = Number(textCount.value);
    if(currentNumber< 10){
        currentNumber +=1;
        textCount.value=currentNumber;
    }
}
let minus =()=>{
    let textCount = document.querySelector(".text-count");
    let currentNumber = Number(textCount.value);
    if(currentNumber >1){
        currentNumber -=1;
        textCount.value=currentNumber;
    }
}