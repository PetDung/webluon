let isPay =(id)=>{
    $.ajax({
        url: `/admin/pay/${id}`,
        type: 'PUT',
        success: function(product) {
            console.log("oke");
        },
        error: function(xhr, status, error) {
            console.error('Error: ' , error);
        },
    });
}
function togglePaymentStatus(button) {
    let id = button.getAttribute("data-id");
    isPay(id);
    let idFather = button.getAttribute("data-remove");
    let father = document.getElementById(idFather);
    let product = father.nextElementSibling;
    father.remove();
    product.remove();
    if (button.classList.contains('unpaid')) {
        button.classList.remove('unpaid');
        button.classList.add('paid');
        button.textContent = 'Đã thanh toán';

    } else {
        button.classList.remove('paid');
        button.classList.add('unpaid');
        button.textContent = 'Chưa thanh toán';
    }}