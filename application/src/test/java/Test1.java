import io.reactivex.disposables.Disposable;
import org.apache.commons.lang3.StringUtils;
import org.cos.application.CosdApplication;
import org.cos.common.util.MailUtil;
import org.cos.common.util.ZipUtils;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.tx.Contract;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.CreateEmailTemplateRequest;
import software.amazon.awssdk.services.sesv2.model.DeleteEmailTemplateRequest;
import software.amazon.awssdk.services.sesv2.model.EmailTemplateContent;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CosdApplication.class)
public class Test1 {
    @Autowired
    private SesV2Client sesV2Client;
    @Test
    public void test1(){
        Date date1 = new Date();
        System.out.println(date1);


        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC); // 指定时区为 UTC

// 转换为 Date 类型
        Date date = Date.from(now.toInstant());
        System.out.println(date);
        System.out.println(StringUtils.toRootLowerCase("0x5fb6AC2aff7B356Ab5Bbd49E544e63557cdB1E13"));
    }

    @Test
    public void test2(){
        String assetType = "computer";
        String property = "name";

        class Asset{
            public String assetType;
            public String name;

            Asset(String assetType,String name){
                this.assetType = assetType;
                this.name = name;
            }

        }


        List<Asset> assets = Arrays.asList(
                new Asset("computer", "XPS13"),
                new Asset("printer", "LaserJet 4000"),
                new Asset("computer", "MacBook Pro"),
                new Asset("computer", "ThinkPad T480")
        );

        String result = assets.stream()                       // 创建 Stream 对象
                .filter(asset -> assetType.equalsIgnoreCase(asset.assetType))    // 过滤符合条件的 Asset 对象
                .map(asset -> asset.name)            // 选取 Asset 对象的属性
                .findFirst()                                          // 返回第一个符合条件的值
                .orElse(null);                                        // 如果没有符合条件的值，则返回 null

        System.out.println("The property of computer is: " + result);
        ZipUtils.sendMain("13156050650@163.com");
    }
    @Test
    public void test3() throws IOException {
        Logger log = LoggerFactory.getLogger(Test1.class);
        String contractAddress="0xe9e7CEA3DedcA5984780Bafc599bD69ADd087D56";
        Web3j web3j = Web3j.build(new HttpService("https://bsc-dataseed.binance.org"));

        //第一步生成的合约实体
            Contract contract;
            Disposable subscribe = null;
//            try {
//                Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
//                String clientVersion = web3ClientVersion.getWeb3ClientVersion();
//                log.info("wen3j subscribe --clientVersion-- :{} ", clientVersion);
//                //设置过滤条件 这个示例是监听最新的1000个块
//                BigInteger blockNumber = web3j.ethBlockNumber().send().getBlockNumber()
//                        .subtract(new BigInteger("28406817"));
//                EthFilter ethFilter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, contractAddress);
                EthFilter ethFilter = new EthFilter(DefaultBlockParameter.valueOf(BigInteger.valueOf(28407017)), DefaultBlockParameterName.LATEST, contractAddress);
//        BigInteger startBlock = BigInteger.valueOf(10000);
//        BigInteger endBlock = web3j.ethBlockNumber().send().getBlockNumber();
//        if (endBlock.subtract(startBlock).compareTo(BigInteger.valueOf(5000)) > 0) {
//            endBlock = startBlock.add(BigInteger.valueOf(11000));
//        }
//
//        EthFilter ethFilter = new EthFilter(
//                new DefaultBlockParameterNumber(startBlock),
//                new DefaultBlockParameterNumber(endBlock),
//                contractAddress
//        );
                //监听哪个事件，合约中的event写了几个参数，这里就写几个，类型对应好
                Event event = new Event("Transfer",
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Address>(true) {},
                                new TypeReference<Address>(true) {},
                                new TypeReference<Uint256>(false) {}));
                ethFilter.addSingleTopic(EventEncoder.encode(event));
                subscribe = web3j.ethLogFlowable(ethFilter).subscribe(tx -> {
                    int newBlock = tx.getBlockNumber().intValue();
                    log.info("wen3j subscribe --newBlock-- :{} ", newBlock);
                    log.info("wen3j subscribe --tx-- :{} ", tx);
                    EventValues eventValues = staticExtractEventParameters(event, tx);
                    //定义接收参数(本示例使用的事件返回了6个，具体按自己合约来)
//                    String address1 = "";
//                    String address2 = "";
//                    int uint1 = 0;
//                    int uint2 = 0;
//                    int uint3 = 0;
//                    int uint4 = 0;
//                    List<Type> indexedValues = eventValues.getIndexedValues();
//                    if (ObjectUtils.isNotEmpty(indexedValues) && indexedValues.size() == 3) {
//                        Type type1 = indexedValues.get(0);
//                        address1 = type1.getValue().toString();
//                        Type type2 = indexedValues.get(1);
//                        address2 = type2.getValue().toString();
//                        Type type3 = indexedValues.get(2);
//                        uint1 = Integer.parseInt(type3.getValue().toString());
//                    }
//                    List<Type> nonIndexedValues = eventValues.getNonIndexedValues();
//                    if (ObjectUtils.isNotEmpty(nonIndexedValues) && nonIndexedValues.size() == 3) {
//                        Type type1 = nonIndexedValues.get(0);
//                        uint2 = Integer.parseInt(type1.getValue().toString());
//                        Type type2 = nonIndexedValues.get(1);
//                        uint3 = Integer.parseInt(type2.getValue().toString());
//                        Type type3 = nonIndexedValues.get(2);
//                        uint4 = Integer.parseInt(type3.getValue().toString());
//                    }
//                    log.info("address1:{};address2:{};uint1:{};uint2:{};uint3:{};uint4:{}",address1, address2, uint1, uint2, uint3, uint4);
                });
