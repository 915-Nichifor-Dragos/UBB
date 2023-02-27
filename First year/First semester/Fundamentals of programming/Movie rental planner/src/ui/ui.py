"""

@author: Nichifor Dragos

"""
import datetime
import random

from src.data_structure.sorting import ShellSort
from src.domain.validators import MovieException, ClientException, RentalException
from src.services.undo import UndoRedoManager


class UI:
    def __init__(self, service_clients, service_movies, service_rentals):
        self.__service_clients = service_clients
        self.__service_movies = service_movies
        self.__service_rentals = service_rentals

    @staticmethod
    def print_update_movie_menu():
        print("\n1 - Update Title")
        print("2 - Update Description")
        print("3 - Update genre")
        print("x - exit\n")

    @staticmethod
    def print_update_movie_menu_internal():
        print("\n1 - Update Title")
        print("2 - Update Description")
        print("3 - Update genre")
        print("x - Done\n")

    @staticmethod
    def print_update_client_menu():
        print("\n1 - Update Name")
        print("x - Don't update\n")

    @staticmethod
    def exit_function():
        print("Exiting...")

    @staticmethod
    def menu():
        print("\n1 - Add a client/movie")
        print("2 - Remove a client/movie")
        print("3 - Update a client/movie")
        print("4 - List both clients and movies")
        print("5 - Borrow/return a movie")
        print("6 - Create statistics")
        print("7 - Search a client/movie")
        print("8 - Undo operation")
        print("9 - Redo operation")
        print("x - Exit\n")

    @staticmethod
    def borrow_menu():
        print("1 - Borrow a movie")
        print("2 - Return a movie")

    @staticmethod
    def statistics_menu():
        print("1 - Number of movies rented")
        print("2 - Most active clients")
        print("3 - Late rentals\n")

    @staticmethod
    def check_option(option):
        if option == "1" or option == "2" or option == "3" or option == "4" or option == 1 or option == 2 or \
                option == 3 or option == 4 or option == 5 or option == "5" or option == "6" or option == 6 or \
                option == 7 or option == "7" or option == 8 or option == "8" or option == 9 or option == "9":
            return True
        raise ValueError("Please enter a valid command!")

    @staticmethod
    def check_add_cmd(value):
        if value == "1" or value == "2" or value == 1 or value == 2:
            return True
        raise ValueError("Please enter a valid command!")

    @staticmethod
    def check_filter_search_cmd(value):
        if value == "1" or value == "2" or value == 1 or value == 2 or value == "3" or value == 3:
            return True
        raise ValueError("Please enter a valid command!")

    @staticmethod
    def print_element_movie(id, title, description, movie):
        print("ID: ", id, " ✪  Movie:", title, " ✪  Description: ", description, " ✪  Type: ", movie)

    @staticmethod
    def print_element_client(id, name):
        print("ID: ", id, " ✪  Name: ", name)

    @staticmethod
    def print_element_rental(rent_id, mov_id, cl_id, rented_date, due_date, returned_date):
        if returned_date is not None:
            print("RENT ID:", rent_id, "CLIENT ID:", cl_id, "MOVIE ID:", mov_id, "RENTED DATE:",
                  rented_date.strftime("%d"),
                  rented_date.strftime("%b"), rented_date.strftime("%Y"), "DUE DATE:", due_date.strftime("%d"),
                  due_date.strftime("%b"), due_date.strftime("%Y"), "RETURNED DATE:", returned_date.strftime("%d"),
                  returned_date.strftime("%b"), returned_date.strftime("%Y"))
        else:
            print("RENT ID:", rent_id, "CLIENT ID:", cl_id, "MOVIE ID:", mov_id, "RENTED DATE:",
                  rented_date.strftime("%d"),
                  rented_date.strftime("%b"), rented_date.strftime("%Y"), "DUE DATE:", due_date.strftime("%d"),
                  due_date.strftime("%b"), due_date.strftime("%Y"), "RETURNED DATE: None")

    @staticmethod
    def no_element_movie():
        print("There are no movies")

    @staticmethod
    def no_element_client():
        print("There are no clients")

    @staticmethod
    def no_element_rental_list():
        print("There are no current rentals")

    @staticmethod
    def check_filter(cmd):
        if cmd != "1" and cmd != "2" and cmd != "3":
            raise ValueError("Please enter a valid command!")

    @staticmethod
    def search_menu():
        print("1 - Search a client")
        print("2 - Search a movie\n")

    @staticmethod
    def search_client_menu():
        print("1 - Search by ID")
        print("2 - Search by name")

    @staticmethod
    def search_movie_menu():
        print("1 - Search by ID")
        print("2 - Search by description")
        print("3 - Search by title")
        print("4 - Search by genre")

    def check_if_no_movie(self):
        movie_list = self.__service_movies.repo.return_list_movies()
        if len(movie_list) == 0:
            return False
        return True

    def check_if_no_client(self):
        client_list = self.__service_clients.repo.return_list_clients()
        if len(client_list) == 0:
            return False
        return True

    def print_movies(self):
        movie_list = self.__service_movies.repo.return_list_movies()
        if len(movie_list) == 0:
            self.no_element_movie()
            return
        for i in range(len(movie_list)):
            self.print_element_movie(movie_list[i].id, movie_list[i].title, movie_list[i].description,
                                     movie_list[i].genre)

    def print_clients(self):
        client_list = self.__service_clients.repo.return_list_clients()
        if len(client_list) == 0:
            self.no_element_client()
        for i in range(len(client_list)):
            self.print_element_client(client_list[i].client_id, client_list[i].name)

    def print_rentals(self):
        rental_list = self.__service_rentals.repo.return_list_rentals()
        if len(rental_list) == 0:
            self.no_element_rental_list()
        for i in range(len(rental_list)):
            self.print_element_rental(rental_list[i].rent_id, rental_list[i].movie_id, rental_list[i].client_id,
                                      rental_list[i].rented_date, rental_list[i].due_date,
                                      rental_list[i].returned_date)

    def add_cmd_client_ui(self):
        try:
            UndoRedoManager.empty_redo()
            name = input("Please enter the client name: ")
            self.__service_clients.add_cmd_run_client(name)
            print("\nClient added successfully")
        except ClientException as ve:
            print(ve)

    def add_cmd_movie_ui(self):
        try:
            UndoRedoManager.empty_redo()
            title = input("Please enter the movie title: ")
            description = input("Please enter the movie description: ")
            genre = input("Please enter the genre: ")
            self.__service_movies.add_cmd_run_movie(title, description, genre)
            print("\nMovie added successfully")
        except MovieException as ve:
            print(ve)

    def remove_client_ui(self):
        if not self.check_if_no_client():
            raise ValueError("Cannot remove client, as there is no client")
        UndoRedoManager.empty_redo()
        cmd = input("The ID is: ")
        self.__service_clients.remove_cmd_run_client(cmd)
        print("\nClient removed successfully")

    def remove_movie_ui(self):
        if not self.check_if_no_movie():
            raise ValueError("Cannot remove movie, as there is no movie")
        UndoRedoManager.empty_redo()
        cmd = input("The ID is: ")
        self.__service_movies.remove_cmd_run_movie(cmd)
        print("\nMovie removed successfully")

    def update_client_ui(self):
        if not self.check_if_no_client():
            raise ValueError("Cannot update movie, as there is no client")
        UndoRedoManager.empty_redo()
        cmd = input("The ID is: ")
        self.__service_clients.check_update_id_client(cmd)
        self.print_update_client_menu()
        res = input("Your command: ")
        if res == 'X' or res == 'x':
            return
        if res == '1' or res == 1:
            new_name = input("The new name is: ")
            self.__service_clients.update_cmd_run_client(cmd, new_name)
            print("\nClient updated successfully")
            return
        raise ValueError("Please enter a valid command!")

    def update_movie_ui(self):
        try:
            if not self.check_if_no_movie():
                raise ValueError("Cannot update movie, as there is no movie")
            UndoRedoManager.empty_redo()
            cmd = input("The ID is: ")
            self.__service_movies.check_update_id_movie(cmd)
            self.print_update_movie_menu()
            title = None
            description = None
            genre = None
            res = input("Your command is: ")
            if res == "x" or res == "X":
                return
            while res != "x" and res != "X":
                if res == 1 or res == "1":
                    title = input("The new title is: ")
                if res == 2 or res == "2":
                    description = input("The new description is: ")
                if res == 3 or res == "3":
                    genre = input("The new genre is: ")
                if title is not None and description is not None and genre is not None:
                    break
                if res != "1" and res != "2" and res != "3" and res != "x" and res != "X":
                    raise ValueError("Please enter a valid command!")
                self.print_update_movie_menu_internal()
                res = input("Your command is: ")
            self.__service_movies.update_cmd_run_movie(cmd, title, description, genre)
            print("\nMovie updated successfully")
        except MovieException as ve:
            print(ve)

    def list_cmd_ui(self):
        self.print_movies()
        print("-------------------------------------------------------------------------")
        self.print_clients()
        print("-------------------------------------------------------------------------")
        self.print_rentals()

    def borrow_a_movie_ui(self):
        try:
            cl_id = input("\nThe ID of the client: ")
            print("")
            self.__service_clients.check_update_id_client(cl_id)
            mov_id = input("The ID of the movie: ")
            self.__service_movies.check_update_id_movie(mov_id)
            print("")
            current_date = input("The current date (must be in dd/mm/aaaa format): ")
            UndoRedoManager.empty_redo()
            self.__service_rentals.borrow_cmd_run(cl_id, mov_id, current_date)
        except RentalException as ve:
            print(ve)
        except ValueError as ve:
            print(ve)

    def return_a_movie_ui(self):
        try:
            rent_id = input("\nThe rent ID: ")
            print("")
            self.__service_rentals.check_update_id_rent(rent_id)
            current_date = input("The return date (must be in dd/mm/aaaa format): ")
            UndoRedoManager.empty_redo()
            self.__service_rentals.return_cmd_run(rent_id, current_date)
        except RentalException as ve:
            print(ve)
        except ValueError as ve:
            print(ve)

    @staticmethod
    def print_element_movie_filter(id, title, movie, days):
        print("ID: ", id, " ✪  Movie:", title, " ✪  Type: ", movie,
              " ✪  Number of days rented: ", days)

    def print_filter_nr_mov_rented(self, z, y):
        print("")
        for i in range(len(z)):
            if y[i] != 0:
                self.print_element_movie_filter(z[i].id, z[i].title, z[i].genre, y[i])

    def filter_nr_mov_rented(self):
        try:
            current_date = input("The current date (must be in dd/mm/aaaa format): ")
            mov_list = self.__service_movies.repo.return_list_movies()
            z, y = self.__service_rentals.filter_nr_mov_rented_cmd(current_date, mov_list)
            self.print_filter_nr_mov_rented(z, y)
        except RentalException as ve:
            print(ve)

    @staticmethod
    def print_element_client_filter(id, name, days):
        print("ID: ", id, " ✪  Name: ", name, " ✪  Number of days rented: ", days)

    def print_filter_most_active_clients(self, z, y):
        print("")
        for i in range(len(z)):
            if y[i] != 0:
                self.print_element_client_filter(z[i].client_id, z[i].name, y[i])

    def filter_most_active_clients(self):
        try:
            current_date = input("The current date (must be in dd/mm/aaaa format): ")
            cl_list = self.__service_clients.repo.return_list_clients()
            z, y = self.__service_rentals.filter_most_active_clients_cmd(current_date, cl_list)
            self.print_filter_most_active_clients(z, y)
        except RentalException as ve:
            print(ve)

    @staticmethod
    def print_element_rental_filter(rent_id, mov_id, cl_id, rented_date, due_date, days):
        print("RENT ID:", rent_id, "CLIENT ID:", cl_id, "MOVIE ID:", mov_id, "RENTED DATE:",
              rented_date.strftime("%d"),
              rented_date.strftime("%b"), rented_date.strftime("%Y"), "DUE DATE:", due_date.strftime("%d"),
              due_date.strftime("%b"), due_date.strftime("%Y"), "RETURNED DATE: None", "NUMBER OF DAYS PAST RETURN: ",
              days)

    def print_filter_late_rentals(self, rental_list, y):
        print("")
        for i in range(len(rental_list)):
            self.print_element_rental_filter(rental_list[i].rent_id, rental_list[i].movie_id, rental_list[i].client_id,
                                             rental_list[i].rented_date, rental_list[i].due_date, y[i])

    def filter_late_rentals(self):
        try:
            current_date = input("The current date (must be in dd/mm/aaaa format): ")
            rental_list = self.__service_rentals.repo.return_list_rentals()
            z, y = self.__service_rentals.filter_late_rentals_cmd(current_date, rental_list)
            self.print_filter_late_rentals(z, y)
        except RentalException as ve:
            print(ve)

    def create_begin_rentals(self):
        cl = self.__service_clients.repo.return_list_clients()
        mv = self.__service_movies.repo.return_list_movies()
        for i in range(8):
            client = cl[i].client_id
            movie = mv[i].id
            month = random.randint(1, 12)
            day = random.randint(1, 27)
            year = 2020
            random_date = datetime.datetime(year, month, day)
            self.__service_rentals.borrow_cmd_run_begin(client, movie, random_date)

    def print_search_clients(self, z):
        print("")
        if len(z) == 0:
            print("There are no results to the search")
            return
        for i in range(len(z)):
            self.print_element_client(z[i].client_id, z[i].name)

    def search_client_ui(self):
        try:
            self.search_client_menu()
            cmd = input("\nYour command: ")
            print("")
            if cmd == 1 or cmd == "1":
                cl_id = input("The ID: ")
                z = self.__service_clients.search_by_client_id_cmd(cl_id)
                self.print_search_clients(z)
                return
            if cmd == 2 or cmd == "2":
                cl_name = input("The client name: ")
                z = self.__service_clients.search_by_client_name_cmd(cl_name)
                self.print_search_clients(z)
                return
            raise ValueError("Please enter a valid command\n")
        except ClientException as ve:
            print(ve)

    def print_search_movies(self, z):
        print("")
        if len(z) == 0:
            print("There are no results to the search")
            return
        for i in range(len(z)):
            self.print_element_movie(z[i].id, z[i].title, z[i].description, z[i].genre)

    def search_movie_ui(self):
        try:
            self.search_movie_menu()
            cmd = input("\nYour command: ")
            print("")
            if cmd == 1 or cmd == "1":
                mov_id = input("The ID: ")
                z = self.__service_movies.search_by_movie_id_cmd(mov_id)
                self.print_search_movies(z)
                return
            if cmd == 2 or cmd == "2":
                mov_description = input("The movie description: ")
                z = self.__service_movies.search_by_movie_description_cmd(mov_description)
                self.print_search_movies(z)
                return
            if cmd == 3 or cmd == "3":
                mov_title = input("The movie title: ")
                z = self.__service_movies.search_by_movie_title_cmd(mov_title)
                self.print_search_movies(z)
                return
            if cmd == 4 or cmd == "4":
                mov_genre = input("The movie genre: ")
                z = self.__service_movies.search_by_movie_genre(mov_genre)
                self.print_search_movies(z)
                return
            raise ValueError("Please enter a valid command\n")
        except MovieException as ve:
            print(ve)

    @staticmethod
    def undo_cmd_ui():
        try:
            UndoRedoManager.undo()
            print("Undo was successful")
        except ValueError as ve:
            print(ve)

    @staticmethod
    def redo_cmd_ui():
        try:
            UndoRedoManager.redo()
            print("Redo was successful")
        except ValueError as ve:
            print(ve)

    def add_begin(self):
        self.__service_movies.add_begin_movies()
        self.__service_clients.add_begin_clients()
        self.create_begin_rentals()

    def start_ui(self):

        while True:
            try:
                ShellSort(structure=self.__service_clients.repo.return_list_clients(), key=lambda x: x.client_id,
                          reverse=False)
                ShellSort(structure=self.__service_movies.repo.return_list_movies(), key=lambda x: x.id,
                          reverse=False)
                ShellSort(structure=self.__service_rentals.repo.return_list_rentals(), key=lambda x: x.rent_id,
                          reverse=False)
                self.menu()
                opt = input("Please enter your command: ")
                print(" ")
                if opt.lower() == "x":
                    self.exit_function()
                    return
                self.check_option(opt)
                if opt == 1 or opt == "1":
                    print("1 - Add a client")
                    print("2 - Add a movie\n")
                    opt_add = input("Your command: ")
                    self.check_add_cmd(opt_add)
                    if opt_add == "1":
                        self.add_cmd_client_ui()
                    if opt_add == "2":
                        self.add_cmd_movie_ui()
                if opt == 2 or opt == "2":
                    print("1 - Remove a client")
                    print("2 - Remove a movie\n")
                    opt_remove = input("Your command: ")
                    self.check_add_cmd(opt_remove)
                    if opt_remove == "1":
                        self.remove_client_ui()
                    if opt_remove == "2":
                        self.remove_movie_ui()
                if opt == 3 or opt == "3":
                    print("1 - Update a client")
                    print("2 - Update a movie\n")
                    opt_update = input("Your command: ")
                    self.check_add_cmd(opt_update)
                    if opt_update == "1":
                        self.update_client_ui()
                    if opt_update == "2":
                        self.update_movie_ui()
                if opt == 4 or opt == "4":
                    self.list_cmd_ui()
                if opt == 5 or opt == "5":
                    self.borrow_menu()
                    cmd = input("\nYour command is: ")
                    self.check_add_cmd(cmd)
                    if cmd == "1" or cmd == 1:
                        self.borrow_a_movie_ui()
                    if cmd == "2" or cmd == 2:
                        self.return_a_movie_ui()
                if opt == 6 or opt == "6":
                    self.statistics_menu()
                    cmd = input("Your command: ")
                    self.check_filter_search_cmd(cmd)
                    print("")
                    if cmd == "1":
                        self.filter_nr_mov_rented()
                    if cmd == "2":
                        self.filter_most_active_clients()
                    if cmd == "3":
                        self.filter_late_rentals()
                if opt == 7 or opt == "7":
                    self.search_menu()
                    cmd = input("Your command: ")
                    self.check_add_cmd(cmd)
                    print("")
                    if cmd == "1":
                        self.search_client_ui()
                    if cmd == "2":
                        self.search_movie_ui()
                if opt == 8 or opt == "8":
                    self.undo_cmd_ui()
                if opt == 9 or opt == "9":
                    self.redo_cmd_ui()
            except ValueError as ve:
                print(ve)
            except TypeError as te:
                print(te)
