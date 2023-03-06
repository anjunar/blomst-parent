package com.anjunar.blomst.shared.visibility;

import com.anjunar.blomst.control.users.user.connections.categories.category.CategoryForm;
import com.anjunar.common.ddd.AbstractColumnRight;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.security.Category;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.metamodel.EntityType;
import jakarta.ws.rs.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
@Path("shared/visibility/column")
public class VisibilityColumnResource {

    private final EntityManager entityManager;

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;

    @Inject
    public VisibilityColumnResource(EntityManager entityManager, ResourceEntityMapper entityMapper, ResourceRestMapper restMapper) {
        this.entityManager = entityManager;
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
    }

    public VisibilityColumnResource() {
        this(null, null, null);
    }

    @GET
    @Produces("application/json")
    public Set<CategoryForm> read(@QueryParam("property") String property, @QueryParam("class") String clazz, @QueryParam("id") UUID id) {
        try {
            AbstractColumnRight<?> columnRight = entityManager.createQuery("select a from " + clazz + "Right a where a.source.id = :id and a.column = :property", AbstractColumnRight.class)
                    .setParameter("id", id)
                    .setParameter("property", property)
                    .getSingleResult();

            Set<CategoryForm> resources = new HashSet<>();

            for (Category category : columnRight.getCategories()) {
                resources.add(entityMapper.map(category, CategoryForm.class));
            }

            return resources;
        } catch (NoResultException e) {
            return new HashSet<>();
        }
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseOk save(@QueryParam("property") String property, @QueryParam("class") String clazz, @QueryParam("id") UUID id, Set<CategoryForm> forms) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        AbstractColumnRight<?> columnRight = null;
        try {
            columnRight = entityManager.createQuery("select a from " + clazz + "Right a where a.source.id = :id and a.column = :property", AbstractColumnRight.class)
                    .setParameter("id", id)
                    .setParameter("property", property)
                    .getSingleResult();
        } catch (NoResultException e) {
            Optional<EntityType<?>> source = entityManager.getMetamodel()
                    .getEntities()
                    .stream()
                    .filter((entity) -> entity.getName().equals(clazz))
                    .findFirst();

            Object entity = entityManager.find(source.get().getJavaType(), id);

            Class<?> right = Class.forName(source.getClass().getPackageName() + "." + source.getClass().getSimpleName() + "Right");
            Constructor<?> constructor = right.getDeclaredConstructor();
            AbstractColumnRight instance = (AbstractColumnRight<?>) constructor.newInstance();

            instance.setColumn(property);
            instance.setSource(entity);

            entityManager.persist(instance);
        }

        Set<Category> categories = new HashSet<>();

        for (CategoryForm form : forms) {
            categories.add(restMapper.map(form, Category.class, null, false));
        }

        columnRight.getCategories().clear();
        columnRight.getCategories().addAll(categories);
        return new ResponseOk();
    }
}
