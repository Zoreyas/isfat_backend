package org.erusakov.backend.repository.redis;

import org.erusakov.backend.entities.redis.AccessTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface AccessTokenRepository extends CrudRepository<AccessTokenEntity, String> {

    boolean existsByUsername(String username);

    boolean existsByToken(String token);

}
