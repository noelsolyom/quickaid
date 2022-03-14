function getCities() {
    fetch('http://localhost:8080/cities')
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            createCityOptions(data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function createCityOptions(data) {
    let cityList = document.getElementById("cities");
    removeCityOptions(cityList);

    let defaultValue = document.createElement("option");
    defaultValue.setAttribute("disabled", true);
    defaultValue.setAttribute("selected", true);
    defaultValue.innerHTML = "---Városok---";
    cityList.appendChild(defaultValue);

    for (let i = 0; i < data.data.length; i++) {
        let option = document.createElement("option");
        let city = data.data[i].city;
        option.setAttribute("value", city);
        option.innerHTML = city;
        cityList.appendChild(option);
    }
}

function removeCityOptions(selectElement) {
    var i, L = selectElement.options.length - 1;
    for (i = L; i >= 0; i--) {
        selectElement.remove(i);
    }
}

document.addEventListener('DOMContentLoaded', function () {
    getCities();
}, false);
