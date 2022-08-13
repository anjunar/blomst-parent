package com.anjunar.common.rest.search.provider;

import com.anjunar.common.rest.search.AbstractRestSortProvider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class GenericSortProvider<E> extends AbstractRestSortProvider<List<String>, E> {
    @Override
    public List<Order> sort(List<String> value, EntityManager entityManager, CriteriaBuilder builder, Root<E> root) {
        List<Order> result = new ArrayList<>();

        if (value == null) {
            return result;
        }

        for (String sortExpression : value) {

            String[] sortSegment = sortExpression.split(":");

            Path cursor = cursor(root, sortSegment[0]);

            String direction = sortSegment[1];

            switch (direction) {
                case "asc": {
                    result.add(builder.asc(cursor));
                }
                break;
                case "desc": {
                    result.add(builder.desc(cursor));
                }
                break;
            }

        }

        return result;
    }
}
