#include "xshell.h"

int main(int argc, char * argv[])
{
	// Set the linked list to NULL and process count to -1
	list = NULL;
	process_count = -1;
	while(1)
	{
		// Get the command from the command line by the user
		printf("cmd%s","% \0");
		gets(temp);
		strcpy(line,temp);
		
		// Get the actual command and not the args
		char * cmd = strtok(temp," ");

		// If it is quit, exit the program
		if( strcmp(cmd,"quit") == 0 )
		{
			exit(0);
		}
		// If the command does not exist
		else if( !commandExists(cmd) )
		{
			printf("\nError: Unknown command '%s'\n\n",cmd);
			continue;
		}

		// Get the args count and the actual arguements
		free(args);
		count = getArgCount();
		args = getArgs();

		if( strcmp(cmd,"spawn") == 0 )
		{
			spawn();
		}
		else if( strcmp(cmd,"print") == 0 )
		{
			print();
		}
		else if( strcmp(cmd,"replay") == 0 )
		{
			replay();
		}
		else if( strcmp(cmd,"clear") == 0 )
		{
			system("clear || cls");
			continue;
		}
		printf("\n");
	}

	printf("\n");
	return 0;
}

// Function spawn for spawning new processes
void spawn()
{
	// Allocate memory for the program name, start and end times
	char program[80]; char start[80]; char end[80];
	time_t startTime; time_t endTime;

	// If there is no program specified
	if( strcmp(args[0],"") == 0 )
	{
		printf("\nNo program specified.\n");
		return;
	}

	// Set the program name to the name from the command line
	memset(program,'\0',80);
	strcpy(program,args[0]);

	// Make a copy of the args plus a space for a NULL
	char * args2[count+1];
	int status;

	// Copy the args from the command line
	for(i=0;i<count;i++)
	{
		args2[i] = (char *) malloc( sizeof(args[i]) );
		strcpy(args2[i],args[i]);
	}
	args2[count] = NULL;
	
	// Get the start time of the program
	startTime = time ( NULL );
	strcpy(start,ctime(&startTime));

	// Fork off a child process and run the program if we can
	int childID = fork();	
	if( childID == 0 )
	{
		execvp(args2[0],args2);
		printf("\nProgram %s could not be executed.\n",program);
		exit(1);
	}
	else
	{
		// Wait for the child to return before moving on
		wait(&status);
	}

	// If the program exists make a new Process
	if(status != 256)
	{
		endTime = time ( NULL );
		strcpy(end,ctime(&endTime));
		process_count++;

		// Calculate the elapsed time
		int elapsed = (int)difftime(endTime,startTime);

		// Initialize a new Process
		Process * p = (Process *)malloc(sizeof(Process));
		strcpy(p->name,program);
		p->PID = childID;
		strcpy(p->start,start);
		strcpy(p->end,end);
		p->elapsed = elapsed;
		p->status = status;
		p->nArgs = count;
		p->args = (char **)malloc(count * sizeof(char*));

		// Fill the process args
		for(i=0;i<count;i++)
		{
			p->args[i] = (char*)malloc(sizeof(args[i]));
			strcpy(p->args[i],args[i]);
		}

		// Get the process position and set the next to NULL, add the process to the list
		p->position = process_count;
		p->next = NULL;
		addProcess(p);
	}
	return;
}

// If the print command is issued
void print()
{
	// If the print command has no addition args, print all Processes information
	if( strcmp(args[0],"") == 0 )
	{
		printf("\nNo program specified, Printing All:\n");
		printList();
	}
	else
	{
		// Get the process by the name specified by the user
		Process * p = getProcessByName(args[0]);
		if(p == NULL)
		{
			printf("\nProgram %s not found.\n",args[0]);
			return;
		}
		
		// Print out the program information
		printf("\nProgram %s found:\n",args[0]);
		printInfo(p);
	}
	return;
}

// If the replay command is issued
void replay()
{
	// If no program is specified to replay, we replay them all
	if( strcmp(args[0],"") == 0 )
	{
		printf("\nNo position specified, replaying all:\n");
		replayAll();
		return;
	}

	// Get the position from the command line
	int pos = atoi(args[0]);
	Process * p = getProcessByPosition(pos);

	if( p == NULL )
	{
		printf("\nProgram not found at position %d\n",pos);
		return;
	}

	// Replay the process
	printf("\nRunning program %s found at position %d:\n",p->name,pos);
	replayProcess(p);
	return;
}

