import com.alibaba.fastjson.JSON;
import io.reactivex.disposables.Disposable;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.cos.application.CosdApplication;
import org.cos.common.entity.data.req.CosdStakeForSLReq;
import org.cos.common.redis.RedisService;
import org.cos.common.redis.TransactionKey;
import org.cos.common.util.ZipUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
import redis.clients.jedis.StreamEntry;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.StreamPendingEntry;
import redis.clients.jedis.params.XReadGroupParams;

import java.io.IOException;
import java.math.BigInteger;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

@SpringBootTest(classes = CosdApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class Test1 {
    @Autowired
    RedisService redisService;
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
    @Test
    public void test6(){

//        redisService.xgroupCreate("testStream", "testGGroup", true);
        Map<String,String> map = new HashMap<>();
        map.put("testKey1","testValue1");
        map.put("testKey2","testValue2");
        map.put("testKey3","testValue3");
        map.put("testKey4","testValue4");
        redisService.xadd(TransactionKey.getTx,"tx",map);

    }
    @Test
    public void test7(){

        redisService.xgroupCreate("testStream", "testGGroup", true);
//        Map<String,String> map = new HashMap<>();
//        map.put("testKey5","testValue5");


//        redisService.xadd("testStream",map);

        // Setup parameters
        XReadGroupParams xReadGroupParams = new XReadGroupParams().noAck().count(2);
        Map<String, StreamEntryID> entry = new HashMap<>();
        entry.put("testStream", StreamEntryID.UNRECEIVED_ENTRY);
        List<Map.Entry<String, List<StreamEntry>>> entries = redisService.xreadGroup(TransactionKey.getTx, "tx", CosdStakeForSLReq.class);
// Read from stream
//        List<StreamEntry> result = jedisCluster.xreadGroup(group, consumer, xReadGroupParams, entry);
//
// Process result
//        for (StreamEntry message : entries) {
//            System.out.println("Received message: " + message.getID() + " = " + message.getFields());
//        }
//        redisService.xreadGroup("testGGroup","a",)
//        redisService.xadd("testStream",map);
        System.out.println(entries);

    }
    @Test
    public void test8(){

//        redisService.xgroupCreate("testStream", "testGGroup", true);


        for (int i = 0; i < 20; i++) {
            CosdStakeForSLReq cosdStakeForSLReq = new CosdStakeForSLReq();
            cosdStakeForSLReq.setTxId("tx"+i);
            cosdStakeForSLReq.setBlockNumber((long) i);

            Map<String,String> map = new HashMap<>();
            map.put("tx", JSON.toJSONString(cosdStakeForSLReq));
            redisService.xadd(TransactionKey.getTx,"tx",cosdStakeForSLReq);
        }
    }

    @Test
    public void test9(){
        XReadGroupParams xReadGroupParams = new XReadGroupParams().count(5);
        Map<String, StreamEntryID> entry = new HashMap<>();
        entry.put("testStream", StreamEntryID.UNRECEIVED_ENTRY);
        List<Map.Entry<String, List<StreamEntry>>> entries = redisService.xreadGroup(TransactionKey.getTx,"tx", CosdStakeForSLReq.class);
        if (ObjectUtils.isNotEmpty(entries)&& entries.size()>0){
            Map.Entry<String, List<StreamEntry>> stringListEntry = entries.get(0);
            List<StreamEntry> value = stringListEntry.getValue();
            for(StreamEntry streamEntry:value){
                Map<String, String> fields = streamEntry.getFields();
                for (Map.Entry<String, String> fieldEntry : fields.entrySet()) {
                    System.out.println("Field: " + fieldEntry.getKey() + " Value: " + fieldEntry.getValue());
                }
            }
        }
    }

    @Test
    public void test10(){
        String streamName = "testStream";
        String groupName = "testGroup";
        String consumerName = "consumer_a";
//        List<Object> xpending = redisService.xpending(streamName, groupName, consumerName);
//        System.out.println(xpending);
//
//        for (Object message : xpending) {
//            List<Object> message1 = (List<Object>) message;
//            String id = new String( (byte[])message1.get(0),StandardCharsets.UTF_8); // 消息ID
//            String consumerName1 = new String((byte[]) message1.get(1),StandardCharsets.UTF_8); // 消费者名
//            long elapsedMillis = (Long) message1.get(2); // 自消费者读取此消息以来的毫秒数
//            long timesDelivered = (Long) message1.get(3); // 消息被传递的次数
//
//            System.out.println("Message ID: " + id);
//            System.out.println("Consumer Name: " + consumerName1);
//            System.out.println("Elapsed Milliseconds: " + elapsedMillis);
//            System.out.println("Times Delivered: " + timesDelivered);
//        }
        List<StreamPendingEntry> xpending = redisService.xpending(TransactionKey.getTx,"",CosdStakeForSLReq.class);
        for ( StreamPendingEntry message : xpending) {
            List<StreamEntry> xrange = redisService.xrange(streamName, message.getID(), message.getID(), 1);
            System.out.println(xrange.get(0).getFields());
            System.out.println(message);
        }
    }
}
