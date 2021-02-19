package com.hnb.models;

import javax.persistence.*;

@Entity
@Table(name = "tecajevi")
public class Tecajevi{

    @Column(name = "broj_tecajnice")
    private String broj_tecajnice;

    @Column(name = "datum_primjene")
    private String datum_primjene;

    @Column(name = "drzava")
    private String drzava;

    @Column(name = "drzava_iso")
    private String drzava_iso;

    @Column(name = "sifra_valute")
    private String sifra_valute;

    @Column(name = "valuta")
    private String valuta;

    @Column(name = "jedinica")
    private int jedinica;

    @Column(name = "kupovni_tecaj")
    private String kupovni_tecaj;

    @Column(name = "srednji_tecaj")
    private String srednji_tecaj;

    @Column(name = "prodajni_tecaj")
    private String prodajni_tecaj;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Tecajevi() {
    }

    public Tecajevi(String broj_tecajnice, String datum_primjene, String drzava, String drzava_iso, String sifra_valute, String valuta, int jedinica, String kupovni_tecaj, String srednji_tecaj, String prodajni_tecaj) {
        this.broj_tecajnice = broj_tecajnice;
        this.datum_primjene = datum_primjene;
        this.drzava = drzava;
        this.drzava_iso = drzava_iso;
        this.sifra_valute = sifra_valute;
        this.valuta = valuta;
        this.jedinica = jedinica;
        this.kupovni_tecaj = kupovni_tecaj;
        this.srednji_tecaj = srednji_tecaj;
        this.prodajni_tecaj = prodajni_tecaj;
    }



    public String getBroj_tecajnice() {
        return broj_tecajnice;
    }

    public void setBroj_tecajnice(String broj_tecajnice) {
        this.broj_tecajnice = broj_tecajnice;
    }

    public String getDatum_primjene() {
        return datum_primjene;
    }

    public void setDatum_primjene(String datum_primjene) {
        this.datum_primjene = datum_primjene;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public String getDrzava_iso() {
        return drzava_iso;
    }

    public void setDrzava_iso(String drzava_iso) {
        this.drzava_iso = drzava_iso;
    }

    public String getSifra_valute() {
        return sifra_valute;
    }

    public void setSifra_valute(String sifra_valute) {
        this.sifra_valute = sifra_valute;
    }

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public int getJedinica() {
        return jedinica;
    }

    public void setJedinica(int jedinica) {
        this.jedinica = jedinica;
    }

    public String getKupovni_tecaj() {
        return kupovni_tecaj;
    }

    public void setKupovni_tecaj(String kupovni_tecaj) {
        this.kupovni_tecaj = kupovni_tecaj;
    }

    public String getSrednji_tecaj() {
        return srednji_tecaj;
    }

    public void setSrednji_tecaj(String srednji_tecaj) {
        this.srednji_tecaj = srednji_tecaj;
    }

    public String getProdajni_tecaj() {
        return prodajni_tecaj;
    }

    public void setProdajni_tecaj(String prodajni_tecaj) {
        this.prodajni_tecaj = prodajni_tecaj;
    }

    @Override
    public String toString() {
        return "Tecajevi{" +
                "broj_tecajnice='" + broj_tecajnice + '\'' +
                ", datum_primjene='" + datum_primjene + '\'' +
                ", drzava='" + drzava + '\'' +
                ", drzava_iso='" + drzava_iso + '\'' +
                ", sifra_valute='" + sifra_valute + '\'' +
                ", valuta='" + valuta + '\'' +
                ", jedinica=" + jedinica +
                ", kupovni_tecaj='" + kupovni_tecaj + '\'' +
                ", srednji_tecaj='" + srednji_tecaj + '\'' +
                ", prodajni_tecaj='" + prodajni_tecaj + '\'' +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}