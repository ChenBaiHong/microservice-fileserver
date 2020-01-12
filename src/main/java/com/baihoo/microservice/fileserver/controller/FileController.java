package com.baihoo.microservice.fileserver.controller;

import com.baihoo.microservice.fileserver.criteria.CriteriaType;
import com.baihoo.microservice.fileserver.domain.File;
import com.baihoo.microservice.fileserver.service.FileService;
import com.baihoo.microservice.fileserver.utils.MD5Util;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

/**
 * 文件服务器controller
 *
 * @author Administrator
 */
@CrossOrigin(origins = "*", maxAge = 3600) // 允许所有域名访问，响应最大时间
@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${file.upload.domain}")
    private String domain;

    /**
     * 访问文件服务器首页
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/")
    public String index(@RequestParam(value = "async", required = false) boolean async,
                        @RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                        Model model) throws Exception {
        // 展示最新10条数据
        Sort sort = new Sort(Direction.DESC, "uploadDate");
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
        Page<File> page = fileService.listFilesByPage(pageable);
        model.addAttribute("files", page.getContent());
        model.addAttribute("page", page);
        return "index";
    }

    /**
     * 分页携带条件查询文件
     *
     * @param async
     * @param pageIndex
     * @param pageSize
     * @param criteria
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryAction", method = RequestMethod.POST)
    public String index(@RequestParam(value = "async", required = false) boolean async,
                        @RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                        CriteriaType criteriaType,
                        Model model) throws Exception {
        // 展示最新二十条数据
        Sort sort = new Sort(Direction.DESC, "uploadDate");
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
        Page<File> page = fileService.listFilesByPage(pageable, criteriaType.getMeCriteria());
        model.addAttribute("files", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("meCriteria", criteriaType.getMeCriteria());
        return "index";
    }

    /**
     * 分页查询文件
     *
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    @GetMapping("files/{pageIndex}/{pageSize}")
    @ResponseBody
    public List<File> listFilesByPage(@PathVariable int pageIndex, @PathVariable int pageSize) throws Exception {
        return fileService.listFilesByPage(pageIndex, pageSize);
    }

    /**
     * 获取文件片信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("files/{id}")
    @ResponseBody
    public ResponseEntity<Object> serveFile(@PathVariable String id) throws Exception {

        Optional<File> file = fileService.getFileById(id);

        if (file.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + new String(file.get().getName().getBytes("utf-8"), "ISO-8859-1"))
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
                    .body(file.get().getContent().getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }

    }

    /**
     * 在线显示文件
     *
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/view/{id}")
    @ResponseBody
    public ResponseEntity<Object> serveFileOnline(@PathVariable String id) throws Exception {

        Optional<File> file = fileService.getFileById(id);

        if (file.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + file.get().getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, file.get().getContentType())
                    .header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connection", "close")
                    .body(file.get().getContent().getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }

    }
    /***
     *
     * @Author baihoo.chen
     * @Description TODO 在线显示压缩文件
     * @Date 2019/8/29 14:17
     * @Param [id]
     * @return org.springframework.http.ResponseEntity<java.lang.Object>
     **/
    @GetMapping("/view/compress/{id}")
    @ResponseBody
    public ResponseEntity<Object> serveCompressFileOnline(@PathVariable String id) throws Exception {
        Optional<File> file = fileService.getFileById(id);
        if (file.isPresent() && file.get().getCompressContent()!=null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "fileName=\"" + file.get().getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, file.get().getContentType())
                    .header(HttpHeaders.CONTENT_LENGTH, file.get().getCompressContent().getData().length + "").header("Connection", "close")
                    .body(file.get().getCompressContent().getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not fount");
        }
    }
    /**
     * 上传
     *
     * @param file
     * @param redirectAttributes 转向跳转携带值
     * @return
     * @throws Exception
     */
    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {

        try {
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || "".equals(originalFileName)) {
                redirectAttributes.addFlashAttribute("message", "请不要上传空文件！");
                return "redirect:/";
            }
            File f = new File(originalFileName, file.getContentType(), file.getSize(),
                    new Binary(file.getBytes()));
            Binary binary = fileCompress(file);
            if (binary!=null){
                f.setCompressContent(binary);
            }
            f.setMd5(MD5Util.getMD5(file.getInputStream()));
            fileService.saveFile(f);
        } catch (IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Your " + file.getOriginalFilename() + " is wrong!");
            return "redirect:/";
        }

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    /**
     * 上传文件的接口
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload2")
    @ResponseBody
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        File returnFile = null;
        try {
            File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(),
                    new Binary(file.getBytes()));
            f.setMd5(MD5Util.getMD5(file.getInputStream()));
            Binary binary = fileCompress(file);
            if (binary!=null){
                f.setCompressContent(binary);
            }
            returnFile = fileService.saveFile(f);
            // 展示图片的url地址 https://www.jklz.online/lgyyfile
            String path = domain + "/view/" + returnFile.getId();
            String compressPath = domain + "/view/compress/" + returnFile.getId();
            return ResponseEntity.status(HttpStatus.OK).body(path+","+compressPath);
        } catch (IOException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    /***
     *
     * @Author baihoo.chen
     * @Description TODO 文件压缩处理
     * @Date 2019/8/29 14:10
     * @Param [file]
     * @return org.bson.types.Binary
     **/
    private Binary fileCompress(MultipartFile file) throws IOException {
        String[] imgTyes = new String[]{"image/jpeg","image/bmp", "image/jpg", "image/png","image/gif",  "image/tif", "image/pcx", "image/tga", "image/exif", "image/fpx", "image/svg", "image/psd", "image/cdr", "image/pcd", "image/dxf", "image/ufo", "image/eps", "image/ai", "image/raw", "image/wmf", "image/webp"};
        if (StringUtils.isNotEmpty(file.getContentType())) {
            String contentType = file.getContentType().toLowerCase();
            for (String imgType : imgTyes) {
                // 1.图片文件压缩处理
                if (imgType.equals(contentType)) {
                    ByteOutputStream byteOutputStream = new ByteOutputStream();
                    Thumbnails.of(file.getInputStream()).scale(0.2f).toOutputStream(byteOutputStream);
                    return new Binary(byteOutputStream.getBytes());
                }
            }
        }
        return null;
    }

    /**
     * @Author baihoo.chen
     * @Description TODO 微信上传文件接口特别处理方式
     * @Date 2019/7/16
     **/
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> handleFileUpload(HttpServletRequest request) throws Exception {
        try {
            //进行分布式对象存储Ambry文件上传
            MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
            MultipartFile file = req.getFile("file");
            //String authorization = request.getHeader("Authorization");
            if (file != null
                    //&& authorization != null
            ) {
                return handleFileUpload(file);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上传文件为空~！");
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    /**
     * 删除文件
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteFile(@PathVariable String id) {

        try {
            fileService.removeFile(id);
            return ResponseEntity.status(HttpStatus.OK).body("DELETE Success!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
