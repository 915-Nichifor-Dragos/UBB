"""

@author: Nichifor Dragos

"""
import datetime
import random

from src.data_structure.filter import Filter
from src.data_structure.sorting import ShellSort
from src.domain.classes import Client, Movie, Rental
from src.domain.validators import ClientValidator, MovieValidator, RentalValidator, RentalException, ClientException, \
    MovieException
from src.repository.repository import RepositoryClients, RepositoryMovies, RepositoryRentals
from src.services.undo import UndoHandler, UndoRedoManager


class ServicesClients:
    def __init__(self, repository, validator):
        self.__repository = repository
        self.__validator = validator

    @staticmethod
    def generate_id():
        """
        Generates a random id: a number between 100 and 999

        :return:
        """

        ran_id = random.randint(100, 900)
        return ran_id

    def check_id_client(self, cl_id):
        """
        Checks if the client id exists

        :param cl_id: the client id
        :return:
        """

        li = self.__repository.return_list_clients()
        for i in range(len(li)):
            if li[i].client_id == int(cl_id):
                return li[i]
        return None

    @property
    def repo(self):
        """
        Returns the repository of service_client, service_movie or service_rental

        :return:
        """

        return self.__repository

    def search_by_id_client(self, cl_id):
        """
        Searches if the client id exists. If yes, it returns the client, if not it raises an error

        :param cl_id: the client id
        :return:
        """

        li = self.__repository.return_list_clients()
        for i in range(len(li)):
            if int(li[i].client_id) == int(cl_id):
                return li[i]
        raise ValueError("This ID does not exist")

    def check_update_id_client(self, id):
        """
        Checks if the id of the client we want to update exists. If not it raises an error

        :param id:
        :return:
        """

        self.search_by_id_client(id)

    def add_cmd_run_client(self, name):
        """
        Adds a new client

        :param name: the client name
        :return:
        """

        while True:
            cl_id = self.generate_id()
            if self.check_id_client(cl_id) is None:
                break
        cl = Client(cl_id, name)
        self.__validator.validate(cl)
        self.__repository.add_cl(cl)
        UndoRedoManager.register_operation(self, UndoHandler.ADD_CLIENT, cl.client_id, cl.name)

    def add_cmd_run_client_undo(self, cl_id, name):
        """
        Adds a new client. Function for undo operation

        :param cl_id: the client id
        :param name: the client name
        :return:
        """

        cl = Client(cl_id, name)
        self.__validator.validate(cl)
        self.__repository.add_cl(cl)

    def remove_cmd_run_client(self, id):
        """
        Removes a client

        :param id: the client id
        :return:
        """

        obj = self.search_by_id_client(id)
        self.__repository.remove_cl(obj)
        UndoRedoManager.register_operation(self, UndoHandler.REMOVE_CLIENT, obj.client_id, obj.name)

    def remove_cmd_run_client_undo(self, id, name):
        """
        Removes a client for undo operation

        :param id: the client id
        :return:
        """

        obj = self.search_by_id_client(id)
        self.__repository.remove_cl(obj)

    def update_cmd_run_client(self, id, name):
        """
        Updates a client for undo operation

        :param id: the client id
        :param name: the client new name
        :return:
        """

        obj = self.search_by_id_client(id)
        undo_id = obj.client_id
        undo_name = obj.name
        self.__repository.update_cl(obj, name)
        UndoRedoManager.register_operation(self, UndoHandler.UPDATE_CLIENT, undo_id, undo_name, obj.name)

    def update_cmd_run_client_undo(self, id, name):
        """
        Updates a client

        :param id: the client id
        :param name: the client new name
        :return:
        """

        obj = self.search_by_id_client(id)
        self.__repository.update_cl(obj, name)

    def add_begin_clients(self):
        """
        Generates 20 random clients at the beginning of the program

        :return:
        """

        namelist = ['Andrew Smith', 'Mathew Jones', 'James Taylor', 'Michael Wiliams', 'David Brown', 'Richard White',
                    'Thomas Harris', 'Joseph Martin', 'Daniel Davies', 'Christopher Wilson', 'Anthony Cooper',
                    'Mark Evans', 'Donald King', 'Paul Thomas', 'Steven Baker', 'Stephen Green', 'Brian Wright',
                    'Edward Johnson', 'Scott Edwards', 'Justin Clark', 'Gregory Roberts', 'Frank Robinson', 'Ted Hall',
                    'Noah Lewis', 'Denise Clarke', 'Amber Young', 'Danielle Davis', 'Marie Turner', 'Diana Hill',
                    'Victoria Phillips', 'Heather Collins', 'Ruth Allen', 'Janet Moore', 'Christine Thompson',
                    'Carolyn Carter', 'Katherine James', 'Emma Knight', 'Brenda Walker', 'Amy Wood',
                    'Stephanie Hughes', 'Amanda Ward', 'Emily Bennett', 'Karen Lee', 'Sarah Scott']
        current = 1
        ap = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
              0, 0, 0, 0, 0, 0, 0, 0, 0]
        while current <= 20:
            while True:
                cl_id = self.generate_id()
                if self.check_id_client(cl_id) is None:
                    break
            select = random.randint(0, 43)
            while ap[select] == 1:
                select = random.randint(0, 43)
            ap[select] = 1
            cl = Client(cl_id, namelist[select])
            self.__repository.add_cl(cl)
            current += 1

    def search_by_client_id_cmd(self, cl_id):
        """
        Searches for all the clients that contain the parameter given by the user in their id, using partial string
        matching

        :param cl_id: the parameter given by the user
        :return:
        """

        cl_id = str(cl_id)
        if cl_id == '' or cl_id == ' ' or cl_id.isnumeric() is False:
            raise ClientException("\nPlease enter a number or a real character\n")
        y = []
        cl_list = self.__repository.return_list_clients()
        for i in range(len(cl_list)):
            if cl_id in str(cl_list[i].client_id):
                y.append(cl_list[i])
        return y

    def search_by_client_name_cmd(self, cl_name):
        """
        Searches for all the clients that contain the parameter given by the user in their name, using partial string
        matching

        :param cl_name: the parameter given by the user
        :return:
        """

        cl_name = str(cl_name)
        if cl_name.isnumeric() is True or cl_name == ' ' or cl_name == '':
            raise ClientException("\nPlease enter a name that is not numeric\n")
        y = []
        cl_list = self.__repository.return_list_clients()
        for i in range(len(cl_list)):
            if cl_name.lower() in str(cl_list[i].name).lower():
                y.append(cl_list[i])
        return y


