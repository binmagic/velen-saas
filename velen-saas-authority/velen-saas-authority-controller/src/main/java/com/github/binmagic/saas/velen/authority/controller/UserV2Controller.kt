package com.github.binmagic.saas.velen.authority.controller

import com.github.binmagic.saas.velen.authority.dto.LoginParamDTO
import com.github.binmagic.saas.velen.authority.dto.RegisterParamDTO
import com.github.binmagic.saas.velen.authority.entity.User
import com.github.binmagic.saas.velen.authority.granter.TokenGranter
import com.github.binmagic.saas.velen.authority.granter.TokenGranterBuilder
import com.github.binmagic.saas.velen.authority.service.ResourceService
import com.github.binmagic.saas.velen.authority.service.RoleService
import com.github.binmagic.saas.velen.authority.service.UserService
import com.github.binmagic.saas.velen.common.component.controller.BaseController
import com.github.binmagic.saas.velen.common.config.JWTConfig
import com.github.binmagic.saas.velen.common.config.JWTConfig.JWTAuthInfo
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RequestMapping("accounts")
@RestController
class UserV2Controller : BaseController() {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var resourceService: ResourceService

    @Autowired
    lateinit var roleService: RoleService

    @Autowired
    lateinit var tokenGranterBuilder: TokenGranterBuilder

    @Autowired
    lateinit var jwtConfig: JWTConfig

    @PostMapping("login")
    fun auth(@Validated @RequestBody loginParamDTO: LoginParamDTO)  = mono(Unconfined)  {
        val granter: TokenGranter = tokenGranterBuilder.getGranter(loginParamDTO.grantType)
        val user = granter.grant(loginParamDTO).awaitFirstOrNull() ?: throw NoSuchMethodException("grant fail")
        val jwtAuthInfo = JWTAuthInfo(user.account, user.id, user.name)
        val authInfo = jwtConfig.createAutoInfo(jwtAuthInfo, jwtConfig.tokenExpireTime)
        ResponseEntity.ok(authInfo)
    }


    @PostMapping("register")
    fun register(@Validated @RequestBody registerParamDTO: RegisterParamDTO) = mono(Unconfined) {
        val user = User()
        BeanUtils.copyProperties(registerParamDTO, user)
        userService.saveUser(user).awaitSingle()
    }

    @GetMapping("authorization")
    fun authorization() = mono(Unconfined) {

        val userId = currentUserId.awaitSingle()

        val userRoles = userService.getUserRole(userId).collectList().awaitSingle()

        val roleIds = ArrayList<String>()

        for(roleUser in userRoles){
            roleIds.add(roleUser.roleId)
        }

        val roleResources = roleService.getRoleResources(roleIds).collectList().awaitSingle()

        val resourceIds = ArrayList<String>()

        for(roleResource in roleResources){
            resourceIds.add(roleResource.resourceId)
        }

        resourceService.getResource(resourceIds)
    }


}