function reload() {
    let id = $("#id").val();
    const url = '/restaurants/order/'+id+'/order-items-data';
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            $("#orderitems").html(data);
            console.log(data);
        }
    });
}

setInterval(reload, 2000);