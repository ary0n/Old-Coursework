#include <cstring>
#include <iostream>
#include <iomanip>
#include "appointment.h"

//Ashton Yon, Raymond Chan

using namespace std;

Appointment::Appointment() : subject(NULL) , location(NULL)
{
}  // Appointment()


Appointment::Appointment(const char* s) : location(NULL)
{
  subject = new char[strlen(s) + 1];
  strcpy(subject, s);
}  // Appointment()


Appointment::~Appointment()
{
  if(subject)
    delete [] subject;

  if(location)
    delete [] location;
} // ~Appointment()


bool Appointment::operator==(const Appointment &appt) const
{
  return strcmp(subject, appt.subject) == 0;
}  // operator==()


ostream& operator<< (ostream &os, const Appointment &a)
{
  os << a.startTime;
  os << a.endTime;
  os << setw(15) << left << a.subject << " " << a.location << endl;
  return os;
} // operator<<()


Appointment& Appointment::operator= (const Appointment &a)
{
  if(this == &a)
    return *this;

  if(subject)
    delete [] subject;

  if(location)
    delete [] location;

  startTime = a.startTime;
  endTime = a.endTime;
  subject = new char[strlen(a.subject) + 1];
  strcpy(subject, a.subject);
  location = new char[strlen(a.location) + 1];
  strcpy(location, a.location);
  return *this;
} // operator=


istream& operator>> (istream & is, Appointment &a)
{
  char s[80];
  cout << "Subject >> ";
  is.getline(s,80);
  a.subject = new char[strlen(s) + 1];
  strcpy(a.subject, s);
  cout << "Location >> ";
  is.getline(s,80);
  a.location = new char[strlen(s) + 1];
  strcpy(a.location, s);

  do
  {
    cout << "Start time >> ";
    is >> a.startTime;
    cout << "End time >> ";
    is >> a.endTime;
    if(a.endTime < a.startTime)
      cout << "Start time is later than end time!\n";
  } while (a.endTime < a.startTime);

  return is;
} // operator>>


void Appointment::read()
{
  char *ptr;
  ptr = strtok(NULL, ",");
  subject = new char[strlen(ptr) + 1];
  strcpy(subject, ptr);
  startTime.read();
  endTime.read();
  ptr = strtok(NULL, ",");
  location = new char[strlen(ptr) + 1];
  strcpy(location, ptr);
} // read()
