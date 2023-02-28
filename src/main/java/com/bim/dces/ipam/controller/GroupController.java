package com.bim.dces.ipam.controller;

import com.bim.dces.ipam.model.IPamGroup;
import com.bim.dces.ipam.model.Subnet;
import com.bim.dces.ipam.model.VnetSpoke;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface GroupController {

  ResponseEntity getIpamGroups();

  ResponseEntity getIpamGroupByName(String groupName);

  ResponseEntity getVnetDetailsBySpokeName(String groupName, String vnetName);

  ResponseEntity getVnetDetailsBySubscriptionName(String groupName, String subscriptionName);

  ResponseEntity getSubnetDetailsByType(String groupName, String vnetName, String subNetTypeName);

  ResponseEntity addNewIpamGroup(IPamGroup iPamGroup);

  ResponseEntity addNewIpamGroupOnly(String groupName);

  ResponseEntity addVnetSpokeToGroup(String groupName, List<VnetSpoke> vnetSpoke);

  ResponseEntity addSubnetToVnet(String groupName, String vnetSpokeName, List<Subnet> subnet);

  ResponseEntity updateGroupName(Long id, String groupName);

  ResponseEntity updateVnetStatusByName(String groupName, String vNetName, String status,
      String vNetNameNew, String subscriptionName);

  ResponseEntity updateSubnetStatusByName(String groupName, String vNetName, String subnetType,
      String status, String name);

  ResponseEntity fetchVnetDetailsBySpokeNameWithStatus(String groupName, String status);

  ResponseEntity fetchSubnetDetailsByTypeWithStatus(String groupName, String vnetName,
      String subNetTypeName, String status);
}
