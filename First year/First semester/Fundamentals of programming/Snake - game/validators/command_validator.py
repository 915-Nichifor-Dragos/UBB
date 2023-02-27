"""

@author: Nichifor Dragos

"""


class CommandException(Exception):
    pass


class CommandValidator:
    @staticmethod
    def check_validity(command):
        command = command.lower()
        if command != "move" and command != "right" and command != "left" and command != "up" and command != "down":
            raise CommandException("\nPlease enter a valid command")

    @staticmethod
    def check_number(command):
        command = str(command)
        if command.isnumeric() is False:
            raise CommandException("\nPlease enter a valid number of steps")
