package junit.jupiter.log.common;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class ClassHierarchyExtractorTest {
    private final ClassHierarchyExtractor classHierarchyExtractor = new ClassHierarchyExtractor();

    @Test
    void extractParentsWithCurrentClass_for_object_class() {
        // call
        Collection<Class<?>> classes = classHierarchyExtractor.apply(Object.class);

        assertThat(classes).isEmpty();
    }

    @Test
    void extractParentsWithCurrentClass_with_parents() {
        // call
        Collection<Class<?>> classes = classHierarchyExtractor.apply(C3.class);

        assertThat(classes)
                .hasSize(5)
                .contains(I1.class, I2.class, C1.class, C2.class, C3.class);
    }

    @Test
    void extractParentsWithCurrentClass_without_parents() {
        // call
        Collection<Class<?>> classes = classHierarchyExtractor.apply(C.class);

        assertThat(classes)
                .hasSize(1)
                .contains(C.class);
    }

    interface I1 {
    }

    interface I2 {
    }

    static class C {
    }

    static class C1 implements I1, I2 {
    }

    static class C2 extends C1 {
    }

    static class C3 extends C2 implements I1 {
    }
}