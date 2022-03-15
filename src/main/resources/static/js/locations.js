function getLocations(city) {
    fetch('https://quick-aid-snz.herokuapp.com/locations?city=' + city)
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            createLocationOptions(data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function createLocationOptions(data) {
    if (document.getElementById("train") != null) {
        document.getElementById("train").disabled = false;
    }

    let locationList = document.getElementById("locations");
    if (document.getElementById("from") != null) {
        document.getElementById("from").value = "";
    }

    let chart = document.getElementById("chart");
    if (chart != null) {
        chart.innerHTML = "";
    }
    removeLocationOptions(locationList);
    let location = document.getElementById("location");
    let addressP = document.getElementById("addressP");
    let address = document.getElementById("address");
    if (location != null) {
        location.value = "";
    }
    if (addressP != null) {
        addressP.innerHTML = "";
    }
    if (address != null) {
        address.value = "";
    }


    let defaultValue = document.createElement("option");
    defaultValue.setAttribute("disabled", true);
    defaultValue.setAttribute("selected", true);
    if (data.data.length == 0) {
        defaultValue.innerHTML = "---Ebben a városban nem találhatók segélyhelyek---";
    } else {
        defaultValue.innerHTML = "---Segélyhelyek---";
    }
    locationList.appendChild(defaultValue);

    for (let i = 0; i < data.data.length; i++) {
        let option = document.createElement("option");
        let location = data.data[i];
        option.locationData = location;
        option.innerHTML = location.name;
        locationList.appendChild(option);
    }
}

function removeLocationOptions(selectElement) {
    var i, L = selectElement.options.length - 1;
    for (i = L; i >= 0; i--) {
        selectElement.remove(i);
    }
}

function fillLocation(selectBox) {
    let location = document.getElementById("location");
    if (location != null) {
        location.value = selectBox.options[selectBox.selectedIndex].locationData.name;
    }

    let address = document.getElementById("address");
    if (address != null) {
        address.value = selectBox.options[selectBox.selectedIndex].locationData.details.address;
    }


    let addressP = document.getElementById("addressP");
    if (addressP != null) {
        addressP.innerHTML = selectBox.options[selectBox.selectedIndex].locationData.details.address;
    }

    let chart = document.getElementById("chart");
    if (chart != null) {
        let city = document.getElementById("cities").value;
        getStocks(chart, city, selectBox.options[selectBox.selectedIndex].locationData.name);
    }

}