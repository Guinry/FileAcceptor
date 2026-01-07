package com.guinry.fileacceptor.controller;

import com.guinry.fileacceptor.entity.FileUploadResponse;
import com.guinry.fileacceptor.result.ApiResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/ecs/upload")
public class EcsFileController {

    @Value("${upload.base-dir}")
    private String baseDir;

    @PostMapping("/{projectName}")
    public ApiResult<FileUploadResponse> upload(
            @PathVariable String projectName,
            @RequestParam("file") MultipartFile file) {

        // 1. 校验项目名（防路径穿越）
        if (!projectName.matches("[a-zA-Z0-9_-]+")) {
            return ApiResult.fail(4005, "项目名非法");
        }

        // 2. 校验文件
        if (file == null || file.isEmpty()) {
            return ApiResult.fail(4001, "文件不能为空");
        }

        String originalName = file.getOriginalFilename();
        if (originalName == null) {
            return ApiResult.fail(4002, "文件名非法");
        }

        // 3. 后缀
        String suffix = "";
        int idx = originalName.lastIndexOf(".");
        if (idx != -1) {
            suffix = originalName.substring(idx);
        }

        // 4. 生成唯一文件名（解决重名）
        String storedName = UUID.randomUUID()
                + "_" + System.currentTimeMillis()
                + suffix;

        // 5. 项目目录
        File projectDir = new File(baseDir, projectName);
        if (!projectDir.exists() && !projectDir.mkdirs()) {
            return ApiResult.fail(5001, "创建目录失败");
        }

        File dest = new File(projectDir, storedName);

        // 6. 保存文件
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            return ApiResult.fail(5002, "文件保存失败");
        }

        // 7. 返回
        return ApiResult.success(
                new FileUploadResponse(
                        projectName,
                        originalName,
                        storedName,
                        dest.getAbsolutePath(),
                        file.getSize()
                )
        );
    }
}
