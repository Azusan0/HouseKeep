# CityDirectoryService 开发文档

## 一、这篇文章是干什么的

这份文档会手把手带你完成"城市目录管理"功能的全部后端开发工作。读完之后你会知道一个 Spring Boot 项目里从数据库到接口的完整开发流程。

**读者前提**：你已经把项目代码下载到本地、装好了 JDK 17 和 Maven，并且能在 IDEA 里打开项目。

**目标产出**：一个可用的城市目录增删改查接口，含分页查询。

---

## 二、先认识一下你手里的项目

### 2.1 项目是干什么的

这是一个家政服务平台的后端项目，名字叫 duolai-clean。它采用微服务架构，由多个子模块组成。你当前要修改的是其中一个子模块：`housekeeping-service`（家政服务模块）。

### 2.2 项目用到了哪些技术

| 技术 | 作用 | 类比（如果你完全不熟） |
|------|------|----------------------|
| Spring Boot 3.x | 项目的骨架，负责启动和管理整个应用 | 相当于房子的地基和框架 |
| MyBatis-Plus | 操作数据库的工具，让你不写 SQL 也能查数据 | 相当于数据库的遥控器 |
| MySQL | 关系型数据库，存数据的地方 | Excel 表格的超强版 |
| Redis (Redisson) | 缓存，把常用数据放内存里，访问更快 | 相当于电脑的内存条 |
| Nacos | 服务注册与配置中心 | 所有微服务的电话本 |
| OpenFeign | 微服务之间互相调用的工具 | 服务A给服务B打电话 |
| MapStruct | 自动生成 Java 对象转换代码 | 自动帮你把 A 格式转成 B 格式 |
| Hutool | Java 工具库，提供各种常用功能 | 瑞士军刀 |
| Lombok | 让你少写 getter/setter/构造器 | 代码自动生成器 |
| Maven | 项目构建和依赖管理 | 项目的包管理器 |

### 2.3 项目代码是怎么分层的

这个项目采用经典的分层架构，每一层只做自己的事：

```
请求进来
    ↓
Controller 层（控制器）     ← 接收前端请求，调用 Service
controller/operation/
    ↓
Service 层（业务逻辑）      ← 处理业务规则，调用 Mapper
service/ + service/impl/
    ↓
Mapper 层（数据访问）       ← 操作数据库
dao/mapper/
    ↓
Entity 层（数据库表映射）    ← Java 对象对应数据库表
dao/entity/
```

辅助层：
- `dto/` — 数据传输对象（返回给前端的数据格式）
- `request/` — 请求参数对象（前端传过来的参数格式）
- `converter/` — 对象格式转换（MapStruct 自动生成）
- `enums/` — 枚举常量

**数据流向（以分页查询为例）**：

```
前端发请求 → Controller 接收 → Service 处理 → Mapper 查数据库
                                                     ↓
前端收到数据 ← Controller 返回 ← Service 组装 ← 数据库返回结果
```

### 2.4 项目目录结构速览

```
housekeeping-service/
└── src/main/java/com/cskaoyan/duolai/clean/housekeeping/
    ├── HouseKeepingServiceApplication.java   ← 启动类
    ├── config/                               ← Spring 配置
    ├── constants/                            ← 常量
    ├── controller/
    │   ├── inner/        ← 内部微服务调用接口
    │   ├── operation/    ← 运营后台管理接口（你要改的在这里）
    │   ├── user/         ← 用户端接口
    │   └── worker/       ← 工人端接口
    ├── converter/        ← MapStruct 转换器
    ├── dao/
    │   ├── entity/       ← 数据库实体类（DO = Data Object）
    │   ├── mapper/       ← MyBatis-Plus Mapper
    │   └── repository/   ← ES 仓库
    ├── dto/              ← 返回给前端的数据对象
    ├── enums/            ← 枚举
    ├── properties/       ← 配置属性类
    ├── request/          ← 前端请求参数对象
    └── service/
        ├── I*.java       ← Service 接口
        └── impl/         ← Service 实现类
```

