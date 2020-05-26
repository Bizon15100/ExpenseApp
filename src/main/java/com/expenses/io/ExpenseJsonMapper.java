package com.expenses.io;

import com.expenses.Expense;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.Set;

public class ExpenseJsonMapper implements ExpenseMapper {
    @Override
    public Set<Expense> read(Reader reader) {
        JsonDeserializer<Expense> deserializer = new JsonDeserializer<Expense>() {
            @Override
            public Expense deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
                Iterator<String> stringIterator;

                return null;
            }
        };
        return null;
    }

    @Override
    public void write(Set<Expense> expenses, Writer writer) throws IOException {
        Expense expense;
        JsonMapper mapper = new JsonMapper();

        for (Expense record:expenses) {
            expense = record;
            mapper.getFactory().createGenerator(writer).writeRaw(expense.toString());
        }

    }


}
