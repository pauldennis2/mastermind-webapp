Welcome to the Mastermind Readme Show! I'm your host, Paul Dennis. Let's get started.

Mastermind is a "code-breaking" game invented by Mordecai Meirowitz.
For anyone unfamiliar with the game, Wikipedia provides a great description and info:
https://en.wikipedia.org/wiki/Mastermind_(board_game)
There is also a full description with visual examples included within the app (help.html). I will provide a brief
description here:

The computer will generate a "secret code" which consists of a row of (by default 4) colored pegs. The player attempts
to guess the secret code. Each time they guess, the computer will tell them how many of the pegs were the right color,
but in the wrong spot, and how many were right color, right spot.

This app is hosted on heroku here:
https://tiy-paul-mastermind.herokuapp.com
Enjoy!

(Please note that I am currently using the free version of heroku. What this means for you is that if no one has
accessed the app recently, it may take a while (around 1-2 minutes) to "wake up")

This was my first real AngularJS app, and I learned a lot about Angular and JavaScript in this process (though I still
have a long way to go).

Features:
* Drag and drop interface using the Angular Drop. Learning how to implement this library into a workable drag and drop
interface for Mastermind was the biggest challenge for me in this app. Making this interface be truly dynamic and scale
to the size of the board was a great extension.
* Configurable difficulty/options. Players can choose:
  * Whether duplicate colors can show up in the secret code (default: no)
  * How many colors are possible (2-6, default 6)
  * The size of the board, i.e. how many pegs in a row (2-8, default 8)
  * The number of guesses allowed to solve the puzzle


Extensions:
* Draggable droppables. In other words, being able to drag the pegs that have just been dropped and swap them.
* Login/scoring with a database. The app will ask users to log in, give the user the ability to save a game and return
to it later. Leaderboards and "ranked" games could be a part of this.
* Implement an algorithm to solve the board in 5 moves (see Donald Knuth's algorithm described on the Wikipedia page)
  * As a step towards this, develop a function that calculates the number of possibilities remaining and shows this in
  a progress bar. (Each guess will presumably remove some possibilities, and the progress bar will fill up)
