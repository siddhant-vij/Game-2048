package com.game2048;

import com.codegym.engine.cell.*;

public class Game2048 extends Game {
  private static final int SIDE = 4;
  private int[][] gameField = new int[SIDE][SIDE];
  private boolean isGameStopped = false;
  private int score = 0;

  @Override
  public void initialize() {
    setScreenSize(SIDE, SIDE);
    createGame();
    drawScene();
  }

  private void createGame() {
    gameField = new int[SIDE][SIDE];
    createNewNumber();
    createNewNumber();
  }

  private void drawScene() {
    for (int i = 0; i < SIDE; i++) {
      for (int j = 0; j < SIDE; j++) {
        setCellColoredNumber(i, j, gameField[j][i]);
      }
    }
  }

  private void createNewNumber() {
    if (getMaxTileValue() >= 2048) {
      win();
      return;
    }
    int x = getRandomNumber(SIDE);
    int y = getRandomNumber(SIDE);
    if (gameField[x][y] == 0) {
      gameField[x][y] = getRandomNumber(10) == 9 ? 4 : 2;
    } else {
      createNewNumber();
    }
  }

  private Color getColorByValue(int value) {
    if (value == 0) {
      return Color.WHITE;
    } else if (value == 2) {
      return Color.PLUM;
    } else if (value == 4) {
      return Color.SLATEBLUE;
    } else if (value == 8) {
      return Color.DODGERBLUE;
    } else if (value == 16) {
      return Color.DARKTURQUOISE;
    } else if (value == 32) {
      return Color.MEDIUMSEAGREEN;
    } else if (value == 64) {
      return Color.LIMEGREEN;
    } else if (value == 128) {
      return Color.DARKORANGE;
    } else if (value == 256) {
      return Color.SALMON;
    } else if (value == 512) {
      return Color.ORANGERED;
    } else if (value == 1024) {
      return Color.DEEPPINK;
    } else if (value == 2048) {
      return Color.MEDIUMVIOLETRED;
    }
    return Color.NONE;
  }

  private void setCellColoredNumber(int x, int y, int value) {
    setCellValueEx(x, y, getColorByValue(value), value == 0 ? "" : String.valueOf(value));
  }

  private boolean compressRow(int[] row) {
    int insertPosition = 0;
    boolean result = false;
    for (int x = 0; x < row.length; x++) {
      if (row[x] > 0) {
        if (x != insertPosition) {
          row[insertPosition] = row[x];
          row[x] = 0;
          result = true;
        }
        insertPosition++;
      }
    }
    return result;
  }

  private boolean mergeRow(int[] row) {
    boolean result = false;
    for (int x = 0; x < row.length - 1; x++) {
      if (row[x] > 0 && row[x] == row[x + 1]) {
        row[x] += row[x + 1];
        row[x + 1] = 0;
        result = true;
        score += row[x];
        setScore(score);
      }
    }
    return result;
  }

  private void moveLeft() {
    boolean newNUmberNeeded = false;
    for (int[] row : gameField) {
      boolean wasCompressed = compressRow(row);
      boolean wasMerged = mergeRow(row);
      if (wasMerged) {
        compressRow(row);
      }
      if (wasCompressed || wasMerged) {
        newNUmberNeeded = true;
      }
    }
    if (newNUmberNeeded) {
      createNewNumber();
    }
  }

  private void moveRight() {
    rotateClockwise();
    rotateClockwise();
    moveLeft();
    rotateClockwise();
    rotateClockwise();
  }

  private void moveUp() {
    rotateClockwise();
    rotateClockwise();
    rotateClockwise();
    moveLeft();
    rotateClockwise();
  }

  private void moveDown() {
    rotateClockwise();
    moveLeft();
    rotateClockwise();
    rotateClockwise();
    rotateClockwise();
  }

  @Override
  public void onKeyPress(Key key) {
    if (isGameStopped) {
      if (key == Key.SPACE) {
        isGameStopped = false;
        score = 0;
        setScore(score);
        createGame();
        drawScene();
      } else {
        return;
      }
    }
    if (!canUserMove()) {
      gameOver();
      return;
    }
    if (key == Key.UP) {
      moveUp();
    } else if (key == Key.DOWN) {
      moveDown();
    } else if (key == Key.LEFT) {
      moveLeft();
    } else if (key == Key.RIGHT) {
      moveRight();
    } else {
      return;
    }
    drawScene();
  }

  private void rotateClockwise() {
    int[][] rotatedMatrix = new int[gameField.length][gameField[0].length];
    for (int i = 0; i < gameField.length; i++) {
      for (int j = 0; j < gameField[0].length; j++) {
        rotatedMatrix[j][gameField.length - 1 - i] = gameField[i][j];
      }
    }
    gameField = rotatedMatrix;
  }

  private int getMaxTileValue() {
    int maxTileValue = 0;
    for (int[] row : gameField) {
      for (int value : row) {
        if (value > maxTileValue) {
          maxTileValue = value;
        }
      }
    }
    return maxTileValue;
  }

  private void win() {
    isGameStopped = true;
    showMessageDialog(Color.GREEN, "YOU WIN!", Color.BLACK, 100);
  }

  private boolean canUserMove() {
    if (isGameStopped) {
      return false;
    }
    for (int x = 0; x < SIDE; x++) {
      for (int y = 0; y < SIDE; y++) {
        if (gameField[x][y] == 0) {
          return true;
        } else if (x < SIDE - 1 && gameField[x][y] == gameField[x + 1][y]) {
          return true;
        } else if (y < SIDE - 1 && gameField[x][y] == gameField[x][y + 1]) {
          return true;
        }
      }
    }
    return false;
  }

  private void gameOver() {
    isGameStopped = true;
    showMessageDialog(Color.RED, "GAME OVER", Color.BLACK, 100);
  }
}
