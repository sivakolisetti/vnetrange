package com.bim.dces.ipam.controller.impl;

import com.bim.dces.ipam.controller.GroupController;
import com.bim.dces.ipam.model.IPStatus;
import com.bim.dces.ipam.model.IPamGroup;
import com.bim.dces.ipam.model.Subnet;
import com.bim.dces.ipam.model.VnetSpoke;
import com.bim.dces.ipam.services.GroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ipam")
public class GroupControllerImpl implements GroupController {

  @Autowired
  private GroupService groupService;

  @Override
  @GetMapping("/group")
  public ResponseEntity getIpamGroups() {
    List<IPamGroup> allGroups = groupService.getIpamGroups();
    if (allGroups.size() > 0) {
      return ResponseEntity.ok(allGroups);
    } else {
      return ResponseEntity.ok("No groups found");
    }
  }

  @Override
  @GetMapping("/group/{groupName}")
  public ResponseEntity getIpamGroupByName(@PathVariable("groupName") String groupName) {
    Long ipamGroupCount = groupService.countByGroupName(groupName);
    if (ipamGroupCount != null && ipamGroupCount > 0) {
      return ResponseEntity.ok(ipamGroupCount);
    } else {
      return ResponseEntity.ok("No group found with name " + groupName);
    }
  }

  @Override
  @GetMapping("/group/{groupName}/vnet")
  public ResponseEntity getVnetDetailsBySpokeName(@PathVariable("groupName") String groupName,
      @RequestParam("name") String vnetName) {
    VnetSpoke vnetDetailsBySpokeName = groupService.getVnetDetailsBySpokeName(groupName,
        vnetName);
    if (vnetDetailsBySpokeName != null) {
      return ResponseEntity.ok(vnetDetailsBySpokeName);
    } else {
      return ResponseEntity.ok(
          "vnet spoke " + vnetName + " doesn't exist for this group " + groupName);
    }
  }

    @Override
  @GetMapping("/group/{groupName}/vnetsubscription/{subscriptionName}")
  public ResponseEntity getVnetDetailsBySubscriptionName(@PathVariable("groupName") String groupName,
      @PathVariable("subscriptionName") String subscriptionName) {
    VnetSpoke vnetDetailsBySubscriptionName = groupService.getVnetDetailsBySubscriptionName(groupName,
        subscriptionName);
    if (vnetDetailsBySubscriptionName != null) {
      return ResponseEntity.ok(vnetDetailsBySubscriptionName);
    } else {
      return ResponseEntity.ok(
          "vnet spoke for this subscription  " + subscriptionName + " doesn't exist for this group " + groupName);
    }
  }

  @Override
  @GetMapping("/group/{groupName}/snet")
  public ResponseEntity getSubnetDetailsByType(@PathVariable("groupName") String groupName,
      @RequestParam("name") String vnetName,
      @RequestParam("type") String subNetTypeName) {
    Subnet subnetDetailsByType = groupService.getSubnetDetailsByType(groupName,
        vnetName, subNetTypeName);
    if (subnetDetailsByType != null) {
      return ResponseEntity.ok(subnetDetailsByType);
    } else {
      return ResponseEntity.ok(
          "subnet " + subNetTypeName + " doesn't exist for vnet " + vnetName + " and group "
              + groupName);
    }
  }

  @Override
  @PostMapping("/group/create/full")
  public ResponseEntity addNewIpamGroup(@RequestBody IPamGroup iPamGroup) {
    Long ipamGroupCount = groupService.countByGroupName(iPamGroup.getGroupName());
    if (ipamGroupCount != null && ipamGroupCount > 0) {
      return ResponseEntity.ok(
          "Full : Group with name '" + iPamGroup.getGroupName() + "' is already exist");
    } else {
      IPamGroup iPamGroupCreated = groupService.addNewIpamGroup(iPamGroup);
      if (iPamGroupCreated != null) {
        return ResponseEntity.ok(iPamGroupCreated);
      } else {
        return ResponseEntity.ok(
            "Full : Creating IPam group failed");
      }
    }
  }

  @Override
  @PostMapping("/group/create")
  public ResponseEntity addNewIpamGroupOnly(@RequestParam("groupName") String groupName) {
    Long ipamGroupCount = groupService.countByGroupName(groupName);
    if (ipamGroupCount != null && ipamGroupCount > 0) {
      return ResponseEntity.ok(
          "Partially: Group with name '" + groupName + "' is already exist");
    } else {
      IPamGroup iPamGroupCreated = groupService.addNewIpamGroupOnly(groupName);
      if (iPamGroupCreated != null) {
        return ResponseEntity.ok(iPamGroupCreated);
      } else {
        return ResponseEntity.ok(
            "Partially: Creating IPam group failed");
      }
    }
  }

  @Override
  @PostMapping("/group/create/vnet")
  public ResponseEntity addVnetSpokeToGroup(@RequestParam("name") String groupName,
      @RequestBody List<VnetSpoke> vnetSpoke) {
    IPamGroup ipamGroupByName = groupService.getIpamGroupByName(groupName);
    if (ipamGroupByName != null) {
      List<VnetSpoke> vnetSpokes = groupService.addVnetSpokeToGroup(ipamGroupByName, vnetSpoke);
      if (vnetSpokes.size() > 0) {
        return ResponseEntity.ok(vnetSpokes);
      } else {
        return ResponseEntity.ok(
            "Vnet spoke adding failed");
      }
    } else {
      return ResponseEntity.ok(
          "Group '" + groupName + "' doesn't exist ");
    }
  }

