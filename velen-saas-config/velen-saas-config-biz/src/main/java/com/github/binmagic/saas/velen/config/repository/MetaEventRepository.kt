package com.github.binmagic.saas.velen.config.repository

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import com.github.binmagic.saas.velen.config.entity.MetaEvent
import org.springframework.stereotype.Repository

@Repository
interface MetaEventRepository : BaseRepository<MetaEvent> {
}