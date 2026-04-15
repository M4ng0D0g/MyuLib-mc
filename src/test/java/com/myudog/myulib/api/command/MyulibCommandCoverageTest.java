package com.myudog.myulib.api.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class MyulibCommandCoverageTest {

    @BeforeEach
    void reset() {
        CommandRegistry.clear();
        AccessCommandService.registerDefaults();
    }

    @Test
    void allSystemCommandMirrorsUseMyulibPrefix() {
        Map<String, ?> commands = CommandRegistry.snapshot();
        assertTrue(commands.containsKey("myulib:save"), "Missing command mirror: myulib:save");
        assertTrue(commands.containsKey("myulib:status"), "Missing command mirror: myulib:status");
        assertTrue(commands.containsKey("myulib:field:count"), "Missing command mirror: myulib:field:count");
        assertTrue(commands.containsKey("myulib:permission:save"), "Missing command mirror: myulib:permission:save");
        assertTrue(commands.containsKey("myulib:rolegroup:count"), "Missing command mirror: myulib:rolegroup:count");
        assertTrue(commands.containsKey("myulib:team:count"), "Missing command mirror: myulib:team:count");
        assertTrue(commands.containsKey("myulib:game:count"), "Missing command mirror: myulib:game:count");
        assertTrue(commands.containsKey("myulib:timer:count"), "Missing command mirror: myulib:timer:count");
        assertTrue(commands.containsKey("myulib:camera:status"), "Missing command mirror: myulib:camera:status");
        assertTrue(commands.containsKey("myulib:control:status"), "Missing command mirror: myulib:control:status");
    }
}

