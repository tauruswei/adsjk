package org.cos.common.blockchain;

import io.reactivex.disposables.Disposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
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
import org.web3j.tx.Contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ydw
 * @version 1.0
 * @date 2020/11/6 11:15
 */
@Component
public class ContractListener {

    @Autowired
    Web3j web3j;
    @Autowired
    @Qualifier("inviteFilter") // 你自己创建的过滤器
    EthFilter inviteFilter;

    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void registerEthFilters() {
        inviteFilterHandle();
    }

    private void inviteFilterHandle() {
        Logger log = LoggerFactory.getLogger(ContractListener.class);
        String contractAddress="0xFF16bFd957f0376012ec4e0CF3Aa0c3D1c487f3F";
        Web3j web3j = Web3j.build(new HttpService("https://endpoints.omniatech.io/v1/bsc/testnet/public"));
//        String contractAddress="0x1f49b66d82e55eB2e02cC2b1FddBC70978469238";
//        Web3j web3j = Web3j.build(new HttpService("https://bsc-dataseed.binance.org"));

        //第一步生成的合约实体
        Contract contract;
        Disposable subscribe = null;
        EthFilter ethFilter = new EthFilter(DefaultBlockParameter.valueOf(BigInteger.valueOf(28422000)), DefaultBlockParameterName.LATEST, contractAddress);
        //监听哪个事件，合约中的event写了几个参数，这里就写几个，类型对应好
//        Event event = new Event("Transfer",
//                Arrays.<TypeReference<?>>asList(
//                        new TypeReference<Address>(true) {},
//                        new TypeReference<Address>(true) {},
//                        new TypeReference<Uint256>(false) {}));
        Event event = new Event("BuyToken",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Address>(false) {},
                        new TypeReference<Uint256>(true) {},
                        new TypeReference<Uint256>(true) {}));
        ethFilter.addSingleTopic(EventEncoder.encode(event));
        subscribe = web3j.ethLogFlowable(ethFilter).subscribe(tx -> {
            int newBlock = tx.getBlockNumber().intValue();
            log.info("wen3j subscribe --newBlock-- :{} ", newBlock);
            log.info("wen3j subscribe --tx-- :{} ", tx);
//          EventValues eventValues = staticExtractEventParameters(event, tx);
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
}