  @Override
  @PostMapping("/group/create/snet")
  public ResponseEntity addSubnetToVnet(@RequestParam("name") String groupName,
      @RequestParam("vnetname") String vnetSpokeName,
      @RequestBody List<Subnet> subnet) {

    Long ipamGroupByCount = groupService.countByGroupName(groupName);
    if (ipamGroupByCount != null && ipamGroupByCount > 0) {
      VnetSpoke vnetSpoke = groupService.getVnetDetailsBySpokeName(groupName, vnetSpokeName);
      if (vnetSpoke != null) {
        List<Subnet> subnets = groupService.addSubnetToVnet(
            groupService.getIpamGroupByName(groupName), vnetSpoke,
            subnet);
        if (subnets.size() > 0) {
          return ResponseEntity.ok(subnets);
        } else {
          return ResponseEntity.ok("subnet adding failed");
        }
      } else {
        return ResponseEntity.ok("vnet '" + vnetSpokeName + "' doesn't exist ");
      }
    } else {
      return ResponseEntity.ok("Group '" + groupName + "' doesn't exist ");
    }
  }

  @Override
  @PostMapping("/group/update")
  public ResponseEntity updateGroupName(@RequestParam("id") Long id,
      @RequestParam("groupName") String groupName) {
    groupService.updateGroupName(id, groupName);
    IPamGroup ipamGroupByName = groupService.getIpamGroupByName(groupName);
    if (ipamGroupByName != null && ipamGroupByName.getGroupName().equalsIgnoreCase(groupName)) {
      return ResponseEntity.ok("Group '" + groupName + "' is updated ");
    } else {
      return ResponseEntity.ok(
          "Group '" + groupName + "' is not updated. Please make sure the group already exist! ");
    }
  }

  @Override
  @PostMapping("/group/update/vnet")
  public ResponseEntity updateVnetStatusByName(@RequestParam("groupName") String groupName,
      @RequestParam("vNetName") String vNetName, @RequestParam("status") String status,
      @RequestParam("NewVNetName") String vNetNameNew,
      @RequestParam("subscriptionName") String subscriptionName) {
    if (!status.equalsIgnoreCase(IPStatus.NEW.name()) && !status.equalsIgnoreCase(
        IPStatus.USED.name())) {
      return ResponseEntity.ok("Invalid status. Please use 'NEW' or 'USED'");
    }
    groupService.updateVnetStatusByName(groupName, vNetName, status, vNetNameNew, subscriptionName);
    VnetSpoke vnetSpoke = groupService.getVnetDetailsBySpokeName(groupName, vNetName);
    if (vnetSpoke != null && vnetSpoke.getName().equalsIgnoreCase(vNetName)) {
      return ResponseEntity.ok("vNet '" + vNetName + "' is updated with status '" + status + "'");
    } else {
      return ResponseEntity.ok(
          "vNet '" + vNetName + "' is not updated. Please check group name and vnet name.");
    }

  }

  @Override
  @PostMapping("/group/update/subnet")
  public ResponseEntity updateSubnetStatusByName(@RequestParam("groupName") String groupName,
      @RequestParam("vNetName") String vNetName, @RequestParam("subnetType") String subnetType,
      @RequestParam("status") String status, @RequestParam("name") String name) {
    if (!status.equalsIgnoreCase(IPStatus.NEW.name()) && !status.equalsIgnoreCase(
        IPStatus.USED.name())) {
      return ResponseEntity.ok("Invalid status. Please use 'NEW' or 'USED'");
    }
    groupService.updateSubnetStatusByName(groupName, vNetName, subnetType, status, name);
    Subnet subnet = groupService.getSubnetDetailsByType(groupName, vNetName,
        subnetType);
    if (subnet != null && subnet.getType().equalsIgnoreCase(subnetType)) {
      return ResponseEntity.ok(
          "subnet '" + subnetType + "' is updated with status '" + status + "'");
    } else {
      return ResponseEntity.ok("subnet '" + subnetType
          + "' is not updated. Please check group name, vnet name and subnet name.");
    }
  }

  @Override
  @GetMapping("/group/{groupName}/vnet/{status}")
  public ResponseEntity fetchVnetDetailsBySpokeNameWithStatus(
      @PathVariable("groupName") String groupName, @PathVariable("status") String status) {

    VnetSpoke vnetSpoke = groupService.fetchVnetDetailsBySpokeNameWithStatus(groupName, status);

    if (vnetSpoke != null) {
      return ResponseEntity.ok().body(vnetSpoke);
    } else {
      return ResponseEntity.ok("vnet with status '" + status
          + "'is not found. Please check group name, vnet name and status.");
    }
  }

  @Override
  @GetMapping("/group/{groupName}/snet/{status}")
  public ResponseEntity fetchSubnetDetailsByTypeWithStatus(
      @PathVariable("groupName") String groupName,
      @RequestParam("vnetName") String vnetName,
      @RequestParam("subNetTypeName") String subNetTypeName,
      @PathVariable("status") String status) {
    Subnet subnet = groupService.fetchSubnetDetailsByTypeWithStatus(groupName, vnetName,
        subNetTypeName, status);

    if (subnet != null) {
      return ResponseEntity.ok().body(subnet);
    } else {
      return ResponseEntity.ok("subnet '" + vnetName
          + "' with status '" + status
          + "'is not found. Please check group name, vnet name, subnet type and status.");
    }
  }

}
