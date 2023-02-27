"""

@author: Nichifor Dragos

"""
import random

from validators.validate_column import ColumnException, ValidateColumn


class Game:
    def __init__(self, board, human, computer):
        self.__board = board
        self.__human = human
        self.__computer = computer
        self.__column_validator = ValidateColumn

    @staticmethod
    def choose_who_starts():
        print("\nAre you lucky enough to start? Shuffling numbers...")
        k = random.randint(0, 1)
        if k == 0:
            print('\nLooks like human is going to start\n')
        if k == 1:
            print('\nLooks like computer is going to start\n')
        return k

    def start_game(self):
        k = self.choose_who_starts()
        if k == 0:
            while True:
                try:
                    if self.move_human() is True:
                        return
                    if self.move_computer() is True:
                        return
                except ValueError as ve:
                    print(ve)
        else:
            while True:
                try:
                    if self.move_computer() is True:
                        return
                    if self.move_human() is True:
                        return
                except ValueError as ve:
                    print(ve)

    def move_human(self):
        ok = 0
        print(self.__board)
        while ok == 0:
            try:
                column = input("Please enter the column in which you want to place your move: ")
                self.__column_validator.validate_column(column)
                k = self.__board.return_column(column)
                line = None
                for cell in k:
                    if self.__board.return_element(cell.line, column):
                        line = cell.line
                        ok = 1
                if line is None:
                    raise ColumnException("\nThis line is already full\n")
                else:
                    self.__human.move(line, column)
                    print("\nHuman moved!\n")
                    if self.__board.check_if_won() is True:
                        print(self.__board)
                        print("\nHuman won!\n")
                        return True
                    if self.__board.check_if_full() is True:
                        print(self.__board)
                        print("\nIt's a draw\n")
                        return True
            except ColumnException as ce:
                print(ce)

    def move_computer(self):
        print(self.__board)
        self.__computer.move()
        print("Computer moved!\n")
        if self.__board.check_if_won() is True:
            print(self.__board)
            print("\nComputer won!\n")
            return True
        if self.__board.check_if_full() is True:
            print(self.__board)
            print("\nIt's a draw\n")
            return True
