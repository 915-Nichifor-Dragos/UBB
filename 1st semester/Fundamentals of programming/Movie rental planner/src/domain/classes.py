"""

@author: Nichifor Dragos

"""
"""

Movie: movie_id, title, description, genre
Client: client_id, name
Rental: rental_id, movie_id, client_id, rented_date, due_date, returned_date

"""


class Movie:
    def __init__(self, mov_id, title, description, genre):
        self.__movie_id = mov_id
        self.__title = title
        self.__description = description
        self.__genre = genre

    @property
    def id(self):
        """
        Movie id getter

        :return:
        """

        return self.__movie_id

    @id.setter
    def id(self, value):
        """
        Movie id setter

        :param value: the value
        :return:
        """

        self.__movie_id = int(value)

    @property
    def title(self):
        """
        Movie title getter

        :return:
        """

        return self.__title

    @title.setter
    def title(self, value):
        """
        Movie title setter

        :param value: the value
        :return:
        """

        self.__title = value

    @property
    def description(self):
        """
        Movie description getter

        :return:
        """

        return self.__description

    @description.setter
    def description(self, value):
        """
        Movie description setter

        :param value: the value
        :return:
        """

        self.__description = value

    @property
    def genre(self):
        """
        Movie genre getter

        :return:
        """

        return self.__genre

    @genre.setter
    def genre(self, value):
        """
        Movie genre setter

        :param value: the value
        :return:
        """

        self.__genre = value


class Client:
    def __init__(self, cl_id, name):
        self.__client_id = cl_id
        self.__name = name

    @property
    def client_id(self):
        """
        The client id getter

        :return:
        """

        return self.__client_id

    @client_id.setter
    def client_id(self, value):
        """
        The client id setter

        :param value: the value
        :return:
        """

        self.__client_id = int(value)

    @property
    def name(self):
        """
        The client name getter

        :return:
        """

        return self.__name

    @name.setter
    def name(self, value):
        """
        The client name setter

        :param value:
        :return:
        """

        self.__name = value


class Rental:
    def __init__(self, rent_id, mov_id, cl_id, rented_date, due_date, returned_date):
        self.__rent_id = rent_id
        self.__movie_id = mov_id
        self.__client_id = cl_id
        self.__rented_date = rented_date
        self.__due_date = due_date
        self.__returned_date = returned_date

    @property
    def rent_id(self):
        """
        The rental id getter

        :return:
        """

        return self.__rent_id

    @rent_id.setter
    def rent_id(self, value):
        """
        The rental id setter

        :param value: the value
        :return:
        """

        self.__rent_id = int(value)

    @property
    def movie_id(self):
        """
        The rented movie id getter

        :return:
        """

        return self.__movie_id

    @movie_id.setter
    def movie_id(self, value):
        """
        The rented movie id setter

        :param value: the value
        :return:
        """

        self.__movie_id = int(value)

    @property
    def client_id(self):
        """
        The client that rented the movie id getter

        :return:
        """

        return self.__client_id

    @client_id.setter
    def client_id(self, value):
        """
        The client that rented the movie id setter

        :param value: the value
        :return:
        """

        self.__client_id = int(value)

    @property
    def rented_date(self):
        """
        The rented date getter

        :return:
        """

        return self.__rented_date

    @rented_date.setter
    def rented_date(self, value):
        """
        The rented date setter

        :param value: the value
        :return:
        """

        self.__rented_date = value

    @property
    def due_date(self):
        """
        The due date getter

        :return:
        """

        return self.__due_date

    @due_date.setter
    def due_date(self, value):
        """
        The due date setter

        :param value: the value
        :return:
        """

        self.__due_date = value

    @property
    def returned_date(self):
        """
        The returned date getter

        :return:
        """

        return self.__returned_date

    @returned_date.setter
    def returned_date(self, value):
        """
        The returned date setter

        :param value: the value
        :return:
        """

        self.__returned_date = value
