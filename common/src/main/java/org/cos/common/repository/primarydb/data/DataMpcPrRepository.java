package org.cos.common.repository.primarydb.data;

import org.cos.common.entity.data.po.DataMpcTask;
import org.cos.common.entity.data.po.DataScript;
import org.springframework.stereotype.Repository;

@Repository
public interface DataMpcPrRepository {

    void saveDataScript(DataScript dataScript);

    void updateDataScript(DataScript dataScript);

    void deleteDataScript(Long scriptId);

    void saveDataMpcTask(DataMpcTask dataMpcTask);

    void updateDataMpcTask(DataMpcTask dataMpcTask);
}
