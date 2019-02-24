package com.test.autosuggest.autosuggest.services;

import com.test.autosuggest.autosuggest.model.TernaryNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;


@Component
public class TernaryServicesImpl implements TernaryServices,InitializingBean {

    private static final Logger LOGGER = LogManager.getLogger(TernaryServicesImpl.class);

    private TernaryNode root;

    public void afterPropertiesSet() throws Exception {
        Resource resource  = new ClassPathResource("/static/Sample.txt");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"))){
            String line;
            while ((line = br.readLine()) != null) {
                root = insert(root, line.toLowerCase().toCharArray(), 0);
            }
        }
    }
    private TernaryNode insert(TernaryNode root, char[] word, int i) {
        if (root == null) {
            root = new TernaryNode(word[i]);
        }
        if (word[i] < root.data)
            root.left = insert(root.left, word, i);
        else if (word[i] > root.data)
            root.right = insert(root.right, word, i);
        else
        {
            if (i + 1 < word.length)
                root.middle = insert(root.middle, word, i + 1);
            else
                root.isEnd = true;
        }
        return root;
    }

    public String search(String word,int count) {
        LOGGER.info("Start search word: "+word+" count: "+count);
        StringBuilder sb = new StringBuilder();

        TernaryNode prefixRoot = crawlToPrefixLastNode(root, word.toCharArray(), 0);
        findAllSuggestions(prefixRoot, "", sb, word);
        if (sb.length() < 1) {
            return "No Matching String Found";
        }
        StringBuilder res = new StringBuilder();
        String[] array= sb.toString().split("_separator_ ");
        int i=0;
        while(i<count){
            res.append(array[i]+"\n");
            i++;
        }

        return res.toString();
    }

    private TernaryNode crawlToPrefixLastNode(TernaryNode tNode, char[] word, int ptr)
    {
        if (tNode == null)
            return null;
        if (word[ptr] < tNode.data)
            return crawlToPrefixLastNode(tNode.left, word, ptr);
        else if (word[ptr] > tNode.data)
            return crawlToPrefixLastNode(tNode.right, word, ptr);
        else {
            if (ptr == word.length - 1)
                return tNode;
            else
                return crawlToPrefixLastNode(tNode.middle, word, ptr + 1);
        }
    }

    private void findAllSuggestions(TernaryNode tNode, String str, StringBuilder sb, String word)
    {
        if (tNode != null) {
            findAllSuggestions(tNode.left, str, sb, word);
            str = str + tNode.data;
            if (tNode.isEnd ) {
                sb.append(word + str.substring(1) + "\n _separator_ ");
            }
            findAllSuggestions(tNode.middle, str, sb, word);
            str = str.substring(0, str.length() - 1);
            findAllSuggestions(tNode.right, str, sb, word);
        }
    }
}
