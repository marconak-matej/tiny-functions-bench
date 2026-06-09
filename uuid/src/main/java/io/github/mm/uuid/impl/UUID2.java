package io.github.mm.uuid.impl;

import com.github.f4b6a3.uuid.UuidCreator;
import com.github.f4b6a3.uuid.enums.UuidLocalDomain;
import io.github.mm.uuid.UUIDGenerationStrategy;
import java.util.UUID;

/**
 * UUID v2 (DCE Security) implementation.
 * Source: <a href="https://mvnrepository.com/artifact/com.github.f4b6a3/uuid-creator">uuid-creator</a>
 */
public class UUID2 implements UUIDGenerationStrategy {

    @Override
    public UUID generateUUID() {
        return UuidCreator.getDceSecurity(UuidLocalDomain.LOCAL_DOMAIN_PERSON, 1);
    }
}
