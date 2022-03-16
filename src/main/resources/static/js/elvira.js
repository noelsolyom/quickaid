function getTrain() {
    let from = document.getElementById("from").value;
    let to = document.getElementById("cities").value;
    document.getElementById("train").disabled = true;

    fetch('https://quick-aid-snz.herokuapp.com/elvira?from=' + from + "&to=" + to)
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            if (data.httpStatus == "OK") {
                alertTrains(data, from, to);
            } else {
                alert("Sajnos nem található vonat.");
            }
            document.getElementById("train").disabled = false;


        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function alertTrains(data, from, to) {
    console.log(data.data);
    let message = `A mai napon a legközelebbi (maximum) három vonat ${from} és ${to} között:\n\n`
    for (let i = 0; i < data.data.length; i++) {
        console.log(data.data[i]);
        message += `indul: ${data.data[i].startTime} - honnan: ${data.data[i].start}\nérkezik: ${data.data[i].destinationTime} - hova: ${data.data[i].destination}\n---\n`
    }
    message = message.substring(0, message.length - 4);
    alert(message);
}