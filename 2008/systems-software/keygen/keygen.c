#include<stdio.h>

void printArgsHelp();

int main(int argc, char *argv[])
{
	char c;
	char * filename;
	char ext[5] = ".key";
	int keyLength, rotation, i;
	FILE * keyFile;

	// ------------------------------------------------------------
	// If there are less than 3 arguments, prints out error message
	// ------------------------------------------------------------
	
	if( argc < 3 )
	{
		printArgsHelp();
		return -1;
	}

	// ------------------------------------------------------
	// Read in keyLength, rotation, and filename command line
	// ------------------------------------------------------
	
	keyLength = atoi(argv[1]);
	rotation = atoi(argv[2]);
	filename = argv[3];
	strcat(filename, ".key");

	// ------------------------------------------
	// Error checking for key length and rotation
	// ------------------------------------------
	
	if( keyLength < 1 || keyLength > 256 || rotation < -8 || rotation > 8)
	{
		printArgsHelp();
		return -1;
	}

	printf("\nKey Length: %d",keyLength);
	printf("\nRotation Amount: %d",rotation);
	printf("\nFilename: %s",filename);

	// ----------------------------------------------------------
	// Create a key array that is length of key + 1 for null char
	// ----------------------------------------------------------
	
	char key[keyLength+1];
	key[keyLength] = '\0';

	// -----------------------
	// Seeds the rand function
	// -----------------------
	
	srand(time(NULL));

	// ----------------------------------------------
	// Generate the key randomly with capital letters 
	// ----------------------------------------------
	
	for(i=0; i < keyLength; i++)
	{
		c = rand() % 26 + 65;
		key[i] = c;
	}

	// -------------------------------------------------
	// Writes key length, rotation, and key to .key file
	// -------------------------------------------------
	
	printf("\n\nKey: \n\n%s",key);
	keyFile = fopen(filename,"w");

	if( keyFile == NULL )
	{
		printf("\nCould not open file for writing.");
		close(keyFile);
		return -1;
	}

	fprintf(keyFile,"%d %d\n\n%s",keyLength,rotation,key);

	close(keyFile);

	printf("\n\n");
	return 0;
}

// ----------------------------
// Prints out usage information
// ----------------------------

void printArgsHelp()
{
	printf("\nIncorrect arguments ! Please provide the following:\n");
	printf("\n\t1. Key Length (1 to 256)");
	printf("\n\t2. Rotation Amount (-8 to 8)");
	printf("\n\t3. File Name (to store the key)");
	printf("\n\tExample: ./keygen 128 4 mykey");
	printf("\n\n");
}
