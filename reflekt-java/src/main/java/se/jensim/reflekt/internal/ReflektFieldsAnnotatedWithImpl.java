package se.jensim.reflekt.internal;

import se.jensim.reflekt.ReflektAllFields;
import se.jensim.reflekt.ReflektFieldsAnnotatedWith;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class ReflektFieldsAnnotatedWithImpl implements ReflektFieldsAnnotatedWith {

    private final Map<Boolean, Map<String, Set<Field>>> keeper = new ConcurrentHashMap<>();
    private final ReflektAllFields reflektAllFields;
    private Set<Field> defaultValue = Collections.emptySet();

    public ReflektFieldsAnnotatedWithImpl(ReflektAllFields reflektAllFields) {
        this.reflektAllFields = reflektAllFields;
    }

    @Override
    public Set<Field> getFieldsAnnotatedWith(Class<Annotation> annotation) {
        return keeper.computeIfAbsent(false, b->init())
                .getOrDefault(annotation.getCanonicalName(), defaultValue);
    }

    private Map<String, Set<Field>> init() {
        throw new UnsupportedOperationException("Not yet implemented"); // TODO
}
}
