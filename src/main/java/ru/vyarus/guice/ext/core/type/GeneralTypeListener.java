package ru.vyarus.guice.ext.core.type;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import ru.vyarus.guice.ext.core.util.Utils;

/**
 * Generic type listener for bean types (exact class, by base class or beans annotating interface).
 *
 * @author Vyacheslav Rusakov
 * @since 30.06.2014
 */
public class GeneralTypeListener<T> implements TypeListener {

    private Class<T> typeClass;
    private TypePostProcessor<T> postProcessor;

    public GeneralTypeListener(final Class<T> typeClass, final TypePostProcessor<T> postProcessor) {
        this.typeClass = typeClass;
        this.postProcessor = postProcessor;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <I> void hear(final TypeLiteral<I> type, final TypeEncounter<I> encounter) {
        final Class<? super I> actualType = type.getRawType();
        if (!Utils.isPackageValid(actualType)) {
            return;
        }
        if (checkType(actualType)) {
            encounter.register(new InjectionListener<I>() {
                @Override
                public void afterInjection(I injectee) {
                    try {
                        postProcessor.process((T) injectee);
                    } catch (Exception ex) {
                        throw new IllegalStateException(
                                String.format("Failed to process type %s of class %s",
                                        typeClass.getSimpleName(), injectee.getClass().getSimpleName()), ex);
                    }
                }
            });
        }
    }

    /**
     * @param check class to check
     * @return true if type is assignable from required type or implements interface (directly or on any hierarchy level), false otherwise
     */
    private boolean checkType(final Class<?> check) {
        if (check.isAssignableFrom(typeClass)) {
            return true;
        }
        if (typeClass.isInterface()) {
            Class<?> investigating = check;
            while (investigating != null && !investigating.equals(Object.class)) {
                for (Class<?> test : investigating.getInterfaces()) {
                    if (test.equals(typeClass)) {
                        return true;
                    }
                }
                investigating = investigating.getSuperclass();
            }
        }
        return false;
    }
}