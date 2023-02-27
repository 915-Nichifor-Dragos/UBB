"""

@author: Nichifor Dragos





Write an application for movie rentals. The application will store:

Movie: movie_id, title, description, genre
Client: client_id, name
Rental: rental_id, movie_id, client_id, rented_date, due_date, returned_date
Create an application which allows to:

--> Manage clients and movies. The user can add, remove, update, and list both clients and movies.

--> Rent or return a movie. A client can rent a movie until a given date, as long as they have no rented movies
that passed their due date for return. A client can return a rented movie at any time.

--> Search for clients or movies using any one of their fields (e.g. movies can be searched for using id, title,
description or genre). The search must work using case-insensitive, partial string matching, and must return all
matching items.

Create statistics:

<> Most rented movies. This will provide the list of movies, sorted in descending order of the number of days they were
rented.
<> Most active clients. This will provide the list of clients, sorted in descending order of the number of movie rental
days they have (e.g. having 2 rented movies for 3 days each counts as 2 x 3 = 6 days).
<> Late rentals. All the movies that are currently rented, for which the due date for return has passed, sorted in
descending order of the number of days of delay.
<> Unlimited undo/redo functionality. Each step will undo/redo the previous operation performed by the user. Undo/redo
operations must cascade and have a memory-efficient implementation (no superfluous list copying).

"""
from src.domain.validators import ClientValidator, MovieValidator, RentalValidator
from src.repository.binary_file_repository import BinRepositoryClients, BinRepositoryMovies, BinRepositoryRentals
from src.repository.file_repository import FileRepositoryClients, FileRepositoryMovies, FileRepositoryRentals
from src.repository.repository import RepositoryRentals, RepositoryClients, RepositoryMovies
from src.services.services import ServicesRentals, ServicesClients, ServicesMovies
from src.ui.ui import UI


def check_option(cmd):
    if cmd != 1 and cmd != "1" and cmd != 2 and cmd != "2":
        raise ValueError("\nPlease enter a valid command\n")


def print_menu():
    print("\n1 - Start with UI")
    print("2 - Start with GUI")


def in_memory(client, movie, rental):
    client_validator = ClientValidator
    repository_clients = RepositoryClients()
    service_clients = ServicesClients(repository_clients, client_validator)

    movie_validator = MovieValidator
    repository_movies = RepositoryMovies()
    service_movies = ServicesMovies(repository_movies, movie_validator)

    rental_validator = RentalValidator
    repository_rentals = RepositoryRentals()
    service_rentals = ServicesRentals(repository_rentals, rental_validator)

    st = UI(service_clients, service_movies, service_rentals)
    st.add_begin()
    st.start_ui()


def text_files(client, movie, rental):
    client_validator = ClientValidator
    repository_clients = FileRepositoryClients(client)
    service_clients = ServicesClients(repository_clients, client_validator)

    movie_validator = MovieValidator
    repository_movies = FileRepositoryMovies(movie)
    service_movies = ServicesMovies(repository_movies, movie_validator)

    rental_validator = RentalValidator
    repository_rentals = FileRepositoryRentals(rental)
    service_rentals = ServicesRentals(repository_rentals, rental_validator)

    st = UI(service_clients, service_movies, service_rentals)
    st.start_ui()


def binary_files(client, movie, rental):
    # TODO: FOR BINARY FILE REPO
    client_validator = ClientValidator
    repository_clients = BinRepositoryClients(client)
    service_clients = ServicesClients(repository_clients, client_validator)

    movie_validator = MovieValidator
    repository_movies = BinRepositoryMovies(movie)
    service_movies = ServicesMovies(repository_movies, movie_validator)

    rental_validator = RentalValidator
    repository_rentals = BinRepositoryRentals(rental)
    service_rentals = ServicesRentals(repository_rentals, rental_validator)

    st = UI(service_clients, service_movies, service_rentals)
    st.start_ui()


def translate(cmd):
    if cmd == 'inmemory':
        return in_memory
    if cmd == 'textfiles':
        return text_files
    if cmd == 'binaryfiles':
        return binary_files


"""
-> START TEXT FILES

repository=textfiles
client_repo=../data/client_repo.txt
movie_repo=../data/movie_repo.txt
rental_repo=../data/rental_repo.txt

-> START IN MEMORY

repository=inmemory
client_repo=../data/client_repo.txt
movie_repo=../data/movie_repo.txt
rental_repo=../data/rental_repo.txt

-> START BINARY FILES

repository=binaryfiles
client_repo=../data_binary/b_client_repo.bin
movie_repo=../data_binary/b_movie_repo.bin
rental_repo=../data_binary/b_rental_repo.bin

"""

if __name__ == '__main__':
    token = {}
    f = open("../settings/settings.properties")
    for line in f.readlines():
        k = line.split('=')
        k[1] = k[1].strip()
        token[k[0]] = k[1]
    f.close()

    try:
        translate(token['repository'])(token['client_repo'], token['movie_repo'], token['rental_repo'])
    except TypeError as te:
        print("\nAn error occurred -", te)
        print("\nThe repository type, the text files or the binary files do not exist!\nExiting...\n")
