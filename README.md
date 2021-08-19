# TicTacTecToe
## Installation
**Download TicTacTecToe.jar** and start it by clicking on it.<br>
Link: https://github.com/DustinScharf/TicTacTecToe/releases/download/v1.1/TicTacTecToe.jar <br>
_(Requires Java 11)_
## How does the game work?
First select the game mode and the game will start.

![Demo GIF could not load, read the tutorial below](gameDemo.gif)

1. **Selection Phase**<br>
Select a PLACER, the player sending the higher PLACER starts this round<br>
PLACERs are the numbers above / below the 3x3 board

2. **Place Phase**<br>
Select a PLACER, and place it on the board<br>
Placing is possible on an empty field or a field with a lower placer on it

3. **Win Check**<br>
If a player hits 3 in a row (like normal TicTacToe) the players wins<br>
If a player can not set anything, the other player wins<br>
If none of them holds, the selection phase starts again

<hr>

#### Note: Project for trying and learning
_The Project is used for trying and learning JavaFX and network programming stuff.<br>
The network traffic is minimal for hosting on a private computer,
but the network is not designed secure._