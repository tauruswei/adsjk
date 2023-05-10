package org.cos.common.repository.primarydb.data;


import org.cos.common.entity.data.po.DataSecretkey;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSecretkeyPrRepository {

    void saveDataSecretkey(DataSecretkey dataSecretkey);

}
