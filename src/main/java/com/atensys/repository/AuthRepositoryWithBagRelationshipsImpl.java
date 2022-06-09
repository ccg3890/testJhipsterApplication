package com.atensys.repository;

import com.atensys.domain.Auth;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class AuthRepositoryWithBagRelationshipsImpl implements AuthRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Auth> fetchBagRelationships(Optional<Auth> auth) {
        return auth.map(this::fetchMenus);
    }

    @Override
    public Page<Auth> fetchBagRelationships(Page<Auth> auths) {
        return new PageImpl<>(fetchBagRelationships(auths.getContent()), auths.getPageable(), auths.getTotalElements());
    }

    @Override
    public List<Auth> fetchBagRelationships(List<Auth> auths) {
        return Optional.of(auths).map(this::fetchMenus).orElse(Collections.emptyList());
    }

    Auth fetchMenus(Auth result) {
        return entityManager
            .createQuery("select auth from Auth auth left join fetch auth.menus where auth is :auth", Auth.class)
            .setParameter("auth", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Auth> fetchMenus(List<Auth> auths) {
        return entityManager
            .createQuery("select distinct auth from Auth auth left join fetch auth.menus where auth in :auths", Auth.class)
            .setParameter("auths", auths)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
