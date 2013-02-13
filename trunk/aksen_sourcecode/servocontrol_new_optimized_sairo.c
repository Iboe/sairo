 // Testausgabe auf dem seriellen Port

 #include <stdio.h>
 #include <regc515c.h>
 #include <stub.h>

 #include "serielle.h"

 // Verbindungsaufbau und bidirektionale Kommnikation zwischen eeePC und Aksen
 //
 // eeePC => Taste/Zeichen 's' - Verbindungaufbau anfordern
 // Aksen => Zeichen 'a' (Acknowledge) - Verbindungsaufbau bestätigen
 // eeePC => 1,90 - Befehlssatz mit Komma getrennt übertragen (Servonummer, Winkel)
 //					mit Komma getrennt können weitere Befehle eingegeben werden (0,50,1,75,...)
 // Aksen => Zeichen '+' - bei Eingabe mehrerer Befehle nach Empfang jedes korrekten Befehls
 // eeePC => Taste/Zeichen 'e' - Ende der Übertragung
 // Aksen => Übertragung des Anzahl empfangener Pakete und des letzten Befehls (z.B. 3:2,98)
 // eeePC => Zeichen 'a' - erfolgreiche Übertragung bestätigen
 // Aksen => Zeichen 'e' - Befehlsliste wurde abgearbeitet

 // Übertragung des Zeichens 'n' bei Auftreten von Fehlern

 /***************************************************************************************/
 //
 //Einstellung Aktoren: Port 0 => Segelservo => 31 bis 114 - Nullstellung 73
 //                     Port 1 => Ruderservo => 34 bis 108 - Nullstellung 68
 //                     Port 2 => Flautenschieber => 32 bis 112 - Nullstellung 72
 // 
 /***************************************************************************************/

struct servobefehl{											// Struktur für einen Servobefehl bestehend aus 
	unsigned char servonummer;								// Servonummer und dem	
	unsigned short int alpha;								// vorgesehenden Winkel
};

void initialisiereServos();									// Servos zu Beginn des Programms auf Nullstellung setzen
unsigned char fuehreBefehleAus(struct servobefehl *, int);	// gesammelte Befehle werden ausgeführt

struct servobefehl befehlsliste[50];						// Liste der gesammelten Befehle

void AksenMain(void)
{
	char debug = 0;
	
	serielle_init();										// Verbindung Ÿber serielle Schnittstelle initialisieren
	initialisiereServos();
	led(0,1);
	
	debug = serielle_getchar();
	if(debug='d'){
	AksenDebug();
	}
	else {
	AksenNoDebug();
	}
}

unsigned char fuehreBefehleAus(struct servobefehl *befehlsliste, int anz_pakete){

	float pulsweite = 1050.0;	
	int j;

		for (j=0; j<anz_pakete; j++)
		{
			// Befehl auf LCD ausgeben
			//lcd_cls();
			//lcd_int(j+1);
			//lcd_puts(" s");
			//lcd_int(befehlsliste[j].servonummer);
			//lcd_puts(" w");
			//lcd_int(befehlsliste[j].alpha);
			
			// und Befehl ausfuehren
			// Umwandlung des Winkels in Pulsweite mit Angabe in Mikrosekunden (genauer Faktor ist 5,555555555... bei 1-2ms)
				// 0 Grad => 1000 µs; 180 Grad => 2000 µs
				// pulsweite = 5.555 * befehlsliste[j].alpha + 1000;
			// Umwandlung des Winkels in Pulsweite mit Angabe in Mikrosekunden (genauer Faktor ist 11,66666666... bei 0-2,1ms)
			
			pulsweite = 11.666 * befehlsliste[j].alpha;
			//servo(befehlsliste[j].servonummer, pulsweite);					//automatischer Cast von float auf int für Pulsweite?
			
			//lcd_setxy(0,10);
			//lcd_uint(pulsweite);
			
			servo_arc(befehlsliste[j].servonummer,befehlsliste[j].alpha);
		
			//servos(programm[j].servonr, programm[j].winkel);

			// gemäß Befehl warten
			//sleep(programm[j].zeit);
		}

	serielle_putchar('e');							// meldung an pc: programm fertig abgearbeitet
	
	return 1;
}

