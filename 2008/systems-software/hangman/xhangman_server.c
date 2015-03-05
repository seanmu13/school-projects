#include "mine.h"
int newsockfd;
void communicate(int client1, int client2);

int main(int argc,char*argv[])
{
	// Clear the screen and initialize variables
	system("clear");
	int sockfd; int port = 9999; int i = 0; int clients[2];
	
	// If there are 2 args, read the port from the command line
	if(argc>1) port = atoi(argv[1]);
	
	// Try to make the socket
	struct sockaddr_in server = {AF_INET,port,INADDR_ANY};	
	
	// Try to make the socket
	if( (sockfd = socket(AF_INET,SOCK_STREAM,0)) == -1 )
	{
		perror("socket call failed");
		exit(-1);
	}

	// Try to bind the socket
	if( bind(sockfd, (struct sockaddr *)&server, SIZE) == -1 )
	{
		perror("bind call failed");
		exit(-1);
	}

	// Try to listen on the socket
	fprintf(stderr,"Server Listening on Port %d\n",port);
	if( listen(sockfd,5) == -1)
	{
		perror("listen call failed");
		exit(-1);
	}
	
	// Infinitely loop
	while(1)
	{
		// Accept a client
		if( ( clients[i] = accept(sockfd,NULL,NULL ) ) == -1)
		{
			perror("accept call failed");
			continue;
		}
		
		// If the number is even, we must wait for another client, else we are going to start the game
		if(i==0)
		{
			i++;
			continue;
		}
		else
		{
			// Reset i and fork off a new process
			i = 0;			
			if(fork() == 0)
			{
				// Send the players their player numbers
				int player = 1;
				send(clients[0],&player,sizeof(int),0);
				player++;
				send(clients[1],&player,sizeof(int),0);
				communicate(clients[0],clients[1]);
			}
		}
	}
}

// This is only called by the child process and establishes the connection between players 1 and 2
void communicate(int client1, int client2)
{
	// We use this as the string to send and receive data
	char buff[128];
	
	// Receive the word from player 1 and send it to player 2
	recv(client1,buff,sizeof(buff),0);
	send(client2,buff,sizeof(buff),0);
	
	// While we don't receive the quit signal
	while( strcmp(buff,"quit") != 0 )
	{
		// Receive info from player 2
		recv(client2,buff,sizeof(buff),0);
		
		// If the switch signal is here
		if( strcmp(buff,"!@#$%^&") == 0 )
		{
			// Switch the clients around and wait for the new player 1 to send the hangman word
			fprintf(stderr,"Players are now switching\n");
			int temp = client1;
			client1 = client2;
			client2 = temp;	
			recv(client1,buff,sizeof(buff),0);
			send(client2,buff,sizeof(buff),0);
			continue;
		}
		
		// Send the date to the client
		send(client1,buff,sizeof(buff),0);
	}
	
	// This game is over and the child process will die
	printf("Received \"quit\" from a player\n");
	exit(0);
}
