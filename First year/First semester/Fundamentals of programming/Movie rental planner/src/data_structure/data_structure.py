"""

@author: Nichifor Dragos

"""
from src.domain.classes import Client


class Container:
    def __init__(self):
        self.container = []
        self.current = 0

    def append(self, obj):
        self.container.append(obj)

    def pop(self):
        self.container.pop(len(self.container) - 1)

    def remove(self, obj):
        for element in self.container:
            if element == obj:
                self.container.remove(obj)

    def __getitem__(self, key):
        return self.container[key]

    def __setitem__(self, key, value):
        if value is not None:
            self.container[key] = value

    def __len__(self):
        return len(self.container)

    def __str__(self):
        return self.container

    def __iter__(self):
        self.current = 0
        return iter(self.container)
