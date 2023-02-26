"""

@author: Nichifor Dragos

"""
from abc import ABC


class Filter(ABC):
    def __init__(self, structure, key):
        self.__structure = structure
        self.__key = key
        self.filter()

    def filter(self):
        i = 0
        while i < len(self.__structure):
            if self._to_be_filtered(self.__structure[i]) is False:
                self.__structure.remove(self.__structure[i])
                i -= 1
            i += 1

    def _to_be_filtered(self, element):
        if self.__key(element) is True:
            return True
        return False
