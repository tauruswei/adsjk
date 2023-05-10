package org.cos.common.repository.primarydb.data;

import org.cos.common.entity.data.po.DataPirTask;
import org.cos.common.entity.data.po.DataTask;
import org.springframework.stereotype.Repository;

@Repository
public interface DataTaskPrRepository {

    void saveDataTask(DataTask dataTask);

    void updateDataTask(DataTask dataTask);

    void deleteDataTask(Long taskId);

    void saveDataPirTask(DataPirTask DataPirTask);

    void deleteDataPirTask(Long taskId);

}
