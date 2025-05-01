package org.example;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadDoc {
    public static void main(String[] args) {
        try {
            // 定义文件路径
            File file = new File("E:/运维资料/日常运维记录.docx");
            // 创建文件输入流
            FileInputStream fis = new FileInputStream(file);
            // 创建 XWPFDocument 对象
            XWPFDocument document = new XWPFDocument(fis);
            // 遍历文档中的段落
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                // 打印段落文本
                System.out.println(paragraph.getText());
            }
            // 关闭文件输入流
            fis.close();
        } catch (IOException e) {
            // 捕获并打印异常信息
            e.printStackTrace();
        }
    }
}
