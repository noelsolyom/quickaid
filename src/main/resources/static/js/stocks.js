function getStocks(chart, city, location) {
    fetch('https://quick-aid-snz.herokuapp.com/stocks?city=' + city + "&location=" + location)
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            createStocks(data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function createStocks(data) {
    let chart = document.getElementById("chart");

    let chartData = "";
    chart.innerHTML = chartData;

    if (data.data.length > 0) {
        for (let i = 0; i < data.data.length; i++) {
            chartData += drawStock(data.data[i]);
        }
    } else {
        chartData = `<p class="fs-1">Nincs megjeleníthető készlet.</p>`;
    }

    chart.innerHTML = chartData;
}

function drawStock(stock) {
    let level = null;
    let percent = null;
    let keyInput = document.getElementById("key").value.length > 9 ? "visible" : "hidden";
    switch (stock.value) {
        case 0:
            level = "bg-danger";
            percent = 1;
            break;
        case 1:
        case 2:
        case 3:
        case 4:
            level = "bg-danger";
            percent = 100 / 20 * stock.value;
            break;
        case 5:
        case 6:
        case 7:
        case 8:
            level = "bg-warning";
            percent = 100 / 20 * stock.value;
            break;
        case 9:
        case 10:
        case 11:
        case 12:
            level = "";
            percent = 100 / 20 * stock.value;
            break;
        case 13:
        case 14:
        case 15:
        case 16:
            level = "bg-info";
            percent = 100 / 20 * stock.value;
            break;
        case 17:
        case 18:
        case 19:
        case 20:
            level = "bg-success";
            percent = 100 / 20 * stock.value;
            break;
        default:
            level = "bg-warning";
            percent = 100;
    }

    return `<p class="fs-5">${stock.name}</p>
            <button type="button" class="btn btn-warning contributor" onclick="removeStock(this)" stock_name="${stock.name}" style="visibility: ${keyInput}")>Fogyasztás</button>
            <button type="button" class="btn btn-success contributor" onclick="addStock(this)" stock_name="${stock.name}" style="visibility: ${keyInput}")>Feltöltés</button>
            <button type="button" class="btn btn-danger contributor" onclick=deleteStock(this) stock_name="${stock.name}" style="visibility: ${keyInput}; float: right">Törlés</button>
            <div class="progress" style="margin-top: 1rem">
                <div class="progress-bar progress-bar-striped ${level}" role="progressbar" style="width: ${percent}%"
                    aria-valuenow="${percent}" aria-valuemin="0" aria-valuemax="100"></div>
            </div>
            <br></br>`
}