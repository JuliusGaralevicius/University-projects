/*
 * 160467709
 */
#include <stdbool.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>
#include <libgen.h>
#include <sys/types.h>
#include <errno.h>
#include <string.h>
#include "fileinflib.h"
#include <sys/param.h>

/* is_user_exec helper function forward declaration */
bool is_user_exec(mode_t mode, uid_t owner_uid, gid_t owner_gid);
char getfiletype(mode_t mode, uid_t owner_uid, gid_t owner_gid);
 
/* see mode2str specification comments in fileinflib.h */
char *mode2str(mode_t mode, uid_t owner_uid, gid_t owner_gid) { 
	char *baseString = {"-rwxrwxrwx"};
	if (mode<MODE_MIN || mode>MODE_MAX){
		errno = EDOM;
		return NULL;
	}
	// Alocate string that will be returned
	char *finalString = malloc(sizeof(char)*MODE_S_LEN);
	if (!finalString){
		errno = ENOMEM;
		return NULL;
	}
	char fileType = getfiletype(mode, owner_uid, owner_gid);
	// if filemode is invalid, free the memory of allocated string and return
	if (fileType == 'q'){
		free(finalString);
		errno = EDOM;
		return NULL;
	}
	finalString[0]=fileType;
	// Set each char in finalString to corresponding char in baseString or '-'
	for (int i=MODE_S_LEN-2; i>0; i--){
		mode_t mask = (1 << ((MODE_S_LEN-2)-i)); // isolate the needed bit
		finalString[i]= (mask & mode) ? baseString[i] : '-'; // if the bit is on, file has a corresponding permission
	}
	finalString[MODE_S_LEN-1]='\0';
	return finalString;
}
// returns a letter for each file type, returns 'q' if file mode is not valid
char getfiletype(mode_t mode, uid_t owner_uid, gid_t owner_gid){
	mode_t result = (S_IFMT & mode);
	switch (result) {
		case S_IFDIR:
			return 'd';
	    case S_IFREG:
	    	if (is_user_exec(mode, owner_uid, owner_gid)){
				return 'e';
			}
			return 'f';
		case S_IFLNK: 
			return 'l';
		default:
			// if 13th bit is on and if file type is not S_IFIFO, then the mode is not valid
			if ((1<<12) & result){
				if (result!=S_IFIFO)
		   			return 'q';
			}
			return 'o';
	}
}
/* see getfileinf specification comments in fileinflib.h */
fileinf *getfileinf(char *path) {
		if (strlen(path)>MAXPATHLEN){
			errno = EINVAL;
			return NULL;
		}
		fileinf *inf = malloc(sizeof(fileinf));
		if (!inf)
			return NULL;

		char *baseName = basename(path);
		char *dirName = dirname(path);

		inf->basename = malloc(strlen(baseName)*sizeof(char));
		if (!(inf->basename))
			return NULL;
		inf->dirname = malloc(strlen(dirName)*sizeof(char));
		if (!(inf->dirname))
			return NULL;

		// copy path strings to allocated space in struct
		strcpy(inf->basename, baseName);
		strcpy(inf->dirname, dirName);

 	    struct stat sbuf;
        lstat(baseName, &sbuf);

        inf->mode_s = mode2str(sbuf.st_mode, sbuf.st_uid, sbuf.st_gid);
        if (!(inf->mode_s))
        	return NULL;
        // index of ftype is 'd' ascii value subtracted from the first char ascii value in mode_s 
        int index = *(inf->mode_s) - 'd';
        inf->type = (ftype)index;

        inf->size = sbuf.st_size;

		return inf;
}

/* see delfileinf specification comments in fileinflib.h */
void delfileinf(fileinf *fi) {
	if (fi->mode_s)
		free(fi->mode_s);
	if (fi->dirname)
		free(fi->dirname);
	if (fi->basename)
		free(fi->basename);
	if (fi)
		free(fi);
}
/***************************************************************************/
/***** HELPER FUNCTIONS ****************************************************/
/***************************************************************************/
/* 
 * is_user_exec helper function to test user execute permission for given 
 * file mode and file owner uid and file owner gid.
 * Uses getuid() to get the uid of the calling process and getgid() to get the 
 * gid of the calling process.
 * This function is not part of the filecmdrlib interface.
 */ 
bool is_user_exec(mode_t mode, uid_t owner_uid, gid_t owner_gid) {
    if (owner_uid == getuid())
        return mode & S_IXUSR;
    
    if (owner_gid == getgid())
        return mode & S_IXGRP;
        
    return mode & S_IXOTH;
}

/***************************************************************************/
