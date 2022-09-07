package com.anjunar.blomst.system.languages;

import com.anjunar.blomst.system.languages.language.LanguageForm;
import com.anjunar.common.i18n.Language;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;

import java.util.ArrayList;
import java.util.List;

@Path("system/languages")
@ApplicationScoped
public class LanguagesResource implements ListResourceTemplate<LanguageForm, LanguagesSearch> {

    private final LanguagesService service;

    private final ResourceEntityMapper entityMapper;

    @Inject
    public LanguagesResource(LanguagesService service, ResourceEntityMapper entityMapper) {
        this.service = service;
        this.entityMapper = entityMapper;
    }

    public LanguagesResource() {
        this(null, null);
    }

    @Override
    @RolesAllowed({"User", "Administrator"})
    public Table<LanguageForm> list(LanguagesSearch search) {
        long count = service.count(search);
        List<Language> languages = service.find(search);
        List<LanguageForm> resources = new ArrayList<>();

        for (Language language : languages) {
            LanguageForm form = entityMapper.map(language, LanguageForm.class);

            resources.add(form);
        }

        return new Table<>(resources, count) {};
    }
}