---

## 三、CityDirectory 是要干什么的

### 3.1 业务背景

"城市目录" (`city_directory` 表) 存储的是全国省市两级行政区划数据。它是一个**基础数据表**，不直接面向用户，但其他功能（如开通区域）会依赖它来选择城市。

运营人员在后台需要能够：

1. **分页查看**所有城市数据
2. **新增**一个城市
3. **删除**一个城市
4. **启用或禁用**某个城市
5. **查看单个城市**的详细信息

### 3.2 数据库表结构

表名：`city_directory`

| 字段 | 类型 | 说明 | 举例 |
|------|------|------|------|
| id | varchar | 主键（手动指定） | "110000" |
| parent_code | varchar | 父级城市编码 | "0"（省份的父级是0） |
| type | char(1) | 1=省份, 2=市级 | "1" |
| city_name | varchar | 城市名称 | "北京市" |
| city_code | varchar | 城市编码 | "110000" |
| sort_num | int | 排序号，数字越小越靠前 | 1 |
| pinyin_initial | varchar | 拼音首字母，用于按字母检索 | "B" |

**注意**：这个表的主键 `id` 是 `IdType.INPUT`，意味着新增时需要你手动指定 id，而不是数据库自动生成。

---

## 四、动手前的准备：确认已有代码

打开项目，你会在以下位置看到这些文件。它们已经存在了，你不用动它们：

### 实体类：`CityDirectoryDO.java`

路径：`dao/entity/CityDirectoryDO.java`

这个类已经把 `city_directory` 表的每一列映射成了 Java 字段。Lombok 的 `@Data` 注解会自动生成所有字段的 getter/setter。

**关键注解解释**：

- `@TableName("city_directory")` → 告诉 MyBatis-Plus 这个类对应哪张表
- `@TableId(value = "id", type = IdType.INPUT)` → id 是主键，手动指定值
- `@Data` → Lombok 自动生成 get/set/toString/equals/hashCode

### Mapper 接口：`CityDirectoryMapper.java`

路径：`dao/mapper/CityDirectoryMapper.java`

它继承了 `BaseMapper<CityDirectoryDO>`，这意味着它自动拥有了增删改查的基本方法，比如 `selectById`、`insert`、`deleteById` 等，不需要你写任何 SQL。

### 这些是你要编写的文件

| 文件 | 路径 | 状态 |
|------|------|------|
| CityDirectoryPageRequest.java | request/ | 需要新建 |
| CityDirectoryCommand.java | request/ | 需要新建 |
| CityDirectoryDTO.java | dto/ | 需要新建 |
| CityDirectoryConverter.java | converter/ | 需要新建 |
| ICityDirectoryService.java | service/ | 已有骨架，填充方法 |
| CityDirectoryServiceImpl.java | service/impl/ | 已有骨架，填充逻辑 |
| CityDirectoryController.java | controller/operation/ | 已有骨架，填充接口 |

---

## 五、开始写代码

### 5.1 创建 Request 类（前端传什么给后端）

#### CityDirectoryPageRequest —— 分页查询的请求参数

新建文件：`src/main/java/com/cskaoyan/duolai/clean/housekeeping/request/CityDirectoryPageRequest.java`

```java
package com.cskaoyan.duolai.clean.housekeeping.request;

import com.cskaoyan.duolai.clean.common.model.dto.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 城市目录分页查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("城市目录分页查询")
public class CityDirectoryPageRequest extends PageRequest {

    @ApiModelProperty("城市名称（模糊搜索）")
    private String cityName;

    @ApiModelProperty("城市编码")
    private String cityCode;

    @ApiModelProperty("城市类型：1=省份，2=市级")
    private String type;
}
```

