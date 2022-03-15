function showInputs() {
    let key = document.getElementById("key");
    let contributorElements = document.getElementsByClassName("contributor");
    if (key.value.length > 9) {
        for (let i = 0; i < contributorElements.length; i++) {
            contributorElements[i].style.visibility = "visible";
        }
    } else {
        for (let i = 0; i < contributorElements.length; i++) {
            contributorElements[i].style.visibility = "hidden";
        }
    }
}

function setNewStock() {
    let stockname = document.getElementById("stock").value;
    let city = document.getElementById("cities").value;
    let location = document.getElementById("locations").value;
    let key = document.getElementById("key").value;

    fetch('https://quick-aid-snz.herokuapp.com/stocks?city=' + city + "&location=" + location + "&stock=" + stockname, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'api-key': key
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            if (data.httpStatus == "OK") {

                createStocks(data);
                document.getElementById("stock").value = "";
            } else {
                alert(data.httpStatus);
            }

        })
        .catch((error) => {
            console.error('Error:', error);
        });

}

function deleteStock(data) {
    let stockname = null;

    if (data.hasOwnProperty("stock_name")) {
        stockname = data.stock_name;
    } else {
        stockname = data.getAttribute("stock_name");
    }
    let city = document.getElementById("cities").value;
    let location = document.getElementById("locations").value;
    let key = document.getElementById("key").value;

    fetch('https://quick-aid-snz.herokuapp.com/stocks?city=' + city + "&location=" + location + "&stock=" + stockname, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'api-key': key
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            if (data.httpStatus == "OK") {

                createStocks(data);
            } else {
                alert(data.httpStatus);
            }

        })
        .catch((error) => {
            console.error('Error:', error);
        });

}

function addStock(data) {
    let city = document.getElementById("cities").value;
    let location = document.getElementById("locations").value;
    let key = document.getElementById("key").value;
    let stockname = data.getAttribute("stock_name");

    fetch('https://quick-aid-snz.herokuapp.com/stocks/add?city=' + city + "&location=" + location + "&stock=" + stockname, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            'api-key': key
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            if (data.httpStatus == "OK") {

                createStocks(data);
            } else {
                alert(data.httpStatus);
            }
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function removeStock(data) {
    let city = document.getElementById("cities").value;
    let location = document.getElementById("locations").value;
    let key = document.getElementById("key").value;
    let stockname = data.getAttribute("stock_name");

    fetch('https://quick-aid-snz.herokuapp.com/stocks/remove?city=' + city + "&location=" + location + "&stock=" + stockname, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            'api-key': key
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            if (data.httpStatus == "OK") {

                createStocks(data);
            } else {
                alert(data.httpStatus);
            }
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}