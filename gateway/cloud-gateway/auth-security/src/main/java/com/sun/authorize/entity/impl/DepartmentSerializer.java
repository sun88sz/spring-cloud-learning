package com.sun.authorize.entity.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.sun.authorize.entity.IDepartment;

import java.io.IOException;

public class DepartmentSerializer extends JsonDeserializer<IDepartment> {
    @Override
    public IDepartment deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        BaseDepartment baseDepartment = new BaseDepartment();
        baseDepartment.setId(convertLong(node, "id"));
        baseDepartment.setName(convertString(node, "name"));
        return baseDepartment;
    }

    private String convertString(JsonNode node, String key) {
        JsonNode jsonNode = node.get(key);
        if (jsonNode != null) {
            return jsonNode.asText();
        }
        return null;
    }


    private Long convertLong(JsonNode node, String key) {
        JsonNode jsonNode = node.get(key);
        if (jsonNode != null) {
            return jsonNode.asLong();
        }
        return null;
    }


    private Boolean convertBoolean(JsonNode node, String key) {
        JsonNode jsonNode = node.get(key);
        if (jsonNode != null) {
            return jsonNode.asBoolean();
        }
        return null;
    }
}
