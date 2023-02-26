"""

@author: Nichifor Dragos

"""

import math


# ============================================= DOMAIN =============================================


def get_real(number):
    """
    This function returns the real part of a complex number

    :param number: The complex number
    :return: Returns the real part of the complex number
    """

    return number['real']


def get_imaginary(number):
    """
    This function returns the imaginary part of a complex number

    :param number: The complex number
    :return: Returns the imaginary part of the complex number
     """

    return number['imaginary']


def set_real(number, real):
    """
    Sets a new value to the real part of a complex number

    :param number: The complex number
    :param real: The real part you want to attribute to the complex number
    """

    number['real'] = real


def set_imaginary(number, imaginary):
    """
        Sets a new value to the imaginary part of a complex number

        :param number: The complex number
        :param imaginary: The imaginary part you want to attribute to the complex number
    """

    number['imaginary'] = imaginary


def create_number(real, imaginary):
    """
    This function creates a complex number in the form of a dictionary

    :param real: The real part of the complex number
    :param imaginary: The imaginary part of the complex number
    :return: The complex number in the form of a dictionary
    """

    return {'real': real, 'imaginary': imaginary}


# =========================================== OPERATIONS ===========================================

def add_complex_number(numbers, real, imaginary):
    """
    Adds a complex number to the complex number list. The complex numbers are represented in a dictionary

    :param numbers: The list of complex numbers
    :param real: The real part of the complex number you want to add to the list
    :param imaginary: The imaginary part of the complex number you want to add to the list
    """

    number = create_number(real, imaginary)
    numbers.append(number)


def check_longest_module_sequence(module_list):
    """
    Checks which is the longest sequence of complex numbers with a length bigger than 1 with the same
    modulus

    :param module_list: The list where the modulus of all the complex numbers are stored
    :return: The length and the starting index of the longest sequence of complex numbers having the
    same modulus
    """

    start_pos = int(0)
    max_length = int(0)
    length = int(1)
    current = int(1)
    while current < len(module_list):
        if module_list[current] == module_list[current - 1]:
            length += 1
        else:
            if length > max_length:
                start_pos = current - length
                max_length = length
            length = 1
        current += 1
    if length > max_length:
        start_pos = current - length
        max_length = length
    return max_length, start_pos


def check_module(number):
    """
    Calculates the modulus of a given complex number

    :param number: The complex number
    :return: The module of a complex number
    """

    return math.sqrt(int(get_real(number)) * int(get_real(number)) +
                     int(get_imaginary(number)) * int(get_imaginary(number)))


def create_module_list(numbers):
    """
    This function creates a list in which we store the modulus of each complex number

    :param numbers: The list of complex number
    """

    module_list = []
    current = 0
    while current < len(numbers):
        module = check_module(numbers[current])
        module_list.append(module)
        current += 1
    length, start_pos = check_longest_module_sequence(module_list)
    length = int(length)
    start_pos = int(start_pos)
    if length == 1:
        print("There are no sequences of complex numbers with the same module")
    else:
        print_module_list(start_pos, length, numbers)


def check_real_numbers_longest_list(numbers):
    """
    Checks which is the longest sequence of real numbers (with no existing imaginary part)

    :param numbers: The list of complex numbers
    """
    start_pos = int(0)
    max_length = int(0)
    length = int(0)
    current = int(0)
    while current < len(numbers):
        if int(get_imaginary(numbers[current])) == 0:
            length += 1
        else:
            if length > max_length:
                start_pos = current - length
                max_length = length
            length = 0
        current += 1
    if length > max_length:
        start_pos = current - length
        max_length = length
    print_real_number_list(numbers, start_pos, max_length)


# ============================================== UI ================================================

def print_menu_items():
    """
    Prints the menu in the console

    """

    print("\n1 - add complex number")
    print("2 - print all the complex numbers")
    print("3 - print the longest sequence where the complex numbers have the same modulus (task 3)")
    print("4 - print the longest sequence where all the numbers are real (task 5)")
    print("x - exit\n")


def add_numbers_ui(numbers):
    """
    Asks the user for the real and imaginary part of a new complex number, in order to create it

    :param numbers: The list of complex numbers
    """

    real = input("Enter the real part: ")
    imaginary = input("Enter the imaginary part: ")

    add_complex_number(numbers, real, imaginary)


def print_number(number):
    """
    Prints a number in the console

    :param number: The complex number stored in a dictionary
    """

    imaginary = get_imaginary(number)
    imaginary = int(imaginary)
    if imaginary == 0:
        print(get_real(number))
    elif imaginary > 0:
        result = get_real(number) + " +" + get_imaginary(number) + "i"
        print(result)
    elif imaginary < 0:
        result = get_real(number) + " " + get_imaginary(number) + "i"
        print(result)


def print_real_number_list(numbers, start_pos, length):
    """
    Prints the longest sequence of real numbers

    :param numbers: The list of complex numbers
    :param start_pos: The starting position of the longest sequence of real numbers
    :param length: The length of the longest sequence of real numbers
    """

    end = start_pos + length
    end = int(end)
    while start_pos < end:
        print_number(numbers[start_pos])
        start_pos += 1


def print_numbers_ui(numbers):
    """
    Prints all the numbers read by the user

    :param numbers: The list of complex numbers
    """

    current = 0
    while current < len(numbers):
        print_number(numbers[current])
        current += 1


def print_module_list(start_pos, length, numbers):
    """
    Prints the longest sequence of complex numbers with the same modulus

    :param start_pos: The starting position of the longest sequence of complex numbers having the same modulus
    :param length: The length of the longest sequence of complex numbers having the same modulus-
    :param numbers: The list of complex numbers
    """

    end = start_pos + length
    while start_pos < end:
        print_number(numbers[start_pos])
        start_pos += 1


def run_menu():
    """
    Runs the menu until the user selects the exit input ("x")

    """
    numbers = [{'real': "10", 'imaginary': "3"}, {'real': "10", 'imaginary': "-3"}, {'real': "-10", 'imaginary': "-3"},
               {'real': "2", 'imaginary': "0"}, {'real': "3", 'imaginary': "0"}, {'real': "-1", 'imaginary': "-3"}]

    options = {1: add_numbers_ui, 2: print_numbers_ui, 3: create_module_list, 4: check_real_numbers_longest_list}
    while True:
        print_menu_items()
        option = input("Your option: ")
        if option == "x":
            break
        try:
            option = int(option)
            options[option](numbers)
        except ValueError as ve:
            print("\n")
            print(ve)
            rint("\n")


if __name__ == '__main__':
    """
    The main function
    
    """

    run_menu()
