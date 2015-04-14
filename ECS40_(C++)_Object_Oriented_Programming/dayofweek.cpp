
#include <fstream>
#include <iostream>
#include <cstring>
#include "dayofweek.h"

//Ashton Yon, Raymond Chan

using namespace std;

DayOfWeek::DayOfWeek(int mo, int d): month(mo), day(d)
{
} // DayOfWeek()


ostream& operator<< (ostream &os, const DayOfWeek &d)
{
  int len = strlen(d.dayName) + strlen(d.monthName);

  if (d.day > 9)
    len++;

  os << d.dayName << ", " <<  d.monthName << " " << d.day;

  for(int i = len; i < 27; i++)
    os << " ";

  return os;
} // operator<<


void DayOfWeek::read()
{
  ifstream inf("DOW.dat", ios::binary);
  inf.seekg(sizeof(DayOfWeek) * ((month - 1) * 31 + day - 1));
  inf.read((char*) this, sizeof(DayOfWeek));
} // read()






