/***********************************************/
/*                                             */
/* V.24-Funktionen							   */
/*                                             */
/* 14.01.2004                                  */
/* K.-U. Mrkor                                 */
/*                                             */
/***********************************************/

#include "serielle.h"

/****************************/
/* V.24-Initialisierung     */
/*						    */
/* 9600 8-N-1				*/
/****************************/
void serielle_init( void)
{
    /* Baudrate der Seriellen 0 auf 9600 einstellen */
    SCON=0x50;
    SRELH=0x03;
    SRELL=0xD9;
    BD=1;                                         /* Baudratengenerator aktivieren */
    PCON |= 0x80;                                 /* SMOD setzen*/
    TI=1;                                         /* los gehts */
}


/****************************/
/*	Senden eines einzelnen  */
/*	Zeichens			    */
/****************************/
void serielle_putchar (char c)
{
    while (!TI);
    TI = 0;

    SBUF = c;
}


/*****************************/
/* Senden einer Zeichenkette */
/*****************************/
void serielle_puts(const char *string)
{

    while(*string!=0)
    {
        serielle_putchar(*string++);
    }
}


/*******************************/
/*	Empfangen eines einzelnen  */
/*	Zeichens    			   */
/*                             */
/* ohne TimeOut !!!			   */
/*******************************/
char serielle_getchar( void)
{
    while (!RI);
    RI = 0;

    return SBUF;
}


/********************************/
/* Empfangen einer Zeichenkette */
/*                              */
/* Zeichenkettenende: 0 oder CR */
/********************************/
void serielle_gets( char * string)
{
    char c;

    do
    {
        c=serielle_getchar();
        *string++=c;
    }
    while (c!=0 && c!=0x0A && c!=0xD);
}