class ServicesMovies:
    def __init__(self, repository, validator):
        self.__repository = repository
        self.__validator = validator

    @property
    def repo(self):
        """
        Returns the repository of service_client, service_movie or service_rental

        :return:
        """

        return self.__repository

    def search_by_id_movie(self, mov_id):
        """
        Searches if the movie id exists. If yes, it returns the movie, if not it raises an error

        :param mov_id: the movie id
        :return:
        """

        li = self.__repository.return_list_movies()
        for i in range(len(li)):
            if li[i].id == int(mov_id):
                return li[i]
        raise ValueError("This ID does not exist")

    def check_id_movie(self, mov_id):
        """
        Checks if the movie id exists

        :param mov_id: the movie id
        :return:
        """

        li = self.__repository.return_list_movies()
        for i in range(len(li)):
            if int(li[i].id) == int(mov_id):
                return li[i]
        return None

    @staticmethod
    def generate_id():
        """
        Generates a random id: a number between 100 and 999

        :return:
        """

        ran_id = random.randint(100, 900)
        return ran_id

    def check_update_id_movie(self, id):
        """
        Checks if the id of the movie we want to update exists. If not it raises an error

        :param id:
        :return:
        """
        self.check_id_movie(id)

    def add_begin_movies(self):
        """
        Creates 20 randomly generated movies at the beginning of the program

        :return:
        """

        movielist = ['Lord of the Rings', 'Hercules', 'Home Alone', 'Hard to kill', 'Hard to kill 2', 'Friends',
                     'How I met your mother', 'The 100', 'Prison Break', 'Taken', 'Operation Finale', 'Robin Hood',
                     'Hobbs & Shaw', 'Tomb Rider', 'Hunger Games', 'Boyka: Undisputed IV', 'Five feet apart', 'Titanic',
                     '50 first dates', 'Zohan', 'Big Daddy', 'Uncut Gems', 'Paul Blart - Mall Cop']
        descriptionlist = ['A very intriguing SF movie, presenting a world full of adventure',
                           'Hercules, the Son of Zeus is the most beloved warrior in Antique Greece',
                           'A smart kid left at home tricks and makes fun of the thieves that try to rob him',
                           'An action movie full of badass scenes, with only well known actors in play',
                           'The part 2 of the original movie Hard to kill',
                           'An awesome comedy that can be watched with the family',
                           'This show presents how Ted found his loved one among the other adventures he has been'
                           ' through',
                           'An amazing SF show that shows us what artificial intelligence can do to earth',
                           'Michael Scofield saves his brother from the death penalty and proves his innocence',
                           'A desperate father does anything to find his kidnapped daughter in Paris',
                           'Presents a historical-drama from the United-States past',
                           'Robin Hood was probably the only thief that was loved by the folk for his generosity',
                           'The Fast and Furious crew fight a terrorist that prepares a cybernetic attack',
                           'Lara Croft explores the island on which her father disappeared',
                           'Katniss Everdeen voluntarily takes her younger sisters place in the Hunger Games',
                           'Boyka fights in prison, beating every enemy he faces',
                           'A pair of teenagers with cystic fibrosis meet in a hospital and fall in love',
                           'A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the '
                           'Titanic',
                           'Henry Roth is a man afraid of commitment until he meets the beautiful Lucy',
                           'An Israeli Special Forces Soldier fakes his death so he can re-emerge in New York City '
                           'as a hair stylist.',
                           'A lazy law school grad adopts a kid to impress his girlfriend, but everything does not'
                           ' go as planned',
                           'A fast-talking New York City jeweler risks everything in hope of staying afloat and alive.',
                           'It is up to a mild-mannered security guard to save the day.']
        genrelist = ['science fiction', 'action', 'comedy', 'action', 'action', 'comedy', 'comedy',
                     'science fiction', 'action', 'action', 'historic', 'adventure', 'action', 'action',
                     'survival', 'action', 'romance', 'romance', 'romance', 'comedy', 'comedy', 'action',
                     'comedy']
        current = 1
        ap = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        while current <= 20:
            while True:
                mov_id = self.generate_id()
                if self.check_id_movie(mov_id) is None:
                    break
            select = random.randint(0, 22)
            while ap[select] == 1:
                select = random.randint(0, 22)
            ap[select] = 1
            mov = Movie(mov_id, movielist[select], descriptionlist[select], genrelist[select])
            self.__repository.add_mov(mov)
            current += 1

    def add_cmd_run_movie(self, title, description, genre):
        """
        Adds a new movie for undo function

        :param title: the movie title
        :param description: the movie description
        :param genre: the movie genre
        :return:
        """

        while True:
            mov_id = self.generate_id()
            if self.check_id_movie(mov_id) is None:
                break
        mov = Movie(mov_id, title, description, genre)
        self.__validator.validate(mov)
        self.__repository.add_mov(mov)
        UndoRedoManager.register_operation(self, UndoHandler.ADD_MOVIE, mov.id, mov.title, mov.description, mov.genre)

    def add_cmd_run_movie_undo(self, mov_id, title, description, genre):
        """
        Adds a new movie

        :param title: the movie title
        :param description: the movie description
        :param genre: the movie genre
        :return:
        """

        mov = Movie(mov_id, title, description, genre)
        self.__validator.validate(mov)
        self.__repository.add_mov(mov)

    def remove_cmd_run_movie(self, id):
        """
        Removes a movie for undo function

        :param id: the movie id
        :return:
        """

        obj = self.search_by_id_movie(id)
        self.__repository.remove_mov(obj)
        UndoRedoManager.register_operation(self, UndoHandler.REMOVE_MOVIE, obj.id, obj.title, obj.description,
                                           obj.genre)

    def remove_cmd_run_movie_undo(self, id, title, description, genre):
        """
        Removes a movie

        :param id: the movie id
        :return:
        """

        obj = self.search_by_id_movie(id)
        self.__repository.remove_mov(obj)

    def update_cmd_run_movie(self, id, title, description, genre):
        """
        Updates a movie

        :param id: the movie id
        :param title: the new movie title
        :param description: the new movie description
        :param genre: the new movie genre
        :return:
        """

        obj = self.search_by_id_movie(id)
        prev_title = obj.title
        prev_description = obj.description
        prev_genre = obj.genre
        if genre is not None:
            self.__validator.validate_genre(genre)
        if title is not None:
            self.__repository.update_mov_title(obj, title)
        if description is not None:
            self.__repository.update_mov_description(obj, description)
        if genre is not None:
            self.__repository.update_mov_genre(obj, genre)
        UndoRedoManager.register_operation(self, UndoHandler.UPDATE_MOVIE, obj.id, prev_title, prev_description,
                                           prev_genre, obj.title, obj.description, obj.genre)

    def update_cmd_run_movie_undo(self, id, title, description, genre):
        """
        Updates a movie for undo function

        :param id: the movie id
        :param title: the new movie title
        :param description: the new movie description
        :param genre: the new movie genre
        :return:
        """

        obj = self.search_by_id_movie(id)
        self.__validator.validate_genre(genre)
        self.__repository.update_mov_title(obj, title)
        self.__repository.update_mov_description(obj, description)
        self.__repository.update_mov_genre(obj, genre)

    def search_by_movie_id_cmd(self, mov_id):
        """
        Searches for all the movies that contain the parameter given by the user in their id, using partial string
        matching

        :param mov_id: the parameter given by the user
        :return:
        """

        if mov_id == '' or mov_id == ' ' or mov_id.isnumeric() is False:
            raise MovieException("\nPlease enter a number or a real character\n")
        mov_id = str(mov_id)
        y = []
        mov_list = self.__repository.return_list_movies()
        for i in range(len(mov_list)):
            if mov_id in str(mov_list[i].id):
                y.append(mov_list[i])
        return y

    def search_by_movie_description_cmd(self, mov_description):
        """
        Searches for all the movies that contain the parameter given by the user in their description, using partial
        string matching

        :param mov_description: the parameter given by the user
        :return:
        """

        mov_description = str(mov_description)
        y = []
        mov_list = self.__repository.return_list_movies()
        for i in range(len(mov_list)):
            if mov_description.lower() in str(mov_list[i].description).lower():
                y.append(mov_list[i])
        return y

    def search_by_movie_title_cmd(self, mov_title):
        """
        Searches for all the movies that contain the parameter given by the user in their title, using partial string
        matching

        :param mov_title: the parameter given by the user
        :return:
        """

        if mov_title.isnumeric() is True or mov_title == ' ' or mov_title == '':
            raise MovieException("\nPlease enter a name that is not numeric\n")
        mov_title = str(mov_title)
        y = []
        mov_list = self.__repository.return_list_movies()
        for i in range(len(mov_list)):
            if mov_title.lower() in str(mov_list[i].title).lower():
                y.append(mov_list[i])
        return y

    def search_by_movie_genre(self, mov_genre):
        """
        Searches for all the movies that contain the parameter given by the user in their genre, using partial string
        matching

        :param mov_genre: the parameter given by the user
        :return:
        """

        mov_genre = str(mov_genre).lower()
        self.__validator.validate_genre(mov_genre)
        y = []
        mov_list = self.__repository.return_list_movies()
        for i in range(len(mov_list)):
            if mov_genre in str(mov_list[i].genre):
                y.append(mov_list[i])
        return y


