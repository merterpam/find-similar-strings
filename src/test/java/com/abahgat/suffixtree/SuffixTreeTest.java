/**
 * Copyright 2012 Alessandro Bahgat Shehata
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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import junit.framework.TestCase;

import static com.abahgat.suffixtree.Utils.areStringsSimilar;
import static com.abahgat.suffixtree.Utils.getSubstrings;

public class SuffixTreeTest extends TestCase {

    public static <E> void assertEmpty(Collection<E> collection) {
        assertTrue("Expected empty collection.", collection.isEmpty());
    }

    public void testBasicTreeGeneration() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();

        String word = "cacao";
        in.put(word, 0);

        /* test that every substring is contained within the tree */
        for (String s : getSubstrings(word)) {
            assertTrue(in.search(s).contains(0));
        }
        assertEmpty(in.search("caco"));
        assertEmpty(in.search("cacaoo"));
        assertEmpty(in.search("ccacao"));

        in = new GeneralizedSuffixTree();
        word = "bookkeeper";
        in.put(word, 0);
        for (String s : getSubstrings(word)) {
            assertTrue(in.search(s).contains(0));
        }
        assertEmpty(in.search("books"));
        assertEmpty(in.search("boke"));
        assertEmpty(in.search("ookepr"));
    }

    public void testWeirdword() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();

        String word = "cacacato";
        in.put(word, 0);

        /* test that every substring is contained within the tree */
        for (String s : getSubstrings(word)) {
            assertTrue(in.search(s).contains(0));
        }
    }

    public void testDouble() {
        // test whether the tree can handle repetitions
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();
        String word = "cacao";
        in.put(word, 0);
        in.put(word, 1);

        for (String s : getSubstrings(word)) {
            assertTrue(in.search(s).contains(0));
            assertTrue(in.search(s).contains(1));
        }
    }

    public void testBananaAddition() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();
        String[] words = new String[]{"banana", "bano", "ba"};
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i);

            for (String s : getSubstrings(words[i])) {
                Collection<Integer> result = in.search(s);
                assertNotNull("result null for string " + s + " after adding " + words[i], result);
                assertTrue("substring " + s + " not found after adding " + words[i], result.contains(i));
            }

        }

        // verify post-addition
        for (int i = 0; i < words.length; ++i) {
            for (String s : getSubstrings(words[i])) {
                assertTrue(in.search(s).contains(i));
            }
        }

        // add again, to see if it's stable
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i + words.length);

            for (String s : getSubstrings(words[i])) {
                assertTrue(in.search(s).contains(i + words.length));
            }
        }

    }

    public void testAddition() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();
        String[] words = new String[]{"cacaor", "caricato", "cacato", "cacata", "caricata", "cacao", "banana"};
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i);

            for (String s : getSubstrings(words[i])) {
                Collection<Integer> result = in.search(s);
                assertNotNull("result null for string " + s + " after adding " + words[i], result);
                assertTrue("substring " + s + " not found after adding " + words[i], result.contains(i));
            }
        }
        // verify post-addition
        for (int i = 0; i < words.length; ++i) {
            for (String s : getSubstrings(words[i])) {
                Collection<Integer> result = in.search(s);
                assertNotNull("result null for string " + s + " after adding " + words[i], result);
                assertTrue("substring " + s + " not found after adding " + words[i], result.contains(i));
            }
        }

        // add again, to see if it's stable
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i + words.length);

            for (String s : getSubstrings(words[i])) {
                assertTrue(in.search(s).contains(i + words.length));
            }
        }

        assertEmpty(in.search("aoca"));
    }

    public void testSampleAddition() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();
        String[] words = new String[]{"libertypike",
                "franklintn",
                "carothersjohnhenryhouse",
                "carothersezealhouse",
                "acrossthetauntonriverfromdightonindightonrockstatepark",
                "dightonma",
                "dightonrock",
                "6mineoflowgaponlowgapfork",
                "lowgapky",
                "lemasterjohnjandellenhouse",
                "lemasterhouse",
                "70wilburblvd",
                "poughkeepsieny",
                "freerhouse",
                "701laurelst",
                "conwaysc",
                "hollidayjwjrhouse",
                "mainandappletonsts",
                "menomoneefallswi",
                "mainstreethistoricdistrict",
                "addressrestricted",
                "brownsmillsnj",
                "hanoverfurnace",
                "hanoverbogironfurnace",
                "sofsavannahatfergusonaveandbethesdard",
                "savannahga",
                "bethesdahomeforboys",
                "bethesda"};
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i);

            for (String s : getSubstrings(words[i])) {
                Collection<Integer> result = in.search(s);
                assertNotNull("result null for string " + s + " after adding " + words[i], result);
                assertTrue("substring " + s + " not found after adding " + words[i], result.contains(i));
            }


        }
        // verify post-addition
        for (int i = 0; i < words.length; ++i) {
            for (String s : getSubstrings(words[i])) {
                assertTrue(in.search(s).contains(i));
            }
        }

        // add again, to see if it's stable
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i + words.length);

            for (String s : getSubstrings(words[i])) {
                assertTrue(in.search(s).contains(i + words.length));
            }
        }

        assertEmpty(in.search("aoca"));
    }


    /* testing a test method :) */
    public void testGetSubstrings() {
        Collection<String> exp = new HashSet<String>();
        exp.addAll(Arrays.asList(new String[]{"w", "r", "d", "wr", "rd", "wrd"}));
        Collection<String> ret = getSubstrings("wrd");
        assertTrue(ret.equals(exp));
    }


    public void testPopulateIndices() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();
        String[] words = new String[]{"libertypike",
                "franklintn",
                "carothersjohnhenryhouse",
                "carothersezealhouse",
                "acrossthetauntonriverfromdightonindightonrockstatepark",
                "dightonma",
                "dightonrock",
                "6mineoflowgaponlowgapfork",
                "lowgapky",
                "lemasterjohnjandellenhouse",
                "lemasterhouse",
                "70wilburblvd",
                "poughkeepsieny",
                "freerhouse",
                "701laurelst",
                "conwaysc",
                "hollidayjwjrhouse",
                "mainandappletonsts",
                "menomoneefallswi",
                "mainstreethistoricdistrict",
                "addressrestricted",
                "brownsmillsnj",
                "hanoverfurnace",
                "hanoverbogironfurnace",
                "sofsavannahatfergusonaveandbethesdard",
                "savannahga",
                "bethesdahomeforboys",
                "bethesda"};
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i);
        }

        in.populateIndices();
        for (Node node : in.getNodes()) {
            HashSet<Integer> indexSet = new HashSet<Integer>();
            for (int index : node.getIndexSet())
                indexSet.add(index);

            HashSet<Integer> fetchedIndexSet = new HashSet<>();
            fetchedIndexSet.addAll(node.fetchIndexSet());

            assertEquals(node.getIndexSet().length, indexSet.size());
            assertEquals(fetchedIndexSet, indexSet);
        }
    }

    public void testNearestStringIds() {
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();
        String[] words = new String[]{"libertypike",
                "franklintn",
                "carothersjohnhenryhouse",
                "carothersezealhouse",
                "acrossthetauntonriverfromdightonindightonrockstatepark",
                "dightonma",
                "dightonrock",
                "6mineoflowgaponlowgapfork",
                "lowgapky",
                "lemasterjohnjandellenhouse",
                "lemasterhouse",
                "70wilburblvd",
                "poughkeepsieny",
                "freerhouse",
                "701laurelst",
                "conwaysc",
                "hollidayjwjrhouse",
                "mainandappletonsts",
                "menomoneefallswi",
                "mainstreethistoricdistrict",
                "addressrestricted",
                "brownsmillsnj",
                "hanoverfurnace",
                "hanoverbogironfurnace",
                "sofsavannahatfergusonaveandbethesdard",
                "savannahga",
                "bethesdahomeforboys",
                "bethesda"};
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i);
        }

        in.populateIndices();

        float thresholds[] = {0.1f, 0.4f, 0.7f, 1.0f};
        for (String word : words) {
            for (float threshold : thresholds) {
                HashSet<Integer> expectedSimilarWordIndexes = new HashSet<>();
                for (int i = 0; i < words.length; i++) {
                    if (areStringsSimilar(word, words[i], threshold)) {
                        expectedSimilarWordIndexes.add(i);
                    }
                }

                HashSet<Integer> similarWordIndexes = in.getSimilarStringIndexes(word, threshold);
                assertEquals(expectedSimilarWordIndexes, similarWordIndexes);
            }
        }

    }
}
