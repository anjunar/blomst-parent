package com.anjunar.blomst.social.pages.page;

import com.anjunar.blomst.shared.system.Language;
import com.anjunar.common.rest.objectmapper.Converter;

import java.util.Locale;

public class LanguageConverter implements Converter<Locale, Language> {

    @Override
    public Language factory(Locale locale) {
        Language language = new Language();
        if (locale.toLanguageTag().equals("de-DE")) {
            language.setLanguage("Deutsch");
            language.setLocale(locale);
        }
        if (locale.toLanguageTag().equals("en-DE")) {
            language.setLanguage("English");
            language.setLocale(locale);
        }
        return language;
    }

    @Override
    public Locale updater(Language language) {
        return language.getLocale();
    }

}
