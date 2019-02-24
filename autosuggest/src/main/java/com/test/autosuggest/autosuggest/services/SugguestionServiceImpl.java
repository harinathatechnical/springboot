package com.test.autosuggest.autosuggest.services;

import com.test.autosuggest.autosuggest.exceptions.NotANumberException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class SugguestionServiceImpl implements SugguestionService {

    private static final Logger LOGGER = LogManager.getLogger(SugguestionServiceImpl.class);

    @Autowired
    TernaryServices ternaryServices;

    @ExceptionHandler(NotANumberException.class)
    public String getSuggestCities(final String prefix,final String atMost) throws NotANumberException{
        LOGGER.info("Start getSuggestCities prefix:"+prefix+" atMost:"+atMost);
        if(!StringUtils.isNumeric(atMost)){
            LOGGER.error("Given at most is not a number");
            throw new NotANumberException("Given at most "+atMost+" is not a number");
        }
        return ternaryServices.search(prefix,new Integer(atMost));
    }
}
