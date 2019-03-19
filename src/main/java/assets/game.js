var isSetup = true;
var placedShips = 0;
var game;
var shipType;
var vertical;
var isSonar = false;
var trackFirstHit = 0;
var written = [];
var numMoves = 0;


var clicks = 0;

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
    if (sonarOn == false) {
        document.getElementById('sonar_button').style.display='none';
    }

}

var sonarOn = false;
var trackFirstHit = 0;

function markHits(board, elementId, surrenderText) {

    board.attacks.forEach((attack) => {
        let className;
        if (attack.result === "MISS" || attack.result === "PROTECTED"){
            className = "miss";
        }
        else if (attack.result === "HIT")
            className = "hit";
        else if (attack.result === "SUNK"){
            className = "hit";
            trackFirstHit++;
            if(trackFirstHit == 1){
               sonarOn = true;
               var sonarBut = document.getElementById('sonar_button');
               sonarBut.style.display = "block";
            }
           /* if (sonarOff == true){
               var button = document.getElementById('sonar_button');
               button.style.display = "block";
               trackFirstHit = false; //will never be changed
               console.log('At least I get here');
            }*/
        }
        else if (attack.result === "SURRENDER")
            alert(surrenderText);
        else if (attack.result === "REVEALED"){
            className = "revealed";
            //console.log("in revealed else if block");
        }
        else if (attack.result === "OCCUPIED"){
            className = "occupied";
        }
        //console.log(attack.location.row-1);
        //console.log(attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0));
           document.getElementById(elementId).rows[attack.location.row-1].cells[attack.location.column.charCodeAt(0) - 'A'.charCodeAt(0)].classList.add(className);
        //console.log(className);
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
    // every time the grid is redrawn, the conditions are checked for move and laser
    shipsRemaining();
    checkLaserConditions();
    checkButtonConditions();
}

//written array holds the string names of the ships that already appear in the "Sunk Ships" box
function shipsRemaining(){
    //all the ships with "Sunk" as AtackStatus
    var sunkShips = game.opponentsBoard.sunkShips;

    //checks if a new ship has been added to board's sunk ships list,
        // if there's a new ship that has been sunk, add it to the list to write to the box
    sunkShips.forEach(function(ship){
      //console.log("Ship: ");
      if(!written.includes(ship.kind)){
      written.push(ship.kind);
      var para = document.createElement('button');
      para.textContent = ship.kind;
      var sunkship = document.getElementById("sunk-ships");
      sunkship.appendChild(para);
      }

    });
}

var firstLaser = true;
function checkLaserConditions(){

    if(written.length >= 1){
        if(firstLaser){
            alert("Laser mode activated");
            //some java functionality here
            //change global var so alert doesn't show
            firstLaser = false;
        }
    }

}

function checkButtonConditions(){

    let sonarBut = document.getElementById("sonar_button");
    if(sonarOn == true){
       sonarBut.style.display = "block";
    } else{
       sonarBut.style.display = "none";
    }

    let moveButtons = document.getElementsByClassName("move");

    //if more than two ships have been sunk and there are still moves available
    //then remove the hidden variable from all the buttons and give them event listener
    if(written.length >= 2){
        if(numMoves <= 1){
            if(moveButtons[0].classList.contains('hidden')){
                for(var i = 0; i < moveButtons.length; i++){
                    moveButtons[i].classList.remove('hidden');
                }
           //addMoveButtonEventListeners();
            }
        }
    }

    if(numMoves >= 2){
        if(moveButtons[0].classList.contains('hidden')){
            //nothing should happen here
        } else {
            for(var i = 0; i < moveButtons.length; i++){
                moveButtons[i].classList.add('hidden');
            }
        }
    }

    //if all the moves have been used and the buttons are not hidden, rehide them from the user

}

function addMoveButtonEventListeners(){
    let moveButtons = document.getElementsByClassName('move');
    for(var i = 0; i < moveButtons.length; i++){
        moveButtons[i].addEventListener('click', function(e){
            numMoves++;
            checkButtonConditions();
            console.log("move button clicked! " + numMoves);
        })
    }

    document.getElementById('fleet_up').addEventListener('click', function(){
        moveShips('u');
    });
    document.getElementById('fleet_down').addEventListener('click', function(){
        moveShips('d');
    });
    document.getElementById('fleet_left').addEventListener('click', function(){
        moveShips('l');
    });
    document.getElementById('fleet_right').addEventListener('click', function(){
        moveShips('r');
    });
}

function moveShips(direction){

    sendXhr("POST", "/moveFleet", {game: game, direction: direction}, function(data) {
                            game = data;
                            redrawGrid();
    })
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
            if (placedShips == 4) {
                isSetup = false;
                registerCellListener((e) => {});
            }
        });
    }
    else {
        if(isSonar){
            sendXhr("POST", "/sonar", {game: game, x: row, y: col }, function(data) {
                game = data;
                redrawGrid();
                isSonar = false;
            })
        } else {
            sendXhr("POST", "/attack", {game: game, x: row, y: col}, function(data) {
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

function place(size, kind) {
    return function() {
        let row = this.parentNode.rowIndex;
        let col = this.cellIndex;
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
        if(kind === "submarine"){
            let cell;
            if(vertical){
                cell = table.rows[row+2].cells[col+1];
            }
            else {
                cell = table.rows[row-1].cells[col+2];
            }
            if(cell === undefined){

            }
            else {
                cell.classList.toggle("placed");
            }
        }
    }
}

 let sonarBut = document.getElementById('sonar_button').addEventListener("click", function(e){
    //is attack hit in miss
    //if (attack.result === "HIT"){
    //markHits(game.opponentsBoard, "opponent", "");
    clicks++;
    if (clicks >= 2){
        console.log("setting the button off");
        sonarOn = false;
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
       registerCellListener(place(2, "mindsweeper"));
    });
    document.getElementById("place_destroyer").addEventListener("click", function(e) {
        shipType = "DESTROYER";
       registerCellListener(place(3, "destroyer"));
    });
    document.getElementById("place_battleship").addEventListener("click", function(e) {
        shipType = "BATTLESHIP";
       registerCellListener(place(4, "battleship"));
    });
    document.getElementById("place_submarine").addEventListener("click", function(e) {
         shipType = "SUBMARINE";
         registerCellListener(place(4, "submarine"));
    });
    document.getElementById("is_vertical").addEventListener("click", function(e){
        if(vertical){
            vertical=false;
        }else{
            vertical=true;
        }
    });
     addMoveButtonEventListeners();
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

function dBox() {
    var dirbox = document.getElementById("vis")
    if (dirbox.style.visibility == 'hidden') {
        dirbox.style.visibility = "visible";
    } else {
        dirbox.style.visibility = "hidden";
    }
}