**解释**：
- `extends PageRequest` 是项目中写好的分页基类，包含 `pageNo`（页码）、`pageSize`（每页条数）、排序字段等。
- `cityName`、`cityCode`、`type` 是筛选条件。

#### CityDirectoryCommand —— 新增/编辑的请求参数

新建文件：`src/main/java/com/cskaoyan/duolai/clean/housekeeping/request/CityDirectoryCommand.java`

```java
package com.cskaoyan.duolai.clean.housekeeping.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 城市目录新增/编辑请求
 */
@Data
@ApiModel("城市目录新增/编辑")
public class CityDirectoryCommand {

    @ApiModelProperty("主键（城市编码作为id）")
    private String id;

    @ApiModelProperty("父级城市编码")
    private String parentCode;

    @ApiModelProperty("城市类型：1=省份，2=市级")
    private String type;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("城市编码")
    private String cityCode;

    @ApiModelProperty("排序字段")
    private Integer sortNum;

    @ApiModelProperty("拼音首字母")
    private String pinyinInitial;
}
```

---

### 5.2 创建 DTO 类（后端返回什么给前端）

DTO（Data Transfer Object）是后端返回给前端的数据格式。**不要把数据库实体直接返回给前端**，原因有二：
1. 实体可能包含敏感字段，不应该暴露
2. 前端需要的字段和数据库字段可能不完全一致

新建文件：`src/main/java/com/cskaoyan/duolai/clean/housekeeping/dto/CityDirectoryDTO.java`

```java
package com.cskaoyan.duolai.clean.housekeeping.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 城市目录响应数据
 */
@Data
@ApiModel("城市目录响应")
public class CityDirectoryDTO {

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("父级城市编码")
    private String parentCode;

    @ApiModelProperty("城市类型：1=省份，2=市级")
    private String type;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("城市编码")
    private String cityCode;

    @ApiModelProperty("排序字段")
    private Integer sortNum;

    @ApiModelProperty("拼音首字母")
    private String pinyinInitial;

    /**
     * 根据type动态展示的文字："省份" 或 "市级"
     */
    @ApiModelProperty("城市类型文字描述")
    private String typeName;

    /**
     * 组装 typeName 的 getter
     */
    public String getTypeName() {
        if ("1".equals(this.type)) {
            return "省份";
        } else if ("2".equals(this.type)) {
            return "市级";
        }
        return "未知";
    }
}
```

**解释**：`typeName` 是一个计算字段，根据 `type` 的值自动生成中文描述。前端表格里直接就能显示"省份"而不是"1"。

---

### 5.3 创建 Converter（对象转换器）

用 MapStruct 自动生成 DO 和 DTO 之间的转换代码，不用手写。

新建文件：`src/main/java/com/cskaoyan/duolai/clean/housekeeping/converter/CityDirectoryConverter.java`

```java
package com.cskaoyan.duolai.clean.housekeeping.converter;

import com.cskaoyan.duolai.clean.housekeeping.dao.entity.CityDirectoryDO;
import com.cskaoyan.duolai.clean.housekeeping.dto.CityDirectoryDTO;
import com.cskaoyan.duolai.clean.housekeeping.request.CityDirectoryCommand;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 城市目录对象转换器
 * MapStruct 会在编译时自动生成实现类
 */
@Mapper(componentModel = "spring")
public interface CityDirectoryConverter {

    /**
     * 请求参数 → 数据库实体
     */
    CityDirectoryDO commandToDO(CityDirectoryCommand command);

    /**
     * 数据库实体 → 响应 DTO
     */
    CityDirectoryDTO doToDTO(CityDirectoryDO cityDirectoryDO);

    /**
     * 数据库实体列表 → 响应 DTO 列表
     */
    List<CityDirectoryDTO> doListToDTOList(List<CityDirectoryDO> doList);
}
```

**关键点**：`@Mapper(componentModel = "spring")` 让 MapStruct 生成的实现类交给 Spring 管理，这样你才能用 `@Resource` 注入它。

