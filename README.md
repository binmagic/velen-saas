# velen-saas

##基础软件环境：
| 软件 | 版本 |
| ---  | ---- |
| MongoDB | lastest |
| Nacos | lastest |
| Java   | jdk8+ |
| Node | lastest |

##依赖插件
| 插件  |
| ---  |
| [Lombook](https://github.com/mplushnikov/lombok-intellij-plugin) |


##服务划分：
| 服务 | 描述 |
| ---  | ---- |
| velen-saas-authority | 认证权限服务 |
| velen-saas-common | 通用组件/工具类等 |
| velen-saas-config | 应用配置服务 |
| velen-saas-gateway | 服务网关 |
| velen-saas-app |  前端应用 |

##服务子模块划分
- 这样划分主要是让其他服务好依赖
- 一般其他服务要依赖其他服务只需要引入**entity,**api 

| 子模块 | 描述 |
| ---  | ---- |
| velen-saas-**-controller | 接口服务层 |
| velen-saas-**-biz | 逻辑处理层，数据库等操作 |
| velen-saas-**-entity | 实体数据层 |
| velen-saas-**-api | 服务内部API层 |
| velen-saas-**-server | 服务启动，用里面的App类启动 |

##服务内包名
| 包名 | 描述 |
| ---  | ---- |
| dto | 领域模型,用于映射接口 |
| entity | 实体类,用于映射数据库 |
| controller | 接口处理逻辑 |
| service | 业务处理逻辑 |
| repository | 数据库处理逻辑 |
| config | 配置逻辑 |
| filter | 网关过滤器 |
| provider | 逻辑提供器 |
| component | 通用组件 |
| constant | 常亮 |
| exception | 自定义异常 |
| util | 工具类 |

##基础配置说明
```
#变量
middleware:
  host: localhost
#ribbon 调用配置(可选)
ribbon:
  ConnectTimeout: 60000
  ReadTimeout: 60000
  maxAutoRetries: 3
  maxAutoRetriesNextServer: 3
  okToRetryOnAllOperations: true

server:
  port: 6002

spring:
  # 开发工具，包含修改代码自动重启
  devtools:
    restart:
      enabled: true
  # 应用名
  application:
    name: velen-saas-authority
  cloud:
    # 配置中心与服务发现
    nacos:
      discovery:
        metadata:
          group: velen-saas-group
          version: 1.0
        server-addr: ${middleware.host}:8848
  data:
    # mongo db 配置
    mongodb:
      database: velen-saas-authority
      host: localhost
      port: 27017

# 所有微服务保持一致，做相互可信，调用接口使用
jwt:
  token_secret: pXFb4i%*834gfdh96(3df&%18iodGq4ODQyMzc4lz7yI6ImF1dG
```

##pom文件规范
- 所有依赖库必须在最上层pom中dependencyManagement中定义并且把版本号定义在property中
方便更换版本
- plugin同理
- 子pom中依赖库中不许出现版本号



##后端编码规范
- 项目优先使用Kotlin编写，不然响应式编程会让人怀疑人生
- 项目中需要遵循RESTful规范
- 项目中实体分为Entity(映射数据库) & DTO(校验与传输)
- 整个项目结构可以参考velen-saas-authority
- 接口命名已模块为前缀 例如：/dashboard
- 每个服务包名规范为com.github.binmagic.saas.velen.模块名

##后端注意事项：
- 启动前先整个项目 clean install
- 增加了Entity或修改了并且使用了Qxxx类 需要 mvn compile
- 项目中接口命名模块为前缀

##前端运行说明
- 
```
# 安装依赖
npm install
# 开发
npm run dev
# 编译发布生产
npm run build:prod


# 代码格式检查
npm run lint

# 代码格式检查并自动修复
npm run lint -- --fix
```
- 指定后端地址 vue.config.js 文件中找到 process.env.VUE_APP_BASE_API 这一段就明白了

##前端目录说明(src)
| 目录名 | 描述 |
| ---  | ---- |
| api | 定义与服务器之前的接口 |
| assets | 定义一些图片或字体等资源 |
| components | 定义应用中通用的组件/布局 |
| config | 定义一些配置变量 |
| directive | 自定义指令 |
| filters | 通用过滤器 |
| icons | 图标定义 |
| layout | 逻辑提供器 |
| libs | 通用工具库 |
| locale | 多语言定义 |
| router | 应用中路由 |
| store | 状态存储器 |
| styles | 自定义全局样式 |
| utils | 一些工具类 |
| views | 页面 |

##前端代码规范
- 所有目录文件名全小写，遇多个单词用-结合
- 能复用的代码定义成一个通用业务组件
- 复杂单页也要组件化，增加代码可读性
- 前端项目中一般不会出现操作原生dom的情况
- 所有样式有可读的名字
- 样式少时可以定义在页面最下方，当样式多时增加scss文件定义
