#include "mine.h"
char * make_dashes(int);
int find_and_replace(char,char*,char*);

int main(int argc,char*argv[])
{
	// Clear the command line to make it a little neater
	system("clear");
	printf("\nType 'quit' to exit the program when prompted for word or guess.");
	
	// Establish the default port and ip address as well as other variables
	int sockfd; int port = 9999; int player; int cont = 1; int wrong;
	char * ip = "127.0.0.1"; char * dashes; char buff[128]; char word[128]; 	

	// If there are 3 arguements, get the ip and port from the command line
	if(argc==3)
	{
		ip = argv[1];
		port = atoi(argv[2]);
	}
	fprintf(stderr,"\nClient Trying to Connect to %s on Port %d\n",ip,port);
	
	// Establish the socket
	struct sockaddr_in server = {AF_INET,port};
	server.sin_addr.s_addr = inet_addr(ip);
	
	// Try to make the socket
	if( ( sockfd = socket(AF_INET,SOCK_STREAM,0) ) == -1)
	{
		perror("socket call failed");
		exit(-1);
	}
	
	// Try to connect to the server
	if( connect(sockfd,(struct sockaddr *)&server,SIZE) == -1)
	{
		perror("connect call failed");
		exit(-1);
	}
	
	// Wait for the server to tell you the player number that you are
	printf("\nWaiting for another client...");
	recv(sockfd,&player,sizeof(int),0);	
	printf("\nYou are PLAYER %d",player);
	
	// This is the loop which has the actual hangman game in it
	do
	{
		// Set number of wrong guesses to 0 upon start of game
		wrong = 0;
		
		// If this is player 1, else it has to be player 2
		if(player == 1)
		{		
			// Prompt player to enter word, enter 'quit' to quit the game and tell the other player you quit as well, send the word to player 2
			printf("\nPlease enter a word: ");
			scanf("%s",word);
			send(sockfd,word,sizeof(word),0);
		}
		else
		{
			// Receive the hangman word from player 1
			recv(sockfd,word,sizeof(word),0);
			printf("\nPlayer 2 Received the Word!");
		}
		
		// If the word is quit, exit the game
		if( strcmp(word,"quit") == 0)
		{
			break;
		}
		
		// Make the dashes for the game
		dashes = make_dashes(strlen(word));
		
		// Start the current game
		do
		{
			printf("\nCurrent Word: %s",dashes);
		
			// If player 1 we are going to receive player 2's guess, if player 2, enter a guess
			if(player == 1)
			{
				recv(sockfd,buff,sizeof(buff),0);
			}
			else
			{
				printf("\nPlease enter a guess: ");
				scanf("%s",buff);
				send(sockfd,buff,sizeof(buff),0);
			}		
			
			// Check to see if the word is quit, even on a guess player2 can enter a whole word because we only check the first character when trying to match the word
			if( strcmp(buff,"quit") == 0)
			{
				cont = 0;
				break;
			}
			
			// See if the guess is in the word, if not, increase the number of wrong guesses by 1
			if( find_and_replace(buff[0],word,dashes) == 0 )
			{
				wrong++;
				printf("\nIncorrect guess, there are %d guesses remaining",7-wrong);
			}
			
		}while(wrong < 7 && strcmp(dashes,word) != 0);
		
		// The user hasn't quit
		if(cont != 0)
		{
			// Clear the screen and indicate the outcome, also we switch roles
			system("clear");
			if(wrong == 7) printf("\nPlayer 2 Lost, the word was: %s",word);
			else printf("\nPlayer 2 Won, the word was: %s",word);
			
			printf("\nPlayers now switching roles");
			
			// Switch the player numbers and send the switch command to the server
			// I made the command wacky so that there would be little to no chance that the user will type this in and accidentally send it to the server
			if(player == 1) player = 2;
			else
			{
				player = 1;			
				send(sockfd,"!@#$%^&",8,0);
			}
			printf("\nYou are now PLAYER %d",player);
		}
	}while(cont);
	
	// Somebody quit the game
	printf("\nA player has entered \"quit\" or somehow quit the game!");
	printf("\n");
}

// Makes the initial dashes variable based on the length of the word
char * make_dashes(int num)
{
  char* dashes = (char*)malloc(sizeof(char)*num+1);
  memset(dashes,'-',num);
  dashes[num] = '\0';
  return dashes;
}

// Try to find player 2's guess in the word and fix the dashes if a good guess
int find_and_replace(char guess, char* word, char* dashes)
{
	int len = strlen(word);
	int i = 0;
	int found = 0;
	for(i = 0; i < len; i++)
	{
		if(word[i] == guess)
		{
			dashes[i] = guess;
			found = 1;
		}
	}
	return found;
}