//            }catch (Exception e) {
//                e.printStackTrace();
//            }finally {
//                //关闭监听
//                if(ObjectUtils.isNotEmpty(subscribe) && !subscribe.isDisposed()){
//                    subscribe.dispose();
//                }
//            }
    }
    /**
     * 解析log返回的data
     * @param event 合约中定义的事件
     * @param log 监听到的log
     * @return 解析后的数据
     */
    public EventValues staticExtractEventParameters(Event event, Log log) {
        final List<String> topics = log.getTopics();
        String encodedEventSignature = EventEncoder.encode(event);
        if (topics == null || topics.size() == 0 || !topics.get(0).equals(encodedEventSignature)) {
            return null;
        }
        List<Type> indexedValues = new ArrayList<>();
        List<Type> nonIndexedValues = FunctionReturnDecoder.decode(
                log.getData(), event.getNonIndexedParameters());
        List<TypeReference<Type>> indexedParameters = event.getIndexedParameters();
        for (int i = 0; i < indexedParameters.size(); i++) {
            Type value = FunctionReturnDecoder.decodeIndexedValue(
                    topics.get(i + 1), indexedParameters.get(i));
            indexedValues.add(value);
        }
        return new EventValues(indexedValues, nonIndexedValues);
    }
    @Test
    public void test4(){
        String wssUrl = "wss://bsc-testnet.publicnode.com";
        String contractAddress = "0xef49E6ff0aa91D6655a73A736696Bef5E8796031";

        // initialize web socket service
        WebSocketService wss = new WebSocketService(wssUrl, false);
        try {
            wss.connect();
        } catch (Exception e) {
            System.out.println("Error while connecting to WSS service: " + e);
        }

        // build web3j client
        Web3j web3j = Web3j.build(wss);

        // create filter for contract events
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, contractAddress);

        // subscribe to events
        web3j.ethLogFlowable(filter).subscribe(event -> {
            System.out.println("Event received");
            System.out.println(event);
        }, error -> {
            System.out.println("Error: " + error);
        });
    }
    @Test
    public void test5() throws IOException {
        Logger log = LoggerFactory.getLogger(Test1.class);
        String contractAddress="0x401084C7F44f4e2d2945F37bcad2406c24edE223";
        Web3j web3j = Web3j.build(new HttpService("https://endpoints.omniatech.io/v1/bsc/testnet/public"));

        //第一步生成的合约实体
        Contract contract;
        Disposable subscribe = null;
//            try {
//                Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
//                String clientVersion = web3ClientVersion.getWeb3ClientVersion();
//                log.info("wen3j subscribe --clientVersion-- :{} ", clientVersion);
//                //设置过滤条件 这个示例是监听最新的1000个块
//                BigInteger blockNumber = web3j.ethBlockNumber().send().getBlockNumber()
//                        .subtract(new BigInteger("28406817"));
//                EthFilter ethFilter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, contractAddress);
        EthFilter ethFilter = new EthFilter(DefaultBlockParameter.valueOf(BigInteger.valueOf(29003017)), DefaultBlockParameterName.LATEST, contractAddress);
//        BigInteger startBlock = BigInteger.valueOf(10000);
//        BigInteger endBlock = web3j.ethBlockNumber().send().getBlockNumber();
//        if (endBlock.subtract(startBlock).compareTo(BigInteger.valueOf(5000)) > 0) {
//            endBlock = startBlock.add(BigInteger.valueOf(11000));
//        }
//
//        EthFilter ethFilter = new EthFilter(
//                new DefaultBlockParameterNumber(startBlock),
//                new DefaultBlockParameterNumber(endBlock),
//                contractAddress
//        );
        //监听哪个事件，合约中的event写了几个参数，这里就写几个，类型对应好
        Event event = new Event("Transfer",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Address>(true) {},
                        new TypeReference<Address>(true) {},
                        new TypeReference<Uint256>(false) {}));
        ethFilter.addSingleTopic(EventEncoder.encode(event));
        subscribe = web3j.ethLogFlowable(ethFilter).subscribe(tx -> {
            int newBlock = tx.getBlockNumber().intValue();
            log.info("wen3j subscribe --newBlock-- :{} ", newBlock);
            log.info("wen3j subscribe --tx-- :{} ", tx);
            EventValues eventValues = staticExtractEventParameters(event, tx);
            //定义接收参数(本示例使用的事件返回了6个，具体按自己合约来)
//                    String address1 = "";
//                    String address2 = "";
//                    int uint1 = 0;
//                    int uint2 = 0;
//                    int uint3 = 0;
//                    int uint4 = 0;
//                    List<Type> indexedValues = eventValues.getIndexedValues();
//                    if (ObjectUtils.isNotEmpty(indexedValues) && indexedValues.size() == 3) {
//                        Type type1 = indexedValues.get(0);
//                        address1 = type1.getValue().toString();
//                        Type type2 = indexedValues.get(1);
//                        address2 = type2.getValue().toString();
//                        Type type3 = indexedValues.get(2);
//                        uint1 = Integer.parseInt(type3.getValue().toString());
//                    }
//                    List<Type> nonIndexedValues = eventValues.getNonIndexedValues();
//                    if (ObjectUtils.isNotEmpty(nonIndexedValues) && nonIndexedValues.size() == 3) {
//                        Type type1 = nonIndexedValues.get(0);
//                        uint2 = Integer.parseInt(type1.getValue().toString());
//                        Type type2 = nonIndexedValues.get(1);
//                        uint3 = Integer.parseInt(type2.getValue().toString());
//                        Type type3 = nonIndexedValues.get(2);
//                        uint4 = Integer.parseInt(type3.getValue().toString());
//                    }
//                    log.info("address1:{};address2:{};uint1:{};uint2:{};uint3:{};uint4:{}",address1, address2, uint1, uint2, uint3, uint4);
        });
