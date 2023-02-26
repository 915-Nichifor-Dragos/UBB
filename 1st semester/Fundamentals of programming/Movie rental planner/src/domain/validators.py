"""


@author: Nichifor Dragos


"""
import datetime


class ClientException(Exception):
    pass


class MovieException(Exception):
    pass


class RentalException(Exception):
    pass


class ClientValidator:
    @staticmethod
    def validate(client):
        """
        Checks if the name of the client is valid

        :param client: the client's name
        :return:
        """

        if len(client.name) == 0 or client.name.isnumeric() is True:
            raise ClientException("\nPlease enter a valid name!")


class MovieValidator:
    @staticmethod
    def validate(movie):
        """
        Validates the movie

        :param movie: the movie object
        :return:
        """

        if len(movie.title) == 0:
            raise MovieException("\nPlease enter a valid title!")
        if len(movie.description) == 0:
            raise MovieException("\nPlease enter a valid description!")
        if movie.genre != 'science fiction' and movie.genre != 'action' and \
                movie.genre != 'comedy' and movie.genre != 'historic' and \
                movie.genre != 'adventure' and movie.genre != 'survival' and movie.genre != 'romance':
            raise MovieException("\nPlease enter a valid genre")

    @staticmethod
    def validate_genre(genre):
        """
        Validates the movie genre, when updating an existing one

        :param genre: the new genre
        :return:
        """

        if genre != 'science fiction' and genre != 'action' and genre != 'comedy' and genre != 'historic' \
                and genre != 'adventure' and genre != 'survival' and genre != 'romance':
            raise MovieException("\nPlease enter a valid type of movie!")


class RentalValidator:

    @staticmethod
    def validate_borrow(now_date, other_date):
        """
        Checks if you have a rent which due date is before the current day and doesn't let the client rent another
        movie until returning it.

        :param now_date: current date
        :param other_date: due date of rented movie
        :return:
        """

        if now_date > other_date:
            raise RentalException("\nYou can not rent another movie, until you return the one past the due date")

    @staticmethod
    def validate_already_rented():
        """
        Raises an error in case the user tries to rent another time the movie that has been rented to him and
        is still active

        :return:
        """

        raise RentalException("\nYou can not rent the same movie 2 times when the previous rent is active")
