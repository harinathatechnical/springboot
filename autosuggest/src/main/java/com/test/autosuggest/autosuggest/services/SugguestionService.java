package com.test.autosuggest.autosuggest.services;

import com.test.autosuggest.autosuggest.exceptions.NotANumberException;

public interface SugguestionService {

    String getSuggestCities(final String prefix,final String atMost) throws NotANumberException;
}
