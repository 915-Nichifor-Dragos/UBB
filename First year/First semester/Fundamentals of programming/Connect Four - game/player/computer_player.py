"""

@author: Nichifor Dragos

"""
from player.player import Player


class Computer(Player):
    def __init__(self, board, value, strategy):
        super().__init__(board, value)
        self.__strategy = strategy

    def move(self):
        """
        Initializes the move function

        :return:
        """
        self.__strategy.execute(self._board)
