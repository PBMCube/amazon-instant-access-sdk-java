/*
 * Copyright 2017-2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.amazon.dtasdk.v3.messages;

import com.amazon.dtasdk.base.SubscriptionResponseValue;
import com.amazon.dtasdk.serializer.JacksonSerializer;
import com.amazon.dtasdk.serializer.SerializationException;
import com.amazon.dtasdk.v2.serialization.messages.GetUserIdSerializableResponseValue;
import com.amazon.dtasdk.v3.serialization.messages.GetUserIdSerializableResponse;
import com.amazon.dtasdk.v3.serialization.messages.SubscriptionGetResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InstantAccessResponseTest {
    private JacksonSerializer serializer = new JacksonSerializer();

    @Test
    public void testSerialize() throws SerializationException {
        GetUserIdSerializableResponse response = new GetUserIdSerializableResponse();
        response.setResponse(GetUserIdSerializableResponseValue.OK);
        response.setUserId("190248");

        String responseString = serializer.encode(response);

        assertEquals("{\"response\":\"OK\",\"userId\":\"190248\"}", responseString);
    }

    @Test
    public void testDeserialize() throws SerializationException {
        String str = "{\"response\":\"OK\",\"userId\":\"190248\"}";
        GetUserIdSerializableResponse response = serializer.decode(str, GetUserIdSerializableResponse.class);

        assertEquals(GetUserIdSerializableResponseValue.OK, response.getResponse());
        assertEquals("190248", response.getUserId());
    }

    @Test
    public void testDeserializeExtraFields() throws SerializationException {
        String str = "{\"response\":\"OK\",\"userId\":\"190248\",\"newField\":\"newValue\"}";
        GetUserIdSerializableResponse response = serializer.decode(str, GetUserIdSerializableResponse.class);

        assertEquals(GetUserIdSerializableResponseValue.OK, response.getResponse());
        assertEquals("190248", response.getUserId());
    }

    @Test
    public void testDeserializeSubscriptionGetResponse() throws SerializationException {
        String str = "{\"response\":\"OK\", \"numberOfUnusedLicenses\": 5, \"numberOfUsedLicenses\": 10, \"managementURL\": \"dummyUrl\"}";
        SubscriptionGetResponse response = serializer.decode(str, SubscriptionGetResponse.class);

        assertEquals(SubscriptionResponseValue.OK, response.getResponse());
        assertEquals(Integer.valueOf(5), response.getNumberOfUnusedLicenses());
        assertEquals(Integer.valueOf(10), response.getNumberOfUsedLicenses());
        assertEquals("dummyUrl", response.getManagementURL());
    }
}
