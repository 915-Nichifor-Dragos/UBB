"""

@author: Nichifor Dragos

"""
import unittest

from src.data_structure.data_structure import Container
from src.data_structure.filter import Filter
from src.data_structure.sorting import ShellSort
from src.domain.classes import Client


class TestStructures(unittest.TestCase):
    def setUp(self) -> None:
        self.__structure = []
        self.__implemented_structure = Container()

    def tearDown(self) -> None:
        pass

    def test_sorting(self):
        c1 = Client(123, "Andrei")
        c2 = Client(124, "Mathews")
        c3 = Client(125, "Richard")
        c4 = Client(126, "Markus")
        self.__structure.append(c3)
        self.__structure.append(c1)
        self.__structure.append(c2)
        self.__structure.append(c4)
        self.assertEqual(self.__structure, [c3, c1, c2, c4])

        # list = [c3, c1, c2, c4], we order by id
        # --> list should be [c1, c2, c3, c4] is reverse = False
        # --> list should be [c4, c3, c2, c1] is reverse = True

        k = ShellSort(self.__structure, key=lambda x: x.client_id, reverse=False)
        self.assertEqual(self.__structure, [c1, c2, c3, c4])
        k = ShellSort(self.__structure, key=lambda x: x.client_id, reverse=True)
        self.assertEqual(self.__structure, [c4, c3, c2, c1])

        self.__structure.clear()

        self.__structure.append(1)
        self.__structure.append(10)
        self.__structure.append(7)
        self.__structure.append(8)
        self.__structure.append(12)

        self.assertEqual(self.__structure, [1, 10, 7, 8, 12])

        k = ShellSort(self.__structure, key=None, reverse=False)
        self.assertEqual(self.__structure, [1, 7, 8, 10, 12])
        k = ShellSort(self.__structure, key=None, reverse=True)
        self.assertEqual(self.__structure, [12, 10, 8, 7, 1])

    def test_data_structure(self):
        self.assertEqual(len(self.__implemented_structure), 0)
        self.assertEqual(self.__implemented_structure.__str__(), [])
        self.__implemented_structure.append(1)
        self.__implemented_structure.append(2)
        self.__implemented_structure.append(3)
        self.__implemented_structure.append(4)
        self.assertEqual(len(self.__implemented_structure), 4)
        self.assertEqual(self.__implemented_structure.__str__(), [1, 2, 3, 4])
        self.__implemented_structure.pop()
        self.assertEqual(self.__implemented_structure.__str__(), [1, 2, 3])
        self.__implemented_structure.append(4)
        self.assertEqual(self.__implemented_structure.__str__(), [1, 2, 3, 4])
        self.__implemented_structure.remove(2)
        self.assertEqual(len(self.__implemented_structure), 3)
        self.assertEqual(self.__implemented_structure.__str__(), [1, 3, 4])
        k = self.__implemented_structure[2]
        k = 8
        self.__implemented_structure[2] = k
        self.assertEqual(self.__implemented_structure[2], 8)
        self.assertEqual(self.__implemented_structure.__str__(), [1, 3, 8])

    def test_filter(self):
        self.__implemented_structure.append(1)
        self.__implemented_structure.append(3)
        self.__implemented_structure.append(8)
        self.assertEqual(self.__implemented_structure.__str__(), [1, 3, 8])
        self.__implemented_structure.append(3)
        self.__implemented_structure.append(4)
        self.assertEqual(self.__implemented_structure.__str__(), [1, 3, 8, 3, 4])
        k = Filter(structure=self.__implemented_structure, key=lambda x: x <= 3)
        self.assertEqual(self.__implemented_structure.__str__(), [1, 3, 3])
