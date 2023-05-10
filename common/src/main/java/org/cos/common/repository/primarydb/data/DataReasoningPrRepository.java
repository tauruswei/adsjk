package org.cos.common.repository.primarydb.data;


import org.apache.ibatis.annotations.Param;
import org.cos.common.entity.data.po.DataReasoning;
import org.cos.common.entity.data.po.DataReasoningResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataReasoningPrRepository {
    void saveDataReasoning(DataReasoning dataReasoning);

    void updateDataReasoning(DataReasoning dataReasoning);

    void deleteDataReasoning(Long id);

    void saveDataReasoningResources(@Param("list") List<DataReasoningResource> list);

    void deleteDataReasoningResources(Long reasoningId);
}
