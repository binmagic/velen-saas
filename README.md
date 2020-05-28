# velen-saas

##基础软件环境：
| 软件 | 版本 |
| ---  | ---- |
| MongoDB | lastest |
| Nacos | lastest |
| Java   | jdk8+ |

##依赖插件
| 插件  |
| ---  |
| [Lombook](https://github.com/mplushnikov/lombok-intellij-plugin) |


##编码规范
- 项目优先使用Kotlin编写
- 项目中需要遵循RESTful规范
- 项目中实体分为Entity(映射数据库) & DTO(校验与传输)
- 整个项目结构可以参考velen-saas-authority
- 接口命名已模块为前缀

##注意事项：
- 启动前先整个项目 clean install
- 增加了Entity或修改了并且使用了Qxxx类 需要 mvn compile
- 项目中接口命名模块为前缀

