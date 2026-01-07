# FileAcceptor 项目说明

## 项目概述
`FileAcceptor` 是一个基于 Spring Boot 的文件上传服务系统，提供安全的文件上传功能，支持项目隔离和文件防路径穿越等安全特性。

## 功能特性

- **安全文件上传**：支持按项目名称隔离存储文件
- **路径穿越防护**：通过项目名正则校验防止路径穿越攻击
- **唯一文件名**：使用 UUID + 时间戳生成唯一文件名避免重名
- **RESTful API**：提供标准的 REST 接口用于文件上传

## 快速开始

### 环境要求
- Java 8+
- Maven 3.6+

### 配置说明
在 `application.properties` 或 [application.yml](file://C:\Users\23869\Desktop\FileAcceptor\target\classes\application.yml) 中配置：
```properties
upload.base-dir=/path/to/upload/directory
```


### API 接口
- **上传接口**：`POST /ecs/upload/{projectName}`
- **参数**：
    - `projectName`：路径参数，项目名称（只允许字母、数字、下划线、横线）
    - `file`：表单参数，上传的文件

## 使用说明

1. 启动应用
2. 通过 POST 请求向 `/ecs/upload/{projectName}` 上传文件
3. 系统会验证项目名称和文件，生成唯一文件名并保存

## 返回格式
成功上传后返回 `FileUploadResponse` 对象，包含：
- 项目名称
- 原始文件名
- 存储文件名
- 文件绝对路径
- 文件大小

## 安全措施
- 项目名正则校验防止路径穿越
- 文件名唯一化处理
- 空文件校验
- 目录自动创建