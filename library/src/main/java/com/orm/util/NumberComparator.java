package com.orm.util;

import java.util.Comparator;

public class NumberComparator implements Comparator<Object> {

    private static char charAt(String s, int i) {
        if (i >= s.length()) {
            return '\000';
        }

        return s.charAt(i);
    }

    private int compareRight(String a, String b) {
       int result = a.compareTo(b);
        if(result < 0) return -1;
        else if(result > 0) return 1;
        else return 0;
    }

    public int compare(Object object1, Object object2) {
        String a = object1.toString();
        String b = object2.toString();

        int indexA = 0, indexB = 0;

        while (true) {
           int nza = 0, nzb = 0;

            char charA = charAt(a, indexA);
            char charB = charAt(b, indexB);

            while ((Character.isSpaceChar(charA)) || (charA == '0')) {
                nza = (charA == 0) ? nza+1 : 0;
                charA = charAt(a, ++indexA);
            }

            while ((Character.isSpaceChar(charB)) || (charB == '0')) {
                nzb = (charB == 0) ? nzb+1 : 0;
                charB = charAt(b, ++indexB);
            }
            int result;
            if ((Character.isDigit(charA)) && (Character.isDigit(charB)) &&
                    ((result = compareRight(a.substring(indexA), b.substring(indexB))) != 0)) {
                return result;
            }

            if ((charA == 0) && (charB == 0)) {
                return nza - nzb;
            }

            if (charA < charB) {
                return -1;
            }
            if (charA > charB) {
                return 1;
            }

            indexA++;
            indexB++;
        }
    }

}
