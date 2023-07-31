package com.hkk.demo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author kang
 * @date 2023/7/31
 */
@Slf4j
public class FfmpegUtils {

    public static byte[] generateThumbnail(String httpUrl) {
        String coverPath = getThumbnailPath(httpUrl);
        if (StringUtils.isNotBlank(coverPath)) {
            byte[] bytes = new byte[0];
            try {
                try (final FileInputStream inputStream = new FileInputStream(coverPath)) {
                    bytes = IOUtils.toByteArray(inputStream);
                }
            } catch (IOException e) {
                log.error("ffmpeg 生成封面失败", e);
            }
            try {
                Files.delete(new File(coverPath).toPath());
            } catch (IOException e) {
                log.error("删除临时文件失败", e);
            }
            if (bytes.length != 0) {
                return bytes;
            }
        }
        throw new RuntimeException("ffmpeg 生成封面失败");
    }

    public static String getThumbnailPath(String videoUrl) {
        // 1. 构造ffmpeg命令
        String ffmpeg = "ffmpeg"; // ffmpeg executable文件路径
        // 文件夹路径要存在，不然汇报错
        String coverPath = "D:/thumbnails/cover234.jpg";
        List<String> command = new ArrayList<>();
        command.add(ffmpeg);
        command.add("-v");
        command.add("quiet");
        command.add("-stats");
        command.add("-i");
        command.add(videoUrl); // 网络视频地址作为输入
        command.add("-ss");
        command.add("00:00:05"); // 从第5秒开始截图
        command.add("-vframes");
        command.add("1"); // 截取1帧
        command.add("-y");
        command.add(coverPath);
        log.info("ffmpeg 命令为{}", String.join(" ", command));
        // 2. 执行命令生成封面
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process;
        try {
            process = builder.start();
            int exitCode = process.waitFor();
            // 3. 判断命令执行结果
            if (exitCode == 0) {
                return coverPath;
            } else {
                log.error("生成封面失败，退出码为{}", exitCode);
            }
        } catch (IOException | InterruptedException e) {
            log.error("ffmpeg 生成封面失败", e);
        }
        throw new RuntimeException("ffmpeg 生成封面失败");
    }

    public static void main(String[] args) {
        // System.out.println(getThumbnailPath("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"));
        System.out.println(generateThumbnail("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"));
    }

}
