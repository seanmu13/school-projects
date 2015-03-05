// Couldn't get the arguements part working so I just did the bare minimum

#include<stdio.h>
#include<unistd.h>
#include<stdlib.h>
#include<string.h>
#include<sys/types.h>
#include<sys/wait.h>
/*
int getCommandArgCount(char * command)
{
	int argCount = 0;
	
	char * tempString = (char*) malloc( strlen(command) + 1);
	strcpy(tempString,command);	
	
	char * tArg = strtok(tempString," ");

	while (tArg != NULL)
	{
		argCount++;
		tArg = strtok (NULL, " ");
	}
	
	free(tempString);
	return argCount;
}

char ** getCommandArgs(char * command, int count)
{
	char ** args = (char**) calloc( count+1,sizeof(char*) );
	char * tempString = (char*) malloc( strlen(command) + 1);
	strcpy(tempString,command);
	
	char * tArg = strtok(tempString," ");
	
	int i;
	for(i = 0; i < count; i++)
	{
		args[i] = (char*) malloc(strlen(tArg) + 1);
		strcpy(args[i],tArg);
		tArg = strtok(NULL," ");
	}
	args[count] = NULL;
	
	free(tempString);
	return args;	
}
*/
int main(int argc, char * argv[])
{
	// String to store the command
	char commandString[100];
	
	// Loop continuously until the 'exit' command is given
	while(1){
		// Get the command from the command line
		printf("\ncmd%s","% \0");
		//fgets(commandString,100,stdin);
		gets(commandString);

		// If the 'exit' command is given, exit the program
		if( strcmp(commandString,"exit") == 0 )
		{
			char * user = getlogin();
			printf("Goodbye %s!\n\n",user);
			exit(0);
		}
		/*
		// Get the actual command and ot the args
		int count = getCommandArgCount(commandString);		
		
		char ** myArgs = NULL;
		if( count > 1 )
		{
			myArgs = getCommandArgs(commandString,count);
		}		
*/
		// Fork off a child process and run the program if we can

		int childID = fork();
		int status;
		
		// If we are in the child process, execute the command or exit if the program cannot be run
		
		if( childID == 0 )
		{
			execvp(commandString,NULL);
			//execvp(commandString,myArgs);
			printf("\nProgram %s could not be executed.\n",commandString);
			exit(1);
		}
		else
		{
			// Wait for the child to return before moving on
			wait(&status);
			int exitValue = WEXITSTATUS (status);

			// If the exit status from the child program is non-zero, tell the user the exit value
			if( exitValue != 0)
			{
				printf("The child process has exited with a non-zero error code, EXIT VALUE=%d\n",exitValue);
			}
		}
	}
}
