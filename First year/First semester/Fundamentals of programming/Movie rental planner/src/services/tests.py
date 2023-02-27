"""

@author: Nichifor Dragos

"""
import datetime

from src.domain.validators import ClientValidator, MovieValidator, RentalValidator, ClientException, MovieException, \
    RentalException
from src.repository.repository import RepositoryMovies, RepositoryRentals, RepositoryClients
from src.domain.classes import Client, Movie, Rental

import unittest

from src.services.services import ServicesClients, ServicesMovies, ServicesRentals
from src.services.undo import UndoRedoManager


class ServicesTest(unittest.TestCase):

    def setUp(self) -> None:
        repository_clients = RepositoryClients()
        validator_client = ClientValidator
        self._service_clients = ServicesClients(repository_clients, validator_client)

        repository_movies = RepositoryMovies()
        validator_movie = MovieValidator
        self._service_movies = ServicesMovies(repository_movies, validator_movie)

        repository_rental = RepositoryRentals()
        validator_rental = RentalValidator
        self._service_rentals = ServicesRentals(repository_rental, validator_rental)

    def tearDown(self) -> None:
        pass

    def test_client_services(self):
        x = self._service_clients.repo.return_list_clients()
        self.assertEqual(len(x), 0)
        self._service_clients.add_cmd_run_client("Andrew Mathews")
        x = self._service_clients.repo.return_list_clients()
        self.assertEqual(len(x), 1)
        self._service_clients.add_cmd_run_client("Richard Mathews")
        x = self._service_clients.repo.return_list_clients()
        self.assertEqual(len(x), 2)
        id_cl = x[0].client_id
        self._service_clients.update_cmd_run_client(id_cl, "Andrew")
        self.assertEqual(x[0].name, "Andrew")
        self._service_clients.remove_cmd_run_client(id_cl)
        self.assertEqual(x[0].name, "Richard Mathews")
        z = self._service_clients.search_by_client_name_cmd("richard")
        self.assertEqual(len(z), 1)
        id_cl = x[0].client_id
        z = self._service_clients.search_by_client_id_cmd(id_cl)
        self.assertEqual(len(z), 1)

    def test_movie_services(self):
        x = self._service_movies.repo.return_list_movies()
        self.assertEqual(len(x), 0)
        self._service_movies.add_cmd_run_movie("ABCDE", "ABCDE", "action")
        x = self._service_movies.repo.return_list_movies()
        self.assertEqual(len(x), 1)
        self._service_movies.add_cmd_run_movie("ABCDEFG", "ABCDEFG", "romance")
        self.assertEqual(len(x), 2)
        id_mov = x[0].id
        self._service_movies.update_cmd_run_movie(id_mov, "The 300", "Nice", "action")
        k = x[0]
        self.assertEqual(x[0].id, id_mov)
        self.assertEqual(x[0].title, "The 300")
        self.assertEqual(x[0].description, "Nice")
        self.assertEqual(x[0].genre, "action")
        self._service_movies.remove_cmd_run_movie(id_mov)
        x = self._service_movies.repo.return_list_movies()
        self.assertEqual(len(x), 1)
        id_mov = x[0].id
        z = self._service_movies.search_by_movie_id_cmd(str(id_mov))
        x = self._service_movies.repo.return_list_movies()
        self.assertEqual(len(x), 1)
        z = self._service_movies.search_by_movie_genre("action")
        x = self._service_movies.repo.return_list_movies()
        self.assertEqual(len(x), 1)
        z = self._service_movies.search_by_movie_title_cmd("The 300")
        x = self._service_movies.repo.return_list_movies()
        self.assertEqual(len(x), 1)
        z = self._service_movies.search_by_movie_description_cmd("Nice")
        x = self._service_movies.repo.return_list_movies()
        self.assertEqual(len(x), 1)

    def test_rental_services(self):
        self._service_movies.add_cmd_run_movie("ABCDE", "ABCDE", "action")
        self._service_clients.add_cmd_run_client("Richard Mathews")
        x = self._service_clients.repo.return_list_clients()
        y = self._service_movies.repo.return_list_movies()
        cl_id = x[0].client_id
        mov_id = y[0].id
        self._service_rentals.borrow_cmd_run(cl_id, mov_id, "14/10/2002")
        w = self._service_rentals.repo.return_list_rentals()
        d1 = datetime.datetime(2002, 10, 14)
        d2 = datetime.datetime(2002, 10, 28)
        self.assertEqual(w[0].client_id, cl_id)
        self.assertEqual(w[0].movie_id, mov_id)
        self.assertEqual(w[0].rented_date, d1)
        self.assertEqual(w[0].due_date, d2)
        self.assertEqual(w[0].returned_date, None)

        p, k = self._service_rentals.filter_late_rentals_cmd('01/11/2002', w)
        self.assertEqual(len(p), 1)
        self.assertEqual(k[0], 4)

        p, k = self._service_rentals.filter_nr_mov_rented_cmd('16/10/2002', y)
        self.assertEqual(len(p), 1)
        self.assertEqual(k[0], 2)

        p, k = self._service_rentals.filter_most_active_clients_cmd('16/10/2002', x)
        self.assertEqual(len(p), 1)
        self.assertEqual(k[0], 2)

        id_rent = w[0].rent_id
        self._service_rentals.return_cmd_run(id_rent, "24/10/2002")
        d3 = datetime.datetime(2002, 10, 24)
        self.assertEqual(w[0].returned_date, d3)


