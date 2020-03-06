function addToCart(phoneId, url) {
    $.post({
        url: url,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        data: JSON.stringify({
            phoneId: phoneId,
            quantity: $('#quantity-' + phoneId).val()
        }),
        dataType: 'json',
        success: function (response) {
            let responseMessage = $('#quantity-message-' + phoneId);
            responseMessage.text(response['message']);
            responseMessage.css("color", response['status'] === 'ok' ? "green" : "red");
            responseMessage.show();
        }
    })
}
