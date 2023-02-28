package com.bim.dces.ipam.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Table(name = "ipamgroup")
@Entity
public class IPamGroup {

  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String groupName;

  @OneToMany(mappedBy = "iPamGroup", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<VnetSpoke> vnetSpoke;

  public IPamGroup() {
  }

  public IPamGroup(String groupName) {
    this.groupName = groupName;
  }
}