class RepositoryTest(unittest.TestCase):

    def setUp(self) -> None:
        self._repository_movies = RepositoryMovies()
        self._repository_clients = RepositoryClients()
        self._repository_rentals = RepositoryRentals()

    def tearDown(self) -> None:
        pass

    def test_movie_repository(self):
        self.assertEqual(len(self._repository_movies.return_list_movies()), 0)
        obj = Movie(321, 'Hunger Games', 'Nice movie', 'adventure')
        self._repository_movies.add_mov(obj)
        self.assertEqual(len(self._repository_movies.return_list_movies()), 1)
        self._repository_movies.update_mov_genre(obj, "action")
        self._repository_movies.update_mov_description(obj, "acd")
        self._repository_movies.update_mov_title(obj, "Forest")
        k = self._repository_movies.return_list_movies()
        self.assertEqual(k[0].id, 321)
        self.assertEqual(k[0].description, "acd")
        self.assertEqual(k[0].genre, "action")
        self.assertEqual(k[0].title, "Forest")
        self._repository_movies.remove_mov(obj)
        self.assertEqual(len(self._repository_movies.return_list_movies()), 0)

    def test_client_repository(self):
        self.assertEqual(len(self._repository_clients.return_list_clients()), 0)
        obj = Client(303, 'Andrew Mathews')
        self._repository_clients.add_cl(obj)
        self.assertEqual(len(self._repository_clients.return_list_clients()), 1)
        self._repository_clients.update_cl(obj, "Mark")
        k = self._repository_clients.return_list_clients()
        self.assertEqual(k[0].client_id, 303)
        self.assertEqual(k[0].name, "Mark")
        self._repository_clients.remove_cl(obj)
        self.assertEqual(len(self._repository_clients.return_list_clients()), 0)

    def test_rental_repository(self):
        self.assertEqual(len(self._repository_rentals.return_list_rentals()), 0)
        obj = Rental(984, 321, 343, '14/10/2021', '28/10/2021', 1)
        self._repository_rentals.add_rental(obj)
        self.assertEqual(len(self._repository_rentals.return_list_rentals()), 1)
        self._repository_rentals.update_renturned_date(obj, '01/01/2020')
        k = self._repository_rentals.return_list_rentals()
        self.assertEqual(k[0].rent_id, 984)
        self.assertEqual(k[0].client_id, 343)
        self.assertEqual(k[0].movie_id, 321)
        self.assertEqual(k[0].rented_date, '14/10/2021')
        self.assertEqual(k[0].due_date, '28/10/2021')
        self.assertEqual(k[0].returned_date, '01/01/2020')


