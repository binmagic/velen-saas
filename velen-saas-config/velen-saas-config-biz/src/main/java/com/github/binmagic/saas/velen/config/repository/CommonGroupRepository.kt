package com.github.binmagic.saas.velen.config.repository

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import com.github.binmagic.saas.velen.config.entity.CommonGroup
import org.springframework.stereotype.Repository

@Repository
interface CommonGroupRepository :BaseRepository<CommonGroup> {
    fun findByAppId(appId:String) :List<CommonGroup>
}