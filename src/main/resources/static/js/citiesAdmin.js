function setNewCity() {
    let key = document.getElementById("key").value;
    let city = document.getElementById("city");

    fetch('https://quick-aid-snz.herokuapp.com/cities/?city=' + city.value, {
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
                city.value = "";
                createCityOptions(data);
                let locationList = document.getElementById("locations");
                removeLocationOptions(locationList);
            } else {
                alert(data.httpStatus);
            }
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function deleteCity() {
    let key = document.getElementById("key").value;
    let city = document.getElementById("city");
    fetch('https://quick-aid-snz.herokuapp.com/cities/?city=' + city.value, {
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
                city.value = "";
                createCityOptions(data);
                let locationList = document.getElementById("locations");
                removeLocationOptions(locationList);
            } else {
                alert(data.httpStatus);
            }

        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function fillCity(value) {
    let city = document.getElementById("city");
    city.value = value;
    getLocations(value);
}