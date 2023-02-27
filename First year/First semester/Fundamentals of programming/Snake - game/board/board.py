"""

@author: Nichifor Dragos

"""
import random


class Board:
    def __init__(self, n, apples):
        self.__dimension = n
        self.__apples = apples
        self.__board = [["-" for i in range(n)] for j in range(n)]
        self.place_snake()
        self.place_apples()

    def __str__(self):
        s = "\n"
        for i in range(self.__dimension):
            for j in range(self.__dimension):
                s += str(self.__board[i][j]) + " "
            s += "\n"
        return s

    def place_snake(self):
        middle = self.__dimension // 2
        self.__board[middle][middle] = '+'
        self.__board[middle + 1][middle] = '+'
        self.__board[middle - 1][middle] = '*'

    def place_apples(self):
        nr = self.__apples
        while nr > 0:
            i = random.randint(0, self.__dimension - 1)
            j = random.randint(0, self.__dimension - 1)
            if i != 0 and self.__board[i - 1][j] != 'a':
                if i != self.__dimension - 1 and self.__board[i + 1][j] != 'a':
                    if j != 0 and self.__board[i][j - 1] != 'a':
                        if j != self.__dimension - 1 and self.__board[i][j + 1] != 'a' and self.__board[i][j] == '-':
                            self.__board[i][j] = 'a'
                            nr -= 1

    def add_1_apple(self):
        nr = 1
        while nr > 0:
            i = random.randint(0, self.__dimension - 1)
            j = random.randint(0, self.__dimension - 1)
            if i != 0 and self.__board[i - 1][j] != 'a':
                if i != self.__dimension - 1 and self.__board[i + 1][j] != 'a':
                    if j != 0 and self.__board[i][j - 1] != 'a':
                        if j != self.__dimension - 1 and self.__board[i][j + 1] != 'a' and self.__board[i][j] == '-':
                            self.__board[i][j] = 'a'
                            nr -= 1

    def return_board(self):
        return self.__board

    def find_head(self):
        for i in range(self.__dimension):
            for j in range(self.__dimension):
                if self.__board[i][j] == '*':
                    return i, j

    def find_body(self, i_head, j_head):
        positions = []
        copy = self.return_board()
        ok = True
        while 0 <= i_head < self.__dimension and 0 <= j_head < self.__dimension and ok is True:
            ok = False
            if copy[i_head - 1][j_head] == '+':
                i_head -= 1
                copy[i_head][j_head] = '-'
                ok = True
                positions.append((i_head, j_head))
            if i_head != self.__dimension - 1:
                if copy[i_head + 1][j_head] == '+':
                    i_head += 1
                    copy[i_head][j_head] = '-'
                    ok = True
                    positions.append((i_head, j_head))
            if copy[i_head][j_head - 1] == '+':
                j_head -= 1
                copy[i_head][j_head] = '-'
                ok = True
                positions.append((i_head, j_head))
            if j_head != self.__dimension - 1:
                if copy[i_head][j_head + 1] == '+':
                    j_head += 1
                    copy[i_head][j_head] = '-'
                    ok = True
                    positions.append((i_head, j_head))
        return positions

    def check_if_hits_board(self, direction, steps):
        i, j = self.find_head()
        if direction == 'up':
            if i - steps < 0:
                return False
        if direction == 'down':
            if i + steps > self.__dimension - 1:
                return False
        if direction == 'right':
            if j + steps > self.__dimension - 1:
                return False
        if direction == 'left':
            if j - steps < 0:
                return False
        return True

    def move(self, direction, steps):
        steps = int(steps)
        if self.check_if_hits_board(direction, steps) is False:
            return False
        if direction == 'up':
            while steps > 0:
                apple = False
                i, j = self.find_head()
                body = self.find_body(i, j)
                if self.__board[i - 1][j] == 'a':
                    apple = True
                self.__board[i - 1][j] = '*'
                self.__board[i][j] = '+'
                for i in range(len(body) - 1):
                    self.__board[body[i][0]][body[i][1]] = "+"
                if apple is True:
                    self.__board[body[len(body) - 1][0]][body[len(body) - 1][1]] = '+'
                    self.add_1_apple()
                steps -= 1
        if direction == 'down':
            while steps > 0:
                apple = False
                i, j = self.find_head()
                body = self.find_body(i, j)
                if self.__board[i + 1][j] == 'a':
                    apple = True
                self.__board[i + 1][j] = '*'
                self.__board[i][j] = '+'
                for i in range(len(body) - 1):
                    self.__board[body[i][0]][body[i][1]] = "+"
                if apple is True:
                    self.__board[body[len(body) - 1][0]][body[len(body) - 1][1]] = '+'
                    self.add_1_apple()
                steps -= 1
        if direction == 'right':
            while steps > 0:
                apple = False
                i, j = self.find_head()
                body = self.find_body(i, j)
                if self.__board[i][j + 1] == 'a':
                    apple = True
                self.__board[i][j + 1] = '*'
                self.__board[i][j] = '+'
                for i in range(len(body) - 1):
                    self.__board[body[i][0]][body[i][1]] = "+"
                if apple is True:
                    self.__board[body[len(body) - 1][0]][body[len(body) - 1][1]] = '+'
                    self.add_1_apple()
                steps -= 1
        if direction == 'left':
            while steps > 0:
                apple = False
                i, j = self.find_head()
                body = self.find_body(i, j)
                if self.__board[i][j - 1] == 'a':
                    apple = True
                self.__board[i][j - 1] = '*'
                self.__board[i][j] = '+'
                for i in range(len(body) - 1):
                    self.__board[body[i][0]][body[i][1]] = "+"
                if apple is True:
                    self.__board[body[len(body) - 1][0]][body[len(body) - 1][1]] = '+'
                    self.add_1_apple()
                steps -= 1

    def up(self):
        i, j = self.find_head()
        if i == 0:
            return False
        body = self.find_body(i, j)
        apple = False
        if self.__board[i - 1][j] == 'a':
            apple = True
        self.__board[i - 1][j] = '*'
        self.__board[i][j] = "+"
        for i in range(len(body) - 1):
            self.__board[body[i][0]][body[i][1]] = "+"
        if apple is True:
            self.__board[body[len(body) - 1][0]][body[len(body) - 1][1]] = '+'
            self.add_1_apple()

    def down(self):
        i, j = self.find_head()
        if i == self.__dimension - 1:
            return False
        body = self.find_body(i, j)
        apple = False
        if self.__board[i + 1][j] == 'a':
            apple = True
        self.__board[i + 1][j] = '*'
        self.__board[i][j] = "+"
        for i in range(len(body) - 1):
            self.__board[body[i][0]][body[i][1]] = "+"
        if apple is True:
            self.__board[body[len(body) - 1][0]][body[len(body) - 1][1]] = '+'
            self.add_1_apple()

    def right(self):
        i, j = self.find_head()
        if j == self.__dimension - 1:
            return False
        body = self.find_body(i, j)
        apple = False
        if self.__board[i][j + 1] == 'a':
            apple = True
        self.__board[i][j + 1] = '*'
        self.__board[i][j] = "+"
        for i in range(len(body) - 1):
            self.__board[body[i][0]][body[i][1]] = "+"
        if apple is True:
            self.__board[body[len(body) - 1][0]][body[len(body) - 1][1]] = '+'
            self.add_1_apple()

    def left(self):
        i, j = self.find_head()
        if j == 0:
            return False
        body = self.find_body(i, j)
        apple = False
        if self.__board[i][j - 1] == 'a':
            apple = True
        self.__board[i][j - 1] = '*'
        self.__board[i][j] = "+"
        for i in range(len(body) - 1):
            self.__board[body[i][0]][body[i][1]] = "+"
        if apple is True:
            self.__board[body[len(body) - 1][0]][body[len(body) - 1][1]] = '+'
            self.add_1_apple()
