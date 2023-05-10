import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.List;

public class TestSentinel {
    @Test
    public void test(){
        // 定义 Sentinel Dashboard 的地址
        String dashboardUrl = "http://localhost:8719";
        String appName = "gateway";

        // 获取应用的所有流控规则
        List<FlowRule> flowRules = FlowRuleManager.getRules();
        System.out.println("应用【" + appName + "】的流控规则：");
        System.out.println(JSON.toJSONString(flowRules));

//        // 获取 Gateway 应用的所有规则
//        List<GatewayRule> gatewayRules = GatewayRuleManager.getRules();
//        System.out.println("Gateway应用的所有规则：");
//        System.out.println(JSON.toJSONString(gatewayRules, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat));
//
//        // 获取 Gateway 参数流控规则
//        List<GatewayParamFlowRule> paramFlowRules = GatewayParamFlowRuleManager.getRules();
//        System.out.println("Gateway应用的参数流控规则：");
//        System.out.println(JSON.toJSONString(paramFlowRules, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat));
//
//        // 获取 Gateway 参数流控 item
//        List<GatewayParamFlowItem> items = GatewayParamFlowItemManager.getItems();
//        System.out.println("Gateway应用的参数流控项：");
//        System.out.println(JSON.toJSONString(items, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat));
//
//        // 获取系统规则
//        List<SystemRule> systemRules = SystemRuleManager.getRules();
//        System.out.println("应用【" + appName + "】的系统规则：");
//        System.out.println(JSON.toJSONString(systemRules));
//
//        // 获取 Gateway 流控规则
//        List<GatewayFlowRule> gatewayFlowRules = GatewayRuleManager.getRulesByType(GatewayRule.SC_GATEWAY_FLOW);
//        System.out.println("应用【" + appName + "】的Gateway流控规则：");
//        System.out.println(JSON.toJSONString(gatewayFlowRules));
//
//        System.out.println("Sentinel API 上的应用节点信息：");
//        DefaultNode node = new DefaultNode(appName, EntryType.IN);
//        System.out.println(node);
//
//        // 获取 Servlet 规则
//        WebServletConfig servletConfig = new WebServletConfig();
//        servletConfig.filter(new CommonFilter());
//        List<FlowRule> rules = servletConfig.getServletFilters()
//                .stream()
//                .filter(filter -> filter instanceof CommonFilter)
//                .findFirst()
//                .map(filter -> ((CommonFilter) filter).getRules())
//                .orElse(null);
//        if (rules != null) {
//            System.out.println("Servlet 规则：");
//            System.out.println(JSON.toJSONString(rules));
//        }
    }
}
