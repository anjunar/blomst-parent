package com.anjunar.common.i18n;

import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;

public class Detector {

    private final static LanguageDetector detector = LanguageDetectorBuilder
            .fromLanguages(
                    com.github.pemistahl.lingua.api.Language.ARABIC,
                    com.github.pemistahl.lingua.api.Language.ARMENIAN,
                    com.github.pemistahl.lingua.api.Language.BASQUE,
                    com.github.pemistahl.lingua.api.Language.CATALAN,
                    com.github.pemistahl.lingua.api.Language.DANISH,
                    com.github.pemistahl.lingua.api.Language.DUTCH,
                    com.github.pemistahl.lingua.api.Language.ENGLISH,
                    com.github.pemistahl.lingua.api.Language.FINNISH,
                    com.github.pemistahl.lingua.api.Language.FRENCH,
                    com.github.pemistahl.lingua.api.Language.GERMAN,
                    com.github.pemistahl.lingua.api.Language.GREEK,
                    com.github.pemistahl.lingua.api.Language.HINDI,
                    com.github.pemistahl.lingua.api.Language.HUNGARIAN,
                    com.github.pemistahl.lingua.api.Language.INDONESIAN,
                    com.github.pemistahl.lingua.api.Language.IRISH,
                    com.github.pemistahl.lingua.api.Language.ITALIAN,
                    com.github.pemistahl.lingua.api.Language.LITHUANIAN,
//                    lithuanian
//                    nepali
//                    norwegian
                    com.github.pemistahl.lingua.api.Language.PORTUGUESE,
                    com.github.pemistahl.lingua.api.Language.ROMANIAN,
                    com.github.pemistahl.lingua.api.Language.RUSSIAN,
                    com.github.pemistahl.lingua.api.Language.SERBIAN,
                    com.github.pemistahl.lingua.api.Language.SPANISH,
                    com.github.pemistahl.lingua.api.Language.SWEDISH,
                    com.github.pemistahl.lingua.api.Language.TAMIL,
                    Language.TURKISH
//                    yiddish
            )
            .build();

    public static String detectLanguageOf(String text) {
        Language language = detector.detectLanguageOf(text);
        if (language.name().equals("UNKNOWN")) {
            return "simple";
        }
        return language.name().toLowerCase();
    }
}
