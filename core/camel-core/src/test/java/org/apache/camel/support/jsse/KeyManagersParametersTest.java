/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.support.jsse;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.X509KeyManager;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;

import static org.junit.jupiter.api.Assertions.*;

@Isolated("This test is regularly flaky")
public class KeyManagersParametersTest extends AbstractJsseParametersTest {

    protected KeyStoreParameters createMinimalKeyStoreParameters() {
        KeyStoreParameters ksp = new KeyStoreParameters();
        ksp.setCamelContext(new DefaultCamelContext());

        ksp.setResource("org/apache/camel/support/jsse/localhost.p12");
        ksp.setPassword("changeit");
        ksp.setType("PKCS12");

        return ksp;
    }

    protected KeyManagersParameters createMinimalKeyManagersParameters() {
        KeyManagersParameters kmp = new KeyManagersParameters();
        kmp.setCamelContext(new DefaultCamelContext());

        kmp.setKeyStore(this.createMinimalKeyStoreParameters());
        kmp.setKeyPassword("changeit");

        return kmp;
    }

    @Test
    public void testPropertyPlaceholders() throws Exception {
        CamelContext context = this.createPropertiesPlaceholderAwareContext();

        KeyStoreParameters ksp = new KeyStoreParameters();
        ksp.setCamelContext(context);

        ksp.setType("{{keyStoreParameters.type}}");
        ksp.setProvider("{{keyStoreParameters.provider}}");
        ksp.setResource("{{keyStoreParameters.resource}}");
        ksp.setPassword("{{keyStoreParameters.password}}");

        KeyManagersParameters kmp = new KeyManagersParameters();
        kmp.setCamelContext(context);
        kmp.setKeyStore(ksp);

        kmp.setKeyPassword("{{keyManagersParameters.keyPassword}}");
        kmp.setAlgorithm("{{keyManagersParameters.algorithm}}");
        kmp.setProvider("{{keyManagersParameters.provider}}");

        KeyManager[] kms = kmp.createKeyManagers();
        validateKeyManagers(kms);
    }

    @Test
    public void testCreateKeyManagers() throws Exception {
        KeyManagersParameters kmp = this.createMinimalKeyManagersParameters();

        KeyManager[] kms = kmp.createKeyManagers();
        validateKeyManagers(kms);
    }

    @Test
    public void testExplicitAlgorithm() throws Exception {
        KeyManagersParameters kmp = this.createMinimalKeyManagersParameters();
        kmp.setAlgorithm(KeyManagerFactory.getDefaultAlgorithm());

        KeyManager[] kms = kmp.createKeyManagers();
        validateKeyManagers(kms);
    }

    @Test
    public void testExplicitProvider() throws Exception {
        KeyManagersParameters kmp = this.createMinimalKeyManagersParameters();
        kmp.setProvider(KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm()).getProvider().getName());

        KeyManager[] kms = kmp.createKeyManagers();
        validateKeyManagers(kms);
    }

    @Test
    public void testInvalidPassword() throws Exception {
        KeyManagersParameters kmp = this.createMinimalKeyManagersParameters();
        kmp.setKeyPassword("");

        try {
            kmp.createKeyManagers();
            fail();
        } catch (UnrecoverableKeyException e) {
            // expected
        }
    }

    @Test
    public void testInvalidExplicitAlgorithm() throws Exception {
        KeyManagersParameters kmp = this.createMinimalKeyManagersParameters();
        kmp.setAlgorithm("dsfsdfsdfdsfdsF");

        try {
            kmp.createKeyManagers();
            fail();
        } catch (NoSuchAlgorithmException e) {
            // expected
        }
    }

    @Test
    public void testInvalidExplicitProvider() throws Exception {
        KeyManagersParameters kmp = this.createMinimalKeyManagersParameters();
        kmp.setProvider("dsfsdfsdfdsfdsF");

        try {
            kmp.createKeyManagers();
            fail();
        } catch (NoSuchProviderException e) {
            // expected
        }
    }

    @Test
    public void testAliasedKeyManager() throws Exception {
        KeyManagersParameters kmp = this.createMinimalKeyManagersParameters();

        KeyManager[] kms = kmp.createKeyManagers();
        assertEquals(1, kms.length);
        boolean b = kms[0] instanceof X509KeyManager;
        assertTrue(b);

        kms[0] = new AliasedX509ExtendedKeyManager("localhost", (X509KeyManager) kms[0]);
        AliasedX509ExtendedKeyManager km = (AliasedX509ExtendedKeyManager) kms[0];
        assertNotNull(km.getPrivateKey("localhost"));
    }

    protected void validateKeyManagers(KeyManager[] kms) {
        assertEquals(1, kms.length);
        boolean b = kms[0] instanceof X509KeyManager;
        assertTrue(b);
        X509KeyManager km = (X509KeyManager) kms[0];
        assertNotNull(km.getPrivateKey("localhost"));
    }
}
