#include<stdio.h>
#include<string.h>
#include<stdlib.h>

void encode(char *, char *);
void decode(char *, char *);
unsigned char rotate(unsigned char, int);
void printInfo(char *, char *, int, int, char *);
char * getOtherFileName(char *, const char *);

int main(int argc, char *argv[])
{
	int i;
	int mode = 2;
	int numFiles = 0;
	char * keyName;
	char * txtName;

	// -------------------------------------------------------------------------------
	// Cycles through the command line arguements and detects the different file types
	// -------------------------------------------------------------------------------

	for(i = 1; i < argc; i++)
	{
		if( strcmp(argv[i],"-e") == 0 )
		{
			mode = 0;
		}
		else if( strcmp(argv[i],"-d") == 0 )
		{
			mode = 1;
		}
		else if( strstr(argv[i],".key") != NULL )
		{
			keyName = argv[i];
			numFiles++;
		}
		else if( strstr(argv[i],".txt") != NULL || strstr(argv[i],".enc") != NULL )
		{
			txtName = argv[i];
			numFiles++;
		}
	}

	// ----------------------------------------------------------------------------------------------------
	// If decode/encode is not set or 2 of the necessary files were not specified, prints out error message
	// ----------------------------------------------------------------------------------------------------

	if( mode == 2 || numFiles != 2)
	{
		printf("\nIncorrect arguments ! Please provide the following:\n");
		printf("\n\t1. -e or -d (encode or decode)");
		printf("\n\t2. plainTextFileName.txt");
		printf("\n\t3. keyFileName.key");
		printf("\n\n");
		return -1;
	}
	else if( mode == 0)
	{
		encode(keyName,txtName);
	}
	else
	{
		decode(keyName,txtName);
	}

	printf("\n\n");
	return 0;
}

// ---------------------
// Encodes the text file
// ---------------------

void encode(char * keyName, char * txtName)
{
	int keyLength, rotation;
	char key[257];
	FILE * keyFile;
	FILE * txtFile;
	FILE * encFile;

	printf("\nEncoding Mode:\n");

	// ----------------------
	// Tries to open key file
	// ----------------------

	if( (keyFile = fopen(keyName,"r")) == NULL)
	{
		printf("\nCould not open key file: %s, exiting...",keyName);
		close(keyFile);
		return;
	}

	// -----------------------------------------------------
	// Reads in key length, rotation, and key from .key file
	// -----------------------------------------------------

	fscanf(keyFile,"%d%d%s",&keyLength,&rotation,&key);
	printInfo(keyName,txtName,keyLength,rotation,key);
	close(keyFile);

	// ----------------------------------------------
	// Creates enc file string with .enc and not .txt
	// ----------------------------------------------

	char temp1[strlen(txtName)+1];
	strcpy(temp1,txtName);
	char * encName = strtok(temp1,".");
	encName = strcat(encName,".enc");

	// ----------------------
	// Tries to open txt file
	// ----------------------

	if( (txtFile = fopen(txtName,"r") ) == NULL)
	{
		printf("\nCould not open text file: %s, exiting...",txtName);
		close(txtFile);
		return;
	}

	// ----------------------------------
	// Tries to open enc file for writing
	// ----------------------------------

	if( (encFile = fopen(encName,"w") ) == NULL)
	{
		printf("\nCould not open encoded file: %s, exiting...",encName);
		close(encFile);
		return;
	}

	int index = 0;
	unsigned char c;
	int k = 0;

	// ----------------------------------------------------------------------------------------
	// Reads chars from text files, then rotates and shifts them, then writes char to .enc file
	// ----------------------------------------------------------------------------------------

	while( ( c = fgetc(txtFile) ) != ( (unsigned char) EOF) )
	{
		c = rotate( (c + key[index])%128 , rotation);
		index = (index + 1) % keyLength;
		fputc(c,encFile);
	}

	// -----------------------
	// Closes txt and enc file
	// -----------------------

	close(txtFile);
	close(encFile);

	printf("\n\nFinished writing to file: %s",encName);

	return;
}

// --------------------
// Decodes the enc file
// --------------------

void decode(char * keyName, char * encName)
{
	int keyLength, rotation;
	char key[257];
	FILE * keyFile;
	FILE * encFile;
	FILE * txtFile;

	printf("\nDecoding Mode:\n");

	// ----------------------
	// Tries to open key file
	// ----------------------

	if( (keyFile = fopen(keyName,"r")) == NULL)
	{
		printf("\nCould not open key file: %s, exiting...",keyName);
		close(keyFile);
		return;
	}

	// -----------------------------------------------------
	// Reads in key length, rotation, and key from .key file
	// -----------------------------------------------------

	fscanf(keyFile,"%d%d%s",&keyLength,&rotation,&key);
	printInfo(keyName,encName,keyLength,rotation,key);
	close(keyFile);

	// ----------------------------------------------
	// Creates enc file string with .txt and not .enc
	// ----------------------------------------------

	char temp1[strlen(encName)+1];
	strcpy(temp1,encName);
	char * txtName = strtok(temp1,".");
	txtName = strcat(txtName,".txt");

	// ----------------------
	// Tries to open enc file
	// ----------------------

	if( (encFile = fopen(encName,"r") ) == NULL)
	{
		printf("\nCould not open text file: %s, exiting...",encName);
		close(txtFile);
		return;
	}

	// ----------------------------------
	// Tries to open txt file for writing
	// ----------------------------------

	if( (txtFile = fopen(txtName,"w") ) == NULL)
	{
		printf("\nCould not open encoded file: %s, exiting...",txtName);
		close(encFile);
		return;
	}

	int index = 0;
	unsigned char c;
	int k = 0;
	int shift = 0,tempi = 0;

	// --------------------------------------------------------------------------------------------------------------------------
	// Reads chars from enc file, then reverse rotates and shifts them back to their original char, then writes char to .txt file
	// --------------------------------------------------------------------------------------------------------------------------

	while( ( c = fgetc(encFile) ) != ( (unsigned char) EOF) )
	{
		c = rotate(c,-rotation);
		shift = c - key[index];

		if( shift < 0 )
		{
			c = 128 + shift;
		}
		else
		{
			c = shift;
		}

		index = (index + 1) % keyLength;
		fputc(c,txtFile);
	}

	// -----------------------
	// Closes txt and enc file
	// -----------------------

	close(encFile);
	close(txtFile);

	printf("\n\nFinished writing to file: %s",txtName);

	return;
}

// ----------------------------------------------------
// Rotates the bits of the char by the amount specified
// ----------------------------------------------------

unsigned char rotate(unsigned char in, int shift)
{
	unsigned char out;

	// ----------------------------------------
	// Shifts the bits of the char to the right
	// ----------------------------------------

	if(shift > 0)
	{
		out = in >> (shift % 8);
        out |= in << (8 - shift % 8);
	}

	// ----------------------------------------
	// Shifts the bits of the char to the left
	// ----------------------------------------

	else if(shift < 0)
	{
		shift = -shift;
		out = in << (shift % 8);
        out |= in >> (8 - shift % 8);
	}

	// -------------------------------------
	// No shift performed for 0 bit rotation
	// -------------------------------------

	else
	{
		out = in;
	}

	return out;
}

// -------------------------------------------------
// Prints out information about the key, files, etc.
// -------------------------------------------------

void printInfo(char * keyName, char * txtName, int keyLength, int rotation, char * key)
{
	printf("\nKey File: %s",keyName);
	printf("\nText/Enc File: %s\n",txtName);
	printf("\nKey Length: %3d",keyLength);
	printf("\nRotation: %5d",rotation);
	printf("\nKey: %s",key);
}
