package com.ll.ch03_10.standard.dto.util;

public class Ut {
    public static class str{
        public static boolean isBlank(String str){
            return str == null || str.isBlank();
        }

        public static boolean hasLenght(String str){
            return !str.isBlank();
        }
    }
}
