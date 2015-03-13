/* Robert Lyons
 * rglyons
 * cmps012m
 * 12/9/14
 * main.c
 * Hasg table implementation
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#define BUFFERSIZE 1024
#define HASHMAX 65536
#define COUNTMAX 5

typedef int hash_t;

hash_t hash(unsigned char *key) {
    unsigned int wordVal;
    wordVal = 0;
    for(; *key != '\0'; key++)
	wordVal = *key + (wordVal << 5) - wordVal;
    return wordVal % 65536;
}

int count[HASHMAX];
int countcount[COUNTMAX];

int rev_compare_int(const void *a, const void *b) {
    return (*(int *)b - *(int *)a);
}

int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Requires word file\n");
        return EXIT_FAILURE;
    }
    FILE *fp = fopen(argv[1], "rt");
    if (!fp) {
        printf("Error opening file %s\n", argv[1]);
        return EXIT_FAILURE;
    }
    char buffer[BUFFERSIZE];
    int lines = 0;
    for(int i = 0; i < HASHMAX; i++) {
        count[i] = 0;
    }
    for(int i = 0; i < COUNTMAX; i++) {
        countcount[i] = 0;
    }
    while (1) {
        memset(buffer, 0, BUFFERSIZE);
        if (fgets(buffer, BUFFERSIZE, fp) == NULL) {
            break;
        }
        lines++;
        buffer[strlen(buffer) - 1] = 0; // remove newline
        hash_t h = hash((unsigned char *)buffer);
        if (h < 0 || h >= HASHMAX) {
            printf("Illegal hash value %d for '%s'\n", h, buffer);
            return EXIT_FAILURE;
        }
        count[h]++;
    }
    printf("%d lines read\n", lines);

    // Sort counts
    qsort(count, HASHMAX, sizeof(int), rev_compare_int);

    // Show collision statistics
    int collisions = 0;
    double score = 0;
    for(int i = 0; i < HASHMAX; i++) {
        int j;
        int cnt = 0;
        for(j = i; count[j] == count[i] && j < HASHMAX; j++) {
            cnt++;
        }
        printf("%d bucket(s) with %d entries\n", cnt, count[i]);
        if (count[i] > 1) {
            collisions += cnt * (count[i] - 1);
            score += cnt * (double)(count[i] - 1) * (double)(count[i] - 1);
        }
        i = j;
    }
    printf("\n--------------------\nTotal collisions: %d\n", collisions);
    printf("SCORE = %g (lower is better)\n", score);
    return EXIT_SUCCESS;
}
