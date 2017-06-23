# Find Similar Strings

Find Similar Strings is a small Java application which finds similar documents using substring similarity. The application uses a modified version of [abahgat’s generalized suffix tree implementation](https://github.com/abahgat/suffixtree) as the underlying data structure.

The intended usage of this application is to find documents similar to a given input string in a list of documents. We define the similarity between two documents s1 and s2 with the following equation: 2* |longest common substring (s1, s2) | / (|s1| + |s2|). 

# Usage  

When using the application, the user inserts to the suffix tree the list of documents which he/she wants to search for similarity. Then, the user gives a document with a threshold and the algorithm gives the indexes of documents whose similarity with the given document is above the threshold.

Sample usage:

```java
        GeneralizedSuffixTree in = new GeneralizedSuffixTree();
        String[] words = new String[]{"libertypike",
                "franklintn",
                "carothersjohnhenryhouse",
                "carothersezealhouse",
                "acrossthetauntonriverfromdightonindightonrockstatepark",
                "dightonma",
                "dightonrock",
                "bethesda"};
        for (int i = 0; i < words.length; ++i) {
            in.put(words[i], i);
        }
        
        String word = "carothersezealhouse";
        HashSet<Integer> similarWordIndexes = in.getSimilarStringIndexes(word, 0.3);
        for(Integer index: similarWordIndexes) {
          System.out.println(words[index];
        }

```
# Complexity

The space complexity of the algorithm is O(n) where n is the total number of characters in the list of documents. Time complexity of one similarity search operation is O(m^2) where m is the length of the given document.
