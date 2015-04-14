#include <cstring>
#include <cstdlib>
#include <iostream>
#include <iomanip>
#include <cctype>
#include "linkedlist.h"
#include "day.h"

//Ashton Yon, Raymond Chan

using namespace std;

ListNode::ListNode(const Day &d) //changed
{ 
  Day *day2 = new Day(d);
  day = (*day2);
  next = NULL;
  
}//ListNode constructor

/* /// day.day? ::Day? ::ListNode?
ListNode::Day(const Day& otherDay)
{
  day = otherDay.day;
  month = otherDay.month;
  apptCount = otherDay.apptCount;

  for(int i = 0; i < apptCount; i++)
  {
    appts[i] = new Appointment();
    *appts[i] = *otherDay.appts[i];
  }  // for each appointment

} // day copy constructor
*/

LinkedList::LinkedList()
{
  head = NULL;

}

LinkedList::~LinkedList()
{
  //loop to deleting and deleting head
  ListNode * ptr1, *ptr2;
  
  ptr1=head;
  while(ptr1 != NULL)
  {
    ptr2=ptr1->next;  
    delete ptr1;
	ptr1=ptr2;
  }
}

LinkedList& LinkedList::operator-=(const Day&d)
{
  ListNode* ptr1, *ptr2=NULL;

  ptr1=head; 
  if(head->day==d)
  {
    ptr1=head->next;
	delete head;
	head=ptr1;
  } // head is the node to delete
  else
  {
  while( ptr1!=NULL && !(ptr1->day==d))
  {
    ptr2=ptr1;
	ptr1=ptr1->next;
  }
  
  if(ptr1)
  {
    ptr2->next=ptr1->next;
	delete ptr1;
   } // ptr1 is not null which means it matched a day
  else
  {
    int j=d.getMonth();
	int k= d.getDay();
    cout << j << "/" << k << " not found.\n\n" ;
   } // ptr1 is null so it didn't match a day
   } // head isn't one to delete
   
   return *this;
}

LinkedList& LinkedList::operator+=(const Day& d) //changed
{

/*
 *two pointers ListNode *ptr, *ptr2 = NULL;
 *write a loop to get the last ptr->day which is less than d and use ptr2 to poin tto that node

if(ptr2)
	add the new node to ptr->next
else
//should be the first node (last??)
//head = new ListNode(day,ptr); //THE KEY LINE!!!########
//return *this;
 */
  ListNode* ptr1, *ptr2=NULL;
 
      ptr1=head;
  
   if(!head) // if head is empty
    {
      head= new ListNode(d);
    }
   else
     {
  while(ptr1!=NULL && ptr1->day < d)
    {
	  ptr2=ptr1;
	  ptr1=ptr1->next;
    }//while
	
     if(ptr2)
	 {
	   ptr2->next = new ListNode(d);
	   ptr2->next->next=ptr1;
         }
     else // if the day goes before the current first node
	 {
	   head =new ListNode(d);
	   ptr2 = head;
           ptr2->next=ptr1;
	 }
     } // head is not null
  return *this;

}

Day& LinkedList::operator[](int index) const
{
  ListNode *ptr;
  ptr=head;
  for(int i=0;i < index ; i++)
    {
      ptr=ptr->next;
      if (ptr==NULL)
        return ptr->day;
    }
  
  return ptr->day;

}

Day& LinkedList::operator [](int index)
{
  ListNode *ptr;
  ptr=head;
  for(int i=0;i < index ; i++)
    {
      ptr=ptr->next;
      if (ptr==NULL)
        return ptr->day;
    }
  
  return ptr->day;

}
