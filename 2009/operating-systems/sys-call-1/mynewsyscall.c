// Testing my new system calls

#include<stdio.h>
#include<sys/syscall.h>
 
int main()
{
	// Syscall 325 is the mynewsyscall_set function and takes one integer arguement
	// Syscall 326 is the mynewsyscall_get function and prints out the value of the variable

	// Example: Set the variable to 10 and then print it out
   syscall(325,10);
   syscall(326);
   
   syscall(325,134);
   syscall(326);
   
   syscall(325,4);
   syscall(326);
   
   syscall(325,67);
   syscall(326);
   
   syscall(325,245);
   syscall(326);
   return 0;
}
