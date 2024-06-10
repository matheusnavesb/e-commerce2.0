package org.acme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "telephone")
public class Telephone extends DefaultEntity{
    
    private String codeCountry;
    private String codeState;
    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Telephone() {
    }

    public Telephone(String codeCountry, String codeState, String number, User user) {
        this.codeCountry = codeCountry;
        this.codeState = codeState;
        this.number = number;
        this.user = user;
    }

    public String getCodeCountry() {
        return codeCountry;
    }

    public void setCodeCountry(String codeCountry) {
        this.codeCountry = codeCountry;
    }

    public String getCodeState() {
        return codeState;
    }

    public void setCodeState(String codestate) {
        this.codeState = codestate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
