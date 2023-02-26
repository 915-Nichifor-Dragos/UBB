"""

@author: Nichifor Dragos

"""
import random

from strategy.strategy import Strategy


class Easy(Strategy):
    def execute(self, board):
        super().execute(board)
        self.check_if_computer_can_win(board)

    def check_if_human_can_win(self, board):
        """
        Checks if human can win next round and blocks is future winning move

        :param board: the board
        :return:
        """
        ok = False
        choices = board.return_all_possible_choices()
        for i in range(len(choices)):
            if choices[i] != -1:
                line, column = board.check_if_won_with_move_computer(choices[i], i, 0)
                if line != -1 and column != -1:
                    board.set_value_element(line, column, 1)
                    ok = True
                    break
        if ok is False:
            self.move_random(board)

    def check_if_computer_can_win(self, board):
        """
        Checks if computer can win, and, if possible, moves and wins the match

        :param board: the board
        :return:
        """
        ok = False
        choices = board.return_all_possible_choices()
        for i in range(len(choices)):
            if choices[i] != -1:
                line, column = board.check_if_won_with_move_computer(choices[i], i, 1)
                if line != -1 and column != -1:
                    board.set_value_element(line, column, 1)
                    ok = True
                    break
        if ok is False:
            self.check_if_human_can_win(board)

    @staticmethod
    def move_random(board):
        """
        In case the computer can't win and neither the human next round, the computer moves randomly on a column

        :param board: the board
        :return:
        """
        choices = board.return_all_possible_choices()
        while True:
            k = random.randint(0, 6)
            if choices[k] != -1:
                board.set_value_element(choices[k], k, 1)
                break
