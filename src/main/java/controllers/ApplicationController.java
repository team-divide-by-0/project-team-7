package controllers;

import com.google.inject.Singleton;
import cs361.battleships.models.Game;
import cs361.battleships.models.Square;
import cs361.battleships.models.Ship;
import cs361.battleships.models.Board;
import ninja.Context;
import ninja.Result;
import ninja.Results;

import java.util.List;

@Singleton
public class ApplicationController {

    public Result index() {
        return Results.html();
    }

    public Result newGame() {
        Game g = new Game();
        return Results.json().render(g);
    }

    public Result placeShip(Context context, PlacementGameAction g) {
        Game game = g.getGame();
        Ship ship = new Ship(g.getShipType());
        boolean result = game.placeShip(ship, g.getActionRow(), g.getActionColumn(), g.isVertical());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
        }
    }

    public Result attack(Context context, AttackGameAction g) {
        Game game = g.getGame();
        boolean result = true;
            result = game.attack(g.getActionRow(), g.getActionColumn());
        if (result) {
            return Results.json().render(game);
        } else {
            return Results.badRequest();
        }
    }

    public Result sonar(Context context, AttackGameAction g) {
        Game game = g.getGame();
        boolean result = game.sonarAttack(g.getActionRow(), g.getActionColumn());
        //boolean result = game.attack(g.getActionRow(), g.getActionColumn());
        //if(result){
            return Results.json().render(game);
       // } else {
         //   return Results.badRequest();
       // }
        //return Results.json().render(game);

    }

    public Result moveFleet(Context context, MoveGameAction g) {
        Game game = g.getGame();
        Board playerBoard = game.getPlayersBoard();
        playerBoard.moveFleet(g.getDirection());
        return Results.json().render(game);
    }
}
