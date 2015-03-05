#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<time.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/wait.h>

typedef struct process Process;
struct process{
	char name[80];
	int PID;
	char start[80];
	char end[80];
	int elapsed;
	int status;
	int nArgs;
	char ** args;
	int position;
	Process * next;
};

int commandExists(char *);
void spawn();
void print();
void replay();
int getArgCount();
char ** getArgs();
void printInfo(Process *);
void addProcess(Process *);
void printList();
Process * getProcessByName(char*);
Process * getProcessByPosition(int);
void replayAll();
void replayProcess(Process *);

char line[80];
char temp[80];
int count, i;
char ** args;
Process * list;
static int process_count;
