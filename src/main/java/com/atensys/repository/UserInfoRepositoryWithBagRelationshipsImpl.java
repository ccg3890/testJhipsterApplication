package com.atensys.repository;

import com.atensys.domain.UserInfo;
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
public class UserInfoRepositoryWithBagRelationshipsImpl implements UserInfoRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<UserInfo> fetchBagRelationships(Optional<UserInfo> userInfo) {
        return userInfo.map(this::fetchAuths);
    }

    @Override
    public Page<UserInfo> fetchBagRelationships(Page<UserInfo> userInfos) {
        return new PageImpl<>(fetchBagRelationships(userInfos.getContent()), userInfos.getPageable(), userInfos.getTotalElements());
    }

    @Override
    public List<UserInfo> fetchBagRelationships(List<UserInfo> userInfos) {
        return Optional.of(userInfos).map(this::fetchAuths).orElse(Collections.emptyList());
    }

    UserInfo fetchAuths(UserInfo result) {
        return entityManager
            .createQuery(
                "select userInfo from UserInfo userInfo left join fetch userInfo.auths where userInfo is :userInfo",
                UserInfo.class
            )
            .setParameter("userInfo", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<UserInfo> fetchAuths(List<UserInfo> userInfos) {
        return entityManager
            .createQuery(
                "select distinct userInfo from UserInfo userInfo left join fetch userInfo.auths where userInfo in :userInfos",
                UserInfo.class
            )
            .setParameter("userInfos", userInfos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