void AksenDebug(void){
	char zeichen, unerwartetes_zeichen = 0;							// um Eingabe von unerwarteten Zeichen abzufangen
	char ungueltiger_winkel = 0;
    unsigned char anz_pakete = 0,							// Anzahl der empfangenen Befehlssätze
	              analog_wert = 0;
				  				  				  
	unsigned short int servonr = 0, winkel = 0,				
					   bef_element = 0,						// welcher Teil des Befehlsatzes wird gerade empfangen?
					   zahl = 0;							// Hilfsvariable zur Umwandlung von Zeichen in Zahl

	float spannung = 0.0;

	while (1)												// Endlosschleife zum Lesen
	{ 	

		serielle_gets(string);

		lcd_cls();
		lcd_puts(string);
		
		lcd_cls();
		lcd_puts("Warte auf Kontakt");

		unerwartetes_zeichen = 0;
		ungueltiger_winkel = 0;
		anz_pakete = 0;										
		bef_element = 0;
		zahl = 0;
		
		zeichen = serielle_getchar();						// Zeichen von COM-Port lesen
		
		
		if(zeichen=='s')									// 
		{	
			serielle_putchar('a');
			lcd_cls();
			lcd_puts("connected");							// Verbindung steht
			
			while ((!unerwartetes_zeichen) && ((zeichen = serielle_getchar()) != 'e')) 
			{
				lcd_cls();
				lcd_putchar(zeichen);
				if ((zeichen >= '0') && (zeichen <= '9'))
				{	
					zahl = zahl*10 + (zeichen - 48);		// empfangenes Zeichen wird in Zahl umgewandelt	
				}
				else if (zeichen == ',')					// Trennzeichen
				{
					if (bef_element == 0)					// erstes Zeichen des Befehlssatzes ist Nummer des anzusteuernden Servos
					{
						servonr = zahl;
					}
					else if (bef_element == 1)				// danach kommt der anzusteuernde Winkel
					{
						winkel = zahl;
															// hier ist ein Befehlssatz komplett
															// und muss auf Korrektheit ŸberprŸft werden
	
						if ((servonr >= 0) && (servonr <= 2))
						{
							switch (servonr)
							{
							case 0:
								if ((winkel > 30) && (winkel < 115))
									break;
								else ungueltiger_winkel = 1;
									break;
							case 1:
								if ((winkel > 33) && (winkel < 109))
									break;
								else ungueltiger_winkel = 1;
									break;
							case 2:
								if ((winkel > 31) && (winkel < 113))
									break;
								else ungueltiger_winkel = 1;
									break;
							}
							if (ungueltiger_winkel == 0)
							{
								befehlsliste[anz_pakete].servonummer = servonr;
								befehlsliste[anz_pakete].alpha = winkel;
								serielle_putchar('+');
								lcd_cls();
								lcd_putchar(winkel);
								anz_pakete++;
							}
							else
								serielle_putchar('f');
						}					
						else
						{
							unerwartetes_zeichen = 1;
							serielle_putchar('f');
						}
					}
					zahl = 0;
					bef_element = (bef_element + 1) % 2;	
				}
			
				else
				{
					unerwartetes_zeichen = 1;
					serielle_putchar('f'):
				}

			}												// Ende der inneren While-Schleife

			if((bef_element == 1) && (zeichen = 'e'))		// letzter Befehlsatz muss noch ŸberprŸft 
			{												// und ins Array geschrieben werden	
				
				winkel = zahl;
				
				if ((servonr >= 0) && (servonr <= 2))
				{
					switch (servonr)
					{
					case 0:
						if ((winkel > 30) && (winkel < 115))
							break;
						else ungueltiger_winkel = 1;
							break;
					case 1:
						if ((winkel > 33) && (winkel < 109))
							break;
						else ungueltiger_winkel = 1;
							break;
					case 2:
						if ((winkel > 31) && (winkel < 113))
							break;
						else ungueltiger_winkel = 1;
							break;
					}
					if (ungueltiger_winkel == 0)
					{
						befehlsliste[anz_pakete].servonummer = servonr;
						befehlsliste[anz_pakete].alpha = winkel;
						anz_pakete++;
						lcd_cls();
						lcd_putchar(anz_pakete + 48);			// Umwandlung in Dezimalzahl zur Ausgabe
						lcd_setxy(0,2);
						lcd_uint(winkel);
					}
					else 
						serielle_putchar('f');
				}						
				else
				{
					unerwartetes_zeichen = 1;
					serielle_putchar('f');
				}
			
				bef_element = 0;							// Variable 0 setzen um Befehlausführung zu ermöglichen
		
			}
			
			if(zeichen != 'e')								// Ende ('e') wird erwartet - alles andere >> Fehler
			{						
				lcd_cls();
				lcd_puts("Progr.fehler");
				serielle_putchar('n');				 				// NACK senden => springt an Anfang der while-Schleife
			}

			if((bef_element == 0) && (!unerwartetes_zeichen) && (!ungueltiger_winkel))		// komplette Befehle empfangen?
			{
				serielle_putchar(anz_pakete+48);					// erfolgreichen Paketempfang bestätigen
				serielle_putchar(':');
				serielle_putchar(servonr+48);
				serielle_putchar(',');
				serielle_putchar(winkel);
				zeichen=serielle_getchar();							// auf BestŠtigung warten
				if(zeichen == 'a'){ 								// ACK
					lcd_puts("Start ...");
					fuehreBefehleAus(befehlsliste, anz_pakete);		// Komplettes Programm (alle Befehle) abarbeiten
				}
				else if(zeichen=='n')								// empfangene Packete nicht korrekt, Empfang wird abgebrochen
				{								
					lcd_cls();
					lcd_puts("ERR 3way handshake");
					serielle_putchar('n');
				}
				else
				{
					unerwartetes_zeichen = 1;					// Error, Übertragung oder Programm fehlerhaft
					lcd_cls();
					lcd_puts("illegal char");
					serielle_putchar('n');
				}
			}
			else 
			{
				lcd_cls();
				lcd_puts("resend command");
			}
		}
		else if (zeichen == 'v')
		{
			lcd_cls();
			Wert einlesen
			analog_wert=analog(0);

			Wert im Bereich von 0-255 ausgeben
			lcd_setxy(0,0);
			lcd_ubyte(analog_wert);

			//Wert als Spannung auf LCD aus- und an EeePc übergeben

			serielle_putchar(analog_wert);
			spannung = (analog_wert / 51.0 - 0.198) / 0.565;   // Abbildung 0...5 V auf Werte 0...255 ergibt n = 51 
															   // dazu umgestellte Formel Uaus = 0,565 * Uein + 0,198
			
			lcd_setxy(1,0);
			lcd_uint(spannung*100);							   // da Nachkommastellen sonst abgeschnitten werden
	
			sleep(500);
		}
		else
		{
			unerwartetes_zeichen = 1;							// SYN erwartet, breche Übertragung ab
			lcd_cls();
			lcd_int(zeichen);
			sleep(5000);

			lcd_cls();
			lcd_puts("syn erwartet");
			serielle_putchar('n');
			sleep(1000);
		}

}

void AksenNoDebug(void){
char zeichen, unerwartetes_zeichen = 0;							// um Eingabe von unerwarteten Zeichen abzufangen
	char ungueltiger_winkel = 0;
    unsigned char anz_pakete = 0,							// Anzahl der empfangenen Befehlssätze
	              analog_wert = 0;
				  				  				  
	unsigned short int servonr = 0, winkel = 0,				
					   bef_element = 0,						// welcher Teil des Befehlsatzes wird gerade empfangen?
					   zahl = 0;							// Hilfsvariable zur Umwandlung von Zeichen in Zahl

	float spannung = 0.0;
	
	while (1)												// Endlosschleife zum Lesen
	{ 	

		serielle_gets(string);

		unerwartetes_zeichen = 0;
		ungueltiger_winkel = 0;
		anz_pakete = 0;										
		bef_element = 0;
		zahl = 0;
		
		zeichen = serielle_getchar();						// Zeichen von COM-Port lesen
		
		
		if(zeichen=='s')									// 
		{	
			serielle_putchar('a');
			
			while ((!unerwartetes_zeichen) && ((zeichen = serielle_getchar()) != 'e')) 
			{

				if ((zeichen >= '0') && (zeichen <= '9'))
				{	
					zahl = zahl*10 + (zeichen - 48);		// empfangenes Zeichen wird in Zahl umgewandelt	
				}
				else if (zeichen == ',')					// Trennzeichen
				{
					if (bef_element == 0)					// erstes Zeichen des Befehlssatzes ist Nummer des anzusteuernden Servos
					{
						servonr = zahl;
					}
					else if (bef_element == 1)				// danach kommt der anzusteuernde Winkel
					{
						winkel = zahl;
															// hier ist ein Befehlssatz komplett
															// und muss auf Korrektheit überprüft werden
	
						if ((servonr >= 0) && (servonr <= 2))
						{
							switch (servonr)
							{
							case 0:
								if ((winkel > 30) && (winkel < 115))
									break;
								else ungueltiger_winkel = 1;
									break;
							case 1:
								if ((winkel > 33) && (winkel < 109))
									break;
								else ungueltiger_winkel = 1;
									break;
							case 2:
								if ((winkel > 31) && (winkel < 113))
									break;
								else ungueltiger_winkel = 1;
									break;
							}
							if (ungueltiger_winkel == 0)
							{
								befehlsliste[anz_pakete].servonummer = servonr;
								befehlsliste[anz_pakete].alpha = winkel;
								serielle_putchar('+');
								anz_pakete++;
							}
							else
								serielle_putchar('f');
						}					
						else
						{
							unerwartetes_zeichen = 1;
							serielle_putchar('f');
						}
					}
					zahl = 0;
					bef_element = (bef_element + 1) % 2;	
				}
			
				else
				{
					unerwartetes_zeichen = 1;
					serielle_putchar('n');
				}

			}												// Ende der inneren While-Schleife

			if((bef_element == 1) && (zeichen = 'e'))		// letzter Befehlsatz muss noch überprüft 
			{												// und ins Array geschrieben werden	
				
				winkel = zahl;
				
				if ((servonr >= 0) && (servonr <= 2))
				{
					switch (servonr)
					{
					case 0:
						if ((winkel > 30) && (winkel < 115))
							break;
						else ungueltiger_winkel = 1;
							break;
					case 1:
						if ((winkel > 33) && (winkel < 109))
							break;
						else ungueltiger_winkel = 1;
							break;
					case 2:
						if ((winkel > 31) && (winkel < 113))
							break;
						else ungueltiger_winkel = 1;
							break;
					}
					if (ungueltiger_winkel == 0)
					{
						befehlsliste[anz_pakete].servonummer = servonr;
						befehlsliste[anz_pakete].alpha = winkel;
						anz_pakete++;
					}
					else 
						serielle_putchar('f');
				}						
				else
				{
					unerwartetes_zeichen = 1;
					serielle_putchar('f');
				}
			
				bef_element = 0;							// Variable 0 setzen um Befehlausführung zu ermöglichen
		
			}
			
			if(zeichen != 'e')								// Ende ('e') wird erwartet - alles andere >> Fehler
			{						
				serielle_putchar('n');				 				// NACK senden => springt an Anfang der while-Schleife
			}

			if((bef_element == 0) && (!unerwartetes_zeichen) && (!ungueltiger_winkel))		// komplette Befehle empfangen?
			{
				serielle_putchar(anz_pakete+48);					// erfolgreichen Paketempfang bestätigen
				serielle_putchar(':');
				serielle_putchar(servonr+48);
				serielle_putchar(',');
				serielle_putchar(winkel);
				zeichen=serielle_getchar();							// auf Bestätigung warten
				if(zeichen == 'a'){ 								// ACK
					fuehreBefehleAus(befehlsliste, anz_pakete);		// Komplettes Programm (alle Befehle) abarbeiten
				}
				else if(zeichen=='n')								// empfangene Packete nicht korrekt, Empfang wird abgebrochen
				{								
					serielle_putchar('n');
				}
				else
				{
					unerwartetes_zeichen = 1;					// Error, Übertragung oder Programm fehlerhaft
					serielle_putchar('n');
				}
			}
			else 
			{

				serielle_putchar('n');
			}
		}
		else if (zeichen == 'v')
		{
			//Wert einlesen
			analog_wert=analog(0);

			//Wert als Spannung auf LCD aus- und an EeePc übergeben

			serielle_putchar(analog_wert);
			spannung = (analog_wert / 51.0 - 0.198) / 0.565;   // Abbildung 0...5 V auf Werte 0...255 ergibt n = 51 
															   // dazu umgestellte Formel Uaus = 0,565 * Uein + 0,198
			sleep(500);
		}
		else
		{
			unerwartetes_zeichen = 1;							// SYN erwartet, breche Übertragung ab
			sleep(5000);
			serielle_putchar('n');
			sleep(1000);
		}
}

void initialisiereServos(void)
{
	servo_arc(0,73);								// Nullstellungen der einzelnen Aktoren 
	servo_arc(1,68);
	servo_arc(2,73);
}

void getChecksumme(void)
//unsigned char getChecksumme(char * str)
{
	//unsigned char Checksum = 0;
	//for (str++; *str != '*' && *str != '\0'; str++)
	//	Checksum ^= *(unsigned char*)str;
 //  return Checksum;
}

void ueberpruefeEingabe(void)
{
}
