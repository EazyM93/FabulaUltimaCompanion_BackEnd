package eazym.FabulaUltimaCompanion_BackEnd.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter @Setter
public class ErrorsPayload {

    private String message;
    private Date timestamp;
    private int internalCode;

}
