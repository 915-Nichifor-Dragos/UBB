"""


@author: Nichifor Dragos





Requirements:

You will be given one of the problems below to solve using feature-driven development
The program must provide a menu-driven console user interface.
Use classes to represent the following:
The domain entity (complex, expense, student, book)
A services class that implements the required functionalities
The ui class which implements the user interface
Have 10 programmatically generated entries in the application at start-up.
Unit tests and specifications for non-UI functions related to the first functionality.

===========================================================================================================

2. Expenses

Manage a list of expenses. Each expense has a day (integer between 1 and 30), amount of money (positive integer) and 
expense type (string). Provide the following features:

Add an expense. Expense data is read from the console.
Display the list of expenses.
Filter the list so that it contains only expenses above a certain value read from the console.
Undo the last operation that modified program data. This step can be repeated.

"""

import random

from src.domain.domain import Expense
from src.services.services import Services


class StartUi:
    def __init__(self):
        self.__explist = []
        self.__undolist = []

    def create_element_list(self, current):
        types = ['food', 'internet', 'housekeeping', 'transport', 'others', 'electricity']
        expense_type = random.choice(types)
        day = random.randint(1, 30)
        value = random.randint(1, 400)
        p = Expense(day, value, expense_type)
        self.__explist.append(p)

    def fill_list(self):
        current = 0
        while current < 10:
            current += 1
            self.create_element_list(current)

    def print_element(self, day, amount, expense):
        print(" day: ", day, " amount: ", amount, " type of expense: ", expense)

    def print_menu(self):
        print("1 - add an expense")
        print("2 - display the expenses")
        print("3 - filter by value")
        print("4 - undo")
        print("exit - exit the program")

    def check_command(self, command):
        if command != 1 and command != 2 and command != 3 and command != 4 and command != "1" and command != "2" \
                and command != "3" and command != "4":
            raise ValueError("Please enter a valid command!")

    def add_cmd(self, day, amount, expense):
        addition = Services(self.__explist, self.__undolist)
        addition.add_cmd_run(day, amount, expense)

    def list_cmd(self):
        if len(self.__explist) == 0:
            print("The list has no elements")
            return
        for i in range(len(self.__explist)):
            k = self.__explist[i]
            self.print_element(k.day, k.amount, k.type_expense)

    def filter_cmd(self, value):
        filter_c = Services(self.__explist, self.__undolist)
        filter_c.filter_cmd_run(value)

    def undo_cmd(self):
        undo_c = Services(self.__explist, self.__undolist)
        undo_c.undo_cmd_run()

    def start(self):

        self.fill_list()
        self.__undolist.append(self.__explist[:])
        while True:
            try:
                self.print_menu()
                command = input("Please enter the command! ")
                if command.lower() == 'exit':
                    print("Quiting...")
                    return
                self.check_command(command)
                if command == "1" or command == 1:
                    day = input("The day is: ")
                    amount = input("The amount is: ")
                    expense = input("The expense type is: ")
                    self.add_cmd(day, amount, expense)
                if command == "2" or command == 2:
                    self.list_cmd()
                if command == "3" or command == 3:
                    value = input("The value is: ")
                    self.filter_cmd(value)
                if command == "4" or command == 4:
                    self.undo_cmd()
            except ValueError as ve:
                print(ve)


if __name__ == '__main__':
    k = StartUi()
    k.start()
