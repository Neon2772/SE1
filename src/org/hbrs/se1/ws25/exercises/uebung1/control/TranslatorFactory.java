package org.hbrs.se1.ws25.exercises.uebung1.control;

public class TranslatorFactory {

    public static Translator getGermanTranslator() {
        // Translator german = new GermanTranslator();
        // Translator german = new GermanTranslator();
        // german.setDate("Okt/2025");
        return new GermanTranslator();
    }
}
