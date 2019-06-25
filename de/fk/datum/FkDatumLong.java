package de.fk.datum;

public class FkDatumLong
{
  /**
   * <pre>
   * Liefert fuer eine gueltige Datumseingabe im Format JJJJMMTT einen Long-Wert im Format JJJJMMTT.
   * Wandlung eines Stringdatums in einen Long-Wert mit Datumspruefung.
   * 
   * Ist die Eingabe kein gueltiges Datum, wird 0 zurueckgegeben.
   * 
   * Das Jahr muss mindestens 1583 sein.
   * 
   * Es wird kein Trim auf die Eingabe gemacht.
   * 
   * FkDatum.getLongAusJJJJMMTT( "20100115"  ) = "20100115"
   * FkDatum.getLongAusJJJJMMTT( "20120229"  ) = "20120229"
   * FkDatum.getLongAusJJJJMMTT( "20100229"  ) = 0
   * FkDatum.getLongAusJJJJMMTT( "20100999"  ) = 0
   * FkDatum.getLongAusJJJJMMTT( "15810101"  ) = 0
   * FkDatum.getLongAusJJJJMMTT( "JJJJMMTT"  ) = 0
   * FkDatum.getLongAusJJJJMMTT( "12010"     ) = 0
   * FkDatum.getLongAusJJJJMMTT( ""          ) = 0
   * FkDatum.getLongAusJJJJMMTT( null        ) = 0
   * FkDatum.getLongAusJJJJMMTT( "20100115A" ) = 0
   * 
   * 
   * long long_datum = FkDatum.getLongAusJJJJMMTT( pStringDatum );
   * 
   * if ( long_datum > 19000101 ) 
   * {
   *   ...
   * }
   * 
   * </pre>
   * 
   * @param pEingabe die Eingabe im Format JJJJMMTT (Zahlen und 8 Stellen)
   * @return 0 oder ein Datum im Format JJJJMMTT
   */
  public static long getLongAusJJJJMMTT( String pEingabe )
  {
    try
    {
      /*
       * Pruefung: Ist die Eingabe null?
       * Ist die Eingabe null, bekommt der Aufrufer 0 zurueck.
       */
      if ( pEingabe == null )
      {
        return 0;
      }

      /*
       * Pruefung: Zeichenanzahl korrekt?
       * Die Eingabe muss genau 8 Stellen lang sein. 
       * Ist die Eingabe ungleich 8 Stellen, bekommt der Aufrufer 0 zurueck.
       */
      if ( pEingabe.length() != 8 )
      {
        return 0;
      }

      int akt_tag = -1;
      int akt_monat = -1;
      int akt_jahr = -1;

      int akt_zahl = 0;

      int akt_index = 0;

      /*
       * While-Schleife ueber die Eingabe zum lesen von Tag, Monat und Jahr.
       */
      while ( akt_index < 8 )
      {
        /*
         * Aktuelles Zeichen aus der Eingabe lesen
         */
        char akt_char = pEingabe.charAt( akt_index );

        /*
         * Zeichenpruefung
         * Das Datum im Format JJJJMMTT besteht nur aus Zahlen.
         * Ist das aktuelle Zeichen keine Zahl, wird die Funktion mit 0 verlassen.
         */
        if ( ( akt_char < '0' ) || ( akt_char > '9' ) )
        {
          return 0;
        }

        /*
         * Berechnung Akt-Zahl
         * Der Wert in der Variablen "akt_zahl" wird mit 10 multipliziert und 
         * der Wert des aktuellen Zeichens hinzugezaehlt. 
         * 
         * Der Wert des aktuellen Zeichens ist der Wert des ASCII-Code abzueglich 48. 
         */
        akt_zahl = ( akt_zahl * 10 ) + ( ( (int) akt_char ) - 48 );

        /*
         * Zuweisung von Jahr und Monat nach Index-Positionen
         */
        if ( akt_index == 3 )
        {
          /*
           * Index 3 (4 Stellen gelesen)
           * Die ersten 4 Stellen sind Zahlen und geben das Jahr an.
           * Beim Erreichen des 4ten Zeichens wird das Jahr mit dem 
           * Wert der Variablen "akt_zahl" gesetzt.
           * 
           * Die Variable "akt_zahl" wird anschliessend auf 0 gesetzt.
           */
          akt_jahr = akt_zahl;

          akt_zahl = 0;
        }
        else if ( akt_index == 5 )
        {
          /*
           * Index 5
           * Die Zeichen 5 und 6 sind Zahlen und geben den Monat an.  
           * Beim Erreichen des 6ten Zeichens wird der Monat mit 
           * dem Wert der Variablen "akt_zahl" gesetzt.
           * 
           * Die Variable "akt_zahl" wird anschliessend auf 0 gesetzt.
           */
          akt_monat = akt_zahl;

          akt_zahl = 0;
        }

        /*
         * Leseprozess ein Zeichen weiter stellen 
         */
        akt_index++;
      }

      /*
       * Die Zeichen 7 und 8 sind Zahlen und geben den Tag an.
       * Nach der While-Schleife ist in der Variablen "akt_zahl" der Tag gesetzt.
       * Der Tag wird mit dem Wert aus "akt_zahl" gesetzt.
       */
      akt_tag = akt_zahl;

      /*
       * Pruefung: Datumswerte 
       * Der Tag muss groesser als 1 sein, der Maximalwerttest kommt spaeter.
       * Der Monat muss zwischen 1 und 12 liegen
       * Das Jahr muss zwischen 1583 und 9999 liegen 
       */
      if ( ( akt_tag >= 1 ) && ( ( akt_monat >= 1 ) && ( akt_monat <= 12 ) ) && ( ( akt_jahr >= 1583 ) && ( akt_jahr <= 9999 ) ) )
      {
        /*
         * Ermittlung Maximalwert Tag
         * Fuer die Maximalwertpruefung des Tages wird die Anzahl der Tage von 
         * Monat und Jahr bestimmt. Dabei wird das Schaltjahr beruecksichtigt.
         */
        int anzahl_tage = 31;

        if ( akt_monat == 2 )
        {
          if ( ( ( akt_jahr % 400 ) == 0 ) || ( ( akt_jahr % 100 ) > 0 ) && ( ( akt_jahr % 4 ) == 0 ) )
          {
            anzahl_tage = 29;
          }
          else
          {
            anzahl_tage = 28;
          }
        }
        else if ( akt_monat == 4 || akt_monat == 6 || akt_monat == 9 || akt_monat == 11 )
        {
          anzahl_tage = 30;
        }

        /*
         * Pruefung: Maximalgrenze Tag
         * Ist der uebergebene Tag kleiner oder gleich der Maximalgrenze,
         * wird das Ergebnisdatum erstellt und zurueckgegeben 
         *
         * Liegt der uebergebene Tag hinter der Maximalgrenze, ist das 
         * Datum ungueltig und der Aufrufer bekommt 0 zurueck. 
         */
        if ( akt_tag <= anzahl_tage )
        {
          /*
           * Ergebnisaufbau
           * Es wird das Datum im Format JJJJMMTT zurueckgegeben.
           */
          return akt_jahr * 10000 + akt_monat * 100 + akt_tag;
        }
      }
    }
    catch ( Exception err_inst )
    {
      // Fehler in der While-Schleife ergibt eine Rueckgabe von 0
    }

    return 0;
  }

