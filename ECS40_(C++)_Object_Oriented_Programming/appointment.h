//Ashton Yon, Raymond Chan

#ifndef appointmentH
#define appointmentH

#include "time.h"

class Appointment
{
  Time startTime;
  Time endTime;
  char *subject;
  char *location;
public:
  Appointment();
  Appointment(const char* s);
  ~Appointment();
  friend ostream& operator<< (ostream &os, const Appointment &a);
  friend istream& operator>> (istream & is, Appointment &a);
  bool operator==(const Appointment &appt) const;
  Appointment& operator= (const Appointment &a);
  void read();
}; // class Appointment

#endif
