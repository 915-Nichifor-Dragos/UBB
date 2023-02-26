"""

@author: Nichifor Dragos

"""

from abc import ABC


class ShellSort(ABC):
    def __init__(self, structure, key, reverse):
        self.__structure = structure
        self.__key = key
        self.__reverse = reverse
        self._shell_sort()

    @property
    def key(self):
        return self.__key

    @key.setter
    def key(self, value):
        self.__key = value

    @property
    def reverse(self):
        return self.__reverse

    def _shell_sort(self):
        n = len(self.__structure)
        gap = n
        while gap > 0:
            gap = int(gap / 2)
            for i in range(gap, n):
                temp = self.__structure[i]
                j = i
                while j >= gap and self._in_order(self.__structure[j - gap], temp):
                    self.__structure[j] = self.__structure[j - gap]
                    j -= gap
                self.__structure[j] = temp

    def _in_order(self, e1, e2):
        if self.key is None:
            self.key = lambda x: x
        if self.key(e1) == self.key(e2):
            return False
        if self.reverse is False:
            if self.key(e1) > self.key(e2):
                return True
            else:
                return False
        if self.reverse is True:
            if self.key(e1) < self.key(e2):
                return True
            else:
                return False
