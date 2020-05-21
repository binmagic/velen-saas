package com.github.binmagic.saas.velen.config.repository

import com.github.binmagic.saas.velen.common.component.dao.BaseRepository
import com.github.binmagic.saas.velen.config.entity.Bookmarks
import org.springframework.stereotype.Repository

@Repository
interface BookmarksRepository : BaseRepository<Bookmarks>{
}