---

### 5.4 编写 Service 接口

打开已有的 `ICityDirectoryService.java`，参照项目里 `IRegionService` 的写法来填充方法签名：

```java
package com.cskaoyan.duolai.clean.housekeeping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.CityDirectoryDO;
import com.cskaoyan.duolai.clean.housekeeping.dto.CityDirectoryDTO;
import com.cskaoyan.duolai.clean.housekeeping.request.CityDirectoryCommand;
import com.cskaoyan.duolai.clean.housekeeping.request.CityDirectoryPageRequest;

/**
 * 城市目录服务接口
 */
public interface ICityDirectoryService extends IService<CityDirectoryDO> {

    /**
     * 分页查询城市目录
     */
    PageDTO<CityDirectoryDTO> getPage(CityDirectoryPageRequest pageRequest);

    /**
     * 新增城市
     */
    void addCity(CityDirectoryCommand command);

    /**
     * 删除城市
     */
    void deleteCity(String id);

    /**
     * 启用城市
     */
    void enableCity(String id);

    /**
     * 禁用城市
     */
    void disableCity(String id);

    /**
     * 根据id查询单个城市
     */
    CityDirectoryDTO getById(String id);
}
```

**解释**：`extends IService<CityDirectoryDO>` 是 MyBatis-Plus 提供的接口，继承后就能用 `save`、`getById`、`list` 等通用方法。

---

### 5.5 编写 Service 实现（核心部分）

打开已有的 `CityDirectoryServiceImpl.java`，替换为以下代码。**这是整个开发最重要的一步。**

