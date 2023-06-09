package org.cos.common.convert;

import org.cos.common.entity.data.po.DataSource;
import org.cos.common.entity.data.req.DataSourceReq;

public class DataSourceConvert {

    public static DataSource DataSourceReqConvertPo(DataSourceReq req){
        DataSource dataSource = new DataSource();
        dataSource.setId(req.getId());
        dataSource.setDbType(req.getDbType());
        dataSource.setDbDriver(req.getDbDriver());
        dataSource.setDbUrl(req.getDbUrl());
        dataSource.setDbName(req.getDbName());
        dataSource.setDbTableName(req.getDbTableName());
        dataSource.setDbUsername(req.getDbUsername());
        dataSource.setDbPassword(req.getDbPassword());
        return dataSource;

    }
}
