/**
 * Copyright 2012 Alessandro Bahgat Shehata
 * Copyright 2017 Mert Erpam
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.abahgat.suffixtree;

import java.util.HashSet;
import java.util.Set;

public class Utils {

    /**
     * Normalize an input string
     *
     * @param in the input string to normalize
     * @return <tt>in</tt> all lower-case, without any non alphanumeric character
     */
    public static String normalize(String in) {
        StringBuilder out = new StringBuilder();
        String l = in.toLowerCase();
        for (int i = 0; i < l.length(); ++i) {
            char c = l.charAt(i);
            if (c >= 'a' && c <= 'z' || c >= '0' && c <= '9') {
                out.append(c);
            }
        }
        return out.toString();
    }

    /**
     * Computes the set of all the substrings contained within the <tt>str</tt>
     *
     * It is fairly inefficient, but it is used just in tests ;)
     * @param str the string to compute substrings of
     * @return the set of all possible substrings of str
     */
    public static Set<String> getSubstrings(String str) {
        Set<String> ret = new HashSet<String>();
        // compute all substrings
        for (int len = 1; len <= str.length(); ++len) {
            for (int start = 0; start + len <= str.length(); ++start) {
                String itstr = str.substring(start, start + len);
                ret.add(itstr);
            }
        }

        return ret;
    }

    /**
     * Returns whether the similarity of two given strings are above the threshold ratio
     * The similarity is calculated with: 2*lCSubstring(s1,s2)/ |s1| + |s2|
     *
     * @param s1 is the first string
     * @param s2 is the second string
     * @param ratio the ratio for similarity
     * @return true if the similarity s1 and s2 are above ratio, false otherwise
     */
    public static boolean areStringsSimilar(String s1, String s2, float ratio) {
        int lCSubstringLength = getLongestCommonSubstringLength(s1, s2);
        float similarity = ((float) 2 * lCSubstringLength) / (s1.length() + s2.length());
        return similarity > ratio;
    }

    private static int getLongestCommonSubstringLength(String s, String t) {
        int[][] table = new int[s.length()][t.length()];
        int longest = 0;

        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < t.length(); j++) {
                if (s.charAt(i) != t.charAt(j)) {
                    continue;
                }

                table[i][j] = (i == 0 || j == 0) ? 1
                        : 1 + table[i - 1][j - 1];
                if (table[i][j] > longest) {
                    longest = table[i][j];
                }
            }
        }
        return longest;
    }
}
