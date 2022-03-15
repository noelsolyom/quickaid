function getTrain() {
    let from = document.getElementById("from").value;
    let to = document.getElementById("cities").value;

    fetch('http://localhost:8080/elvira?from=' + from + "&to=" + to)
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            if (data.httpStatus == "OK") {
                alertTrains(data);
            } else {
                alert("Sajnos nem található vonat.");
            }


        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function alertTrains(data) {
    console.log(data.data);
    let message = "A legközelebbi három vonat a mai napon: \n"
    for (let i = 0; i < data.data.length; i++) {
        console.log(data.data[i]);
        message += `indul: ${data.data[i].startTime} - állomás: ${data.data[i].start} - érkezik: ${data.data[i].destinationTime} - állomás:${data.data[i].destination}\n`
    }
    alert(message);
}