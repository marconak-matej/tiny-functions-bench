package io.github.mm.uuid.impl;

import com.github.f4b6a3.uuid.UuidCreator;
import com.github.f4b6a3.uuid.enums.UuidNamespace;
import io.github.mm.uuid.UUIDGenerationStrategy;
import java.util.UUID;

/**
 * UUID v3 (Name-based MD5) implementation.
 * Source: <a href="https://mvnrepository.com/artifact/com.github.f4b6a3/uuid-creator">uuid-creator</a>
 */
public class UUID3 implements UUIDGenerationStrategy {

    @Override
    public UUID generateUUID() {
        return UuidCreator.getNameBasedMd5(UuidNamespace.NAMESPACE_URL, "https://marconak-matej.github.io/");
    }
}
