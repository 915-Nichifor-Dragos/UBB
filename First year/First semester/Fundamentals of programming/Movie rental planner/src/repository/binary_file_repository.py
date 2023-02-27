"""

@author: Nichifor Dragos

"""

import pickle

from src.repository.repository import RepositoryClients, RepositoryMovies, RepositoryRentals


class BinRepositoryClients(RepositoryClients):
    def __init__(self, filename):
        super().__init__()
        self.__filename = filename
        self._load_file()

    def _load_file(self):
        f = open(self.__filename, 'rb')
        self._clients = pickle.load(f)
        f.close()

    def _write_data(self):
        f = open(self.__filename, 'wb')
        pickle.dump(self._clients, f)
        f.close()

    def add_cl(self, obj):
        super(BinRepositoryClients, self).add_cl(obj)
        self._write_data()

    def remove_cl(self, obj):
        super(BinRepositoryClients, self).remove_cl(obj)
        self._write_data()

    def update_cl(self, obj, name):
        super(BinRepositoryClients, self).update_cl(obj, name)
        self._write_data()


class BinRepositoryMovies(RepositoryMovies):
    def __init__(self, filename):
        super().__init__()
        self.__filename = filename
        self._load_file()

    def _load_file(self):
        f = open(self.__filename, 'rb')
        self._movies = pickle.load(f)
        f.close()

    def _write_data(self):
        f = open(self.__filename, 'wb')
        pickle.dump(self._movies, f)
        f.close()

    def add_mov(self, obj):
        super(BinRepositoryMovies, self).add_mov(obj)
        self._write_data()

    def remove_mov(self, obj):
        super(BinRepositoryMovies, self).remove_mov(obj)
        self._write_data()

    def update_mov_description(self, obj, description):
        super(BinRepositoryMovies, self).update_mov_description(obj, description)
        self._write_data()

    def update_mov_title(self, obj, title):
        super(BinRepositoryMovies, self).update_mov_title(obj, title)
        self._write_data()

    def update_mov_genre(self, obj, genre):
        super(BinRepositoryMovies, self).update_mov_genre(obj, genre)
        self._write_data()


class BinRepositoryRentals(RepositoryRentals):
    def __init__(self, filename):
        super().__init__()
        self.__filename = filename
        self._load_file()

    def _load_file(self):
        f = open(self.__filename, 'rb')
        self._rentals = pickle.load(f)
        f.close()

    def _write_data(self):
        f = open(self.__filename, 'wb')
        pickle.dump(self._rentals, f)
        f.close()

    def add_rental(self, obj):
        super(BinRepositoryRentals, self).add_rental(obj)
        self._write_data()

    def update_renturned_date(self, obj, current_date):
        super(BinRepositoryRentals, self).update_renturned_date(obj, current_date)
        self._write_data()

    def remove_borrow(self, obj):
        super(BinRepositoryRentals, self).remove_borrow(obj)
        self._write_data()
