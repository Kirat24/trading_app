package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.dao.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

public class ResponseExceptionUtil {
    private static final  Logger logger=LoggerFactory.getLogger(ResponseExceptionUtil.class);

    public  static ResponseStatusException getResponseStatusException(Exception e){
        if(e instanceof IllegalAccessException) {
            logger.debug("Invalid put", e);
            return new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
         else if(e instanceof ResourceNotFoundException) {
             logger.debug("Not found",e);
             return new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());}
         else{
             logger.debug("internal error",e);
              return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                                                "please contact admin , internal server error");

            }

        }


    }

