package org.cos.common.repository.primarydb.sys;

import org.cos.common.entity.sys.po.SysFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysFilePrimarydbRepository {
    void insertSysFile(SysFile sysFile);
    void updateSysFileCurrentSize(@Param("fileId") Long fileId, @Param("fileCurrentSize") Long fileCurrentSize);
}
