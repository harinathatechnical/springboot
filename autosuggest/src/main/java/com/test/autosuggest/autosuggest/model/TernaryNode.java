package com.test.autosuggest.autosuggest.model;

public class TernaryNode {

    public char data;
    public boolean isEnd;
    public TernaryNode left, middle, right;

    public TernaryNode(char c) {
        super();
        this.data = c;
    }
}
