"""

@author: Nichifor Dragos

"""
from validators.command_validator import CommandException


class Game:
    def __init__(self, board, validator):
        self.__direction = 'up'
        self.__board = board
        self.__move_validator = validator

    def game(self):
        while True:
            try:
                print(self.__board)
                command = input("Please enter your command: ")
                command = command.split(" ")
                self.__move_validator.check_validity(command[0])
                command[0].lower()
                if command[0] == 'move':
                    if len(command) == 1:
                        steps = 1
                    else:
                        self.__move_validator.check_number(command[1])
                        steps = command[1]
                    check = self.__board.move(self.__direction, steps)
                    if check is False:
                        print("\nYou lost")
                        return
                if self.__direction == 'up' and command[0] == 'down' or self.__direction == 'down' and command[
                    0] == 'up' or self.__direction == 'right' and command[0] == 'left' or self.__direction == 'left' \
                        and command[0] == 'right':
                    raise ValueError("Can not change snake direction by 180 degrees")

                if self.__direction == command[0]:
                    raise ValueError("Already set to " + command[0])
                if command[0] == 'up':
                    self.__direction = 'up'
                    check = self.__board.up()
                    if check is False:
                        print("\nYou lost")
                        return
                if command[0] == 'down':
                    self.__direction = 'down'
                    check = self.__board.down()
                    if check is False:
                        print("\nYou lost")
                        return
                if command[0] == 'right':
                    self.__direction = 'right'
                    check = self.__board.right()
                    if check is False:
                        print("\nYou lost")
                        return
                if command[0] == 'left':
                    self.__direction = 'left'
                    check = self.__board.left()
                    if check is False:
                        print("\nYou lost")
                        return
            except CommandException as ce:
                print(ce)
            except ValueError as ve:
                print(ve)
