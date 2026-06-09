package io.github.mm.uuid.impl;

import com.github.f4b6a3.uuid.UuidCreator;
import io.github.mm.uuid.UUIDGenerationStrategy;
import java.util.UUID;

/**
 * UUID v7 (Time-ordered Epoch) implementation.
 * Source: <a href="https://mvnrepository.com/artifact/com.github.f4b6a3/uuid-creator">uuid-creator</a>
 */
public class UUID7 implements UUIDGenerationStrategy {

    @Override
    public UUID generateUUID() {
        return UuidCreator.getTimeOrderedEpoch();
    }
}
