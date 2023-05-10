package org.cos.common.entity.data.vo;

import org.cos.common.entity.data.dto.ModelDerivationDto;
import org.cos.common.entity.data.po.*;
import lombok.Data;

import java.util.List;

@Data
public class ShareModelVo {


    public ShareModelVo(DataProject project) {
        this.projectId = project.getProjectId();
        this.serverAddress = project.getServerAddress();
    }

    public ShareModelVo() {
    }

    private String projectId;
    private String serverAddress;
    private DataModel dataModel;
    private DataTask dataTask;
    private DataModelTask dataModelTask;
    private List<DataModelResource> dmrList;
    private Long timestamp;
    private Integer nonce;
    private List<String> shareOrganId;
    private List<ModelDerivationDto> derivationList;

    public void init(DataProject project){
        this.projectId = project.getProjectId();
        this.serverAddress = project.getServerAddress();
    }

    public void supplement(){
        this.timestamp = System.currentTimeMillis();
        this.nonce = (int)Math.random()*100;
    }
}
