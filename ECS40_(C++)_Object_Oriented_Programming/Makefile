cal.out : calendar.o year.o day.o appointment.o time.o dayofweek.o linkedlist.o
	g++ -g -Wall -ansi -o cal.out calendar.o year.o day.o appointment.o time.o dayofweek.o linkedlist.o

calendar.o : calendar.cpp year.h
	g++ -g -Wall -ansi -c calendar.cpp

year.o : year.cpp year.h day.h linkedlist.h
	g++ -g -Wall -ansi -c year.cpp

linkedlist.o: linkedlist.cpp linkedlist.h day.h
	g++ -g -Wall -ansi -c linkedlist.cpp

day.o : day.cpp day.h appointment.h dayofweek.h
	g++ -g -Wall -ansi -c day.cpp

appointment.o : appointment.cpp appointment.h time.h
	g++ -g -Wall -ansi -c appointment.cpp

time.o : time.cpp time.h
	g++ -g -Wall -ansi -c time.cpp

dayofweek.o: dayofweek.cpp dayofweek.h
	g++ -g -Wall -ansi -c dayofweek.cpp

clean:
	rm -f cal.out core appointment.o calendar.o day.o dayofweek.o time.o year.o linkedlist.o 
