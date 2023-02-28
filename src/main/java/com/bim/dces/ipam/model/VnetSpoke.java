package com.bim.dces.ipam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Table(name = "vnetspoke")
@Entity
public class VnetSpoke {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column
  private String name;
  @Column(name = "cidr")
  private Integer cidr;
  @Column(name = "start_ip")
  private String startIp;
  @Column(name = "end_ip")
  private String endIp;
  @Column(name = "total_vnet_ip_count")
  private Integer totalVnetIpCount;
  @Column
  private String status;
  @Column(name = "subscription_name")
  private String subscriptionName;

  @OneToMany(mappedBy = "vnetSpoke", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Subnet> subnet;

  @ManyToOne
  @JoinColumn(name = "ipamgroup_id")
  @JsonIgnore
  private IPamGroup iPamGroup;

  public VnetSpoke() {
  }

  public VnetSpoke(String name, Integer cidr, String startIp, String endIp,
      Integer totalVnetIpCount,
      String subscriptionName, String status) {
    this.name = name;
    this.cidr = cidr;
    this.startIp = startIp;
    this.endIp = endIp;
    this.totalVnetIpCount = totalVnetIpCount;
    this.subscriptionName = subscriptionName;
    this.status = status;
  }
}
