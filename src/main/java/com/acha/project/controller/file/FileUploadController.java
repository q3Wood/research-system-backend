package com.acha.project.controller.file;

import com.acha.project.common.BaseResponse;
import com.acha.project.common.ErrorCode;
import com.acha.project.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@Slf4j
@Tag(name = "通用管理-文件上传/下载", description = "处理附件上传与鉴权下载")
public class FileUploadController {

    /**
     * 上传目录绝对路径 (我们把文件存放在项目同级根目录的 uploads/achievements 文件夹里)
     */
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "achievements";

    @PostMapping("/upload")
    @Operation(summary = "上传科研成果附件", description = "返回文件名，用于前端暂存及入库")
    public BaseResponse<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "上传文件不能为空");
        }

        try {
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                originalFilename = "unknown";
            }
            int lastDotIdx = originalFilename.lastIndexOf(".");
            String suffix = lastDotIdx > -1 ? originalFilename.substring(lastDotIdx) : "";
            
            // 为了安全和防重复，生成带原生后缀的随机名
            String newFileName = UUID.randomUUID().toString().replace("-", "") + suffix;

            File destFile = new File(dir, newFileName);
            file.transferTo(destFile);

            // 返回文件名即可，前端后续调用 /file/download/{fileName} 下载
            return BaseResponse.success(newFileName);
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件上传失败!");
        }
    }

    @GetMapping("/download/{fileName}")
    @Operation(summary = "下载/预览科研成果附件", description = "带有 Token 鉴权的文件流下载接口")
    public void downloadFile(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        // 防止路径遍历攻击 (Path Traversal)
        if (fileName.contains("/") || fileName.contains("\\") || fileName.contains("..")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "非法的文件名!");
        }

        File file = new File(UPLOAD_DIR + File.separator + fileName);
        if (!file.exists()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "文件不存在!");
        }

        try (FileInputStream fis = new FileInputStream(file);
             OutputStream os = response.getOutputStream()) {

            // 设置响应头，指定作为附件下载。如果想让浏览器直接预览PDF/图片，可以调整 contentType 和 inline
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));

            byte[] buffer = new byte[1024 * 8];
            int readBytes;
            while ((readBytes = fis.read(buffer)) != -1) {
                os.write(buffer, 0, readBytes);
            }
            os.flush();
        } catch (IOException e) {
            log.error("文件下载失败", e);
            // 这里既然已经把流打开了，抛出异常也无法再返回规范的 JSON，通常只打印日志
        }
    }
}
