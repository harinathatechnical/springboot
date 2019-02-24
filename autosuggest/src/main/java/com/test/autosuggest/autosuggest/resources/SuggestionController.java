package com.test.autosuggest.autosuggest.resources;

import com.test.autosuggest.autosuggest.exceptions.NotANumberException;
import com.test.autosuggest.autosuggest.services.SugguestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SuggestionController {

    @Autowired
    SugguestionService sugguestionService;

    @RequestMapping(value = "/suggest_cities" ,produces = {"text/plain", "application/json"})
    @ResponseBody
    @ExceptionHandler(NotANumberException.class)
    public String suggestCities(@RequestParam(name="start", required=true) String start,
                                  @RequestParam(name="atmost", required=false, defaultValue="5") String atmost)
    throws NotANumberException{
        return sugguestionService.getSuggestCities(start.toLowerCase(),atmost);
    }
}
