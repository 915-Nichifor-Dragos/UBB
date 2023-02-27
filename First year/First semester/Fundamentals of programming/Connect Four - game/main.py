"""

@author: Nichifor Dragos

"""
from board_game.board import Board
from game.game import Game
from player.computer_player import Computer
from player.human_player import Human
from strategy.strategy_easy import Easy

if __name__ == '__main__':
    board = Board(6, 7, '-')
    human = Human(board, 0)
    strategy = Easy()
    computer = Computer(board, 1, strategy)
    game = Game(board, human, computer)
    game.start_game()
