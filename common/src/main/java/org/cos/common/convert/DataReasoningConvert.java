package org.cos.common.convert;

import org.cos.common.entity.data.po.DataReasoning;
import org.cos.common.entity.data.po.DataReasoningResource;
import org.cos.common.entity.data.req.DataReasoningReq;
import org.cos.common.entity.data.req.DataReasoningResourceReq;
import org.cos.common.entity.data.vo.DataReasoningVo;

import java.util.UUID;

public class DataReasoningConvert {

    public static DataReasoning dataReasoningReqConvertPo(DataReasoningReq req){
        DataReasoning dataReasoning = new DataReasoning();
        dataReasoning.setReasoningId(UUID.randomUUID().toString());
        dataReasoning.setReasoningName(req.getReasoningName());
        dataReasoning.setReasoningDesc(req.getReasoningDesc());
        dataReasoning.setReasoningType(req.getResourceList().size());
        dataReasoning.setReasoningState(0);
        dataReasoning.setTaskId(req.getTaskId());
        dataReasoning.setUserId(req.getUserId());
        return dataReasoning;
    }

    public static DataReasoningResource dataReasoningResourceReqConvertPo(DataReasoningResourceReq req,Long reasoningId,String serverAddress){
        DataReasoningResource dataReasoningResource = new DataReasoningResource();
        dataReasoningResource.setReasoningId(reasoningId);
        dataReasoningResource.setResourceId(req.getResourceId());
        dataReasoningResource.setParticipationIdentity(req.getParticipationIdentity());
        dataReasoningResource.setServerAddress(serverAddress);
        return dataReasoningResource;
    }

    public static DataReasoningVo dataReasoningConvertVo(DataReasoning dataReasoning){
        DataReasoningVo dataReasoningVo = new DataReasoningVo();
        dataReasoningVo.setId(dataReasoning.getId());
        dataReasoningVo.setReasoningId(dataReasoning.getReasoningId());
        dataReasoningVo.setReasoningName(dataReasoning.getReasoningName());
        dataReasoningVo.setReasoningDesc(dataReasoning.getReasoningDesc());
        dataReasoningVo.setReasoningType(dataReasoning.getReasoningType());
        dataReasoningVo.setReasoningState(dataReasoning.getReasoningState());
        dataReasoningVo.setTaskId(dataReasoning.getTaskId());
        dataReasoningVo.setReleaseDate(dataReasoning.getReleaseDate());
        dataReasoningVo.setRunTaskId(dataReasoning.getRunTaskId());
        return dataReasoningVo;
    }

}
