package com.bim.dces.ipam.repository;

import com.bim.dces.ipam.model.VnetSpoke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VnetSpokeRepository extends JpaRepository<VnetSpoke, Long> {

}
