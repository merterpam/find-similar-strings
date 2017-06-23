package com.abahgat.suffixtree;
/**
 * Copyright 2012 Alessandro Bahgat Shehata
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * A specialized implementation of Map that uses native char types and sorted
 * arrays to keep minimize the memory footprint.
 * Implements only the operations that are needed within the suffix tree context.
 */
class EdgeBag implements Map<Character, Edge>, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private char[] chars;
    private Edge[] values;
    private static final int BSEARCH_THRESHOLD = 6;

    public Edge put(Character character, Edge e) {
        char c = character.charValue();
        
        if (chars == null) {
            chars = new char[0];
            values = new Edge[0];
        }
        int idx = search(c);
        Edge previous = null;

        if (idx < 0) {
            int currsize = chars.length;
            char[] copy = new char[currsize + 1];
            System.arraycopy(chars, 0, copy, 0, currsize);
            chars = copy;
            Edge[] copy1 = new Edge[currsize + 1];
            System.arraycopy(values, 0, copy1, 0, currsize);
            values = copy1;
            chars[currsize] = c;
            values[currsize] = e;
            currsize++;
            if (currsize > BSEARCH_THRESHOLD) {
                sortArrays();
            }
        } else {
            previous = values[idx];
            values[idx] = e;
        }
        return previous;
    }
    
     
    public Edge get(Object maybeCharacter) {
        return get(((Character) maybeCharacter).charValue());  // throws if cast fails.
    }

    public Edge get(char c) {
        
        int idx = search(c);
        if (idx < 0) {
            return null;
        }
        return values[idx];
    }

    private int search(char c) {
        if (chars == null)
            return -1;
        
        if (chars.length > BSEARCH_THRESHOLD) {
            return Arrays.binarySearch(chars, c);
        }

        for (int i = 0; i < chars.length; i++) {
            if (c == chars[i]) {
                return i;
            }
        }
        return -1;
    }

     
    public Collection<Edge> values() {
        return Arrays.asList(values == null ? new Edge[0] : values);
    }
    
    /**
     * A trivial implementation of sort, used to sort chars[] and values[] according to the data in chars.
     * 
     * It was preferred to faster sorts (like qsort) because of the small sizes (<=36) of the collections involved.
     */
    private void sortArrays() {
        for (int i = 0; i < chars.length; i++) {
         for (int j = i; j > 0; j--) {
            if (chars[j-1] > chars[j]) {
               char swap = chars[j];
               chars[j] = chars[j-1];
               chars[j-1] = swap;

               Edge swapEdge = values[j];
               values[j] = values[j-1];
               values[j-1] = swapEdge;
            }
         }
      }
    }
    
     
    public boolean isEmpty() {
        return chars == null || chars.length == 0;
    }
    
     
    public int size() {
        return chars == null ? 0 : chars.length;
    }
    
     
    public Set<Entry<Character, Edge>> entrySet() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
     
    public Set<Character> keySet() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
     
    public void clear() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
     
    public void putAll(Map<? extends Character, ? extends Edge> m) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
     
    public Edge remove(Object key) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
     
    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException("Not implemented");
    }
    
     
    public boolean containsValue(Object key) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
