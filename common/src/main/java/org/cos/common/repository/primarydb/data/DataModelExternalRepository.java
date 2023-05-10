package org.cos.common.repository.primarydb.data;

import org.cos.common.entity.data.po.DataModelExternal;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DataModelExternalRepository {
    void insertDataModelExternal(DataModelExternal dataModelExternal);
    void updateDataModelExternalCurrentSize(@Param("fileId") Long fileId, @Param("fileCurrentSize") Long fileCurrentSize);
}
