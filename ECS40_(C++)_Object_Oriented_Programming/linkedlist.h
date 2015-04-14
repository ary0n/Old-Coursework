//Ashton Yon, Raymond Chan

#ifndef linkedlistH
#define linkedlistH

#include <cstring>
#include <cstdlib>
#include <iostream>
#include <iomanip>
#include <cctype>
#include "day.h"

using namespace std;

class ListNode{
  Day day;
  ListNode *next; 
  ListNode(const Day &d); 
  friend class LinkedList;
};
  
  
class LinkedList{
    ListNode *head;
    public:
     LinkedList();
     ~LinkedList();
     Day& operator[](int index) const; //it is day
     Day& operator[](int index); //it is day
     LinkedList& operator+=(const Day &d); //corrected
     LinkedList& operator-=(const Day &d); //corrected
  };


#endif
