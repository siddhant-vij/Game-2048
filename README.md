## [Game 2048](https://codegym.cc/projects/games/com.codegym.games.game2048)

### Rules:
- The game board is square. The standard board size is 4x4 tiles. The objective of the game is to get a tile whose value is 2048.
- A tile with a value of 2 (probability of 90%) or 4 (probability of 10%) appears in each round.
- You can shift all the tiles on the game board to one of the 4 sides by pressing the corresponding key: up, down, right, or left. If a shift causes two tiles with the same value to "collide", then they merge into one. The new value is the sum of the merged tiles. After each move, a new tile with a value of 2 or 4 appears in an empty space on the game board. If pressing a key does not cause the tiles to change locations or values, then no turn has been made.
- If more than two tiles of the same value are in the same row or column, then they merge from the side from which they are being shifted. For example, after a shift to the left, the tiles (4, 4, 4, 0) in the same row become (8, 4, 0, 0). After a shift to the right, they become (0, 0, 4, 8). This way of handling ambiguity makes it possible to more accurately formulate a game strategy.
- With each merger, the score increases by the value of the resulting tile.
- The game is lost if no further moves are possible.
- The game ends in victory if a tile with the value 2048 appears on the game board.

<br>

### Future Tasks:
- Change the size of the game board.
- Continue the game even if a tile with the value 2048 is found.
- Instead of the numbers 2 and 4 (powers of two), use Fibonacci numbers.
- Add a "super-tile" that can merge with any existing tile on the game board.
- Add visual effects (animation).