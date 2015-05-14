//Ashton Yon, Raymond Chan

#ifndef timeH
#define timeH

#include <iostream>

using namespace std;

class Time
{
  short hour;
  short minute;
public:
  friend bool checkAMPM(Time &t);
  friend bool checkMinute(Time &t);
  friend ostream& operator<< (ostream &os, const Time &t);
  friend istream& operator>> (istream & is, Time &t);
  bool operator< (const Time &t) const;
  void read();
}; // class Time
#endif
