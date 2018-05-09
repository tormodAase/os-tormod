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
	/*gnuplot*/
	FILE *gnuplot = popen("gnuplot -persist", "w");
	fprintf(gnuplot, "set style line 1 lc rgb '#0060ad' lt 1 lw 2 pt 7 ps 1.5\n");
	fprintf(gnuplot, "plot '-' with linespoints ls 1\n");

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
		
		fprintf(gnuplot, "%d %g\n", weight, time_spent); //gnuplot
		
		printf("Execution time with n=%d: %f\n", n, time_spent);
	}
	
	/*gnuplot*/
	fprintf(gnuplot, "e\n");
	fflush(gnuplot);
}
