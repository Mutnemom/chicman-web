function genCreditCardToken() {
	'use strict'

	Omise.setPublicKey('pkey_test_5bdl08brxlvpou9e3nb')

	var expDate = document.getElementById('expiry_date').value.split('/')
	var cardInfo = {
		name: document.getElementById('name_oncard').value,
		number: document.getElementById('card_number').value,
		expiration_month: expDate[0],
		expiration_year: expDate[1],
		security_code: document.getElementById('security_code').value
	}

	Omise.createToken('card', cardInfo, function (statusCode, response) {
		if (statusCode === 200) {
			receiveId(response.id)
		} else {
			receiveId('400')
		}
	});
}

function showCardNumber() {
	var card = document.getElementById('card_number').value
	alert('Card Number : ' + card)
}

function getFocused() {
	var id = document.activeElement.id

	if (id != null && id != "") {
		receiveFocused(id)
	} else {
		receiveFocused(document.activeElement.tagName)
	}
}

function showInput() {
	document.addEventListener('keydown', checkKeyDown, false)
}

// function checkKeyDown(event) {
// 	const isLetter = /[a-z]/i.test(event.key.toLowerCase())
// 	const isNumber = /[0-9]/i.test(event.key)

// 	if (isLetter) {
// 		// alert("Found letter : " + event.key.toLowerCase())
// 		return
// 	}

// 	if (isNumber) {
// 		alert("Found number : " + event.key)
// 	}
// }

function acceptNumber(input) {
	input.addEventListener("keydown", function (event) {
		if (!/[0-9]/i.test(event.key) && event.keyCode != 8 && event.keyCode != 9) {
			event.preventDefault()
			return
		}
	})
}

function acceptLetter(input) {
	input.addEventListener("keydown", function (event) {
		if (!/[a-z]/i.test(event.key.toLowerCase())) {
			event.preventDefault()
			return
		}
	})
}

function enter2ClickButton(input, button) {
	if (input && button) {
		input.addEventListener("keyup", function (event) {
			event.preventDefault()
			if (event.keyCode === 13) {
				button.focus()
				button.click()
			}
		})
	}
}

function showCartItem(items) {
	var cart = document.getElementById("cart")
	if (items != 0) {
		var spanNode = document.createElement("span")
		spanNode.className = "cart-item"
		var cartNumber = document.createTextNode(items)
		spanNode.appendChild(cartNumber)
		cart.appendChild(spanNode)
	} else {
		var spanNode = cart.lastChild
        if (spanNode.className === "cart-item") {
			cart.removeChild(spanNode)
		}
	}
}

var oldWidth
function rearrangeMainCard() {
	var mainCard = document.getElementById("products")
	var img = mainCard.getElementsByTagName("img")
	if (oldWidth === undefined) {
		oldWidth = img[0].style.width
	}
	
	for (i = 0; i < img.length; i++) {
		if (img[i].parentNode.parentNode.className.includes("hide")) {
			img[i].style.width = "0px"
		}
	}
}

var rearrangeLoop
function loopRearrangeMainCard() {
	if (rearrangeLoop !== undefined) {
		clearInterval(rearrangeLoop)
	}
	rearrangeLoop = setInterval(rearrangeMainCard, 200)
}

function showMainCard(id) {
	// clearInterval(rearrangeLoop)
	var img = document.getElementById(id)
	img.style.width = oldWidth
}

function extractUrl() {
	receiveUrl(window.location.href)
}