package com.atensys.repository;

import com.atensys.domain.Auth;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface AuthRepositoryWithBagRelationships {
    Optional<Auth> fetchBagRelationships(Optional<Auth> auth);

    List<Auth> fetchBagRelationships(List<Auth> auths);

    Page<Auth> fetchBagRelationships(Page<Auth> auths);
}
