package com.xa.lookupservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xa.lookupservice.models.LookUp;

@Repository
public interface LookupRepository extends JpaRepository<LookUp, Long> {
    

}
