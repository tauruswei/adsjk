package org.cos.common.entity.data.req;

import com.alibaba.fastjson.JSONObject;
import org.cos.common.entity.data.dto.ModelDerivationDto;
import org.cos.common.entity.data.po.*;
import org.cos.common.entity.data.vo.ModelProjectResourceVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

@Data
@Slf4j
public class  ComponentTaskReq {
    private DataModel dataModel;
    private DataModelTask dataModelTask;
    // 只是记录 task 对状态，不保存 componnet json
    private DataTask dataTask;
    private List<DataModelResource> dmrList = new ArrayList<>();
    private List<ModelProjectResourceVo> resourceList;
    private Map<String,String> freemarkerMap = new HashMap<>();
    private DataModelAndComponentReq modelComponentReq = null;
    private List<DataModelComponent> dataModelComponents = new ArrayList<>();
    private List<DataComponent> dataComponents = new ArrayList<>();
    private Map<String, String> valueMap = new HashMap<>();
    private List<LinkedHashMap<String,Object>> fusionResourceList;
    private List<ModelDerivationDto> derivationList = new ArrayList<>();
    private List<ModelDerivationDto> newest;
    private String serverAddress;
    private int job = 0;

    public ComponentTaskReq(DataModel dataModel) {
        this.dataModel = dataModel;
        this.dataTask = new DataTask();
    }

    public DataModelAndComponentReq getModelComponentReq(){
        if (modelComponentReq!=null)
            return modelComponentReq;
        try {
            if (StringUtils.isNotBlank(dataModel.getComponentJson())) {
                modelComponentReq  = JSONObject.parseObject(dataModel.getComponentJson(), DataModelAndComponentReq.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelComponentReq;
    }

    public int getJob() {
        job++;
        return job;
    }
}
