package org.hbrs.se1.ws25.exercises.uebung1.view;

import org.hbrs.se1.ws25.exercises.uebung1.control.Translator;
import org.hbrs.se1.ws25.exercises.uebung1.control.TranslatorFactory;

public class Client {

	/**
		 * Methode zur Ausgabe einer Zahl auf der Console
		 * (auch bezeichnet als CLI, Terminal)
		 *
		 */
		 void display( int aNumber ){
			 Translator t = TranslatorFactory.createGermanTranslator();
			 System.out.println("Das Ergebnis der Berechnung: " + t.translateNumber(aNumber));
		 }
}





