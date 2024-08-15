package domi.back.utils;

import java.util.UUID;

public class ServicioUtils {
    public static String generarCodigo() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
