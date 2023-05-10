package org.cos.common.repository.primarydb.data;

import org.cos.common.entity.data.po.DataFusionCopyTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DataCopyPrimarydbRepository {
    void updateCopyInfo(@Param("id")Long id, @Param("currentOffset")Long currentOffset, @Param("latestErrorMsg")String latestErrorMsg);

    void saveCopyInfo(DataFusionCopyTask copyTask);
}
