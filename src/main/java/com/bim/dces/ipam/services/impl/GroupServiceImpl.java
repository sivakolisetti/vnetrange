package com.bim.dces.ipam.services.impl;


import com.bim.dces.ipam.model.IPamGroup;
import com.bim.dces.ipam.model.Subnet;
import com.bim.dces.ipam.model.VnetSpoke;
import com.bim.dces.ipam.repository.IPamGroupRepository;
import com.bim.dces.ipam.repository.SubnetRepository;
import com.bim.dces.ipam.repository.VnetSpokeRepository;
import com.bim.dces.ipam.services.GroupService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupServiceImpl implements GroupService {

  private Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);
  @Autowired
  private IPamGroupRepository iPamGroupRepository;

  @Autowired
  private SubnetRepository subnetRepository;

  @Autowired
  private VnetSpokeRepository vnetSpokeRepository;


  @Override
  public List<IPamGroup> getIpamGroups() {
    logger.info("getIpamGroups: all groups : ");
    return iPamGroupRepository.findAll();
  }

  @Override
  public IPamGroup getIpamGroupByName(String groupName) {
    logger.info("getIpamGroupByName: groupName : " + groupName);
    return iPamGroupRepository.findByGroupName(groupName);
  }

  @Override
  public Long countByGroupName(String groupName) {
    logger.info("countByGroupName: groupName : " + groupName);
    return iPamGroupRepository.countByGroupName(groupName);
  }

  @Override
  public VnetSpoke getVnetDetailsBySpokeName(String groupName, String vnetName) {
    logger.info("getVnetDetailsBySpokeName");
    logger.info("groupName : " + groupName);
    logger.info("vnetName : " + vnetName);
    return iPamGroupRepository.fetchVnetDetailsBySpokeName(groupName, vnetName);
  }

  @Override
  public VnetSpoke getVnetDetailsBySubscriptionName(String groupName, String subscriptionName) {
    logger.info("getVnetDetailsBySubscriptionName");
    logger.info("groupName : " + groupName);
    logger.info("subscriptionName : " + subscriptionName);
    return iPamGroupRepository.fetchVnetDetailsBySubscriptionName(groupName, subscriptionName);
  }

  @Override
  public VnetSpoke fetchVnetDetailsBySpokeNameWithStatus(String groupName, String status) {
    logger.info("fetchVnetDetailsBySpokeNameWithStatus");
    logger.info("groupName : " + groupName);
    logger.info("status : " + status);
    List<VnetSpoke> vnetSpokes = iPamGroupRepository.fetchVnetDetailsBySpokeNameWithStatus(
        groupName, status);

    Optional<VnetSpoke> first = vnetSpokes.stream().findFirst();

    if (first.isPresent()) {
      logger.info("returns top available vnet with status : " + status);
      return first.get();
    } else {
      return null; // TODO:
    }
  }

  @Override
  public Subnet getSubnetDetailsByType(String groupName, String vnetName,
      String subNetTypeName) {
    logger.info("getSubnetDetailsByType");
    logger.info("groupName : " + groupName);
    logger.info("vnetName : " + vnetName);
    logger.info("subNetTypeName : " + subNetTypeName);
    return iPamGroupRepository.fetchSubnetDetailsByType(groupName, vnetName, subNetTypeName);
  }

  @Override
  public Subnet fetchSubnetDetailsByTypeWithStatus(String groupName, String vnetName,
      String subNetTypeName, String status) {
    logger.info("fetchSubnetDetailsByTypeWithStatus");
    logger.info("groupName : " + groupName);
    logger.info("vnetName : " + vnetName);
    logger.info("subNetTypeName : " + subNetTypeName);
    logger.info("status : " + status);
    return iPamGroupRepository.fetchSubnetDetailsByTypeWithStatus(groupName, vnetName,
        subNetTypeName, status);
  }

  @Override
  public IPamGroup addNewIpamGroup(IPamGroup iPamGroup) {
    logger.info("addNewIpamGroup");
    logger.info("groupName : " + iPamGroup.getGroupName());

    IPamGroup iPamGroupSaved = iPamGroupRepository.saveAndFlush(iPamGroup);
    logger.info("iPamGroupSaved saved: " + iPamGroupSaved.getId());

    List<VnetSpoke> vnetSpokes = iPamGroup.getVnetSpoke();

    vnetSpokes.forEach(vnetSpoke -> {
      logger.info("Found vnetspoke : " + vnetSpoke.getName());
      vnetSpoke.setIPamGroup(iPamGroup);
      VnetSpoke vnetSpokeSaved = vnetSpokeRepository.saveAndFlush(vnetSpoke);
      System.out.println("VnetSpoke saved: " + vnetSpokeSaved.getId());

      vnetSpoke.getSubnet().forEach(subnet -> {
        logger.info("Found subnet : " + subnet.getType());
        subnet.setVnetSpoke(vnetSpoke);
        Subnet subnetSaved = subnetRepository.saveAndFlush(subnet);
        System.out.println("Subnet saved: " + subnetSaved.getId());
      });
      logger.info("------------------subnet end----------------");
    });
    logger.info("------------------vnet end----------------");
    return iPamGroupSaved;
  }

  @Override
  public IPamGroup addNewIpamGroupOnly(String name) {
    logger.info("addNewIpamGroupOnly");
    logger.info("groupName : " + name);
    IPamGroup iPamGroup = new IPamGroup();
    iPamGroup.setGroupName(name);
    IPamGroup iPamGroupSaved = iPamGroupRepository.saveAndFlush(iPamGroup);
    logger.info("new group created: " + iPamGroupSaved.getId());
    return iPamGroupSaved;
  }

  @Override
  public List<VnetSpoke> addVnetSpokeToGroup(IPamGroup iPamGroup, List<VnetSpoke> vnetSpoke) {
    logger.info("addVnetSpokeToGroup");
    logger.info("groupName : " + iPamGroup.getGroupName());
    List<VnetSpoke> vnetSpokeSavedList = new ArrayList<>();
    vnetSpoke.forEach(vnet -> {
      VnetSpoke existingVnet = getVnetDetailsBySpokeName(iPamGroup.getGroupName(),
          vnet.getName());
      if (existingVnet == null) {
        vnet.setIPamGroup(iPamGroup);
        VnetSpoke vnetSpokeSaved = vnetSpokeRepository.saveAndFlush(vnet);
        vnetSpokeSavedList.add(vnetSpokeSaved);
        logger.info("VnetSpoke saved: " + vnetSpokeSaved.getId());

        vnet.getSubnet().forEach(subnet -> {
          Subnet existingSubnet = getSubnetDetailsByType(iPamGroup.getGroupName(),
              vnet.getName(), subnet.getType());
          if (existingSubnet == null) {
            subnet.setVnetSpoke(vnet);
            Subnet subnetSaved = subnetRepository.saveAndFlush(subnet);
            logger.info("Subnet saved: " + subnetSaved.getId());
          } else {
            logger.info("This subnet already exists : " + existingSubnet.getType());
          }
        });
        logger.info("----------subnet end------------------");
      } else {
        logger.info("This vnet already exists : " + vnet.getName());
      }
    });
    logger.info("----------vnet end------------------");
    return vnetSpokeSavedList;
  }

  @Override
  public List<Subnet> addSubnetToVnet(IPamGroup iPamGroup, VnetSpoke vnetSpoke,
      List<Subnet> subnets) {
    logger.info("----------addSubnetToVnet------------------");
    logger.info("subnet size :" + subnets.size());
    List<Subnet> subnetList = new ArrayList<>();
    subnets.forEach(subnet -> {
      logger.info("----------subnet end------------------");
      Subnet existingSubnet = getSubnetDetailsByType(iPamGroup.getGroupName(),
          vnetSpoke.getName(), subnet.getType());
      vnetSpoke.setIPamGroup(iPamGroup);

      if (existingSubnet == null) {
        subnet.setVnetSpoke(vnetSpoke);
        Subnet subnetSaved = subnetRepository.saveAndFlush(subnet);
        logger.info("Subnet saved: " + subnetSaved.getId());
        subnetList.add(subnetSaved);
      } else {
        logger.info("This subnet already exists : " + existingSubnet.getType());
      }
    });
    logger.info("----------subnet end------------------");
    return subnetList;
  }

  @Override
  @Transactional
  public void updateGroupName(Long id, String groupName) {
    logger.info("----------updateGroupName------------------ : " + id + " : " + groupName);
    iPamGroupRepository.updateGroupNameById(id, groupName);
  }

  @Override
  @Transactional
  public void updateVnetStatusByName(String groupName, String vNetName, String status,
      String vNetNameNew, String subscriptionName) {
    logger.info(
        "----------updateVnetStatusByName------------------ : " + groupName + " : " + vNetName
            + " : " + status+ " : " + vNetNameNew+ " : " + subscriptionName);
    iPamGroupRepository.updateVnetStatusByName(groupName, vNetName, status, vNetNameNew,
        subscriptionName);
  }

  @Override
  @Transactional
  public void updateSubnetStatusByName(String groupName, String vNetName, String subnetType,
      String status, String name) {
    logger.info(
        "----------updateSubnetStatusByName------------------ : " + groupName + " : " + vNetName
            + " : " + subnetType + " : " + status+ " : " + name);
    iPamGroupRepository.updateSubnetStatusByName(groupName, vNetName, subnetType, status, name);
  }

}
