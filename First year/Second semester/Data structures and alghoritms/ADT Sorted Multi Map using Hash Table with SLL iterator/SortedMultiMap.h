#pragma once
//DO NOT INCLUDE SMMITERATOR

//DO NOT CHANGE THIS PART
#include <vector>
#include <utility>
typedef int TKey;
typedef int TValue;
typedef std::pair<TKey, TValue> TElem;
#define NULL_TVALUE -111111
#define NULL_TELEM pair<TKey, TValue>(-111111, -111111);
using namespace std;
class SMMIterator;
typedef bool(*Relation)(TKey, TKey);
typedef TValue(*Transformer)(TKey, TValue);

class SortedMultiMap {
	friend class SMMIterator;
    private:
        struct Node
        {
            TElem element;
            Node* next;

            Node();

            Node(TElem element, Node* next);
        };

        struct SLLElement
        {
            int hashPos;
            Node* currentNode;
            SLLElement* nextElement;

            SLLElement(int hashPos, Node* node, SLLElement* nextElement);
        };

        int sizeHashTable; // keeps track of the size of the hash table
        Node** hashTable; // the hash table
        int length; // the length of the sorted multi map
        double loadFactor; // the load factor

        SLLElement* iteratorSLL;

        Relation compare; // the comparison relation

        void resizeHash();

        int computeHash(TKey key);

    public:

    // constructor
    SortedMultiMap(Relation r);

	//adds a new key value pair to the sorted multi map
    void add(TKey c, TValue v);

	//returns the values belonging to a given key
    vector<TValue> search(TKey c);

	//removes a key value pair from the sorted multimap
	//returns true if the pair was removed (it was part of the multimap), false if nothing is removed
    bool remove(TKey c, TValue v);

    //returns the number of key-value pairs from the sorted multimap
    int size() const;

    //verifies if the sorted multi map is empty
    bool isEmpty() const;

    // returns an iterator for the sorted multimap. The iterator will returns the pairs as required by the relation (given to the constructor)	
    SMMIterator iterator() const;

    // replaces the value of every key with the result given by a function invoked on the pair.
    void replaceAll(Transformer t);

    // destructor
    ~SortedMultiMap();
};
