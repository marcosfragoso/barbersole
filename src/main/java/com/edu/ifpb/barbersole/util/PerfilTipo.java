package com.edu.ifpb.barbersole.util;

public enum PerfilTipo {
    BARBEIRO(1, "BARBEIRO"), CLIENTE(2, "CLIENTE");

    private long cod;
    private String desc;

    private PerfilTipo(long cod, String desc) {
        this.cod = cod;
        this.desc = desc;
    }

    public long getCod() {
        return cod;
    }

    public String getDesc() {
        return desc;
    }
}
