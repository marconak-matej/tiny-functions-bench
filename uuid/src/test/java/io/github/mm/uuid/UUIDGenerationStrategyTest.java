package io.github.mm.uuid;

import io.github.mm.uuid.impl.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UUIDGenerationStrategyTest {

    @Test
    void testUUID1GeneratesValidUUID() {
        var strategy = new UUID1();
        var uuid = strategy.generateUUID();
        assertNotNull(uuid, "UUID should not be null");
        assertEquals(1, uuid.version(), "UUID version should be 1");
    }

    @Test
    void testUUID2GeneratesValidUUID() {
        var strategy = new UUID2();
        var uuid = strategy.generateUUID();
        assertNotNull(uuid, "UUID should not be null");
        assertEquals(2, uuid.version(), "UUID version should be 2");
    }

    @Test
    void testUUID3GeneratesValidUUID() {
        var strategy = new UUID3();
        var uuid = strategy.generateUUID();
        assertNotNull(uuid, "UUID should not be null");
        assertEquals(3, uuid.version(), "UUID version should be 3");
    }

    @Test
    void testUUID4GeneratesValidUUID() {
        var strategy = new UUID4();
        var uuid = strategy.generateUUID();
        assertNotNull(uuid, "UUID should not be null");
        assertEquals(4, uuid.version(), "UUID version should be 4");
    }

    @Test
    void testUUID5GeneratesValidUUID() {
        var strategy = new UUID5();
        var uuid = strategy.generateUUID();
        assertNotNull(uuid, "UUID should not be null");
        assertEquals(5, uuid.version(), "UUID version should be 5");
    }

    @Test
    void testUUID6GeneratesValidUUID() {
        var strategy = new UUID6();
        var uuid = strategy.generateUUID();
        assertNotNull(uuid, "UUID should not be null");
        assertEquals(6, uuid.version(), "UUID version should be 6");
    }

    @Test
    void testUUID7GeneratesValidUUID() {
        var strategy = new UUID7();
        var uuid = strategy.generateUUID();
        assertNotNull(uuid, "UUID should not be null");
        assertEquals(7, uuid.version(), "UUID version should be 7");
    }

    @Test
    void testUUID1GeneratesDifferentUUIDs() {
        var strategy = new UUID1();
        var uuid1 = strategy.generateUUID();
        var uuid2 = strategy.generateUUID();
        assertNotEquals(uuid1, uuid2, "UUID1 should generate different UUIDs on each call");
    }

    @Test
    void testUUID4GeneratesDifferentUUIDs() {
        var strategy = new UUID4();
        var uuid1 = strategy.generateUUID();
        var uuid2 = strategy.generateUUID();
        assertNotEquals(uuid1, uuid2, "UUID4 should generate different UUIDs on each call");
    }

    @Test
    void testUUID3GeneratesDeterministicUUIDs() {
        var strategy1 = new UUID3();
        var strategy2 = new UUID3();
        var uuid1 = strategy1.generateUUID();
        var uuid2 = strategy2.generateUUID();
        assertEquals(uuid1, uuid2, "UUID3 should generate the same UUID for the same namespace and name");
    }

    @Test
    void testUUID5GeneratesDeterministicUUIDs() {
        var strategy1 = new UUID5();
        var strategy2 = new UUID5();
        var uuid1 = strategy1.generateUUID();
        var uuid2 = strategy2.generateUUID();
        assertEquals(uuid1, uuid2, "UUID5 should generate the same UUID for the same namespace and name");
    }
}