```java
package com.cskaoyan.duolai.clean.housekeeping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cskaoyan.duolai.clean.common.expcetions.ForbiddenOperationException;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.housekeeping.converter.CityDirectoryConverter;
import com.cskaoyan.duolai.clean.housekeeping.dao.entity.CityDirectoryDO;
import com.cskaoyan.duolai.clean.housekeeping.dao.mapper.CityDirectoryMapper;
import com.cskaoyan.duolai.clean.housekeeping.dto.CityDirectoryDTO;
import com.cskaoyan.duolai.clean.housekeeping.request.CityDirectoryCommand;
import com.cskaoyan.duolai.clean.housekeeping.request.CityDirectoryPageRequest;
import com.cskaoyan.duolai.clean.housekeeping.service.ICityDirectoryService;
import com.cskaoyan.duolai.clean.mysql.utils.PageUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 城市目录服务实现
 */
@Service
public class CityDirectoryServiceImpl
        extends ServiceImpl<CityDirectoryMapper, CityDirectoryDO>
        implements ICityDirectoryService {

    @Resource
    private CityDirectoryConverter cityDirectoryConverter;

    // ==================== 1. 分页查询 ====================

    /**
     * 分页查询城市目录
     *
     * 流程：
     * 1. 把前端的 PageRequest 转成 MyBatis-Plus 的 Page 对象
     * 2. 构建查询条件（按城市名称模糊搜索、按编码和类型筛选）
     * 3. 执行分页查询
     * 4. DO 列表转 DTO 列表
     * 5. 包装成统一的 PageDTO 返回
     */
    @Override
    public PageDTO<CityDirectoryDTO> getPage(CityDirectoryPageRequest pageRequest) {
        // 第一步：构建 MyBatis-Plus 分页对象
        Page<CityDirectoryDO> page = PageUtils.parsePageQuery(pageRequest, CityDirectoryDO.class);

        // 第二步：构建查询条件
        LambdaQueryWrapper<CityDirectoryDO> wrapper = Wrappers.<CityDirectoryDO>lambdaQuery();

        // 城市名称模糊搜索 → SQL: WHERE city_name LIKE "%北京%"
        if (StringUtils.hasText(pageRequest.getCityName())) {
            wrapper.like(CityDirectoryDO::getCityName, pageRequest.getCityName());
        }

        // 城市编码精确匹配 → SQL: AND city_code = "110000"
        if (StringUtils.hasText(pageRequest.getCityCode())) {
            wrapper.eq(CityDirectoryDO::getCityCode, pageRequest.getCityCode());
        }

        // 类型精确匹配 → SQL: AND type = "1"
        if (StringUtils.hasText(pageRequest.getType())) {
            wrapper.eq(CityDirectoryDO::getType, pageRequest.getType());
        }

        // 默认按 sort_num 升序排列
        wrapper.orderByAsc(CityDirectoryDO::getSortNum);

        // 第三步：执行分页查询
        Page<CityDirectoryDO> resultPage = baseMapper.selectPage(page, wrapper);

        // 第四步：DO 列表转 DTO 列表
        List<CityDirectoryDTO> dtoList = cityDirectoryConverter.doListToDTOList(resultPage.getRecords());

        // 第五步：包装成统一的 PageDTO 返回
        return PageUtils.toPage(resultPage, dtoList);
    }

    // ==================== 2. 新增城市 ====================

    /**
     * 新增城市
     *
     * 流程：
     * 1. 校验城市编码是否已存在
     * 2. Command 转 DO
     * 3. 插入数据库
     */
    @Override
    @Transactional
    public void addCity(CityDirectoryCommand command) {
        // 第一步：校验城市编码不能重复
        LambdaQueryWrapper<CityDirectoryDO> wrapper = Wrappers.<CityDirectoryDO>lambdaQuery()
                .eq(CityDirectoryDO::getCityCode, command.getCityCode());
        Long count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new ForbiddenOperationException("城市编码已存在，请勿重复添加");
        }

        // 第二步：Command 转 DO
        CityDirectoryDO cityDO = cityDirectoryConverter.commandToDO(command);

        // 第三步：插入数据库
        baseMapper.insert(cityDO);
    }

    // ==================== 3. 删除城市 ====================

    /**
     * 删除城市
     *
     * 流程：
     * 1. 校验城市是否存在
     * 2. 执行删除
     */
    @Override
    @Transactional
    public void deleteCity(String id) {
        CityDirectoryDO city = baseMapper.selectById(id);
        if (city == null) {
            throw new ForbiddenOperationException("城市不存在");
        }
        baseMapper.deleteById(id);
    }

    // ==================== 4. 启用/禁用城市 ====================

    /**
     * 注意：city_directory 表目前没有 status 字段！
     * 如果业务确实需要启用/禁用功能，需要先在数据库加字段：
     * ALTER TABLE city_directory ADD COLUMN status INT DEFAULT 1 COMMENT "状态：0=禁用，1=启用";
     * 并在 CityDirectoryDO 中添加对应的 status 属性。
     *
     * 以下代码假设 status 字段已存在（0=禁用, 1=启用）。
     * 如果还没加字段，暂时用 TODO 占位，等加好字段后取消注释。
     */

    @Override
    public void enableCity(String id) {
        CityDirectoryDO city = baseMapper.selectById(id);
        if (city == null) {
            throw new ForbiddenOperationException("城市不存在");
        }
        // TODO: 等数据库加上 status 字段后取消下面注释
        // city.setStatus(1);
        // baseMapper.updateById(city);
        throw new ForbiddenOperationException("功能开发中，数据库需要先加status字段");
    }

    @Override
    public void disableCity(String id) {
        CityDirectoryDO city = baseMapper.selectById(id);
        if (city == null) {
            throw new ForbiddenOperationException("城市不存在");
        }
        // TODO: 等数据库加上 status 字段后取消下面注释
        // city.setStatus(0);
        // baseMapper.updateById(city);
        throw new ForbiddenOperationException("功能开发中，数据库需要先加status字段");
    }

    // ==================== 5. 查询单个城市 ====================

    @Override
    public CityDirectoryDTO getById(String id) {
        CityDirectoryDO city = baseMapper.selectById(id);
        if (city == null) {
            throw new ForbiddenOperationException("城市不存在");
        }
        return cityDirectoryConverter.doToDTO(city);
    }
}
```

