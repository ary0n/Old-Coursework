#include <fstream>
#include <iostream>
#include <cstdlib>
#include <cstring>
#include "year.h"
#include "day.h"
#include "linkedlist.h"

//Ashton Yon, Raymond Chan

using namespace std;


Year::Year():count(0)
{
  days = new LinkedList();
}  // Year()


/*
Year::~Year()
{
  delete [] days;
}  // ~Year()
*/


/*
void Year::addDate(int month, int day)
{
  int i;

  if(count + 1 >= size)
  {
    Day *temp =  new Day[size * 2];

    for(int i = 0; i < size; i++)
      temp[i] = days[i];

    delete [] days;
    days = temp;
    size *= 2;
  } // if need to resize

  for(i = 0; i < count; i++)
    if(days[i].compareDate(month, day) > 0)
      break;

  for(int j = count - 1; j >= i; --j)
    days[j + 1] = days[j];  // roll entries toward end
  days[i].reset(month, day);
  count++;
} // addDate()
*/

Year& Year:: operator -= (const Day& d)
{
    int pos = findDate(d.getMonth(), d.getDay());
	  if(pos!=count)
	    count--;
	
	
	(*days)-=d;
  
    
  return *this;
}

int Year::findDate(int month, int day) const
{
  for(int i = 0; i < count; i++)
    if((*days)[i].compareDate(month, day) == 0)
      return i;

  return count; // not found
} // findDate()



Year& Year::operator+= (const Day &day)
{
  //cout << "year+= called" << endl;
  int pos = findDate(day.getMonth(), day.getDay());

  if(pos == count)
  {
    //addDate(day.getMonth(), day.getDay());
    (*days)+=day;
    count++;
    //pos = findDate(day.getMonth(), day.getDay());
  } // if date not found

  else if(pos!=count)
  //days[pos] += day; werid error didn't compare
  ((*days)[pos])+=day; 

  return *this;
} // operator+=


void Year::read()
{
  char s[256];
  int month, day;

  ifstream inf("appts.csv");
  inf.getline(s, 256); // eat titles.
  while(inf.getline(s, 256))
  {
    month = atoi(strtok(s,"/"));
    day = atoi(strtok(NULL,"/"));
    strtok(NULL, ","); // read through 2003

    int pos = findDate(month, day);
    
    Day * newday = new Day(month,day);
     newday->read();//finder pointer to just call appointment.read()
   
    
    if(pos == count)
    {
      // ListNode * n = new ListNode();
      //ListNode *newNode = new ListNode(newday,n);
      //addDate(month, day);
      (*days)+=(*newday); // uses linked list +=
      count++;     
      //pos = findDate(month, day);
    } // if date not found

	else if (pos!= count)
    ((*days)[pos])+=(*newday); // uses the day += not linked list +=

  } // while more to read

  inf.close();
} // read()


void Year::searchDate(int month, int day) const
{

  bool found = false;

  for(int i = 0; i < count; i++)
    if((*days)[i].compareDate(month, day) == 0)
    {
      cout << "Start End   Subject         Location\n";
      (*days)[i].printAppts();
      found = true;
      break;
    }  // if found a matching date

  if(!found)
    cout << "There are no appointments for that date.\n";

  cout << endl;
} // searchDate()


void Year::searchSubject(const char *s) const
{
  bool found = false;

  for(int i = 0; i < count; i++)
    (*days)[i].printSubject(s, found);

  if(!found)
    cout << s << " was not found as a subject in the calendar.\n";

  cout << endl;
} // searchSubject()

