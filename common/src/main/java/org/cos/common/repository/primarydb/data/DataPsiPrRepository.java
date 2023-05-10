package org.cos.common.repository.primarydb.data;

import org.cos.common.entity.data.po.DataPsi;
import org.cos.common.entity.data.po.DataPsiTask;
import org.springframework.stereotype.Repository;

@Repository
public interface DataPsiPrRepository {

    void saveDataPsi(DataPsi dataPsi);
    void saveDataPsiTask(DataPsiTask dataPsiTask);

    void updateDataPsiTask(DataPsiTask dataPsiTask);

    void delPsiTask(Long taskId);

    void delPsi(Long psiId);
}
