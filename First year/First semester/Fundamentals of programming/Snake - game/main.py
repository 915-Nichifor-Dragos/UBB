"""

@author: Nichifor Dragos

"""
from board.board import Board
from ui.game import Game
from validators.command_validator import CommandValidator


def read_n_and_apples():
    filename = 'settings'
    f = open(filename)
    line = f.readlines()
    n = line[0]
    n.strip()
    apples = line[1].strip()
    apples.strip()
    apples = int(apples)
    n = int(n)
    return n, apples


if __name__ == '__main__':
    n, apples = read_n_and_apples()
    board = Board(n, apples)
    validator = CommandValidator
    game = Game(board, validator)
    game.game()
