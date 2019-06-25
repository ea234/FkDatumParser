package de.fk.datum;

public class FkDatumString
{
  /**
   * <pre>
   * Erstellt aus der Eingabe JJJJMMTT die Ausgabe TT.MM.JJJJ. 
   * 
   * Das Datum muss ein gueltiges Datum ergeben.
   * 
   * Ist die Eingabe kein gueltiges Datum, wird null zurueckgegeben.
   * 
   * Das Jahr muss mindestens 1583 sein.
   * 
   * FkDatum.getStringAusJJJJMMTT( "20100115"  ) = "15.01.2010"
   * FkDatum.getStringAusJJJJMMTT( "20120229"  ) = "29.02.2012"
   * FkDatum.getStringAusJJJJMMTT( "20100229"  ) = null
   * FkDatum.getStringAusJJJJMMTT( "20100999"  ) = null
   * FkDatum.getStringAusJJJJMMTT( "15810101"  ) = null = Jahr kleiner 1583
   * FkDatum.getStringAusJJJJMMTT( "JJJJMMTT"  ) = null
   * FkDatum.getStringAusJJJJMMTT( "12010"     ) = null
   * FkDatum.getStringAusJJJJMMTT( ""          ) = null
   * FkDatum.getStringAusJJJJMMTT( null        ) = null
   * FkDatum.getStringAusJJJJMMTT( "20100115A" ) = null
   * </pre>
   * 
   * @param pDatum die Eingabe (Zahlen und 8 Stellen)
   * @return null oder ein Datum im Format TT.MM.JJJJ
   */
  public static String getStringAusJJJJMMTT( String pDatum )
  {
    try
    {
      /*
       * Pruefung: Ist die Eingabe null?
       * Ist die Eingabe null, bekommt der Aufrufer null zurueck.
       */
      if ( pDatum == null )
      {
        return null;
      }

      /*
       * Pruefung: Zeichenanzahl korrekt?
       * Die Eingabe muss genau 8 Stellen lang sein. 
       * Ist die Eingabe ungleich 8 Stellen, bekommt der Aufrufer null zurueck.
       */
      if ( pDatum.length() != 8 )
      {
        return null;
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
        char akt_char = pDatum.charAt( akt_index );

        /*
         * Zeichenpruefung
         * Das Datum im Format JJJJMMTT besteht nur aus Zahlen.
         * Ist das aktuelle Zeichen keine Zahl, wird die Funktion mit null verlassen.
         */
        if ( ( akt_char < '0' ) || ( akt_char > '9' ) )
        {
          return null;
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
       * Der Tag muss groesser als 1 sein, der Maximalwert test kommt spaeter.
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
         * wird der Ergebnisstring erstellt. 
         *
         * Liegt der uebergebene Tag hinter der Maximalgrenze, ist das 
         * Datum ungueltig und es gibt kein Ergebnis. 
         */
        if ( akt_tag <= anzahl_tage )
        {
          return ( akt_tag < 10 ? "0" : "" ) + akt_tag + "." + ( akt_monat < 10 ? "0" : "" ) + akt_monat + "." + akt_jahr;
        }
      }
    }
    catch ( Exception err_inst )
    {
      //
    }

    /*
     * Funktionsende
     * Der Aufrufer bekommt das erstellte Ergebnis zurueck. 
     */
    return null;
  }

  /**
   * <pre>
   * Validiert das Eingabedatum und liefert im Erfolgsfall ein Datum TT.MM.JJJJ.
   * 
   * Diese Funktion sorgt dafuer, dass das Ergebnisdatum 10 Stellen hat und korrekt ist.
   *  
   * Es muessen die Punkte als Trennzeichen vorhanden sein.
   * 
   * Auf die Eingabe wird kein Trim gemacht.
   * 
   * Ist das String-Datum ungueltig, wird null zurueckgegeben.
   * 
   * FkDatum.getStringAusTTPMMPJJJJ( "01.5.2010"   ); = "01.05.2010"
   * FkDatum.getStringAusTTPMMPJJJJ( "1.05.2010"   ); = "01.05.2010"  
   * FkDatum.getStringAusTTPMMPJJJJ( "10.05.2010"  ); = "10.05.2010"
   * FkDatum.getStringAusTTPMMPJJJJ( "10.5.2010"   ); = "10.05.2010"
   * FkDatum.getStringAusTTPMMPJJJJ( "1.5.2010"    ); = "01.05.2010"
   * 
   * FkDatum.getStringAusTTPMMPJJJJ( " 1.5.2010 "  ); = null = Leerzeichen fuehren zum Fehler
   * 
   * FkDatum.getStringAusTTPMMPJJJJ( "1.5.10"      ); = null = zu kurz
   * FkDatum.getStringAusTTPMMPJJJJ( "10.05.20100" ); = null = zu lang
   * 
   * FkDatum.getStringAusTTPMMPJJJJ( "1..05.2010"  ); = null = mehr Punkte als erwartet
   * FkDatum.getStringAusTTPMMPJJJJ( "1.05..2010"  ); = null
   * FkDatum.getStringAusTTPMMPJJJJ( "1.05.20.10"  ); = null
   * 
   * FkDatum.getStringAusTTPMMPJJJJ( "10.052010"   ); = null = weniger Punkte als erwartet
   * FkDatum.getStringAusTTPMMPJJJJ( "10052010"    ); = null = keine Trennzeichen in der Eingabe
   * 
   * FkDatum.getStringAusTTPMMPJJJJ( "1A.05.2010"  ); = null = ungueltige Zeichen 
   * FkDatum.getStringAusTTPMMPJJJJ( "10.B.2010"   ); = null
   * FkDatum.getStringAusTTPMMPJJJJ( "10.05.C010"  ); = null
   * 
   * FkDatum.getStringAusTTPMMPJJJJ( "01.13.2010"  ); = null = Monat existiert nicht
   * FkDatum.getStringAusTTPMMPJJJJ( "30.2.2010"   ); = null = Datum ungueltig
   * FkDatum.getStringAusTTPMMPJJJJ( "ABCDEFGH"    ); = null = nur Text
   * FkDatum.getStringAusTTPMMPJJJJ( null          ); = null = keine Eingabe
   * </pre>
   * 
   * @param pDatum das Eingabedatum im Format "TT.MM.JJJJ" (Tag und Monat koennen einstellig sein, Jahr muss 4 stellig sein)
   * @return das Datum im Format "TT.MM.JJJJ", oder null sofern die Eingabe kein korrektes Datum ist.
   */
  public static String getStringAusTTPMMPJJJJ( String pDatum )
  {
    try
    {
      /*
       * Pruefung: Ist die Eingabe null?
       * Ist die Eingabe null, bekommt der Aufrufer 0 zurueck.
       */
      if ( pDatum == null )
      {
        return null;
      }

      /*
       * Ermittlung der Zeichenanzahl der Eingabe
       */
      int anzahl_eingabe_zeichen = pDatum.length();

      /*
       * Pruefung: Zeichenanzahl korrekt?
       * 
       * Die Eingabe darf nicht kuerzer als 8 Zeichen sein. 
       * Das sind zwei Punkte und 6 Zahlen.
       * 
       * Die Eingabe darf nicht laenger als 10 Zeichen sein. 
       * Das sind zwei Punkte und 8 Zahlen.
       * 
       * Liegt eine ungueltige Eingabe vor, bekommt der Aufrufer null zurueck.
       */
      if ( ( anzahl_eingabe_zeichen < 8 ) || ( anzahl_eingabe_zeichen > 10 ) )
      {
        return null;
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
        char akt_char = pDatum.charAt( akt_index );

        /*
         * Pruefung: Trennzeichen gefunden und aktuelle Zahl groesser 0 ?
         * 
         * Das Trennzeichen muss 2mal vorhanden sein.
         * Bei einem Trennzeichen muss die aktuelle Zahl groesser als 0 sein.
         * Ein Datumsbestandteil kann nicht 0 sein.
         * 
         * Ist die aktuelle Zahl nicht groesser als 0, wird das aktuelle 
         * Zeichen (=Trennzeichen) als ungueltig erkannt und fuehrt in der 
         * naechsten IF-Bedingung zu einem Fehler (Rueckgabe von null).
         * (Kein Start mit einem Trennzeichen)
         * 
         * Ist die aktuelle Zahl groesser als 0, wird beim ersten Trennzeichen 
         * der Tag und beim zweiten Trennzeichen der Monat gesetzt. 
         * Das Jahr wird nach der While-Schleife gesetzt.
         * 
         * Kommt in der Eingabe ein drittes Trennzeichen vor, sind Tag und Monat 
         * schon gesetzt und es kommt zur Rueckgabe von null.
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
            return null;
          }

          akt_zahl = 0;
        }
        /*
         * Zeichenpruefung
         * Ist das aktuelle Zeichen keine Zahl, wird die Funktion mit null verlassen.
         */
        else if ( ( akt_char < '0' ) || ( akt_char > '9' ) )
        {
          return null;
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
           * Rueckgabe im Format TT.MM.JJJJ.
           */
          return ( akt_tag < 10 ? "0" : "" ) + akt_tag + "." + ( akt_monat < 10 ? "0" : "" ) + akt_monat + "." + akt_jahr;
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
    return null;
  }

}
