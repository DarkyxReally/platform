package com.platform.system.common.web;


import java.util.List;

import com.platform.system.common.model.OperateLogDTO;

/**
 * @version: 1.0
 */
public interface SaveOperateLog {

    void save(List<OperateLogDTO> entity);
}
