all: createtables queries

createtables: insert.o
	g++ -Wall -g -o createtables insert.o -lpqxx -lpq

queries: queries.o
	g++ -Wall -g -o queries queries.o -lpqxx -lpq

insert.o: insert.cpp
	g++ -Wall -g -c insert.cpp -lpqxx -lpq

queries.o: queries.cpp
	g++ -Wall -g -c queries.cpp -lpqxx -lpq

clean:
	rm -f createtables queries insert.o queries.o