class ClassTest(unittest.TestCase):

    def test_client(self):
        x = Client(303, 'Andrew Mathews')
        self.assertEqual(x.client_id, 303)
        self.assertEqual(x.name, 'Andrew Mathews')

    def test_movie(self):
        x = Movie(321, 'Hunger Games', 'Nice movie', 'adventure')
        self.assertEqual(x.id, 321)
        self.assertEqual(x.title, 'Hunger Games')
        self.assertEqual(x.description, 'Nice movie')
        self.assertEqual(x.genre, 'adventure')

    def test_rental(self):
        x = Rental(984, 321, 343, '14/10/2021', '28/10/2021', 1)
        self.assertEqual(x.rent_id, 984)
        self.assertEqual(x.client_id, 343)
        self.assertEqual(x.movie_id, 321)
        self.assertEqual(x.rented_date, '14/10/2021')
        self.assertEqual(x.due_date, '28/10/2021')
        self.assertEqual(x.returned_date, 1)


class ClassSettersValidatorsTest(unittest.TestCase):

    def test_setters(self):
        mv = Movie(10, "movie_name", "movie", "action")
        mv.id = 12
        self.assertEqual(mv.id, 12)
        cl = Client(12, "Henry")
        cl.client_id = 13
        cl.name = "12"
        self.assertEqual(cl.client_id, 13)
        self.assertEqual(int(cl.name), 12)
        rent = Rental(10, 11, 12, "14/10/2014", "28/10/2014", "26/10/2014")
        rent.rent_id = 11
        rent.movie_id = 12
        rent.client_id = 13
        rent.due_date = "26/10/2014"
        rent.rented_date = "28/10/2014"
        self.assertEqual(rent.rent_id, 11)
        self.assertEqual(rent.movie_id, 12)
        self.assertEqual(rent.client_id, 13)
        self.assertEqual(rent.due_date, "26/10/2014")
        self.assertEqual(rent.rented_date, "28/10/2014")

        with self.assertRaises(ClientException) as ce:
            cl_valid = ClientValidator
            cl_valid.validate(cl)

        self.assertEqual(str(ce.exception), "\nPlease enter a valid name!")

        mv.title = ''
        mv.description = ''
        mv.genre = 'abc'
        mv_valid = MovieValidator

        with self.assertRaises(MovieException) as me:
            mv_valid.validate(mv)

        self.assertEqual(str(me.exception), "\nPlease enter a valid title!")
        mv.title = "Nice"

        with self.assertRaises(MovieException) as me:
            mv_valid.validate(mv)

        self.assertEqual(str(me.exception), "\nPlease enter a valid description!")
        mv.description = "nice2"

        with self.assertRaises(MovieException) as me:
            mv_valid.validate(mv)

        self.assertEqual(str(me.exception), "\nPlease enter a valid genre")

        with self.assertRaises(MovieException) as me:
            mv_valid.validate_genre(mv)

        self.assertEqual(str(me.exception), "\nPlease enter a valid type of movie!")

        x = datetime.datetime(2019, 10, 18)
        y = datetime.datetime(2018, 8, 19)
        rent_valid = RentalValidator

        with self.assertRaises(RentalException) as re:
            rent_valid.validate_borrow(x, y)

        self.assertEqual(str(re.exception),
                         "\nYou can not rent another movie, until you return the one past the due date")

        with self.assertRaises(RentalException) as re:
            rent_valid.validate_already_rented()

        self.assertEqual(str(re.exception),
                         "\nYou can not rent the same movie 2 times when the previous rent is active")

        repo_cl = RepositoryClients()
        repo_cl.add_cl(cl)
        with self.assertRaises(ValueError) as ve:
            repo_cl.add_cl(cl)

        repo_mov = RepositoryMovies()
        repo_mov.add_mov(mv)
        with self.assertRaises(ValueError):
            repo_mov.add_mov(mv)

        repo_rent = RepositoryRentals()
        repo_rent.add_rental(rent)
        repo_rent.remove_borrow(rent)
        self.assertEqual(len(repo_rent.return_list_rentals()), 0)


