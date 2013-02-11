#include <stub.h>
#include <motor.h>

//Einlesen eines Wertes von Analog-Port 0
//und Anzeige auf dem LCD

void AksenMain(void)
{
    unsigned char pin=0;
    unsigned char wert=0;
	unsigned float spannung=0;

    while(1)
    {
        lcd_cls();
		//Wert einlesen
        wert=analog(0);

        //Wert im Bereich von 0-255 ausgeben
		lcd_setxy(0,0);
        lcd_ubyte(wert);

		//Wert als Spannung ausgeben

		spannung = wert/51;

		lcd_setxy(1,0);
		lcd_uint(spannung);

		//prüfen, ob Spannung noch ausreicht 	

		lcd_setxy(1,15);
		if (wert > 189)							// Spannung liegt über 3,7 V
		{
			lcd_puts('gruen');
		}
		else if (wert <= 189) && (wert >= 163)	// Spannung liegt zwischen 3,7 und 3,2 V
		{	
			lcd_puts('gelb');
		}
		else									// Spannung liegt unter 3,2 V
		{
			lcd_puts('laden!'); 
		}
		sleep(1000);
    }
}
