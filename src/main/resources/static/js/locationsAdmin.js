function setNewLocation() {
    let key = document.getElementById("key").value;
    let city = document.getElementById("city");
    let location = document.getElementById("location");
    let addressLine = document.getElementById("address");
    let secretKey = document.getElementById("secretKey");

    let bodyData = {
        details:
        {
            address: addressLine.value,
            latitude: null,
            longitude: null,
            key: secretKey.value
        }
    }

    fetch('http://localhost:8080/locations/?city=' + city.value + "&location=" + location.value, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'api-key': key
        },
        body: JSON.stringify(bodyData)
    })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            if (data.httpStatus == "OK") {
                location.value = "";
                addressLine.value = "";
                secretKey.value = "";
                createLocationOptions(data);
            } else {
                alert(data.httpStatus);
            }
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function deleteLocation() {
    let key = document.getElementById("key").value;
    let city = document.getElementById("city");
    let location = document.getElementById("location");
    let address = document.getElementById("address");
    fetch('http://localhost:8080/locations/?city=' + city.value + "&location=" + location.value, {
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
                location.value = "";
                address.value = "";
                createLocationOptions(data);
            } else {
                alert(data.httpStatus);
            }

        })
        .catch((error) => {
            console.error('Error:', error);
        });
}
