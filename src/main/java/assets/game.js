var isSetup = true;
var placedShips = 0;
var game;
var shipType;
var vertical;
var isSonar = false;
var trackFirstHit = 0;

function makeGrid(table, isPlayer) {

    for (i=0; i<10; i++) {
        let row = document.createElement('tr');
        for (j=0; j<10; j++) {
            let column = document.createElement('td');
            column.addEventListener("click", cellClick);
            row.appendChild(column);
        }
        table.appendChild(row);
    }
    document.getElementById('sonar_button').style.display='none';
}



function markHits(board, elementId, surrenderText) {
    board.attacks.forEach((attack) => {
        let className;

        if (attack.result === "MISS")
            className = "miss";
        else if (attack.result === "HIT")
            className = "hit";
        else if (attack.result === "SUNK"){
            className = "hit";
            trackFirstHit++;
            if (trackFirstHit >= 1){
               var button = document.getElementById('sonar_button');
               button.style.display = "block";
               console.log('At least I get here');
            }
        }
        else if (attack.result === "SURRENDER")
            alert(surrenderText);
        else if (attack.result === "REVEALED")
            className = "revealed";
        else if (attack.result === "OCCUPIED")
            className = "occupied";
        //console.log(attack.location.row-1);
        //console.log(attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0));
        document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className);
    });

}

function redrawGrid() {
    Array.from(document.getElementById("opponent").childNodes).forEach((row) => row.remove());
    Array.from(document.getElementById("player").childNodes).forEach((row) => row.remove());
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    if (game === undefined) {
        return;
    }

    game.playersBoard.ships.forEach((ship) => ship.occupiedSquares.forEach((square) => {
        document.getElementById("player").rows[square.row-1].cells[square.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add("occupied");
    }));
    markHits(game.opponentsBoard, "opponent", "You won the game");
    markHits(game.playersBoard, "player", "You lost the game");
}

var oldListener;
function registerCellListener(f) {
    let el = document.getElementById("player");
    for (i=0; i<10; i++) {
        for (j=0; j<10; j++) {
            let cell = el.rows[i].cells[j];
            cell.removeEventListener("mouseover", oldListener);
            cell.removeEventListener("mouseout", oldListener);
            cell.addEventListener("mouseover", f);
            cell.addEventListener("mouseout", f);
        }
    }
    oldListener = f;
}

function cellClick() {
    let row = this.parentNode.rowIndex + 1;
    let col = String.fromCharCode(this.cellIndex + 65);
    if (isSetup) {
        sendXhr("POST", "/place", {game: game, shipType: shipType, x: row, y: col, isVertical: vertical}, function(data) {
            game = data;
            redrawGrid();
            placedShips++;
            if (placedShips == 3) {
                isSetup = false;
                registerCellListener((e) => {});
            }
        });
    }
    else {
        if(isSonar){
            sendXhr("POST", "/attack", {game: game, x: row, y: col, isSonar: true}, function(data) {
                game = data;
                redrawGrid();
                isSonar = false;
            })
        } else {
            sendXhr("POST", "/attack", {game: game, x: row, y: col, isSonar: false}, function(data) {
                            game = data;
                            redrawGrid();
                        })
        }
    }
}

function sendXhr(method, url, data, handler) {
    var req = new XMLHttpRequest();
    req.addEventListener("load", function(event) {
        if (req.status != 200) {
            alert("Cannot complete the action");
            return;
        }
        handler(JSON.parse(req.responseText));
    });
    req.open(method, url);
    req.setRequestHeader("Content-Type", "application/json");
    req.send(JSON.stringify(data));
}

function place(size) {
    return function() {
        let row = this.parentNode.rowIndex;
        let col = this.cellIndex;
        //vertical = document.getElementById("is_vertical").checked;
        let table = document.getElementById("player");
        for (let i=0; i<size; i++) {
            let cell;
            if(vertical) {
             let tableRow = table.rows[row+i];
                if (tableRow === undefined) {
                    // ship is over the edge; let the back end deal with it
                    break;
                }
                cell = tableRow.cells[col];
            } else {
                cell = table.rows[row].cells[col+i];
            }
            if (cell === undefined) {
                // ship is over the edge; let the back end deal with it
                break;
            }
            cell.classList.toggle("placed");
        }
    }
}

 var clicks = 0
 let sonarBut = document.getElementById('sonar_button').addEventListener("click", function(e){
    //is attack hit in miss
    //if (attack.result === "HIT"){
        //markHits(game.opponentsBoard, "opponent", "");
    ++clicks;
     if (clicks >= 2){
             document.getElementById("sonar_button").style.display = "none";
         }
    console.log("clicks:", clicks);
     isSonar = true;
     opponentCellListener(sonarHover());
    })

function initGame() {
    makeGrid(document.getElementById("opponent"), false);
    makeGrid(document.getElementById("player"), true);
    document.getElementById("place_minesweeper").addEventListener("click", function(e) {
        shipType = "MINESWEEPER";
       registerCellListener(place(2));
    });
    document.getElementById("place_destroyer").addEventListener("click", function(e) {
        shipType = "DESTROYER";
       registerCellListener(place(3));
    });
    document.getElementById("place_battleship").addEventListener("click", function(e) {
        shipType = "BATTLESHIP";
       registerCellListener(place(4));
    });
    document.getElementById("is_vertical").addEventListener("click", function(e){
        if(vertical){
            vertical=false;
        }else{
            vertical=true;
        }
    });
     sendXhr("GET", "/game", {}, function(data) {
                game = data;
     })
}

var oldOppListener;
function opponentCellListener(f) {
    let el = document.getElementById("opponent");
    for (i=0; i<10; i++) {
        for (j=0; j<10; j++) {
            let cell = el.rows[i].cells[j];
            cell.removeEventListener("mouseover", oldOppListener);
            cell.removeEventListener("mouseout", oldOppListener);
            cell.addEventListener("mouseover", f);
            cell.addEventListener("mouseout", f);
        }
    }
    oldOppListener = f;
}

function sonarHover() {
    return function() {
        let row = this.parentNode.rowIndex;
        let col = this.cellIndex;
        let table = document.getElementById("opponent");
        let cell;
        //create the hover effect vertically
        for(let i=-2; i<3; i++){
            let tableRow = table.rows[row+i];
            if(tableRow == undefined){
                break;
            }
            cell=tableRow.cells[col];
            cell.classList.toggle("sonar");
            //shade the columns for the outer square
            if(i>-2 && i<2){
               cell = tableRow.cells[col + 1];
               //check each cell to clear console errors/if the radar goes off the board
               if(cell == undefined){
                    break;
               }
               cell.classList.toggle("sonar");
               cell = tableRow.cells[col - 1];
               if(cell == undefined){
                    break;
               }
               cell.classList.toggle("sonar");
               //if it is the middle row, add two additional cells to the list
               if(i == 0){
                    cell = tableRow.cells[col + 2];
                    if(cell == undefined){
                        break;
                    }
                    cell.classList.toggle("sonar");
                    cell = tableRow.cells[col - 2];
                    if(cell == undefined){
                         break;
                    }
                    cell.classList.toggle("sonar");
                    cell = tableRow.cells[col];
                    if(cell == undefined){
                        break;
                    }
               }
            }
        }
    }
    sonarBut.removeEventListener("click", sonarHover());
}