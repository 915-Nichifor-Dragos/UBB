"""

@author: Nichifor Dragos

"""
from dataclasses import dataclass


@dataclass
class Cell:
    """
    The dataclass in which we will represent each move made by human or computer
    """
    line: int
    column: int
    value: any