**逐段解释**：

**关于 `@Service`**：告诉 Spring "请帮我管理这个类的实例"。之后在 Controller 里用 `@Resource` 就能拿到它。

**关于 `extends ServiceImpl<CityDirectoryMapper, CityDirectoryDO>`**：MyBatis-Plus 提供的基础实现类，继承后就能用 `baseMapper` 字段来操作数据库。

**关于 `LambdaQueryWrapper`**：MyBatis-Plus 的查询条件构造器。好处是**类型安全**——写 `CityDirectoryDO::getCityName` 而不是字符串 `"city_name"`，字段名写错编译器会直接报错。等价 SQL：

```sql
SELECT * FROM city_directory
WHERE city_name LIKE "%北京%"
  AND city_code = "110000"
  AND type = "1"
ORDER BY sort_num ASC
LIMIT 0, 10
```

**关于 `@Transactional`**：加在方法上表示这个方法里的数据库操作是一个事务——要么全部成功，要么全部回滚。新增和删除涉及数据变更，必须加。

**关于异常处理**：项目使用 `ForbiddenOperationException` 向前端返回业务错误（如"城市编码重复"）。Spring 的全局异常处理器会自动捕获它，转成统一的错误响应。

---

### 5.6 编写 Controller（对外的接口）

Controller 是前后端交互的入口。前端发 HTTP 请求，Controller 接收并调用 Service。

打开已有的 `CityDirectoryController.java`，替换为以下代码：

```java
package com.cskaoyan.duolai.clean.housekeeping.controller.operation;

import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.housekeeping.dto.CityDirectoryDTO;
import com.cskaoyan.duolai.clean.housekeeping.request.CityDirectoryCommand;
import com.cskaoyan.duolai.clean.housekeeping.request.CityDirectoryPageRequest;
import com.cskaoyan.duolai.clean.housekeeping.service.ICityDirectoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 运营端 - 城市目录管理接口
 */
@RestController("operationCityDirectoryController")
@RequestMapping("/operation/city")
@Api(tags = "运营端 - 城市目录管理")
public class CityDirectoryController {

    @Resource
    private ICityDirectoryService cityDirectoryService;

    // ==================== 1. 分页查询 ====================

    @GetMapping("/page")
    @ApiOperation("城市目录分页查询")
    public PageDTO<CityDirectoryDTO> getPage(CityDirectoryPageRequest pageRequest) {
        return cityDirectoryService.getPage(pageRequest);
    }

    // ==================== 2. 新增城市 ====================

    @PostMapping
    @ApiOperation("新增城市")
    public void addCity(@RequestBody CityDirectoryCommand command) {
        cityDirectoryService.addCity(command);
    }

    // ==================== 3. 删除城市 ====================

    @DeleteMapping("/{id}")
    @ApiOperation("删除城市")
    public void deleteCity(@PathVariable("id") String id) {
        cityDirectoryService.deleteCity(id);
    }

    // ==================== 4. 启用城市 ====================

    @PutMapping("/enable/{id}")
    @ApiOperation("启用城市")
    public void enableCity(@PathVariable("id") String id) {
        cityDirectoryService.enableCity(id);
    }

    // ==================== 5. 禁用城市 ====================

    @PutMapping("/disable/{id}")
    @ApiOperation("禁用城市")
    public void disableCity(@PathVariable("id") String id) {
        cityDirectoryService.disableCity(id);
    }

    // ==================== 6. 查询单个城市 ====================

    @GetMapping("/{id}")
    @ApiOperation("查询单个城市详情")
    public CityDirectoryDTO getById(@PathVariable("id") String id) {
        return cityDirectoryService.getById(id);
    }
}
```

