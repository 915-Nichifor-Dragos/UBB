"""


@author: Nichifor Dragos


"""


# Manage a list of expenses. Each expense has a day (integer between 1 and 30), amount of money (positive integer) and
# expense type (string).

class Expense:
    def __init__(self, day, amount, type_expense):
        """
        Initializes a new expense object

        :param day: the day
        :param amount: the amount of money spent
        :param type_expense: the expense type
        """

        self.__day = day
        self.__amount = amount
        self.__type_expense = type_expense

    @property
    def day(self):
        """
        Day getter

        :return:
        """

        return self.__day

    @day.setter
    def day(self, value):
        """
        Day setter

        :param value: Sets the day equal to the variable 'value'
        :return:
        """

        self.__day = value

    @property
    def amount(self):
        """
        Amount getter

        :return:
        """

        return self.__amount

    @amount.setter
    def amount(self, value):
        """
        Amount setter

        :param value: Sets the amount equal to the variable 'value'
        :return:
        """

        self.__amount = value

    @property
    def type_expense(self):
        """
        Expense type getter

        :return:
        """

        return self.__type_expense

    @type_expense.setter
    def type_expense(self, value):
        """
        Expense type setter

        :param value: Sets the expense type equal to the variable 'value'
        :return:
        """

        self.__type_expense = value


class Validator:
    def validate(self):
        """
        Checks wheater an expense has a valid input:
        - numerical day between 1 and 30
        - numerical amount of money spent
        - one of the following expenses: food, internet, housekeeping, transport, electricity and others

        :return:
        """

        if self.day.isnumeric() == False:
            raise ValueError("Please enter a numeric day")
        if self.amount.isnumeric() == False:
            raise ValueError("Please enter a real price")
        if int(self.day) <= 0 or int(self.day) > 30:
            raise ValueError("Please enter a day from 1 to 30")
        if self.type_expense != 'food' and self.type_expense != 'internet' and self.type_expense != 'housekeeping' \
                and self.type_expense != 'transport' and self.type_expense != 'electricity' \
                and self.type_expense != 'others':
            raise ValueError("Please enter a valid type of expense")


def test():
    """
    Test functuion for the class 'Expenses"

    :return:
    """

    exp = Expense(10, 30, 'food')
    assert exp.day == 10
    assert exp.amount == 30
    assert exp.type_expense == 'food'
    exp.day = 4
    assert exp.day == 4
    exp.amount = 300
    assert exp.amount == 300
    exp.type_expense = 'internet'
    assert exp.type_expense == 'internet'


test()
