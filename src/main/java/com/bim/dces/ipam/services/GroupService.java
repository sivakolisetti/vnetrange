package com.bim.dces.ipam.services;

import com.bim.dces.ipam.model.IPamGroup;
import com.bim.dces.ipam.model.Subnet;
import com.bim.dces.ipam.model.VnetSpoke;
import java.util.List;

public interface GroupService {


  List<IPamGroup> getIpamGroups();

  IPamGroup getIpamGroupByName(String groupName);

  Long countByGroupName(String groupName);

  VnetSpoke getVnetDetailsBySpokeName(String groupName, String vnetName);

  
  VnetSpoke getVnetDetailsBySubscriptionName(String groupName, String subscriptionName);

  VnetSpoke fetchVnetDetailsBySpokeNameWithStatus(String groupName, String status);

  Subnet getSubnetDetailsByType(String groupName, String vnetName, String subNetTypeName);

  Subnet fetchSubnetDetailsByTypeWithStatus(String groupName, String vnetName,
      String subNetTypeName, String status);

  IPamGroup addNewIpamGroupOnly(String name);

  IPamGroup addNewIpamGroup(IPamGroup iPamGroup);

  List<VnetSpoke> addVnetSpokeToGroup(IPamGroup iPamGroup, List<VnetSpoke> vnetSpoke);

  List<Subnet> addSubnetToVnet(IPamGroup iPamGroup, VnetSpoke vnetSpoke,
      List<Subnet> subnet);

  void updateGroupName(Long id, String groupName);

  void updateVnetStatusByName(String groupName, String vNetName, String status, String vNetNameNew, String subscriptionName);

  void updateSubnetStatusByName(String groupName, String vNetName, String subnetType,
      String status, String name);
}
