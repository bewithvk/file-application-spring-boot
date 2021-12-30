package com.ikea.filehandling;

import java.util.*;
import java.util.stream.Collectors;

public class FileUtil {

    public Map<String, Integer> characterCount(String inputString)
    {
        // Declaring the String
        String str = inputString;
        Map<String, Integer> hashMap = new HashMap<>();

        String[] words = str.split(" ");
        for (String word : words) {
            Integer integer = hashMap.get(word);
            if (integer == null)
                hashMap.put(word, 1);
            else {
                hashMap.put(word, integer + 1);
            }
        }
        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
        hashMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

        Map<String, Integer> res = reverseSortedMap.entrySet().stream()
                .limit(10)
                .collect(Collectors.toMap(Map.Entry<String, Integer>::getKey, Map.Entry<String, Integer>::getValue));

        return res;
    }

    public FileUtilLongest longestWords(String inputString)
    {
        String m="",q="";
        int lar=0;
        inputString = inputString+" ";
        String firstLong = "";
        String secondLong = "";
        for(int i=1;i<=2;i++)
        {
            for(int k=0;k<inputString.length();k++)
            {
                char ch=inputString.charAt(k);
                if(ch!=' ')
                    m=m+ch;
                else
                {
                    if(m.length()>lar)
                    {
                        q=m;
                        lar=q.length();
                    }
                    m="";
                }
            }
            if(i==1)
            {
                firstLong = q;
                inputString=inputString.replace(q," ");
                lar=0;
            }
        }
        secondLong = q;
        return new FileUtilLongest(firstLong, secondLong);
    }
}
