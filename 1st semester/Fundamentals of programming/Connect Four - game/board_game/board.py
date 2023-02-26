"""

@author: Nichifor Dragos

"""
from board_game.cell import Cell

from colorama import Fore, Back, Style


class Board:
    def __init__(self, lines, columns, empty_value):
        self.__lines = lines
        self.__columns = columns
        self.__empty_value = empty_value
        self.__cells = self.__create_board()

    def __create_board(self):
        """
        Initializes the board with every cell having the empty values

        :return: the initialized board
        """
        x = []
        for line in range(self.__lines):
            for column in range(self.__columns):
                x.append(Cell(line, column, self.__empty_value))
        return x

    def return_column(self, column):
        """
        Returns a column

        :param column: the column that will be returned
        :return: the column
        """
        x = []
        for cell in self.__cells:
            if cell.column == int(column):
                x.append(cell)
        return x

    def return_line(self, line):
        """
        Returns a line

        :param line: the line that will be returned
        :return: the line
        """
        x = []
        for cell in self.__cells:
            if cell.line == int(line):
                x.append(cell)
        return x

    def return_element(self, line, column):
        """
        Returns 'True' if the element situated at the given line and column has empty value and 'False' if it does not

        :param line: the line
        :param column: the column
        :return: 'True' if the element situated at the given line and column has empty value and 'False' if it does not
        """
        for cell in self.__cells:
            if cell.line == int(line) and cell.column == int(column):
                if cell.value == self.__empty_value:
                    return True
                else:
                    return False

    def set_value_element(self, line, column, value):
        """
        Initializes the element with the given line and column with the value given

        :param line: the line
        :param column: the column
        :param value: the given value
        :return:
        """
        for cell in self.__cells:
            if cell.line == int(line) and cell.column == int(column):
                cell.value = value
                break

    def return_all_possible_choices(self):
        """
        Returns all the possible columns where the computer can move: -1 stands for impossible and every other number
        represents the first line where a move is possible

        :return: the list with all the possible moves
        """
        min_index = [-1, -1, -1, -1, -1, -1, -1]
        for cell in self.__cells:
            if cell.line > min_index[int(cell.column)] and cell.value == self.__empty_value:
                min_index[int(cell.column)] = cell.line
        return min_index

    def check_if_won_with_move_computer(self, line_st, column_st, value):
        """
        Checks, in advance if the user can win using the given move

        :param line_st: the line
        :param column_st: the column
        :param value: the value (0 - human players)
        :return: -1 -1 in case the human won't win with this move, and column_st line_st in case human would win
        """
        self.set_value_element(line_st, column_st, value)
        for i in range(6):
            line = self.return_line(i)
            for j in range(4):
                if line[j].value == line[j + 1].value == line[j + 2].value == line[j + 3].value and \
                        line[j].value == value:
                    self.set_value_element(line_st, column_st, self.__empty_value)
                    return line_st, column_st
        k = self.check_diagonal()
        if k is True:
            return line_st, column_st
        for i in range(7):
            column = self.return_column(i)
            for j in range(3):
                if column[j].value == column[j + 1].value == column[j + 2].value == column[j + 3].value and \
                        column[j].value == value:
                    self.set_value_element(line_st, column_st, self.__empty_value)
                    return line_st, column_st
        self.set_value_element(line_st, column_st, self.__empty_value)
        return -1, -1

    def check_if_won(self):
        """
        Checks if the table has a winning combination

        :return: True if yes, False if not
        """
        for i in range(6):
            line = self.return_line(i)
            for j in range(4):
                if line[j].value == line[j + 1].value == line[j + 2].value == line[j + 3].value and \
                        line[j].value != self.__empty_value:
                    return True
        k = self.check_diagonal()
        if k is True:
            return True
        for i in range(7):
            column = self.return_column(i)
            for j in range(3):
                if column[j].value == column[j + 1].value == column[j + 2].value == column[j + 3].value and \
                        column[j].value != self.__empty_value:
                    return True
        return False

    def check_diagonal(self):
        """
        Checks if there is any winning combination on a diagonal

        :return: True if yes, False if no
        """
        col_st = 0
        lin_st = 0
        while col_st != self.__columns - 1 or lin_st != self.__lines - 1:
            col = col_st
            lin = lin_st
            list_diagonal = []
            for i in range(len(self.__cells) - 1, -1, -1):
                if int(self.__cells[i].line) == lin and int(self.__cells[i].column) == col:
                    list_diagonal.append(self.__cells[i])
                    if lin != 0 and col != int(self.__columns) - 1:
                        lin -= 1
                        col += 1
            if lin_st == self.__lines - 1:
                col_st += 1
            else:
                lin_st += 1
            if len(list_diagonal) >= 4:
                k = len(list_diagonal) - 4
                for i in range(k + 1):
                    if list_diagonal[i].value == list_diagonal[i + 1].value == list_diagonal[i + 2].value == \
                            list_diagonal[i + 3].value and list_diagonal[i].value != self.__empty_value:
                        return True
        col_st = 0
        lin_st = 5
        while lin_st != 0 or col_st != self.__columns - 1:
            col = col_st
            lin = lin_st
            list_diagonal = []
            for i in range(len(self.__cells) - 1, -1, -1):
                if int(self.__cells[i].line) == lin and int(self.__cells[i].column) == col:
                    list_diagonal.append(self.__cells[i])
                    if lin != 0 or col != int(self.__columns) - 1:
                        lin -= 1
                        col -= 1
            if col_st == self.__columns - 1:
                lin_st -= 1
            else:
                col_st += 1
            if len(list_diagonal) >= 4:
                k = len(list_diagonal) - 4
                for i in range(k + 1):
                    if list_diagonal[i].value == list_diagonal[i + 1].value == list_diagonal[i + 2].value == \
                            list_diagonal[i + 3].value and list_diagonal[i].value != self.__empty_value:
                        return True

    def check_if_full(self):
        """
        Checks if the table is full. If it is then the game is a draw

        :return:
        """
        for cell in self.__cells:
            if cell.value == self.__empty_value:
                return False
        return True

    def get_element(self, line, column):
        """
        Returns the element at the given line and column

        :param line: the line
        :param column: the column
        :return: the element
        """
        for cell in self.__cells:
            if cell.line == line and cell.column == column:
                return cell

    def __str__(self):
        """
        We print the board in a more 'nicer' way

        :return: the board, modified for nice printing
        """
        res = ""
        current = 0
        for cell in self.__cells:
            if cell.value == 0:
                res = res + Fore.RED + str(cell.value) + " " + Style.RESET_ALL
            elif cell.value == 1:
                res = res + Fore.YELLOW + str(cell.value) + " " + Style.RESET_ALL
            else:
                res = res + str(cell.value) + " "
            current += 1
            if current == self.__columns:
                current = 0
                res += "\n"
        return res
