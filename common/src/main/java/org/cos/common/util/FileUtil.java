package org.cos.common.util;

import org.cos.common.constant.DataConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class FileUtil {
    public static Integer getFileLineNumber(String filePath){
        String type = StringUtils.split(filePath,".")[1];
        switch (type) {
            case "csv":
                try {
                    FileReader fileReader = new FileReader(filePath);
                    LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
                    lineNumberReader.skip(Long.MAX_VALUE);
                    Integer lineNumber = lineNumberReader.getLineNumber();
                    lineNumberReader.close();
                    fileReader.close();
                    return lineNumber;
                } catch (IOException e) {
                    log.info("{}-IOException",filePath,e.getMessage());
                }
            case "xls":
                try {
                    File excel = new File(filePath);
                    Workbook wb;
                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
                    wb = new HSSFWorkbook(fis);
                    Sheet sheet = wb.getSheetAt(0);//读取sheet 0
                    return  sheet.getLastRowNum()+1;
                }catch (Exception e){
                    log.info("{}-IOException",filePath,e.getMessage());
                }
            case "xlsx":
                try {
                    File excel = new File(filePath);
                    Workbook wb= new XSSFWorkbook(excel);
                    Sheet sheet = wb.getSheetAt(0);//读取sheet 0
                    return sheet.getLastRowNum()+1;
                }catch (Exception e){
                    log.info("{}-IOException",filePath,e.getMessage());
                }
        }
        return -0;
    }
    public static boolean isFileExists(String filePath){
        File file = new File(filePath);
        return file.exists();
    }
    public static String getFileContent(String filePath){
        String type = StringUtils.split(filePath,".")[1];
        StringBuilder sb = new StringBuilder();

        switch (type) {
            case "csv":
                try {
                    File file = new File(filePath);
                    if (!file.exists()){
                        log.info("{}-不存在",filePath);
                        return null;
                    }
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),charset(file)));
                    String str;
                    while((str = bufferedReader.readLine())!=null) {
                        sb.append(str+"\t\n");
                    }
                    bufferedReader.close();
                } catch (IOException e) {
                    log.info("{}-IOException: {}",filePath,e.getMessage());
                }
                return  sb.toString();
            case "xls":
            case "xlsx":
                List<Row> rows = getXlsFileContent(filePath, 0);
                List<String> content = rows.stream().map(row -> {
                    sb.append(row.getCell(0).toString());
                    for (int i = 1; i < row.getLastCellNum(); i++) {
                        sb.append(",").append(row.getCell(i));
                    }

                    return sb.toString();
                }).collect(Collectors.toList());
                return StringUtils.join(content.toArray(),"\t\n");
        }
        return null;
    }

    public static List<String> getFileContent(String filePath,Integer severalLines) {
        List<String> list = new ArrayList<>();
        String type = StringUtils.split(filePath,".")[1];
        switch (type) {
            case "csv":
                try {
                    File file = new File(filePath);
                    if (!file.exists()){
                        log.info("{}-不存在",filePath);
                        return list;
                    }
                    String charsetName = charset(file);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),charsetName));
                    String str;
                    while((str = bufferedReader.readLine())!=null) {
                        list.add(str);
                        if (severalLines!=null&&severalLines>0){
                            if (list.size()==severalLines)
                                break;
                        }
                    }
                    bufferedReader.close();
                } catch (IOException e) {
                    log.info("{}-IOException: {}",filePath,e.getMessage());
                }
                return list;
            case "xls":
            case "xlsx":

                List<Row> rows = getXlsFileContent(filePath, severalLines);
                list = rows.stream().map(row -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append(row.getCell(0).toString());
                    for (int i =1 ; i < row.getLastCellNum(); i++) {
                        sb.append(",").append(row.getCell(i));
                    }
                    return sb.toString();
                }).collect(Collectors.toList());
                return list;
        }
        return null;
    }
    public static List<Row> getXlsFileContent(String filePath,Integer severalLines) {
        List<Row> list = new ArrayList<>();
        try {
            File excel = new File(filePath);
            String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
            Workbook wb;
            //根据文件后缀（xls/xlsx）进行判断
            if ("xls".equals(split[1])) {
                FileInputStream fis = new FileInputStream(excel);   //文件流对象
                wb = new HSSFWorkbook(fis);
            } else if ("xlsx".equals(split[1])) {
                wb = new XSSFWorkbook(excel);
            } else {
                log.info("get excel Data: ","文件类型错误!");
                return null;
            }
            Sheet sheet = wb.getSheetAt(0);//读取sheet 0
            int firstRowIndex = sheet.getFirstRowNum();   //第一行是列名，所以不读
            int lastRowIndex = sheet.getLastRowNum()< severalLines.intValue()?sheet.getLastRowNum():severalLines.intValue();
            for (int rIndex = firstRowIndex; rIndex < lastRowIndex; rIndex++) {   //遍历行
                Row row = sheet.getRow(rIndex);
                list.add(row);
            }
        }catch (Exception e){
            log.info("{}-IOException: {}",filePath,e.getMessage());
        }
        return list;
    }

    /**
     * 判断文本文件的字符集，文件开头三个字节表明编码格式。
     * @return
     * @throws Exception
     * @throws Exception
     */
    public static String charset(File file) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            bis.mark(0); // 读者注： bis.mark(0);修改为 bis.mark(100);我用过这段代码，需要修改上面标出的地方。
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                bis.close();
                return charset; // 文件编码为 ANSI
            } else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE"; // 文件编码为 Unicode
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE"; // 文件编码为 Unicode big endian
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8"; // 文件编码为 UTF-8
                checked = true;
            }
            bis.reset();
            if (!checked) {
                while ((read = bis.read()) != -1) {
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            // (0x80 - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) { // 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("--文件-> [" + file.getPath() + "] 采用的字符集为: [" + charset + "]");
        return charset;
    }

    public static void writeFile(String filePath,String fileNamePath,List<String> dataList) throws IOException {
        File tempFile=new File(filePath);
        if(!tempFile.exists())
            tempFile.mkdirs();
        FileOutputStream fos=new FileOutputStream(new File(fileNamePath));
        OutputStreamWriter osw=new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter  bw=new BufferedWriter(osw);
        for(String data:dataList){
            bw.write(data+"\t\n");
        }
        bw.close();
        osw.close();
        fos.close();
    }

    public static List<LinkedHashMap<String, Object>> getCsvData(String filePath,Integer pageNo, Integer pageSize){
        List<LinkedHashMap<String, Object>> dataList = new ArrayList<>();
        //        try(Stream<String> curStream= Files.lines(Paths.get(filePath), Charset.forName(charset(new File(filePath))))) {
        try{
//            List<String> list=curStream.skip(pageNo).limit(pageSize+1).collect(Collectors.toList());
            List<String> list = getFileContent(filePath, pageSize + 1);
            if (list.size()==0)
                return dataList;
            String[] fields = list.get(0).split(",");
            log.info(Arrays.toString(fields));
            for(int i=1;i<list.size();i++) {
                String[] data = StringUtils.splitPreserveAllTokens(list.get(i), ",");
                log.info(Arrays.toString(data));
                if (Integer.valueOf(data.length).equals(Integer.valueOf(fields.length)))
                    dataList.add(readValues(data,fields));
            }
        } catch (Exception e) {
            log.info("getCsvData",e);
        }
        return dataList;
    }
    public static List<LinkedHashMap<String, Object>> getXlsData(String filePath,Integer pageNo, Integer pageSize){
        List<LinkedHashMap<String, Object>> dataList = new ArrayList<>();
        try {
            File excel = new File(filePath);
            String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
            Workbook wb;
            //根据文件后缀（xls/xlsx）进行判断
            if ("xls".equals(split[1])) {
                FileInputStream fis = new FileInputStream(excel);   //文件流对象
                wb = new HSSFWorkbook(fis);
            } else if ("xlsx".equals(split[1])) {
                wb = new XSSFWorkbook(excel);
            } else {
                log.info("get excel Data: ","文件类型错误!");
                return null;
            }
            Sheet sheet = wb.getSheetAt(0);//读取sheet 0
            Row header = sheet.getRow(0);
            String[] fields = new String[header.getLastCellNum()];
            if (header != null) {
                for (int cIndex = header.getFirstCellNum(); cIndex < header.getLastCellNum(); cIndex++) {   //遍历列
                    Cell cell = header.getCell(cIndex);
                    if (cell != null) {
                        fields[cIndex]=(cell.toString());
                    }
                }
            }
            int firstRowIndex = sheet.getFirstRowNum() + 1;   //第一行是列名，所以不读
            int lastRowIndex = sheet.getLastRowNum()> DataConstant.READ_DATA_ROW?DataConstant.READ_DATA_ROW: sheet.getLastRowNum();
            for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                Row row = sheet.getRow(rIndex);
                if (row != null) {
                    if (Integer.valueOf(row.getLastCellNum()).equals(Integer.valueOf(header.getLastCellNum()))){
                        LinkedHashMap<String,Object> map = new LinkedHashMap<>();
                        for (int i = 0; i < row.getLastCellNum(); i++) {
                            map.put(header.getCell(i).toString(),row.getCell(i).toString());
                        }
                        dataList.add(map);
                    }
                }
            }
        }catch (Exception e){
            log.info("get excel Data",e);
        }
        return  dataList;
    }

        @Test
    public void test1(){
        String filepath = "/Users/fengxiaoxiao/Downloads/测试数据资源/PIR-PSI用户数据A.csv";
        String filepath1 = "/Users/fengxiaoxiao/Downloads/测试数据资源/PIR-PSI用户数据A-1.xls";
        System.out.println(getFileLineNumber(filepath));
        System.out.println(getFileLineNumber(filepath1));
        System.out.println(getFileContent(filepath,1));
        System.out.println(getFileContent(filepath1,1));

        List<LinkedHashMap<String, Object>> csvData = getCsvData(filepath, 0, 50);
        System.out.println(csvData);
        List<LinkedHashMap<String, Object>> xlsData = getXlsData(filepath1, 0, 0);
        System.out.println(xlsData);

    }


    @Test
    public void test(){
        try {
            String excelPath = "/tmp/data/upload/1/2023021011/32b232dc-5277-43ec-8372-52d956402798.xls";
            File excel = new File(excelPath);

                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
                Workbook wb;
                //根据文件后缀（xls/xlsx）进行判断
                if ("xls".equals(split[1])) {
                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
                    wb = new HSSFWorkbook(fis);
                } else if ("xlsx".equals(split[1])) {
                    wb = new XSSFWorkbook(excel);
                } else {
                    System.out.println("文件类型错误!");
                    return;
                }

                //开始解析
                Sheet sheet = wb.getSheetAt(0);     //读取sheet 0

                int firstRowIndex = sheet.getFirstRowNum();   //第一行是列名
                int lastRowIndex = sheet.getLastRowNum();
                System.out.println("firstRowIndex: " + firstRowIndex);
                System.out.println("lastRowIndex: " + lastRowIndex);

                for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                    System.out.println("rIndex: " + rIndex);
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        int firstCellIndex = row.getFirstCellNum();
                        int lastCellIndex = row.getLastCellNum();
                        for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
                            Cell cell = row.getCell(cIndex);
                            if (cell != null) {
                                System.out.println(cell.toString());
                            }
                        }
                    }
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static LinkedHashMap<String,Object> readValues(String[] values, String[] headers){
        LinkedHashMap<String,Object> map = new LinkedHashMap<>();
        for (int i = 0; i < headers.length; i++) {
            map.put(headers[i],values[i]);
        }
        return map;
    }
    /**
     * 计算文件hash值
     */
    public static String hashFile(File file) throws Exception {
        FileInputStream fis = null;
        String sha256 = null;
        try {
            fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte buffer[] = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            byte[] digest = md.digest();
            sha256 = byte2hexLower(digest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("计算文件hash值错误");
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sha256;
    }

    private static String byte2hexLower(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int i = 0; i < b.length; i++) {
            stmp = Integer.toHexString(b[i] & 0XFF);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }

    public static String md5HashCode(File file) {
        try {
            InputStream fis = new FileInputStream(file);
            //拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest md = MessageDigest.getInstance("MD5");

            //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            fis.close();
            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes  = md.digest();
            BigInteger bigInt = new BigInteger(1, md5Bytes);//1代表绝对值
            return bigInt.toString(16);//转换为16进制
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static List<String> getFileContentData(String filePath,Integer severalLines){
        List<String> list = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (!file.exists()){
                log.info("{}-不存在",filePath);
                return list;
            }
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                list.add(sc.nextLine());
                if (severalLines!=null&&severalLines>0){
                    if (list.size()==severalLines)
                        break;
                }
            }
        } catch (IOException e) {
            log.info("{}-IOException: {}",filePath,e.getMessage());
        }
        return list;
    }



    public static void main(String[] args) throws Exception {
//        System.out.println(FileUtil.getCsvData("E://x.csv",0,50));
//        File file = new File("/Users/zhongziqian/Downloads/7a1ff80f-9e00-48f1-9a46-338fdc5f3aa9.csv");
        long start = System.currentTimeMillis();
        File file = new File("/Users/zhongziqian/Downloads/c0e309f0-0924-4b8d-84e1-947a1617aafc.csv");
        System.out.println(hashFile(file));
        long end1 = System.currentTimeMillis();
        System.out.println(end1 - start);
        System.out.println(md5HashCode(file));
        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start);
    }
}
