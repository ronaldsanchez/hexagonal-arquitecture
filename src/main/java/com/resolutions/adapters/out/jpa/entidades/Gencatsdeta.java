package com.resolutions.adapters.out.jpa.entidades;

import jakarta.persistence.*;

@Entity
public class Gencatsdeta {
    @EmbeddedId
    private GencatsdetaPK gencatsdetaPK;
    private String corDesc;
    @Embedded
    private CamposAudit camposAudit;

    @ManyToOne
    @MapsId("codCat")
    @JoinColumn(name = "codCat")
    private Gencatsenca gencatsenca;


    public GencatsdetaPK getGencatsdetaPK() {
        return gencatsdetaPK;
    }

    public void setGencatsdetaPK(GencatsdetaPK gencatsdetaPK) {
        this.gencatsdetaPK = gencatsdetaPK;
    }

    public String getCorDesc() {
        return corDesc;
    }

    public void setCorDesc(String corDesc) {
        this.corDesc = corDesc;
    }

    public CamposAudit getCamposAudit() {
        return camposAudit;
    }

    public void setCamposAudit(CamposAudit camposAudit) {
        this.camposAudit = camposAudit;
    }

    public Gencatsenca getGencatsenca() {
        return gencatsenca;
    }

    public void setGencatsenca(Gencatsenca gencatsenca) {
        this.gencatsenca = gencatsenca;
    }
}