class ServicesFunctionsTest(unittest.TestCase):

    def setUp(self) -> None:
        repository_clients = RepositoryClients()
        validator_client = ClientValidator
        self._service_clients = ServicesClients(repository_clients, validator_client)

        repository_movies = RepositoryMovies()
        validator_movie = MovieValidator
        self._service_movies = ServicesMovies(repository_movies, validator_movie)

        repository_rental = RepositoryRentals()
        validator_rental = RentalValidator
        self._service_rentals = ServicesRentals(repository_rental, validator_rental)

    def tearDown(self) -> None:
        pass

    def test_client_service_functions(self):
        self._service_clients.add_cmd_run_client("Andrew")
        x = self._service_clients.repo.return_list_clients()
        id_cl = x[0].client_id
        v = self._service_clients.check_id_client(id_cl)
        self.assertEqual(v, x[0])

        with self.assertRaises(ValueError) as ve:
            self._service_clients.search_by_id_client(99999)

        self.assertEqual(str(ve.exception), "This ID does not exist")

        v = self._service_clients.check_update_id_client(id_cl)
        self.assertEqual(v, None)

        self._service_clients.add_begin_clients()
        x = self._service_clients.repo.return_list_clients()
        self.assertEqual(len(x), 21)

        with self.assertRaises(ClientException) as ce:
            self._service_clients.search_by_client_id_cmd("ab1")

        self.assertEqual(str(ce.exception), "\nPlease enter a number or a real character\n")

        with self.assertRaises(ClientException) as ce:
            self._service_clients.search_by_client_name_cmd("123")

        self.assertEqual(str(ce.exception), "\nPlease enter a name that is not numeric\n")

        self._service_movies.add_cmd_run_movie("ABC", "ABCD", "action")
        x = self._service_movies.repo.return_list_movies()
        id_mov = x[0].id
        w = self._service_movies.check_update_id_movie(id_mov)
        v = self._service_movies.check_id_movie(id_mov)
        self.assertEqual(v, x[0])

        with self.assertRaises(ValueError) as ve:
            self._service_movies.search_by_id_movie(99999)

        self.assertEqual(str(ve.exception), "This ID does not exist")

        self._service_movies.add_begin_movies()
        x = self._service_movies.repo.return_list_movies()
        self.assertEqual(len(x), 21)

        with self.assertRaises(MovieException) as me:
            self._service_movies.search_by_movie_id_cmd("ab2")

        self.assertEqual(str(me.exception), "\nPlease enter a number or a real character\n")

        with self.assertRaises(MovieException) as me:
            self._service_movies.search_by_movie_title_cmd("123")

        self.assertEqual(str(me.exception), "\nPlease enter a name that is not numeric\n")

        y = self._service_movies.search_by_movie_title_cmd("a")
        y = self._service_movies.search_by_movie_genre("action")
        y = self._service_movies.search_by_movie_description_cmd("a")

        self._service_rentals.borrow_cmd_run(id_cl, id_mov, "14/10/2021")
        x = self._service_rentals.repo.return_list_rentals()
        d1 = datetime.datetime(2021, 10, 14)
        d2 = datetime.datetime(2021, 10, 28)
        rent = Rental(x[0].rent_id, id_cl, id_mov, d1, d2, None)
        self.assertEqual(rent.rented_date, x[0].rented_date)
        self.assertEqual(rent.due_date, x[0].due_date)

        with self.assertRaises(ValueError) as ve:
            self._service_rentals.check_update_id_rent(99999)

        self.assertEqual(str(ve.exception), "This ID does not exist")

        v = self._service_rentals.check_id_rental(x[0].rent_id)
        self.assertEqual(v, x[0])

        self._service_rentals.delete_rental_undo(x[0].rent_id)
        x = self._service_rentals.repo.return_list_rentals()
        self.assertEqual(len(x), 0)

        self._service_rentals.borrow_cmd_run_begin(id_cl, id_mov, d1)
        x = self._service_rentals.repo.return_list_rentals()
        self.assertEqual(len(x), 1)

        with self.assertRaises(RentalException):
            self._service_rentals.check_if_other_rental(id_cl, d1, id_mov)

        self._service_rentals.delete_rental_undo(x[0].rent_id)
        x = self._service_rentals.repo.return_list_rentals()
        self.assertEqual(len(x), 0)

        with self.assertRaises(RentalException) as re:
            self._service_rentals.borrow_cmd_run(id_cl, id_mov, "abs")

        self.assertEqual(str(re.exception), "\nPlease enter a valid date!")

        self._service_rentals.borrow_cmd_run_begin(id_cl, id_mov, d1)
        x = self._service_rentals.repo.return_list_rentals()
        self.assertEqual(len(x), 1)

        with self.assertRaises(RentalException) as re:
            self._service_rentals.return_cmd_run(x[0].rent_id, "abs")

        self.assertEqual(str(re.exception), "\nPlease enter a valid date!")

        self._service_rentals.delete_rental_undo(x[0].rent_id)
        x = self._service_rentals.repo.return_list_rentals()
        self.assertEqual(len(x), 0)

        with self.assertRaises(RentalException) as re:
            self._service_rentals.filter_nr_mov_rented_cmd("abs", [])

        self.assertEqual(str(re.exception), "\nPlease enter a valid date!")

        with self.assertRaises(RentalException) as re:
            self._service_rentals.filter_nr_mov_rented_cmd("14/10/2021", [])

        self.assertEqual(str(re.exception), "\nThere are no current movies available")

        with self.assertRaises(RentalException) as re:
            self._service_rentals.filter_nr_mov_rented_cmd("14/10/2021", [1])

        self.assertEqual(str(re.exception), "\nThere are no current rentals")

        self._service_rentals.borrow_cmd_run(id_cl, id_mov, "14/10/2021")
        with self.assertRaises(RentalException) as re:
            self._service_rentals.filter_nr_mov_rented_cmd("13/10/2021", [1, 2])

        self.assertEqual(str(re.exception), "\nThe current day is not valid.")

        self._service_movies.add_cmd_run_movie("nice", "nice", "action")
        self._service_movies.add_cmd_run_movie("nice2", "nice2", "adventure")
        self._service_movies.add_cmd_run_movie("nice3", "nice3", "comedy")
        y = self._service_movies.repo.return_list_movies()
        self._service_rentals.borrow_cmd_run(id_cl, y[1].id, "16/10/2021")
        self._service_rentals.borrow_cmd_run(id_cl, y[2].id, "15/10/2021")
        w = self._service_rentals.repo.return_list_rentals()
        self._service_rentals.filter_nr_mov_rented_cmd("21/10/2021", y)

        self._service_clients.add_cmd_run_client("henry")
        self._service_clients.add_cmd_run_client("adrian")
        self._service_clients.add_cmd_run_client("andrew")
        q = self._service_clients.repo.return_list_clients()
        self._service_rentals.delete_rental_undo(x[2].rent_id)
        self._service_rentals.delete_rental_undo(x[1].rent_id)
        self._service_rentals.delete_rental_undo(x[0].rent_id)
        x = self._service_rentals.repo.return_list_rentals()
        self.assertEqual(len(x), 0)

        with self.assertRaises(RentalException) as re:
            self._service_rentals.filter_most_active_clients_cmd("abs", q)

        self.assertEqual(str(re.exception), "\nPlease enter a valid date!")

        with self.assertRaises(RentalException) as re:
            self._service_rentals.filter_most_active_clients_cmd("21/10/2021", [])

        self.assertEqual(str(re.exception), "\nThere are no current clients")

        with self.assertRaises(RentalException) as re:
            self._service_rentals.filter_most_active_clients_cmd("21/10/2021", q)

        self.assertEqual(str(re.exception), "\nThere are no current rentals")

        self._service_rentals.borrow_cmd_run(q[0].client_id, y[0].id, "14/10/2021")
        self._service_rentals.borrow_cmd_run(q[1].client_id, y[0].id, "14/10/2021")
        self._service_rentals.borrow_cmd_run(q[1].client_id, y[1].id, "13/10/2021")
        self._service_rentals.borrow_cmd_run(q[2].client_id, y[2].id, "15/10/2021")

        with self.assertRaises(RentalException) as re:
            self._service_rentals.filter_most_active_clients_cmd("12/10/2021", q)

        self.assertEqual(str(re.exception), "\nThe current day is not valid.")

        self._service_rentals.filter_most_active_clients_cmd("21/10/2021", q)

        x = self._service_rentals.repo.return_list_rentals()
        self._service_rentals.delete_rental_undo(x[3].rent_id)
        self._service_rentals.delete_rental_undo(x[2].rent_id)
        self._service_rentals.delete_rental_undo(x[1].rent_id)
        self._service_rentals.delete_rental_undo(x[0].rent_id)

        x = self._service_rentals.repo.return_list_rentals()

        with self.assertRaises(RentalException) as re:
            self._service_rentals.filter_late_rentals_cmd("asd", x)

        self.assertEqual(str(re.exception), "\nPlease enter a valid date!")

        with self.assertRaises(RentalException) as re:
            self._service_rentals.filter_late_rentals_cmd("21/10/2021", x)

        self.assertEqual(str(re.exception), "\nThere are no current clients")

        self._service_rentals.borrow_cmd_run(q[0].client_id, y[0].id, "14/10/2021")
        self._service_rentals.borrow_cmd_run(q[1].client_id, y[0].id, "14/10/2021")
        self._service_rentals.borrow_cmd_run(q[1].client_id, y[1].id, "13/10/2021")
        self._service_rentals.borrow_cmd_run(q[2].client_id, y[2].id, "15/10/2021")
        x = self._service_rentals.repo.return_list_rentals()

        with self.assertRaises(RentalException) as re:
            self._service_rentals.filter_late_rentals_cmd("12/10/2021", x)

        self.assertEqual(str(re.exception), "\nThe current day is not valid.")

        self._service_rentals.filter_late_rentals_cmd("21/11/2021", x)


