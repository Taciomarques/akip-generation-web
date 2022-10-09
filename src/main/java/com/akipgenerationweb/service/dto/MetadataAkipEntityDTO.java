package com.akipgenerationweb.service.dto;

import java.io.Serializable;

public class MetadataAkipEntityDTO implements Serializable {

    private String service = "serviceClass";

    private String dto = "mapstruct";

    private boolean jpaMetamodelFiltering = false;

    private String pagination = "no";

    private boolean skipFakeData = true;
}
