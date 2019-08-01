package io.github.reflekt.internal;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;

abstract class ClassFilePattern {

    private static final Predicate<String> PACKAGE_DIR_MATCHER = Pattern.compile("^[a-z][a-z0-9]*$").asPredicate();

    private ClassFilePattern() {
        // Im very private
    }

    static boolean isClassFile(String fileName) {
        if (fileName.endsWith(".class")) {
            fileName = fileName.substring(0, fileName.length() - 6);
            if(fileName.startsWith("/") || fileName.startsWith("\\")){
                fileName = fileName.substring(1);
            }
            String[] split = fileName.split("[\\\\/]");
            if (split.length > 0 && filePartIsClassLike(split[split.length - 1])) {
                if (split.length > 1) {
                    return Arrays.stream(split)
                            .limit(split.length - 1L)

                            .allMatch(PACKAGE_DIR_MATCHER);
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean filePartIsClassLike(String fileName) {
        return Arrays.stream(fileName.split("[$]"))
                .filter(it -> !it.isEmpty())
                .allMatch(ClassFilePattern::isAllowedClassName);
    }

    private static boolean isAllowedClassName(String name) {
        return Character.isJavaIdentifierStart(name.charAt(0))
                && name.chars().skip(1)
                .allMatch(Character::isJavaIdentifierPart);
    }
}
