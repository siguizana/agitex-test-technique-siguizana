package org.agitex.test.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@XmlRootElement(name = "Clients")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClientListDto {
    @XmlElement(name = "Client")
    private List<ClientDto> clients;
}
