package com.bim.dces.ipam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Table(name = "subnet")
@Entity
public class Subnet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column
  private String type;

  @Column
  private String name;
  @Column(name = "cidr_subnet")
  private Integer cidrSubnet;
  @Column(name = "start_ip")
  private String startIp;
  @Column(name = "end_ip")
  private String endIp;
  @Column(name = "total_subnet_ip_count")
  private Integer totalSubnetIpCount;
  @Column
  private String status;

  @ManyToOne
  @JoinColumn(name = "vnet_spoke_id")
  @JsonIgnore
  private VnetSpoke vnetSpoke;

  public Subnet() {
  }

  public Subnet(String type, String name, Integer cidrSubnet, String startIp, String endIp,
      Integer totalSubnetIpCount, String status) {
    this.type = type;
    this.name = name;
    this.cidrSubnet = cidrSubnet;
    this.startIp = startIp;
    this.endIp = endIp;
    this.totalSubnetIpCount = totalSubnetIpCount;
    this.status = status;
  }
}
