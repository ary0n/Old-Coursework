//Ashton Yon, Raymond Chan

#ifndef dayofweekH
#define dayofweekH

#include <iostream>

using namespace std;

class DayOfWeek
{
  char monthName[10];
  int month;
  int day;
  char dayName[10];
public:
  DayOfWeek(int mo, int d);
  friend ostream& operator<< (ostream &os, const DayOfWeek &d);
  void read();
}; // class DayOfWeek
//---------------------------------------------------------------------------
#endif

