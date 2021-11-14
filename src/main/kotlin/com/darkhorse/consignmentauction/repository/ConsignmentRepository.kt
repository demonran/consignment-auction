package com.darkhorse.consignmentauction.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConsignmentRepository: JpaRepository<ConsignmentEntity, String> {

}
