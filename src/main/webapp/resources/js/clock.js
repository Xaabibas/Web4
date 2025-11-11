const clock = document.getElementById("clock");

function drawTime() {
    let now = new Date();
    let string = dateToString(now);
    clock.textContent = string;
}

function dateToString(date) {
    return (date.getHours() > 9 ? date.getHours() : "0" + date.getHours()) + ":" +
                (date.getMinutes() > 9 ? date.getMinutes() : "0" + date.getMinutes()) + ":"
                + (date.getSeconds() > 9 ? date.getSeconds() : "0" + date.getSeconds());
}

setInterval(drawTime, 12000);

drawTime();