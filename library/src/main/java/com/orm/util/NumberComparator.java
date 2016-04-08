package com.orm.util;

import java.util.Comparator;

import static java.lang.Character.isDigit;
import static java.lang.Character.isSpaceChar;

public class NumberComparator implements Comparator<Object> {

    protected static char charAt(String s, int i) {
        if (i >= s.length()) {
            return '\000';
        }

        return s.charAt(i);
    }

    protected int compareRight(String a, String b) {
        int bias = 0;
        int ia = 0;
        int ib = 0;
        while (true) {
            char ca = charAt(a, ia);
            char cb = charAt(b, ib);

            if ((!isDigit(ca)) && (!isDigit(cb))) {
                return bias;
            }
            if (!isDigit(ca)) {
                return -1;
            }
            if (!isDigit(cb)) {
                return 1;
            }
            if (ca < cb) {
                if (bias == 0) {
                    bias = -1;
                }
            } else if (ca > cb) {
                if (bias == 0)
                    bias = 1;
            } else if ((ca == 0) && (cb == 0))
                return bias;
            ia++;
            ib++;
        }
    }

    public int compare(Object o1, Object o2) {
        String a = o1.toString();
        String b = o2.toString();

        int ia = 0;
        int ib = 0;
        int nza;
        int nzb;
        while (true) {
            nza = nzb = 0;

            char ca = charAt(a, ia);
            char cb = charAt(b, ib);

            while ((isSpaceChar(ca)) || (ca == '0')) {
                if (ca == '0') {
                    nza++;
                } else {
                    nza = 0;
                }

                ca = charAt(a, ++ia);
            }

            while ((isSpaceChar(cb)) || (cb == '0')) {
                if (cb == '0') {
                    nzb++;
                } else {
                    nzb = 0;
                }

                cb = charAt(b, ++ib);
            }
            int result;
            if ((isDigit(ca)) && (isDigit(cb)) &&
                    ((result = compareRight(a.substring(ia), b.substring(ib))) != 0)) {
                return result;
            }

            if ((ca == 0) && (cb == 0)) {
                return nza - nzb;
            }

            if (ca < cb) {
                return -1;
            }
            if (ca > cb) {
                return 1;
            }

            ia++;
            ib++;
        }
    }

}