  /**
   * <pre>
   * Liefert fuer ein gueltiges String-Datum im Format TT.MM.JJJJ das Long-Datum im Format JJJJMMTT.
   * 
   * Auf die Eingabe wird kein Trim gemacht.
   * 
   * Ist das String-Datum ungueltig, wird 0 zurueckgegeben.
   * 
   * FkDatum.getLongAusTTPMMPJJJJ( "10.05.2010"  ); = 20100510
   * FkDatum.getLongAusTTPMMPJJJJ( "10.5.2010"   ); = 20100510
   * FkDatum.getLongAusTTPMMPJJJJ( "1.5.2010"    ); = 20100501
   * FkDatum.getLongAusTTPMMPJJJJ( "1.05.2010"   ); = 20100501   
   * 
   * FkDatum.getLongAusTTPMMPJJJJ( " 1.5.2010 "  ); = 0 = Leerzeichen fuehren zum Fehler
   * 
   * 
   * FkDatum.getLongAusTTPMMPJJJJ( "1.5.10"      ); = 0 = zu kurz
   * FkDatum.getLongAusTTPMMPJJJJ( "10.05.20100" ); = 0 = zu lang
   * 
   * FkDatum.getLongAusTTPMMPJJJJ( "1..05.2010"  ); = 0 = mehr Punkte als erwartet
   * FkDatum.getLongAusTTPMMPJJJJ( "1.05..2010"  ); = 0
   * FkDatum.getLongAusTTPMMPJJJJ( "1.05.20.10"  ); = 0
   * 
   * FkDatum.getLongAusTTPMMPJJJJ( ".01.012010"  ); = 0 = Start mit Punkt
   * FkDatum.getLongAusTTPMMPJJJJ( "01.012010."  ); = 0 = Ende mit Punkt
   * 
   * FkDatum.getLongAusTTPMMPJJJJ( "10.052010"   ); = 0 = weniger Punkte als erwartet
   * FkDatum.getLongAusTTPMMPJJJJ( "10052010"    ); = 0 = keine Trennzeichen in der Eingabe
   * 
   * FkDatum.getLongAusTTPMMPJJJJ( "1A.05.2010"  ); = 0 = ungueltige Zeichen 
   * FkDatum.getLongAusTTPMMPJJJJ( "10.B.2010"   ); = 0
   * FkDatum.getLongAusTTPMMPJJJJ( "10.05.C010"  ); = 0
  
   * FkDatum.getLongAusTTPMMPJJJJ( "00.01.2010"  ); = 0 = Tagesanteil hat den Wert 0
   * FkDatum.getLongAusTTPMMPJJJJ( "01.00.2010"  ); = 0 = Monatsanteil hat den Wert 0
   * 
   * FkDatum.getLongAusTTPMMPJJJJ( "01.13.2010"  ); = 0 = Monat existiert nicht
   * FkDatum.getLongAusTTPMMPJJJJ( "30.2.2010"   ); = 0 = Datum ungueltig
   * FkDatum.getLongAusTTPMMPJJJJ( "ABCDEFGH"    ); = 0 = nur Text
   * FkDatum.getLongAusTTPMMPJJJJ( null          ); = 0 = keine Eingabe
   * </pre>
   * 
   * @param pDatum das Eingabedatum im Format "TT.MM.JJJJ" (Tag und Monat koennen einstellig sein, Jahr muss 4 stellig sein)
   * @return das Datum im Long-Format JJJJMMTT oder 0 sofern die Eingabe kein korrektes Datum ist.
   */
  public static long getLongAusTTPMMPJJJJ( String pEingabe )
  {
    try
    {
      /*
       * Pruefung: Ist die Eingabe null?
       * Ist die Eingabe null, bekommt der Aufrufer 0 zurueck.
       */
      if ( pEingabe == null )
      {
        return 0;
      }

      /*
       * Ermittlung der Zeichenanzahl der Eingabe
       */
      int anzahl_eingabe_zeichen = pEingabe.length();

      /*
       * Pruefung: Zeichenanzahl korrekt?
       * 
       * Die Eingabe darf nicht kuerzer als 8 Zeichen sein. 
       * Das sind zwei Punkte und 6 Zahlen.
       * 
       * Die Eingabe darf nicht laenger als 10 Zeichen sein. 
       * Das sind zwei Punkte und 8 Zahlen.
       * 
       * Liegt eine ungueltige Eingabe vor, bekommt der Aufrufer 0 zurueck.
       */
      if ( ( anzahl_eingabe_zeichen < 8 ) || ( anzahl_eingabe_zeichen > 10 ) )
      {
        return 0;
      }

      int akt_tag = -1;
      int akt_monat = -1;
      int akt_jahr = -1;

      int akt_zahl = 0;

      int akt_index = 0;

      /*
       * While-Schleife ueber die Eingabe zum lesen von Tag, Monat und Jahr.
       */
      while ( akt_index < anzahl_eingabe_zeichen )
      {
        /*
         * Aktuelles Zeichen aus der Eingabe lesen
         */
        char akt_char = pEingabe.charAt( akt_index );

        /*
         * Pruefung: Trennzeichen gefunden und aktuelle Zahl groesser 0 ?
         * 
         * Das Trennzeichen muss 2mal vorhanden sein.
         * Bei einem Trennzeichen muss die aktuelle Zahl groesser als 0 sein.
         * Ein Datumsbestandteil kann nicht 0 sein.
         * 
         * Ist die aktuelle Zahl nicht groesser als 0, wird das aktuelle 
         * Zeichen (=Trennzeichen) als ungueltig erkannt und fuehrt in der 
         * naechsten IF-Bedingung zu einem Fehler (Rueckgabe von 0).
         * (Kein Start mit einem Trennzeichen)
         * 
         * Ist die aktuelle Zahl groesser als 0, wird beim ersten Trennzeichen 
         * der Tag und beim zweiten Trennzeichen der Monat gesetzt. 
         * Das Jahr wird nach der While-Schleife gesetzt.
         * 
         * Kommt in der Eingabe ein drittes Trennzeichen vor, sind Tag und Monat 
         * schon gesetzt und es kommt zur Rueckgabe von 0.
         * 
         * Ist in der Eingabe kein Trennzeichen vorhanden, kommt es eventuell 
         * zu einer Ueberlaufexception, oder nach der While-Schleife ist 
         * der Tag oder der Monat noch auf dem Initialwert von -1, welches 
         * wiederum zum Fehler fuehrt. 
         * 
         * Nach der Zuweisung von Tag oder Monat, wird die aktuelle Zahl  
         * auf 0 gestellt.
         */
        if ( ( akt_char == '.' ) && ( akt_zahl > 0 ) )
        {
          if ( akt_tag == -1 )
          {
            akt_tag = akt_zahl;
          }
          else if ( akt_monat == -1 )
          {
            akt_monat = akt_zahl;
          }
          else
          {
            return 0;
          }

          akt_zahl = 0;
        }
        /*
         * Zeichenpruefung
         * Ist das aktuelle Zeichen keine Zahl, wird die Funktion mit 0 verlassen.
         */
        else if ( ( akt_char < '0' ) || ( akt_char > '9' ) )
        {
          return 0;
        }
        else
        {
          /*
           * Berechnung Akt-Zahl
           * Der Wert in der Variablen "akt_zahl" wird mit 10 multipliziert und 
           * der Wert des aktuellen Zeichens hinzugezaehlt. 
           * 
           * Der Wert des aktuellen Zeichens ist der Wert des ASCII-Code abzueglich 48. 
           */
          akt_zahl = ( akt_zahl * 10 ) + ( ( (int) akt_char ) - 48 );
        }

        /*
         * Leseprozess ein Zeichen weiter stellen 
         */
        akt_index++;
      }

      /*
       * Nach dem zweiten Trennzeichen sind die restlichen Zahlen das Jahr. 
       * Nach der While-Schleife ist in der Variablen "akt_zahl" das Jahr gesetzt.
       * Das Jahr wird hier mit dem Wert aus "akt_zahl" gesetzt.
       * 
       * Die Variable "akt_zahl" muss sein, es kann keine Variable eingespart werden. 
       */
      akt_jahr = akt_zahl;

      /*
       * Pruefung: Datumswerte 
       * Der Tag muss groesser als 1 sein, der Maximalwerttest kommt spaeter.
       * Der Monat muss zwischen 1 und 12 liegen
       * Das Jahr muss zwischen 1583 und 9999 liegen 
       */
      if ( ( akt_tag >= 1 ) && ( ( akt_monat >= 1 ) && ( akt_monat <= 12 ) ) && ( ( akt_jahr >= 1583 ) && ( akt_jahr <= 9999 ) ) )
      {
        /*
         * Ermittlung Maximalwert Tag
         * Fuer die Maximalwertpruefung des Tages wird die Anzahl der Tage von 
         * Monat und Jahr bestimmt. Dabei wird das Schaltjahr beruecksichtigt.
         */
        int anzahl_tage = 31;

        if ( akt_monat == 2 )
        {
          if ( ( ( akt_jahr % 400 ) == 0 ) || ( ( akt_jahr % 100 ) > 0 ) && ( ( akt_jahr % 4 ) == 0 ) )
          {
            anzahl_tage = 29;
          }
          else
          {
            anzahl_tage = 28;
          }
        }
        else if ( akt_monat == 4 || akt_monat == 6 || akt_monat == 9 || akt_monat == 11 )
        {
          anzahl_tage = 30;
        }

        /*
         * Pruefung: Maximalgrenze Tag
         * Es gibt nur dann ein Ergebnis, sofern der uebergebene Tag kleiner 
         * oder gleich der Maximalgrenze ist.
         */
        if ( akt_tag <= anzahl_tage )
        {
          /*
           * Ergebnisaufbau
           * Rueckgabe im Format JJJJMMTT.
           */
          return akt_jahr * 10000 + akt_monat * 100 + akt_tag;
        }
      }
    }
    catch ( Exception err_inst )
    {
      // Fehler in der While-Schleife ergibt eine Rueckgabe von FALSE
    }

    /*
     * Fehler: Rueckgabe von 0
     */
    return 0;
  }

}
