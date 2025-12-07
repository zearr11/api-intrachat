package com.api.intrachat.utils.enums;

public enum AreaAtencion {
    ATENCION_AL_CLIENTE,
    RETENCIONES,
    VENTAS,
    SOPORTE_TECNICO,
    COBRANZAS,
    AGENDAMIENTO_DE_CITAS;

    public String toTitleCase() {
        String raw = this.name().toLowerCase().replace("_", " ");
        String[] words = raw.split(" ");
        StringBuilder sb = new StringBuilder();

        for (String w : words) {
            sb.append(Character.toUpperCase(w.charAt(0)))
                    .append(w.substring(1))
                    .append(" ");
        }

        return sb.toString().trim();
    }
}
