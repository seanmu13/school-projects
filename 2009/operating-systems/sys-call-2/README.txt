---- How I changed Linux source to make 2 new system calls

	hw3.c			- Calls my two new system calls: createFile and getFile
					- Compiled using command: gcc -m32 -o hw3 -I /u/OSLab/smm78/linux-2.6.23.1/include/ hw3.c
					- Transfered to QEMU using scp command
					- Usage: ./hw3 -create filename filecontent
							 ./hw3 -get filename

	sys.c			- Contains the implementation for createFile and getFile
					- Changes are at the bottom of the file
					- Found at linux-2.6.23.1/kernel/sys.c
					- I could not get my linked list to work so I decided just to use an array of structs.
					- The maximum number of files you can 'create' is 100 because that is what I have hardcoded in
					
					
	unistd.h		- createFile is at #325 and getFile # 326
					- Changes made at the bottom of the system call list
					- Found at linux-2.6.23.1/include/asm-i386/unistd.h
					
	syscall_table.S	- Added definitions for createFile and getFile at the end of the file
					- Found at linux-2.6.23.1/arch/i386/kernel/syscall_table.S				
					
---- Overall procedure how I changed and tested the modified Linux Source

1. Changed the sys.c, unistd.h, and syscall_table.S files
2. Copied the /u/OSLab/original/.config file to the linux-2.6.23.1 directory
3. Built the linux kernel using: make ARCH=i386 bzImage
4. In QEMU, used the scp command to get the linux-2.6.23.1/arch/i386/boot/bzImage and linux-2.6.23.1/System.map files
5. In QEMU, copied bzImage to /boot/bzImage-devel and copied System.map to /boot/System.map-devel
6. Used the lilo command to update the bootloader
7. Reboot
8. Launch Linux in development mode in QEMU
9. Ran my hw3 program using ./hw3 command