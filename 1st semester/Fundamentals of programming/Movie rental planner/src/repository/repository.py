"""


@author: Nichifor Dragos


"""
from src.data_structure.data_structure import Container


class RepositoryMovies:
    def __init__(self):
        self._movies = Container()

    def add_mov(self, obj):
        """
        Adds a movie to the movie repository

        :param obj: the movie object
        :return:
        """

        obj.id = int(obj.id)
        for i in range(len(self._movies)):
            k = self._movies[i]
            if k.id == obj.id:
                raise ValueError("The id", obj.id, "is already in repo")
        self._movies.append(obj)

    def remove_mov(self, obj):
        """
        Removes a movie from the movie repository

        :param obj: the movie object
        :return:
        """

        self._movies.remove(obj)

    def update_mov_title(self, obj, title):
        """
        Update's a movie title

        :param obj: the movie object
        :param title: the new title
        :return:
        """

        for movie in self._movies:
            if obj.id == movie.id:
                movie.title = title

        # for i in range(len(self._movies)):
        #     k = self._movies[i]
        #     if k.id == obj.id:
        #         self._movies[i].title = title

    def update_mov_description(self, obj, description):
        """
        Updates a movie description

        :param obj: the movie object
        :param description: the new description
        :return:
        """

        for movie in self._movies:
            if obj.id == movie.id:
                movie.description = description

        # for i in range(len(self._movies)):
        #     k = self._movies[i]
        #     if k.id == obj.id:
        #         self._movies[i].description = description

    def update_mov_genre(self, obj, genre):
        """
        Updates a movie genre

        :param obj: the movie object
        :param genre: the new genre
        :return:
        """

        for movie in self._movies:
            if obj.id == movie.id:
                movie.genre = genre

        # for i in range(len(self._movies)):
        #     k = self._movies[i]
        #     if k.id == obj.id:
        #         self._movies[i].genre = genre

    def return_list_movies(self):
        """
        Returns the list of movies

        :return:
        """

        return self._movies.__str__()


class RepositoryClients:
    def __init__(self):
        self._clients = Container()

    def add_cl(self, obj):
        """
        Adds a new client to the client repository

        :param obj: the client object
        :return:
        """

        obj.client_id = int(obj.client_id)
        for i in range(len(self._clients)):
            k = self._clients[i]
            if k.client_id == obj.client_id:
                raise ValueError("The id", obj.client_id, "is already in repo")
        self._clients.append(obj)

    def remove_cl(self, obj):
        """
        Removes a client from the client repository

        :param obj: the client object
        :return:
        """

        self._clients.remove(obj)

    def update_cl(self, obj, name):
        """
        Updates a client name

        :param obj: the client object
        :param name: the new name
        :return:
        """

        for client in self._clients:
            if obj.client_id == client.client_id:
                client.name = name

        # for i in range(len(self._clients)):
        #     k = self._clients[i]
        #     if k.client_id == obj.client_id:
        #         self._clients[i].name = name

    def return_list_clients(self):
        """
        Returns the list of clients

        :return:
        """
        return self._clients.__str__()


class RepositoryRentals:
    def __init__(self):
        self._rentals = Container()

    def add_rental(self, obj):
        """
        Adds a new rental to the rental repository

        :param obj: the rental object
        :return:
        """

        obj.rent_id = int(obj.rent_id)
        self._rentals.append(obj)

    def update_renturned_date(self, obj, current_date):
        """
        Updates the return date of a rental

        :param obj: the rental object
        :param current_date: the return date
        :return:
        """

        for rental in self._rentals:
            if rental.rent_id == obj.rent_id:
                rental.returned_date = current_date

        # for i in range(len(self._rentals)):
        #     if self._rentals[i].rent_id == obj.rent_id:
        #         self._rentals[i].returned_date = current_date

    def remove_borrow(self, obj):
        self._rentals.remove(obj)

    def return_list_rentals(self):
        """
        Returns the list of rentals

        :return:
        """

        return self._rentals.__str__()
