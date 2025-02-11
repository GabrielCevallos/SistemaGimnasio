package com.esgurg.gym.dto.validation;

import com.esgurg.gym.entity.Persona;
import com.esgurg.gym.entity.enumeration.TipoDocumento;

public class Validator {
    public static void validateDoc(String doc, TipoDocumento doctype) {
        if (doc == null) {
            throw new IllegalArgumentException("El número de documento no puede ser nulo");
        }

        if (doctype == TipoDocumento.CEDULA) {
            if(!doc.matches("\\d{10}"))
                throw new IllegalArgumentException("La cédula debe tener 10 dígitos");

        } else if (doctype == TipoDocumento.PASAPORTE) {
            if (!doc.matches("[A-Z]{2}\\d{7}"))
                throw new IllegalArgumentException("El pasaporte debe tener 2 letras y 7 dígitos");

        } else if (doctype == TipoDocumento.RUC) {
            if(!doc.matches("\\d{13}"))
                throw new IllegalArgumentException("El RUC debe tener 13 dígitos");
        }
    }

    public static void validateDoc(Persona persona) {
        final String doc = persona.getNumeroDocumento();
        final TipoDocumento doctype = persona.getTipoDocumento();
        validateDoc(doc, doctype);
    }

}
