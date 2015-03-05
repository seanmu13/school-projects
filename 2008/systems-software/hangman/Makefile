FLAGS=-lnsl -lsocket -lresolv
OBJ_FILES= xhangman_server.o xhangman_client.o
all:$(OBJ_FILES)
	gcc -o xhangman_server $(FLAGS) xhangman_server.o
	gcc -o xhangman_client $(FLAGS) xhangman_client.o
xhangman_server.o: xhangman_server.c
	gcc -c xhangman_server.c
xhangman_client.o: xhangman_client.c
	gcc -c xhangman_client.c

