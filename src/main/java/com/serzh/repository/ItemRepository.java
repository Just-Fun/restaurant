package com.serzh.repository;

import com.serzh.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    @Override
    @EntityGraph(attributePaths = "types")
    Page<Item> findAll(Specification<Item> spec, Pageable pageable);

    //    alternative variant
    @EntityGraph(attributePaths = "types")
    Page<Item> findByNameContainingOrDescriptionContainingOrTypes_NameContaining(String nameKeyword, String descKeyword, String typeNameKeyword, Pageable pageable);

    //    @Lock(LockModeType.PESSIMISTIC_WRITE)
}
