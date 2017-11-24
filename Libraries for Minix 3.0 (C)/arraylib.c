/*
* 160467709
 */
#include <errno.h>
#include <limits.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "arraylib.h"

/* error message format for fatalerror */
static const char *ERR_FMT = "%s:%d - %s, errno %d: %s\n";
array *newarray(int len) {
	if (len<1) {
		errno = EINVAL;
		return NULL;
	}
	array *arr = malloc(sizeof(array));
	arr->ai = calloc(len,sizeof(int));
	if (arr && arr->ai){
		arr->len = len;
		// if array and array field inside was allocated, assign lenght
	}
	else {
		// else free memory
		if (arr)
			free(arr);
		if (arr->ai)
			free(arr->ai);
		errno = ENOMEM;
		return NULL;
	}

    return arr;
}

/* see get specification comments in arraylib.h */
int get(array *arr, int idx) {
	if (!arr || !(arr->ai) || idx<0 || idx>=arr->len){
		errno = EINVAL;
    	return INT_MIN;
	}
	int value = *((arr->ai)+idx);
	return value;
}
                
/* see set specification comments in arraylib.h */
void set(array *arr, int idx, int value) {
	if (!arr || !(arr->ai) || idx<0 || idx>=arr->len){
		errno = EINVAL;
		return;
	}
	*((arr->ai)+idx) = value;
}

/* see foreach specification comments in arraylib.h */
void foreach(array *arr, applyfunction applyf) {
	if (!arr || !(arr->ai) || !applyf || arr->len<1)
		return;
	for (int i =0; i<(arr->len); i++){
		*(arr->ai+i) = applyf(arr, i);
	}
}
/* see print specification comments in arraylib.h */
void print(FILE *stream, array *arr) {
	if (!arr || !(arr->ai) || arr->len<1 || !stream){
		fprintf(stream, "%c %c", '[', ']');
		return;
	}
	fprintf(stream, "%c ", '[');
    for (int i =0; i<arr->len; i++){
    	fprintf(stream, "%d, ", *(arr->ai+i));
    }
    fprintf(stream, "%c", ']');
}
        
/* see println comments in arraylib.h */
void println(FILE *stream, array *arr) {
    print(stream, arr);
    fprintf(stream, "\n");
}

/* see delearray comments in arraylib.h */
void delarray(array *arr) {
    if (arr) { 
        if (arr->ai) 
            free(arr->ai);
        free(arr);
    }
}

/* see fatalerror comments in arraylib.h */
void fatalerror(int line, char *msg) {
    fprintf(stderr, ERR_FMT, __FILE__, line, msg, errno, strerror(errno));
    exit(EXIT_FAILURE);
}

/* see newarray_e comments in arraylib.h */
array *newarray_e(int len) {
    array *arr = newarray(len);
    
    if (!arr)
        fatalerror(__LINE__, "array allocation failed");
        
    return arr;
}

/* see get_e comments in arraylib.h */
int get_e(array *arr, int idx) {
    int val = get(arr, idx);
    
    if (val == INT_MIN && errno == EINVAL)
        fatalerror(__LINE__, "null array or index out of bounds");
    
    return val;
}
        
        
/* see set_e comments in arraylib.h */
void set_e(array *arr, int idx, int value) {
    set(arr, idx, value);
    
    if (errno == EINVAL)
        fatalerror(__LINE__, "null array or index out of bounds");
}

/***************************************************************************/

