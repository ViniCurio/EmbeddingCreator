package com.exemplo.embedding.service;

import org.springframework.stereotype.Component;

@Component
public class VectorLiteralConverter {

    public String convert(float[] vector) {
        StringBuilder value = new StringBuilder("[");

        for (int index = 0; index < vector.length; index++) {
            if (index > 0) {
                value.append(',');
            }
            value.append(vector[index]);
        }

        return value.append(']').toString();
    }
}
