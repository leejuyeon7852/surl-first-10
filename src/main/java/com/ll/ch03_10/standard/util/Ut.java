package com.ll.ch03_10.standard.util;

import com.ll.ch03_10.global.app.AppConfig;
import lombok.SneakyThrows;

public class Ut {

    public static class str {
        public static boolean isBlank(String str) {
            return str == null || str.isBlank();
        }

        public static boolean hasLenght(String str) {
            return !str.isBlank();
        }
    }

    public static class json {
        @SneakyThrows
        public static String toString(Object obj) {
            return AppConfig.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        }
    }
}