**API 一览表**：

| 功能 | 请求方式 | URL | 参数 |
|------|---------|-----|------|
| 分页查询 | GET | `/operation/city/page` | URL 参数：pageNo, pageSize, cityName, cityCode, type |
| 新增城市 | POST | `/operation/city` | JSON Body：CityDirectoryCommand |
| 删除城市 | DELETE | `/operation/city/{id}` | 路径参数：id |
| 启用城市 | PUT | `/operation/city/enable/{id}` | 路径参数：id |
| 禁用城市 | PUT | `/operation/city/disable/{id}` | 路径参数：id |
| 查询单个 | GET | `/operation/city/{id}` | 路径参数：id |

**注解速查**：

| 注解 | 含义 |
|------|------|
| `@RestController` | 声明这是 REST 接口类，返回值自动转 JSON |
| `@RequestMapping("/operation/city")` | 这个类下所有接口的统一前缀 |
| `@GetMapping` | 处理 GET 请求（查询） |
| `@PostMapping` | 处理 POST 请求（新增） |
| `@DeleteMapping` | 处理 DELETE 请求（删除） |
| `@PutMapping` | 处理 PUT 请求（更新） |
| `@PathVariable` | 从 URL 路径中取参数，如 `/{id}` |
| `@RequestBody` | 从请求体中取 JSON 参数 |
| `@Api / @ApiOperation` | Swagger 接口文档注解 |
| `@Resource` | 注入 Spring 管理的 Bean |

**特别注意**：我把 `@RestController` 的 name 改成了 `"operationCityDirectoryController"`。原有代码写的是 `"operationRegionController"`，和 RegionController 同名，会导致 Spring 启动报错。

---

## 六、关于启用/禁用功能的说明

`city_directory` 表目前没有 `status` 字段。如果你确实需要这个功能，分三步走：

**第一步**：在数据库执行：
```sql
ALTER TABLE city_directory ADD COLUMN status INT DEFAULT 1 COMMENT "状态：0=禁用，1=启用";
```

**第二步**：在 `CityDirectoryDO.java` 中加字段：
```java
/**
 * 状态：0=禁用，1=启用
 */
private Integer status;
```

**第三步**：取消 `CityDirectoryServiceImpl` 中 `enableCity` 和 `disableCity` 方法里的 TODO 注释即可。

如果业务上不需要启用/禁用功能，直接把两个方法和对应的 Controller 接口删掉即可。

---

## 七、如何验证你的代码

### 7.1 启动项目

在 IDEA 中运行 `HouseKeepingServiceApplication.java` 的 main 方法。

先确认以下服务已启动：
- MySQL
- Nacos
- Redis（如果用到）

### 7.2 用 Swagger 测试

项目集成了 Swagger。启动后访问：

```
http://localhost:你的端口/swagger-ui.html
```

或：

```
http://localhost:你的端口/doc.html
```

页面上找到"运营端 - 城市目录管理"分组，直接在线调试每个接口。

### 7.3 用 curl 测试

**分页查询**：
```bash
curl "http://localhost:8080/operation/city/page?pageNo=1&pageSize=10"
```

**新增城市**：
```bash
curl -X POST http://localhost:8080/operation/city -H "Content-Type: application/json" -d "{\"id\":\"440000\",\"parentCode\":\"0\",\"type\":\"1\",\"cityName\":\"广东省\",\"cityCode\":\"440000\",\"sortNum\":1,\"pinyinInitial\":\"G\"}"
```

**查询单个**：
```bash
curl http://localhost:8080/operation/city/440000
```

**删除**：
```bash
curl -X DELETE http://localhost:8080/operation/city/440000
```

### 7.4 单元测试

在 `src/test/java` 下创建测试类：