class ServicesRentals:
    def __init__(self, repository, validator):
        self.__repository = repository
        self.__validator = validator

    @property
    def repo(self):
        """
        Returns the repository of service_client, service_movie or service_rental

        :return:
        """

        return self.__repository

    def search_by_id_rental(self, rent_id):
        """
        Searches if the rental id exists. If yes, it returns the rental, if not it raises an error

        :param rent_id: the rental id
        :return:
        """

        li = self.__repository.return_list_rentals()
        for i in range(len(li)):
            if int(li[i].rent_id) == int(rent_id):
                return li[i]
        raise ValueError("This ID does not exist")

    def check_id_rental(self, rent_id):
        """
        Checks if the rental id exists

        :param rent_id: the rental id
        :return:
        """

        li = self.__repository.return_list_rentals()
        for i in range(len(li)):
            if int(li[i].rent_id) == int(rent_id):
                return li[i]
        return None

    @staticmethod
    def generate_id():
        """
        Generates a random id: a number between 100 and 999

        :return:
        """

        ran_id = random.randint(100, 900)
        return ran_id

    def check_update_id_rent(self, id):
        """
        Checks if the id of the rental we want to update exists. If not it raises an error

        :param id:
        :return:
        """

        self.search_by_id_rental(id)

    @staticmethod
    def separate_initial_date(current_date):
        """
        Turns date(dd/mm/yyyy) into datetime type

        :param current_date: the date
        :return:
        """

        tokens = current_date.split("/")
        for s in tokens:
            s.strip()
        x = datetime.datetime(int(tokens[2]), int(tokens[1]), int(tokens[0]))
        return x

    @staticmethod
    def compute_return_date(current_date):
        """
        Computes the due date (rent date + 14)
        :param current_date: the rent date
        :return:
        """

        end_date = current_date + datetime.timedelta(days=14)
        return end_date

    def borrow_cmd_run_begin(self, cl_id, mov_id, borrow_date):
        """
        Adds a new rental if all the requirement of issuing one are satisfied

        :param cl_id: the client id
        :param mov_id: the movie id
        :param borrow_date: the date when the rental was made
        :return:
        """

        while True:
            rent_id = self.generate_id()
            if self.check_id_rental(rent_id) is None:
                break
        self.check_if_other_rental(cl_id, borrow_date, mov_id)
        due_date = self.compute_return_date(borrow_date)
        return_date = None
        rent = Rental(rent_id, mov_id, cl_id, borrow_date, due_date, return_date)
        self.__repository.add_rental(rent)

    def check_if_other_rental(self, cl_id, current_date, mov_id):
        """
        Checks if the client has a rental that is past the due date or if the client tries to rent the same movie 2
        times, without returning the 1st one

        :param cl_id: the clients id
        :param current_date: the date of rental
        :param mov_id: the movie the client wants to rent
        :return:
        """

        x = self.__repository.return_list_rentals()
        for i in range(len(x)):
            if x[i].client_id == cl_id and x[i].returned_date is None:
                self.__validator.validate_borrow(current_date, x[i].due_date)
        for i in range(len(x)):
            if x[i].client_id == cl_id and x[i].movie_id == mov_id and x[i].returned_date is None:
                self.__validator.validate_already_rented()

    def borrow_cmd_run(self, cl_id, mov_id, borrow_date):
        """
        Adds a new rental if all the requirement of issuing one are satisfied

        :param cl_id: the client id
        :param mov_id: the movie id
        :param borrow_date: the date when the rental was made
        :return:
        """

        try:
            borrow_date = self.separate_initial_date(borrow_date)
        except IndexError:
            raise RentalException("\nPlease enter a valid date!")
        while True:
            rent_id = self.generate_id()
            if self.check_id_rental(rent_id) is None:
                break
        self.check_if_other_rental(cl_id, borrow_date, mov_id)
        due_date = self.compute_return_date(borrow_date)
        return_date = None
        rent = Rental(rent_id, mov_id, cl_id, borrow_date, due_date, return_date)
        self.__repository.add_rental(rent)
        UndoRedoManager.register_operation(self, UndoHandler.BORROW, rent_id, mov_id, cl_id, borrow_date,
                                           due_date, return_date)

    def borrow_cmd_run_undo(self, rent_id, mov_id, cl_id, borrow_date, due_date, return_date):
        """
        Adds a new rental if all the requirement of issuing one are satisfied for undo function

        :param cl_id: the client id
        :param mov_id: the movie id
        :param borrow_date: the date when the rental was made
        :return:
        """

        rent = Rental(rent_id, mov_id, cl_id, borrow_date, due_date, return_date)
        self.__repository.add_rental(rent)

    def delete_rental_undo(self, rent_id):
        """
        Deletes a rental for undo function

        :param rent_id: the id of the rental
        :return:
        """

        obj = self.search_by_id_rental(rent_id)
        self.__repository.remove_borrow(obj)

    def return_cmd_run(self, rent_id, current_date):
        """
        Updates the return date of a rental when the client returns the movie

        :param rent_id: the id of the rent
        :param current_date: the day the client is returning the movie
        :return:
        """

        try:
            current_date = self.separate_initial_date(current_date)
        except IndexError:
            raise RentalException("\nPlease enter a valid date!")

        obj = self.search_by_id_rental(rent_id)
        self.__repository.update_renturned_date(obj, current_date)
        UndoRedoManager.register_operation(self, UndoHandler.RETURNED, rent_id, current_date)

    def return_cmd_run_undo(self, rent_id, current_date):
        """
        Updates the return date of a rental when the client returns the movie for undo function

        :param rent_id: the id of the rent
        :param current_date: the day the client is returning the movie
        :return:
        """

        obj = self.search_by_id_rental(rent_id)
        self.__repository.update_renturned_date(obj, current_date)
        UndoRedoManager.register_operation(self, UndoHandler.RETURNED, rent_id, current_date)

    def return_cmd_run_reset_undo(self, rent_id):
        """
        Updates the return date of a rental to None for undo function

        :param rent_id: the id of the rent
        :param current_date: the day the client is returning the movie
        :return:
        """

        current_date = None
        obj = self.search_by_id_rental(rent_id)
        self.__repository.update_renturned_date(obj, current_date)

    def filter_nr_mov_rented_cmd(self, current_date, mov_list):
        """
        Filters the movies in descending order by the number of days they were rented

        :param current_date: The current date
        :param mov_list: The list of movies
        :return:
        """

        try:
            current_date = self.separate_initial_date(current_date)
        except IndexError:
            raise RentalException("\nPlease enter a valid date!")
        z = []
        k = self.__repository.return_list_rentals()
        for i in range(len(mov_list)):
            z.append(mov_list[i])
        if len(z) == 0:
            raise RentalException("\nThere are no current movies available")
        if len(k) == 0:
            raise RentalException("\nThere are no current rentals")
        for i in range(len(k)):
            if current_date < k[i].rented_date:
                raise RentalException("\nThe current day is not valid.")

        y = []

        for movie in z:
            sum_mov = 0
            for rental in k:
                if movie.id == rental.movie_id:
                    sum_dates = current_date - rental.rented_date
                    sum_mov += sum_dates.days
            y.append(sum_mov)

        dictionary = [(0, "Gigel")]
        for i in range(len(y)):
            if y[i] != 0:
                dictionary.append((y[i], z[i]))

        ShellSort(structure=y, key=lambda x: x, reverse=True)
        for i in range(len(y)):
            for element in dictionary:
                if element[0] == y[i]:
                    z[i] = element[1]
                    break

        # for i in range(len(z)):
        #     id_movie = z[i].id
        #     sum_mov = 0
        #     for j in range(len(k)):
        #         if str(k[j].movie_id) == str(id_movie):
        #             sum_dates = current_date - k[j].rented_date
        #             sum_mov += sum_dates.days
        #     y.append(sum_mov)
        # for i in range(len(y)):
        #     for j in range(i, len(y)):
        #         if y[j] > y[i]:
        #             aux1 = y[i]
        #             aux2 = z[i]
        #             y[i] = y[j]
        #             z[i] = z[j]
        #             y[j] = aux1
        #             z[j] = aux2

        return z, y

    def filter_most_active_clients_cmd(self, current_date, cl_list):
        """
        Filters the clients in descending order by the sum of days of any available rental they have

        :param current_date: the current date
        :param cl_list: the client list
        :return:
        """

        try:
            current_date = self.separate_initial_date(current_date)
        except IndexError:
            raise RentalException("\nPlease enter a valid date!")
        z = []
        k = self.__repository.return_list_rentals()
        for i in range(len(cl_list)):
            z.append(cl_list[i])
        if len(z) == 0:
            raise RentalException("\nThere are no current clients")
        if len(k) == 0:
            raise RentalException("\nThere are no current rentals")
        for i in range(len(k)):
            if current_date < k[i].rented_date:
                raise RentalException("\nThe current day is not valid.")

        y = []

        for client in z:
            sum_mov = 0
            for rental in k:
                if rental.client_id == client.client_id:
                    sum_dates = current_date - rental.rented_date
                    sum_mov += sum_dates.days
            y.append(sum_mov)

        dictionary = [(0, "Gigel")]
        for i in range(len(y)):
            if y[i] != 0:
                dictionary.append((y[i], z[i]))

        ShellSort(structure=y, key=lambda x: x, reverse=True)
        for i in range(len(y)):
            for element in dictionary:
                if element[0] == y[i]:
                    z[i] = element[1]
                    break

        # for i in range(len(z)):
        #     id_client = z[i].client_id
        #     sum_mov = 0
        #     for j in range(len(k)):
        #         if str(k[j].client_id) == str(id_client):
        #             sum_dates = current_date - k[j].rented_date
        #             sum_mov += sum_dates.days
        #     y.append(sum_mov)
        # for i in range(len(y)):
        #     for j in range(i, len(y)):
        #         if y[j] > y[i]:
        #             aux1 = y[i]
        #             aux2 = z[i]
        #             y[i] = y[j]
        #             z[i] = z[j]
        #             y[j] = aux1
        #             z[j] = aux2

        return z, y

    def filter_late_rentals_cmd(self, current_date, rental_list):
        """
        Filters the rentals and sends the UI a list of late rentals in descending order of number of days
        past due date

        :param current_date: the current date
        :param rental_list: the list of rentals
        :return:
        """

        try:
            current_date = self.separate_initial_date(current_date)
        except IndexError:
            raise RentalException("\nPlease enter a valid date!")
        if len(rental_list) == 0:
            raise RentalException("\nThere are no current clients")
        for i in range(len(rental_list)):
            if current_date < rental_list[i].rented_date:
                raise RentalException("\nThe current day is not valid.")

        z = []
        y = []

        for rental in rental_list:
            if rental.returned_date is None:
                z.append(rental)
                sum_dates = current_date - rental.due_date
                sum_dates = sum_dates.days
                y.append(sum_dates)

        Filter(structure=z, key=lambda x: x.due_date < current_date)
        Filter(structure=y, key=lambda x: x > 0)
        ShellSort(structure=y, key=lambda x: x, reverse=True)
        ShellSort(structure=z, key=lambda x: x.due_date, reverse=False)

        # for i in range(len(rental_list)):
        #     if current_date > rental_list[i].due_date and rental_list[i].returned_date is None:
        #         sum_dates = current_date - rental_list[i].due_date
        #         sum_mov = sum_dates.days
        #         z.append(rental_list[i])
        #         y.append(sum_mov)
        # for i in range(len(y)):
        #     for j in range(i, len(y)):
        #         if y[j] > y[i]:
        #             aux1 = y[i]
        #             aux2 = z[i]
        #             y[i] = y[j]
        #             z[i] = z[j]
        #             y[j] = aux1
        #             z[j] = aux2

        return z, y
