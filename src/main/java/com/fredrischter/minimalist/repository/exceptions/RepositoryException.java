package com.fredrischter.minimalist.repository.exceptions;

public class RepositoryException extends RuntimeException {

    public RepositoryException(Exception e) {
        super(e);
    }
}