```java
package com.cskaoyan.duolai.clean.housekeeping.service.impl;

import com.cskaoyan.duolai.clean.housekeeping.request.CityDirectoryCommand;
import com.cskaoyan.duolai.clean.housekeeping.service.ICityDirectoryService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CityDirectoryServiceImplTest {

    @Resource
    private ICityDirectoryService cityDirectoryService;

    @Test
    void testAddAndQuery() {
        // 1. 新增
        CityDirectoryCommand command = new CityDirectoryCommand();
        command.setId("999999");
        command.setCityCode("999999");
        command.setCityName("测试城市");
        command.setType("2");
        command.setParentCode("440000");
        command.setSortNum(99);
        cityDirectoryService.addCity(command);

        // 2. 查询
        var result = cityDirectoryService.getById("999999");
        assertNotNull(result);
        assertEquals("测试城市", result.getCityName());

        // 3. 清理
        cityDirectoryService.deleteCity("999999");
    }
}
```

---

## 八、开发检查清单

改完代码后，逐项确认：

- [ ] `CityDirectoryController` 的 `@RestController` name 是否唯一（不能和任何 Controller 重名）
- [ ] `CityDirectoryController` 的 `@RequestMapping` 路径是否合理（建议 `/operation/city`）
- [ ] 新增/删除方法是否加了 `@Transactional`
- [ ] 新增时是否校验了 cityCode 唯一性
- [ ] 删除/查询前是否校验了数据存在
- [ ] 启用/禁用功能是否确认了数据库有 status 字段
- [ ] PageRequest 是否继承了项目的 `PageRequest` 基类
- [ ] DTO 是否有 Swagger 的 `@ApiModel` / `@ApiModelProperty` 注解
- [ ] Converter 是否加了 `@Mapper(componentModel = "spring")`
- [ ] Service 实现类是否加了 `@Service`
- [ ] 启动项目后 Swagger 上能否看到新接口
- [ ] curl 能否正常调用

---

## 九、常见问题

**Q1：启动报错 "There is already a bean named 'operationRegionController'"**

A：`CityDirectoryController` 原来的 `@RestController("operationRegionController")` 和 `RegionController` 重名了。把 name 改成 `"operationCityDirectoryController"` 即可。

**Q2：新增时报空指针异常**

A：检查 `CityDirectoryConverter` 是否加了 `@Mapper(componentModel = "spring")`，以及 Service 里注入时用的是不是 `@Resource` 而不是 `new`。

**Q3：Swagger 上看不到我的接口**

A：确认 Controller 类上加了 `@Api(tags = "...")`，方法上加了 `@ApiOperation("...")`。如果还看不到，检查是否有权限拦截器过滤了请求。

**Q4：分页查询返回的数据为空，但我数据库里有数据**

A：检查 `CityDirectoryPageRequest` 的字段名是否和 `LambdaQueryWrapper` 里引用的字段名一致。比如你传了 `cityName` 但代码里写的却是 `CityDirectoryDO::getName`，肯定查不到。

**Q5：启用/禁用接口报错"功能开发中"**

A：这是预期的。`city_directory` 表还没有 `status` 字段，需要按第六章的步骤加上。

**Q6：IDEA 里 MapStruct 报红**

A：需要安装 MapStruct 插件，并且在 pom.xml 中确认有 `mapstruct` 和 `mapstruct-processor` 两个依赖。编译一次后错误就会消失。

---

## 十、下一步可以做什么

完成本篇文章的开发后，你可以继续：

1. **加缓存**：城市目录是基础数据，变化频率低，适合加 Redis 缓存。参考项目里 `RegionServiceImpl` 中的 `@Cacheable` 注解用法。
2. **数据导入**：写一个接口或脚本，从民政部公开的行政区划数据批量导入到 `city_directory` 表。
3. **树形查询**：城市目录有父子关系（parentCode），可以写一个接口返回完整的省市树形结构，方便前端做级联选择。
4. **单元测试**：为每个方法写完整的单元测试，后续改代码不怕改坏。
