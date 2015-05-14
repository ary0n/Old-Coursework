
#include <cstring>
#include <cstdlib>
#include <iostream>
#include <iomanip>
#include <cctype>
#include "time.h"

//Ashton Yon, Raymond Chan

using namespace std;


bool checkAMPM(Time &t)
{
  bool OK = true;
  char *ptr = strtok(NULL, " \t\n");

  if(ptr)
  {
    if(!ptr || (ptr[0] != 'A' && ptr[0] != 'P') || ptr[1] != 'M'
      || ptr[2] != '\0' || strtok(NULL, " \t\n"))
    {
      cout << "The AM/PM is invalid.\n";
      OK = false;
    } // if not AM or PM
    else // AM or PM
      if(ptr[0] == 'P' && t.hour < 12)
        t.hour += 12;
  } // if something after minute
  else // not something after minute
  {
    cout << "The AM/PM is invalid.\n";
    OK = false;
  } // else no AM/PM

  return OK;
} // checkAMPM()


bool checkMinute(Time &t)
{
  char *ptr = strtok(NULL, " \t\n");
  bool OK = true;

  if(!ptr || ptr[2] != '\0')
  {
   cout << "Your time is invalid.\n";
      OK = false;
  }
  else // after : is valid so far
  {
    t.minute = atoi(ptr);

    if(t.minute < 0 || t.minute > 59 || !isdigit(ptr[0]) || !isdigit(ptr[1]))
    {
      cout << "Minutes must be between 00 and 59.\n";
      OK = false;
    } // invalid minutes
  }

  return OK;
}  // checkMinute()


bool Time::operator< (const Time &t) const
{
  return (hour < t.hour) || (hour == t.hour && minute < t.minute);
} // operator<


ostream& operator<< (ostream &os, const Time &t)
{
  os << setw(2) << setfill('0') << right << t.hour << ":" << setw(2) << t.minute
    << " " << setfill(' ');

  return os;
} // operator<<()


istream& operator>> (istream & is, Time &t)
{
  char s[80];
  bool OK;

  do
  {
    OK = true;
    is.getline(s, 80);
    t.hour = atoi(strtok(s, ":"));

    if(t.hour < 1 || t.hour > 12)
    {
      cout << "Hour must be between 1 and 12.\n";
      OK = false;
    } // if hour error
    else  // valid hour
      if((OK = checkMinute(t)))
        OK = checkAMPM(t);

    if(!OK)
      cout << "Format of time is hh:mm PM or hh:mm AM.\n\nTime >> ";
  } while(OK == false);

  return is;
} // operator>>


void Time::read()
{
  hour = atoi(strtok(NULL, ":"));
  minute = atoi(strtok(NULL, ":"));
  char *ptr = strtok(NULL, ",");

  if(strchr(ptr, 'P') && hour != 12)
    hour += 12;
} // read()
