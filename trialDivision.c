#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <unistd.h>

#define sched_setweight 380


void trialDivision(int n) {
	int i = 2;
	while (n > 1) {
		if (n%i==0) {
			//printf("%d ",i);
			n = n/i;
		}
		else {
			i++;
		}
	}
	//printf("\n");
}

int main(int argc, char **argv) {
	for (int weight=1; weight<=20; weight++) {
		syscall(sched_setweight, getpid(), weight);
		
		clock_t begin = clock();
		int n = atoi(argv[1]);
		for (int i=2; i<=n; i++) {
			//printf("n = %d\n", i);
			trialDivision(i);
			//printf("\n");
		}
		clock_t end = clock();
		double time_spent = (double)(end-begin)/CLOCKS_PER_SEC;
		printf("Execution time with n=%d: %f\n", n, time_spent);
	}
}