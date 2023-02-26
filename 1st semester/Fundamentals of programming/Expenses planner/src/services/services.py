"""


@author: Nichifor Dragos


"""

from src.domain.domain import Expense, Validator


class Services:
    def __init__(self, explist, undolist):
        """
        Initializes the Service class

        :param explist: the current list of expenses
        :param undolist: the list of previous list of expenses -> required for undo operation
        """

        self.__explist = explist
        self.__undolist = undolist

    def check_if_present(self, day, amount, expense):
        """
        Checks if there is already an expense with the current day and type, and, instead of creating a new one
        with the same values, it adds the amounts and creates a new class.

        :param day: The day of the new expense
        :param amount: The amount of the new expense
        :param expense: The type of the new expense
        :return:
        """

        for i in range(len(self.__explist)):
            if int(self.__explist[i].day) == int(day) and self.__explist[i].type_expense == expense:
                k = int(self.__explist[i].amount) + int(amount)
                p = Expense(day, k, expense)
                self.__explist[i] = p
                self.__undolist.append(self.__explist[:])
                return True
        return False

    def add_cmd_run(self, day, amount, expense):
        """
        The add command run function. Adds a new Expense class to the expenses list, if 'check_if_present' returns
        false -> there is no expense with the current day and type so we have to create a new one

        :param day: The day of the new expense
        :param amount: The amount of the new expense
        :param expense: The type of the new expense
        :return:
        """

        p = Expense(day, amount, expense)
        Validator.validate(p)
        if self.check_if_present(day, amount, expense) == True:
            return
        self.__explist.append(p)
        if len(self.__explist) != 0:
            self.__undolist.append(self.__explist[:])

    def filter_cmd_run(self, value):
        """
        Filters the list and keeps only the expenses with amount greater than the given value

        :param value: The value we compare to
        :return:
        """

        current = 0
        while current < len(self.__explist):
            if int(self.__explist[current].amount) <= int(value):
                self.__explist.remove(self.__explist[current])
                current -= 1
            current += 1
        self.__undolist.append(self.__explist[:])

    def undo_cmd_run(self):
        """
        Undo to the last operation
        -> all the list are stored in a list (list of lists)
        -> every time the user types undo we can go back and update the current list

        :return:
        """

        if len(self.__undolist) >= 2:
            self.__explist[:] = self.__undolist[len(self.__undolist) - 2]
            self.__undolist.remove(self.__undolist[len(self.__undolist) - 1])
        else:
            raise ValueError("Cannot undo operation")


def test():
    """
    The test function for class Services

    :return:
    """

    k = []
    p = Expense('10', '100', 'food')
    q = Expense('24', '320', 'transport')
    x = []
    x.append(p)
    k.append(x[:])
    x.append(q)
    k.append(x[:])
    # we add (3, 21, housekeeping) to our list
    cmd = Services(x, k)
    cmd.add_cmd_run('3', '21', 'housekeeping')
    assert x[len(x) - 1].day == '3'
    assert x[len(x) - 1].amount == '21'
    assert x[len(x) - 1].type_expense == 'housekeeping'
    cmd.filter_cmd_run(100)
    assert x[len(x) - 1].day == '24'
    assert x[len(x) - 1].amount == '320'
    assert x[len(x) - 1].type_expense == 'transport'
    cmd.undo_cmd_run()
    assert x[len(x) - 1].day == '3'
    assert x[len(x) - 1].amount == '21'
    assert x[len(x) - 1].type_expense == 'housekeeping'
    cmd.add_cmd_run('3', '42', 'housekeeping')
    assert x[len(x) - 1].day == '3'
    assert x[len(x) - 1].amount == 63
    assert x[len(x) - 1].type_expense == 'housekeeping'


test()
