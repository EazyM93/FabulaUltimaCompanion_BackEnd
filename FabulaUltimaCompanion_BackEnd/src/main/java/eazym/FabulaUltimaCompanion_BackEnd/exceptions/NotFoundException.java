package eazym.FabulaUltimaCompanion_BackEnd.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(UUID id) {
        super(id + " not found!");
    }

}
