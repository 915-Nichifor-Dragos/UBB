"""

@author: Nichifor Dragos

"""
import datetime

from src.data_structure.sorting import ShellSort
from src.domain.classes import Movie, Client, Rental
from src.repository.repository import RepositoryClients, RepositoryMovies, RepositoryRentals


class FileRepositoryClients(RepositoryClients):
    def __init__(self, filename):
        super().__init__()
        self.__filename = filename
        self._load_data()
        self._write_data()

    def _load_data(self):
        f = open(self.__filename)
        for line in f.readlines():
            _id, name = line.split(maxsplit=1, sep=',')
            name = name.strip()
            client = Client(_id, name)
            self.add_cl(client)
        f.close()

    def _write_data(self):
        ShellSort(structure=self.return_list_clients(), key=lambda x: x.client_id, reverse=False)
        f = open(self.__filename, 'wt')
        k = self.return_list_clients()
        for element in k:
            f.write(str(element.client_id) + ',' + str(element.name) + "\n")
        f.close()

    def add_cl(self, obj):
        super(FileRepositoryClients, self).add_cl(obj)
        self._write_data()

    def remove_cl(self, obj):
        super(FileRepositoryClients, self).remove_cl(obj)
        self._write_data()

    def update_cl(self, obj, name):
        super(FileRepositoryClients, self).update_cl(obj, name)
        self._write_data()


class FileRepositoryMovies(RepositoryMovies):
    def __init__(self, filename):
        super().__init__()
        self.__filename = filename
        self._load_data()
        self._write_data()

    def _load_data(self):
        f = open(self.__filename)
        for line in f.readlines():
            _id, title, description, genre = line.split(maxsplit=3, sep=';')
            genre = genre.strip()
            movie = Movie(_id, title, description, genre)
            self.add_mov(movie)
        f.close()

    def _write_data(self):
        ShellSort(structure=self.return_list_movies(), key=lambda x: x.id, reverse=False)
        f = open(self.__filename, 'wt')
        k = self.return_list_movies()
        for element in k:
            f.write(str(element.id) + ';' + str(element.title) + ';' + str(element.description) + ';' +
                    str(element.genre) + "\n")
        f.close()

    def add_mov(self, obj):
        super(FileRepositoryMovies, self).add_mov(obj)
        self._write_data()

    def remove_mov(self, obj):
        super(FileRepositoryMovies, self).remove_mov(obj)
        self._write_data()

    def update_mov_description(self, obj, description):
        super(FileRepositoryMovies, self).update_mov_description(obj, description)
        self._write_data()

    def update_mov_title(self, obj, title):
        super(FileRepositoryMovies, self).update_mov_title(obj, title)
        self._write_data()

    def update_mov_genre(self, obj, genre):
        super(FileRepositoryMovies, self).update_mov_genre(obj, genre)
        self._write_data()


class FileRepositoryRentals(RepositoryRentals):
    def __init__(self, filename):
        super().__init__()
        self.__filename = filename
        self._load_files()
        self._write_data()

    def _load_files(self):
        f = open(self.__filename)
        for line in f.readlines():
            _id, _client_id, _movie_id, rented_date, due_date, returned_date = line.split(maxsplit=5, sep=',')

            tkn = rented_date.split()
            rented_date = tkn[0]
            tkn = rented_date.split(maxsplit=2, sep='-')
            rented_date = datetime.datetime(int(tkn[0]), int(tkn[1]), int(tkn[2]))

            tkn = due_date.split()
            due_date = tkn[0]
            tkn = due_date.split(maxsplit=2, sep='-')
            due_date = datetime.datetime(int(tkn[0]), int(tkn[1]), int(tkn[2]))

            returned_date = None

            rent = Rental(_id, _client_id, _movie_id, rented_date, due_date, returned_date)
            self.add_rental(rent)

    def _write_data(self):
        ShellSort(structure=self.return_list_rentals(), key=lambda x: x.rent_id, reverse=False)
        f = open(self.__filename, 'wt')
        k = self.return_list_rentals()
        for element in k:
            f.write(str(element.rent_id) + ',' + str(element.movie_id) + ',' + str(element.client_id) + ',' + str(
                element.rented_date) + ',' + str(element.due_date) + ',' + str(element.returned_date) + "\n")

    def add_rental(self, obj):
        super(FileRepositoryRentals, self).add_rental(obj)
        self._write_data()

    def update_renturned_date(self, obj, current_date):
        super(FileRepositoryRentals, self).update_renturned_date(obj, current_date)
        self._write_data()

    def remove_borrow(self, obj):
        super(FileRepositoryRentals, self).remove_borrow(obj)
        self._write_data()
