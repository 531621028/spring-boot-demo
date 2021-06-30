package com.hkk.demo.infrastructure;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 自定义的缓存可以接收器
 *
 * @author hukangkang
 * @since 2021/6/25
 */
@Component("customerKeyGenerator")
public class CustomerKeyGenerator extends SimpleKeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return new CustomerKey(target.getClass().getSimpleName(), method.getName(), params);
    }

    private static class CustomerKey {

        private final String className;
        private final String methodName;
        private final Object[] params;

        // Effectively final, just re-calculated on deserialization
        private final transient int hashCode;


        public CustomerKey(String className, String methodName, Object... elements) {
            Assert.notNull(elements, "Elements must not be null");
            this.className = className;
            this.methodName = methodName;
            this.params = elements.clone();
            // Pre-calculate hashCode field
            this.hashCode = Arrays.deepHashCode(this.params);
        }


        @Override
        public boolean equals(@Nullable Object other) {
            return (this == other ||
                (other instanceof CustomerKey
                    && Arrays.deepEquals(this.params, ((CustomerKey) other).params))
                    && StringUtils.equals(this.className, ((CustomerKey) other).className)
                    && StringUtils.equals(this.methodName, ((CustomerKey) other).methodName));
        }

        @Override
        public final int hashCode() {
            return this.hashCode;
        }

        @Override
        public String toString() {
            return className + methodName + " [" + org.springframework.util.StringUtils.arrayToCommaDelimitedString(this.params) + "]";
        }
    }
}
