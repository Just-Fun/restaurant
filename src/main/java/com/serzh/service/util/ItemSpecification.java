package com.serzh.service.util;

import com.serzh.domain.Item;
import com.serzh.domain.Type;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.SetJoin;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class ItemSpecification {

    private static final String WILDCARD = "%";

    public Specification<Item> getFilter(String keyword) {
        return (root, query, cb) -> {
            query.distinct(true);
            return where(
                    where(firstNameContains(keyword))
                            .or(lastNameContains(keyword))
                            .or(typeContains(keyword))
            )
                    .toPredicate(root, query, cb);
        };
    }


    private Specification<Item> firstNameContains(String keyword) {
        return itemAttributeContains("name", keyword);
    }

    private Specification<Item> lastNameContains(String keyword) {
        return itemAttributeContains("description", keyword);
    }

    private Specification<Item> itemAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }

            return cb.like(
                    cb.lower(root.get(attribute)),
                    containsLowerCase(value)
            );
        };
    }

    private Specification<Item> typeContains(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null) {
                return null;
            }

            SetJoin<Item, Type> types = root.joinSet("types", JoinType.INNER);

            return cb.like(
                    cb.lower(types.get("name")),
                    containsLowerCase(keyword)
            );
        };
    }

    private String containsLowerCase(String searchField) {
        return WILDCARD + searchField.toLowerCase() + WILDCARD;
    }

}
