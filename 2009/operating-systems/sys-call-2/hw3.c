// Testing

#include<stdio.h>
#include<string.h>
#include<linux/unistd.h>
#include<sys/syscall.h>

int main(int argc, char * argv[])
{
	// If there are lesss than 3 arguements, then print out the usage
	if( argc < 3 )
	{
		printf("\nUsage:");
		printf("\nhw3 -create filename content");
		printf("\nhw3 -get filename\n");
		return -1;
	}
	else
	{
		// If there are 4 args and the -create option is used
		if(argc == 4 && ! strcmp("-create",argv[1]) )
		{
			syscall(__NR_createFile,argv[2],argv[3]);
			return 0;
		}
		// If there are 3 args and the -get option is used
		else if(argc == 3 && !strcmp("-get",argv[1]) )
		{
			syscall(__NR_getFile, argv[2]);
			return 0;
		}
		else
		{
			// Else, print usage
			printf("\nUsage:");
			printf("\nhw3 -create filename content");
			printf("\nhw3 -get filename\n");
			return -1;
		}		
		
		return 0;
	}

	printf("\n");
	return 0;
}
