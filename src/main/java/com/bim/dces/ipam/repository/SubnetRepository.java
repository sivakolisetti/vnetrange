package com.bim.dces.ipam.repository;

import com.bim.dces.ipam.model.Subnet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubnetRepository extends JpaRepository<Subnet, Long> {

}
