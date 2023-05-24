package org.cos.common.constant;

public class CommonConstant {
    // fusion api address
    public static final String FUSION_RESOURCE_LIST_BY_ID_URL = "<address>/fusionResource/getResourceListById";
    public static final String FUSION_ORGAN_BY_GLOBAL_ID_URL = "<address>/fusion/findOrganByGlobalId";


    // sync Project api
    public static final String PROJECT_SYNC_API_URL = "<address>/share/shareData/syncProject";
    public static final String MODEL_SYNC_API_URL = "<address>/share/shareData/syncModel";

    // blockchain gateway api
    public static final String FAB_INVOKE = "<address>/gateway/api/v1/channels/<chanel_name>/transactions";
    public static final String FAB_QUERY = "<address>/gateway/api/v1/channels/<chanel_name>/state-query";
    public static final String FAB_SUBSCRIBE = "<address>/gateway/api/v1/channels/<chanel_name>/subscriptions";

    public static final String CM_INVOKE = "<address>/gateway/api/v1/transaction/invoke";
    public static final String CM_QUERY = "<address>/gateway/api/v1/transaction/query";

    // 用户资产类型
    public static final int USDT=0;
    public static final int COSD=1;
    public static final int NFT=2;
    public static final int EVIC=3;
    public static final int SL=4;

    // nft 的状态
    public static final int NFT_PURCHASED=0;
    public static final int NFT_USED=1;
    public static final int NFT_INEFFECTIVE=2;

    // 质押池类型
    public static final int POOL_DEFI=1;
    public static final int POOL_SL=2;
    public static final int POOL_CLUB=3;


}
