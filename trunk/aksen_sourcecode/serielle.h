/***********************************************/
/*                                             */
/* CAN-Routinen                                */
/*                                             */
/* 18.07.2003                                  */
/* K.-U. Mrkor                                 */
/*                                             */
/***********************************************/

#include <stdio.h>
#include <regc515c.h>
#include <stub.h>

void serielle_init( void);
void serielle_putchar (char c);
void serielle_puts(const char *string);

char serielle_getchar( void);
void serielle_gets( char * string);
