//Ashton Yon, Raymond Chan

#ifndef yearH
#define yearH

#include "day.h"
#include "linkedlist.h"

class Year
{
  LinkedList *days;
  int count;
  
public:
  Year();
  //~Year();
  //void addDate(int month, int day);
  int findDate(int month, int day) const;
  Year& operator+= (const Day &d);
  Year& operator-= (const Day &d);
  void read();
  void searchDate(int month, int day) const;
  void searchSubject(const char *s) const;
};  // class Year
#endif
