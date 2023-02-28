package com.bim.dces.ipam.controller.impl;

import com.bim.dces.ipam.controller.IPamGroupStaticDataController;
import com.bim.dces.ipam.model.IPamGroup;
import com.bim.dces.ipam.model.Subnet;
import com.bim.dces.ipam.model.VnetSpoke;
import com.bim.dces.ipam.repository.IPamGroupRepository;
import com.bim.dces.ipam.repository.SubnetRepository;
import com.bim.dces.ipam.repository.VnetSpokeRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("readonlyipamgroup")
public class IPamGroupStaticDataControllerImpl implements IPamGroupStaticDataController {

  @Autowired
  private IPamGroupRepository ipamGroupRepository;

  @Autowired
  private SubnetRepository subnetRepository;

  @Autowired
  private VnetSpokeRepository vnetSpokeRepository;

  @Override
  @PostMapping("data")
  public IPamGroup createGroup() {
    IPamGroup iPamGroup = new IPamGroup();
    iPamGroup.setGroupName("DEV");
    IPamGroup savedGroup = ipamGroupRepository.saveAndFlush(iPamGroup);

    VnetSpoke vnetSpoke1 = new VnetSpoke("SPOKE 21", 17, "10.12.0.0", "10.12.127.255", 32768,
        "ecad-onedimension-ppr-cel-bls", "NEW");

    VnetSpoke vnetSpoke2 = new VnetSpoke("Spoke 1", 17, "10.2.0.0", "10.2.127.255", 32768,
        "ecad-ops-sandbox", "NEW");

    VnetSpoke vnetSpoke3 = new VnetSpoke("Spoke 3", 17, "10.3.0.0", "10.3.127.255", 32768,
        "ecad-oe-dev-ige-rnd-poland", "NEW");

    VnetSpoke vnetSpoke4 = new VnetSpoke("Spoke 2", 17, "10.2.128.0", "10.2.255.255", 32768,
        "ecad-commoncomponents-dev-ige-plm", "NEW");

    List<VnetSpoke> vnetSpokeList = new ArrayList<>();
    vnetSpokeList.add(vnetSpoke1);
    vnetSpokeList.add(vnetSpoke2);
    vnetSpokeList.add(vnetSpoke3);
    vnetSpokeList.add(vnetSpoke4);

    vnetSpoke1.setIPamGroup(iPamGroup);
    vnetSpoke2.setIPamGroup(iPamGroup);
    vnetSpoke3.setIPamGroup(iPamGroup);
    vnetSpoke4.setIPamGroup(iPamGroup);

    vnetSpokeRepository.saveAllAndFlush(vnetSpokeList);

    Subnet subnet1 = new Subnet("AKS System Subnet", "subnet", 21, "10.12.0.0", "10.12.7.255", 2048,
        "new");
    Subnet subnet2 = new Subnet("AKS User Subnet", "subnet", 21, "10.12.8.0", "10.12.15.255", 2048,
        "new");
    Subnet subnet3 = new Subnet("VM Subnet", "subnet", 21, "10.12.16.0", "10.12.23.255", 2048,
        "new");

    Subnet subnet4 = new Subnet("AKS System Subnet", "subnet", 21, "10.2.0.0", "10.2.7.255", 2048,
        "new");
    Subnet subnet5 = new Subnet("AKS User Subnet", "subnet", 21, "10.2.8.0", "10.2.15.255", 2048,
        "new");
    Subnet subnet6 = new Subnet("VM Subnet", "subnet", 21, "10.2.16.0", "10.2.23.255", 2048, "new");

    Subnet subnet7 = new Subnet("AKS System Subnet", "subnet", 21, "10.3.0.0", "10.3.7.255", 2048,
        "new");
    Subnet subnet8 = new Subnet("AKS User Subnet", "subnet", 21, "10.3.8.0", "10.3.15.255", 2048,
        "new");
    Subnet subnet9 = new Subnet("VM Subnet", "subnet", 21, "10.3.16.0/21", "10.3.23.255", 2048,
        "new");

    Subnet subnet10 = new Subnet("AKS System Subnet", "subnet", 21, "10.2.128.0/21", "10.2.135.255",
        2048,
        "new");
    Subnet subnet11 = new Subnet("AKS User Subnet", "subnet", 21, "10.2.136.0/21", "10.2.143.255",
        2048,
        "new");
    Subnet subnet12 = new Subnet("VM Subnet", "subnet", 21, "10.2.144.0/21", "10.2.151.255", 2048,
        "new");

    List<Subnet> subnetSpoke1 = new ArrayList<>();
    subnet1.setVnetSpoke(vnetSpoke1);
    subnet2.setVnetSpoke(vnetSpoke1);
    subnet3.setVnetSpoke(vnetSpoke1);
    subnetSpoke1.add(subnet1);
    subnetSpoke1.add(subnet2);
    subnetSpoke1.add(subnet3);

    List<Subnet> subnetSpoke2 = new ArrayList<>();
    subnet4.setVnetSpoke(vnetSpoke2);
    subnet5.setVnetSpoke(vnetSpoke2);
    subnet6.setVnetSpoke(vnetSpoke2);
    subnetSpoke2.add(subnet4);
    subnetSpoke2.add(subnet5);
    subnetSpoke2.add(subnet6);

    List<Subnet> subnetSpoke3 = new ArrayList<>();
    subnet7.setVnetSpoke(vnetSpoke3);
    subnet8.setVnetSpoke(vnetSpoke3);
    subnet9.setVnetSpoke(vnetSpoke3);
    subnetSpoke3.add(subnet7);
    subnetSpoke3.add(subnet8);
    subnetSpoke3.add(subnet9);

    List<Subnet> subnetSpoke4 = new ArrayList<>();
    subnet10.setVnetSpoke(vnetSpoke4);
    subnet11.setVnetSpoke(vnetSpoke4);
    subnet12.setVnetSpoke(vnetSpoke4);
    subnetSpoke4.add(subnet10);
    subnetSpoke4.add(subnet11);
    subnetSpoke4.add(subnet12);

    subnetRepository.saveAllAndFlush(subnetSpoke1);
    subnetRepository.saveAllAndFlush(subnetSpoke2);
    subnetRepository.saveAllAndFlush(subnetSpoke3);
    subnetRepository.saveAllAndFlush(subnetSpoke4);

    return savedGroup;
  }
}
