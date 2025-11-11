const svg = document.getElementById("graph");
let pt = svg.createSVGPoint();
const centerX = 250;
const centerY = 250;
const gap = 40;

function drawGraph(event, ui) {
    const newR = ui.value;

    changeTriangle(newR);
    changeRectangle(newR);
    changeCircle(newR);
}

function changeTriangle(newR) {
    const triangle = document.getElementById("triangle");
    const newTrianglePoints = [
        [centerX, centerY],
        [centerX, centerY + newR * gap],
        [centerX + newR / 2 * gap, centerY]
    ];
    triangle.setAttribute(
        "points",
        newTrianglePoints.map(p => p.join(",")).join(" ")
    );
}

function changeRectangle(newR) {
    const rectangle = document.getElementById("rectangle");

    const newRectanglePoints = [
        [centerX, centerY],
        [centerX, centerY + newR * gap],
        [centerX - newR * gap , centerY + newR * gap],
        [centerX - newR * gap, centerY]
    ];
    rectangle.setAttribute(
        "points",
        newRectanglePoints.map(p => p.join(",")).join(" ")
    );
}

function changeCircle(newR) {
    const  circle = document.getElementById("circle");
    const startX = centerX + newR * gap;
    const startY = centerY;
    const endX = centerX;
    const endY = centerY - newR * gap;
    const sweepFlag = 0;
    const R = newR * gap;

    circle.setAttribute(
       "d",
       `M ${startX} ${startY} A ${R} ${R} 0 0 ${sweepFlag} ${endX} ${endY} L ${centerX} ${centerY} Z`
    );
}


function drawPoint(x, y, result) {
    const point = document.createElementNS("http://www.w3.org/2000/svg", "circle");
    if (result) {
        point.classList.add("hit");
    } else {
        point.classList.add("miss");
    }
    point.style.visibility = "visible";
    point.setAttribute("r", 3);

    point.setAttribute("cx", centerX + x * gap);
    point.setAttribute("cy", centerY - y * gap);
    svg.appendChild(point);
}

svg.onclick = async function clickedPoint(e) {
    const [correctX, correctY, currentR] = await calculateCorrectCoordinates(e);

    const response = await addPoint([
        {name: "x", value: correctX},
        {name: "y", value: correctY},
        {name: "r", value: currentR}
    ]);

    const result = response.jqXHR.pfArgs.result;
    drawPoint(correctX, correctY, result);
}

async function calculateCorrectCoordinates(e) {
    pt.x = e.clientX;
    pt.y = e.clientY;

    let cursorPoint = pt.matrixTransform(svg.getScreenCTM().inverse());

    let correctX = ((cursorPoint.x - centerX) / gap).toFixed(2);
    let correctY = (-(cursorPoint.y - centerY) / gap).toFixed(2);

    const res = await getR();
    const currentR = res.jqXHR.pfArgs.r;

    return [correctX, correctY, currentR];
}

async function drawButtonPoint() {
    const response = await getLastAttempt();
    const x = response.jqXHR.pfArgs.x;
    const y = response.jqXHR.pfArgs.y;
    console.log(x, y);

    const result = response.jqXHR.pfArgs.result;
    drawPoint(x, y, result);
}

function drawAttempts(attempts) {
    for (let i = 0; i < attempts.length; i++) {
        const x = attempts[i].point.x;
        const y = attempts[i].point.y;
        const result = attempts[i].result;

        drawPoint(x, y, result);
    }
}

function dotToComma(event) {
    if (event.key === '.' || event.key === 'Decimal') {
       event.preventDefault();

       var input = event.target;
       var val = input.value;
       var start = input.selectionStart;
       var end = input.selectionEnd;

       input.value = val.substring(0, start) + ',' + val.substring(end);

       input.selectionStart = input.selectionEnd = start + 1;

       if (typeof input.dispatchEvent === 'function') {
            input.dispatchEvent(new Event('input', { bubbles: true }));
       }
   }
}

window.drawAttempts = drawAttempts;
window.dotToComma = dotToComma;