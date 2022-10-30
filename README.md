# TicTacTecToe

## Installation
**Download [**TicTacTecToe**.jar](https://github.com/DustinScharf/TicTacTecToe/releases/download/v1.2/TicTacTecToe.jar "Click here to download TicTacTecToe.")** and start it by double-clicking on it or from the terminal / cmd (normaly found in the downloads directory / folder).  
_(Requires Java 11 or higher / newer.)_  

Link: https://github.com/DustinScharf/TicTacTecToe/releases/download/v1.2/TicTacTecToe.jar <br>

## How does a TicTacTecToe game / turn work?
First select a game mode and the game will start.

![This is a demo GIF, alternatively read the tutorial below.](gameDemo.gif "An example game versus a bot")

1. **Selection Phase** <br>
Select a PLACER (by a click / touch), the player sending the higher PLACER starts this round<br>
PLACERs are the numbers above / below the 3x3 board.

2. **Place Phase** <br>
Select a PLACER (by a click / touch), and place it on the 3x3 board<br>
Placing is possible on an empty field or a field with a lower placer on it.

3. **Win Check** <br>
If a player hits 3 PLACERS in a row (like normal TicTacToe, including diagonal) the players wins <br>
If a player can not set anything, the other player wins (no tie) <br>
If none of them holds, the selection phase (see above) starts again.

<hr>

#### Please Note: Project for trying and learning
_The Project is used for trying and learning first JavaFX and network programming stuff.  
The network traffic is minimal for hosting on a private computer,
but notice the network part is not designed secure / (cheat-)fair._