//            }catch (Exception e) {
//                e.printStackTrace();
//            }finally {
//                //关闭监听
//                if(ObjectUtils.isNotEmpty(subscribe) && !subscribe.isDisposed()){
//                    subscribe.dispose();
//                }
//            }
    }
    @org.junit.jupiter.api.Test
    @Order(10)
    public void createTemplate() {
        String templateName = "VerifyCodeTemplate-English";
        String subjectPart = "Verification - Chess of Stars";
//        String textPart = "Dear {{name}},\nYour favorite animal is {{favoriteanimal}}.";
        String textPart = "Dear user,\n" +
                "We have received a request to authorize this email address for use with Chess of Stars. If you requested this verification, please enter the verification code {{code}} (valid for 5 minutes) to complete the operation.\n" +
                "Note: This operation may modify your password, login email or bound phone number. If this operation was not initiated by you, please log in promptly and modify your password to ensure account security.\n" +
                "Staff will never ask you for this verification code, do not disclose it!\n" +
                "This is a system email, please do not reply. Please keep your email secure to prevent your account from being stolen by others.\n" +
                "——Chess of Stars" ;
        String htmlPart = "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Verification - Chess of Stars</title>\n" +
                "    <style>\n" +
                "        table {\n" +
                "            width: 700px;\n" +
                "            margin: 0 auto;\n" +
                "        }\n" +
                "\n" +
                "        #top {\n" +
                "            width: 700px;\n" +
                "            border-bottom: 1px solid #ccc;\n" +
                "            margin: 0 auto 30px;\n" +
                "        }\n" +
                "\n" +
                "        #top table {\n" +
                "            font: 12px Tahoma, Arial, 宋体;\n" +
                "            height: 40px;\n" +
                "        }\n" +
                "\n" +
                "        #content {\n" +
                "            width: 680px;\n" +
                "            padding: 0 10px;\n" +
                "            margin: 0 auto;\n" +
                "        }\n" +
                "\n" +
                "        #content_top {\n" +
                "            line-height: 1.5;\n" +
                "            font-size: 14px;\n" +
                "            margin-bottom: 25px;\n" +
                "            color: #4d4d4d;\n" +
                "        }\n" +
                "\n" +
                "        #content_top strong {\n" +
                "            display: block;\n" +
                "            margin-bottom: 15px;\n" +
                "        }\n" +
                "\n" +
                "        #content_top strong span {\n" +
                "            color: #f60;\n" +
                "            font-size: 16px;\n" +
                "        }\n" +
                "\n" +
                "        #verificationCode {\n" +
                "            color: #f60;\n" +
                "            font-size: 24px;\n" +
                "        }\n" +
                "\n" +
                "        #content_bottom {\n" +
                "            margin-bottom: 30px;\n" +
                "        }\n" +
                "\n" +
                "        #content_bottom small {\n" +
                "            display: block;\n" +
                "            margin-bottom: 20px;\n" +
                "            font-size: 12px;\n" +
                "            color: #747474;\n" +
                "        }\n" +
                "\n" +
                "        #bottom {\n" +
                "            width: 700px;\n" +
                "            margin: 0 auto;\n" +
                "        }\n" +
                "\n" +
                "        #bottom div {\n" +
                "            padding: 10px 10px 0;\n" +
                "            border-top: 1px solid #ccc;\n" +
                "            color: #747474;\n" +
                "            margin-bottom: 20px;\n" +
                "            line-height: 1.3em;\n" +
                "            font-size: 12px;\n" +
                "        }\n" +
                "\n" +
                "        #content_top strong span {\n" +
                "            font-size: 18px;\n" +
                "            color: #FE4F70;\n" +
                "        }\n" +
                "\n" +
                "        #sign {\n" +
                "            text-align: right;\n" +
                "            font-size: 18px;\n" +
                "            color: #FE4F70;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "\n" +
                "        #verificationCode {\n" +
                "            height: 100px;\n" +
                "            width: 680px;\n" +
                "            text-align: center;\n" +
                "            margin: 30px 0;\n" +
                "        }\n" +
                "\n" +
                "        #verificationCode div {\n" +
                "            height: 100px;\n" +
                "            width: 680px;\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "        .button {\n" +
                "            color: #FE4F70;\n" +
                "            margin-left: 10px;\n" +
                "            height: 80px;\n" +
                "            min-width: 80px;\n" +
                "            resize: none;\n" +
                "            font-size: 42px;\n" +
                "            border: none;\n" +
                "            outline: none;\n" +
                "            padding: 10px 15px;\n" +
                "            background: #ededed;\n" +
                "            text-align: center;\n" +
                "            border-radius: 17px;\n" +
                "            box-shadow: 6px 6px 12px #cccccc,\n" +
                "            -6px -6px 12px #ffffff;\n" +
                "        }\n" +
                "\n" +
                "        .button:hover {\n" +
                "            box-shadow: inset 6px 6px 4px #d1d1d1,\n" +
                "            inset -6px -6px 4px #ffffff;\n" +
                "        }\n" +
                "\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table>\n" +
                "    <tbody>\n" +
                "    <tr>\n" +
                "        <td>\n" +
                "            <div id=\"top\">\n" +
                "                <table>\n" +
                "                    <tbody><tr><td></td></tr></tbody>\n" +
                "                </table>\n" +
                "            </div>\n" +
                "\n" +
                "            <div id=\"content\">\n" +
                "                <div id=\"content_top\">\n" +
                "                    <strong>Dear User,</strong>\n" +
                "                    <strong>\n" +
                "                        We have received a request to authorize this email address for use with <span>Chess of Stars</span>. If you requested this verification, please enter the following verification code  <span>(valid for 5 minutes)</span> to complete the operation:\n" +
                "                    </strong>\n" +
                "                    <div id=\"verificationCode\">\n" +
                "                        <!--                        <button class=\"button\" th:each=\"a:${code}\">[[${a}]]</button>-->\n" +
                "                        <button class=\"button\" th:text=\"${code}\">{{code}}</button>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <div id=\"content_bottom\">\n" +
                "                    <small>\n" +
                "                        Note: This operation may modify your password, login email or bound phone number. If this operation was not initiated by you, please log in promptly and modify your password to ensure account security.\n" +
                "                        <br>(Staff will never ask you for this verification code, do not disclose it!)\n" +
                "                    </small>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div id=\"bottom\">\n" +
                "                <div>\n" +
                "                    <p>This is a system email, please do not reply.<br>\n" +
                "                        Please keep your email secure to prevent your account from being stolen by others.\n" +
                "                    </p>\n" +
                "                    <p id=\"sign\">——Chess of Stars</p>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    </tbody>\n" +
                "</table>\n" +
                "</body>";

        EmailTemplateContent templateContent = EmailTemplateContent.builder()
                .subject(subjectPart)
                .text(textPart)
                .html(htmlPart)
                .build();

        CreateEmailTemplateRequest createEmailTemplateRequest = CreateEmailTemplateRequest.builder()
                .templateName(templateName)
                .templateContent(templateContent)
                .build();
        assertDoesNotThrow(() -> MailUtil.createTemplate(sesV2Client,createEmailTemplateRequest));
        System.out.println("Test 4 passed");
    }
    @org.junit.jupiter.api.Test
    @Order(9)
    public void DeleteEmailTemplates() {
        DeleteEmailTemplateRequest deleteEmailTemplateRequest = DeleteEmailTemplateRequest.builder()
                .templateName("VerifyCodeTemplate")
                .build();
        assertDoesNotThrow(() -> MailUtil.deleteTemplate(sesV2Client,deleteEmailTemplateRequest));
        System.out.println("Test 4 passed");
    }

    @Test
    public void test6(){
        String errMsg = "[code=500604, msg=Token other error：the token is invalid, please obtain it again.]";

// 去除左右两侧的 []
        errMsg = errMsg.substring(1, errMsg.length() - 1);

// 按照 "," 分割字符串，获取 msg 部分
        String[] parts = errMsg.split(", ");

// 选择 msg 部分
        String msg = parts[1];

        System.out.println(msg);
    }

    @Test
    public void test7() throws Exception {
        String key = "BLOCKCHAINAESKEY";
        String iv = "AESKEYBLOCKCHAIN";
        String message = "Xiao120315!";

        String encrypted = encryptAES(message, key, iv);
        System.out.println(encrypted);
    }
    public static String encryptAES(String message, String key, String iv) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] encryptedBytes = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return toHex(encryptedBytes).toUpperCase();

    }
    public static String toHex(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
}