class UndoRedoTest(unittest.TestCase):

    def setUp(self) -> None:
        repository_clients = RepositoryClients()
        validator_client = ClientValidator
        self._service_clients = ServicesClients(repository_clients, validator_client)

        repository_movies = RepositoryMovies()
        validator_movie = MovieValidator
        self._service_movies = ServicesMovies(repository_movies, validator_movie)

        repository_rental = RepositoryRentals()
        validator_rental = RentalValidator
        self._service_rentals = ServicesRentals(repository_rental, validator_rental)

    def tearDown(self) -> None:
        pass

    def test_undo_redo(self):
        UndoRedoManager.empty_undo()
        UndoRedoManager.empty_redo()

        with self.assertRaises(ValueError) as ve:
            UndoRedoManager.undo()

        self.assertEqual(str(ve.exception), "Can not undo")

        x = self._service_clients.repo.return_list_clients()
        self.assertEqual(len(x), 0)
        self._service_clients.add_cmd_run_client("henry")
        self.assertEqual(len(x), 1)
        UndoRedoManager.undo()
        self.assertEqual(len(x), 0)
        UndoRedoManager.redo()
        self.assertEqual(len(x), 1)

        with self.assertRaises(ValueError) as ve:
            UndoRedoManager.redo()

        self.assertEqual(str(ve.exception), "Can not redo")

        UndoRedoManager.undo()
        self.assertEqual(len(x), 0)

        UndoRedoManager.redo()
        self.assertEqual(len(x), 1)

        cl_id = x[0].client_id
        self._service_clients.remove_cmd_run_client(cl_id)
        UndoRedoManager.undo()
        self.assertEqual(len(x), 1)

        UndoRedoManager.redo()
        self.assertEqual(len(x), 0)

        UndoRedoManager.undo()
        self.assertEqual(len(x), 1)

        self._service_clients.update_cmd_run_client(cl_id, "andrew")
        UndoRedoManager.undo()
        self.assertEqual(x[0].name, "henry")
        UndoRedoManager.redo()
        self.assertEqual(x[0].name, "andrew")

        y = self._service_movies.repo.return_list_movies()
        self.assertEqual(len(y), 0)
        self._service_movies.add_cmd_run_movie("abc", "abed", "action")
        self.assertEqual(len(y), 1)
        UndoRedoManager.undo()
        self.assertEqual(len(y), 0)
        UndoRedoManager.redo()
        self._service_movies.remove_cmd_run_movie(y[0].id)
        self.assertEqual(len(y), 0)
        UndoRedoManager.undo()
        self.assertEqual(len(y), 1)
        self._service_movies.update_cmd_run_movie(y[0].id, "ABCD", "ABCDE", "adventure")
        UndoRedoManager.undo()
        UndoRedoManager.redo()

        q = self._service_rentals.repo.return_list_rentals()
        self._service_rentals.borrow_cmd_run(x[0].client_id, y[0].id, "14/10/2021")
        UndoRedoManager.undo()
        UndoRedoManager.redo()
        self._service_rentals.return_cmd_run(q[0].rent_id, "16/10/2021")
        UndoRedoManager.undo()
        UndoRedoManager.redo()
