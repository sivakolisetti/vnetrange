package com.bim.dces.ipam.repository;

import com.bim.dces.ipam.model.IPamGroup;
import com.bim.dces.ipam.model.Subnet;
import com.bim.dces.ipam.model.VnetSpoke;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPamGroupRepository extends JpaRepository<IPamGroup, Long> {

  IPamGroup findByGroupName(String groupName);

  Long countByGroupName(String groupName);

  @Query("select v from VnetSpoke v join IPamGroup i on v.iPamGroup.id = i.id where i.groupName = ?1 and v.name = ?2")
  VnetSpoke fetchVnetDetailsBySpokeName(String groupName, String vnetName);

  @Query("select v from VnetSpoke v join IPamGroup i on v.iPamGroup.id = i.id where i.groupName = ?1 and v.subscriptionName = ?2")
  VnetSpoke fetchVnetDetailsBySubscriptionName(String groupName, String subscriptionName);

  @Query("select v from VnetSpoke v join IPamGroup i on v.iPamGroup.id = i.id where i.groupName = ?1 and v.status=?2 order by v.id")
  List<VnetSpoke> fetchVnetDetailsBySpokeNameWithStatus(String groupName, String status);

  @Query("select s from Subnet s join VnetSpoke v on s.vnetSpoke.id = v.id join IPamGroup i on v.iPamGroup.id = i.id where i.groupName = ?1 and v.name = ?2 and s.type=?3")
  Subnet fetchSubnetDetailsByType(String groupName, String vnetName, String subNetTypeName);

  @Query("select s from Subnet s join VnetSpoke v on s.vnetSpoke.id = v.id join IPamGroup i on v.iPamGroup.id = i.id where i.groupName = ?1 and v.name = ?2 and s.type=?3 and s.status=?4")
  Subnet fetchSubnetDetailsByTypeWithStatus(String groupName, String vnetName,
      String subNetTypeName, String status);

  @Modifying
  @Query("update IPamGroup i set i.groupName = ?2 where i.id=?1")
  void updateGroupNameById(Long id, String groupName);

  @Modifying
  @Query("UPDATE VnetSpoke v set v.status = ?3,v.name=?4, v.subscriptionName=?5 where v.name=?2 and v.iPamGroup.id = (select i.id from IPamGroup i where i.groupName=?1)")
  void updateVnetStatusByName(String groupName, String vNetName, String status, String vNetNameNew,
      String subscriptionName);

  @Modifying
  @Query("UPDATE Subnet s set s.status = ?4, s.name=?5 where s.type=?3 and s.vnetSpoke.id = (select v.id from VnetSpoke v where v.name=?2 and v.iPamGroup.id = (select i.id from IPamGroup i where i.groupName=?1))")
  void updateSubnetStatusByName(String groupName, String vNetName, String subnetType,
      String status, String name);
}
