package uk.co.flax.luwak.termextractor;

import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.intervals.FieldedConjunctionQuery;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Copyright (c) 2013 Lemur Consulting Ltd.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class FieldedConjunctionQueryExtractor extends Extractor<FieldedConjunctionQuery> {

    public FieldedConjunctionQueryExtractor() {
        super(FieldedConjunctionQuery.class);
    }

    @Override
    public void extract(FieldedConjunctionQuery query, List<QueryTerm> terms,
                        QueryTermExtractor queryTermExtractor) {
        try {
            Field field = query.getClass().getDeclaredField("bq");
            field.setAccessible(true);
            BooleanQuery bq = (BooleanQuery) field.get(query);
            queryTermExtractor.extractTerms(bq, terms);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}