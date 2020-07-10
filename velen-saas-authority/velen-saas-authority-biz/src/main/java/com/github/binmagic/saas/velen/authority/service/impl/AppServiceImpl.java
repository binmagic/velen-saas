package com.github.binmagic.saas.velen.authority.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.binmagic.saas.velen.authority.dto.AppAddMemberDTO;
import com.github.binmagic.saas.velen.authority.dto.AppMemberInfoDTO;
import com.github.binmagic.saas.velen.authority.entity.App;
import com.github.binmagic.saas.velen.authority.entity.AppMember;
import com.github.binmagic.saas.velen.authority.event.CreateApp;
import com.github.binmagic.saas.velen.authority.event.CreateDashboard;
import com.github.binmagic.saas.velen.authority.event.DeployApp;
import com.github.binmagic.saas.velen.authority.repository.AppMemberRepository;
import com.github.binmagic.saas.velen.authority.repository.AppRepository;
import com.github.binmagic.saas.velen.authority.service.AppService;
import com.github.binmagic.saas.velen.common.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppServiceImpl implements AppService {
    @Autowired
    AppRepository appRepository;

    @Autowired
    AppMemberRepository appMemberRepository;

    @Autowired
    ApplicationContext applicationContext;

    public Flux<App> findApp(String account) {
        return appMemberRepository.findByAccount(account)
                .collectList().flatMapMany(apps -> {
                    List<String> appIds = new ArrayList<>();
                    apps.forEach(app -> appIds.add(app.getAppId()));
                    return appRepository.findByIdIn(appIds);
                });
    }

    @Override
    public Mono<App> createApp(App app) {
        LocalDateTime now = LocalDateTime.now();
        app.setAppKey(IdUtil.fastSimpleUUID())
                .setState(App.DRAFT)
                .setUpdateTime(now)
                .setCreateTime(now);
        Mono<App> mono = appRepository.insert(app).flatMap(_app -> appMemberRepository
                .insert(new AppMember(_app.getId(), app.getOwner(), Constant.ROLE_MANAGER))
                .thenReturn(_app));

        mono.subscribe(app1 -> {

            applicationContext.publishEvent(new CreateDashboard(app.getId(), "admin"));

            applicationContext.publishEvent(new CreateApp(app.getId(), "topic", false, "admin"));

            applicationContext.publishEvent(new DeployApp(app.getId(), "type", "admin"));

        });


        return mono;
    }

    @Override
    public Mono<Void> submitToAuditor(String appId) {
        return appRepository.findById(appId)
                .flatMap(app -> {
                    app.setState(App.WAIT_CHECK);
                    return appRepository.save(app).then();
                });
    }

    @Override
    public Mono<App> updateApp(App app) {
        return getApp(app.getId())
                .flatMap(_app -> {
                    _app.setName(app.getName())
                            .setDesc(app.getDesc());
                    return appRepository.save(_app);
                });
    }

    @Override
    public Mono<App> getApp(String appId) {
        return appRepository.findById(appId);
    }

    @Override
    public Flux<AppMemberInfoDTO> getAppMember(String appId) {
        return appMemberRepository.findByAppId(appId)
                .flatMapSequential(appMember -> Mono.just(new AppMemberInfoDTO().setId(appMember.getId())
                        .setAccount(appMember.getAccount()).setRole(appMember.getRole())));
    }

    @Override
    public Mono<String> getAppMemberRole(String appId, String account) {
        return appMemberRepository.findFirstByAccountAndAppId(account, appId)
                .flatMap(appMember -> Mono.just(appMember.getRole()));
    }

    @Override
    public Mono<Void> addMember(String appId, List<AppAddMemberDTO> appAddMemberDTO) {
        return appRepository.findById(appId)
                .flatMap(app -> {
                    List<Mono> monoList = new ArrayList<>(appAddMemberDTO.size());
                    appAddMemberDTO.stream().forEach(dto -> {
                        AppMember appMember = new AppMember().setAppId(app.getId())
                                .setRole(dto.getRole()).setAccount(dto.getAccount());
                        monoList.add(appMemberRepository.insert(appMember));
                    });
                    return Flux.concat(monoList.toArray(new Mono[0])).then();
                });
    }

    @Override
    public Mono<Void> deleteMember(String appId, String id) {
        return appMemberRepository.deleteById(id);
    }

}
