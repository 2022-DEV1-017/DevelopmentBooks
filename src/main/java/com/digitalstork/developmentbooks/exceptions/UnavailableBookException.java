package com.digitalstork.developmentbooks.exceptions;

public class UnavailableBookException extends RuntimeException {

    public UnavailableBookException(String bookCode) {
        super("The following book is not available : " + bookCode);
    }

}
