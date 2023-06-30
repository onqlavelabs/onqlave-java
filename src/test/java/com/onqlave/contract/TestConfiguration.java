package com.onqlave.contract;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Duration;

public class TestConfiguration {
    @Test
    public void TestSplitArxUrl() {

        Configuration config = new Configuration(
                new Credential("access_key", "signing_key", "secret_key"),
                new RetrySettings(3, Duration.ofSeconds(30), Duration.ofSeconds(30)),
                "https://dp0.onqlave.io/cluster--lfGOnCmR9-niDEN5aAiBG",
                false
        );

        assertEquals(config.getArxID(), "cluster--lfGOnCmR9-niDEN5aAiBG");
        assertEquals(config.getArxURL(), "https://dp0.onqlave.io");
        assertEquals(config.getCredential().getAccessKey(), "access_key");
        assertEquals(config.getCredential().getSigningKey(), "signing_key");
        assertEquals(config.getCredential().getSecretKey(), "secret_key");
    }
}
