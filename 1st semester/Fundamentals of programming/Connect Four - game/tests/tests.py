"""

@author: Nichifor Dragos

"""
import unittest

from board_game.board import Board
from board_game.cell import Cell
from player.computer_player import Computer
from player.human_player import Human
from strategy.strategy_easy import Easy
from validators.validate_column import ValidateColumn, ColumnException


class Tests(unittest.TestCase):
    def setUp(self) -> None:
        self.__board = Board(6, 7, '-')
        strategy = Easy()
        self.__computer = Computer(self.__board, 1, strategy)
        self.__human = Human(self.__board, 0)

    def tearDown(self) -> None:
        pass

    def test_board(self):
        self.__board.set_value_element(5, 0, 0)
        k = self.__board.return_column(0)
        self.assertEqual(k, [Cell(line=0, column=0, value='-'),
                             Cell(line=1, column=0, value='-'),
                             Cell(line=2, column=0, value='-'),
                             Cell(line=3, column=0, value='-'),
                             Cell(line=4, column=0, value='-'),
                             Cell(line=5, column=0, value=0)])
        k = self.__board.return_line(5)
        self.assertEqual(k, [Cell(line=5, column=0, value=0),
                             Cell(line=5, column=1, value='-'),
                             Cell(line=5, column=2, value='-'),
                             Cell(line=5, column=3, value='-'),
                             Cell(line=5, column=4, value='-'),
                             Cell(line=5, column=5, value='-'),
                             Cell(line=5, column=6, value='-')])
        k = self.__board.return_element(5, 0)
        self.assertEqual(k, False)
        k = self.__board.return_element(5, 1)
        self.assertEqual(k, True)
        k = self.__board.return_all_possible_choices()
        self.assertEqual(k, [4, 5, 5, 5, 5, 5, 5])
        k = self.__board.check_if_won()
        self.assertEqual(k, False)
        self.__board.set_value_element(5, 1, 1)
        self.__board.set_value_element(5, 2, 1)
        self.__board.set_value_element(5, 3, 1)
        self.__board.set_value_element(4, 1, 0)
        self.__board.set_value_element(4, 2, 1)
        self.__board.set_value_element(4, 3, 1)
        self.__board.set_value_element(3, 2, 0)
        self.__board.set_value_element(3, 3, 1)
        self.__board.set_value_element(2, 3, 0)
        k = self.__board.check_if_won()
        self.assertEqual(k, True)
        self.__board.set_value_element(5, 1, 0)
        self.__board.set_value_element(5, 2, 0)
        self.__board.set_value_element(5, 3, 0)
        k = self.__board.check_if_won()
        self.assertEqual(k, True)
        self.__board.set_value_element(5, 1, '-')
        self.__board.set_value_element(5, 2, '-')
        self.__board.set_value_element(5, 3, '-')
        self.__board.set_value_element(4, 1, '-')
        self.__board.set_value_element(4, 2, '-')
        self.__board.set_value_element(4, 3, '-')
        self.__board.set_value_element(3, 2, '-')
        self.__board.set_value_element(3, 3, '-')
        self.__board.set_value_element(2, 3, '-')
        k = self.__board.check_if_won()
        self.assertEqual(k, False)
        self.__board.set_value_element(5, 1, 0)
        self.__board.set_value_element(4, 1, 0)
        self.__board.set_value_element(3, 1, 0)
        self.__board.set_value_element(2, 1, 0)
        k = self.__board.check_if_won()
        self.assertEqual(k, True)
        self.__board.set_value_element(4, 1, '-')
        self.__board.set_value_element(3, 1, '-')
        self.__board.set_value_element(5, 1, '-')
        self.__board.set_value_element(3, 2, 0)
        self.__board.set_value_element(4, 3, 0)
        self.__board.set_value_element(5, 4, 0)
        k = self.__board.check_if_won()
        self.assertEqual(k, True)
        k = self.__board.check_if_full()
        self.assertEqual(k, False)
        for i in range(6):
            for j in range(7):
                self.__board.set_value_element(i, j, 1)
        k = self.__board.check_if_full()
        self.assertEqual(k, True)
        for i in range(6):
            for j in range(7):
                self.__board.set_value_element(i, j, '-')
        k = self.__board.check_if_full()
        self.assertEqual(k, False)
        self.__board.set_value_element(5, 0, 0)
        self.__board.set_value_element(5, 1, 1)
        self.__board.set_value_element(5, 2, 1)
        self.__board.set_value_element(5, 3, 1)
        self.__board.set_value_element(4, 1, 0)
        self.__board.set_value_element(4, 2, 1)
        self.__board.set_value_element(4, 3, 1)
        self.__board.set_value_element(3, 2, 0)
        self.__board.set_value_element(3, 3, 1)
        l, c = self.__board.check_if_won_with_move_computer(2, 3, 0)
        self.assertEqual(l, 2)
        self.assertEqual(c, 3)
        self.__board.set_value_element(5, 0, 1)
        l, c = self.__board.check_if_won_with_move_computer(2, 3, 0)
        self.assertEqual(l, -1)
        self.assertEqual(c, -1)
        l, c = self.__board.check_if_won_with_move_computer(5, 4, 1)
        self.assertEqual(l, 5)
        self.assertEqual(c, 4)
        for i in range(6):
            for j in range(7):
                self.__board.set_value_element(i, j, '-')
        k = self.__board.check_if_full()
        self.assertEqual(k, False)
        self.__board.set_value_element(5, 0, 0)
        self.__board.set_value_element(4, 0, 0)
        self.__board.set_value_element(3, 0, 0)
        l, c = self.__board.check_if_won_with_move_computer(2, 0, 0)
        self.assertEqual(l, 2)
        self.assertEqual(c, 0)

    def test_human_and_computer(self):
        for i in range(6):
            for j in range(7):
                self.__board.set_value_element(i, j, '-')
        self.__human.move(5, 0)
        self.assertEqual(self.__board.get_element(5, 0).value, 0)
        self.__human.move(5, 1)
        self.__human.move(5, 2)
        self.__computer.move()
        self.assertEqual(self.__board.get_element(5, 3).value, 1)
        for i in range(6):
            for j in range(7):
                self.__board.set_value_element(i, j, '-')
        self.__board.set_value_element(5, 0, 1)
        self.__board.set_value_element(5, 1, 1)
        self.__board.set_value_element(5, 2, 1)
        self.__computer.move()
        self.assertEqual(self.__board.get_element(5, 3).value, 1)
        for i in range(6):
            for j in range(7):
                self.__board.set_value_element(i, j, '-')
        self.__computer.move()

    def test_validator(self):
        validator = ValidateColumn()
        validator.validate_column('3')
        with self.assertRaises(ColumnException) as ce:
            validator.validate_column('a')
            self.assertEqual(str(ce), "Please enter a numeric column!\n")
        with self.assertRaises(ColumnException) as ce:
            validator.validate_column('8')
            self.assertEqual(str(ce), "Please enter a column from 0 to 6!\n")
