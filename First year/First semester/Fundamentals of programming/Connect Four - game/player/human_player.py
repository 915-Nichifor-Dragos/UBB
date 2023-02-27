"""

@author: Nichifor Dragos

"""
from player.player import Player


class Human(Player):
    def __init__(self, board, value):
        super().__init__(board, value)

    def move(self, line, column):
        """
        Sets the value on the given line and column to 0

        :param line: the line
        :param column: the column
        :return:
        """
        self._board.set_value_element(line, column, 0)
