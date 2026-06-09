package io.github.mm.uuid;

import java.util.UUID;

/**
 * Common interface for all UUID generation strategies.
 * Each implementation generates a UUID using a different version/method.
 */
public interface UUIDGenerationStrategy {

    /**
     * Generates a UUID.
     *
     * @return a newly generated UUID
     */
    UUID generateUUID();
}
