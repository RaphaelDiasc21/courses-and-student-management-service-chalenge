package com.alura.chalenge.application.courses.specifications;

import com.alura.chalenge.application.courses.Course;
import com.alura.chalenge.application.shared.enums.Status;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchCourseSpecification implements Specification<Course> {
    private final Status statusFilter;

    public SearchCourseSpecification(Status statusFilter) {
        super();
        this.statusFilter = statusFilter;
    }

    @Override
    public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();

        if(Objects.nonNull(statusFilter)) {
            predicateList.add(criteriaBuilder.equal(root.get("status"),statusFilter));
        }

        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}
