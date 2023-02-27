"""

@author: Nichifor Dragos

"""


class ColumnException(Exception):
    pass


class ValidateColumn:
    @staticmethod
    def validate_column(col):
        """
        Checks if the column introduced by the user is valid. In case it's not, raises a 'ColumnException'

        :param col: the column entered by the user
        :return:
        """
        if col.isnumeric() is False:
            raise ColumnException("Please enter a numeric column!\n")
        col = int(col)
        if col != 0 and col != 1 and col != 2 and col != 3 and col != 4 and col != 5 and col != 6:
            raise ColumnException("Please enter a column from 0 to 6!\n")
