function addToCart(phoneId, url) {
    $.ajax({
        url: url,
        type: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        data: JSON.stringify({
            phoneId: phoneId,
            quantity: $('#quantity-' + phoneId).val()
        }),
        success: function (response) {
            updateMiniCart();
            showAddToCartResponse('ok', 'Successfully added to cart', phoneId);
        },
        error: function (errorResponse) {
            showAddToCartResponse('error', errorResponse.responseText, phoneId);
        }
    })
}

function showAddToCartResponse(status, message, phoneId) {
    let responseMessage = $('#quantity-message-' + phoneId);
    responseMessage.text(message);
    responseMessage.css("color", 'ok' === status ? "green" : "red");
    responseMessage.show();
}
