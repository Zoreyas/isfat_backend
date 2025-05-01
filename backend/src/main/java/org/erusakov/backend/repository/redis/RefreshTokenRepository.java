package org.erusakov.backend.repository.redis;

import org.erusakov.backend.entities.redis.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, String> {

    boolean existsByUsername(String username);

    boolean existsByToken(String token);

}

