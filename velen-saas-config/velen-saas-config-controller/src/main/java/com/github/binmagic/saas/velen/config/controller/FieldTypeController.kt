package com.github.binmagic.saas.velen.config.controller

import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.config.dto.FieldTypeDTO
import com.velen.etl.verification.entity.EventFieldType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dashboard")
class FieldTypeController : BaseController() {

    @GetMapping("/eventFieldType")
    suspend fun getFieldType(): List<FieldTypeDTO> {
        val list: MutableList<FieldTypeDTO> = ArrayList()
        val fieldTypes = EventFieldType.values()

        for (eventFieldType: EventFieldType in fieldTypes) {
            val fieldTypeDTO = FieldTypeDTO()
            fieldTypeDTO.name = eventFieldType.getName()
            fieldTypeDTO.value = eventFieldType.value
            fieldTypeDTO.comment = eventFieldType.comment
            list.add(fieldTypeDTO)
        }
        return list
    }
}