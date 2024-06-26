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
package org.apache.camel.component.salesforce;

import java.util.Arrays;

import org.apache.camel.component.salesforce.api.dto.composite.SObjectTree;
import org.apache.camel.component.salesforce.dto.generated.Account;
import org.apache.camel.component.salesforce.dto.generated.Account_IndustryEnum;
import org.apache.camel.component.salesforce.dto.generated.Asset;
import org.apache.camel.component.salesforce.dto.generated.Contact;
import org.apache.camel.test.junit5.params.Parameter;
import org.apache.camel.test.junit5.params.Parameterized;
import org.apache.camel.test.junit5.params.Parameters;
import org.apache.camel.test.junit5.params.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Parameterized
public class CompositeApiTreeManualIT extends AbstractSalesforceTestBase {

    @Parameter
    private String format;

    @Test
    public void shouldSubmitTreeUsingCompositeApi() {
        final Account simpleAccount = new Account();

        final Contact smith = new Contact();

        final Contact evans = new Contact();

        final Account simpleAccount2 = new Account();

        simpleAccount.setName("SampleAccount");
        simpleAccount.setPhone("1234567890");
        simpleAccount.setWebsite("www.salesforce.com");
        simpleAccount.setNumberOfEmployees(100);
        simpleAccount.setIndustry(Account_IndustryEnum.BANKING);

        smith.setLastName("Smith");
        smith.setTitle("President");
        smith.setEmail("sample@salesforce.com");

        evans.setLastName("Evans");
        evans.setTitle("Vice President");
        evans.setEmail("sample@salesforce.com");

        simpleAccount2.setName("SampleAccount2");
        simpleAccount2.setPhone("1234567890");
        simpleAccount2.setWebsite("www.salesforce2.com");
        simpleAccount2.setNumberOfEmployees(100);
        simpleAccount2.setIndustry(Account_IndustryEnum.BANKING);

        final SObjectTree tree = new SObjectTree();
        tree.addObject(simpleAccount).addChildren("Contacts", smith, evans);
        tree.addObject(simpleAccount2);

        final Account simpleAccount3 = new Account();
        simpleAccount3.setName("SimpleAccount3");

        final Contact contact = new Contact();
        contact.setFirstName("Simple");
        contact.setLastName("Contact");

        final Asset asset = new Asset();
        asset.setName("Asset Name");
        asset.setDescription("Simple asset");

        tree.addObject(simpleAccount3).addChild("Contacts", contact).addChild("Assets", asset);

        final SObjectTree response
                = template.requestBody("salesforce:composite-tree?format=" + format, tree, SObjectTree.class);

        assertNotNull(response, "Response should be provided");

        assertNotNull(simpleAccount.getId(), "First account should have Id set");
        assertNotNull(smith.getId(), "President of the first account should have Id set");
        assertNotNull(evans.getId(), "Vice president of the first account should have Id set");

        assertNotNull(simpleAccount2.getId(), "Second account should have Id set");

        assertNotNull(simpleAccount3.getId(), "Third account should have Id set");
        assertNotNull(contact.getId(), "Simple contact on third account should have Id set");
        assertNotNull(asset.getId(), "Simple asset on the contact of the third account should have Id set");
    }

    @Parameters(name = "format = {0}")
    public static Iterable<String> formats() {
        return Arrays.asList("JSON", "XML");
    }
}
