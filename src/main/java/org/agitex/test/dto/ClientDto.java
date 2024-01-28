package org.agitex.test.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "Client")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClientDto {
    @XmlElement(name = "nom")
    private String nom;
    @XmlElement(name = "prenom")
    private String prenom;
    @XmlElement(name = "age")
    private Integer age;
    @XmlElement(name = "salaire")
    private Double salaire;
    @XmlElement(name = "profession")
    private String profession;
}
