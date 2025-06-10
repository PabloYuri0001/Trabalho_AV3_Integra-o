package com.hostpet.hostpet.dtos;

public class BaiaStatusDTO {
    private long totalLivres;
    private long totalOcupadas;

    public BaiaStatusDTO(long totalLivres, long totalOcupadas) {
        this.totalLivres = totalLivres;
        this.totalOcupadas = totalOcupadas;
    }

    public long getTotalLivres() {
        return totalLivres;
    }

    public long getTotalOcupadas() {
        return totalOcupadas;
    }
}