// Returns whether or not the command exists
int commandExists(char * cmd)
{
	return strcmp(cmd,"clear") == 0 || strcmp(cmd,"spawn") == 0 || strcmp(cmd,"print") == 0 || strcmp(cmd,"replay") == 0;
}

// Get the arguement count
int getArgCount()
{
	int n = 0;
	char temp2[80];
	strcpy(temp2,line);
	char * cmd = strtok(temp2," ");
	cmd = strtok(NULL," ");
	while( cmd )
	{
		n++;
		cmd = strtok(NULL," ");
	}
	return n;
}

// Get the actual arguements that are listed after the command in the xshell
char ** getArgs()
{
	char ** args2 = (char **) calloc(count,sizeof(char *));
	char temp2[80];
	strcpy(temp2,line);
	char * cmd = strtok(temp2," ");
	cmd = strtok(NULL," ");

	for(i = 0; i < count; i++)
	{
		args2[i] = malloc(strlen(cmd) + 1);
		strcpy(args2[i],cmd);
		cmd = strtok(NULL," ");
	}
	return args2;
}

// Print out the information on a given process
void printInfo(Process * p)
{
	printf("\nName: %s\nProgram %d\nPID: %d\nStart Time: %sEnd Time: %sTime Elapsed: %d\nReturn Status: %d\nArguments: [",p->name,p->position,p->PID,p->start,p->end,p->elapsed,p->status);
	
	// Print the arguements associated with the process
	for(i = 1; i < p-> nArgs ;i++)
	{
		if( i == p->nArgs - 1)
			printf("%s",p->args[i]);
		else
			printf("%s,",p->args[i]);
	}
	printf("]\n");
}

// Add the process to the linked list
void addProcess(Process * p)
{
	// If the list is null make the head the process
	if( list == NULL)
	{
		list = p;
	}
	else
	{
		// Iterate through the list to get to the end, and add in the process
		Process * curr = list;
		while(curr)
		{
			if( curr->next == NULL )
			{
				curr->next = p;
				break;
			}
			curr = curr->next;
		}
	}
}

// Print out information for the entire linked list
void printList()
{
	Process * curr = list;
	while(curr)
	{
		printInfo(curr);
		printf("\n======================================\n");
		curr = curr->next;
	}
}

// Gets the process by the program name
Process * getProcessByName(char* name)
{
	Process * p = NULL;
	Process * curr = list;
	while(curr)
	{
		// If the processes name matches the name given
		if( strcmp(name,curr->name) == 0 )
		{
			p = curr;
			break;
		}
		curr = curr->next;
	}
	return p;
}

// Gets the process by its position number
Process * getProcessByPosition(int pos)
{
	Process * p = NULL;
	Process * curr = list;
	while(curr)
	{
		// If the position matches the given position
		if( curr->position == pos )
		{
			p = curr;
			break;
		}
		curr = curr->next;
	}
	return p;
}

// Replay all process in the linked list
void replayAll()
{
	Process * curr = list;
	while(curr)
	{
		printf("\nRunning Program %s at position %d:\n\n",curr->name,curr->position);
		replayProcess(curr);
		curr = curr->next;
	}
}

// Replay the process
void replayProcess(Process * p)
{
	// Get the args and number of args from the process
	int nArgs = p->nArgs;
	char * tempArgs[nArgs+1];
	
	// Copy the args into another char * array so we can make the last spot in the array NULL, which is required by execvp
	for(i=0;i<nArgs;i++)
	{
		tempArgs[i] = (char*)malloc( sizeof( p->args[i] ) );
		strcpy(tempArgs[i],p->args[i]);
	}
	
	tempArgs[nArgs] = (char*)malloc(sizeof(char*));
	tempArgs[nArgs] = NULL;
	
	// Fork off a child process and execute the process
	if( fork() == 0)
	{
		execvp(p->name,tempArgs);
		printf("\nProgram %s could not be executed.\n",p->name);
		exit(1);
	}	
	wait(NULL);	
	return;
}
