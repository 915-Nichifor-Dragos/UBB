"""

author: Nichifor Dragos

"""
from dataclasses import dataclass
from enum import Enum


def add_client_handler(client_service, client_id, name):
    client_service.remove_cmd_run_client_undo(client_id, name)


def remove_client_handler(client_service, client_id, name):
    client_service.add_cmd_run_client_undo(client_id, name)


def update_client_handler(client_service, client_id, name, prev_name):
    client_service.update_cmd_run_client_undo(client_id, name)


def update_client_handler_redo(client_service, client_id, name, prev_name):
    client_service.update_cmd_run_client_undo(client_id, prev_name)


def add_movie_handler(movie_service, movie_id, name, description, genre):
    movie_service.remove_cmd_run_movie_undo(movie_id, name, description, genre)


def remove_movie_handler(movie_service, movie_id, name, description, genre):
    movie_service.add_cmd_run_movie_undo(movie_id, name, description, genre)


def update_movie_handler(movie_service, movie_id, name, description, genre, prev_name, prev_description, prev_genre):
    movie_service.update_cmd_run_movie_undo(movie_id, name, description, genre)


def update_movie_handler_redo(movie_service, movie_id, name, description, genre, prev_name, prev_description,
                              prev_genre):
    movie_service.update_cmd_run_movie_undo(movie_id, prev_name, prev_description, prev_genre)


def remove_borrow_handler(rental_service, rent_id, mov_id, cl_id, borrow_date,
                          due_date, return_date):
    rental_service.delete_rental_undo(rent_id)


def add_cmd_borrow_handler(rental_service, rent_id, mov_id, cl_id, borrow_date, due_date, return_date):
    rental_service.borrow_cmd_run_undo(rent_id, mov_id, cl_id, borrow_date, due_date, return_date)


def unreturned_handler(rental_service, rent_id, current_date):
    rental_service.return_cmd_run_reset_undo(rent_id)


def return_cmd_handler(rental_service, rent_id, current_date):
    rental_service.return_cmd_run_undo(rent_id, current_date)


@dataclass
class UndoRedoOperation:
    target_object: object
    handler: object
    args: tuple


class UndoRedoManager:
    __undo_operations = []
    __redo_operations = []

    @staticmethod
    def empty_undo():
        UndoRedoManager.__undo_operations.clear()

    @staticmethod
    def empty_redo():
        UndoRedoManager.__redo_operations.clear()

    @staticmethod
    def register_operation(target_object, handler, *args):
        UndoRedoManager.__undo_operations.append(UndoRedoOperation(target_object, handler, args))

    @staticmethod
    def register_operation_redo(target_object, handler, args):
        UndoRedoManager.__redo_operations.append(
            UndoRedoOperation(target_object, UndoRedoManager.translate_redo(handler), args))

    @staticmethod
    def register_operation_undo(target_object, handler, args):
        UndoRedoManager.__undo_operations.append(
            UndoRedoOperation(target_object, UndoRedoManager.translate_redo(handler), args))

    @staticmethod
    def undo():
        if len(UndoRedoManager.__undo_operations) == 0:
            raise ValueError("Can not undo")
        undo_operation = UndoRedoManager.__undo_operations.pop()
        UndoRedoManager.register_operation_redo(undo_operation.target_object, undo_operation.handler,
                                                undo_operation.args)
        undo_operation.handler(undo_operation.target_object, *undo_operation.args)

    @staticmethod
    def redo():
        if len(UndoRedoManager.__redo_operations) == 0:
            raise ValueError("Can not redo")
        redo_operation = UndoRedoManager.__redo_operations.pop()
        UndoRedoManager.register_operation_undo(redo_operation.target_object, redo_operation.handler,
                                                redo_operation.args)
        redo_operation.handler(redo_operation.target_object, *redo_operation.args)

    @staticmethod
    def translate_redo(obj):
        if obj == add_client_handler:
            return remove_client_handler
        if obj == remove_client_handler:
            return add_client_handler
        if obj == update_client_handler:
            return update_client_handler_redo
        if obj == update_client_handler_redo:
            return update_client_handler
        if obj == add_movie_handler:
            return remove_movie_handler
        if obj == remove_movie_handler:
            return add_movie_handler
        if obj == update_movie_handler:
            return update_movie_handler_redo
        if obj == update_movie_handler_redo:
            return update_movie_handler
        if obj == remove_borrow_handler:
            return add_cmd_borrow_handler
        if obj == add_cmd_borrow_handler:
            return remove_borrow_handler
        if obj == unreturned_handler:
            return return_cmd_handler
        if obj == return_cmd_handler:
            return unreturned_handler


class UndoHandler(Enum):
    ADD_CLIENT = add_client_handler
    REMOVE_CLIENT = remove_client_handler
    UPDATE_CLIENT = update_client_handler
    ADD_MOVIE = add_movie_handler
    REMOVE_MOVIE = remove_movie_handler
    UPDATE_MOVIE = update_movie_handler
    BORROW = remove_borrow_handler
    RETURNED = unreturned_handler
